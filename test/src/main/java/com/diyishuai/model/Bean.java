package com.diyishuai.model;

/**
 * @author Bruce
 * @date 2016/11/29
 */
public class Bean {
    private String xSysCode;
    private String xReqNbr;
    private String xTskNbr;

    public String getxSysCode() {
        return xSysCode;
    }

    public void setxSysCode(String xSysCode) {
        this.xSysCode = xSysCode;
    }

    public String getxReqNbr() {
        return xReqNbr;
    }

    public void setxReqNbr(String xReqNbr) {
        this.xReqNbr = xReqNbr;
    }

    public String getxTskNbr() {
        return xTskNbr;
    }

    public void setxTskNbr(String xTskNbr) {
        this.xTskNbr = xTskNbr;
    }

    @Override
    public String toString() {
        return xSysCode + "|" + xReqNbr+ "|"+ xTskNbr;
    }
}
