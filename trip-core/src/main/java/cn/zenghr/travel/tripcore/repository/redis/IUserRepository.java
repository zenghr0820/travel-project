package cn.zenghr.travel.tripcore.repository.redis;

import cn.zenghr.travel.tripcore.domain.UserInfo;

public interface IUserRepository {
    /**
     * 判断手机号的验证码是否存在
     * @param phone 手机号码
     */
    String hasCode(String phone);

    /**
     * 缓存验证码
     * @param phone 手机号
     * @param code 验证码
     */
    void cacheCode(String phone, String code);

    /**
     * 判断 key 是否
     * @param phone 手机号
     * @return 过期时间
     */
    Long isExpireCode(String phone);

    /**
     * 缓存登录用户的数据
     * @param userInfo 登录用户
     * @return 缓存 key
     */
    String setToken(UserInfo userInfo);
}
