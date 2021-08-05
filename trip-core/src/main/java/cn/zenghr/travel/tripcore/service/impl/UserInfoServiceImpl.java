package cn.zenghr.travel.tripcore.service.impl;

import cn.zenghr.travel.tripcore.common.UserInfoException;
import cn.zenghr.travel.tripcore.domain.UserInfo;
import cn.zenghr.travel.tripcore.dto.UserDto;
import cn.zenghr.travel.tripcore.mapper.UserInfoMapper;
import cn.zenghr.travel.tripcore.repository.redis.IUserRepository;
import cn.zenghr.travel.tripcore.service.IUserInfoService;
import cn.zenghr.travel.tripcore.utils.BeanUtils;
import cn.zenghr.travel.tripcore.utils.CommonUtil;
import cn.zenghr.travel.tripcore.utils.RedisUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {

    @Autowired
    private IUserRepository userRepository;

    public UserInfo findByPhone(String phone) {
        // 检查手机号码是否存在
        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserInfo::getPhone, phone);
        return this.getOne(queryWrapper);
    }

    @Override
    public void checkPhone(String phone) {
        // 检查手机号码是否存在
        UserInfo userInfo = this.findByPhone(phone);
        if (userInfo != null) {
            throw new UserInfoException("手机账号已注册!");
        }
    }

    @Override
    public void sendVerifyCode(String phone) {
        // 先校验该手机号码是否被注册
        this.checkPhone(phone);

        // 检查该手机号码是否发起过验证码
        Long expire = RedisUtil.diffExpireTime(userRepository.isExpireCode(phone));

        if (expire > 0) {
            throw new UserInfoException("验证码已发送，请勿重复发送，请 " + expire + "秒后在试!");
        }

        // 生成随机验证码
        String code = CommonUtil.randomUUID(4);

        // 缓存验证码
        userRepository.cacheCode(phone, code);
    }

    @Override
    public UserInfo register(UserDto registerMsg) {
        // 先校验该手机号码是否被注册
        this.checkPhone(registerMsg.getPhone());

        // 先校验 code
        boolean flag = this.verifyCode(registerMsg.getPhone(), registerMsg.getVerifyCode());
        if (!flag) throw new UserInfoException("验证码错误!");

        // todo 数据校验是否放到 controller
        if (!registerMsg.getPassword().equals(registerMsg.getRpassword()))  throw new UserInfoException("两次密码不一致!");

        // 保存数据
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyBeanProp(userInfo, registerMsg);
        log.info("userInfo => {}", userInfo);
        userInfo.setHeadImgUrl("/images/default.jpg");
        userInfo.setState(UserInfo.STATE_NORMAL);
        userInfo.setGender(UserInfo.GENDER_SECRET);

        this.save(userInfo);
        return userInfo;
    }

    @Override
    public boolean verifyCode(String phone, String code) {
        String oldCode = userRepository.hasCode(phone);
        return code.equals(oldCode);
    }

    @Override
    public Map<String, Object> login(String username, String password) {
        Map<String, Object> result = new HashMap<>();
        // 先校验账号是否存在
        UserInfo userInfo = this.findByPhone(username);
        if (userInfo == null) {
            throw new UserInfoException("账号不存在!");
        } else if (!userInfo.getPassword().equals(password)) {
            throw new UserInfoException("密码不正确!");
        }

        // 登陆成功
        result.put("user", userInfo);

        // 缓存在 redis，返回 token 信息
        String token = userRepository.setToken(userInfo);
        result.put("token", token);

        return result;
    }
}
