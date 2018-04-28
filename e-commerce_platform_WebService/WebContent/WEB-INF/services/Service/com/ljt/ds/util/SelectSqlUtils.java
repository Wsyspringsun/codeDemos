package ljt.ds.util;

public class SelectSqlUtils {

	/**
	 * 获得查询用户基本信息SQL
	 *
	 * @param 用户ID
	 *
	 * @return 查询用户基本信息SQL
	 * */
	public static String getUserInfoSelSql(String userId) {
		StringBuilder wSql = new StringBuilder();
		wSql.append(" SELECT IFNULL(uu.FILE_URL,'/web/cn/images/tx.gif') AS FILE_URL,t.LIMIT_NAME, ");
		wSql.append(" u.USER_ID,CASE WHEN uv.VERIFY_FLG='1' THEN 1  ELSE 0 END AS VIP_FLG,");
		wSql.append(" CASE WHEN uv.VIP_TYPE='04' THEN 'VIP' WHEN uv.VIP_TYPE='05' THEN '高级VIP'  ELSE '普通用户' END AS VIP_NAME,");
		wSql.append(" ua.VIDEO_FLG,ua.IDENTITY_FLG,ua.MOBILE_FLG,UM.VERIFY_FLG AS MAIL_FLG,DATE_FORMAT(u.INS_DATE,'%Y-%m-%d') AS REGDATE,");
		wSql.append(" mr.MESSAGECOUNT,IFNULL(t.TYPE_IMG_FILE_ID,0) AS TYPE_IMG_FILE_ID, ");
		wSql.append(" CU.CUSTOMER_QQ_NUMBER,AU.ADMIN_USER_NAME,ae.VERIFY_FLG AS EDUCATION_FLG ");
		wSql.append(" FROM USERS u ");
		wSql.append(" LEFT JOIN APPROVE_EDUCATION ae ON ae.USER_ID=u.USER_ID ");
		wSql.append(" LEFT JOIN USERS_UPFILES uu ON uu.FILE_ID = u.USER_HEAD_FILE_ID ");
		wSql.append(" LEFT JOIN USERS_VIP uv ON uv.USER_ID=u.USER_ID AND DATE_FORMAT(sysdate(), '%Y-%m-%d') BETWEEN uv.START_DATE AND uv.END_DATE ");
		wSql.append(" LEFT JOIN USERS_ATTESTATION ua ON ua.USER_ID=u.USER_ID ");
		wSql.append(" LEFT JOIN USERS_MAIL UM ON UM.USER_ID = u.USER_ID ");
		wSql.append(" LEFT JOIN (SELECT USER_ID_TO AS USER_ID,COUNT(*) AS MESSAGECOUNT FROM MESSAGE_RS WHERE OPEN_FLG='0' AND DEL_FLG='0' GROUP BY USER_ID_TO) mr ON mr.USER_ID = u.USER_ID ");
		wSql.append(" LEFT JOIN (select cl.LIMIT_NAME,uc.USER_ID,uu.FILE_URL AS TYPE_IMG_FILE_ID FROM USERS_CREDIT uc ");
		wSql.append(" LEFT JOIN CREDIT_RANK cr ON IFNULL(uc.CREDIT,0) BETWEEN cr.POINT_START AND cr.POINT_END LEFT JOIN USERS_UPFILES uu ON uu.FILE_ID = cr.TYPE_IMG_FILE_ID");
		wSql.append(" LEFT JOIN CREDIT_LIMIT cl ON IFNULL(uc.CREDIT,0) BETWEEN cl.POINT_START AND cl.POINT_END ) t ON u.USER_ID=t.USER_ID ");
		wSql.append(" LEFT JOIN CUSTOMER_SERVICE CU ON CU.ADMIN_USER_ID=uv.CUSTOM_SERVICE_ID ");
		wSql.append(" LEFT JOIN ADMIN_USERS AU ON AU.ADMIN_USER_ID = uv.CUSTOM_SERVICE_ID ");
		wSql.append(" WHERE u.USER_ID='").append(userId).append("' ");
		return wSql.toString();
	}

