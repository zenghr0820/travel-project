package cn.zenghr.travel.tripcore.validates;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {PasswordValidator.class})
public @interface PasswordEqual {
    // 默认错误消息
    String message() default "两次密码不一致";

    // 分组
    Class<?>[] groups() default {};

    // 负载
    Class<? extends Payload>[] payload() default {};
}
