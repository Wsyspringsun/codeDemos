package mobile.app.webService.bridge.abstraction.concrete;

import java.util.Map;

import ljt.ds.bean.ReturnInfo;
import mobile.app.webService.bridge.abstraction.AbstractAbstraction;

import com.ljt.ds.common.sms.dto.SendSmsDto;
import com.ljt.ds.common.sms.emum.SmsContentType;
import com.ljt.ds.common.sms.emum.SmsOperateTypeEnum;
import com.ljt.ds.common.sms.emum.SmsType;
import com.ljt.ds.common.util.StringUtils;

public class GetRegMobileCodeAbstraction extends AbstractAbstraction {

	@Override
	public Object execute(Map<String, Object> parameters) {
		ReturnInfo result = new ReturnInfo();
		// sendType 0发送文本验证码，1发送语音验证码(找回密码)
		String sendType = (String) parameters.get("sendType");
		String mobile = (String) parameters.get("mobile");

		// 检查注册手机号
		if (!StringUtils.isMobileNum(mobile)) {
			result.setResult("0");
			result.setStatus(false);
			result.setMessage("请输入正确的手机号");
			return result;
		}
		boolean existFlag = (boolean) super.getFaced().executeMethod("checkMobileExists", mobile);
		if("0".equals(sendType)) {
			if (existFlag) {
				result.setResult("0");
				result.setStatus(false);
				result.setMessage("手机号" + mobile + "已经存在");
				return result;
			}
		} else if("1".equals(sendType)) {
			if (!existFlag) {
				result.setResult("0");
				result.setStatus(false);
				result.setMessage("手机号" + mobile + "不存在");
				return result;
			}
		}

		SendSmsDto sendSmsDto = new SendSmsDto();
		sendSmsDto.setUserId("");
		sendSmsDto.setSmsContentType(SmsContentType.IDENTITY);
		if("0".equals(sendType)) {
			sendSmsDto.setSmsType(SmsType.TEXT);
		} else if("1".equals(sendType)) {
			sendSmsDto.setSmsType(SmsType.VOICE);
		}
		sendSmsDto.setPhone(mobile);
		sendSmsDto.setOperateType(SmsOperateTypeEnum.REGIST.getValue());

		boolean isSend = (boolean) super.getFaced().executeMethod("sendSms", sendSmsDto);
		if (isSend) {
			// this.verifyCode = sendSmsDto.getVerifyCode();
			result.setResult("1");
			result.setStatus(true);
			result.setMessage("手机验证码发送成功");
		} else {
			// this.verifyCode = "";
			result.setResult("0");
			result.setStatus(false);
			result.setMessage("手机验证码发送失败，请联系客服。");
		}

		return result;
	}
}