	/**
	 * 检索用户输入的ID和EMAIL是否存在
	 *
	 * @param 用户ID或者EMAIL
	 *
	 * @return 查询用户基本信息SQL
	 *
	 * */
	public static String getCheckUserSelSql(String userId) {
		StringBuilder wSql = new StringBuilder();
		wSql.append(" SELECT COUNT(*) AS CNT ");
		wSql.append(" FROM USERS u ");
		wSql.append(" WHERE (u.USER_ID='").append(userId).append("' ");
		wSql.append(" OR u.MAIL_ADDRESS='").append(userId).append("'");
		wSql.append(" ) ");
		wSql.append(" AND u.VALID_FLG='0' ");
		return wSql.toString();
	}

	/**
	 * 获得查询用户账户信息SQL
	 *
	 * @param 用户ID
	 *
	 * @return 查询用户账户信息SQL
	 * */
	public static String getUserAccountSelSql(String userId) {
		StringBuilder wSql = new StringBuilder();
		wSql.append(" SELECT (IFNULL(CAPITAL_TOTAL,0)+IFNULL(AWAIT,0)) AS CAPITAL_TOTAL,RECHARGE_TOTAL,CASH_TOTAL,BALANCE,FROST,AWAIT,IFNULL(EXPERIENCE_CASH,0) AS EXPERIENCE_CASH");
		wSql.append(" FROM USERS_ACCOUNT WHERE USER_ID='").append(userId).append("' ");
		return wSql.toString();
	}

	/**
	 * 获得查询用户投资合计SQL
	 *
	 * @param 用户ID
	 *
	 * @return 查询用户投资合计SQL
	 * */
	public static String getUserTenderSelSql(String userId) {
		StringBuilder wSql = new StringBuilder();
		wSql.append("SELECT SUM(BR.RECOVER_ACCOUNT_CAPITAL) AS TENDERACCOUNT, ");
		wSql.append(" SUM(CASE ");
		wSql.append("    WHEN BR.RECOVER_FLG = '0' THEN ");
		wSql.append("     BR.RECOVER_ACCOUNT_ALL - BR.RECOVER_MANAGEMENT_AMOUNT + ");
		wSql.append("     BR.OVERDUE_INTEREST + BR.OVERDUE_FORFEIT ");
		wSql.append("    ELSE ");
		wSql.append("     CASE ");
		wSql.append("    WHEN BRP.MAT_FLG = '1' THEN ");
		wSql.append("     BR.RECOVER_ACCOUNT_ALL - BR.RECOVER_ACCOUNT_ALL_YES - ");
		wSql.append("     BR.RECOVER_MANAGEMENT_AMOUNT + BR.OVERDUE_INTEREST + ");
		wSql.append("     BR.OVERDUE_FORFEIT ");
		wSql.append("    ELSE ");
		wSql.append("     0 ");
		wSql.append("  END END) AS TENDERACCOUNTWAIT, ");
		wSql.append(" SUM(CASE ");
		wSql.append("    WHEN BR.RECOVER_FLG = '1' THEN ");
		wSql.append("     BR.RECOVER_ACCOUNT_ALL - BR.RECOVER_MANAGEMENT_AMOUNT + ");
		wSql.append("     BR.OVERDUE_INTEREST + BR.OVERDUE_FORFEIT ");
		wSql.append("    ELSE ");
		wSql.append("     CASE ");
		wSql.append("    WHEN BRP.MAT_FLG = '1' THEN ");
		wSql.append("     BR.RECOVER_ACCOUNT_ALL - BR.RECOVER_ACCOUNT_ALL_YES - ");
		wSql.append("     BR.RECOVER_MANAGEMENT_AMOUNT + BR.OVERDUE_INTEREST + ");
		wSql.append("     BR.OVERDUE_FORFEIT ");
		wSql.append("    ELSE ");
		wSql.append("     0 ");
		wSql.append("  END END) AS TENDERACCOUNTYES, ");
		wSql.append(" SUM(CASE ");
		wSql.append("    WHEN BR.RECOVER_FLG = '0' THEN ");
		wSql.append("     BR.RECOVER_ACCOUNT_INTEREST - BR.RECOVER_ACCOUNT_INTEREST_YES - ");
		wSql.append("     BR.RECOVER_MANAGEMENT_AMOUNT + BR.OVERDUE_INTEREST + ");
		wSql.append("     BR.OVERDUE_FORFEIT ");
		wSql.append("    ELSE ");
		wSql.append("     CASE ");
		wSql.append("    WHEN BRP.MAT_FLG = '1' THEN ");
		wSql.append("     BR.RECOVER_ACCOUNT_INTEREST - BR.RECOVER_MANAGEMENT_AMOUNT + ");
		wSql.append("     BR.OVERDUE_INTEREST + BR.OVERDUE_FORFEIT ");
		wSql.append("    ELSE ");
		wSql.append("     0 ");
		wSql.append("  END END) AS TENDERINTERESWAIT, ");
		wSql.append(" SUM(CASE ");
		wSql.append("    WHEN BR.RECOVER_FLG = '0' THEN ");
		wSql.append("     0 ");
		wSql.append("    ELSE ");
		wSql.append("     CASE ");
		wSql.append("    WHEN BRP.MAT_FLG = '1' THEN ");
		wSql.append("     0 ");
		wSql.append("    ELSE ");
		wSql.append("     BR.RECOVER_ACCOUNT_INTEREST_YES - BR.RECOVER_MANAGEMENT_AMOUNT + ");
		wSql.append("     BR.OVERDUE_INTEREST + BR.OVERDUE_FORFEIT ");
		wSql.append("  END END) AS TENDERINTERES ");
		wSql.append("  FROM BORROW_RECOVER BR ");
		wSql.append("  LEFT JOIN BORROW_REPAY BRP ON BR.BORROW_ID = BRP.BORROW_ID ");
		wSql.append("   AND BR.BORROW_TIMES = BRP.BORROW_TIMES ");
		wSql.append(" WHERE BR.USER_ID = '").append(userId).append("' ");
		return wSql.toString();
	}

