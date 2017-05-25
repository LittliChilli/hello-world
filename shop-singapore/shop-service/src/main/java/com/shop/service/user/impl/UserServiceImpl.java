package com.shop.service.user.impl;

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
import com.shop.common.encode.CipherUtil;
import com.shop.common.exception.Message;
import com.shop.common.exception.ServiceException;
import com.shop.dao.basic.UserMapper;
import com.shop.entity.basic.User;
import com.shop.entity.basic.UserExample;
import com.shop.entity.basic.UserExample.Criteria;
import com.shop.service.user.UserService;

/**
 * 用户user表服务管理实现
 * 
 * @version
 * @author zousheng
 * @date:2016年11月24日
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMppaer;

	@Override
	public User queryUserById(Long userId) {

		return userMppaer.selectByPrimaryKey(userId);
	}

	@Override
	public User queryUserByLoginName(String loginName, String password) {

		UserExample example = new UserExample();
		Criteria criteria = example.createCriteria();

		criteria.andLoginNameEqualTo(loginName);
		criteria.andLoginPasswordEqualTo(CipherUtil.MD5Encode(password));

		List<User> userList = userMppaer.selectByExample(example);

		if (CollectionUtils.isEmpty(userList)) {
			throw new ServiceException(Message.user_pwd_error);
		}

		return userList.get(0);
	}

	@Override
	public User addUserByLoginName(String loginName, String password) {

		User userRecord = new User();
		userRecord.setLoginName(loginName);
		userRecord.setLoginPassword(CipherUtil.MD5Encode(password));
		userRecord.setNickName(loginName);
		userRecord.setCreateTime(DateUtil.getNow());
		userRecord.setUpdateTime(DateUtil.getNow());

		Integer count = userMppaer.insertSelective(userRecord);

		if (count <= 0) {
			throw new ServiceException(Message.sys_error);
		}

		return userRecord;
	}

	@Override
	public void updateUserPassword(String loginName, String password) {

		UserExample example = new UserExample();
		Criteria criteria = example.createCriteria();
		criteria.andLoginNameEqualTo(loginName);

		User userRecord = new User();
		userRecord.setLoginPassword(CipherUtil.MD5Encode(password));
		userRecord.setUpdateTime(DateUtil.getNow());

		Integer count = userMppaer
				.updateByExampleSelective(userRecord, example);

		if (count <= 0) {
			throw new ServiceException(Message.sys_error);
		}
	}

	@Override
	public Boolean checkUserIsExisted(String loginName) {

		UserExample example = new UserExample();
		Criteria criteria = example.createCriteria();
		criteria.andLoginNameEqualTo(loginName);

		Integer count = userMppaer.countByExample(example);

		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Map<String, Object> queryUserListByPage(Map<String, String> queryMap) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String loginName = queryMap.get("loginName");
		String startDate = queryMap.get("startDate");
		String endDate = queryMap.get("endDate");
		String pageNum = queryMap.get("pageNum");
		UserExample userExample = new UserExample();
		Criteria criteria = userExample.createCriteria();
		if (StringUtils.isNotEmpty(loginName)) {
			criteria.andLoginNameLike(loginName);
		}
		if (StringUtils.isNotEmpty(startDate)) {
			criteria.andCreateTimeGreaterThanOrEqualTo(DateUtil.getFormatDate(
					startDate, DateUtil.DateTime_Format));
		}
		if (StringUtils.isNotEmpty(endDate)) {
			criteria.andCreateTimeLessThanOrEqualTo(DateUtil.getFormatDate(
					endDate, DateUtil.DateTime_Format));
		}
		PageHelper.startPage(Integer.parseInt(pageNum), 10);
		List<User> userList = userMppaer.selectByExample(userExample);
		PageInfo<User> pageInfo = new PageInfo<User>(userList);
		dataMap.put("userList", userList);
		dataMap.put("pageInfo", pageInfo);
		return dataMap;
	}

}
