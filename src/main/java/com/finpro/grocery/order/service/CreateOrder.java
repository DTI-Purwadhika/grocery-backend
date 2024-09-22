package com.finpro.grocery.order.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finpro.grocery.cart.entity.Cart;
import com.finpro.grocery.cart.entity.CartItem;
import com.finpro.grocery.cart.service.ReadCart;
import com.finpro.grocery.inventory.entity.Inventory;
import com.finpro.grocery.inventory.service.ReadStock;
import com.finpro.grocery.order.dto.response.InvoiceDTO;
import com.finpro.grocery.order.entity.Order;
import com.finpro.grocery.order.entity.OrderProduct;
import com.finpro.grocery.order.entity.OrderStatus;
import com.finpro.grocery.order.repository.OrderRepository;
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
  private ReadCart cartService;

  @Autowired
  private PaymentService paymentService;

  @Autowired
  private SequenceService sequenceService;

  @Transactional
  public InvoiceDTO save(Long cartId) {
    // 1. Verify stock across warehouses
    Cart cart = cartService.getCartById(cartId);
    verifyStock(cart);

    // 2. Find nearest warehouse using coordinates
    // ! For now, use the store of the cart
    // Store store = storeService.getStoreById(cart.getStore().getId());
   
    // 3. Create order
    Order order = createOrder(cart);

    // ! For now, use dummy user data
    Invoice orderInvoice = paymentService.createInvoice(order.getCode(), order.getTotalPayment(), "budi@gmail.com", "Payment for order " + order.getCode());
    order.setExpiryDate(orderInvoice.getExpiryDate());
    order.setInvoiceUrl(orderInvoice.getInvoiceUrl());
    order.setDescription(orderInvoice.getDescription());
    orderRepository.save(order);

    return InvoiceDTOConverter.convertToDTO(orderInvoice);
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

  private Order createOrder(Cart cart) {
    Order order = new Order();
    order.setUser(1L); // ! For now, use dummy user data
    // order.setDiscount(new Discount());
    order.setStore(cart.getStore());
    order.setStatus(OrderStatus.Menunggu_Pembayaran);
    order.setCode(sequenceService.generateUniqueCode("discount_code_sequence", "INV%09d"));
    order.setTotalAmount(10000);
    order.setTotalShipment(8000);
    order.setTotalDiscount(0);
    order.setTotalPayment(18000);
    
    List<OrderProduct> orderProducts = createOrderProduct(cart, order);
    order.setItems(orderProducts);

    return order;
  }

  private List<OrderProduct> createOrderProduct(Cart cart, Order order) {
    List<OrderProduct> orderProducts = new ArrayList<>();
    for (CartItem item : cart.getItems()) {
      Inventory inventory = stockService.checkStock(item.getProduct().getId(), cart.getStore().getId());
      OrderProduct orderProduct = new OrderProduct();
      
      orderProduct.setOrder(order);
      orderProduct.setInventory(inventory);
      // orderProduct.setDiscount();
      orderProduct.setQuantity(item.getQuantity());
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
