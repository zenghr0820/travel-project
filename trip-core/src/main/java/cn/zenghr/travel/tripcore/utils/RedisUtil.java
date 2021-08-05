package cn.zenghr.travel.tripcore.utils;

import cn.zenghr.travel.tripcore.constant.UserConstants;

public abstract class RedisUtil {
    /**
     * 计算过期时间
     *
     * @param expire 时间
     * @return 返回差值
     */
    public static Long diffExpireTime(Long expire) {
        if (expire > 0 && UserConstants.VERIFY_CODE_EXPIRE - expire < 60) {
            return UserConstants.SEND_CODE_INTERVAL - (UserConstants.VERIFY_CODE_EXPIRE - expire);
        }
        return 0L;
    }
}
