package ru.netology;

import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.List;

public class Request {
    private String method;
    private String way;
    private String version;
    private List<NameValuePair> queryParams;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<NameValuePair> getQueryParams() {
        return queryParams;
    }

    public void setQueryParams(List<NameValuePair> queryParams) {
        this.queryParams = queryParams;
    }

    public List<NameValuePair> getQueryParam(String name){
        if (queryParams == null){
            return null;
        }
        List<NameValuePair> chosenQueryParam = new ArrayList<>();
        for (NameValuePair nameValuePair : queryParams){
            if(nameValuePair.getName().equals(name)){
                chosenQueryParam.add(nameValuePair);
            }
        }
        return chosenQueryParam;
    }

    public Request(List<String> request) {
        this.method = request.get(0);
        this.way = request.get(1);
        this.version = request.get(2);
    }

}

