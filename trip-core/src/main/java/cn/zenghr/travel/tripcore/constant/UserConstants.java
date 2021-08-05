package cn.zenghr.travel.tripcore.constant;

public class UserConstants {
    // 验证码过期时间
    public static final Long VERIFY_CODE_EXPIRE = 5L * 60;

    // 登录过期时间
    public static final Long LOGIN_TOKEN_EXPIRE = 30L * 60;

    // 发送 验证码的间隔频率
    public static final Long SEND_CODE_INTERVAL = 60L;
}
