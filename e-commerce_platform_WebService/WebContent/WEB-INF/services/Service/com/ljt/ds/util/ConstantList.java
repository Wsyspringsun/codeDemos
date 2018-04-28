package ljt.ds.util;


public final class ConstantList {


	public static final String EMAIL_NAME="网贷之家-管家";
    public static final String BORROW_ALL = "0";
    public static final String BORROW_PROGRESS = "1";
    public static final String BORROW_DETAIL = "2";
    public static final String BLACK_ALL = "0";
    public static final String BLACK_SITE = "1";
    public static final String BLACK_DETAIL = "2";
    public static final String BORROW_USER_ALL = "0";
    public static final String BORROW_USER_GROUP = "1";
    public static final String BORROW_USER_DETAIL = "2";
    public static final String ZERO = "0";

	public static final String EMPTY = "";

	public static final int PAGE_SIZE = 20;
	public static final int PAGE_SIZE2 = 20;
	public static final String ORDER_BY_DESC = "DESC";

	public static final String ORDER_BY_ASC = "ASC";
	//未审核
	public static final int STATUS_NOT_AUDIT = 0;
	//已审核
	public static final int STATUS_HAD_AUDIT = 1;
	//未发送
	public static final int LOWDOWN_NOT_SEND = 0;
	//已发送
	public static final int LOWDOWN_HAD_SEND = 1;
	//邮件
	public static final String EMAIL_CONFIG = "guanjia@wangdaizhijia.com";
	//论坛用户
	public static final String FORUM_USER = "FORUM_USER";
	//注册用户
	public static final String REG_USER = "REG_USER";
	//验证码
	public static final String GOOGLECODE = "GOOGLECODE";
	//随机数
	public static final String RANDOMCODE = "RANDOMCODE";
	//论坛
	public static final String WEBSITE = "http://www.wangdaizhijia.com";

	public static final String SUBJECT ="恭喜您成功注册网贷之家管家,请完成邮箱认证!";

	public static final String SESSION_HELPER_ADMIN = "SESSION_HELPER_ADMIN";

	public static final String Mail ="【网贷之家】取回密码说明";

	//############数据走势单位 start######################
	//单位【人】
	public static final String UNIT_PEOPLE="人";
	//单位【万】
	public static final String UNIT_W="万";
	//############数据走势单位 end######################

	/**
	 * 此前缀加平台表的PLAT_ID转成大写后的字符串，即可找到对应BID表
	 */
	public static final String BID_RECORD_PREFIX = "BID_RECORD_";

	/**
	 * 此前缀加平台表的PLAT_ID转成大写后的字符串，即可找到对应BORROW表
	 */
	public static final String BORROW_LIST_PREFIX = "BORROW_LIST_";



	/**
	 * 好标提醒设定用
	 */
	public static final String SPID_MARK = "-";


	/**
	 * 借款期限（月）
	 */
	public static final String PERIOD_MONTH="月";


	/**
	 * 借款期限（天）
	 */
	public static final String PERIOD_DAY="天";

	/**
	 * 短信最大数
	 */
	public static final int SMS_MAX_SIZE=1000;


}
