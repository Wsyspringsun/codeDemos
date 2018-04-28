package ljt.ds.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.CharRange;
import org.apache.commons.lang.CharSet;

import com.ljt.ds.common.util.MathUtils;

public final class StringUtils extends StringUtilities {

	/** カンマ符号 */
	public final static String STR_COMMA = ",";
	/**
	 * ASCII 文字 （半角英数）を表すのインスタンスです。
	 */
	private static CharSet alphaNumChar = CharSet.getInstance("0-9A-Za-z");
	/**
	 * ASCII 文字（半角英数・半角記号）を表すのインスタンスです。
	 */
	private static CharSet ascii = CharSet
			.getInstance(" !-/0-9:-@A-Z[-`a-z{-~");
	/**
	 * 半角英大文字を表すのインスタンスです。
	 */
	private static CharRange alphaCapital = new CharRange('\u0041', '\u005A');
	/**
	 * 半角英小文字を表すのインスタンスです。
	 */
	private static CharRange alphaSmall = new CharRange('\u0061', '\u007A');
	/**
	 * 半角数字を表すのインスタンスです。
	 */
	private static CharRange digit = new CharRange('\u0030', '\u0039');
	/**
	 * 0x00 から 0x7F を表すのインスタンスです。
	 */
	private static CharRange halfChars = new CharRange('\u0000', '\u007F');
	/**
	 * 半角カタカナを表すのインスタンスです。
	 */
	private static CharRange halfKatakana = new CharRange('\uFF61', '\uFF9F');
	/**
	 * 全角ひらがなを表すのインスタンスです。
	 */
	private static CharRange hiragana = new CharRange('\u3040', '\u309F');

	/**
	 * コンストラクタです。
	 */
	private StringUtils() {
	}

	/**
	 * 引数の文字列が半角 A-Z だけで構成されているかどうかをチェックするメソッドです。 引数がnullまたは空文字の場合にはtrueを返します。
	 *
	 * @param str
	 *            文字列
	 * @return 半角 A-Z だけで構成されていれば true
	 */
	public static boolean isUpperCaseOnly(String str) {
		boolean upperCaseOnly = true;
		if (StringUtils.isEmpty(str)) {
			return upperCaseOnly;
		}
		char[] cs = str.toCharArray();
		for (int i = 0; i < cs.length; i++) {
			if (!alphaCapital.contains(cs[i])) {
				upperCaseOnly = false;
			}
		}
		return upperCaseOnly;
	}

	/**
	 * 引数の文字列が半角数字だけで構成されているかどうかをチェックするメソッドです。 引数が nullまたは空文字の場合には falseを返します。
	 *
	 * @param str
	 *            文字列
	 * @return 半角数字 0-9 だけで構成されていれば true
	 */
	public static boolean isDigit(String str) {
		boolean isdigit = false;
		if (StringUtils.isEmpty(str)) {
			return isdigit;
		}
		isdigit = true;
		char[] cs = str.toCharArray();
		for (int i = 0; i < cs.length; i++) {
			if (!digit.contains(cs[i])) {
				isdigit = false;
			}
		}
		return isdigit;
	}

	/**
	 * 引数の文字列が半角 a-z だけで構成されているかどうかをチェックするメソッドです。 引数がnullまたは空文字の場合にはtrueを返します。
	 *
	 * @param str
	 *            文字列
	 * @return 半角 a-z だけで構成されていれば true
	 */
	public static boolean isLowerCaseOnly(String str) {
		boolean lowerCaseOnly = true;
		if (StringUtils.isEmpty(str)) {
			return lowerCaseOnly;
		}
		char[] cs = str.toCharArray();
		for (int i = 0; i < cs.length; i++) {
			if (!alphaSmall.contains(cs[i])) {
				lowerCaseOnly = false;
			}
		}
		return lowerCaseOnly;
	}

	/**
	 * 引数の文字列が半角英字 a-z A-Zだけで構成されているかどうかをチェックするメソッドです。 引数が nullまたは空文字の場合には
	 * trueを返します。
	 *
	 * @param str
	 *            文字列
	 * @return 半角 a-z A-Z だけで構成されていれば true
	 */
	public static boolean isAlphaAlphabetOnly(String str) {
		boolean lowerCaseOnly = true;
		if (StringUtils.isEmpty(str)) {
			return lowerCaseOnly;
		}
		char[] cs = str.toCharArray();
		for (int i = 0; i < cs.length; i++) {
			if (!alphaSmall.contains(cs[i]) && !alphaCapital.contains(cs[i])) {
				lowerCaseOnly = false;
			}
		}
		return lowerCaseOnly;
	}

	/**
	 * 文字列が全てASCII文字か否かをチェックするメソッドです。
	 * ASCII文字の定義を「半角英数・半角記号」とし、「半角英数・半角記号以外」が含まれていればfalseを返します。
	 * 引数がnullまたは空文字の場合にはtrueを返します。
	 *
	 * @param str
	 *            検証する文字列
	 * @return 半角が含まれていれば false
	 */
	public static boolean isAsciiOnly(String str) {
		boolean asciiOnly = true;
		if (StringUtils.isEmpty(str)) {
			return asciiOnly;
		}
		char[] cs = str.toCharArray();
		for (int i = 0; i < cs.length; i++) {
			if (!ascii.contains(cs[i])) {
				asciiOnly = false;
			}
		}
		if (asciiOnly && isVistaData(str)) {
			asciiOnly = false;
		}
		return asciiOnly;
	}

	/**
	 * 文字列が全てASCII文字(半角英数)か否かをチェックするメソッドです。
	 * ASCII文字の定義を「半角英数」とし、「半角英数以外」が含まれていればfalseを返します。
	 * 引数がnullまたは空文字の場合にはtrueを返します。
	 *
	 * @param str
	 *            検証する文字列
	 * @return 半角が含まれていれば false
	 */
	public static boolean isAsciiAlphaNumCharOnly(String str) {
		boolean asciiOnly = true;
		if (StringUtils.isEmpty(str)) {
			return asciiOnly;
		}
		char[] cs = str.toCharArray();
		for (int i = 0; i < cs.length; i++) {
			if (!alphaNumChar.contains(cs[i])) {
				asciiOnly = false;
			}
		}
		return asciiOnly;
	}

	/**
	 * 文字列が全て全角文字か否かをチェックするメソッドです。 全角文字の定義を「半角英数・半角カタカナ・半角記号・制御文字以外」とし、
	 * 「半角英数・半角カタカナ・半角記号・制御文字」が含まれていればfalseを返します。 引数がnullまたは空文字の場合にはtrueを返します。
	 *
	 * @param str
	 *            検証する文字列
	 * @return 半角が含まれていれば false
	 */
	public static boolean isFullWidthOnly(String str) {
		boolean fullWidthOnly = true;
		if (StringUtils.isEmpty(str)) {
			return fullWidthOnly;
		}
		char[] cs = str.toCharArray();
		for (int i = 0; i < cs.length; i++) {
			if (halfChars.contains(cs[i]) || halfKatakana.contains(cs[i])) {
				fullWidthOnly = false;
			}
		}
		return fullWidthOnly;
	}

	/**
	 * 文字列が全て全角文字と改行であるか否かをチェックするメソッドです。 全角文字の定義を「半角英数・半角カタカナ・半角記号・制御文字以外」とし、
	 * 「半角英数・半角カタカナ・半角記号・制御文字」が含まれていればfalseを返します。
	 * ただし、改行文字[0x0D][0x0A]を有効な文字とします。 引数がnullまたは空文字の場合にはtrueを返します。
	 *
	 * @param str
	 *            検証する文字列
	 * @return 半角が含まれていれば false
	 */
	public static boolean isFullWidthOnlyAndEnter(String str) {
		boolean fullWidthOnly = true;
		if (StringUtils.isEmpty(str)) {
			return fullWidthOnly;
		}
		char[] cs = str.toCharArray();
		for (int i = 0; i < cs.length; i++) {
			if (halfChars.contains(cs[i]) || halfKatakana.contains(cs[i])) {
				if (cs[i] != 0x0D && cs[i] != 0x0A) {
					return (false);
				}
			}
		}
		return fullWidthOnly;
	}

	/**
	 * 文字列が全て半角文字か否かをチェックするメソッドです。 半角文字の定義を「半角英数・半角カタカナ・半角記号・制御文字」とし、
	 * 「半角英数・半角カタカナ・半角記号・制御文字以外」が含まれていればfalseを返します。 引数がnullまたは空文字の場合にはtrueを返します。
	 *
	 * @param str
	 *            検証する文字列
	 * @return 全角が含まれていれば false
	 */
	public static boolean isHalfWidthOnly(String str) {
		boolean halfWidthOnly = true;
		if (StringUtils.isEmpty(str)) {
			return halfWidthOnly;
		}
		char[] cs = str.toCharArray();
		for (int i = 0; i < cs.length; i++) {
			if (!halfChars.contains(cs[i]) && !halfKatakana.contains(cs[i])) {
				halfWidthOnly = false;
			}
		}
		if (halfWidthOnly && isVistaData(str)) {
			halfWidthOnly = false;
		}
		return halfWidthOnly;
	}

