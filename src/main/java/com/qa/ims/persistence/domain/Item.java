package com.qa.ims.persistence.domain;

public class Item {

    private Long id;
    private String itemName;
    private Double itemPrice;

    public Item(String itemName, double itemPrice) {
        this.setItemName(itemName);
        this.setItemPrice(itemPrice);
    }

    public Item(Long id, String itemName, double itemPrice) {
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((itemName == null) ? 0 : itemName.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((itemPrice == null) ? 0 : itemPrice.hashCode());
        return result;
    }

    public Double getItemPrice() {
        return itemPrice;
    }

    @Override
    public String toString() {
        return "item_name is:" + itemName + " itemPrice:" + itemPrice;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Item other = (Item) obj;
        if (getItemName() == null) {
            if (other.getItemName() != null)
                return false;
        } else if (!getItemName().equals(other.getItemName()))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (itemPrice == null) {
            if (other.itemPrice != null)
                return false;
        } else if (!itemPrice.equals(other.itemPrice))
            return false;
        return true;
    }

}
