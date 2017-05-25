package com.shop.service.address.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.common.Result;
import com.shop.common.dateutil.DateUtil;
import com.shop.common.exception.Message;
import com.shop.common.exception.ServiceException;
import com.shop.dao.basic.UserReceiptAddressMapper;
import com.shop.entity.basic.UserReceiptAddress;
import com.shop.entity.basic.UserReceiptAddressExample;
import com.shop.entity.basic.UserReceiptAddressExample.Criteria;
import com.shop.service.address.RecAddressService;

@Service
public class RecAddressServiceImpl implements RecAddressService {

	@Autowired
	UserReceiptAddressMapper addressMapper;

	@Override
	public UserReceiptAddress queryDefaultAddress(Long userId) {

		List<UserReceiptAddress> addressList = queryRecAddressListByCondition(userId, true);

		if (CollectionUtils.isNotEmpty(addressList)) {
			return addressList.get(0);
		}
		return null;
	}

	@Override
	public UserReceiptAddress queryRecAddressById(Long id) {
		return addressMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<UserReceiptAddress> queryRecAddressByUserId(Long userId) {

		return queryRecAddressListByCondition(userId, null);
	}

	private List<UserReceiptAddress> queryRecAddressListByCondition(Long userId, Boolean isDefault) {

		UserReceiptAddressExample example = new UserReceiptAddressExample();

		Criteria criteria = example.createCriteria();

		if (userId != null) {
			criteria.andUserIdEqualTo(userId);
		}

		if (isDefault != null) {
			criteria.andIsDefaultEqualTo(true);
		}

		example.setOrderByClause("create_time desc");

		return addressMapper.selectByExample(example);
	}

	@Override
	public boolean checkRecAddressIsExisted(Long id) {

		UserReceiptAddressExample example = new UserReceiptAddressExample();
		example.createCriteria().andIdEqualTo(id);

		Integer count = addressMapper.countByExample(example);

		if (count > 0) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public void addRecAddress(Long userId, String receiptAddress, String receiver, String telphone) {

		Integer number = queryUserAddressNumber(userId);

		if (number >= 5) {
			throw new ServiceException(Result.ERROR_CODE, "收货地址最多添加5个");
		}

		UserReceiptAddress record = new UserReceiptAddress();
		record.setUserId(userId);
		record.setReceiptAddress(receiptAddress);
		record.setReceiver(receiver);
		record.setTelephone(telphone);
		record.setIsDefault(false);
		record.setCreateTime(DateUtil.getNow());
		record.setUpdateTime(DateUtil.getNow());

		if (number == 0) {
			record.setIsDefault(true);
		} else {
			
			// 没有默认地址则设置默认地址
			UserReceiptAddress userReceiptAddress = queryDefaultAddress(userId);
			
			if (userReceiptAddress == null) {
				record.setIsDefault(true);
			}
		}

		Integer count = addressMapper.insertSelective(record);

		if (count <= 0) {
			throw new ServiceException(Message.sys_error);
		}
	}

	@Override
	public void updateRecAddress(UserReceiptAddress userReceiptAddress) {

		userReceiptAddress.setUpdateTime(DateUtil.getNow());

		Integer count = addressMapper.updateByPrimaryKeySelective(userReceiptAddress);

		if (count <= 0) {
			throw new ServiceException(Message.sys_error);
		}
	}

	@Override
	public void deleteRecAddress(Long id) {

		addressMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void updateDefaultRecAddress(Long id, Boolean isDefault) {

		UserReceiptAddress record = new UserReceiptAddress();
		record.setId(id);
		record.setIsDefault(isDefault);
		record.setUpdateTime(DateUtil.getNow());

		Integer count = addressMapper.updateByPrimaryKeySelective(record);

		if (count <= 0) {
			throw new ServiceException(Message.sys_error);
		}
	}

	@Override
	public Integer queryUserAddressNumber(Long userId) {

		UserReceiptAddressExample example = new UserReceiptAddressExample();
		example.createCriteria().andUserIdEqualTo(userId);

		return addressMapper.countByExample(example);
	}
}
