package cn.zenghr.travel.tripcore.service;

import cn.zenghr.travel.tripcore.domain.UserInfo;
import cn.zenghr.travel.tripcore.dto.UserDto;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

public interface IUserInfoService extends IService<UserInfo> {
    void checkPhone(String phone);

    void sendVerifyCode(String phone);

    UserInfo register(UserDto registerMsg);

    boolean verifyCode(String phone, String code);

    Map<String, Object> login(String username, String password);
}
