package ljt.ds.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class NumberUtils {

	/**
	 *
	 * 金额格式化。
	 *
	 * @param money
	 *            金额
	 * @return 格式化后金额
	 */
	public static String moneyFormat(double moneyD) {
		String money = StringUtils.objToString(moneyD);
		DecimalFormat myformat = new DecimalFormat();
		myformat.applyPattern("###,###,##0.00");
		if (StringUtils.isEmpty(money)) {
			return "0.00";
		} else {
			return myformat.format(parseDouble(money));
		}
	}
	/**
	 *
	 * 金额格式化。
	 *
	 * @param money
	 *            金额
	 * @return 格式化后金额
	 */
	public static String moneyFormat(Object moneyD) {
		String money = StringUtils.objToString(moneyD);
		DecimalFormat myformat = new DecimalFormat();
		myformat.applyPattern("###,###,##0.00");
		if (StringUtils.isEmpty(money)) {
			return "0.00";
		} else {
			return myformat.format(parseDouble(money));
		}
	}

	/**
	 *
	 * 金额格式化。
	 *
	 * @param money
	 *            金额
	 * @return 格式化后金额
	 */
	public static String moneyFormat(String money) {
		DecimalFormat myformat = new DecimalFormat();
		myformat.applyPattern("###,###,##0.00");
		if (StringUtils.isEmpty(money)) {
			return "0.00";
		} else {
			return myformat.format(parseDouble(money));
		}
	}

	/**
	 *
	 * 数字格式化。
	 *
	 * @param money
	 *            数字字符串
	 * @return 格式化后数字
	 */
	public static String numberFormat(String money) {
		DecimalFormat myformat = new DecimalFormat();
		myformat.applyPattern("###,###,##0");
		if (StringUtils.isEmpty(money)) {
			return "0";
		} else {
			return myformat.format(parseInteger(money));
		}
	}

	public static String decimalFormat(String decimal) {
		DecimalFormat myformat = new DecimalFormat();
		myformat.applyPattern("#0.00");
		if (StringUtils.isEmpty(decimal)) {
			return "0.00";
		} else {
			return myformat.format(parseDouble(decimal));
		}
	}

	public static double subtract(double minuend, double subtrahend) {
		return minuend - subtrahend;
	}

	public static double add(double minuend, double subtrahend) {
		return minuend + subtrahend;
	}

	/**
	 *
	 * 贷款成交费计算。
	 *
	 * @param val
	 *            贷款金额
	 * @param digit
	 *            成交费比例
	 * @return 贷款成交费
	 */
	public static double calcTransactionFee(double account, double scale) {
		double transactionFee = account * scale / 100;
		return decimalConverter(transactionFee, 2, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 *
	 * 字符串转长整形。
	 *
	 * @param val
	 *            转化前数字
	 * @return 转化后数字
	 */
	public static double parseDouble(String val) {
		if (StringUtils.isEmpty(val)) {
			return 0;
		} else {
			return Double.parseDouble(val);
		}
	}

	/**
	 *
	 * 字符串转long。
	 *
	 * @param val
	 *            转化前数字
	 * @return 转化后数字
	 */
	public static long parseLong(String val) {
		if (StringUtils.isEmpty(val)) {
			return 0;
		} else {
			return Long.parseLong(val);
		}
	}

	/**
	 *
	 * 字符串转整形。
	 *
	 * @param val
	 *            转化前数字
	 * @return 转化后数字
	 */
	public static String convToZero(Object val) {
		String value = StringUtils.objToString(val);
		if (StringUtils.isEmpty(value)) {
			return "0";
		} else {
			return value;
		}
	}

	/**
	 *
	 * 数字转布尔。
	 *
	 * @param val
	 *            转化前数字
	 * @return 转化后true，false
	 */
	public static boolean convToBool(Object val) {
		String value = StringUtils.objToString(val);
		if (StringUtils.isEmpty(value)) {
			return false;
		} else {
			if (parseInteger(value) == 1) {
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 *
	 * 字符串转整形。
	 *
	 * @param val
	 *            转化前数字
	 * @return 转化后数字
	 */
	public static int parseInteger(String val) {
		if (StringUtils.isEmpty(val)) {
			return 0;
		} else {
			return Integer.parseInt(val);
		}
	}

	/**
	 *
	 * 小数点转换。
	 *
	 * @param val
	 *            被转换值
	 * @param digit
	 *            小数点位数
	 * @param roundType
	 *            小数点处理类型
	 * @return 转换后值
	 */
	public static double decimalConverter(double val, int digit, int roundType) {
		BigDecimal bigVal = new BigDecimal(val);
		return bigVal.setScale(digit, roundType).doubleValue();
	}

	/**
	 * 使用java正则表达式去掉多余的.与0
	 *
	 * @param s
	 * @return
	 */
	public static String subZeroAndDot(String s) {
		if (s.indexOf(".") > 0) {
			s = s.replaceAll("0+?$", "");
			s = s.replaceAll("[.]$", "");
		}
		return s;
	}

	/**
	 * 字符串格式化
	 * @param
	 * 		格式前的内容
	 * @return
	 * 		格式后的内容
	 * */
	public static String parseNumber(String val){
		DecimalFormat df3 = new DecimalFormat();
		df3.applyPattern("#0.##");
		return df3.format(parseDouble(val));
	}
}
