package cn.zenghr.travel.tripcore.repository.redis.impl;

import cn.zenghr.travel.tripcore.constant.RedisKeyEnum;
import cn.zenghr.travel.tripcore.constant.UserConstants;
import cn.zenghr.travel.tripcore.domain.UserInfo;
import cn.zenghr.travel.tripcore.repository.redis.IUserRepository;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Repository
public class UserRepositoryImpl implements IUserRepository {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public String hasCode(String phone) {
        return redisTemplate.opsForValue().get(RedisKeyEnum.REGISTER_CODE_KEY.join(phone));
    }

    /**
     * 缓存验证码
     *
     * @param phone 手机号
     * @param code  验证码
     */
    @Override
    public void cacheCode(String phone, String code) {
        redisTemplate.opsForValue().set(RedisKeyEnum.REGISTER_CODE_KEY.join(phone), code, RedisKeyEnum.REGISTER_CODE_KEY.getTime(), TimeUnit.SECONDS);
    }

    /**
     * 判断 key 是否
     *
     * @param phone 手机号
     * @return 过期时间
     */
    @Override
    public Long isExpireCode(String phone) {
        return redisTemplate.opsForValue().getOperations().getExpire(RedisKeyEnum.REGISTER_CODE_KEY.join(phone));
    }

    /**
     * 缓存登录用户的数据
     *
     * @param userInfo 登录用户
     * @return 缓存 key
     */
    @Override
    public String setToken(UserInfo userInfo) {
        // json 化
        String jsonString = JSONObject.toJSONString(userInfo);
        // 生成 key
        String uuid = UUID.randomUUID().toString().replace("_", "");
        String tokenKey = RedisKeyEnum.LOGIN_TOKEN_KEY.join(uuid);
        // 缓存
        redisTemplate.opsForValue().set(tokenKey, jsonString, UserConstants.LOGIN_TOKEN_EXPIRE, TimeUnit.SECONDS);
        return tokenKey;
    }
}
