package com.shop.service.admin.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.common.dateutil.DateUtil;
import com.shop.common.exception.ServiceException;
import com.shop.dao.basic.AdminMapper;
import com.shop.entity.basic.Admin;
import com.shop.entity.basic.AdminExample;
import com.shop.service.admin.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminMapper adminMapper;

	@Override
	public Map<String, Object> queryAdminListByPage(String loginName,
			String pageNum) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PageHelper.startPage(Integer.parseInt(pageNum), 10);
		AdminExample example = new AdminExample();
		example.setOrderByClause("create_time desc");
		if (StringUtils.isNotEmpty(loginName)) {
			example.createCriteria().andLoginNameLike(loginName)
					.andIsDeleteEqualTo(false);
		}
		List<Admin> adminList = adminMapper.selectByExample(example);
		PageInfo<Admin> pageInfo = new PageInfo<Admin>(adminList);
		dataMap.put("pageInfo", pageInfo);
		dataMap.put("adminList", adminList);
		return dataMap;
	}

	@Override
	public void addAdminInfo(String loginName, String loginPassward,
			String nickName) throws ServiceException {
		if (checkAdminByloginName(loginName)) {
			throw new ServiceException("2", "帐号已存在");
		}
		Admin admin = new Admin();
		admin.setLoginName(loginName);
		admin.setLoginPassward(loginPassward);
		if (StringUtils.isNotEmpty(nickName)) {
			admin.setNickName(nickName);
		}
		admin.setCreateTime(DateUtil.getNow());
		admin.setUpdateTime(DateUtil.getNow());
		admin.setIsDelete(false);
		adminMapper.insertSelective(admin);
	}

	@Override
	public void deleteAdminInfo(Long adminId) {
		Admin admin = new Admin();
		admin.setId(adminId);
		admin.setIsDelete(true);
		admin.setUpdateTime(DateUtil.getNow());
		adminMapper.deleteByPrimaryKey(adminId);
	}

	@Override
	public void resetAdminPassward(Long adminId) {
		Admin admin = new Admin();
		admin.setId(adminId);
		admin.setLoginPassward("111111");
		admin.setUpdateTime(DateUtil.getNow());
		adminMapper.updateByPrimaryKeySelective(admin);
	}

	@Override
	public Admin findAdminByLoginNameAndPassward(String loginName,
			String loginPassward) throws ServiceException {
		if (!checkAdminByloginName(loginName)) {
			throw new ServiceException("1", "用户名不存在");
		}
		AdminExample example = new AdminExample();
		example.createCriteria().andLoginNameEqualTo(loginName)
				.andLoginPasswardEqualTo(loginPassward);
		List<Admin> adminList = adminMapper.selectByExample(example);
		if (CollectionUtils.isEmpty(adminList)) {
			throw new ServiceException("1", "密码错误");
		}
		if (adminList.size() > 1) {
			throw new ServiceException("1", "账户异常，请联系管理员");
		}
		return adminList.get(0);
	}

	private Boolean checkAdminByloginName(String loginName) {
		Boolean bol = false;
		AdminExample adminExample = new AdminExample();
		adminExample.createCriteria().andLoginNameEqualTo(loginName);
		List<Admin> adminList = adminMapper.selectByExample(adminExample);
		if (CollectionUtils.isNotEmpty(adminList)) {
			bol = true;
		}
		return bol;
	}
}
