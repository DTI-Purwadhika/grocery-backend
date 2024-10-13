package com.finpro.grocery.order.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.finpro.grocery.cart.entity.Cart;
import com.finpro.grocery.cart.entity.CartItem;
import com.finpro.grocery.cart.service.DeleteCart;
import com.finpro.grocery.cart.service.ReadCart;
import com.finpro.grocery.inventory.entity.Inventory;
import com.finpro.grocery.inventory.service.ReadStock;
import com.finpro.grocery.inventory.service.UpdateStock;
import com.finpro.grocery.order.dto.response.OrderResponseDTO;
import com.finpro.grocery.order.entity.Order;
import com.finpro.grocery.order.entity.OrderProduct;
import com.finpro.grocery.order.entity.OrderStatus;
import com.finpro.grocery.order.repository.OrderRepository;
import com.finpro.grocery.store.service.StoreService;
import com.finpro.grocery.store.entity.Store;

import com.finpro.grocery.share.exception.BadRequestException;
import com.finpro.grocery.share.sequence.service.SequenceService;
import com.xendit.model.Invoice;

import jakarta.transaction.Transactional;

@Service
public class CreateOrder {
  
  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private ReadStock stockService;

  @Autowired
  private UpdateStock updateStockService;

  @Autowired
  private ReadCart cartService;

  @Autowired
  private DeleteCart deleteCart;

  @Autowired
  private PaymentService paymentService;

  @Autowired
  private SequenceService sequenceService;

  @Autowired
  private StoreService storeService;

  @Value("${baseurl.frontend}")
  private String baseUrl;

  @Transactional
  public OrderResponseDTO save(Long cartId, String method) {
    Cart cart = cartService.getCartById(cartId);
    verifyStock(cart);

    // 2. Find nearest warehouse using coordinates
    // ! For now, use the dummy store of the cart while wait store service
    Store store = storeService.getStoreById(1L);
   
    Order order = createOrder(cart, store);
    if(method.equals("manual")){
      order.setExpiryDate(Instant.now().plus(1, ChronoUnit.HOURS).toString());
      order.setInvoiceUrl(baseUrl+"/my-cart/checkout/" + order.getCode());
      order.setDescription("Manual transfer payment for " + order.getCode());
    }
    else if(method.equals("auto")){
      Invoice orderInvoice = paymentService.createInvoice(order.getCode(), order.getTotalPayment(), order.getUser().getEmail(), "Payment for order " + order.getCode());
      order.setExpiryDate(orderInvoice.getExpiryDate());
      order.setInvoiceUrl(orderInvoice.getInvoiceUrl());
      order.setDescription(orderInvoice.getDescription());
    }
    orderRepository.save(order);

    deleteCart.clear(cart.getId());
    
    return InvoiceDTOConverter.convertToDTO(order);
  }

  private void verifyStock(Cart cart) {
    List<String> emptyStock = new ArrayList<>();
    for (CartItem item : cart.getItems()) {
      if(!stockService.checkStockProduct(item.getProduct().getId(), item.getQuantity()))
        emptyStock.add(item.getProduct().getName());
    }
    if (!emptyStock.isEmpty())
      throw new BadRequestException("Out of stock: " + emptyStock);
  }

  private Order createOrder(Cart cart, Store store) {
    Order order = new Order();
    order.setUser(cart.getUser());
    order.setStore(store);
    order.setStatus(OrderStatus.Menunggu_Pembayaran);
    order.setCode(sequenceService.generateUniqueCode("order_code_sequence", "INV%09d"));
    
    BigDecimal totalAmount = BigDecimal.ZERO;
    for (CartItem item : cart.getItems())
      totalAmount = totalAmount.add(BigDecimal.valueOf(item.getQuantity()).multiply(item.getProduct().getPrice()));
    order.setTotalAmount(totalAmount);
    order.setTotalShipment(BigDecimal.valueOf(11500));
    order.setTotalDiscount(BigDecimal.valueOf(0));
    order.setTotalPayment(totalAmount.add(order.getTotalShipment()).subtract(order.getTotalDiscount()));
    
    List<OrderProduct> orderProducts = createOrderProduct(cart, order);
    order.setItems(orderProducts);
    return order;
  }

  private List<OrderProduct> createOrderProduct(Cart cart, Order order) {
    List<OrderProduct> orderProducts = new ArrayList<>();
    for (CartItem item : cart.getItems()) {
      Inventory inventory = stockService.checkStock(item.getProduct().getId(), 1L);
      OrderProduct orderProduct = new OrderProduct();
      
      orderProduct.setOrder(order);
      orderProduct.setInventory(inventory);
      orderProduct.setQuantity(item.getQuantity());
      updateStockService.decreaseStock(inventory, Long.valueOf(item.getQuantity()));
      BigDecimal price = item.getProduct().getPrice();
      BigDecimal quantity = BigDecimal.valueOf(item.getQuantity());
      orderProduct.setPrice(price);
      orderProduct.setTotalPrice(quantity.multiply(price));
      orderProduct.setDiscountAmount(BigDecimal.ZERO);
      orderProducts.add(orderProduct);
    }
    
    return orderProducts;
  }

}
