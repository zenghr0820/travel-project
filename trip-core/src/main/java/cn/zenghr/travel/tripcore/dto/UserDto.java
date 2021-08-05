package cn.zenghr.travel.tripcore.dto;

import cn.zenghr.travel.tripcore.validates.IsPhone;
import cn.zenghr.travel.tripcore.validates.PasswordEqual;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@PasswordEqual
public class UserDto {
    @IsPhone
    private String phone;
    @NotEmpty(message = "昵称不能为空")
    private String nickname;
    @NotEmpty(message = "密码不能为空")
    private String password;
    @NotEmpty(message = "确认密码不能为空")
    private String rpassword;
    @NotEmpty(message = "验证码不能为空")
    private String verifyCode;
}