	/**
	 * 文字列が全て全角ひらがなか否かをチェックするメソッドです。 引数がnullまたは空文字の場合にはtrueを返します。
	 *
	 * @param str
	 *            検証する文字列
	 * @return ひらがなのみならば true
	 */
	public static boolean isHiraganaOnly(String str) {
		boolean hiraganaOnly = true;
		if (StringUtils.isEmpty(str)) {
			return hiraganaOnly;
		}
		char[] cs = str.toCharArray();
		for (int i = 0; i < cs.length; i++) {
			if (!hiragana.contains(cs[i]) && cs[i] != '\u30FC') {
				hiraganaOnly = false;
			}
		}
		return hiraganaOnly;
	}

	/**
	 * 引数の文字列が半角数字だけで構成されているかどうかをチェックするメソッドです。 引数がnullまたは空文字の場合にはfalseを返します。
	 *
	 * @param str
	 *            文字列
	 * @return 半角数字 0-9 だけで構成されていれば true
	 */
	public static boolean isAllDigit(String str) {
		if (str == null || trim(str).equals("")) {
			return false;
		}
		boolean isdigit = true;
		char[] cs = str.toCharArray();
		for (int i = 0; i < cs.length; i++) {
			if (!digit.contains(cs[i])) {
				isdigit = false;
			}
		}
		return isdigit;
	}

	/**
	 * 文字列の前後のスペース（半角、全角）を取り除くメソッドです。
	 *
	 * @param orgstr
	 *            変換対象の文字列
	 * @return 変換済みの文字列
	 */
	public static String trim(String orgstr) {
		if (StringUtils.isEmpty(orgstr)) {
			return "";
		}
		/* 前方のスペースを取り除く */
		while (orgstr.startsWith(" ") || orgstr.startsWith("　")) {
			orgstr = orgstr.substring(1);
		}
		/* 後方のスペースを取り除く */
		while (orgstr.endsWith(" ") || orgstr.endsWith("　")) {
			orgstr = orgstr.substring(0, orgstr.length() - 1);
		}
		return orgstr;
	}

	/**
	 * 指定した桁数にあわせて、左側を "0" で埋めるメソッドです。
	 *
	 * @param str
	 *            変換対象となる文字列
	 * @param length
	 *            桁数
	 * @return 左側0埋めされた文字列
	 * @see StringUtils#leftPad(java.lang.String, int)
	 */
	public static String leftPad(String str, int length, char paddingChar) {
		str = org.apache.commons.lang.StringUtils.leftPad(str, length,
				paddingChar);
		return str;
	}

	public static String rightPad(String str, int length, char paddingChar) {
		return org.apache.commons.lang.StringUtils.rightPad(str, length,
				paddingChar);
	}

	/**
	 * Windows VistaからPOSTされるデータに含まれる文字列が存在するかチェックするメソッドです。
	 * Vistaから送信されるデータには「&#nnnn;」といった文字列が含まれる想定です。
	 * nnnnは半角数字が1文字以上含まれた場合、存在すると判断します。
	 *
	 * @param str
	 *            検証文字列
	 * @return 存在する場合 true、存在しない場合 false
	 */
	public static boolean isVistaData(String str) {
		// &#と;の間が数値で1個以上で一致するか
		Pattern p = Pattern.compile(".*&#\\d+?;.*");
		Matcher m = p.matcher(str);
		return m.find();
	}

	/**
	 * Object対象は文字列へ変更する。
	 *
	 * @param obj
	 *            Object対象
	 * @return 文字列
	 */
	public static String objToString(Object obj) {
		if (obj == null) {
			return "";
		} else {
			return String.valueOf(obj);
		}
	}

	/**
	 * 検証文字列がnullまたは、空文字かどうかのチェックを行います。
	 *
	 * @param str
	 *            検証文字列
	 * @return 検証文字列がnullまたは空文字の場合true、そうでない場合falseを返却。
	 */
	public static boolean isNull(String str) {
		return str == null || str.equals("");
	}

	/**
	 * 文字列変換
	 *
	 * @param input
	 *            入力文字列
	 * @return 文字列
	 */
	public static String nullToString(String input) {
		return input == null ? "" : input;
	}

	/**
	 * 分割符クリア
	 *
	 * @param input
	 *            入力文字列
	 * @param commaBefore
	 *            切替元文字
	 * @param commaAfter
	 *            切替先文字
	 * @return 分割符クリア後文字列
	 */
	public static String clearComma(String input, String commaBefore,
			String commaAfter) {
		if (input == null || "".equals(input)) {
			return "";
		} else {
			return input.replaceAll(commaBefore, commaAfter);
		}
	}