	/**
	 * 获得查询用户投资奖励SQL
	 *
	 * @param 用户ID
	 *
	 * @return 查询用户投资奖励SQL
	 * */
	public static String getUserAwardSelSql(String userId) {
		StringBuilder wSql = new StringBuilder();
		wSql.append("SELECT (SELECT IFNULL(SUM(MONEY), 0) AS REWARDMONEY ");
		wSql.append("    FROM USERS_ACCOUNT_LOG ");
		wSql.append("   WHERE USER_ID = '").append(userId).append("' ");
		wSql.append("     AND LOG_TYPE = '35') - ");
		wSql.append(" (SELECT IFNULL(SUM(MONEY), 0) AS REWARDMONEY ");
		wSql.append("    FROM USERS_ACCOUNT_LOG ");
		wSql.append("   WHERE USER_ID = '").append(userId).append("' ");
		wSql.append("     AND LOG_TYPE = '39')  AS REWARDMONEY ");
		wSql.append("  FROM DUAL ");
		return wSql.toString();
	}

	/**
	 * 获得查询用户最近一次回款记录SQL
	 *
	 * @param 用户ID
	 *
	 * @return 查询用户最近一次回款记录SQL
	 * */
	public static String getUserNowTenderSelSql(String userId) {
		StringBuilder wSql = new StringBuilder();
		wSql.append(" SELECT IFNULL(RECOVER_TIME,'-') AS RECOVER_TIME,IFNULL(RECOVER_ACCOUNT_ALL,0) AS RECOVER_ACCOUNT_ALL ");
		wSql.append(" FROM BORROW_RECOVER WHERE USER_ID='").append(userId).append("'");
		wSql.append(" AND RECOVER_FLG='0' ORDER BY RECOVER_TIME ");
		return wSql.toString();
	}

