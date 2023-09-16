package ru.ntdv.proicis.buisness.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("storage")
public class StorageProperties {
    private String location = "uploads";

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}