package cn.zenghr.travel.tripcore.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * 
 * 
 * @author zenghr
 * @email 2760834764@qq.com
 * @date 2021-08-04 15:35:56
 */
@Data
@TableName("userinfo")
public class UserInfo {

	public static final int GENDER_SECRET = 0; //保密
	public static final int GENDER_MALE = 1;   //男
	public static final int GENDER_FEMALE = 2;  //女
	public static final int STATE_NORMAL = 0;  //正常
	public static final int STATE_DISABLE = 1;  //冻结

	@TableId
	private Long id;
	private String nickname;
	private String phone;
	private String email;
	@JsonIgnore
	private String password;
	private Integer gender;
	private Integer level;
	private String city;
	private String headImgUrl;
	private String info;
	private Integer state = STATE_NORMAL;

}