	/**
	 * 获得查询用户借款总额、待还总额、已还款总额SQL
	 *
	 * @param 用户ID
	 *
	 * @return 查询用户借款总额、待还总额、已还款总额SQL
	 * */
	public static String getUserBorrowSelSql(String userId) {
		StringBuilder wSql = new StringBuilder();
		wSql.append("SELECT SUM(T.REPAY_ACCOUNT_CAPITAL) AS BORROWACCOUNT, ");
		wSql.append(" SUM(CASE ");
		wSql.append("  WHEN T.REPAY_FLG = 0 THEN ");
		wSql.append("   T.REPAY_ACCOUNT_ALL + T.OVERDUE_INTEREST + T.OVERDUE_FORFEIT + ");
		wSql.append("   T.OVERDUE_REMINDER + T.REPAY_MANAGEMENT_AMOUNT ");
		wSql.append("  ELSE ");
		wSql.append("   0 ");
		wSql.append("  END) AS REPAYACCOUNTWAIT, ");
		wSql.append(" SUM(CASE ");
		wSql.append("  WHEN T.REPAY_FLG = 1 THEN ");
		wSql.append("   T.REPAY_ACCOUNT_ALL + T.OVERDUE_INTEREST + T.OVERDUE_FORFEIT + ");
		wSql.append("   T.OVERDUE_REMINDER + T.REPAY_MANAGEMENT_AMOUNT ");
		wSql.append("  ELSE ");
		wSql.append("   0 ");
		wSql.append("  END) AS REPAYACCOUNTYES ");
		wSql.append("  FROM BORROW_REPAY T ");
		wSql.append(" WHERE T.USER_ID = '").append(userId).append("' ");
		return wSql.toString();
	}

	/**
	 * 获得查询用户最近一次还款SQL
	 *
	 * @param 用户ID
	 *
	 * @return 查询用户最近一次还款SQL
	 * */
	public static String getUserBorrowRepaySelSql(String userId) {
		StringBuilder wSql = new StringBuilder();
		wSql.append(" SELECT IFNULL(REPAY_TIME,'-') AS REPAY_TIME,IFNULL(REPAY_ACCOUNT_ALL,0) AS REPAY_ACCOUNT_ALL ");
		wSql.append(" FROM BORROW_REPAY WHERE USER_ID='").append(userId).append("'");
		wSql.append(" AND REPAY_FLG='0' ORDER BY REPAY_TIME ");
		return wSql.toString();
	}

	/**
	 * 获得查询用户额度记录SQL
	 *
	 * @param 用户ID
	 *
	 * @return 查询用户额度记录SQL
	 * */
	public static String getUserQuotaSelSql(String userId) {
		StringBuilder wSql = new StringBuilder();
		wSql.append(" SELECT DISTINCT USER_ID, ");
		wSql.append(" SUM(DECODE(QUOTA_TYPE,'1',QUOTA_AMOUNT,0)) AS CREDITQUOTA, ");
		wSql.append(" SUM(DECODE(QUOTA_TYPE,'1',QUOTA_BALANCE,0)) AS CREDITAVAILQUOTA, ");
		wSql.append(" SUM(DECODE(QUOTA_TYPE,'2',QUOTA_AMOUNT,0)) AS PWANQUOTA, ");
		wSql.append(" SUM(DECODE(QUOTA_TYPE,'2',QUOTA_BALANCE,0)) AS PWANAVAILQUOTA, ");
		wSql.append(" SUM(DECODE(QUOTA_TYPE,'4',QUOTA_AMOUNT,0)) AS WAUCHQUOTA, ");
		wSql.append(" SUM(DECODE(QUOTA_TYPE,'4',QUOTA_BALANCE,0)) AS WAUCHAVAILQUOTA, ");
		wSql.append(" SUM(DECODE(QUOTA_TYPE,'5',QUOTA_AMOUNT,0)) AS ONEOFFQUOTA, ");
		wSql.append(" SUM(DECODE(QUOTA_TYPE,'5',QUOTA_BALANCE,0)) AS ONEOFFAVAILQUOTA, ");
		wSql.append(" SUM(DECODE(QUOTA_TYPE,'6',QUOTA_AMOUNT,0)) AS ROAMQUOTA, ");
		wSql.append(" SUM(DECODE(QUOTA_TYPE,'6',QUOTA_BALANCE,0)) AS ROAMAVAILQUOTA ");
		wSql.append(" FROM BORROW_QUOTA ");
		wSql.append(" WHERE USER_ID='").append(userId).append("'");
		wSql.append(" GROUP BY USER_ID ");
		return wSql.toString();
	}

