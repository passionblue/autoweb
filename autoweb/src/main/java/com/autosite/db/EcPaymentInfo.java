package com.autosite.db;

public interface EcPaymentInfo {

    String getFirstName();
    String getMiddleInitial();
    String getLastName();
    String getAddress1();
    String getAddress2();
    String getCity();
    String getState();
    String getZip();
    String getCountry();
    String getPhone();
    int getPaymentType();
    String getCardType();
    String getPaymentNum();
    int getPaymentExpireMonth();
    int getPaymentExpireYear();
    String getPaymentExtraNum();
}
