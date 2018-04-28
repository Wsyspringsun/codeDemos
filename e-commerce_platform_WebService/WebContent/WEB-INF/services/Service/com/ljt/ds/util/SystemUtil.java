package  ljt.ds.util;

import java.util.Properties;

public class SystemUtil {

	private static Properties props = System.getProperties();

	/**
	 * Java的运行环境版本
	 * @return 版本
	 */
    public static String getVersion() {
    	return props.getProperty("java.version");
    }

	/**
	 * Java的运行环境供应商
	 * @return 供应商
	 */
    public static String getVendor() {
    	return props.getProperty("java.vendor");
    }

	/**
	 * Java供应商的URL
	 * @return URL
	 */
    public static String getVendorUrl() {
    	return props.getProperty("java.vendor.url");
    }

	/**
	 * Java的安装路径
	 * @return JavaHome
	 */
    public static String getJavaHome() {
    	return props.getProperty("java.home");
    }

	/**
	 * Java的虚拟机规范版本
	 * @return 虚拟机规范版本
	 */
    public static String getJvmSpecVer() {
    	return props.getProperty("java.vm.specification.version");
    }

	/**
	 * Java的虚拟机规范供应商
	 * @return 虚拟机规范供应商
	 */
    public static String getJvmVendor() {
    	return props.getProperty("java.vm.specification.vendor");
    }

	/**
	 * Java的虚拟机实现版本
	 * @return 虚拟机实现版本
	 */
    public static String getJvmVer() {
    	return props.getProperty("java.vm.version");
    }

	/**
	 * Java的虚拟机实现名称
	 * @return 虚拟机实现名称
	 */
    public static String getJvmName() {
    	return props.getProperty("java.vm.name");
    }

	/**
	 * Java的类路径
	 * @return 类路径
	 */
    public static String getJavaClassPath() {
    	return props.getProperty("java.class.path");
    }

	/**
	 * Java默认的临时文件路径
	 * @return 临时文件路径
	 */
    public static String getJavaTmpDir() {
    	return props.getProperty("java.io.tmpdir");
    }

	/**
	 * 一个或多个扩展目录的路径
	 * @return 扩展目录的路径
	 */
    public static String getJavaExtDir() {
    	return props.getProperty("java.ext.dirs");
    }

	/**
	 * 操作系统的名称
	 * @return 操作系统的名称
	 */
    public static String getOsName() {
    	return props.getProperty("os.name");
    }

	/**
	 * 操作系统的构架
	 * @return 操作系统的构架
	 */
    public static String getOsArch() {
    	return props.getProperty("os.arch");
    }

	/**
	 * 操作系统的版本
	 * @return 操作系统的版本
	 */
    public static String getOsVer() {
    	return props.getProperty("os.version");
    }

	/**
	 * 文件分隔符
	 * @return 文件分隔符
	 */
    public static String getFileSeparator() {
    	return props.getProperty("file.separator");
    }

	/**
	 * 路径分隔符
	 * @return 路径分隔符
	 */
    public static String getPathSeparator() {
    	return props.getProperty("path.separator");
    }

	/**
	 * 行分隔符
	 * @return 行分隔符
	 */
    public static String getLineSeparator() {
    	return props.getProperty("line.separator");
    }

	/**
	 * 用户的账户名称
	 * @return 用户的账户名称
	 */
    public static String getOsUserName() {
    	return props.getProperty("user.name");
    }

	/**
	 * 用户的主目录
	 * @return 用户的主目录
	 */
    public static String getOsUserHome() {
    	return props.getProperty("user.home");
    }

	/**
	 * 用户的当前工作目录
	 * @return 用户的当前工作目录
	 */
    public static String getOsUserDir() {
    	return props.getProperty("user.dir");
    }
}
