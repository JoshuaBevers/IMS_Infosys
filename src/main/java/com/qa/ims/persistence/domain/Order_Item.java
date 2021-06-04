package com.qa.ims.persistence.domain;

import com.qa.ims.persistence.dao.Order_ItemsDAO;
import com.qa.ims.utils.Utils;

public class Order_Item {

    private long order_items_id;
    private long order_id;
    private long item_code;
    private long quantity;

    public Order_Item(long order_id, long item_code, long quantity) {
        this.setItem_code(item_code);
        this.setQuantity(quantity);
        this.setOrderID(order_id);
    }

    public Order_Item(long order_items_id, long order_id, long item_code, long quantity) {
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
        return "id: " + order_id + " item_id: " + item_code + " quantity: " + quantity + "\n";
    }
}