	/**
	 * 获得查询用户投标统计SQL
	 *
	 * @param 用户ID
	 *
	 * @return 查询用户投标统计SQL
	 * */
	public static String getUserTenderSumSelSql(String userId) {
		StringBuilder wSql = new StringBuilder();
		wSql.append(" SELECT SUM(bt.VALID_ACCOUNT_TENDER) AS BORROW_FROST ");
		wSql.append(" FROM BORROW_TENDER bt ");
		wSql.append(" LEFT JOIN BORROW b ON b.BORROW_ID=bt.BORROW_ID ");
		wSql.append(" WHERE bt.USER_ID='").append(userId).append("' ");
		wSql.append(" AND IFNULL(bt.CANCEL_FLG,'0')='0' ");
		wSql.append(" AND b.VERIFY_FLG='1' AND B.REVERIFY_FLG IS NULL  ");
		wSql.append(" AND B.CANCEL_STATUS = '0' AND B.FAILED_FLG = '0' ");
		return wSql.toString();
	}

	/**
	 * 获得查询用户充值费用SQL
	 *
	 * @param 用户ID
	 *
	 * @return 查询用户充值费用SQL
	 * */
	public static String getUserRechargeFeeSelSql(String userId) {
		StringBuilder wSql = new StringBuilder();
		wSql.append(" SELECT SUM(FEE_AMOUNT) AS FEE_AMOUNT   ");
		wSql.append(" FROM USERS_ACCOUNT_RECHARGE ");
		wSql.append(" WHERE USER_ID='").append(userId).append("' ");
		wSql.append(" AND RECHARGE_STATUS='0'");
		return wSql.toString();
	}

	/**
	 * 获得查询用户提现费用SQL
	 *
	 * @param 用户ID
	 *
	 * @return 查询用户提现费用SQL
	 * */
	public static String getUserCashFeeSelSql(String userId) {
		StringBuilder wSql = new StringBuilder();
		wSql.append(" SELECT SUM(FEE_CASH) AS FEE_CASH   ");
		wSql.append(" FROM USERS_ACCOUNT_CASH ");
		wSql.append(" WHERE USER_ID='").append(userId).append("' ");
		wSql.append(" AND VERIFY_FLG='1'");
		return wSql.toString();
	}

