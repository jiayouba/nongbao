package com.android.lixiang.nongbao.ui;

public class Bank {
    private String bank_name;
    private int imageId;

    public Bank(String name,int imageId){
        this.bank_name=name;
        this.imageId=imageId;
    }

    public String getBank_name(){
        return bank_name;
    }

    public void setBank_name(String bank_name){
        this.bank_name=bank_name;
    }

    public int getImageId(){
       return imageId;
    }

    public void setImageId(int imageId){
        this.imageId=imageId;
    }

}
