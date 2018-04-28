package mobile.app.webService.bridge.abstraction.concrete;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import ljt.ds.bean.ReturnInfo;
import ljt.ds.model.Model;
import ljt.ds.util.IpUtils;
import ljt.ds.util.JsonUtils;
import mobile.app.webService.bridge.abstraction.AbstractAbstraction;

import org.apache.axis2.context.MessageContext;
import org.apache.axis2.transport.http.HTTPConstants;

import com.ljt.ds.common.util.ConvUtils;
import com.ljt.ds.common.util.MessageUtils;
import com.ljt.ds.common.util.StringUtils;
import com.ljt.ds.dto.UserDto;
import com.ljt.ds.framework.config.FWConfig;
import com.ljt.ds.framework.config.FWConfigKey;
import com.ljt.ds.framework.util.DesEncryptUtil;
import com.ljt.ds.framework.util.EncryptUtil;
import com.ljt.ds.framework.util.FWStringUtil;

public class LoginAbstraction extends AbstractAbstraction {

	@Override
	public Object execute(Map<String, Object> parameters) {

		ReturnInfo result = new ReturnInfo();
		// 终端类型 0android，1IOS
		String terminalType = (String) parameters.get("terminalType");
		String userMobile = (String) parameters.get("userName");
		if (!StringUtils.isMobileNum(userMobile)) {
			result.setResult("0");
			result.setStatus(false);
			result.setMessage(MessageUtils.getMessage("ERR0001", "正确的手机号"));
			return result;
		}

		UserDto userDto = (UserDto) super.getFaced().executeMethod("auth", (String) parameters.get("userName"), EncryptUtil.encryptStr((String) parameters.get("password")));
		Model<String, Object> model = new Model<String, Object>();
		if (userDto.getErrorCode() == 0) {
			
			// 设置请求ip
			MessageContext mc = MessageContext.getCurrentMessageContext();
			HttpServletRequest request = (HttpServletRequest) mc.getProperty(HTTPConstants.MC_HTTP_SERVLETREQUEST);

			if (userDto.getOidUserId() != null){
				//登录设备session id保存
				super.getFaced().executeMethod("saveSessionId", userDto.getOidUserId(),userDto.getOidUserId(),IpUtils.getIpAddrByRequest(request));
			}

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date sysDate = new Date();
			String date = dateFormat.format(sysDate);

			String token = new DesEncryptUtil().encrypt(userDto.getOidUserId() + "&" + EncryptUtil.encryptStr((String) parameters.get("password")) + "&" + date + "&" + terminalType);
			
			Map<String, Object> retMap = (Map<String, Object>) super.getFaced().executeMethod("getToken", userDto);
			String oldToken = ConvUtils.convToString(retMap.get("TOKEN"));
			if (StringUtils.isEmpty(oldToken)) {
				// USER_TOKEN表插入用户数据
				userDto.setUserPwd(EncryptUtil.encryptMD5((String) parameters.get("password")));
				super.getFaced().executeMethod("insertToken", userDto);
			}
			
			super.getFaced().executeMethod("updateToken", userDto.getOidUserId(), token, date);
			model.addAttribute("USER_TOKEN", token); // token
			model.addAttribute("USER_ID", userDto.getOidUserId()); // 用户ID
			result.setResult(JsonUtils.objectToJson(token));
			result.setStatus(true);
			result.setMessage("登录成功");
		} else if (userDto.getErrorCode() == 1) {
			result.setResult("0");
			result.setStatus(false);
			result.setMessage(MessageUtils.getMessage("ERR0003"));
		} else if (userDto.getErrorCode() == 2) {
			int n = FWStringUtil.toInt(FWConfig.getInstance().getString(FWConfigKey.LOGIN_ERROR_TIME), 3);
			result.setResult("0");
			result.setStatus(false);
			result.setMessage(MessageUtils.getMessage("ERR0004", n));
		} else if (userDto.getErrorCode() == 3) {
			result.setResult("0");
			result.setStatus(false);
			result.setMessage(MessageUtils.getMessage("ERR0005"));
		} else if (userDto.getErrorCode() == 4) {
			int n = FWStringUtil.toInt(FWConfig.getInstance().getString(FWConfigKey.LOGIN_ERROR_TIMES), 5) - FWStringUtil.toInt(userDto.getLoginErrorTimes(), 0);
			result.setResult("0");
			result.setStatus(false);
			result.setMessage(MessageUtils.getMessage("ERR0032", n));
		}

		return result;
	}

	private DesEncryptUtil DesEncryptUtil() {
		// TODO Auto-generated method stub
		return null;
	}
}
