package com.loststars.tccbootx.gateway.controller.vo;

public class ResponseVO {

    private static final int STATUS_SUCCESS = 0;

    private static final int STATUS_FAIL = 99;

    private Integer status;

    private String msg;

    private Object data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static ResponseVO success(Object data) {
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(STATUS_SUCCESS);
        responseVO.setData(data);
        return responseVO;
    }

    public static ResponseVO fail(String msg) {
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(STATUS_FAIL);
        responseVO.setMsg(msg);
        return responseVO;
    }
}
