package com.dev_bourheem.hadi;

public class exampleitem {

    private String quantity;
    private String quantifier;
    private String ItemName;
    private String ItemPrice;
    private String ShopName;
    private String dateBought;


    public exampleitem(String Qntt,String Quantif,String ITnm, String ITprix,String ShopName1,String dateBought1) {

        quantity =Qntt;
       quantifier=Quantif;
        ItemName = ITnm;
        ItemPrice = ITprix;
        ShopName = ShopName1;
        dateBought = dateBought1;

    }



    public String getShopName() {
        return ShopName;
    }

    public String getDateBought() {
        return dateBought;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getQuantifier() {
        return quantifier;
    }

    public String getItemName() {
        return ItemName;
    }

    public String getItemPrice() {
        return ItemPrice;
    }
}
