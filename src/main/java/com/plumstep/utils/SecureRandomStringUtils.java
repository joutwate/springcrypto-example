package com.plumstep.utils;


import org.apache.commons.lang3.RandomStringUtils;

import java.security.SecureRandom;

/**
 * Utility class based on RandomStringUtils that generates random ascii strings using {@code SecureRandom}.
 */
public class SecureRandomStringUtils {
    private static SecureRandom secureRandom = new SecureRandom();
    private static char[] characters = new char[]{' ', '!', '"', '#', '$', '%', '&', '\'', '(', ')', '*', '+',
            ',', '-', '.', '/', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ':', ';', '<', '=', '>', '?',
            '@', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
            'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '[', '\\', ']', '^', '_', '`', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
            'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '{',
            '|', '}', '~'};

    public static String randomAscii(int count) {
        return RandomStringUtils.random(count, 0, characters.length, false, false, characters, secureRandom);
    }
}
