package ljt.ds.service;


public interface ServiceInterFace {


    /**
     * 注册
     * @param token
     * @param userName
     * @param email
     * @param password
     * @param mobile
     * @return
     */
    public String regist(String phoneNumber, String verifyCode, String passWord, String introducer, String terminalType);

    /**
     * 获取注册手机验证码
     * @param token
     * @param mobile
     * @return
     */
    public String getRegMobileCode(String mobile);

	/**
	 *  验证手机验证码
	 * @param mobile
	 * @param operateType
	 * @param verifyCode
	 * @return
	 */
	public String checkPhoneVerifyCode(String mobile, String operateType, String verifyCode);


	/**
	 * 登录
	 * @param userName
	 * @param passWord
	 * @param terminalType
	 * @return
	 */
	public String login(String userName, String passWord, String terminalType);

	/**
	 * 获取商品列表及信息
	 * @param firstIdx
	 * @param maxCount
	 * @param commodityTypeId 商品类型id
	 * @param sort 排序方式：1、按销量排序；2、按价格排序
	 * @return
	 */
	public String getOnlineMallList(String firstIdx, String maxCount, String commodityTypeId, String sort);

}
