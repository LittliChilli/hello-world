package com.shop.service.admin;

import java.util.Map;

import com.shop.common.exception.ServiceException;
import com.shop.entity.basic.Admin;

public interface AdminService {

	/**
	 * 
	 * 根据帐号密码查询管理员
	 * 
	 * lion 2016年11月28日
	 * 
	 * @param loginName
	 * @param loginPassward
	 */
	Admin findAdminByLoginNameAndPassward(String loginName, String loginPassward)
			throws ServiceException;

	/**
	 * 
	 * 分页查询管理员列表
	 * 
	 * lion 2016年11月25日
	 * 
	 * @param loginName
	 * @return
	 */
	Map<String, Object> queryAdminListByPage(String loginName, String pageNum);

	/**
	 * 
	 * 新增管理员信息
	 * 
	 * 
	 * lion 2016年11月25日
	 * 
	 * @param loginName
	 * @param loginPassward
	 * @param nickName
	 */
	void addAdminInfo(String loginName, String loginPassward, String nickName)
			throws ServiceException;

	/**
	 * 
	 * 
	 * 删除管理员信息
	 * 
	 * lion 2016年11月25日
	 * 
	 * @param adminId
	 */
	void deleteAdminInfo(Long adminId);

	/**
	 * 
	 * 重置管理员密码
	 * 
	 * 
	 * lion 2016年11月25日
	 * 
	 * @param adminId
	 */
	void resetAdminPassward(Long adminId);
}
