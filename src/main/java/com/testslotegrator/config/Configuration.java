package com.testslotegrator.config;

public class Configuration {

    public static String BASE_URL;
    public static String HTTP_BASIC_USERNAME;

    static {
        BASE_URL = System.getProperty("baseUri", "http://test-api.d6.dev.devcaz.com/");
        HTTP_BASIC_USERNAME = System.getProperty("basicUsername", "front_2d6b0a8391742f5d789d7d915755e09e");
    }

}
