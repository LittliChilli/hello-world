package com.shop.service.user;

import java.util.Map;

import com.shop.entity.basic.User;

/**
 * 用户user表服务管理
 * 
 * @version
 * @author zousheng
 * @date:2016年11月24日
 */
public interface UserService {

	/**
	 * 根据用户id查询用户信息
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年11月24日
	 *
	 * @param userId
	 * @return
	 */
	User queryUserById(Long userId);

	/**
	 * 根据用户登录名称和密码查询用户信息(登录使用)
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年11月24日
	 *
	 * @param loginName
	 * @param password
	 * @return
	 */
	User queryUserByLoginName(String loginName, String password);

	/**
	 * 添加用户信息(注册使用)
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年11月24日
	 *
	 * @param loginName
	 * @param password
	 * @return
	 */
	User addUserByLoginName(String loginName, String password);

	/**
	 * 根据用户登录名校验用户是否存在
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年11月24日
	 *
	 * @param loginName
	 * @return
	 */
	Boolean checkUserIsExisted(String loginName);

	/**
	 * 用户重置密码
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年11月24日
	 *
	 * @param loginName
	 * @param password
	 */
	void updateUserPassword(String loginName, String password);
	
	/**
	 * 
	 * 分页查询用户列表
	 * 
	 * @author lion
	 * @date 2016-11-25
	 * 
	 * @param queryMap
	 * @return
	 */
	Map<String,Object> queryUserListByPage(Map<String,String> queryMap);
	
}
