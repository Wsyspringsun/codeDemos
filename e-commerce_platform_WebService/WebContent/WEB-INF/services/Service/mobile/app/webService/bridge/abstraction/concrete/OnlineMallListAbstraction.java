package mobile.app.webService.bridge.abstraction.concrete;

import java.util.Date;
import java.util.List;
import java.util.Map;

import ljt.ds.bean.ReturnInfo;
import ljt.ds.util.ConvUtil;
import ljt.ds.util.JsonUtils;
import mobile.app.webService.bridge.abstraction.AbstractAbstraction;

import com.ljt.ds.common.util.ConvUtils;
import com.ljt.ds.common.util.DateUtils;
import com.ljt.ds.dto.OnlineCommodityDto;
/**
 * 商品列表获取
 * 
 * @author 李健健
 *
 */
public class OnlineMallListAbstraction extends AbstractAbstraction {

	@SuppressWarnings("unchecked")
	@Override
	public Object execute(Map<String, Object> parameters) {
		ReturnInfo returnInfo = new ReturnInfo();

		OnlineCommodityDto dto = new OnlineCommodityDto();
		String maxCount = ConvUtils.convToString(parameters.get("maxCount"));
		// 每页起始位置
		int firstIdxNum = ConvUtil.convToInt(parameters.get("firstIdx"));
		int maxCountNum = ConvUtil.convToInt(parameters.get("maxCount"));
		String commodityTypeId = ConvUtils.convToString(parameters.get("commodityTypeId"));
		String sort = ConvUtils.convToString(parameters.get("sort"));
		dto.setPageIndex(firstIdxNum * maxCountNum);
		dto.setPageSize(maxCountNum);
		dto.setCommodityTypeId(commodityTypeId);
		dto.setSort(sort);
		List<Map<String, Object>> lisDate = (List<Map<String, Object>>) super.getFaced().executeMethod("getCommodityListMobile", dto);
		int rowCount = (int) super.getFaced().executeMethod("getCommodityCnt", dto);
		for(Map<String, Object> row : lisDate) {
			//商品ID
			row.put("COMMODITY_ID", ConvUtils.convToString(row.get("COMMODITY_ID")));
			//商品类型ID
			row.put("COMMODITY_TYPE_ID", ConvUtils.convToString(row.get("COMMODITY_TYPE_ID")));
			//商品名
			row.put("TITLE", ConvUtils.convToString(row.get("TITLE")));
			//商品价格
			row.put("COST_MONEY", ConvUtils.convToString(row.get("COST_MONEY")));
			//封面图片
			row.put("IMG_PATH", ConvUtils.convToString(row.get("IMG_PATH")));
			// 日期
			row.put("INS_DATE", DateUtils.convertDate2String((Date)row.get("INS_DATE"), "yyyy-MM-dd HH:mm:ss"));
		}

		long pageCount = getPageCount(rowCount, maxCount);
		if (pageCount < (firstIdxNum + 1)) {
			returnInfo.setResult("[]");
			returnInfo.setStatus(true);
			returnInfo.setMessage("没有数据了");
		} else {
			returnInfo.setResult(JsonUtils.mapListToJson(lisDate));
			returnInfo.setStatus(true);
			returnInfo.setMessage("商品信息获取成功");
		}
		return returnInfo;
	}

	// 获得总页数
		private final long getPageCount(long rowCount, String maxCount) {
			long itemsPerPage = Long.valueOf(maxCount);
			long count = rowCount / itemsPerPage;

			if (itemsPerPage * count < rowCount) {
				count++;
			}

			return count;

		}

}
