package ljt.ds.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import ljt.ds.service.ServiceInterFace;
import mobile.app.webService.creater.AbstractCreater;
import mobile.app.webService.creater.concrete.CheckPhoneVerifyCodeCreater;
import mobile.app.webService.creater.concrete.CheckTokenCreater;
import mobile.app.webService.creater.concrete.GetRegMobileCodeCreater;
import mobile.app.webService.creater.concrete.LoginCreater;
import mobile.app.webService.creater.concrete.OnlineMallListCreater;
import mobile.app.webService.creater.concrete.RegistCreater;

import org.apache.wicket.ajax.json.JSONException;
import org.apache.wicket.ajax.json.JSONObject;

import com.ljt.ds.common.util.ConvUtils;
import com.ljt.ds.framework.config.BankCodeResource;
import com.ljt.ds.framework.config.FWConfig;
import com.ljt.ds.framework.config.MessageResource;
import com.ljt.ds.framework.config.SmsTemplateResource;

public class Service implements ServiceInterFace {
	public Service() {
		ResourceBundle config = ResourceBundle.getBundle("config");
		FWConfig.init(config.getString("config"));
		SmsTemplateResource.init(config.getString("smsPath"));
		MessageResource.init(config.getString("messagePath"));
		BankCodeResource.init(config.getString("bankCodePath"));
	}

	private Map<String, Object> checkToken(String token) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			AbstractCreater creater = new CheckTokenCreater();
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("token", token);
			String retjson = String.valueOf(AbstractCreater.getAbstraction(
					creater).execute(parameters));
			JSONObject obj = new JSONObject(retjson);
			String retStr = ConvUtils.convToString(obj.get("result"));
			String[] arr = retStr.split("&");
			if(arr.length == 2) {
				retMap.put("OID_USER_ID", arr[0]);
				retMap.put("TERMINAL_TYPE", arr[1]);
			}
			retMap.put("JSON_STR", retjson);
		} catch (JSONException e) {
			return retMap;
		} catch (Exception ex) {
			ex.printStackTrace();
			return retMap;
		}
		return retMap;
	}

	@Override
	public String login(String userName ,String passWord, String terminalType) {
		AbstractCreater creater = new LoginCreater();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("userName", userName);
		parameters.put("password", passWord);
		parameters.put("terminalType", terminalType);
		return String.valueOf(AbstractCreater.getAbstraction(creater).execute(
				parameters));
	}
	
	@Override
	public String regist(String phoneNumber, String verifyCode, String passWord, String introducer, String terminalType) {
		AbstractCreater creater = new RegistCreater();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("userName", phoneNumber);
		parameters.put("verifyCode", verifyCode);
		parameters.put("password", passWord);
		parameters.put("inviteId", introducer);
		parameters.put("terminalType", terminalType);
		return String.valueOf(AbstractCreater.getAbstraction(creater).execute(
				parameters));
	}

	@Override
	public String getRegMobileCode(String mobile) {
		AbstractCreater creater = new GetRegMobileCodeCreater();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("sendType", "0");
		parameters.put("mobile", mobile);
		return String.valueOf(AbstractCreater.getAbstraction(creater).execute(
				parameters));
	}

	@Override
	public String getOnlineMallList(String firstIdx, String maxCount, String commodityTypeId, String sort) {
		AbstractCreater creater = new OnlineMallListCreater();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("firstIdx", firstIdx);
		parameters.put("maxCount", maxCount);
		parameters.put("commodityTypeId", commodityTypeId);
		parameters.put("sort", sort);
		return String.valueOf(AbstractCreater.getAbstraction(creater).execute(parameters));
	}

	@Override
	public String checkPhoneVerifyCode(String mobile, String operateType, String verifyCode) {
		AbstractCreater creater = new CheckPhoneVerifyCodeCreater();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("mobile", mobile);
		parameters.put("operateType", operateType);
		parameters.put("verifyCode", verifyCode);
		return String.valueOf(AbstractCreater.getAbstraction(creater).execute(parameters));
	}
}
