package com.qa.ims.persistence.domain;


import java.util.Objects;

public class Order {

    private Long id;
    private Long customerID;
    private String comments;

    public Order(Long customerID, String comments) {
        this.comments = comments;
        this.customerID = customerID;
    }

    public Order(Long id, Long customerID, String comments) {
        this.setId(id);
        this.setCustomerID(customerID);
        this.setComments(comments);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Long customer_id){
        this.customerID = customer_id;
    }

    public void setComments(String comments){
        this.comments = comments;
    }

    public String getComments(){
        return comments;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customerID=" + customerID +
                ", comments='" + comments + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(customerID, order.customerID) && Objects.equals(comments, order.comments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerID, comments);
    }

    //nested Inner class for OrderItems
    public static class OrderItems {
        private long order_items_id;
        private long order_id;
        private long item_code;
        private long quantity;

        public OrderItems(long order_id, long item_code, long quantity) {
            this.setItem_code(item_code);
            this.setQuantity(quantity);
            this.setOrderID(order_id);
        }

        public OrderItems(long order_items_id, long order_id, long item_code, long quantity) {
            this.setOrderItemsID(order_items_id);
            this.setItem_code(item_code);
            this.setQuantity(quantity);
            this.setOrderID(order_id);
        }

        public long getItem_code(){
            return item_code;
        }

        public void setItem_code(long item_code) {
            this.item_code = item_code;
        }

        public long getQuantity(){
            return quantity;
        }

        public void setQuantity(long quantity){
            this.quantity = quantity;
        }

        public void setOrderID(long order_id) {
            this.order_id = order_id;
        }

        public long getOrder_id(){
            return order_id;
        }

        public void setOrderItemsID(long order_items_id) {
            this.order_items_id = order_items_id;
        }

        public long getOrderItemsID() {
            return order_items_id;
        }

        @Override
        public String toString() {
            return "id: " + order_id + " item_id: " + item_code + " quantity: " + quantity;
        }
    }
}