package com.example.reponse;

/**
 * Author : JinTao Li
 * Create Time : 2020/1/16
 */
public class BaseResponse {

    /**
     * status : 0
     * info : INVALID_PARAMS
     * infocode : 20000
     */

    private String status;
    private String info;
    private String infocode;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfocode() {
        return infocode;
    }

    public void setInfocode(String infocode) {
        this.infocode = infocode;
    }
}