	/**
	 * 获得查询用户 投资资金情况 投资总额 ,待回收总额(期数),待回收本金,待回收利息,已回收总额,已回收本金,已回收利息SQL
	 *
	 * @param 用户ID
	 *
	 * @return 查询用户投资资金情况 投资总额
	 *         ,待回收总额(期数),待回收本金,待回收利息,已回收总额(期数),已回收本金,已回收利息,投资中总额SQL
	 * */
	public static String getUserTenderCountSelSql(String userId) {
		StringBuilder wSql = new StringBuilder();
		wSql.append("SELECT SUM(BR.RECOVER_ACCOUNT_CAPITAL) AS VALID_ACCOUNT_TENDER, ");
		wSql.append(" SUM(CASE ");
		wSql.append("    WHEN BR.RECOVER_FLG = '0' THEN ");
		wSql.append("     BR.RECOVER_ACCOUNT_ALL - BR.RECOVER_MANAGEMENT_AMOUNT + ");
		wSql.append("     BR.OVERDUE_INTEREST + BR.OVERDUE_FORFEIT ");
		wSql.append("    ELSE ");
		wSql.append("     CASE ");
		wSql.append("    WHEN BRP.MAT_FLG = '1' THEN ");
		wSql.append("     BR.RECOVER_ACCOUNT_ALL - BR.RECOVER_ACCOUNT_ALL_YES - ");
		wSql.append("     BR.RECOVER_MANAGEMENT_AMOUNT + BR.OVERDUE_INTEREST + ");
		wSql.append("     BR.OVERDUE_FORFEIT ");
		wSql.append("    ELSE ");
		wSql.append("     0 ");
		wSql.append("  END END) AS RECOVER_ACCOUNT_WAIT, ");
		wSql.append(" SUM(CASE ");
		wSql.append("    WHEN BR.RECOVER_FLG = '0' THEN ");
		wSql.append("     1 ");
		wSql.append("    ELSE ");
		wSql.append("     CASE ");
		wSql.append("    WHEN BR.RECOVER_ACCOUNT_ALL = BR.RECOVER_ACCOUNT_ALL_YES THEN ");
		wSql.append("     0 ");
		wSql.append("    ELSE ");
		wSql.append("     1 ");
		wSql.append("  END END) AS RECOVER_ACCOUNT_COUNT_WAIT, ");
		wSql.append(" SUM(BR.RECOVER_ACCOUNT_CAPITAL - BR.RECOVER_ACCOUNT_CAPITAL_YES) AS RECOVER_ACCOUNT_CAPITAL_WAIT, ");
		wSql.append(" SUM(CASE ");
		wSql.append("    WHEN BR.RECOVER_FLG = '0' THEN ");
		wSql.append("     BR.RECOVER_ACCOUNT_INTEREST - BR.RECOVER_ACCOUNT_INTEREST_YES - ");
		wSql.append("     BR.RECOVER_MANAGEMENT_AMOUNT + BR.OVERDUE_INTEREST + ");
		wSql.append("     BR.OVERDUE_FORFEIT ");
		wSql.append("    ELSE ");
		wSql.append("     CASE ");
		wSql.append("    WHEN BRP.MAT_FLG = '1' THEN ");
		wSql.append("     BR.RECOVER_ACCOUNT_INTEREST - BR.RECOVER_MANAGEMENT_AMOUNT + ");
		wSql.append("     BR.OVERDUE_INTEREST + BR.OVERDUE_FORFEIT ");
		wSql.append("    ELSE ");
		wSql.append("     0 ");
		wSql.append("  END END) AS RECOVER_ACCOUNT_INTEREST_WAIT, ");
		wSql.append(" SUM(BR.RECOVER_ACCOUNT_ALL_YES) AS RECOVER_ACCOUNT_YES, ");
		wSql.append(" SUM(CASE ");
		wSql.append("    WHEN BR.RECOVER_FLG = '1' THEN ");
		wSql.append("     CASE ");
		wSql.append("    WHEN BR.RECOVER_ACCOUNT_ALL = BR.RECOVER_ACCOUNT_ALL_YES THEN ");
		wSql.append("     1 ");
		wSql.append("    ELSE ");
		wSql.append("     0 END ");
		wSql.append("    ELSE ");
		wSql.append("     0 ");
		wSql.append("  END) AS RECOVER_ACCOUNT_COUNT_YES, ");
		wSql.append(" SUM(BR.RECOVER_ACCOUNT_CAPITAL_YES) AS RECOVER_ACCOUNT_CAPITAL_YES, ");
		wSql.append(" SUM(CASE ");
		wSql.append("    WHEN BR.RECOVER_FLG = '0' THEN ");
		wSql.append("     0 ");
		wSql.append("    ELSE ");
		wSql.append("     CASE ");
		wSql.append("    WHEN BRP.MAT_FLG = '1' THEN ");
		wSql.append("     0 ");
		wSql.append("    ELSE ");
		wSql.append("     BR.RECOVER_ACCOUNT_INTEREST_YES - BR.RECOVER_MANAGEMENT_AMOUNT + ");
		wSql.append("     BR.OVERDUE_INTEREST + BR.OVERDUE_FORFEIT ");
		wSql.append("  END END) AS RECOVER_ACCOUNT_INTEREST_YES, ");
		wSql.append(" SUM(CASE ");
		wSql.append("    WHEN BR.RECOVER_FLG = '0' THEN ");
		wSql.append("     0 ");
		wSql.append("    ELSE ");
		wSql.append("     CASE ");
		wSql.append("    WHEN BRP.MAT_FLG = '1' THEN ");
		wSql.append("     BR.RECOVER_ACCOUNT_ALL_YES ");
		wSql.append("    ELSE ");
		wSql.append("     0 ");
		wSql.append("  END END) AS OVERDUE_FORFEIT, ");
		wSql.append(" SUM(BR.OVERDUE_INTEREST + BR.OVERDUE_FORFEIT) AS OVERDUE_ALL ");
		wSql.append("  FROM BORROW_RECOVER BR ");
		wSql.append("  LEFT JOIN BORROW_REPAY BRP ON BR.BORROW_ID = BRP.BORROW_ID ");
		wSql.append("   AND BR.BORROW_TIMES = BRP.BORROW_TIMES ");
		wSql.append(" WHERE BR.USER_ID = '").append(userId).append("' ");
		return wSql.toString();
	}

