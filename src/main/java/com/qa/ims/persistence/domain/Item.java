package com.qa.ims.persistence.domain;

public class Item {

    private Long id;
    private String itemName;
    private Double itemPrice;

    public Item(String itemName, Double itemPrice) {
        this.setItemName(itemName);
        this.setItemPrice(itemPrice);
    }

    public Item(Long id, String itemName, Double itemPrice) {
        this.setId(id);
        this.setItemName(itemName);
        this.setItemPrice(itemPrice);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemPrice(double itemPrice){
        this.itemPrice = itemPrice;
    }

    public Double getItemPrice() {
        return itemPrice;
    }

    @Override
    public String toString() {
        return "id:" + id + " item name:" + itemName;
    }

}
