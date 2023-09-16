package ru.ntdv.proicis.integrations.contract;

import jdk.jshell.spi.ExecutionControl;

import java.net.URL;

public interface Url {
    URL getLink();
    String getLinkAsString();
    static URL getUrlFromString(final String stringUrl) throws IllegalArgumentException, ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("getUrlFromString must be implemented.");
    }
}
