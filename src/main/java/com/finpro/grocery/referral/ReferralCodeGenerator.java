package com.finpro.grocery.referral;

import java.security.SecureRandom;

public class ReferralCodeGenerator {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int LENGTH = 8;

    public static String generateCode(){

        SecureRandom random = new SecureRandom();
        StringBuilder code = new StringBuilder();

        for(int i=0; i<LENGTH; i++){
            code.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }

        return code.toString();
    }
}
