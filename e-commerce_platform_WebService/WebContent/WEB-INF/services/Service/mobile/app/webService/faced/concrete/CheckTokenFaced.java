package mobile.app.webService.faced.concrete;

import java.util.Map;

import com.ljt.ds.dao.ILoginDao;
import com.ljt.ds.dao.IUserDao;
import com.ljt.ds.dto.UserDto;
import com.ljt.ds.service.ILoginService;

import mobile.app.webService.faced.AbstractFaced;

public class CheckTokenFaced extends AbstractFaced {

	private IUserDao userDao;

	public Map<String, Object> getToken(UserDto userDto) {
		return userDao.getToken(userDto);
	}

	public Map<String, Object> getUserDatail(UserDto userDto) {
		return userDao.getUserDatail(userDto);
	}

	@Override
	public void loadAttributes() {
		userDao = (IUserDao) super.getSpringBean("userDao");
	}
}
