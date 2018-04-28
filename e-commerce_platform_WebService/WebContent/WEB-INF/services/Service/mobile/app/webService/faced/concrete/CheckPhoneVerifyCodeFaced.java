package mobile.app.webService.faced.concrete;

import mobile.app.webService.faced.AbstractFaced;

import com.ljt.ds.common.sms.dto.SendSmsDto;
import com.ljt.ds.common.sms.service.ISmsService;
import com.ljt.ds.common.util.ConvUtils;
import com.ljt.ds.service.IUserManageService;

public class CheckPhoneVerifyCodeFaced extends AbstractFaced {

	private ISmsService smsService;
	private IUserManageService userManageService;

	public boolean checkVerifyCode(SendSmsDto arg0, String arg1){
		return smsService.checkVerifyCode(arg0, ConvUtils.convToInt(arg1));
	}

	public boolean checkMobileExists(String mobile) {
		return userManageService.checkMobileExists(mobile);
	}

	@Override
	public void loadAttributes() {
		smsService = (ISmsService) super.getSpringBean("smsService");
		userManageService = (IUserManageService) super.getSpringBean("userManageService");
	}

}
