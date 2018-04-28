package mobile.app.webService.bridge.abstraction.concrete;

import java.util.Map;

import ljt.ds.bean.ReturnInfo;
import mobile.app.webService.bridge.abstraction.AbstractAbstraction;

import com.ljt.ds.common.sms.dto.SendSmsDto;
import com.ljt.ds.common.sms.emum.SmsOperateTypeEnum;
import com.ljt.ds.common.util.ConvUtils;
import com.ljt.ds.common.util.MessageUtils;
import com.ljt.ds.common.util.StringUtils;
import com.ljt.ds.framework.config.FWConfig;
import com.ljt.ds.framework.config.FWConfigKey;

public class CheckPhoneVerifyCodeAbstraction extends AbstractAbstraction {

	@Override
	public Object execute(Map<String, Object> parameters) {
		ReturnInfo returnInfo = new ReturnInfo();
		String type = "";
		String operateType = (String) parameters.get("operateType");
		String mobile = (String) parameters.get("mobile");
		String verifyCode = (String) parameters.get("verifyCode");
		// 判断手机号是否正确和存在
		if (!StringUtils.isMobileNum(mobile)) {
			returnInfo.setResult("0");
			returnInfo.setStatus(false);
			returnInfo.setMessage(MessageUtils.getMessage("ERR0001", "正确的手机号"));
			return returnInfo;
		}
		if("0".equals(operateType)) {
			// 修改手机号时
			type = SmsOperateTypeEnum.CHANGE_PHONE.getValue();
		} else if("1".equals(operateType)) {
			// 判断手机号是否存在
			boolean existFlag = (boolean) super.getFaced().executeMethod("checkMobileExists", mobile);
			if (!existFlag) {
				returnInfo.setResult("0");
				returnInfo.setStatus(false);
				returnInfo.setMessage("手机号码不存在");
				return returnInfo;
			}
			// 找回和修改密码时
			type = SmsOperateTypeEnum.SEARCH_PASSWORD.getValue();
		}
		// 验证短信验证码
		SendSmsDto sendSmsDto = new SendSmsDto();
		sendSmsDto.setPhone(mobile);
		sendSmsDto.setVerifyCode(verifyCode);
		int limit = 10;
		if (!StringUtils.isEmpty(FWConfig.getInstance().getString(FWConfigKey.SMS_VERIFY_CODE_LIMIT))) {
			limit = Integer.parseInt(FWConfig.getInstance().getString(FWConfigKey.SMS_VERIFY_CODE_LIMIT));
		}
		sendSmsDto.setOperateType(type);
		boolean checkRet = (boolean) super.getFaced().executeMethod("checkVerifyCode",sendSmsDto, ConvUtils.convToString(limit));
		if (!checkRet) {
			returnInfo.setResult("0");
			returnInfo.setStatus(false);
			returnInfo.setMessage("短信验证码输入有误或超过时限");
		} else {
			returnInfo.setResult("1");
			returnInfo.setStatus(true);
			returnInfo.setMessage("验证成功");
		}
		return returnInfo;
	}


}
