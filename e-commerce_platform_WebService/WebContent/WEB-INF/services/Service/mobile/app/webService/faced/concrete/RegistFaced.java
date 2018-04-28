package mobile.app.webService.faced.concrete;

import java.math.BigDecimal;
import java.util.Map;

import com.ljt.ds.common.sms.dto.SendSmsDto;
import com.ljt.ds.common.sms.service.ISmsService;
import com.ljt.ds.common.util.ConvUtils;
import com.ljt.ds.dao.IUserDao;
import com.ljt.ds.dto.RateCouponSendDetailDto;
import com.ljt.ds.dto.UserDto;
import com.ljt.ds.service.IRegistService;
import com.ljt.ds.service.IUserManageService;

import mobile.app.webService.faced.AbstractFaced;

public class RegistFaced extends AbstractFaced {
	private IRegistService registService;
	private IUserManageService userManageService;
	private ISmsService smsService;
	private IUserDao userDao;

	public boolean registUser(UserDto userDto) {
		return registService.registUser(userDto);
	}

	public boolean checkMobileExists(String mobile) {
		return userManageService.checkMobileExists(mobile);
	}

	public boolean updateUser(UserDto userDto) {
		return userManageService.updateUser(userDto);
	}

	public int updateToken(String oid, String token, String startDate) {
		return userDao.updateToken(oid, token, startDate);
	}

	public Map<String, Object> getUserByMobile(String mobile) {
		return userManageService.getUserByMobile(mobile);
	}

	public boolean sendSms(SendSmsDto sendSmsDto) {
		return registService.sendSms(sendSmsDto);
	}

	public boolean updateVerifyCodeInvalid(SendSmsDto dto) {
		return smsService.updateVerifyCodeInvalid(dto);
	}

	public boolean checkVerifyCode(SendSmsDto dto, String limit) {
		return smsService.checkVerifyCode(dto, ConvUtils.convToInt(limit));
	}

	@Override
	public void loadAttributes() {
		registService = (IRegistService) super.getSpringBean("registService");
		userManageService = (IUserManageService) super.getSpringBean("userManageService");
		smsService = (ISmsService) super.getSpringBean("smsService");
		userDao = (IUserDao) super.getSpringBean("userDao");
	}
}
