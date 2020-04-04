package com.dev_bourheem.hadi.DatabaseClass;

import android.provider.BaseColumns;

public final class DbContractor  {

    private DbContractor (){}

    public class TableColumns implements BaseColumns {


        public static final String MainTable = "MainTable";
      //  public static final String ID = "ID";
        public static final String MItem_Name = "Item_Name";
        public static final String MItem_Price = "Item_Price";
        public static final String MShopName = "ShopName";
        public static final String MDate = "History";
        public static final String MQuantifier = "Quantifiers";
        public static final String MQuantity = "quantity";

        /*************************************************************/

        public static final String ArchiveTable = "ArchiveTable";
        public static final String ArItem_Name = "Item_Name";
        public static final String ArItem_Price = "Item_Price";
        public static final String ArShopName = "ShopName";
        public static final String ArDate = "History";
        public static final String ArQuantifier = "Quantifiers";
        public static final String ArQuantity = "quantity";


        public static final String ArchivePaymentTable = "ArchivePaymentTable";
        public static final String ArPaidShopName = "ArPshopName";
        public static final String ArPaidAmount = "ArpaidAmount";
        public static final String ArPaymentDate = "ArpaymentDate";
        /**************************************************************/

        public static final String PaymentTable = "PaymentTable";
        public static final String PaidShopName = "pshopName";
        public static final String PaidAmount = "paidAmount";
        public static final String PaymentDate = "paymentDate";

        public static final String InfoTable = "InfoTable";
        public static final String SHOP_NAME = "name";
        public static final String SHOP_PHONE = "phone";
        public static final String SHOP_EMAIL = "email";

        public static final String SettingsTable = "SettingsTable";
        public static final String userQuota = "userQuota";
        public static final String userGQuota = "userGQuota";

        public static final String BooleansTable = "SettingsTable";
        public static final String BooleanName = "userQuota";
        public static final String BooleanStatus = "userGQuota";
    }

}
