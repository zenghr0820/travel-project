package cn.zenghr.travel.tripcore.utils;

import java.util.UUID;

public abstract class CommonUtil {
    public static String randomUUID(int num) {
        return UUID.randomUUID().toString().replace("_", "").substring(0, num);
    }
}
