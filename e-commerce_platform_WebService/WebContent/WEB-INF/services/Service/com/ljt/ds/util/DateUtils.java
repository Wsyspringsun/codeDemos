package  ljt.ds.util;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.ljt.ds.common.util.ConvUtils;

public final class DateUtils {

    public final static String YEAR = "yyyy";
    public final static String MONTH = "MM";
    public final static String DAY = "dd";
    public final static String YEAR_MONTH = "yyyyMM";
    public final static String MONTH_DAY = "MMdd";
    public final static String DATE = "yyyyMMdd";
    public final static String HOUR = "hh";
    public final static String MINUTE = "mm";
    public final static String SECOND = "ss";
    public final static String HOUR_MINUTE = "hhmm";
    public final static String MINUTE_SECOND = "mmss";
    public final static String TIME = "hhmmss";
    public final static String DATE_TIME = "yyyyMMddhhmmss";
    public final static String YEAR_MONTH_LOCALE = "yyyy-MM";
    public final static String MONTH_DAY_LOCALE = "MM-dd";
    public final static String DATE_LOCALE = "yyyy-MM-dd";
    public final static String DATE_LOCALE_S = "yyyy-M-d";
    public final static String DATE_LOCALE_SS = "yy/MM/dd";
    public final static String HOUR_MINUTE_LOCALE = "hh:mm";
    public final static String MINUTE_SECOND_LOCALE = "mm:ss";
    public final static String TIME_LOCALE = "hh:mm:ss";
    public final static String DATE_TIME_LOCALE = "yyyy-MM-dd HH:mm:ss";
    public final static String DATE_TIME_LOCALE_M = "yyyy-MM-dd HH:mm";
    public final static String DATE_TIME_LOCALE_STAMP = "yyyyMMddHHmmssSSS";
    public final static String DATE_TIME_LOCALE_TIMESTAMP = "yyyy-MM-dd HH:mm:ss";
    public final static String HOUR_MINUTE_S = "HH:mm";
    public final static String DATE_TIME_S = "MM-dd HH:mm";
    public final static String NEN = "年";
    public final static String MON = "周一";
    public final static String TUE = "周二";
    public final static String WED = "周三";
    public final static String THU = "周四";
    public final static String FRI = "周五";
    public final static String SAT = "周六";
    public final static String SUN = "周日";
    public final static String CODE_SAT = "7";
    public final static String CODE_SUN = "1";
    public final static String SLASH = "/";
    public final static String FIRST_DAY = "01";
    public final static String BLACKETS_L = "（";
    public final static String BLACKETS_R = "）";
    public final static String GC_COLOR_RED = "#FF0000"; // 赤
    public final static String GC_COLOR_BLU = "#0000FF"; // 青

    /**
     * コンストラクタです。
     */
    private DateUtils() {
    }

    /**
     * 取得AP时刻
     *
     * @return AP时刻
     */
    public static Date getSystemDate() {
        return new Date();
    }

    /**
     * 取得AP时刻
     *
     * @return AP时刻
     */
    public static Calendar getSystemCalendar() {
        return Calendar.getInstance();
    }

    /**
     * 取得系统时间 <br>
     *
     * @return 系统时间
     */
    public static String getSystemYYYYMMDD() {

    	SimpleDateFormat dateFormat1 = new SimpleDateFormat(DATE_LOCALE);
        Date sysDate = new Date();
        return dateFormat1.format(sysDate);
    }

    /**
     * 取得系统时间 <br>
     *
     * @return 系统时间
     */
    public static String getSystemDateByformart(String pattern) {

    	SimpleDateFormat dateFormat1 = new SimpleDateFormat(pattern);
        Date sysDate = new Date();
        return dateFormat1.format(sysDate);
    }

    /**
     * 取得DB时刻。
     *
     * @return DB时刻
     */
    public static Date getDatabaseDate(String dataSourceName) {
        return getCurrentDateTime(dataSourceName);
    }

