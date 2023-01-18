package com.testslotegrator.utils;

import com.github.javafaker.Faker;

public final class RandomUtils {

    private static Faker faker;

    private RandomUtils() {
    }

    public static Faker getFakerInstance() {
        if (faker == null) {
            faker = new Faker();
        }
        return faker;
    }

}
