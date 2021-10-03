package com.imozayozturk.accountmanagementsystem.config.swagger;

public class Host {

    private static final String DEFAULT_PROTOCOL = "http";

    private String url;
    private String protocol = DEFAULT_PROTOCOL;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
}