	/**
	 * 空文字列判定
	 *
	 * @param input
	 *            入力文字列
	 * @return true/false
	 */
	public static boolean isBlank(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 空文字列判定
	 *
	 * @param input
	 *            入力文字列
	 * @return true/false
	 */
	public static boolean isEmpty(String input) {
		if (input == null || "".equals(input)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 文字列変換
	 *
	 * @param input
	 *            入力文字列
	 * @return 文字列
	 */
	public static Character stringToChar(String input) {

		if ("".equals(input) || input == null) {
			return Character.valueOf(' ');
		} else {
			return input.charAt(0);
		}
	}

	/**
	 * 文字列の右側からスペースを満たす。
	 *
	 * @param str
	 *            入力文字列
	 * @param strLength
	 *            文字列桁数
	 * @return 文字列
	 */
	public static String addSpaceForChar(String str, int strLength) {
		int strLen = str.length();
		if (strLen < strLength) {
			while (strLen < strLength) {
				StringBuilder sb = new StringBuilder();
				sb.append(str).append(" ");
				str = sb.toString();
				strLen = str.length();
			}
		}
		return str;
	}

	/**
	 * 小数部あるの数値変換、3桁区切りで返します.<br>
	 * 「#,##0.00」型の数字をフォーマットする。
	 *
	 * @param pVal
	 *            変換元文字列
	 * @return　数値変換
	 * @auther 2012/06/14 hiSoft Yitao
	 */
	public static String formatDecimal(String pVal) {
		BigDecimal dec = ConvUtil.convToDec(pVal);

		DecimalFormat df1 = new DecimalFormat("#,##0.00");
		return df1.format(dec);
	}

	/**
	 * 半角チェック. <br>
	 *
	 * @param value
	 *            String 条件
	 * @return true, <br>
	 *         false
	 */
	public static boolean checkHalf(String value) {

		String str = null;
		try {
			for (int i = 0; i < value.length(); i++) {
				str = String.valueOf(value.charAt(i));
				if (str.getBytes("MS932").length >= 2) {
					return false;
				}
			}
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	/**
	 * 半角数値のチェックを行う
	 *
	 * @param value
	 *            判定する文字列
	 * @param pHyphenFlg
	 *            マイナスフラグ
	 * @return　true:正常　false:不正
	 */
	public static boolean checkHanNum(String value, boolean pHyphenFlg) {
		final String HANKAKU_NUM_MINUS = "^-?\\d+$";
		final String HANKAKU_NUM = "[^0-9]+";
		if (pHyphenFlg) {
			return value.matches(HANKAKU_NUM_MINUS);
		} else {

			return !value.matches(HANKAKU_NUM);
		}
	}

	/**
	 * 数字チェック. <br>
	 *
	 * @param value
	 *            String 条件
	 * @return true, <br>
	 *         false
	 */
	public static boolean isNumeric2(String value) {

		if (value == null || value.equals("")) {
			return false;
		}

		if (!checkHalf(value)) {
			return false;
		}

		try {
			Double.parseDouble(value);
		} catch (Exception pe) {
			return false;
		}

		return true;
	}

	/**
	 * 字符串是数值(含小数)的判定方法。
	 *
	 * @param str 字符串
	 * @return 数值(含小数) true
	 */
	public static boolean isNumeric(String str) {
		if (StringUtils.isEmpty(str)) {
			return false;
		}
		Pattern p = Pattern.compile("^\\d+(\\.\\d+)?$");
		Matcher m = p.matcher(str);
		return m.find();
	}

	/**
	 * 郵便番号チェック. <br>
	 *
	 * @param value
	 *            郵便番号
	 * @return true, <br>
	 *         false
	 */
	public static boolean isZip(String value) {
		if (isEmpty(value)) {
			return false;
		}
		Pattern pattern = Pattern.compile("\\d{3}-\\d{4}");
		Matcher matcher = pattern.matcher(value);
		return matcher.matches();
	}

	/**
	 * 電話番号チェック. <br>
	 *
	 * @param value
	 *            電話番号
	 * @return true, <br>
	 *         false
	 */
	public static boolean isPhoneNum(String value) {
		if (isEmpty(value)) {
			return false;
		}
		Pattern pattern = Pattern.compile("\\d{2}-\\d{4}-\\d{4}");
		Matcher matcher = pattern.matcher(value);
		return matcher.matches();
	}

	/**
	 * 半角英数チェック. <br>
	 *
	 * @param value
	 *            String 条件
	 * @return true, <br>
	 *         false
	 */
	public static boolean fChkNum(String value) {
		if (isEmpty(value)) {
			return false;
		}
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher matcher = pattern.matcher(value);
		return matcher.matches();
	}

	/**
	 * 文字数を取得 <br>
	 *
	 * @param strValue
	 *            条件
	 * @return Interger
	 */
	public static Integer checkLength(String strValue) {
		if (strValue == null || strValue.equals("")) {
			return 0;
		}

		return strValue.getBytes().length;

	}

	/**
	 * 個別ログイン権限チェック <br>
	 *
	 * @param roleTable
	 *            権限テーブル（カンマ区切り）
	 * @param role
	 *            チェック対象権限
	 * @return 権限あり：true/権限なし：false
	 */
	public static boolean roleCheck(String roleTable, String role) {
		if (roleTable == null || roleTable.equals("")) {
			return false;
		}
		for (String allowRole : roleTable.split(",")) {
			if (allowRole.equals(role)) {
				return true;
			}
		}
		return false;
	}

	private static String DIV_ITEM_SPEC1 = "・";
	private static String DIV_ITEM_SPEC2 = "／";

	/**
	 * 文字列から無効な文字を削除した結果を返す関数
	 *
	 * @param str
	 * @return strRtn
	 */
	public static String ignoreString(String str) {
		if (null == str) {
			return "";
		}
		String strRtn = str.replace("%", "");
		strRtn = strRtn.replace("^", "");
		strRtn = strRtn.replace("*", "");
		strRtn = strRtn.replace("(", "");
		strRtn = strRtn.replace(")", "");
		strRtn = strRtn.replace("'", "");
		strRtn = strRtn.replace("[", "");
		strRtn = strRtn.replace("]", "");
		// strRtn = strRtn.replace("<", "");
		// strRtn = strRtn.replace(">", "");
		strRtn = strRtn.replace("\"", "");
		strRtn = strRtn.replace(",", "");
		strRtn = strRtn.replace("\t", "");
		return strRtn;
	}

	/**
	 * 当関数はアップロード機能(VB)へ移植しているため、更新時はアップロード担当者まで連絡してください。
	 *
	 * @param str
	 * @return sqlSet
	 */
	public static String getSqlSet(String str) {
		String sqlSet = "";
		if ("true".equals(str)) {
			sqlSet = "1";
		}
		if ("false".equals(str)) {
			sqlSet = "0";
		}
		if (str.isEmpty()) {
			sqlSet = "NULL";
		}

		str = ignoreString(str.trim());
		if ("".equals(str)) {
			sqlSet = "NULL";
		} else {
			sqlSet = "'" + str + "'";
		}
		return sqlSet;
	}

	/**
	 * MS932文字列としてのLenB.
	 *
	 * @param s
	 * @return int
	 */
	public static int str2LenBSJIS(String s) {
		if (isEmpty(s)) {
			return 0;
		} else {
			try {
				return s.getBytes("MS932").length;
			} catch (UnsupportedEncodingException ex) {
				return 0;
			}
		}
	}

	/**
	 * 文字列を比較してマッチした箇所を取得
	 *
	 * @param pExpression1
	 *            検索対象
	 * @param pExpression2
	 *            検索先
	 * @return マッチした文字列
	 */
	public static String getMatchStr(String pExpression1, String pExpression2) {

		String wFind = "";
		String wMatch = "";
		for (int i = 0; i < pExpression1.length(); i++) {

			for (int j = 2; j <= pExpression1.length(); j++) {

				wFind = mid(pExpression1, i + 1, j);
				if (pExpression2.indexOf(wFind) >= 0) {
					if (wMatch.length() < wFind.length()) {
						wMatch = wFind;
					}
				}
			}
		}
		return wMatch;
	}

	/**
	 * 文字列の指定された位置から、指定された文字数分の文字列を返します
	 *
	 * @param pValue
	 *            対象値
	 * @param pStartIndex
	 *            開始位置
	 * @param pLength
	 *            取り出す文字数
	 * @return str 指定された位置から指定された文字数分の文字列<br/>
	 *         文字数を超えた場合は、指定された位置からすべての文字列を返します
	 */
	public static String mid(String pValue, int pStartIndex, int pLength) {
		String str = "";
		if (StringUtils.isEmpty(pValue)) {
			return str;
		}
		if (pStartIndex <= 0 || pLength < 0) {
			return str;
		}

		if (pLength == 0 || "".equals(pValue)) {
			return str;
		}
		int wLen = pValue.length();
		if (pStartIndex > wLen) {
			return str;
		}
		if (pStartIndex + pLength > wLen) {
			return pValue.substring((pStartIndex - 1));
		}
		str = pValue.substring((pStartIndex - 1), pStartIndex - 1 + pLength);

		return str;
	}

	/**
	 * 不推荐使用 文字列の指定された位置以降のすべての文字列を返します
	 *
	 * @param pValue
	 *            対象値
	 * @param pStartIndex
	 *            開始位置
	 * @return str 指定された位置以降のすべての文字列
	 */
	public static String mid(String pValue, int pStartIndex) {
		String str = "";
		if (StringUtils.isEmpty(pValue)) {
			return str;
		}
		str = mid(pValue, pStartIndex, pValue.length());
		return str;
	}

	/**
	 * ２項演算を行う。（VB‐擬似 IIf 関数）
	 *
	 * @param pvntExpression
	 * @param pvntTruePart
	 *            引数［vntExpression］が True の返り値
	 * @param pvntFalsePart
	 *            引数［vntExpression］が False の返り値
	 * @return int
	 */
	public static int iIf(boolean pvntExpression, int pvntTruePart,
			int pvntFalsePart) {
		try {
			if (pvntExpression) {
				return pvntTruePart;
			} else {
				return pvntFalsePart;
			}
		} catch (Exception e) {
			return pvntFalsePart;
		}
	}

	/**
	 * ２項演算を行う。（VB‐擬似 IIf 関数）
	 *
	 * @param pvntExpression
	 * @param pvntTruePart
	 *            引数［vntExpression］が True の返り値
	 * @param pvntFalsePart
	 *            引数［vntExpression］が False の返り値
	 * @return String
	 */
	public static String iif(boolean pvntExpression, String pvntTruePart,
			String pvntFalsePart) {
		try {
			if (pvntExpression) {
				return pvntTruePart;
			} else {
				return pvntFalsePart;
			}
		} catch (Exception e) {
			return pvntFalsePart;
		}
	}

	/**
	 * 日付を数値に変換
	 *
	 * @param getStr
	 *            Calendar
	 * @return int
	 */
	public static int dateEditToInt(Calendar getStr) {
		String wNum = "";
		int result = 0;
		if (getStr != null) {
			int year = getStr.get(Calendar.YEAR);
			int month = getStr.get(Calendar.MONTH);
			if (month < 10) {
				wNum = year + "" + "0" + month + "";
			} else {
				wNum = year + "" + month + "";
			}
			int day = getStr.get(Calendar.DATE);
			if (day < 10) {
				wNum = wNum + "0" + day + "";
			} else {
				wNum = wNum + day + "";
			}
			result = Integer.parseInt(wNum);
		} else {
			result = 0;
		}
		return result;
	}

	/**
	 * ASより後ろの部分を取得する
	 *
	 * @param pSQL
	 * @return ASより後ろの部分文字列
	 */
	public static String afterAs(String pSQL) {
		String wWork = null;
		int wInStr;
		wWork = pSQL.toLowerCase();
		wInStr = wWork.indexOf("as");
		if (wInStr > 0) {
			wWork = wWork.substring(wInStr + 4);
		}
		return wWork;
	}

	/**
	 * 数値を加工して文字列へ変換
	 *
	 * @param pExpression
	 *            対象式
	 * @return 整形後の文字列
	 */
	public static String getStrNumber(String pExpression) {
		String wNumber = "";
		if (!StringUtils.isEmpty(pExpression) && pExpression.length() > 0) {
			DecimalFormat df = new DecimalFormat("#,###,##0.00");
			wNumber = df.format(pExpression);
		} else {
			wNumber = "";
		}
		return wNumber;
	}

	/**
	 * 数値を加工して文字列へ変換
	 *
	 * @param pExpression
	 *            対象式
	 * @return 整形後の文字列
	 */
	public static String getStrNumber(String pExpression, String pattern) {
		String wNumber = "";
		if (!StringUtils.isEmpty(pExpression) && !StringUtils.isEmpty(pattern)) {
			DecimalFormat df = new DecimalFormat(pattern);
			wNumber = df.format(pExpression);
		}
		return wNumber;
	}

	/**
	 * 電場番号の妥当性チェックを行う
	 *
	 * @param 判定する文字列
	 * @return true:正常　false:不正
	 */
	public static boolean checkValidityPhone(String str) {
		final String PHONE_REGEX = "^([\\d]{2,5}-[\\d]{1,4}-[\\d]{3,4})$"; // 電話番号用正規表現

		return str.matches(PHONE_REGEX);
	}

	/**
	 * SQLのSELECTリストから"AS"より左側の部分を取得する
	 *
	 * @param str
	 * @return "AS"より左側の文字列
	 */
	public static String getBeforeAs(String str) {
		String rtnStr = "";
		String pStr = str;

		int wintInStr = pStr.toLowerCase().indexOf("as");

		if (wintInStr > 0) {
			rtnStr = str.substring(0, wintInStr - 1);
		}
		return rtnStr;
	}

	/**
	 * 制限バイト数のチェックを行う
	 *
	 * @param 判定する文字列
	 *            , 判定バイト数
	 * @param pLimitByte
	 *            制限バイト数
	 * @return true:正常　false:不正
	 */
	public static boolean checkLength(String pKeyWord, Integer pLimitByte) {
		// 未入力の場合は正常終了
		if (pKeyWord == null || "".equals(pKeyWord)
				|| pKeyWord.trim().length() == 0) {
			return true;
		}

		Integer wByteCount;
		try {
			wByteCount = pKeyWord.getBytes("MS932").length;
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}

		// 制限バイト数との比較
		if (wByteCount > pLimitByte) {
			return false;
		}

		return true;
	}

	/**
	 * メールアドレスの妥当性チェックを行う
	 *
	 * @param 判定する文字列
	 * @return true:正常　false:不正
	 */
	public static boolean checkValidityEmail(String str) {
		boolean rtn;
		final String EMAIL_REGEX = "^([\\w\\.\\-~/]+)@([\\w_\\-]+)\\.([\\w_\\.\\-]*)[a-z][a-z]$"; // Email用正規表現

		// メールアドレスが正常かどうか判定
		Pattern p = Pattern.compile(EMAIL_REGEX);
		Matcher m = p.matcher(str);
		rtn = m.find();

		if (rtn) {
			if (str.indexOf("..") >= 0 || str.indexOf(".@") >= 0) {
				rtn = false;
			}
		}

		return rtn;
	}

	/**
	 * 会員コードを生成する
	 *
	 * @param 会員分類コード
	 *            , 自治体配下会員か
	 * @return 会員コード Or Empty
	 */
	public static String createMemberCode(String str, boolean bool) {
		String head = "";

		if ("01".equals(str) || "05".equals(str)) {
			head = "";
		} else if ("02".equals(str) || "03".equals(str) || "04".equals(str)) {
			head = "D";
		} else if ("06".equals(str) || "09".equals(str) || "96".equals(str)) {
			if (bool) {
				head = "DB";
			} else {
				head = "B";
			}
		} else if ("07".equals(str) || "10".equals(str) || "97".equals(str)) {
			if (bool) {
				head = "DS";
			} else {
				head = "S";
			}
		} else if ("08".equals(str)) {
			if (bool) {
				head = "DA";
			} else {
				head = "A";
			}
		} else {
			return "";
		}
		return createRandomString(head, 8, "");

	}

	/**
	 * 指定した長さのランダムな文字列を生成する
	 *
	 * @param ヘッダー文字列
	 *            , 文字列長, 会員コード用の文字列
	 * @return header + ランダムな文字列
	 */
	public static String createRandomString(String head, Integer length,
			String forMemCode) {
		// 定数
		final int NUM_CHAR = 58;
		String t = "";
		int j = 0;
		String[] v = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b",
				"c", "d", "e", "f", "g", "h", "i", "j", "k", "m", "n", "o",
				"p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A",
				"B", "C", "D", "E", "F", "G", "H", "J", "K", "L", "M", "N",
				"P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };

		for (int i = NUM_CHAR - 1; i >= 0; i--) {
			j = (int) ((i + 1) * Math.random());
			t = v[i];
			v[i] = v[j];
			v[j] = t;
		}

		t = "";
		j = length - head.length();
		for (int i = 1; i <= j; i++) {
			t = t + v[i];
		}

		return head + t;
	}

	/**
	 * 指8桁のパスワードを生成する
	 *
	 * @return ランダムな文字列
	 */
	public static String createPassword() {
		String password = "";
		String wstr;
		boolean bool = true;
		String list01 = "A,B,C,D,E,F,G,H,J,K,L,M,N,P,Q,R,S,T,U,V,W,X,Y,Z";
		String list02 = "a,b,c,d,e,f,g,h,i,j,k,m,n,o,p,q,r,s,t,u,v,w,x,y,z";
		String list03 = "1,2,3,4,5,6,7,8,9";
		List<String> wTableList = new ArrayList<String>();
		List<String> wList01 = new ArrayList<String>();
		List<String> wList02 = new ArrayList<String>();
		List<String> wList03 = new ArrayList<String>();
		wTableList.add("ECK6zkp1");
		wTableList.add("VJR4zvw9");
		wTableList.add("SYW8apz6");
		wTableList.add("XVM2xvp4");
		wTableList.add("KML6jwu8");
		wTableList.add("XYB8sbq6");
		wTableList.add("GNJ7mow6");
		wTableList.add("FMN6cmq7");
		wTableList.add("KNW1kgq3");
		wTableList.add("XYC8qme7");
		wTableList.add("RTU5czq3");
		wTableList.add("ZYG7ebq8");
		wTableList.add("FCK4dhi3");
		wTableList.add("VKN4ayq2");
		wTableList.add("MKS5jps8");
		wTableList.add("UWK6puo7");
		wTableList.add("HGR4jnp2");
		wTableList.add("DFB7zsr8");
		wTableList.add("KNR1cja5");
		wTableList.add("CEG9osp3");
		wTableList.add("BWA6fxw3");
		wTableList.add("MYJ8yzb5");
		wTableList.add("LGP1ehz2");
		wTableList.add("LFS9qgu4");
		wTableList.add("BFJ7tqr3");
		wTableList.add("ZHB2xpw1");
		wTableList.add("ZPS6mcg1");
		wTableList.add("XQP2eix1");
		wTableList.add("DGP4kcn3");
		wTableList.add("MQB8zwh6");
		wTableList.add("CAL4sku9");
		wTableList.add("LDM8vpd9");
		wTableList.add("EKT8ovm5");
		wTableList.add("ECW9uiv8");
		wTableList.add("VWZ8tix1");
		wTableList.add("SAK9etg6");
		wTableList.add("WTU4kzo7");
		wTableList.add("NKE8nxf3");
		wTableList.add("PYA8atj1");
		wTableList.add("MVP2hxi5");
		wTableList.add("ATH3our2");
		wTableList.add("GMN5cri7");
		wTableList.add("LEW2mro3");
		wTableList.add("NCA3sfb2");
		wTableList.add("BPX7yxf1");
		wTableList.add("SWF1zai8");
		wTableList.add("TAU4rcm2");
		wTableList.add("TYW6eyz2");
		wTableList.add("DPN4hiv9");
		wTableList.add("HUY3zcu4");
		wTableList.add("LHX4cuv8");
		wTableList.add("WEH1whf5");
		wTableList.add("JAY6wpb4");
		wTableList.add("PZA6vnk9");
		wTableList.add("QBS3des2");
		wTableList.add("YHV4jfu5");
		wTableList.add("CRY8zvb3");
		wTableList.add("ALQ2boz4");
		wTableList.add("FTY1dsc4");
		wTableList.add("UAL6pvn3");
		wTableList.add("JSU7auz5");
		wTableList.add("LWY6rpv3");
		wTableList.add("ZEN4ckt7");
		wTableList.add("WXE4vim1");
		wTableList.add("NJP4nce9");
		wTableList.add("CYS1bep4");
		wTableList.add("XVL3tya1");
		wTableList.add("WQG4vuz8");
		wTableList.add("WZG3cnm9");
		wTableList.add("FLC2rew6");
		wTableList.add("FPJ2ayw7");
		wTableList.add("LAX2cnu1");
		wTableList.add("JCE2kwc8");
		wTableList.add("RLB1gzm6");
		wTableList.add("KPE2wkm3");
		wTableList.add("HPZ5mtd6");
		wTableList.add("WTP5byh7");
		wTableList.add("CUX9tko2");
		wTableList.add("MQA5kna9");
		wTableList.add("JZU8snx2");
		wTableList.add("JQF2osx9");
		wTableList.add("VBA3cky8");
		wTableList.add("LCV9irp8");
		wTableList.add("VJM8nfk3");
		wTableList.add("CGP8yom9");
		wTableList.add("YXH8ptw7");
		wTableList.add("XHA1dmx3");
		wTableList.add("KDW6vuw1");
		wTableList.add("WRZ3smf1");
		wTableList.add("XUZ9wpq1");
		wTableList.add("FUH7cxu3");
		wTableList.add("WGC6srf7");
		wTableList.add("UTP5ixm1");
		wTableList.add("XJH6nfw3");
		wTableList.add("FTW1qwf6");
		wTableList.add("CBZ9ybd3");
		wTableList.add("SHF4uqj9");
		wTableList.add("PVY3jpv7");
		wTableList.add("JRQ7zis8");
		wTableList.add("XFH6mkv1");
		wTableList.add("VDP7zfm5");
		wTableList.add("EQJ3qyk1");
		wTableList.add("ZLG6xzy1");
		wTableList.add("QNG3wmv5");
		wTableList.add("BMC3hdm4");
		wTableList.add("ASV9gtb8");
		wTableList.add("JBS9yug1");
		wTableList.add("NZQ2ptb3");
		wTableList.add("DQS8xuk7");
		wTableList.add("ESX4oqv7");
		wTableList.add("AEJ1vsp2");
		wTableList.add("MCT7jhg6");
		wTableList.add("YQS2kia8");
		wTableList.add("WDV3snu2");
		wTableList.add("QYX4bup7");
		wTableList.add("KUQ7fmr6");
		wTableList.add("MFH4zyv1");
		wTableList.add("GRU5kjh2");
		wTableList.add("YQU4xge2");
		wTableList.add("PDX1dnu4");
		wTableList.add("GWS2osg8");
		wTableList.add("KGV1xpz5");
		wTableList.add("EYZ1eob3");
		wTableList.add("UDR1rvt4");
		wTableList.add("QHF2uhq8");
		wTableList.add("CGZ2ado1");
		wTableList.add("ZWL7rte2");
		wTableList.add("DFM4toy6");
		wTableList.add("DAQ8vha5");
		wTableList.add("KHG4mzx5");
		wTableList.add("LCM7reh6");
		wTableList.add("XSP4anm6");
		wTableList.add("BGW6qch3");
		wTableList.add("ASE4chz9");
		wTableList.add("PEU1wog7");
		wTableList.add("NTR2dnc5");
		wTableList.add("NZK7czx2");
		wTableList.add("MDE4cnu3");
		wTableList.add("XFL1mjy6");
		wTableList.add("WJX2hkg8");
		wTableList.add("PSU2njt8");
		wTableList.add("PXT3pgi7");
		wTableList.add("SEW2yar4");
		wTableList.add("SNR3ptc8");
		wTableList.add("GBM5akj7");
		wTableList.add("FZM3xmy7");
		wTableList.add("JYM1ypt9");
		wTableList.add("SBW8qra6");
		wTableList.add("YQP8bvx5");
		wTableList.add("UQB9rbc5");
		wTableList.add("GVX7nyo5");
		wTableList.add("GXQ1pik7");
		wTableList.add("WUJ5cnq7");
		wTableList.add("WZC7ikb9");
		wTableList.add("HWP3xem7");
		wTableList.add("UHZ6tbu2");
		wTableList.add("XKH5fmj1");
		wTableList.add("XKM1txb4");
		wTableList.add("LEF7duj6");
		wTableList.add("WRD7xbg4");
		wTableList.add("UTL8moa7");
		wTableList.add("TCH7urx1");
		wTableList.add("SQL5cxq1");
		wTableList.add("WBD2zym6");
		wTableList.add("XLU6axb9");
		wTableList.add("GFQ7xwc9");
		wTableList.add("PUN4jze6");
		wTableList.add("HNF3hor4");
		wTableList.add("RYF2uhc7");
		wTableList.add("PNW6zab2");
		wTableList.add("RVQ2aqj8");
		wTableList.add("DEV7uai6");
		wTableList.add("NXF6xiq3");
		wTableList.add("JTD4ybo3");
		wTableList.add("LCS5ipe9");
		wTableList.add("XEM6pum5");
		wTableList.add("MRA8gas7");
		wTableList.add("UQM3vmf5");
		wTableList.add("CZH6pjn8");
		wTableList.add("BRA6zxt5");
		wTableList.add("LZX2iea9");
		wTableList.add("JNZ9mdv2");
		wTableList.add("FQM3gwr5");
		wTableList.add("XSQ8iue2");
		wTableList.add("UYQ7icz1");
		wTableList.add("ECQ5moz2");
		wTableList.add("SRT3cxq4");
		wTableList.add("LFU7yhg8");
		wTableList.add("BYM7erf8");
		wTableList.add("MXZ7iqg2");
		wTableList.add("SAY7vka2");
		wTableList.add("JGH3xsj1");
		wTableList.add("UFL7bex8");
		wTableList.add("TJY8pjy2");
		wTableList.add("GZL3fkr4");
		wTableList.add("FBU5dpu1");
		wTableList.add("CYM6bwq4");
		wTableList.add("SNX2wkt4");
		wTableList.add("FTL7qby4");
		wTableList.add("UPT8kfx6");
		wTableList.add("MLQ5etr3");
		wTableList.add("NYT8xov5");
		wTableList.add("JYB5iqp6");
		wTableList.add("ELQ7dtq8");
		wTableList.add("YEB9wzp7");
		wTableList.add("QYP1xmo2");
		wTableList.add("RUA1dpw4");
		wTableList.add("ALZ3xod6");
		wTableList.add("YDX9cyt7");
		wTableList.add("EWF4zsb8");
		wTableList.add("YSW5mpu2");
		wTableList.add("KZP3pvt7");
		wTableList.add("FNU3sep9");
		wTableList.add("QJE5kob8");
		wTableList.add("YHX1oui7");
		wTableList.add("VPR5ysf7");
		wTableList.add("AYN9xpd3");
		wTableList.add("KLR5jak1");
		wTableList.add("SVF4ofe7");
		wTableList.add("LDJ9ebp5");
		wTableList.add("BYD3bah2");
		wTableList.add("EUY2gzq8");
		wTableList.add("SZQ3uiv4");
		wTableList.add("SZD3jak9");
		wTableList.add("ERP1wrv6");
		wTableList.add("DPH5doq1");
		wTableList.add("WNQ3nxd6");
		wTableList.add("FTE5oxh9");
		wTableList.add("EVU5bxr9");
		wTableList.add("CLD2whj1");
		wTableList.add("KBX4oiz8");
		wTableList.add("KWG5snk1");
		wTableList.add("PTQ8tpi9");
		wTableList.add("CVQ2pqo9");
		wTableList.add("HLF9ehr2");
		wTableList.add("EWB4huk2");
		wTableList.add("ALP4acg6");
		wTableList.add("TGY8mnx6");
		wTableList.add("DGE8exw7");
		wTableList.add("QUA8gyk9");
		wTableList.add("VRT4fhw9");
		wTableList.add("DGK3pgo2");
		wTableList.add("JHX5epd6");
		wTableList.add("USZ2djs3");
		wTableList.add("NBV6cyf2");
		wTableList.add("PEX8dtm1");
		wTableList.add("UBM5oks4");
		wTableList.add("QDT9ohr3");
		wTableList.add("JSR2jco5");
		wTableList.add("NGW2dmv7");
		wTableList.add("YGW7xuf5");
		wTableList.add("FQU8gus3");
		wTableList.add("LME7wcs4");
		wTableList.add("PYA4dga6");
		wTableList.add("CUL7bep4");
		wTableList.add("QHY1zuk3");
		wTableList.add("DFC2hqb3");
		wTableList.add("KRM9oue5");
		wTableList.add("BME7ihm5");
		wTableList.add("ZDR7ieh1");
		wTableList.add("CZX3rsb5");
		wTableList.add("ZPL3nry1");
		wTableList.add("VEK4sdt7");
		wTableList.add("MUB2wup8");
		wTableList.add("XFV7jwh5");
		wTableList.add("VQW5jsb2");
		wTableList.add("KQM5xag8");
		wTableList.add("SHP7ygs1");
		wTableList.add("HFD6npr9");
		wTableList.add("EHB4uqr5");
		wTableList.add("YZW5axf7");
		wTableList.add("ASF1nvq9");
		wTableList.add("QZB1pzc6");
		wTableList.add("RQB6qiz5");
		wTableList.add("LFU9hcq3");
		wTableList.add("WEY1ion3");
		wTableList.add("QEA7qxe2");
		wTableList.add("RPK1aeo3");
		wTableList.add("JMU7mno4");
		wTableList.add("HKS6hia9");
		wTableList.add("PXE4jzf5");
		wTableList.add("PTJ5bsv7");
		wTableList.add("NYF2due7");
		wTableList.add("RMG7xjk4");
		wTableList.add("FJQ7jrs9");
		wTableList.add("BQD7mvp1");
		wTableList.add("JDE1uzm9");
		wTableList.add("VJD4niu2");
		wTableList.add("KAL4hge8");
		wTableList.add("DXE5syp2");
		wTableList.add("KYZ5agx7");
		wTableList.add("LAF2xpo8");
		wTableList.add("JQS1vyn9");
		wTableList.add("EJS6qbp4");
		wTableList.add("ZHJ9sdy7");
		wTableList.add("YFH2jce8");
		wTableList.add("ZEY2qmz4");
		wTableList.add("HKE3zoj4");
		wTableList.add("CAN7zdc1");
		wTableList.add("MGU9rgc2");
		wTableList.add("CMG6dhm4");
		wTableList.add("PXD4bpc9");
		wTableList.add("ZFK4ujy2");
		wTableList.add("DGQ5kwr8");
		wTableList.add("UTE3yrv6");
		wTableList.add("WJH5rye7");
		wTableList.add("GJH7mzh8");
		wTableList.add("NYH2txg5");
		wTableList.add("FUV7tzn6");
		wTableList.add("NLS8dxr9");

		while (bool) {
			wList01 = decodeCSVData(list01);
			for (int i = wList01.size() - 1; i >= 1; i--) {
				// wIndex = Convert.ToInt16(Int((wCnt + 1) * Rnd()))
				int index = (int) ((i + 1) * Math.random());
				wstr = wList01.get(i);
				wList01.set(i, wList01.get(index));
				wList01.set(index, wstr);
			}

			wList02 = decodeCSVData(list02);
			for (int i = wList02.size() - 1; i >= 1; i--) {
				// wIndex = Convert.ToInt16(Int((wCnt + 1) * Rnd()))
				int index = (int) ((i + 1) * Math.random());
				wstr = wList02.get(i);
				wList02.set(i, wList02.get(index));
				wList02.set(index, wstr);
			}

			wList03 = decodeCSVData(list03);
			for (int i = wList03.size() - 1; i >= 1; i--) {
				// wIndex = Convert.ToInt16(Int((wCnt + 1) * Rnd()))
				int index = (int) ((i + 1) * Math.random());
				wstr = wList03.get(i);
				wList03.set(i, wList03.get(index));
				wList03.set(index, wstr);
			}

			password = wList01.get(0) + wList01.get(1) + wList01.get(2)
					+ wList03.get(0) + wList02.get(1) + wList02.get(2)
					+ wList02.get(3) + wList03.get(1);

			// パスワードテーブルと同一の値が無いかチェックする
			bool = false;
			for (int i = 0; i < wTableList.size(); i++) {
				if (wTableList.get(i).equals(password)) {
					bool = true;
					break;
				}
			}
		}

		return password;
	}

	/**
	 * CSV形式のデータから文字列Collectionを作成
	 *
	 * @param CSV形式
	 *            （string）
	 * @return 文字列Collection
	 */
	public static List<String> decodeCSVData(String pCSVData) {
		return decodeSeparateData(pCSVData, ",");
	}

	/**
	 * 指定した区切りデータから文字列Collectionを作成
	 *
	 * @param 指定した区切りデータ
	 * @return 文字列Collection
	 */
	public static List<String> decodeSeparateData(String pCSVData, String regex) {
		List<String> rtnList = new ArrayList<String>();
		String[] strData;

		// 配列に格納
		strData = pCSVData.split(regex);

		// 件数分ループ
		rtnList.addAll(Arrays.asList(strData));

		return rtnList;
	}

	/**
	 * 数値を日付に変換
	 *
	 * @param str
	 * @return StrinDate mm/dd
	 */
	public static String editDate(String str) {

		String strRtn = "";
		if (str.length() == 8) {
			strRtn = str.substring(5, 6) + str.substring(7, 8);
		}
		return strRtn;
	}

	/**
	 * 数値を日付に変換(西暦付)
	 *
	 * @param str
	 * @return StrinDate yyyy/mm/dd
	 */
	public static String editDate2(String str) {
		String strRtn = "";
		if (str.length() == 8) {
			// EditDate2 = MID(get_str,1,4) & "/" & MID(get_str,5,2) & "/" &
			// MID(get_str,7,2)
			strRtn = str.substring(1, 4) + str.substring(5, 6)
					+ str.substring(7, 8);
		}
		return strRtn;

	}

	/**
	 * 空文字は　"　"　へ変換
	 *
	 * @param get_str
	 *            String
	 * @param get_len
	 *            Integer
	 * @return strRtn
	 */
	public static String chkStr(String text, int length) {
		String strRtn = "";

		if (!"".equals(text.trim())) {
			if (length > 0) {
				strRtn = text.trim().substring(0, length);
			} else {
				strRtn = text.trim();
			}
		} else {
			strRtn = " ";
		}

		return strRtn;
	}

	/**
	 * 式 expression が NULL, 長さ 0 の文字列を参照するとき式 replace に置換します．
	 *
	 * @param expression
	 *            Object 式
	 * @param replace
	 *            Object 置換する式
	 * @return strRet 置換値
	 */
	public static String formatFields(Object expression, Object replace) {
		String strRet = "";
		if (expression == null) {
			strRet = String.valueOf(replace);
		} else {
			if (String.valueOf(expression).trim().length() > 0) {
				strRet = String.valueOf(expression).trim();
			} else {
				strRet = String.valueOf(replace);
			}
			strRet = String.valueOf(expression).trim();
		}
		return strRet;
	}

	/**
	 * 指定するフォーマットに変換する．
	 *
	 * @param value
	 *            Object 式
	 * @param pattern
	 *            String フォーマット
	 * @return strRet フォーマット値
	 */
	public static String formatNumber(Object value, String pattern) {
		String strRet = "";
		if (isEmpty(ConvUtil.convToString(value))) {
			return strRet = "";
		}
		int dotIdx = pattern.indexOf(".");
		if (dotIdx > 0) {
			String dot = pattern.substring(dotIdx + 1);
			dotIdx = dot.length();
		} else {
			dotIdx = 0;
		}
		strRet = String.format("%1$,." + dotIdx + "f",
				Double.parseDouble(ConvUtil.convToString(value)));
		return strRet;
	}

	public static String formatNumber(Object value, int scale) {
		return String.format("%1$,." + scale + "f",
				Double.parseDouble(value.toString()));
	}

	/**
	 * 文字列の左側から文字列を充たす。
	 *
	 * @param str
	 *            入力文字列
	 * @param fillChar
	 *            文字列
	 * @param strLength
	 *            文字列桁数
	 * @return 文字列
	 */
	public static String addCharForStrL(String str, char fillChar, int strLength) {
		int strLen = str.length();
		StringBuilder sb = new StringBuilder();
		if (strLen < strLength) {
			while (strLen < strLength) {
				sb.append(fillChar);
				strLen = str.length() + sb.toString().length();
			}
		}
		sb.append(str);
		return sb.toString();
	}

	/**
	 * 文字列の右側から文字列を充たす。
	 *
	 * @param str
	 *            入力文字列
	 * @param fillChar
	 *            文字列
	 * @param strLength
	 *            文字列桁数
	 * @return 文字列
	 */
	public static String addCharForStrR(String str, char fillChar, int strLength) {
		int strLen = str.length();
		if (strLen < strLength) {
			while (strLen < strLength) {
				StringBuilder sb = new StringBuilder();
				sb.append(str).append(fillChar);
				str = sb.toString();
				strLen = str.length();
			}
		}
		return str;
	}

	/**
	 * VBのJoin関数から転換する。<br>
	 * 例えば、join("a,b,c".split(","), "','") ⇒a','b','c
	 *
	 * @param strs
	 * @param s
	 * @return 連接できたstring
	 */
	public static String join(String[] strs, String s) {
		StringBuilder strBuilder = new StringBuilder();
		int n = 0;
		for (String str : strs) {
			strBuilder.append(str);
			if (n < strs.length - 1) {
				strBuilder.append(s);
			}
			n += 1;
		}
		return strBuilder.toString();
	}

	public static final char KATAKANA_SMALL_A = 12449;
	public static final char HIRAGANA_NN = 12435;
	public static final char HIRAGANA_SMALL_A = 12353;
	public static final char KATAKANA_NN = 12531;

	/**
	 * ひらがな文字列 から カタカナ文字列 転換する。
	 *
	 * @param ひらがな
	 * @return カタカナ
	 */
	public static String kanaToGana(String s) {
		StringBuilder sb = new StringBuilder(s);
		for (int i = 0; i < sb.length(); i++) {
			char c = sb.charAt(i);
			if (c >= KATAKANA_SMALL_A && c <= KATAKANA_NN) {
				sb.setCharAt(i,
						(char) (c - KATAKANA_SMALL_A + HIRAGANA_SMALL_A));
			}
		}
		return sb.toString();
	}

	/**
	 * カタカナ文字列 から ひらがな文字列 転換する。
	 *
	 * @param カタカナ
	 * @return ひらがな
	 */
	public static String ganaToKana(String s) {
		StringBuilder sb = new StringBuilder(s);
		for (int i = 0; i < sb.length(); i++) {
			char c = sb.charAt(i);
			if (c >= HIRAGANA_SMALL_A && c <= HIRAGANA_NN) {
				sb.setCharAt(i,
						(char) (c - HIRAGANA_SMALL_A + KATAKANA_SMALL_A));
			}
		}
		return sb.toString();
	}

	/**
	 * Object => 0
	 *
	 * @param obj
	 *            変換したいデータ
	 * @return 変換したデータ／０
	 * @author hisoft 2011/09/28 Limingxu
	 */
	public static double nullToZero(Object obj) {
		double rtn = 0;
		if (obj == null) {
			return rtn;
		} else {
			try {
				rtn = Double.parseDouble(obj.toString());
			} catch (Exception e) {
				rtn = 0;
			}
		}
		return rtn;
	}

	/**
	 * 日時をチェックする. <br>
	 *
	 * @param strDate
	 *            String 日時
	 * @param mask
	 *            String 日付フォーマット
	 * @return 正しい日時の場合、TRUEを返す。
	 * @author 2011/10/08 hiSoft Yitao
	 */
	public static boolean isDateTime(String strDate, String mask) {
		if (StringUtils.isEmpty(strDate)) {
			return false;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(mask);
		Date date = null;
		try {
			date = dateFormat.parse(strDate);
		} catch (ParseException pe) {
			return false;
		}

		String strdate = dateFormat.format(date);
		if (!strdate.equals(strDate)) {
			return false;
		}

		return true;
	}

	/**
	 * 指定された桁数の空白文字列を取得する。
	 *
	 * @param cnt
	 *            空白桁数
	 * @return 指定された桁数の空白文字列
	 */
	public static String space(int cnt) {
		StringBuilder strSpace = new StringBuilder("");
		for (int i = 0; i < cnt; i++) {
			strSpace.append(" ");
		}
		return strSpace.toString();
	}

	/**
	 * ランダムな10桁の文字列を生成する。
	 *
	 * @return ランダムな10桁の文字列
	 */
	public static String getRndStr10() {
		StringBuilder str = new StringBuilder("");

		int modeBack = 0;
		int mode = 0;
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				Random ran = new Random();
				mode = (int) ((3 - 1 + 1) * ran.nextDouble() + 1);
				if (mode != modeBack) {
					modeBack = mode;
					break;
				}
			}
			Random ran = new Random();
			switch (mode) {
			// 大文字生成
			case 1:
				str.append((char) ((int) ((90 - 65 + 1) * ran.nextDouble() + 65)));
				break;
			// 小文字生成
			case 2:
				str.append((char) (int) ((122 - 97 + 1) * ran.nextDouble() + 97));
				break;
			// 数値生成
			case 3:
				str.append(String.valueOf((int) ((9 - 1 + 1) * ran.nextDouble() + 1)));
				break;
			default:
				break;
			}
		}
		String rtn = str.toString();
		// 万が一10桁に達していない場合は“0”で埋める
		if (rtn.length() < 10) {
			rtn = StringUtils.leftPad(rtn, 10, '0');
		}
		return rtn;
	}

	/**
	 * 文字列の左端から指定された文字数分の文字列を返します。
	 *
	 * @param str
	 *            変換対象となる文字列
	 * @param length
	 *            桁数
	 * @return 左端から指定された文字数分の文字列<br/>
	 *         文字数を超えた場合は、文字列全体を返します
	 */
	public static String leftSubString(String str, int length) {
		if (isNull(str) || length <= 0) {
			return "";
		}
		if (length >= str.length()) {
			return str;
		}
		return str.substring(0, length);
	}

	/**
	 * 文字列の右端から指定された文字数分の文字列を返します。
	 *
	 * @param str
	 *            変換対象となる文字列
	 * @param length
	 *            桁数
	 * @return 右端から指定された文字数分の文字列<br/>
	 *         文字数を超えた場合は、文字列全体を返します
	 */
	public static String rightSubString(String str, int length) {
		if (isNull(str) || length <= 0) {
			return "";
		}
		if (length >= str.length()) {
			return str;
		}
		return str.substring(str.length() - length);
	}

	/**
	 * QueryStringを編集する。
	 *
	 * @param urlAndQuery
	 * @param name
	 * @return QueryString文字列
	 */
	public static String getQueryString(String urlAndQuery, String name) {
		if (isNull(urlAndQuery) || isNull(name)) {
			return "";
		}
		String tQuery = mid(urlAndQuery, urlAndQuery.indexOf("?") + 1);
		if (tQuery.length() != 0) {
			String[] arrQuery = tQuery.split("&");
			for (String strQ : arrQuery) {
				if (!strQ.trim().equals("")) {
					String[] arrNV = strQ.split("=");
					if (arrNV[0].equals(name)) {
						return arrNV[1];
					}
				}
			}
		}
		return "";
	}

	/**
	 * 時刻フォーマットに変換する．
	 *
	 * @param time
	 *            時刻フォーマット
	 * @return strRet 時刻フォーマット値 1:1:1 => 01:01:01 1:01:01 => 01:01:01 1:1 =>
	 *         01:01:00 1:a => "" 1:1:a => ""
	 */
	public static String timeHMSFormat(String time) {

		DecimalFormat df = new DecimalFormat("00");
		String timeList[] = time.split(":");
		String hour = "";
		String minute = "";
		String second = "";

		int intHour = 0;
		int intMinute = 0;
		int intSecond = 0;

		// HH:MM
		if (timeList.length == 2) {
			hour = timeList[0];
			minute = timeList[1];

			if (StringUtils.isDigit(hour)) {
				intHour = Integer.parseInt(hour);
			}
			if (StringUtils.isDigit(minute)) {
				intMinute = Integer.parseInt(minute);
			}

			hour = df.format(Integer.parseInt(hour));
			minute = df.format(Integer.parseInt(minute));

			if (intHour > 24 || intHour < 0) {
				hour = "";
			}
			if (intMinute > 60 || intMinute < 0) {
				minute = "";
			}

			if (!"".equals(hour) && !"".equals(minute)) {
				return hour + ":" + minute + ":00";
			}
		}
		// HH:MM:SS
		if (timeList.length == 3) {
			hour = timeList[0];
			minute = timeList[1];
			second = timeList[2];
			if (StringUtils.isDigit(hour)) {
				intHour = Integer.parseInt(hour);
			}
			if (StringUtils.isDigit(minute)) {
				intMinute = Integer.parseInt(minute);
			}
			if (StringUtils.isDigit(second)) {
				intSecond = Integer.parseInt(second);
			}
			hour = df.format(Integer.parseInt(hour));
			minute = df.format(Integer.parseInt(minute));
			second = df.format(Integer.parseInt(second));

			if (intHour > 24 || intHour < 0) {
				hour = "";
			}
			if (intMinute > 60 || intMinute < 0) {
				minute = "";
			}
			if (intSecond > 60 || intSecond < 0) {
				second = "";
			}

			if (!"".equals(hour) && !"".equals(minute) && !"".equals(second)) {
				return hour + ":" + minute + ":" + second;
			}
		}
		return "";
	}

	/**
	 * 時刻フォーマットに変換する．
	 *
	 * @param time
	 *            時刻フォーマット
	 * @return strRet 時刻フォーマット値 1:1 => 01:01 1:01 => 01:01 1:a => "" 1:1:a => ""
	 */
	public static String timeHMFormat(String time) {

		DecimalFormat df = new DecimalFormat("00");
		String timeList[] = time.split(":");
		String hour = "";
		String minute = "";

		int intHour = 0;
		int intMinute = 0;

		// HH:MM
		if (timeList.length == 2) {
			hour = timeList[0];
			minute = timeList[1];

			if (StringUtils.isDigit(hour)) {
				intHour = Integer.parseInt(hour);
			}
			if (StringUtils.isDigit(minute)) {
				intMinute = Integer.parseInt(minute);
			}

			hour = df.format(Integer.parseInt(hour));
			minute = df.format(Integer.parseInt(minute));

			if (intHour > 24 || intHour < 0) {
				hour = "";
			}
			if (intMinute > 60 || intMinute < 0) {
				minute = "";
			}

			if (!"".equals(hour) && !"".equals(minute)) {
				return hour + ":" + minute;
			}
		}
		return "";
	}

	/**
	 * キーワードを配列で取得する。
	 *
	 * @param keyWord
	 *            キーワード
	 * @return キーワード配列
	 */
	public static String[] getKeyWords(String keyWord) {
		if (isEmpty(keyWord)) {
			return null;
		}
		keyWord = keyWord.replaceAll(" ", "　");
		String[] keyWordArr = keyWord.split("　");
		String[] retArr = new String[keyWordArr.length];
		int i = 0;
		for (String key : keyWordArr) {
			key = ignoreString(key);
			retArr[i] = key.trim();
			i++;
		}
		return retArr;
	}

	/**
	 * 文字列のバイト数を取得する。
	 *
	 * @param src
	 *            文字列
	 * @return バイト数
	 * @author 2012/02/14 hiSoft Yitao
	 */
	public static int getShiftJisBytes(String src) {
		if (StringUtils.isEmpty(src)) {
			return 0;
		} else {
			try {
				return src.getBytes("MS932").length;
			} catch (UnsupportedEncodingException e) {
				// 文字列はShift_JISに変換できない。
				throw new RuntimeException();
			}
		}
	}

	/**
	 * 数値を加工して文字列へ変換。<BR>
	 * 移植元： 関数：GetStrNumber
	 *
	 * @param expression
	 *            対象式
	 * @param numberOfDecimals
	 *            小数点以下桁数
	 * @param delimiter
	 *            区切り文字
	 * @return 整形後の文字列。
	 * @author hiSoft GaoHang 2011/11/26
	 */
	public static String getStrNumber(String expression, int numberOfDecimals,
			boolean delimiter) {
		String formatNumber = "";
		if (!StringUtils.isEmpty(expression)) {
			try {
				if (delimiter) {
					formatNumber = String.format("%1$,." + numberOfDecimals
							+ "f", Double.parseDouble(expression));
				} else {
					formatNumber = String.format("%1$." + numberOfDecimals
							+ "f", Double.parseDouble(expression));
				}
			} catch (NumberFormatException ex) {
				formatNumber = expression;
			}
		} else {
			formatNumber = "&nbsp;";
		}
		return formatNumber;
	}

	/**
	 * IEクライアントバージョン判断する。
	 *
	 * @param sUserAgent
	 * @return IE678 true
	 * @author 2012/12/12 hiSoft Yitao
	 */
	public static boolean isIE678(String sUserAgent) {
		Pattern pattern = Pattern.compile(".*((MSIE)+ [6-8]\\.[0-9]).*");
		Matcher matcher = pattern.matcher(sUserAgent);
		boolean bMatch = matcher.matches();
		return bMatch;
	}

	/**
	 * IEクライアントバージョン判断する。
	 *
	 * @param sUserAgent
	 * @return IE67 true
	 * @author 2012/12/12 hiSoft Yitao
	 */
	public static boolean isIE67(String sUserAgent) {
		Pattern pattern = Pattern.compile(".*((MSIE)+ [6-7]\\.[0-9]).*");
		Matcher matcher = pattern.matcher(sUserAgent);
		boolean bMatch = matcher.matches();
		return bMatch;
	}

	/**
	 * IEクライアントバージョン判断する。
	 *
	 * @param sUserAgent
	 * @return IE678 true
	 * @author 2012/12/12 hiSoft Yitao
	 */
	public static boolean isIE8(String sUserAgent) {
		Pattern pattern = Pattern.compile(".*((MSIE)+ [8]\\.[0-9]).*");
		Matcher matcher = pattern.matcher(sUserAgent);
		boolean bMatch = matcher.matches();
		return bMatch;
	}

	public static String encodeUnicode(final String gbString) {
		char[] utfBytes = gbString.toCharArray();
		String unicodeBytes = "";
		for (int byteIndex = 0; byteIndex < utfBytes.length; byteIndex++) {
			String hexB = Integer.toHexString(utfBytes[byteIndex]);
			if (hexB.length() <= 2) {
				hexB = "00" + hexB;
			}
			unicodeBytes = unicodeBytes + "\\u" + hexB;
		}
		return unicodeBytes;
	}

	public byte[] convertToByteArray(List<?> list) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(baos);
			oos.writeObject(list);
			return baos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/** 获得随机数 */
	public static String randomVerifyCode() {
		List<Integer> list = new ArrayList<Integer>();
		Random random = new Random();
		while (true) {
			int randomNum = random.nextInt(10);
			boolean flag = false;
			for (Integer in : list) {
				if (in == randomNum) {
					flag = true;
					break;
				}
			}
			if (!flag) {
				list.add(randomNum);
			}
			if (list.size() >= 6) {
				break;
			}
		}
		String randomStr = "";
		for (Integer in : list) {
			randomStr += in.toString();
		}
		return randomStr;
	}

	public static BigDecimal digitalDisplay(BigDecimal digital) {
		BigDecimal digitalDisp = new BigDecimal("0");
		if (digital.compareTo(new BigDecimal("0")) == 0) {
			return digital;
		}
		if (digital.compareTo(new BigDecimal("100000000")) >= 0)
			digitalDisp = MathUtils.divide(digital, new BigDecimal("100000000")).setScale(2, 6);
		else if (digital.compareTo(new BigDecimal("10000")) >= 0)
			digitalDisp = MathUtils.divide(digital, new BigDecimal("10000")).setScale(2, 6);
		else {
			digitalDisp = digital;
		}
		return digitalDisp;
	}

	public static String digitalUnitDisplay(BigDecimal digital) {
		String digitalStyleDisp = "元";
		if (digital.compareTo(new BigDecimal("0")) == 0) {
			return digitalStyleDisp;
		}
		if (digital.compareTo(new BigDecimal("100000000")) >= 0)
			digitalStyleDisp = "亿";
		else if (digital.compareTo(new BigDecimal("10000")) >= 0)
			digitalStyleDisp = "万";
		else {
			digitalStyleDisp = "元";
		}
		return digitalStyleDisp;
	}

	public static String getSubString(String str, int length) {
		int count = 0;
		int offset = 0;
		char[] c = str.toCharArray();
		for (int i = 0; i < c.length; ++i) {
			if (c[i] > 256) {
				offset = 2;
				count += 2;
			} else {
				offset = 1;
				++count;
			}
			if (count == length) {
				int countRemain = 0;
				for (int j = i + 1; j < c.length; ++j) {
					if (c[i] > 256)
						countRemain += 2;
					else {
						++countRemain;
					}
				}
				if (countRemain <= 3) {
					return str;
				}
				return str.substring(0, i + 1) + "...";
			}
			if ((count == length + 1) && (offset == 2)) {
				int countRemain = 0;
				for (int j = i + 1; j < c.length; ++j) {
					if (c[i] > 256)
						countRemain += 2;
					else {
						++countRemain;
					}
				}
				if (countRemain <= 3) {
					return str;
				}
				return str.substring(0, i) + "...";
			}
		}
		return str;
	}

	/**
	 * 取得UUID。
	 *
	 * @return UUID
	 */
	public static String getUUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replace("-", "");
	}

	/**
	 * 手机号中间4位用*显示。
	 *
	 * @return 替换后手机号
	 */
	public static String encryptMobile(String mobile) {
		if (mobile == null || "".equals(mobile)) {
			return "";
		} else {
			String ret = mobile.substring(0,3);
			ret = ret + "****";
			ret = ret + mobile.substring(7,11);
			return ret;
		}
	}

	/**
	 * 生产请求流水号的方法。
	 *
	 * @return 请求流水号
	 */
	public static String createRequestNo() {
		String nano = String.valueOf(System.nanoTime());
		return DateUtils.getTimeStamp() + nano.substring(nano.length()-7);
	}

	public static boolean isMoney(String str) {
		String regex = "^[0-9]+(.[0-9]{1,2})?$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		boolean bMatch = matcher.matches();
		return bMatch;
	}

	/**
	 * 字符串替换。
	 *
	 * @return 替换后字符串
	 */
	public static String replaceStr(String strInput, String strBefore, String strAfter) {
		if (strInput == null || "".equals(strInput)) {
			return "";
		} else {
			return strInput.replace(strBefore, strAfter);
		}
	}
}
