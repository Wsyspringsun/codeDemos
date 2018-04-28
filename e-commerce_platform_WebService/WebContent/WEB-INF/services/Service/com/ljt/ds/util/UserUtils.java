package ljt.ds.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class UserUtils {

	private static final String NUMBER_PATTERN = "^[0-9]+(.[0-9]{0,2})?$";// 判断小数点后二位的数字的正则表达式

	private static final String USER_ID_PATTERN = "[^a-zA-Z0-9\u4e00-\u9fa5]";// 注册用户名称是否还有非法字符

	private static final String ADDRESS_NAME_PATTERN = "^[\\u4e00-\\u9fa5][\\u4e00-\\u9fa5a-zA-Z0-9-]{1,}$";

	/**
	 * 交易银行支行名称（地址）首字母是否是汉字
	 *
	 * */
	public static boolean checkAddressName(String address) {
		Pattern pattern = Pattern.compile(ADDRESS_NAME_PATTERN);
		return pattern.matcher(address).matches();
	}


	/**
	 * 检查真实姓名
	 * */
	public static String checkRealName(String name) {
		char[] charArray = name.toCharArray();
		int len = charArray.length;
//		String surname = " 赵钱孙李周吴郑王冯陈褚卫蒋沈韩杨朱秦尤许何吕施张孔曹严华金魏陶姜戚谢邹喻柏水窦章云苏潘葛奚范彭郎鲁韦昌马苗凤花方俞任袁柳酆鲍史唐费廉岑薛雷贺倪汤滕殷罗毕郝邬安常乐于时傅皮卞齐伍余元顾孟平黄和穆萧尹姚邵湛汪祁毛禹狄米贝明臧计伏成戴谈宋茅庞熊纪舒屈项祝董梁杜阮蓝闵席季麻强贾路娄危江童颜郭梅盛林刁锺徐邱骆高夏蔡田樊胡凌霍虞万支柯昝管卢莫经房裘缪干解应宗丁宣贲邓郁单杭洪包诸左石崔吉钮龚程嵇邢滑裴陆荣翁荀羊於惠甄麴家封芮羿储靳汲邴糜松井段富巫乌焦巴弓牧隗山谷车侯宓蓬全郗班仰秋仲伊宫宁仇栾暴甘钭历戎祖武符刘景詹束龙叶幸司韶郜黎蓟溥印宿白怀蒲邰从鄂索咸籍赖卓蔺屠蒙池乔阳郁胥能苍双闻莘党翟谭贡劳逄姬申扶堵冉宰郦雍却璩桑桂濮牛寿通边扈燕冀僪浦尚农温别庄晏柴瞿阎充慕连茹习宦艾鱼容向古易慎戈廖庾终暨居衡步都耿满弘匡国文寇广禄阙东欧殳沃利蔚越夔隆师巩厍聂晁勾敖融冷訾辛阚那简饶空曾毋沙乜养鞠须丰巢关蒯相查后荆红游竺权逮盍益桓公万俟司马上官欧阳夏侯诸葛闻人东方赫连皇甫尉迟公羊澹台公冶宗政濮阳淳于单于太叔申屠公孙仲孙轩辕令狐钟离宇文长孙慕容司徒司空召有舜叶赫那拉丛岳寸贰皇侨彤竭端赫实甫集象翠狂辟典良函芒苦其京中夕之章佳那拉冠宾香果依尔根觉罗依尔觉罗萨嘛喇赫舍里额尔德特萨克达钮祜禄他塔喇喜塔腊讷殷富察叶赫那兰库雅喇瓜尔佳舒穆禄爱新觉罗索绰络纳喇乌雅范姜碧鲁张廖张简图门太史公叔乌孙完颜马佳佟佳富察费莫蹇称诺来多繁戊朴回毓税荤靖绪愈硕牢买但巧枚撒泰秘亥绍以壬森斋释奕姒朋求羽用占真穰翦闾漆贵代贯旁崇栋告休褒谏锐皋闳在歧禾示是委钊频嬴呼大威昂律冒保系抄定化莱校么抗祢綦悟宏功庚务敏捷拱兆丑丙畅苟随类卯俟友答乙允甲留尾佼玄乘裔延植环矫赛昔侍度旷遇偶前由咎塞敛受泷袭衅叔圣御夫仆镇藩邸府掌首员焉戏可智尔凭悉进笃厚仁业肇资合仍九衷哀刑俎仵圭夷徭蛮汗孛乾帖罕洛淦洋邶郸郯邗邛剑虢隋蒿茆菅苌树桐锁钟机盘铎斛玉线针箕庹绳磨蒉瓮弭刀疏牵浑恽势世仝同蚁止戢睢冼种涂肖己泣潜卷脱谬蹉赧浮顿说次错念夙斯完丹表聊源姓吾寻展出不户闭才无书学愚本性雪霜烟寒少字桥板斐独千诗嘉扬善揭祈析赤紫青柔刚奇拜佛陀弥阿素长僧隐仙隽宇祭酒淡塔琦闪始星南天接波碧速禚腾潮镜似澄潭謇纵渠奈风春濯沐茂英兰檀藤枝检生折登驹骑貊虎肥鹿雀野禽飞节宜鲜粟栗豆帛官布衣藏宝钞银门盈庆喜及普建营巨望希道载声漫犁力贸勤革改兴亓睦修信闽北守坚勇汉练尉士旅五令将旗军行奉敬恭仪母堂丘义礼慈孝理伦卿问永辉位让尧依犹介承市所苑杞剧第零谌招续达忻六鄞战迟候宛励粘萨邝覃辜初楼城区局台原考妫纳泉老清德卑过麦曲竹百福言第五佟爱年笪谯哈墨南宫赏伯佴佘牟商西门东门左丘梁丘琴后况亢缑帅微生羊舌海归呼延南门东郭百里钦鄢汝法闫楚晋谷梁宰父夹谷拓跋壤驷乐正漆雕公西巫马端木颛孙子车督仉司寇亓官鲜于锺离盖逯库郏逢阴薄厉稽闾丘公良段干开光操瑞眭泥运摩伟铁迮康";
//		int r = surname.indexOf(charArray[0]); // 查找字符串。
//		if (r == -1) {
//			return "姓名必须符合中国姓氏规则，请重新输入真实姓名！";
//		} else {
		if (len == 1) {
			return "姓名必须是一个字以上！";
		}
		if (len > 10) {
			return "姓名必须是十个汉字以内！";
		}
		String reg = "^([\u4E00-\u9FA5])*$";
		if (!match(reg, name)) {
			return "姓名必须全部为中文";
		} else {
			return "";
		}
//		}
	}

	public static String checkTel(String tel) {
		if (isMobileNumber(tel) || isTelNumber(tel)) {
			return "";
		} else {
			return "请输入正确的手机号码或电话号码；例如:13916752109或0712-3614072";
		}
	}



	/**
	 *
	 * 用户ID转义
	 *
	 * @param 用户ID
	 *
	 * @return 转义后ID
	 */

	public static String strToConceal(String userId) {
		String subUserId = "";
		if ("".equals(userId)) {
			return subUserId;
		}
		char[] charArray = userId.toCharArray();
		int len = charArray.length;
		if (len < 4) {
			subUserId = String.valueOf(charArray[0]) + "***";
		} else {
			subUserId = String.valueOf(charArray[0]) + String.valueOf(charArray[1]) + "***" + String.valueOf(charArray[len - 2])
					+ String.valueOf(charArray[len - 1]);
		}
		return subUserId;
	}

	/**
	 *
	 * 手机号转义
	 *
	 * @param 手机号码
	 *
	 * @return 转义后手机号码
	 */

	public static String phoneToConceal(String phoneNum) {
		if (phoneNum == null || "".equals(phoneNum)) {
			return "";
		} else {
			String ret = phoneNum.substring(0,3);
			ret = ret + "****";
			ret = ret + phoneNum.substring(7,11);
			return ret;
		}
	}

	/**
	 * 用户真实姓名转义
	 *
	 * @param 用户姓名
	 *
	 * @return 转义后用户姓名
	 *
	 * */
	public static String idCardToConceal(String idCard) {
		if ("".equals(idCard)) {
			return "";
		}

		return idCard.substring(0, 1) + "******" + idCard.substring(idCard.length()-1);
	}

	/**
	 * 用户身份证转义
	 *
	 * @param 身份证号
	 *
	 * @return 转义后身份证号
	 *
	 * */
	public static String realNameToConceal(String userId) {
		if ("".equals(userId)) {
			return "";
		}
		char[] charArray = userId.toCharArray();
		return String.valueOf(charArray[0]) + "**";
	}

	/**
	 *
	 * 根据用户积分获得对应等级图片
	 *
	 * @param 用户积分
	 *
	 * @return 等级图片
	 */

	public static String strToConceal(int userScore) {
		// TODO 根据积分获得不同等级的图片
		String str = "/web/cn/images/credit_hr.gif";
		return str;
	}

	public static String getUsersStatusImg(int status) {
		String imgSrc = "";
		switch (status) {
			case 1:
				// 普通用户
				imgSrc = "/web/cn/images/icon_user.png";
				break;
			case 2:
				// VIP用户
				imgSrc = "/web/cn/images/icon_userh.png";
				break;
			case 3:
				// 身份未认证
				imgSrc = "/web/cn/images/icon_idcard.png";
				break;
			case 4:
				// 身份已认证
				imgSrc = "/web/cn/images/icon_idcardh.png";
				break;
			case 5:
				// 邮箱未认证
				imgSrc = "/web/cn/images/icon_mail.png";
				break;
			case 6:
				// 邮箱已认证
				imgSrc = "/web/cn/images/icon_mailh.png";
				break;
			case 7:
				// 手机未认证
				imgSrc = "/web/cn/images/icon_phone.png";
				break;
			case 8:
				// 手机已认证
				imgSrc = "/web/cn/images/icon_phoneh.png";
				break;
			case 9:
				// 视频未认证
				imgSrc = "/web/cn/images/icon_video.png";
				break;
			case 10:
				// 视频已认证
				imgSrc = "/web/cn/images/icon_videoh.png";
				break;
		}
		return imgSrc;
	}

	public static String getBorrowUserStatusImg(int status) {
		String imgSrc = "";
		switch (status) {
			case 1:
				// 普通用户
				imgSrc = "/web/cn/images/icon_user.png";
				break;
			case 2:
				// 初级VIP
				imgSrc = "/web/cn/images/icon_userh.png";
				break;
			case 3:
				// 高级VIP
				imgSrc = "/web/cn/images/icon_userh.png";
				break;
			case 4:
				// 身份未认证
				imgSrc = "/web/cn/images/icon_idcard.png";
				break;
			case 5:
				// 身份已认证
				imgSrc = "/web/cn/images/icon_idcardh.png";
				break;
			case 6:
				// 邮箱未认证
				imgSrc = "/web/cn/images/icon_mail.png";
				break;
			case 7:
				// 邮箱已认证
				imgSrc = "/web/cn/images/icon_mailh.png";
				break;
			case 8:
				// 手机未认证
				imgSrc = "/web/cn/images/icon_phone.png";
				break;
			case 9:
				// 手机已认证
				imgSrc = "/web/cn/images/icon_phoneh.png";
				break;
			case 10:
				// 视频未认证
				imgSrc = "/web/cn/images/icon_video.png";
				break;
			case 11:
				// 视频已认证
				imgSrc = "/web/cn/images/icon_videoh.png";
				break;
		}
		return imgSrc;
	}

	public static String getBorrowStatusImg(int status) {
		String imgSrc = "";
		switch (status) {
			case 1:
				// 借款中
				imgSrc = "/web/cn/images/jkz-index.PNG";
				break;
			case 2:
				// 审核中
				imgSrc = "/web/cn/images/shz-index.PNG";
				break;
			case 3:
				// 还款中
				imgSrc = "/web/cn/images/hkz-index.PNG";
				break;
			case 4:
				// 还款完了
				imgSrc = "/web/cn/images/hkwl-index.PNG";
				break;
			case 5:
				// 流标
				imgSrc = "/web/cn/images/lb-index.PNG";
				break;
			case 6:
				// 撤回
				imgSrc = "/web/cn/images/yhch-index.png";
				break;
		}
		return imgSrc;
	}

	public static String getBorrowDayImg() {
		return "/web/cn/images/tb.gif";
	}

	public static String getBorrowAwardImg() {
		return "/web/cn/images/jl.gif";
	}

	public static String getBorrowDirectionalImg() {
		return "/web/cn/images/dx.gif";
	}

	public static String getBorrowTypeImg(int status) {
		String imgSrc = "";
		switch (status) {
			case 1:
				// 信用
				imgSrc = "/web/cn/images/ico_xin.png";
				break;
			case 2:
				// 抵押
				imgSrc = "/web/cn/images/ico_ya.png";
				break;
			case 3:
				// 净值
				imgSrc = "/web/cn/images/ico_jing.png";
				break;
			case 4:
				// 担保
				imgSrc = "/web/cn/images/ico_dan.png";
				break;
			case 5:
				// 秒还
				imgSrc = "/web/cn/images/ico_miao.png";
				break;
			case 6:
				// 流标
				imgSrc = "/web/cn/images/ico_liu.png";
				break;
		}
		return imgSrc;
	}

	public static String getBorrowInfoStatusImg(int status) {
		String imgSrc = "";
		switch (status) {
			case 1:
				// 借款中
				imgSrc = "/web/cn/images/tb-menu1.png";
				break;
			case 2:
				// 审核中
				imgSrc = "/web/cn/images/shz-menu.png";
				break;
			case 3:
				// 还款中
				imgSrc = "/web/cn/images/hkz-menu.png";
				break;
			case 4:
				// 还款完了
				imgSrc = "/web/cn/images/hkwl-menu.png";
				break;
			case 5:
				// 流标
				imgSrc = "/web/cn/images/lb-menu.png";
				break;
			case 6:
				// 撤回
				imgSrc = "/web/cn/images/yhch-menu.png";
				break;
			case 7:
				// 复审失败
				imgSrc = "/web/cn/images/fssb-menu.png";
				break;
		}
		return imgSrc;
	}

	public static String getBorrowListStatusImg(int status) {
		String imgSrc = "";
		switch (status) {
			case 1:
				// 借款中
				imgSrc = "/web/cn/images/ckz-bq.png";
				break;
			case 2:
				// 审核中
				imgSrc = "/web/cn/images/shz-bq.png";
				break;
			case 3:
				// 还款中
				imgSrc = "/web/cn/images/hkz-bq.png";
				break;
			case 4:
				// 还款完了
				imgSrc = "/web/cn/images/yhw-bq.png";
				break;
			case 5:
				// 流标
				imgSrc = "/web/cn/images/zb-yjs.png";
				break;
			case 6:
				// 撤回
				imgSrc = "/web/cn/images/zb-yjs.png";
				break;
		}
		return imgSrc;
	}

	public static String getWanderBorrowInfoStatusImg(int status) {
		String imgSrc = "";
		switch (status) {
			case 1:
				// 借款中
				imgSrc = "/web/cn/images/marg-menu.png";
				break;
			case 2:
				// 审核中
				imgSrc = "/web/cn/images/rgjs-menu.png";
				break;
		}
		return imgSrc;
	}

	/**
	 *
	 * 银行卡号转义
	 *
	 * @param 用户ID
	 *
	 * @return 转义后ID
	 */

	public static String strCardToConceal(String cardCode) {
		if (StringUtils.isEmpty(cardCode)) {
			return "";
		}
		int len = cardCode.length();
		String str1 = cardCode.substring(0, 4);
		String str2 = cardCode.substring(len - 4);
		String str = org.apache.commons.lang.StringUtils.rightPad(str1, len - 4, '*') + str2;
		return str;
	}

	/**
	 * 验证邮箱地址是否正确
	 *
	 * @param email
	 * @return
	 */
	public static boolean checkEmail(String email) {
		boolean flag = false;
		try {
			String check = "^([a-z0-9A-Z]+[_\\-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(email);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}

		return flag;
	}

	public static boolean isMobileNumber(String mobiles) {
		boolean flag = false;
		try {
			Pattern p = Pattern.compile("^13[0-9]{9}$|14[0-9]{9}|15[0-9]{9}$|17[0-9]{9}$|18[0-9]{9}$");
			// "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$"
			Matcher m = p.matcher(mobiles);
			flag = m.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	public static boolean isTelNumber(String tel) {
		boolean flag = false;
		try {
			Pattern p = Pattern.compile("^(([0\\+]\\d{2,3}-)?(0\\d{2,3})-)(\\d{7,8})(-(\\d{3,}))?$");
			// "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$"
			Matcher m = p.matcher(tel);
			flag = m.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;

	}

	public static boolean isDecimalNumber(String input) {

		boolean isDecimal = false;

		if (StringUtils.isEmpty(input)) {
			isDecimal = false;
		} else {
			if (input.lastIndexOf(".") == input.length() - 1) {
				isDecimal = false;
			} else {
				isDecimal = match(NUMBER_PATTERN, input);
			}
		}

		return isDecimal;
	}

	public static boolean checkUserID(String input) {
		return match(USER_ID_PATTERN, input);
	}

	public static boolean isNumeric(String input) {
		if (StringUtils.isEmpty(input)) {
			return false;
		}
		if (input.indexOf(".") != input.lastIndexOf(".")) {
			return false;
		}
		input = input.replace(".", "");
		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if ((c < '0' || c > '9')) {
				return false;
			}
		}
		return true;
	}

	private static boolean match(String pattern, String str) {
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(str);
		return m.find();
	}

	/**
	 * 功能：身份证的有效验证
	 *
	 * @param IDStr
	 *            身份证号
	 * @return 有效：返回"" 无效：返回String信息
	 * @throws ParseException
	 */
	public static String checkIdCard(String IDStr) {
		String errorInfo = "";// 记录错误信息
		String[] ValCodeArr = { "1", "0", "x", "9", "8", "7", "6", "5", "4", "3", "2" };
		String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7", "9", "10", "5", "8", "4", "2" };
		String Ai = "";
		try {
		// ================ 号码的长度 15位或18位 ================
		if (IDStr.length() != 15 && IDStr.length() != 18) {
			errorInfo = "身份证号码长度应该为15位或18位。";
			return errorInfo;
		}
		// =======================(end)========================

		// ================ 数字 除最后以为都为数字 ================
		if (IDStr.length() == 18) {
			Ai = IDStr.substring(0, 17);
		} else if (IDStr.length() == 15) {
			Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);
		}
		if (isNumeric(Ai) == false) {
			errorInfo = "身份证15位号码都应为数字 ; 18位号码除最后一位外，都应为数字。";
			return errorInfo;
		}
		// =======================(end)========================

		// ================ 出生年月是否有效 ================
		String strYear = Ai.substring(6, 10);// 年份
		String strMonth = Ai.substring(10, 12);// 月份
		String strDay = Ai.substring(12, 14);// 月份
		if (isDate(strYear + "-" + strMonth + "-" + strDay) == false) {
			errorInfo = "身份证生日无效。";
			return errorInfo;
		}
		GregorianCalendar gc = new GregorianCalendar();
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150
				|| (gc.getTime().getTime() - s.parse(strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
			errorInfo = "身份证生日不在有效范围。";
			return errorInfo;
		}
		if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
			errorInfo = "身份证月份无效";
			return errorInfo;
		}
		if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
			errorInfo = "身份证日期无效";
			return errorInfo;
		}
		// =====================(end)=====================

		// ================ 地区码时候有效 ================
		Hashtable<String, String> h = GetAreaCode();
		if (h.get(Ai.substring(0, 2)) == null) {
			errorInfo = "身份证地区编码错误。";
			return errorInfo;
		}
		// ==============================================

		// ================ 判断最后一位的值 ================
		int TotalmulAiWi = 0;
		for (int i = 0; i < 17; i++) {
			TotalmulAiWi = TotalmulAiWi + Integer.parseInt(String.valueOf(Ai.charAt(i))) * Integer.parseInt(Wi[i]);
		}
		int modValue = TotalmulAiWi % 11;
		String strVerifyCode = ValCodeArr[modValue];
		Ai = Ai + strVerifyCode;

		if (IDStr.length() == 18) {
			if (Ai.equals(IDStr.toLowerCase()) == false) {
				errorInfo = "身份证无效，不是合法的身份证号码";
				return errorInfo;
			}
		} else {
			return "";
		}
		} catch (NumberFormatException e) {
			return "校验身份证信息时发生异常";
		} catch (ParseException e) {
			return "校验身份证信息时发生异常";
		}
		// =====================(end)=====================
		return "";
	}

	/**
	 * 功能：设置地区编码
	 *
	 * @return Hashtable 对象
	 */
	private static Hashtable<String, String> GetAreaCode() {
		Hashtable<String, String> hashtable = new Hashtable<String, String>();
		hashtable.put("11", "北京");
		hashtable.put("12", "天津");
		hashtable.put("13", "河北");
		hashtable.put("14", "山西");
		hashtable.put("15", "内蒙古");
		hashtable.put("21", "辽宁");
		hashtable.put("22", "吉林");
		hashtable.put("23", "黑龙江");
		hashtable.put("31", "上海");
		hashtable.put("32", "江苏");
		hashtable.put("33", "浙江");
		hashtable.put("34", "安徽");
		hashtable.put("35", "福建");
		hashtable.put("36", "江西");
		hashtable.put("37", "山东");
		hashtable.put("41", "河南");
		hashtable.put("42", "湖北");
		hashtable.put("43", "湖南");
		hashtable.put("44", "广东");
		hashtable.put("45", "广西");
		hashtable.put("46", "海南");
		hashtable.put("50", "重庆");
		hashtable.put("51", "四川");
		hashtable.put("52", "贵州");
		hashtable.put("53", "云南");
		hashtable.put("54", "西藏");
		hashtable.put("61", "陕西");
		hashtable.put("62", "甘肃");
		hashtable.put("63", "青海");
		hashtable.put("64", "宁夏");
		hashtable.put("65", "新疆");
		hashtable.put("71", "台湾");
		hashtable.put("81", "香港");
		hashtable.put("82", "澳门");
		hashtable.put("91", "国外");
		return hashtable;
	}

	/**
	 * 功能：判断字符串是否为日期格式
	 *
	 * @param str
	 * @return
	 */
	public static boolean isDate(String strDate) {
		Pattern pattern = Pattern
				.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
		Matcher m = pattern.matcher(strDate);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 校验银行卡卡号
	 *
	 * @param cardId
	 * @return
	 */
	public static boolean checkBankCard(String cardId) {
		if (StringUtils.isEmpty(cardId)) {
			return false;
		}
		if (!isNumeric(cardId)) {
			return false;
		}
		if (cardId.length() >= 15 && cardId.length() < 20) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
	 *
	 * @param nonCheckCodeCardId
	 * @return
	 */
	public static char getBankCardCheckCode(String nonCheckCodeCardId) {
		if (nonCheckCodeCardId == null || nonCheckCodeCardId.trim().length() == 0 || !nonCheckCodeCardId.matches("\\d+")) {
			// 如果传的不是数据返回N
			return 'N';
		}
		char[] chs = nonCheckCodeCardId.trim().toCharArray();
		int luhmSum = 0;
		for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
			int k = chs[i] - '0';
			if (j % 2 == 0) {
				k *= 2;
				k = k / 10 + k % 10;
			}
			luhmSum += k;
		}
		return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
	}


	public static String clearMoneyDecimal(String tmp) {
		return tmp.replace(",", "");
	}

	public static String getUserAttestStatusImg(int imgIndex) {
		String imgSrc = "";
		switch (imgIndex) {
		// EMAIL认证状态图片
			case 1:
				imgSrc = "/web/cn/images/icon_mail.png";
				break;
			case 2:
				imgSrc = "/web/cn/images/icon_mailh.png";
				break;
			// 手机认证状态图片
			case 3:
				imgSrc = "/web/cn/images/icon_phone.png";
				break;
			case 4:
				imgSrc = "/web/cn/images/icon_phoneh.png";
				break;
			// 实名认证状态图片
			case 5:
				imgSrc = "/web/cn/images/icon_idcard.png";
				break;
			case 6:
				imgSrc = "/web/cn/images/icon_idcardh.png";
				break;
			// 认证状态图片
			case 7:
				imgSrc = "/web/cn/images/cancel.png";
				break;
			case 8:
				imgSrc = "/web/cn/images/exclamation.png";
				break;
			case 9:
				imgSrc = "/web/cn/images/accept.png";
				break;
		}
		return imgSrc;
	}

	/**
	 * 获得标的简称
	 *
	 * @param status
	 * @return
	 */
	public static String getBorrowTypeShortName(int status) {
		String shortName = "";
		switch (status) {
			case 1:
				// 信用
				shortName = "信";
				break;
			case 2:
				// 抵押
				shortName = "抵";
				break;
			case 3:
				// 净值
				shortName = "净";
				break;
			case 4:
				// 担保
				shortName = "担";
				break;
			case 5:
				// 秒还
				shortName = "秒";
				break;
			case 6:
				// 流标
				shortName = "流";
				break;
		}
		return shortName;
	}

	public static String borrowType(String borrowType) {
		switch (Integer.parseInt(borrowType)) {
			case 1:
				return "信用标";
			case 2:
				return "抵押标";
			case 3:
				return "净值标";
			case 4:
				return "担保标";
			case 5:
				return "秒还标";
			case 6:
				return "流转标";
			default:
				return "信用标";
		}
	}

//	public static StatusBean getBorrowListStatusImg(Model<String, Object> borrowInfo) {
//		StatusBean bean = new StatusBean();
//		// 撤回标示
//		String cancelStatus = borrowInfo.getStringValue("CANCEL_STATUS");
//		// 满标标示
//		String borrowFullStatus = borrowInfo.getStringValue("BORROW_FULL_STATUS");
//		// 初审标示
//		String verifyFlg = borrowInfo.getStringValue("VERIFY_FLG");
//		// 复审标示
//		String reverifyFlg = borrowInfo.getStringValue("REVERIFY_FLG");
//		// 流标标示
//		String failedFlg = borrowInfo.getStringValue("FAILED_FLG");
//		String borrowType = borrowInfo.getStringValue("BORROW_TYPE");
//		String borrowStartFlag = borrowInfo.getStringValue("BORROW_START_FLAG");
//		if ("6".equals(borrowType)) {
//			if (NumberUtils.parseDouble(borrowInfo.getStringValue("BORROW_ACCOUNT_SCALE")) >= 100) {
//				if (NumberUtils.parseDouble(borrowInfo.getStringValue("REPAY_ACCOUNT_WAIT")) <= 0) {
//					bean.setStatusValue("已还完");
//					bean.setImgUrl("/web/cn/images/yhw-bq.png");
//					bean.setStatusClass("btn_borrow_sts_yhw");
//				} else {
//					bean.setStatusValue("还款中");
//					bean.setImgUrl("/web/cn/images/hkz-bq.png");
//					bean.setStatusClass("btn_borrow_sts_hkz");
//				}
//			} else {
//				if ("1".equals(borrowStartFlag)) {
//					bean.setStatusCode(9);
//					bean.setStatusValue("等待中");
//				} else {
//					bean.setStatusCode(5);
//					bean.setStatusValue("立即投资");
//					bean.setImgUrl("/web/cn/images/ckz-bq.png");
//					bean.setStatusClass("btn_borrow_sts_ckz");
//				}
//			}
//		} else {
//			if ("1".equals(cancelStatus)) {
//				bean.setStatusCode(1);
//				bean.setStatusValue("撤回");
//				bean.setImgUrl("/web/cn/images/zb-yjs.png");
//				bean.setStatusClass("btn_borrow_sts_zb");
//			} else {
//				if ("1".equals(failedFlg)) {
//					bean.setStatusCode(2);
//					bean.setStatusValue("流标");
//					bean.setImgUrl("/web/cn/images/zb-yjs.png");
//					bean.setStatusClass("btn_borrow_sts_lb");
//				} else {
//					if (!"1".equals(borrowFullStatus)) {
//						switch (verifyFlg) {
//						case "":
//							bean.setStatusCode(3);
//							bean.setStatusValue("初审中");
//							bean.setStatusClass("btn_borrow_sts_csz");
//							break;
//						case "0":
//							bean.setStatusCode(4);
//							bean.setStatusValue("初审失败");
//							bean.setStatusClass("btn_borrow_sts_csf");
//							break;
//						case "1":
//							if ("1".equals(borrowStartFlag)) {
//								bean.setStatusCode(9);
//								bean.setStatusValue("等待中");
//							} else {
//								bean.setStatusCode(5);
//								bean.setStatusValue("立即投资");
//								bean.setImgUrl("/web/cn/images/ckz-bq.png");
//								bean.setStatusClass("btn_borrow_sts_ckz");
//							}
//							break;
//						}
//					} else {
//						switch (reverifyFlg) {
//						case "":
//							bean.setStatusCode(6);
//							bean.setStatusValue("复审中");
//							bean.setImgUrl("/web/cn/images/shz-bq.png");
//							bean.setStatusClass("btn_borrow_sts_fsz");
//							break;
//						case "0":
//							bean.setStatusCode(7);
//							bean.setStatusValue("复审失败");
//							bean.setImgUrl("/web/cn/images/hkz-fssb.png");
//							bean.setStatusClass("btn_borrow_sts_fsf");
//							break;
//						case "1":
//							bean.setStatusCode(8);
//							if (NumberUtils.parseDouble(borrowInfo.getStringValue("REPAY_ACCOUNT_WAIT")) <= 0) {
//								bean.setStatusValue("已还完");
//								bean.setImgUrl("/web/cn/images/yhw-bq.png");
//								bean.setStatusClass("btn_borrow_sts_yhw");
//							} else {
//								bean.setStatusValue("还款中");
//								bean.setImgUrl("/web/cn/images/hkz-bq.png");
//								bean.setStatusClass("btn_borrow_sts_hkz");
//							}
//							break;
//						}
//					}
//				}
//			}
//		}
//		return bean;
//	}

	public static boolean isPasswordStrength(String str) {
		String regex = "^(?![`~!@#$%^&*()_+=\\\\|{}\\[\\]'\":;,.<>/?-]+$)[`~!@#$%^&*()_+=\\\\|{}\\[\\]'\":;,.<>/?0-9a-zA-Z-]{8,20}$";
		return match(regex, str);
	}
}
