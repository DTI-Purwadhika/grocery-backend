package com.finpro.grocery.order.entity;

public enum OrderStatus {
  Menunggu_Pembayaran,
  Menunggu_Konfirmasi_Pembayaran,
  Diproses,
  Siap_Dikirim,
  Dikirim,
  Pesanan_Dikonfirmasi,
  Dibatalkan
}
