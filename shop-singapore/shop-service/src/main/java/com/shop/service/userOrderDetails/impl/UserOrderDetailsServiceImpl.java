package com.shop.service.userOrderDetails.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.common.dateutil.DateUtil;
import com.shop.common.imgutil.ImgPathUtil;
import com.shop.dao.basic.UserOrderDetailsMapper;
import com.shop.dao.ext.ShoppingCartMapperExt;
import com.shop.entity.basic.Goods;
import com.shop.entity.basic.User;
import com.shop.entity.basic.UserOrderDetails;
import com.shop.entity.basic.UserOrderDetailsExample;
import com.shop.entity.basic.UserOrderDetailsExample.Criteria;
import com.shop.service.goods.GoodsService;
import com.shop.service.userOrderDateils.UserOrderDetailsService;

@Service
public class UserOrderDetailsServiceImpl implements UserOrderDetailsService {

	@Autowired
	UserOrderDetailsMapper userOrderDetailsMapper;

	@Autowired
	ShoppingCartMapperExt shoppingCartMapperExt;

	@Autowired
	GoodsService goodsService;

	@Override
	public UserOrderDetails addToCart(Map<String, Object> map) {

		Long goodsId = (Long) map.get("goodsId");
		User user = (User) map.get(User.class.getSimpleName());
		Integer number = (Integer) map.get("number");

		UserOrderDetailsExample example = new UserOrderDetailsExample();
		example.createCriteria().andGoodsIdEqualTo(goodsId).andUserIdEqualTo(user.getId()).andOrderSnEqualTo("");
		List<UserOrderDetails> shoppingCartList = userOrderDetailsMapper.selectByExample(example);
		if (CollectionUtils.isNotEmpty(shoppingCartList)) {
			UserOrderDetails shoppingCart = shoppingCartList.get(0);
			shoppingCart.setNum(shoppingCart.getNum() + number);
			userOrderDetailsMapper.updateByPrimaryKeySelective(shoppingCart);
			return shoppingCart;
		} else {
			Goods goods = goodsService.getGoodsById(goodsId);

			UserOrderDetails shoppingCart = new UserOrderDetails();
			shoppingCart.setGoodsId(goodsId);
			shoppingCart.setNum(number);
			shoppingCart.setUpdateTime(new Date());
			shoppingCart.setCreateTime(new Date());
			shoppingCart.setUserId(user.getId());
			shoppingCart.setGoodsPrice(goods.getGoodsPrice());
			shoppingCart.setGoodsName(goods.getGoodsName());
			shoppingCart.setGoodsPic(goods.getGoodsPic());
			userOrderDetailsMapper.insertSelective(shoppingCart);
			return shoppingCart;
		}
	}
	
	@Override
	public UserOrderDetails addUserOrderDetail(UserOrderDetails shoppingCart) {
			userOrderDetailsMapper.insertSelective(shoppingCart);
			return shoppingCart;
	}

	@Override
	public List<Map<String, Object>> getCartItems(Map<String, Object> map) {
		return shoppingCartMapperExt.selectShopCart(map);

	}

	@Override
	public void deleteCartItemById(Long id) {
		userOrderDetailsMapper.deleteByPrimaryKey(id);
	}

	@Override
	public UserOrderDetails queryCartById(Long id) {
		return userOrderDetailsMapper.selectByPrimaryKey(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserOrderDetails> selectCartByCondition(Map<String, Object> map) {
		UserOrderDetailsExample example = new UserOrderDetailsExample();
		Criteria criteria = example.createCriteria();

		if (null != map) {
			if (StringUtils.isNotEmpty(((Long) map.get("userId")).toString())) {
				criteria.andUserIdEqualTo((Long) map.get("userId"));
			}

			if (null != (List<String>) map.get("orderSnList")) {
				criteria.andOrderSnIn((List<String>) map.get("orderSnList"));
			}

			if (map.get("isShopCart") != null && (boolean) map.get("isShopCart")) {
				criteria.andOrderSnEqualTo("");
			}

			if (StringUtils.isNotEmpty((String) map.get("goodsId"))) {
				criteria.andGoodsIdEqualTo(Long.valueOf((String) map.get("goodsId")));
			}
		}

		return userOrderDetailsMapper.selectByExample(example);
	}

	@Override
	public void updateUserOrderDetailsBatch(List<UserOrderDetails> userOrderDetailsList) {

		for (UserOrderDetails userOrderDetails : userOrderDetailsList) {
			userOrderDetails.setUpdateTime(DateUtil.getNow());
			userOrderDetailsMapper.updateByPrimaryKeySelective(userOrderDetails);
		}
	}

	@Override
	public UserOrderDetails getGoodsPic(UserOrderDetails userOrderDetails) {
		if (null != userOrderDetails && StringUtils.isNotEmpty(userOrderDetails.getGoodsPic())) {
			userOrderDetails.setGoodsPic(ImgPathUtil.getGoodImgPath(userOrderDetails.getGoodsId().toString(),
					userOrderDetails.getGoodsPic(), false));
		}
		return userOrderDetails;
	}

	@Override
	public List<UserOrderDetails> queryShopCartDetailByOrderSn(Long userId, String orderSn) {

		UserOrderDetailsExample example = new UserOrderDetailsExample();
		example.createCriteria().andUserIdEqualTo(userId).andOrderSnEqualTo(orderSn);
		return userOrderDetailsMapper.selectByExample(example);
	}
}
