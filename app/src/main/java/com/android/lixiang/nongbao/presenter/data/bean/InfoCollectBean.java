package com.android.lixiang.nongbao.presenter.data.bean;

public class InfoCollectBean {


    /**
     * informationId : No123
     * message : 添加成功
     * status : 200
     */

    private String informationId;
    private String message;
    private int status;

    public String getInformationId() {
        return informationId;
    }

    public void setInformationId(String informationId) {
        this.informationId = informationId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
