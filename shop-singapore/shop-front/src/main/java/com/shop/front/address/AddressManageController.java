/** 
 * @fileName AddressManageController.java
 * 
 * @version 
 * @author zousheng 
 * @date:2016年11月25日
 * Copyright © 2016.美眉分期 All rights reserved.
 */
package com.shop.front.address;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shop.common.Result;
import com.shop.common.exception.ServiceException;
import com.shop.entity.basic.User;
import com.shop.entity.basic.UserReceiptAddress;
import com.shop.front.common.UserController;
import com.shop.service.address.RecAddressService;

/**
 * 用户收货地址管理
 * 
 * @version
 * @author zousheng
 * @date:2016年11月25日
 */
@Controller
@RequestMapping(value = "/singapore/address")
public class AddressManageController extends UserController {

	@Autowired
	private RecAddressService recAddressService;

	/**
	 * 查询用户所有收货地址
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年11月25日
	 *
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "query_recAddressAll")
	@ResponseBody
	public Result queryUserRecAddressAll(HttpServletRequest request, HttpServletResponse response, Model model) {

		User user = super.getUserFromSession2Exception(request);

		List<UserReceiptAddress> queryUserReceiptAddressList = recAddressService.queryRecAddressByUserId(user.getId());

		model.addAttribute("receipt_address_List", queryUserReceiptAddressList);

		if (CollectionUtils.isNotEmpty(queryUserReceiptAddressList)) {
			model.addAttribute("receipt_address_number", queryUserReceiptAddressList.size());
		}

		return super.result(model);
	}

	/**
	 * 查询用户所有收货地址
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年11月25日
	 *
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "query_address_detail")
	@ResponseBody
	public Result queryAddressDetail(HttpServletRequest request, HttpServletResponse response, Model model) {

		User user = super.getUserFromSession2Exception(request);

		String id = request.getParameter("id");

		UserReceiptAddress receiptAddress = recAddressService.queryRecAddressById(Long.valueOf(id));

		if (user.getId() != receiptAddress.getUserId()) {
			throw new ServiceException(Result.ERROR_CODE, "收货地址操作权限不足");
		}

		model.addAttribute("receiptAddress", receiptAddress);

		return super.result(model);
	}

	/**
	 * 新增用户收货地址
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年11月25日
	 *
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "add_recAddress")
	@ResponseBody
	public Result addUserRecAddress(HttpServletRequest request, HttpServletResponse response, Model model) {

		User user = super.getUserFromSession2Exception(request);

		// 收货地址
		String receiptAddress = request.getParameter("receiptAddress");

		if (StringUtils.isEmpty(receiptAddress)) {
			throw new ServiceException(Result.ERROR_CODE, "收货地址不能为空");
		}

		// 收货人
		String receiver = request.getParameter("receiver");

		if (StringUtils.isEmpty(receiver)) {
			throw new ServiceException(Result.ERROR_CODE, "收货人不能为空");
		}

		// 电话号码
		String telphone = request.getParameter("telephone");

		if (StringUtils.isEmpty(telphone)) {
			throw new ServiceException(Result.ERROR_CODE, "电话号码不能为空");
		}

		recAddressService.addRecAddress(user.getId(), receiptAddress, receiver, telphone);

		return super.result(model);
	}

	/**
	 * 更新用户收货地址
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年11月25日
	 *
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update_recAddress")
	@ResponseBody
	public Result updateUserRecAddress(HttpServletRequest request, HttpServletResponse response, Model model) {

		User user = super.getUserFromSession2Exception(request);

		String id = request.getParameter("id");

		if (StringUtils.isEmpty(id)) {
			throw new ServiceException(Result.ERROR_CODE, "收货地址不存在");
		}

		// 查询收货地址信息
		UserReceiptAddress updateUserReceiptAddress = recAddressService.queryRecAddressById(Long.valueOf(id));

		if (null == updateUserReceiptAddress) {
			throw new ServiceException(Result.ERROR_CODE, "收货地址不存在");
		}

		if (!(user.getId() == updateUserReceiptAddress.getUserId())) {
			throw new ServiceException(Result.ERROR_CODE, "收货地址操作权限不足");
		}

		// 收货地址
		String receiptAddress = request.getParameter("receiptAddress");

		if (StringUtils.isNotEmpty(receiptAddress)) {
			updateUserReceiptAddress.setReceiptAddress(receiptAddress);
		}

		// 收货人
		String receiver = request.getParameter("receiver");

		if (StringUtils.isNotEmpty(receiver)) {
			updateUserReceiptAddress.setReceiver(receiver);
		}

		// 电话号码
		String telphone = request.getParameter("telephone");

		if (StringUtils.isNotEmpty(telphone)) {
			updateUserReceiptAddress.setTelephone(telphone);
		}

		recAddressService.updateRecAddress(updateUserReceiptAddress);

		return super.result(model);
	}

	/**
	 * 删除用户收货地址
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年11月25日
	 *
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "delete_recAddress")
	@ResponseBody
	public Result deleteUserRecAddress(HttpServletRequest request, HttpServletResponse response, Model model) {

		User user = super.getUserFromSession2Exception(request);

		String id = request.getParameter("id");

		if (StringUtils.isEmpty(id)) {
			throw new ServiceException(Result.ERROR_CODE, "收货地址不存在");
		}

		// 查询收货地址信息
		UserReceiptAddress updateUserReceiptAddress = recAddressService.queryRecAddressById(Long.valueOf(id));

		if (null == updateUserReceiptAddress) {
			throw new ServiceException(Result.ERROR_CODE, "收货地址不存在");
		}

		if (!(user.getId() == updateUserReceiptAddress.getUserId())) {
			throw new ServiceException(Result.ERROR_CODE, "收货地址操作权限不足");
		}
		
		deleteUserRecAddress(user, updateUserReceiptAddress);

		return super.result(model);
	}

	/**
	 * 删除默认地址后,重置新的默认地址
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年12月7日
	 *
	 * @param updateUserReceiptAddress
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteUserRecAddress(User user, UserReceiptAddress updateUserReceiptAddress) {

		// 如果是默认地址,则删除后重新设置默认地址
		if (updateUserReceiptAddress.getIsDefault()) {
			recAddressService.deleteRecAddress(updateUserReceiptAddress.getId());

			List<UserReceiptAddress> userReceiptAddressList = recAddressService.queryRecAddressByUserId(user.getId());

			if (CollectionUtils.isNotEmpty(userReceiptAddressList)) {
				updateDefaultRecAddress(userReceiptAddressList.get(0), null);
			}
		} else {
			recAddressService.deleteRecAddress(updateUserReceiptAddress.getId());
		}
	}

	/**
	 * 修改默认收货地址
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年11月25日
	 *
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "default_recAddress")
	@ResponseBody
	public Result defaultUserRecAddress(HttpServletRequest request, HttpServletResponse response, Model model) {

		User user = super.getUserFromSession2Exception(request);

		String id = request.getParameter("id");

		if (StringUtils.isEmpty(id)) {
			throw new ServiceException(Result.ERROR_CODE, "收货地址不存在");
		}

		// 查询收货地址信息
		UserReceiptAddress updateUserReceiptAddress = recAddressService.queryRecAddressById(Long.valueOf(id));

		if (null == updateUserReceiptAddress) {
			throw new ServiceException(Result.ERROR_CODE, "收货地址不存在");
		}

		if (!(user.getId() == updateUserReceiptAddress.getUserId())) {
			throw new ServiceException(Result.ERROR_CODE, "收货地址操作权限不足");
		}

		// 查询默认收货地址
		UserReceiptAddress updateDefaultRecAddress = recAddressService.queryDefaultAddress(user.getId());

		updateDefaultRecAddress(updateUserReceiptAddress, updateDefaultRecAddress);

		return super.result(model);
	}

	/**
	 * 修改默认收货地址
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年11月25日
	 *
	 * @param newDefaultUserRecAddress
	 * @param oldDefaultUserRecAddress
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateDefaultRecAddress(UserReceiptAddress newDefaultUserRecAddress,
			UserReceiptAddress oldDefaultUserRecAddress) {

		if (oldDefaultUserRecAddress != null) {
			recAddressService.updateDefaultRecAddress(oldDefaultUserRecAddress.getId(), false);
		}

		recAddressService.updateDefaultRecAddress(newDefaultUserRecAddress.getId(), true);
	}
}
