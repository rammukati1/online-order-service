package com.online.order.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;


public class OrderProcessingDTO {
    @NotNull
    private String customerName;


    @NotEmpty
    private List<ItemDto> items;


    public static class ItemDto {
        @NotNull
        public String productName;
        @NotNull
        public Integer quantity;
        @NotNull
        public BigDecimal unitPrice;
    }


    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public List<ItemDto> getItems() { return items; }
    public void setItems(List<ItemDto> items) { this.items = items; }
}