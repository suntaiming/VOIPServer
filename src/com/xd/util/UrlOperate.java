package com.xd.util;

public class UrlOperate {
	/**
	 * 操作：获取（菜单、分组、用户列表）
	 */
	public static final String GET = "/get";
	/**
	 * 操作：创建（菜单、分组）
	 */
	public static final String CREATE = "/create";
	/**
	 * 操作：修改（分组名称）
	 */
	public static final String UPDATE = "/update";
	/**
	 * 操作：删除（菜单、分组）
	 */
	public static final String DELETE = "/delete";
	/**
	 * 操作：创建个性化菜单
	 */
	public static final String ADD_CONDITIONAL = "/addconditional";	
	
	/**
	 * 操作：根据openId查询用户所在分组
	 */
	public static final String GET_ID = "/getid";
	/**
	 * 操作：移动用户分组
	 */
	public static final String MEMBERS_UPDATE = "/members/update";
	/**
	 * 操作：批量移动用户分组
	 */
	public static final String MEMBERS_BATCHUPDATE = "/members/batchupdate";
	/**
	 * 操作：修改信息（用户信息）
	 */
	public static final String INFO_UPDATEREMARK = "/info/updateremark";
     /**
      * 操作：获取信息（用户基本信息）
      */
	public static final String INFO = "/info";
}
