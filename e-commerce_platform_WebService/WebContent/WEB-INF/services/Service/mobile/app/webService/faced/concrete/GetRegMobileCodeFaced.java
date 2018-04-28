package mobile.app.webService.faced.concrete;

import com.ljt.ds.common.sms.dto.SendSmsDto;
import com.ljt.ds.service.IRegistService;
import com.ljt.ds.service.IUserManageService;

import mobile.app.webService.faced.AbstractFaced;

public class GetRegMobileCodeFaced extends AbstractFaced {
	private IRegistService registService;
	private IUserManageService userManageService;

	public boolean sendSms(SendSmsDto sendSmsDto) {
		return registService.sendSms(sendSmsDto);
	}

	public boolean checkMobileExists(String mobile) {
		return userManageService.checkMobileExists(mobile);
	}

	@Override
	public void loadAttributes() {
		registService = (IRegistService) super.getSpringBean("registService");
		userManageService = (IUserManageService) super.getSpringBean("userManageService");
	}
}
