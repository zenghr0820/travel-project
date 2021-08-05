package cn.zenghr.travel.tripcore.constant;

import lombok.Getter;

@Getter
public enum RedisKeyEnum {
    /**
     * 注册短信验证码key 实例
     */
    REGISTER_CODE_KEY("register_code_key", UserConstants.VERIFY_CODE_EXPIRE),

    /**
     * 登录 token 实例
     */
    LOGIN_TOKEN_KEY("login_token_key", UserConstants.LOGIN_TOKEN_EXPIRE);

    // key的前缀
    private String prefix;
    // key有效时间， 单位是s
    private Long time;
    // 构造器强制私有
    RedisKeyEnum(String prefix, Long time) {
        this.prefix = prefix;
        this.time = time;
    }

    //拼接出完整redis的key
    public String join(String... values){
        StringBuilder sb = new StringBuilder(80);
        sb.append(this.prefix);
        for (String value : values) {
            sb.append(":").append(value);
        }
        return sb.toString();
    }

}
