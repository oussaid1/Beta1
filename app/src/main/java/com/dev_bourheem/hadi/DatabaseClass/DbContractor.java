package com.dev_bourheem.hadi.DatabaseClass;

import android.provider.BaseColumns;

public final class DbContractor  {

    private DbContractor (){}

    public class TableColumns implements BaseColumns {


        public static final String MainTable = "Stuff";
        //public static final String ID = "ID";
        public static final String MItem_Name = "Item_Name";
        public static final String MItem_Price = "Item_Price";
        public static final String MShopName = "ShopName";
        public static final String MDate = "History";
        public static final String MQuantifier = "Quantifiers";
        public static final String MQuantity = "quantity";

        /*************************************************************/

        public static final String ArchiveTable = "StuffArch";
        public static final String ArItem_Name = "Item_Name";
        public static final String ArItem_Price = "Item_Price";
        public static final String ArShopName = "ShopName";
        public static final String ArDate = "History";
        public static final String ArQuantifier = "Quantifiers";
        public static final String ArQuantity = "quantity";


        public static final String ArchivePaymentTable = "ArchivePaymentTable";
        public static final String ArPayDate = "ArPayDate";
        public static final String ArshopName = "ArpaidAmount";
        public static final String ArpaidAmount = "ArpaidAmount";
        public static final String ArpaymentDate = "ArpaymentDate";
        /**************************************************************/

        public static final String PaymentTable = "Payment";
        public static final String PayDate = "PayDate";
        public static final String shopName = "paidAmount";
        public static final String paidAmount = "paidAmount";
        public static final String paymentDate = "paymentDate";

        public static final String InfoTable = "InfoTable";
        public static final String SHOP_NAME = "name";
        public static final String SHOP_PHONE = "phone";
        public static final String SHOP_EMAIL = "email";

        public static final String SettingsTable = "Payment";
        public static final String userQuota = "userQuota";
        public static final String userGQuota = "userGQuota";
    }

}
