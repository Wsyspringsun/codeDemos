package mobile.app.webService.bridge.abstraction.concrete;

import java.util.Date;
import java.util.Map;

import ljt.ds.bean.ReturnInfo;
import mobile.app.webService.bridge.abstraction.AbstractAbstraction;

import com.ljt.ds.common.util.ConvUtils;
import com.ljt.ds.common.util.DateUtils;
import com.ljt.ds.common.util.StringUtils;
import com.ljt.ds.dto.UserDto;
import com.ljt.ds.framework.util.DesEncryptUtil;

public class CheckTokenAbstraction extends AbstractAbstraction {

	@SuppressWarnings("unchecked")
	@Override
	public Object execute(Map<String, Object> parameters) {
		ReturnInfo result = new ReturnInfo();
		String token = (String) parameters.get("token");
		if (StringUtils.isEmpty(token)) {
			result.setResult("0");
			result.setStatus(false);
			result.setMessage("用户名密码过期，请重新登录。");
			return result;
		}
		String desToken = new DesEncryptUtil().decrypt(token);
		if (StringUtils.isEmpty(desToken)) {
			result.setResult("0");
			result.setStatus(false);
			result.setMessage("用户名密码过期，请重新登录。");
			return result;
		}
		String[] arr = desToken.split("&");
		if (arr.length == 4) {

			String desDate = arr[2];
			String oidUserId = arr[0];
			String desPassword = arr[1];
			String terminalType = arr[3];

			UserDto userDto = new UserDto();
			userDto.setOidUserId(oidUserId);
			Map<String, Object> retMap = (Map<String, Object>) super.getFaced().executeMethod("getToken", userDto);
			String oldToken = ConvUtils.convToString(retMap.get("TOKEN"));
			if (!oldToken.equals(token)) {
				result.setResult("0");
				result.setStatus(false);
				result.setMessage("用户名密码过期，请重新登录。");
				return result;
			}

			Date fDate = DateUtils.convertString2Date(desDate, "yyyy-MM-dd HH:mm:ss");
			Date oDate = DateUtils.getSystemDate();
			int count = DateUtils.dateDiff(fDate, oDate);
			if (count > 30) {
				result.setResult("0");
				result.setStatus(false);
				result.setMessage("用户名密码过期，请重新登录。");
				return result;
			}

			// 判断密码是否正确
			Map<String, Object> userDetialMap = (Map<String, Object>) super.getFaced().executeMethod("getUserDatail", userDto);
			String dbPassWord = ConvUtils.convToString(userDetialMap.get("USER_PWD"));
			if(!desPassword.equals(dbPassWord)) {
				result.setResult("0");
				result.setStatus(false);
				result.setMessage("用户名密码过期，请重新登录。");
				return result;
			}

			result.setResult(oidUserId + "&" + terminalType);
			result.setStatus(true);
			result.setMessage("Token校验成功");
			return result;
		} else {
			result.setResult("0");
			result.setStatus(false);
			result.setMessage("用户名密码过期，请重新登录。");
		}

		return result;
	}
}
