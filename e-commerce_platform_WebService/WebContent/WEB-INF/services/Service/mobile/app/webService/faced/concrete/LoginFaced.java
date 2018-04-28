package mobile.app.webService.faced.concrete;

import java.util.Map;

import com.ljt.ds.dao.IUserDao;
import com.ljt.ds.dto.UserDto;
import com.ljt.ds.service.ILoginService;

import mobile.app.webService.faced.AbstractFaced;

public class LoginFaced extends AbstractFaced {

	private ILoginService loginService = null;
	private IUserDao userDao;

	public UserDto auth(String loginId, String password) {
		return loginService.auth(loginId, password);
	}

	public int saveSessionId(String arg0, String arg1, String arg2) {
		return loginService.saveSessionId(arg0, arg1,arg2);
	}

	public String getLoginSessionId(String arg0) {
		return loginService.getLoginSessionId(arg0);
	}

	public int updateToken(String oid, String token, String startDate) {
		return userDao.updateToken(oid, token, startDate);
	}

	public int insertToken(UserDto userDto) {
		return userDao.insertToken(userDto);
	}

	public Map<String, Object> getToken(UserDto userDto) {
		return userDao.getToken(userDto);
	}

	@Override
	public void loadAttributes() {
		loginService = (ILoginService) super.getSpringBean("loginService");
		userDao = (IUserDao) super.getSpringBean("userDao");

	}
}
