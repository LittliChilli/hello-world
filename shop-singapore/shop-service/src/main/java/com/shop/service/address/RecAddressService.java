package com.shop.service.address;

import java.util.List;

import com.shop.entity.basic.UserReceiptAddress;

public interface RecAddressService {

	/**
	 * 查询默认收货地址
	 * 
	 * @param UserId
	 * @return
	 */
	UserReceiptAddress queryDefaultAddress(Long userId);

	/**
	 * 查询用户所有收货地址
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年11月25日
	 *
	 * @param userId
	 * @return
	 */
	List<UserReceiptAddress> queryRecAddressByUserId(Long userId);

	/**
	 * 添加收货地址
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年11月24日
	 *
	 * @param userId
	 * @param receiptAddress
	 * @param receiver
	 * @param telphone
	 */
	void addRecAddress(Long userId, String receiptAddress, String receiver, String telphone);

	/**
	 * 根据收货地址ID查询收货地址信息
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年11月24日
	 *
	 * @param id
	 * @return
	 */
	UserReceiptAddress queryRecAddressById(Long id);

	/**
	 * 校验收货地址是否存在
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年11月24日
	 *
	 * @param id
	 * @return
	 */
	boolean checkRecAddressIsExisted(Long id);

	/**
	 * 更新用户收货地址
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年11月24日
	 *
	 * @param userReceiptAddress
	 */
	void updateRecAddress(UserReceiptAddress userReceiptAddress);

	/**
	 * 更新默认设置的收货地址
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年11月24日
	 *
	 * @param userId
	 * @param isDefault
	 */
	void updateDefaultRecAddress(Long userId, Boolean isDefault);

	/**
	 * 删除用户收货地址
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年11月24日
	 *
	 * @param id
	 */
	void deleteRecAddress(Long id);
	
	/**
	 * 查询用户收货地址数量
	 * @param userId
	 * @return
	 */
	Integer queryUserAddressNumber(Long userId);
}
