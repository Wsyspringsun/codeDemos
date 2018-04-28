package mobile.app.webService.bridge.abstraction.concrete;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import ljt.ds.bean.ReturnInfo;
import ljt.ds.model.Model;
import ljt.ds.util.JsonUtils;
import mobile.app.webService.bridge.abstraction.AbstractAbstraction;

import com.ljt.ds.common.sms.dto.SendSmsDto;
import com.ljt.ds.common.sms.emum.SmsContentType;
import com.ljt.ds.common.sms.emum.SmsOperateTypeEnum;
import com.ljt.ds.common.sms.emum.SmsType;
import com.ljt.ds.common.util.ConvUtils;
import com.ljt.ds.common.util.MessageUtils;
import com.ljt.ds.common.util.StringUtils;
import com.ljt.ds.dto.RateCouponSendDetailDto;
import com.ljt.ds.dto.UserDto;
import com.ljt.ds.framework.config.FWConfig;
import com.ljt.ds.framework.config.FWConfigKey;
import com.ljt.ds.framework.config.SmsTemplateResource;
import com.ljt.ds.framework.util.DesEncryptUtil;
import com.ljt.ds.framework.util.EncryptUtil;

public class RegistAbstraction extends AbstractAbstraction {

	@Override
	public Object execute(Map<String, Object> parameters) {
		ReturnInfo result = new ReturnInfo();
		// 终端类型 0android，1IOS
		String terminalType = (String) parameters.get("terminalType");

		// 对输入内容进行CHECK
		String inputPhone = (String) parameters.get("userName");
		String inputPassWord = (String) parameters.get("password");
		String inputVerifyCode = (String) parameters.get("verifyCode");
		String inputInviteId = (String) parameters.get("inviteId");
		// 检查手机号
		result = checkPhoneInput(inputPhone);
		if (!result.isStatus()) {
			return result;
		}
		// 检查密码
		result = checkPasswordStrength(inputPassWord);
		if (!result.isStatus()) {
			return result;
		}

		//校验验证码
		result = checkVerifyCode(inputPhone, inputVerifyCode);
		if (!result.isStatus()) {
			return result;
		}

		UserDto userDto = new UserDto();
		userDto.setOidUserId(StringUtils.getUUID());
		userDto.setUserPwd(EncryptUtil.encryptStr(inputPassWord));
		if (StringUtils.isAsciiAlphaNumCharOnly(inputPassWord)) {
			userDto.setUserPwdStrength("1");
		} else {
			userDto.setUserPwdStrength("2");
		}
		userDto.setMobile(inputPhone);
		userDto.setEmail("");
		userDto.setUserType("0");
		userDto.setFinancingFlg("0");
		userDto.setInvestmentFlg("1");

		if(!StringUtils.isEmpty(inputInviteId)) {
			userDto.setIntroducerUserMobile(inputInviteId);
		}

		if (result.isStatus()) {
			if ((boolean) super.getFaced().executeMethod("registUser", userDto)) {
				// 保存token
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date sysDate = new Date();
				String date = dateFormat.format(sysDate);
				String token = new DesEncryptUtil().encrypt(userDto.getOidUserId() + "&" + EncryptUtil.encryptStr((String) parameters.get("password")) + "&" + date + "&" + terminalType);
				// 更新token
				super.getFaced().executeMethod("updateToken", userDto.getOidUserId(), token, date);
				Model<String, Object> model = new Model<String, Object>();
				model.addAttribute("USER_TOKEN", token); // token
				model.addAttribute("USER_ID", userDto.getOidUserId()); // 用户ID

				// 更改验证码为失效
				SendSmsDto sendSmsDto = new SendSmsDto();
				sendSmsDto.setPhone(inputPhone);
				sendSmsDto.setOperateType(SmsOperateTypeEnum.REGIST.getValue());
				super.getFaced().executeMethod("updateVerifyCodeInvalid", sendSmsDto);
				// 短信用Bean生成
				sendSmsDto = new SendSmsDto();
				sendSmsDto.setSmsContentType(SmsContentType.CUSTOM);
				String strMobile = StringUtils.encryptMobile(inputPhone);
				sendSmsDto.setContents(SmsTemplateResource.getInstance().getString("sms_regist_template", strMobile));
				sendSmsDto.setUserId(strMobile);
				sendSmsDto.setSmsType(SmsType.TEXT);
				sendSmsDto.setPhone(inputPhone);
				// 发送注册成功短信
				super.getFaced().executeMethod("sendSms", sendSmsDto);
				
				result.setResult(JsonUtils.objectToJson(token));
				result.setStatus(true);
				result.setMessage("注册成功。");
			} else {
				result.setResult("0");
				result.setStatus(false);
				result.setMessage("注册失败，请联系客服。");
			}
		}

		return result;
	}

