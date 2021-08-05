package cn.zenghr.travel.websiteapi.controller;

import cn.zenghr.travel.tripcore.common.R;
import cn.zenghr.travel.tripcore.domain.UserInfo;
import cn.zenghr.travel.tripcore.dto.UserDto;
import cn.zenghr.travel.tripcore.service.IUserInfoService;
import cn.zenghr.travel.tripcore.validates.IsPhone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UserInfoController {

    @Autowired
    private IUserInfoService userInfoService;

    @GetMapping("/checkPhone")
    public R checkPhone(@IsPhone String phone) {
        System.out.println("phone = " + phone);
        userInfoService.checkPhone(phone);
        return R.ok();
    }

    /**
     * 发送验证码
     * @return 响应
     */
    @GetMapping("/sendVerifyCode")
    public R sendVerifyCode(@IsPhone String phone) {
        userInfoService.sendVerifyCode(phone);
        return R.ok("发送成功");
    }

    @PostMapping("/regist")
    public R register(@Validated UserDto registerMsg) {
        UserInfo userInfo = userInfoService.register(registerMsg);
        System.out.println("userInfo = " + userInfo.getId());
        return R.ok("注册成功!");
    }

    @PostMapping("/login")
    public R login(String username, String password) {
        return R.ok(userInfoService.login(username, password));
    }
}
