package cn.zenghr.travel.tripcore.validates;

import cn.zenghr.travel.tripcore.dto.UserDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordEqual, UserDto> {
    @Override
    public boolean isValid(UserDto userDto, ConstraintValidatorContext constraintValidatorContext) {

        if (userDto != null && userDto.getPassword() != null && userDto.getRpassword() != null) {
            String password1 = userDto.getPassword();
            String password2 = userDto.getRpassword();
            // 这里只是做示例用，所以简单实用了equals进行对比，实际使用可以根据业务要求做更多拓展
            return password1.equals(password2);
        }
        return false;
    }
}
