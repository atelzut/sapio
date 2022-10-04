package com.interlem.sapio.bean;

import org.json.JSONObject;

public class ResponseDetails {


    public String type; //[required]: string, uniquely identifies the error in the context of the functionality that was invoked
    public String title;// [required]: string, gives a brief description of the error in natural language
    public String requestId; // [required]: alphanumeric string, uniquely identifies the request that resulted in an error

    public String detail;// string, describes the specific instance of the error

    //it must be useful to the caller's maintainers in order to identify the problem in case of debugging

    public String  status;//: number, represents the https status of the response containing the error (e.g.if the error was returned with status code 404, the field shows the value 404)


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "ResponseDetails{" +
                "type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", requestId='" + requestId + '\'' +
                ", detail='" + detail + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public JSONObject toJson(){
        return new JSONObject("{" +
                "type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", requestId='" + requestId + '\'' +
                ", detail='" + detail + '\'' +
                ", status='" + status + '\'' +
                '}');
    }


}
