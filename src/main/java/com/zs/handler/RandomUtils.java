package com.zs.handler;

import com.zs.pojo.BlogOutline;

import java.util.List;
import java.util.Random;

/**
 * @Created by zs on 2022/2/23.
 */
public class RandomUtils {

    private static final String BASE_NUM = "1234567890";
    private static Random random = new Random();

    /**
     * 随机生成数字验证码
     * @return
     */
    public static String generateRandomNum() {
        StringBuilder sb = new StringBuilder();
        for (int v = 1; v < 5;  v++) {
            int index = random.nextInt(BASE_NUM.length());
            sb.append(BASE_NUM.charAt(index));
        }
        return sb.toString();
    }

    /**
     * 随机选取一个博客概要对象
     * @param list
     * @return
     */
    public static BlogOutline generateBlogOutline(List<BlogOutline> list) {
        if (list.size() <= 0) {
            throw new UniversalException("未查询到推荐文章");
        }
        return list.get(random.nextInt(list.size()));
    }

}