    /**
     * 取得DB时刻。
     * @param dataSourceName(jdbc/ReferenceDB)
     * @return Date
     */
    public static Date getCurrentDateTime(String dataSourceName) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            DataSource dataSource = InitialContext.doLookup(dataSourceName);
            con = dataSource.getConnection();
            ps = con.prepareStatement("SELECT SYSTIMESTAMP FROM DUAL");
            rs = ps.executeQuery();
            Timestamp timestamp = null;
            while (rs.next()) {
                timestamp = rs.getTimestamp("SYSTIMESTAMP");
            }
            return new Date(timestamp.getTime());
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqle) {
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException sqle) {
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException sqle) {
                }
            }
        }
    }

    /**
     * 从系统中取得年月日时分秒
     *
     * @return 年月日時分秒の文字列
     */
    public static String getSystemTimeStamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return sdf.format(getSystemDate());
    }

    /**
     * 指定的字符串，日期对象的变换方法
     *
     * @param str 变换对象
     * @param pattern 日期时刻的格式
     * @return 指定的日期格式的变换
     * <pre>convertString2Date("2004-04-05 23:22:30", "yyyy-MM-dd") = 2004-04-05 ;</pre>
     * <pre>convertString2Date("2004-04-05 23:22:30", "yyyy/MM/dd") = 2004/04/05 ;</pre>
     * <pre>convertString2Date("2004-04-05", "yyyy-MM-dd HH:mm:ss") = 2004-04-05 00:00:00 ;</pre>
     */
    public static Date convertString2Date(String str, String pattern) {
        if (str == null || "".equals(str)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(sdf.parse(str.trim()));
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
        return calendar.getTime();
    }

    /**
     * 指定的日期，字符串对象的变换方法
     *
     * @param date 变换对象
     * @param pattern 日期时刻的格式
     * @return 指定格式的日期，字符串的变换
     * <pre>convertString2Date("2004-04-05 23:22:30", "yyyy-MM-dd") = 2004-04-05 ;</pre>
     * <pre>convertString2Date("2004-04-05 23:22:30", "yyyy/MM/dd") = 2004/04/05 ;</pre>
     * <pre>convertString2Date("2004-04-05", "yyyy-MM-dd HH:mm:ss") = 2004-04-05 00:00:00 ;</pre>
     */
    public static String convertDate2String(Date date, String pattern) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * 指定的对象，日期对象的变换方法
     *
     * @param obj 变换对象
     * @return 日期时刻的格式
     */
    public static Date convertObject2Date(Object obj) {
    	String strObj = ConvUtils.convToString(obj);

    	Date tempDate = convertString2Date(strObj, DateUtils.DATE_TIME_LOCALE_TIMESTAMP);

        return tempDate;
    }

    /**
     * 是否是日期的判断
     *
     * @param date 判断对象
     * @param pattern 日期时刻的格式
     * @return True：日期对象、False：非日期对象
     */
    public static boolean isValidDate(Object date, String pattern) {
        if (date == null) {
            return false;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            sdf.setLenient(false);
            sdf.parse(String.valueOf(date));
        } catch (ParseException ex) {
            return false;
        }
        return true;
    }

    /**
     * 取得指定日期的年
     *
     * @param date 日期对象
     * @return 年
     */
    public static String getYear(Date date) {
        if (date == null) {
            return "";
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return String.valueOf(calendar.get(Calendar.YEAR));
    }

    /**
     * 取得指定日期的月
     *
     * @param date 日期对象
     * @return 月份
     */
    public static String getMonth(Date date) {
        if (date == null) {
            return "";
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return String.valueOf(calendar.get(Calendar.MONTH) + 1);
    }

    /**
     * 取得指定日期的天
     *
     * @param date 日期对象
     * @return 日
     */
    public static String getDay(Date date) {
        if (date == null) {
            return "";
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return String.valueOf(calendar.get(Calendar.DATE));

    }

    /**
     * 取得时间-小时。
     *
     * @param date 日期对象
     * @return 小时
     */
    public static String getHour(Date date) {
        if (date == null) {
            return "";
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));

    }

    /**
     * 取得时间-分钟。
     *
     * @param date 日期对象
     * @return 分钟
     */
    public static String getMinute(Date date) {
        if (date == null) {
            return "";
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return String.valueOf(calendar.get(Calendar.MINUTE));

    }

    /**
     * 取得时间-秒
     *
     * @param date 日期对象
     * @return 秒
     */
    public static int getSecond(Date date) {
        if (date == null) {
            return 0;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.SECOND);
    }

    /**
     * 根据参数，累加年
     *
     * @param date 日期对象
     * @param year 需要累加的年数
     * @return 计算后的日期
     */
    public static Date addYear(Date date, int year) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, year);
        return calendar.getTime();
    }

    /**
     * 根据参数，累加月
     *
     * @param date 日期对象
     * @param month 需要累加的月数
     * @return 计算后的日期
     */
    public static Date addMonth(Date date, int month) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, month);
        return calendar.getTime();
    }

    /**
     * 根据参数，累加天数
     *
     * @param date 日期对象
     * @param day 需要累加的天数
     * @return 计算后的日期
     */
    public static Date addDay(Date date, int day) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, day);
        return calendar.getTime();
    }

    /**
     * 取得对象日期的首日
     *
     * @param date 日期对象
     * @return 对象日期的首日
     */
    public static Date getFirstDay(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 取得对象日期的末日
     *
     * @param date 日期对象
     * @return 对象日期的末日
     */
    public static Date getLastDay(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, 1);
        calendar.roll(Calendar.DATE, -1);

        return calendar.getTime();
    }

    /**
     * 根据参数，小时的累加
     *
     * @param date 日期对象
     * @param hour 需要累加的小时
     * @return 计算后的日期
     */
    public static Date addHour(Date date, int hour) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hour);
        return calendar.getTime();
    }

    /**
     * 根据参数，分钟累加
     *
     * @param date 日期对象
     * @param min 需要累加的分钟
     * @return 计算后的日期
     */
    public static Date addMinute(Date date, int min) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, min);
        return calendar.getTime();
    }

    /**
     * 根据参数，秒的累加
     *
     * @param date 日期对象
     * @param min 需要累加的秒
     * @return 计算后的日期
     */
    public static Date addSecond(Date date, int second) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, second);
        return calendar.getTime();
    }

    /**
     * 日期周期
     * @param FROM 日期
     * @param TO   日期
     * @return  h:mm:ss
     */
    public static String dateRemainder(Date t1, Date t2) {
        if (t1 == null || t2 == null) {
            return "";
        }
        long l = (t2.getTime() - t1.getTime()) / 1000;
        l = BigDecimal.valueOf(l).abs().longValue();

        long hh = l / 60 / 60;
        long mm = (l - (hh * 60 * 60)) / 60;
        long ss = l - ((hh * 60 * 60) + (mm * 60));

        long lDay = l / 60 / 60 / 24;

        DecimalFormat df = new DecimalFormat("00");

        if (lDay > 0) {
            hh = hh - (lDay * 24);
        }
        String HH = String.valueOf(hh);

        if (HH.length() > 1) {
            HH = HH.substring(HH.length() - 1, HH.length());
        }
        return HH + ":" + df.format(BigDecimal.valueOf(mm).abs()) + ":" + df.format(BigDecimal.valueOf(ss).abs());

    }

    /**
     * 取得YYYY年MM月的形式
     *
     * @param date 日期对象
     * @return yyyy年MM月
     */
    public static String getYearMonthString(String yearMonth) {
        String rtnYearMonth = "";
        if (!StringUtils.isEmpty(yearMonth)) {
            if (yearMonth.length() == 6) {
                rtnYearMonth = yearMonth.substring(0, 4) + "年" + yearMonth.substring(4) + "月";
            } else if (yearMonth.length() == 4) {
                rtnYearMonth = yearMonth + "年";
            }
        }
        return rtnYearMonth;
    }

    /**
     * 日期(yyyy/mm/dd hh:mm:ss)的变换
     *
     * @param dateValue 日期对象
     * @return yyyy-mm-dd hh:mm:ss"
     * @author 2011-11-01
     */
    public static String dateTimeToStr(Date date) {
        return DateUtils.convertDate2String(date, DATE_TIME_LOCALE);

    }

    /**
     * 从yyyymmdd中取得星期的几的方法
     *
     * @param strDate 入力日期（yyyymmdd）
     * @return 1～７
     * @author 2011/11/01
     */
    public static int getWeekDayIndex(String origDate) {
        int formatDate = 0;
        String strDate = "";
        if (origDate == null) {
            return formatDate;
        }
        strDate = String.valueOf(origDate).trim();
        if ("".equals(strDate) || "0".equals(strDate)) {
            return formatDate;
        }

        if (strDate.length() < 8) {
            return formatDate;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(DATE);
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(sdf.parse(strDate));
            return calendar.get(Calendar.DAY_OF_WEEK);
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }

    }

    /**
     * 取得星期几的方法
     * @return 处理后的字符串
     *
     * @author 2011/11/01
     */
    public static String getWeekDayName(String origDate) {
        switch (getWeekDayIndex(origDate)) {
            case 2:
                return MON;
            case 3:
                return TUE;
            case 4:
                return WED;
            case 5:
                return THU;
            case 6:
                return FRI;
            case 7:
                return SAT;
            case 1:
                return SUN;
            default:
                return "";
        }
    }

    /**
     * 格式化日期
     *
     * @param strVal 入力日期（yyyy、yyyymm、yyyymmdd）
     * @param strSpace 使用的符号(""," ","nbsp;"等)
     * @return 处理后日期
     *
     * @author 2011/11/01
     */
    public static String getYMDformat(String strDate, String strSpace) {
        String wY = "";
        String wM = "";
        String wD = "";

        switch (strDate.length()) {
            case 4:
                wY = strDate;
                break;
            case 6:
                wY = strDate.substring(0, 4);
                wM = strDate.substring(4, 6);
                break;
            case 8:
                wY = strDate.substring(0, 4);
                wM = strDate.substring(4, 6);
                wD = strDate.substring(6, 8);
                break;
            default:
                return "";
        }

        if (wY.length() > 0) {
            wY = wY + NEN;
        }
        if (wM.length() > 0) {
            wM = Integer.valueOf(wM) + "月";
        }
        if (wD.length() > 0) {
            wD = Integer.valueOf(wD) + "日";
        }
        if (wY.length() > 0 && wM.length() > 0) {
            wY = wY + strSpace;
        }
        if (wM.length() > 0 && wD.length() > 0) {
            wM = wM + strSpace;
        }

        return wY + wM + wD;

    }

    /**
     * 曜日色の編集をする
     *
     * @param pDay 入力日
     * @param dvFlg 祝日文字列フラグ("000..."＝1～31バイト・日／"0"＝平日・曜日に従う／"1"＝祝日)
     * @param weekValue 入力週（日～土、1～7）
     * @param strVal 値
     * @return 処理後日付
     *
     * @author 2011/11/01 hiSoft HeYa
     */
    public static String weekColor(int days, String dvFlg, String weekValue, String strVal) {
        String wWeek = "";
        String rtnStr = "";

        if (dvFlg.length() >= days) {
            // 祝日は強制的に"日"とする
//            if (CODE_SUN.equals(dvFlg.substring(days - 1, 1))) {
            if (CODE_SUN.equals(dvFlg.substring(days - 1, days))) {
                wWeek = SUN;
            } else {
                wWeek = weekValue;
            }

            if (SUN.equals(wWeek) || CODE_SUN.equals(wWeek)) {
                rtnStr = GC_COLOR_RED;

            } else if (SAT.equals(wWeek) || CODE_SAT.equals(wWeek)) {
                rtnStr = GC_COLOR_BLU;

            } else {
                rtnStr = "";
            }

        }
        return rtnStr;
    }

    /**
     * yyyymmの加減算をする
     *
     * @param addValue 加減算月
     * @param yearMonth  入力年月（yyyymm）
     * @return 処理後日付
     *
     * @author 2011/11/01 hiSoft HeYa
     */
    public static String ymMonthAdd(int addValue, String yearMonth) {
        String formatDate = "";
        String strDate = "";
        if (yearMonth == null) {
            return formatDate;
        }
        strDate = String.valueOf(yearMonth).trim();
        if ("".equals(strDate) || "0".equals(strDate)) {
            return formatDate;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(YEAR_MONTH);
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(sdf.parse(strDate));
            calendar.add(Calendar.MONTH, addValue);
            formatDate = sdf.format(calendar.getTime());
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
        return formatDate;
    }

    /**
     * yyyymmddの加減算をする
     *
     * @param f 時間間隔(DateAdd関数を参照)
     * @param addValue加減算月
     * @param dateValue 入力年月日（yyyymmdd）
     * @return 処理後日付
     * @author 2011/11/01 hiSoft HeYa
     */
    public static String ymdDateAdd(int f, int addValue, String dateValue) {
        String formatDate = "";
        String strDate = "";
        if (dateValue == null) {
            return formatDate;
        }
        strDate = String.valueOf(dateValue).trim();
        if ("".equals(strDate) || "0".equals(strDate)||strDate.length()!=8) {
            return formatDate;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(DATE);
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(sdf.parse(strDate));
            calendar.add(f, addValue);
            formatDate = sdf.format(calendar.getTime());
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
        return formatDate;
    }

    public static Date dateAdd(Date value, int field, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(value);
        calendar.add(field, amount);
        return calendar.getTime();
    }

    /**
     * yyyymmより月末日を取得する
     *
     * @param yearMonth入力年月(yyyymm）
     * @return 処理後日付
     * @author 2011/11/01 hiSoft HeYa
     */
    // public static Date getMonthDays(String yearMonth) {
    // return DateUtil.getLastDay(DateUtil.convertString2Date(yearMonth, YEAR_MONTH));
    // }
    public static int getMonthDays(String yearMonth) {
        return ConvUtils.convToInt(getDay(DateUtils.getLastDay(DateUtils.convertString2Date(yearMonth, YEAR_MONTH))));
    }

    /**
     * yyyymmdd⇔yyyy/mm/dd的变换
     *
     * @param pFlg 変換flg
     * 1 = yyyymmdd→yyyy-mm-dd
     * 2 = yyyy-m-d→yyyymmdd
     * 3 =yyyymmdd→yyyymmdd
     * 4 = yyyy-m-d→yyyy-mm-dd
     * @param updYearMonth 对象年月日
     * @return 处理后日付
     * @author 2011/11/01
     */
    public static String date8FromTo10(int flg, String updYearMonth) {
        switch (flg) {
            case 1:
                return formatDateTime(updYearMonth, DATE, DATE_LOCALE);
            case 2:
                return formatDateTime(updYearMonth, DATE_LOCALE_S, DATE);
            case 3:
                return updYearMonth;
            case 4:
                return formatDateTime(updYearMonth, DATE_LOCALE_S, DATE_LOCALE);
            default:
                return "";
        }

    }

    /**
     * yyyymm⇔yyyy/mmに変換する
     *
     * @param pFlg  変換フラグ
     * @param yearMonth 入力日期（yyyymm）
     * @return 処理後日付
     * @author 2011/11/01 hiSoft HeYa
     */
    public static String date6FromTo7(int pFlg, String yearMonth) {
        return formatDateTime(yearMonth, YEAR_MONTH, YEAR_MONTH_LOCALE);

    }

    /**
     * yyyymmddを年月日曜表示の編集をする
     *
     * @param inDate  入力日期（yyyymmdd）
     * @param strSpace 使用する空白(""," ","nbsp;"等)
     * @return 処理後日付
     * @author 2011/11/01 hiSoft HeYa
     */
    public static String getYMDWformat(String inDate, String strSpace) {

        String wYMD = getYMDformat(inDate, strSpace); // yyyy、yyyymm、yyyymmddを年月日表示の編集をする
        String wWork = getWeekDayName(inDate); // yyyymmddより曜日を取得する

        if (wWork.length() > 0) {
            wWork = strSpace + BLACKETS_L + wWork + BLACKETS_R;
        }
        return wYMD + wWork;

    }

    /**
     * 引数の文字列を指定されたフォーマットで日付に変換するメソッドです。
     *
     * @param strDate 変換対象のオブジェクト
     * @param origPattern  変換元日付時刻フォーマットパターン
     * @param destPattern  変換先日付時刻フォーマットパターン
     * @return 引数がnullの場合は空、文字列の時は指定されたパターンに従ってフォーマットパターン日付を変換したもの
     */
    public static String formatDateTime(Object origDate, String origPattern, String destPattern) {
        String formatDate = "";
        String strDate = "";
        if (origDate == null) {
            return formatDate;
        }
        strDate = String.valueOf(origDate).trim();
        if ("".equals(strDate) || "0".equals(strDate)) {
            return formatDate;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(origPattern);
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(sdf.parse(strDate));
            sdf.applyPattern(destPattern);
            formatDate = sdf.format(calendar.getTime());
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }

        return formatDate;
    }

    /**
     * 日期格式化
     *
     * @param strVal 入力日期（yyyy-mm-dd）
     * @return 処理後日付
     */
    public static String getYMformat(String strDate) {
        String wY = "";
        String wM = "";
        String wD = "";

        switch (strDate.length()) {
            case 4:
                wY = strDate;
                break;
            case 6:
                wY = strDate.substring(0, 4);
                wM = strDate.substring(4, 6);
                break;
            case 8:
                wY = strDate.substring(0, 4);
                wM = strDate.substring(4, 6);
                wD = strDate.substring(6, 8);
                break;
            default:
                return "";
        }

        if (wY.length() > 0) {
            wY = wY + NEN;
        }
        if (wM.length() > 0) {
            wM = wM + MON;
        }
        if (wD.length() > 0) {
            wD = wD + SUN;
        }

        return wY + wM + wD;


    }

    /**
     * 从日期FROM到日期TO的天数
     *
     * @param dateStrFrom 日期FROM("yyyyMMdd")
     * @param dateStrTo 日期TO("yyyyMMdd")
     * @author 2012/02/16 Jinhui
     *
     * @return int 天数
     * <p>
     * <pre>getDaysIn2Day("20040405", "20040407") = 3 ;</pre>
     * <pre>getDaysIn2Day("20040405", "20040405") = 1 ;</pre>
     * <pre>getDaysIn2Day("20040407", "20040405") = -3 ;</pre>
     */
	public static int getDaysIn2Day(String dateStrFrom, String dateStrTo) {
		int dayCount = 0;
		int daysInYearFrom = 0;
		int daysInYearTo = 0;
		int daysIn2Year = 0;
		int tmpSign = 1;
		int addOne = 1;

		if (dateStrFrom.compareTo(dateStrTo) > 0) {
			String tmpString;
			tmpString = dateStrTo;
			dateStrTo = dateStrFrom;
			dateStrFrom = tmpString;
			tmpSign = -1;
		}

		Calendar calFrom = null;
		Calendar calTo = null;

		try {
			calFrom = subCalendar(dateStrFrom);
			calTo = subCalendar(dateStrTo);
		} catch (Exception ex) {
			return 0;
		}

		int dayInYearFrom = calFrom.get(Calendar.DAY_OF_YEAR);
		int yearFrom = calFrom.get(Calendar.YEAR);

		int totalDaysInYearFrom = subSumDaysInYear(dateStrFrom);

		int DayInYearTo = calTo.get(Calendar.DAY_OF_YEAR);
		int yearTo = calTo.get(Calendar.YEAR);

		// get the days in the yearFrom and in the yearTo
		if (yearFrom == yearTo) {
			daysInYearFrom = DayInYearTo - dayInYearFrom;
			daysInYearTo = addOne;
		} else {
			daysInYearFrom = totalDaysInYearFrom - dayInYearFrom;
			daysInYearTo = DayInYearTo + addOne;
		}

		// get the days between yearFrom and yearTo
		daysIn2Year = subDaysIn2Years(yearFrom, yearTo);

		// get the days in the two given years as return value
		dayCount = tmpSign * (daysInYearFrom + daysInYearTo + daysIn2Year);

		return dayCount;
	}

	/**
	 * 取得Calendar
	 *
	 * @return Calendar
	 * @exception Exception
	 */
	private static Calendar subCalendar(String dateStr) throws Exception {
		if (dateStr.length() < 8) {
			throw new Exception("Date Size Error!!!!");
		}

		Calendar nowCalendar = subFormatCalendar();
		nowCalendar.set(Integer.parseInt(dateStr.substring(0, 4)),
				Integer.parseInt(dateStr.substring(4, 6)) - 1,
				Integer.parseInt(dateStr.substring(6, 8)));

		return nowCalendar;
	}

	/**
	 * 取得Calendar（yyyyMMdd）
	 *
	 * @return Calendar
	 */
	private static Calendar subFormatCalendar() {
		Calendar nowCalendar = Calendar.getInstance();
		nowCalendar.setLenient(false);

		return nowCalendar;
	}

    /**
     * 对象日期年的总天数
     *
     * @param dateStr 日期("yyyyMMdd")
     *
     * @return int 日期数
     */
    private static int subSumDaysInYear(String dateStr) {
        int sumDays = 0;
        if(!isDate(dateStr)) {
        	return sumDays;
        }
        Calendar nowCalendar = subFormatCalendar();
        nowCalendar.set(
            Integer.parseInt(dateStr.substring(0, 4)), Calendar.DECEMBER, 31);
        sumDays = nowCalendar.get(Calendar.DAY_OF_YEAR);

        return sumDays;
    }

    /**
     * 取得年FROM--年TO的日数。
     *
     * @param yearFrom 年FROM("yyyy")
     * @param yearTo 年TO("yyyy")
     *
     * @return int 日付数
     */
    private static int subDaysIn2Years(int yearFrom, int yearTo) {
        int sumDays = 0;

        for (; yearFrom < (yearTo - 1); yearFrom++) {
            sumDays += subSumDaysInYear(String.valueOf(yearFrom + 1));
        }

        return sumDays;
    }

    /**
     * 月首日的判断
     *
     * @param date(yyyy-MM-dd)
     * @return
     */
    public static boolean fIsFirstDay(String dat) {// yyyy-MM-dd
        String month = dat.substring(8, 10);
        if (!"01".equals(month)) {
            return false;
        }
        return true;
    }

    /**
     * 末日的判断
     *
     * @param date（yyyy-MM-dd）
     * @return boolean
     */
    public static boolean fIsLastDay(String dat) {// yyyy-MM-dd
        String strDD = "";
        String month = dat.substring(5, 7);
        int mon = Integer.parseInt(month);
        if (mon == 1 || mon == 3 || mon == 5 || mon == 7 || mon == 8 || mon == 10 || mon == 12) {
            strDD = "31";
        } else if (mon == 4 || mon == 6 || mon == 9 || mon == 11) {
            strDD = "30";
        } else {
            if (isLeapYear(dat)) {
                strDD = "29";
            } else {
                strDD = "28";
            }
        }

        if (!strDD.equals(dat.substring(8, 10))) {
            return false;
        }

        return true;
    }

    /**
     * 闰年的判断 <br>
     *
     * @param strYear
     * @return true, <br>
     *         false
     */
    public static boolean isLeapYear(String strYear) {
        String strTemp = "";
        String month = strYear.substring(0, 4);
        int intYear = Integer.parseInt(month);
        int intTemp = intYear / 4;

        if ((intYear - (4 * intTemp)) == 0) {
            strTemp += "1";
        } else {
            strTemp += "0";
        }

        intTemp = intYear / 100;
        if ((intYear - (100 * intTemp)) == 0) {
            strTemp += "1";
        } else {
            strTemp += "0";
        }

        intTemp = intYear / 400;
        if ((intYear - (400 * intTemp)) == 0) {
            strTemp += "1";
        } else {
            strTemp += "0";
        }

        if (strTemp.equals("100") || strTemp.equals("111")) {
            return true;
        }

        return false;
    }

    /**
     * 时间表示-月日（时分）的表示
     *
     * @param date 判定对象
     * @return MM月DD日
     */
    protected String getMonthDay(String tempDate, boolean timeFlg) {

        if (StringUtils.isEmpty(tempDate) || !DateUtils.isDate(tempDate)) {
            return "";
        }

        Date date = DateUtils.convertString2Date(tempDate, DateUtils.DATE_TIME_LOCALE_TIMESTAMP);

        String month = StringUtils.leftPad(DateUtils.getMonth(date), 2, '0');
        String day = StringUtils.leftPad(DateUtils.getDay(date), 2, '0');

        String monthDay = month + "月" + day + "日";

        String tmpDay = DateUtils.formatDateTime(tempDate, DateUtils.DATE_TIME_LOCALE_TIMESTAMP, DateUtils.DATE);
        String wWork = DateUtils.getWeekDayName(tmpDay);
        monthDay = monthDay + DateUtils.BLACKETS_L + wWork + DateUtils.BLACKETS_R;

        if (timeFlg) {
            String hh = getHour(date);
            String mm = getMinute(date);
            monthDay = monthDay + StringUtils.leftPad(ConvUtils.convToString(hh), 2, '0') + ":"
                    + StringUtils.leftPad(ConvUtils.convToString(mm), 2, '0');

        }
        return monthDay;
    }

    /**
     * 日期的判断
     *
     * @param date 对象
     * @return True：日期，False：非日期
     */
    public static boolean isDate(String date) {
        StringBuilder reg = new StringBuilder("^((\\d{2}(([02468][048])|([13579][26]))-?((((0?");
        reg.append("[13578])|(1[02]))-?((0?[1-9])|([1-2][0-9])|(3[01])))");
        reg.append("|(((0?[469])|(11))-?((0?[1-9])|([1-2][0-9])|(30)))|");
        reg.append("(0?2-?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][12");
        reg.append("35679])|([13579][01345789]))-?((((0?[13578])|(1[02]))");
        reg.append("-?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))");
        reg.append("-?((0?[1-9])|([1-2][0-9])|(30)))|(0?2-?((0?[");
        reg.append("1-9])|(1[0-9])|(2[0-8]))))))");
        Pattern p = Pattern.compile(reg.toString());
        return p.matcher(date).matches();
    }

    public static int daysOfDifference(Date fDate, Date oDate) {

        Calendar aCalendar = Calendar.getInstance();

        aCalendar.setTime(fDate);

        int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);

        aCalendar.setTime(oDate);

        int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);

        return day2 - day1;

     }

	public static Date getDateTime() {
		return getSystemDate();
	}

	/**
	 * 获取前后时间的日差的方法。
	 *
	 * @param stateDate 开始时间
	 * @param endDate 结束时间
	 * @return 前后时间的日差
	 */
	public static int dateDiff(Date stateDate, Date endDate) {
		return (int) ((endDate.getTime() - stateDate.getTime()) / (1000 * 60 * 60 * 24));
	}


	/**
	 * 系统日期的年月日时分秒毫秒的文字列，取得方法。
	 *
	 * @return 年月日时分秒毫秒的文字列
	 */
	public static String getTimeStamp() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return sdf.format(getSystemDate());
	}

	/**
	 * 取得AP系统时间，不包括时分秒。
	 *
	 * @return AP系统时间
	 */
	public static Date getDate() {
		Calendar calendar = getSystemCalendar();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
		return DateUtils.convertString2Date(DateUtils.convertDate2String(calendar.getTime(), "yyyyMMdd"),"yyyyMMdd");
	}
}