	/**
	 * 获得查询用户回款时间SQL
	 *
	 * @param 用户ID
	 *
	 * @return 查询用户回款时间SQL
	 * */
	public static String getUserRecoverTimeSelSql(String userId) {
		StringBuilder wSql = new StringBuilder();
		wSql.append(" SELECT RECOVER_TIME ");
		wSql.append(" FROM BORROW_RECOVER ");
		wSql.append(" WHERE USER_ID='").append(userId).append("' ");
		wSql.append(" AND RECOVER_FLG='0'");
		wSql.append(" ORDER BY RECOVER_TIME ASC ");
		return wSql.toString();
	}

	/**
	 * 获得查询用户借款总额SQL
	 *
	 * @param 用户ID
	 *
	 * @return 查询用户借款总额SQL
	 * */
	public static String getUserBorrowCountSelSql(String userId) {
		StringBuilder wSql = new StringBuilder();
		wSql.append("SELECT SUM(BR.REPAY_ACCOUNT_CAPITAL) AS BORROW_ACCOUNT_YES, ");
		wSql.append(" SUM(CASE ");
		wSql.append("    WHEN BR.REPAY_FLG = '1' THEN ");
		wSql.append("     BR.REPAY_ACCOUNT_ALL_YES + BR.OVERDUE_INTEREST + ");
		wSql.append("     BR.OVERDUE_FORFEIT + BR.OVERDUE_REMINDER + ");
		wSql.append("     BR.REPAY_MANAGEMENT_AMOUNT ");
		wSql.append("    ELSE ");
		wSql.append("     0 ");
		wSql.append("  END) AS REPAY_ACCOUNT_YES, ");
		wSql.append(" SUM(CASE ");
		wSql.append("    WHEN BR.REPAY_FLG = '1' THEN ");
		wSql.append("     0 ");
		wSql.append("    ELSE ");
		wSql.append("     BR.REPAY_ACCOUNT_ALL + BR.OVERDUE_INTEREST + BR.OVERDUE_FORFEIT + ");
		wSql.append("     BR.OVERDUE_REMINDER + BR.REPAY_MANAGEMENT_AMOUNT ");
		wSql.append("  END) AS REPAY_ACCOUNT_WAIT, ");
		wSql.append(" COUNT(DISTINCT BR.BORROW_ID) AS BORROW_COUNT, ");
		wSql.append(" COUNT(*) AS BORROW_ISSUE, ");
		wSql.append(" SUM(CASE ");
		wSql.append("    WHEN BR.REPAY_FLG = '1' THEN ");
		wSql.append("     1 ");
		wSql.append("    ELSE ");
		wSql.append("     0 ");
		wSql.append("  END) AS REPAY_ISSUE_YES, ");
		wSql.append(" SUM(CASE ");
		wSql.append("    WHEN BR.REPAY_FLG = '1' THEN ");
		wSql.append("     0 ");
		wSql.append("    ELSE ");
		wSql.append("     1 ");
		wSql.append("  END) AS REPAY_ISSUE_WAIT ");
		wSql.append("  FROM BORROW_REPAY BR ");
		wSql.append(" WHERE BR.USER_ID = '").append(userId).append("' ");
		return wSql.toString();
	}

	/**
	 * 获得查询用户额度基本信息SQL
	 *
	 * @param 用户ID
	 *
	 * @return 查询用户额度基本信息SQL
	 * */
	public static String getUserQuotaInfoSelSql(String userId) {
		StringBuilder wSql = new StringBuilder();
		wSql.append("SELECT T.QUOTA_TYPE, T.QUOTA_AMOUNT, T.QUOTA_BALANCE ");
		wSql.append("  FROM BORROW_QUOTA T ");
		wSql.append(" WHERE T.USER_ID = '").append(userId).append("' ");
		return wSql.toString();
	}