	private ReturnInfo checkPhoneInput(String mobile) {
		ReturnInfo result = new ReturnInfo();
		if (StringUtils.isEmpty(mobile)) {
			result.setResult("0");
			result.setStatus(false);
			result.setMessage(MessageUtils.getMessage("ERR0001", "手机号"));
		} else if (!StringUtils.isMobileNum(mobile)) {
			result.setResult("0");
			result.setStatus(false);
			result.setMessage(MessageUtils.getMessage("ERR0001", "正确的手机号"));
		} else {
			boolean existFlag = (boolean) super.getFaced().executeMethod("checkMobileExists", mobile);
			if (existFlag) {
				result.setResult("0");
				result.setStatus(false);
				result.setMessage("手机号"+mobile+"已经存在");
			} else {
				result.setResult("1");
				result.setStatus(true);
				result.setMessage("");
			}
		}
		return result;
	}

	private ReturnInfo checkPasswordStrength(String password) {
		ReturnInfo result = new ReturnInfo();
		// 检查密码强度
		if(StringUtils.isEmpty(password)) {
			result.setResult("0");
			result.setStatus(false);
			result.setMessage(MessageUtils.getMessage("ERR0001", "密码"));
		} else if (!StringUtils.isPasswordStrength(password)) {
			result.setResult("0");
			result.setStatus(false);
			result.setMessage("密码的长度必须是8-20个字符。");
		} else {
			result.setResult("1");
			result.setStatus(true);
			result.setMessage("");
		}
		return result;
	}

	// 校验验证码
	private ReturnInfo checkVerifyCode(String userPhone, String verifyCode) {
		ReturnInfo result = new ReturnInfo();
		// 检查验证码
		if (StringUtils.isEmpty(verifyCode)) {
			result.setResult("0");
			result.setStatus(false);
			result.setMessage(MessageUtils.getMessage("ERR0001", "短信验证码"));
		} else {
			SendSmsDto sendSmsDto = new SendSmsDto();
			sendSmsDto.setPhone(userPhone);
			sendSmsDto.setVerifyCode(verifyCode);
			int limit = 10;
			if (!StringUtils.isEmpty(FWConfig.getInstance().getString(FWConfigKey.SMS_VERIFY_CODE_LIMIT))) {
				limit = Integer.parseInt(FWConfig.getInstance().getString(FWConfigKey.SMS_VERIFY_CODE_LIMIT));
			}
			sendSmsDto.setOperateType(SmsOperateTypeEnum.REGIST.getValue());
			boolean checkVC = (Boolean) super.getFaced().executeMethod("checkVerifyCode", sendSmsDto, ConvUtils.convToString(limit));
			if (!checkVC) {
				result.setResult("0");
				result.setStatus(false);
				result.setMessage(MessageUtils.getMessage("ERR0013", "短信验证码输入有误或超过时限"));
			} else {
				result.setResult("1");
				result.setStatus(true);
				result.setMessage("");
			}
		}
		return result;
	}

	// 推荐人
	private ReturnInfo checkIntroducer(String introducer) {
		ReturnInfo result = new ReturnInfo();
		if (StringUtils.isEmpty(introducer)) {
			result.setResult("1");
			result.setStatus(true);
			result.setMessage("");
		} else if (!StringUtils.isEmpty(introducer) && StringUtils.isMobileNum(introducer)) {
			boolean existFlag = (Boolean) super.getFaced().executeMethod("checkMobileExists", introducer);
			if (!existFlag) {
				result.setResult("0");
				result.setStatus(false);
				result.setMessage(MessageUtils.getMessage("ERR0021"));
			} else {
				result.setResult("1");
				result.setStatus(true);
				result.setMessage("");
			}
		} else {
			result.setResult("0");
			result.setStatus(false);
			result.setMessage(MessageUtils.getMessage("ERR0001", "正确的手机号"));
		}

		return result;
	}

}
