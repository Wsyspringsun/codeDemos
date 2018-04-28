package ljt.ds.util;

import java.io.File;

public class StringUtilities {
	
    // Web page's character set
    private static final String PAGE_CHARSET = "UTF-8";
	
    /**
     * 未入力（Null）的检查 <br>
     * 
     * @param value String 条件
     * @return true：null
     *          <br>
     *          false：not null
     */
    public static boolean isNull(String value) {

        if (value == null || value.length() < 1) {
            return true;
        }
        return false;
    }

    /**
     * 数组类型未入力（Null）的检查 <br>
     * 
     * @param values String 条件
     * @return true：null
     *          <br>
     *          false：not null
     */
    public static boolean isNull(String[] values) {
        if (values == null)
            return true;
        for (int i = 0; i < values.length; i++)
            if (!isNull(values[i])) {
                return false;
            }
        return true;
    }

    /**
     * 删除字符串左侧的（全角／半角）空格. <br>
     * 
     * @param strParam 文字列
     * @return 删除左侧的空格的字符串
     */
    public static String ltrim(String strParam) {
        String strRet = "";

        if (!isNull(strParam)) {
            int i = 0;
            for (i = 0; i < strParam.length(); i++) {
                if ((strParam.charAt(i) != ' ') && (strParam.charAt(i) != '　')) {
                    break;
                }
            }
            strRet = strParam.substring(i);
        }
        return strRet;
    }

    /**
     * 删除字符串右侧的（全角／半角）空格. <br>
     * 
     * @param strParam 文字列
     * @return 删除右侧的空格的字符串
     */
    public static String rtrim(String strParam) {
        String strRet = "";

        if (!isNull(strParam)) {
            int i;
            for (i = strParam.length() - 1; i >= 0; i--) {
                if ((strParam.charAt(i) != ' ') && (strParam.charAt(i) != '　')) {
                    break;
                }
            }
            if (i >= 0) {
                strRet = strParam.substring(0, i + 1);
            }
        }
        return strRet;
    }

    /**
     * 删除字符串两侧的（全角／半角）空格. <br>
     * 
     * @param strParam 文字列
     * @return 删除两侧的空格的字符串
     */
    public static String trim(String strParam) {

        if (!isNull(strParam)) {
            // 删除字符串左侧的（全角／半角）空格
        	strParam = ltrim(strParam);

            // 删除字符串右侧的（全角／半角）空格
        	strParam = rtrim(strParam);
        }

        return strParam;
    }
    
    /**
     * 置换第一个字符. <br>
     * 
     * @param strSource 置换的字符串
     * @param strFrom 检索的参数
     * @param strTo 置换的字符
     * @return 置换完了的字符串
     * @exception 无
     */
    public static String replaceFirst(String strSource, String strFrom,
            String strTo) {
        String strDest = "";
        int intFromLen = strFrom.length();
        int intPos;
        if (strTo == null) {
            strTo = "";
        }
        if ((intPos = strSource.indexOf(strFrom)) != -1) {
            strDest = strDest + strSource.substring(0, intPos);
            strDest = strDest + strTo;
            strSource = strSource.substring(intPos + intFromLen);
        }
        strDest = strDest + strSource;

        return strDest;
    }
    
	/**
	 * 字符串置换
	 * 
     * @param str 置换的字符串
     * @param oldStr 检索的参数
     * @param newStr 置换的字符
     * @return 置换完了的字符串
	 */	
	public static String replaceString(String str, String oldStr, String newStr){
		if (str != null){
			StringBuffer sbf = new StringBuffer();
			int startIndex = 0;
			int endIndex = 0;
			int intEof = str.length();
			while(startIndex < intEof){
				if(str.indexOf(oldStr,startIndex) > -1) {
					endIndex = str.indexOf(oldStr,startIndex);
				} else {
					sbf.append(str.substring(startIndex,str.length()));
					break;
				}
				sbf.append(str.substring(startIndex,endIndex));
				sbf.append(newStr);
				startIndex = endIndex + oldStr.length();
			}
			return sbf.toString();
		}else{
			return null;
		}
	}
	
    /**
     * 最大长度的检查. <br>
     * 
     * @param value String 条件
     * @param len int 条件
     * @return true, <br>
     *         false
     *  
     */
    public static boolean checkMaxLength(String value, int len) {

        if (isNull(value)) {
            return true;
        }

        try {
            if (value.getBytes(PAGE_CHARSET).length > len) {
                return false;
            }
        } catch (Exception e) {
            /* log.debug(e.getMessage()); */
            return false;
        }

        return true;
    }
    
    /**
     * 最小长度的检查. <br>
     * 
     * @param value String 条件
     * @param len int 条件
     * @return true, <br>
     *         false
     *  
     */
    public static boolean checkMinLength(String value, int len) {

        if (isNull(value)) {
            return true;
        }

        try {
            if (value.getBytes(PAGE_CHARSET).length < len) {
                return false;
            }
        } catch (Exception e) {
            /* log.debug(e.getMessage()); */
            return false;
        }

        return true;
    }
    
    /**
     * 半角英文的检查. <br>
     * 
     * @param value String 条件
     * @return true, <br>
     *         false
     */
    public static boolean isEng(String value) {

        if (isNull(value))
            return true;

        char c;
        for (int i = 0; i < value.length(); i++) {
            c = value.charAt(i);
            if (c < '0' || c > '9')
                if (c < 'a' || c > 'z')
                    if (c < 'A' || c > 'Z')
                        return false;
        }
        return true;
    }
    
    /**
     * 数字的检查. <br>
     * 
     * @param value String 条件
     * @return true  数字<br>
     *          false 数字以外
     */
    public static boolean isNum(String value) {

        if (isNull(value))
            return true;

        char c;
        for (int i = 0; i < value.length(); i++) {
            c = value.charAt(i);
            if (c < '0' || c > '9')
                return false;
        }
        return true;
    }
    
    /**
     * 字符串的置换. <br>
     * 
     * @param strSource 文字列
     * @param strFrom 文字列元
     * @param strTo 文字列先
     * @return 置換的文字列
     */
    public static String replace(String strSource, String strFrom, String strTo) {
        String strDest = "";
        int intFromLen = strFrom.length();
        int intPos;
        if (strTo == null) {
            strTo = "";
        }
        while ((intPos = strSource.indexOf(strFrom)) != -1) {
            strDest = strDest + strSource.substring(0, intPos);
            strDest = strDest + strTo;
            strSource = strSource.substring(intPos + intFromLen);
        }
        strDest = strDest + strSource;

        return strDest;
    }
    
    public static String syoriPathLastSign(String path) {
        if (path != null && !"".equals(path)) {
            if (!path.endsWith("\\") || !path.endsWith(File.separator)) {
                path = path + File.separator;
            }
        }
        return path;
    }
}
