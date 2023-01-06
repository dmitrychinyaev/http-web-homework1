package ru.netology;

import java.util.List;

public class Request {
    private String method;
    private String way;
    private String version;

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

    public Request(List<String> request) {
        this.method = request.get(0);
        this.way = request.get(1);
        this.version = request.get(2);
    }

}

