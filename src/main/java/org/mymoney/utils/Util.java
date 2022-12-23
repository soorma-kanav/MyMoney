package org.mymoney.utils;

import java.util.Optional;

public class Util {
    public static float roundOffFloat(float input) {
        return (float) (Math.round(input * 100.00) / 100.00);
    }

    public static String removeLastCharacter(String str) {
        String result = Optional.ofNullable(str)
                .filter(sStr -> sStr.length() != 0)
                .map(sStr -> sStr.substring(0, sStr.length() - 1))
                .orElse(str);
        return result;
    }
}