	/**
	 * 获得查询用户网站垫付SQL
	 *
	 * @param 用户ID
	 *
	 * @return 查询用户网站垫付SQL
	 * */
	public static String getSiteAdvanceSelSql(String userId) {
		StringBuilder wSql = new StringBuilder();
		wSql.append(" SELECT SUM(BR1.RECOVER_ACCOUNT_ALL_YES) AS SITEADVANCE ");
		wSql.append(" FROM BORROW_REPAY BR ");
		wSql.append(" LEFT JOIN BORROW_RECOVER BR1 ON BR.BORROW_TIMES=BR1.BORROW_TIMES AND BR.BORROW_ID=BR1.BORROW_ID  ");
		wSql.append(" WHERE br.USER_ID='").append(userId).append("' ");
		wSql.append(" AND br.MAT_FLG='1'");
		return wSql.toString();
	}

	/**
	 * 获得查询用户还款中的借款、还清的借款（已借到金额总额和笔数）SQL
	 *
	 * @param 用户ID
	 *
	 * @return 查询用户网站垫付SQL
	 * */
	public static String getUserRepayBorrowCountSelSql(String userId) {
		StringBuilder wSql = new StringBuilder();
		wSql.append(" SELECT SUM(CASE WHEN round(b.REPAY_ACCOUNT_WAIT,0)=0 THEN 1 ELSE 0 END) AS REPAYENDCOUNT, ");
		wSql.append(" SUM(CASE WHEN round(b.REPAY_ACCOUNT_WAIT,0)=0 THEN b.BORROW_ACCOUNT_YES  ELSE 0 END) AS REPAYENDACCOUNT, ");
		wSql.append(" SUM(CASE WHEN round(b.REPAY_ACCOUNT_WAIT,0)>0 THEN 1 ELSE 0 END) AS REPAYCOUNT, ");
		wSql.append(" SUM(CASE WHEN round(b.REPAY_ACCOUNT_WAIT,0)>0 THEN b.BORROW_ACCOUNT_YES ELSE 0 END) AS REPAYACCOUNT ");
		wSql.append(" FROM BORROW b WHERE b.USER_ID='").append(userId).append("' ");
		wSql.append(" AND b.REVERIFY_FLG='1' ");
		return wSql.toString();
	}

	/**
	 * 获得用户投标中的金额SQL
	 *
	 * @param 用户ID
	 *
	 * @return 用户投标中金额SQL
	 * */
	public static String getUserTenderingCountSelSql(String userId) {
		StringBuilder wSql = new StringBuilder();
		wSql.append(" SELECT SUM(CASE WHEN BT.TENDER_STATUS='0' AND BT.DEL_FLG='0' THEN BT.VALID_ACCOUNT_TENDER ELSE 0 END) AS TENDERING_ACCOUNT ");
		wSql.append(" FROM BORROW_TENDER bt ");
		wSql.append(" WHERE bt.USER_ID='").append(userId).append("' ");
		return wSql.toString();
	}

	/**
	 * 获得用户招标中的金额(笔数)SQL
	 *
	 * @param 用户ID
	 *
	 * @return 用户招标中的金额(笔数)SQL
	 * */
	public static String getUserBorrowingCountSelSql(String userId) {
		/**
		 * 招标中借款的条件：初审通过、未满标、未撤销的借款
		 * */
		StringBuilder wSql = new StringBuilder();
		wSql.append(" SELECT COUNT(*) AS BORROWINGCOUNT,SUM(b.BORROW_ACCOUNT) AS BORROWINGACCOUNT");
		wSql.append(" FROM BORROW b WHERE b.USER_ID='").append(userId).append("' ");
		wSql.append(" AND b.VERIFY_FLG='1' AND B.BORROW_FULL_STATUS <> '1' AND B.CANCEL_STATUS <> '1' ");
		return wSql.toString();
	}

	/**
	 * 获得用户登录密码、提现密码、交易密码SQL
	 *
	 * @param 用户ID
	 *
	 * @return 用户招标中的金额(笔数)SQL
	 * */
	public static String getUserPasswordSelSql(String userId) {
		StringBuilder wSql = new StringBuilder();
		wSql.append(" SELECT u.USER_PWD,ua.TRADE_PWD,ua.CASH_PWD");
		wSql.append(" FROM USERS u ");
		wSql.append(" LEFT JOIN USERS_ACCOUNT ua ON ua.USER_ID=u.USER_ID ");
		wSql.append(" WHERE u.USER_ID='").append(userId).append("'");
		return wSql.toString();
	}

}
