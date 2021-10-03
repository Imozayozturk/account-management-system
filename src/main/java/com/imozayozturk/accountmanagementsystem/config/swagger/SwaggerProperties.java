package com.imozayozturk.accountmanagementsystem.config.swagger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import springfox.documentation.service.Contact;

@ConfigurationProperties(prefix = "swagger")
public class SwaggerProperties {

    private String version;

    private Host host;

    private Contact contact;

    private License license;

    @Value("${spring.application.name:Api Documentation}")
    private String appName;

    @Value("${spring.application.description}")
    private String description;

    @Value("${swagger.ignoredParameterTypes:#{T(org.apache.commons.lang3.ArrayUtils).EMPTY_CLASS_ARRAY}}")
    private Class[] ignoredParameterTypes;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public License getLicense() {
        return license;
    }

    public void setLicense(License license) {
        this.license = license;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Class[] getIgnoredParameterTypes() {
        return ignoredParameterTypes;
    }

    public void setIgnoredParameterTypes(Class[] ignoredParameterTypes) {
        this.ignoredParameterTypes = ignoredParameterTypes;
    }
}
