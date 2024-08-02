package com.bankeasy.bankeasy.services;

import java.util.Random;

public class AccountUtils {

    public static String generateRandomAccountNumber() {
        Random random = new Random();
        int number = 1000000000 + random.nextInt(900000000);
        return Integer.toString(number);
    }
}

