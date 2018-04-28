package mobile.app.webService.faced.concrete;

import java.util.List;
import java.util.Map;

import mobile.app.webService.faced.AbstractFaced;

import com.ljt.ds.service.IOnlineMallService;
import com.ljt.ds.dto.OnlineCommodityDto;

public class OnlineMallListFaced extends AbstractFaced {

	private IOnlineMallService onlineMallService;
	
	public List<Map<String, Object>> getCommodityList(OnlineCommodityDto arg0){
		return onlineMallService.getCommodityList(arg0);
	}
	public int getCommodityCnt(OnlineCommodityDto arg0){
		return onlineMallService.getCommodityCnt(arg0);
	}
	public List<Map<String, Object>> getCommodityListMobile(OnlineCommodityDto arg0){
		return onlineMallService.getCommodityListMobile(arg0);
	}
	
	@Override
	public void loadAttributes() {
		onlineMallService = (IOnlineMallService) super.getSpringBean("onlineMallService");
		
	}

}
