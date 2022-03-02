package com.zs.handler;

import java.util.Random;

/**
 * @Created by zs on 2022/2/23.
 */
public class RandomUtils {

    private static final String BASE_NUM = "1234567890";
    private static Random random = new Random();

    public static String generateRandomNum() {
        StringBuilder sb = new StringBuilder();
        for (int v = 1; v < 5;  v++) {
            int index = random.nextInt(BASE_NUM.length());
            sb.append(BASE_NUM.charAt(index));
        }
        return sb.toString();
    }


}
