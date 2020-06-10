package com.dev_bourheem.hadi;

public class ProductObject {

    private Double quantity;
    private String quantifier;
    private String ItemName;
    private Double ItemPrice;
    private String ShopName;
    private String dateBought;

    public ProductObject(Double quantity, String quantifier, String itemName, Double itemPrice, String shopName, String dateBought) {
        this.quantity = quantity;
        this.quantifier = quantifier;
        ItemName = itemName;
        ItemPrice = itemPrice;
        ShopName = shopName;
        this.dateBought = dateBought;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getQuantifier() {
        return quantifier;
    }

    public void setQuantifier(String quantifier) {
        this.quantifier = quantifier;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public Double getItemPrice() {
        return ItemPrice;
    }

    public void setItemPrice(Double itemPrice) {
        ItemPrice = itemPrice;
    }

    public String getShopName() {
        return ShopName;
    }

    public void setShopName(String shopName) {
        ShopName = shopName;
    }

    public String getDateBought() {
        return dateBought;
    }

    public void setDateBought(String dateBought) {
        this.dateBought = dateBought;
    }
}
