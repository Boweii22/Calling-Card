package com.example.businesscard;

public class DataHolder {
    private static DataHolder instance;
    private long data;
    private String cardColor;
    private String businessCardName;


    public static synchronized DataHolder getInstance() {
        if (instance == null) {
            instance = new DataHolder();
        }
        return instance;
    }

    public long getData() {
        return data;
    }

    public void setData(long data) {
        this.data = data;
    }

    public String getCardColor() {
        return cardColor;
    }

    public void setCardColor(String cardColor) {
        this.cardColor = cardColor;
    }

    public String getBusinessCardName() {
        return businessCardName;
    }

    public void  setBusinessCardName(String businessCardName){
        this.businessCardName = businessCardName;
    }
}
