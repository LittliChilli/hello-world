package com.shop.front.userOrderDateils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shop.common.ConstantBasic;
import com.shop.common.Result;
import com.shop.common.Utils;
import com.shop.common.imgutil.ImgPathUtil;
import com.shop.entity.basic.User;
import com.shop.entity.basic.UserOrderDetails;
import com.shop.front.common.UserController;
import com.shop.service.userOrderDateils.UserOrderDetailsService;;

@Controller
@RequestMapping(value = "/singapore/userOrderDateils")
public class UserOrderDetailsController extends UserController {

	@Autowired
	private UserOrderDetailsService userOrderDetailsService;

	/**
	 * 加入购物车
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("add_shopping_cart")
	@ResponseBody
	public Result addToShoppingCart(HttpServletRequest request, HttpServletResponse response, Model model) {

		User user = getUserFromSession2Exception(request);

		String goodsId = request.getParameter("goodsId");
		String number = request.getParameter("number");

		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("goodsId", Long.parseLong(goodsId));
		queryMap.put("number", Integer.parseInt(number));
		queryMap.put(User.class.getSimpleName(), user);

		userOrderDetailsService.addToCart(queryMap);
		return super.result(model);
	}

	/**
	 * 修改购物车
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("update_shopping_cart")
	@ResponseBody
	public Result updateToShoppingCart(HttpServletRequest request, HttpServletResponse response, Model model) {

		getUserFromSession2Exception(request);

		String saveShopCartInfoStr = request.getParameter("saveShopCartInfoStr");

		ArrayList<String> saveShopCartInfo = Utils.splitArrayString(saveShopCartInfoStr,
				ConstantBasic.Punctuation.COMMA);

		if (CollectionUtils.isNotEmpty(saveShopCartInfo)) {

			List<UserOrderDetails> userOrderDetailsLists = new ArrayList<UserOrderDetails>();

			for (String saveShopCart : saveShopCartInfo) {
				ArrayList<String> saveShopCartList = Utils.splitArrayString(saveShopCart,
						ConstantBasic.Punctuation.COLON);

				// 分割出主键id和购买数量
				if (CollectionUtils.isNotEmpty(saveShopCartList) && saveShopCartList.size() == 2) {
					UserOrderDetails userOrderDetails = new UserOrderDetails();
					userOrderDetails.setId(Long.valueOf(saveShopCartList.get(0)));
					userOrderDetails.setNum(Integer.valueOf(saveShopCartList.get(1)));

					userOrderDetailsLists.add(userOrderDetails);
				}
			}

			userOrderDetailsService.updateUserOrderDetailsBatch(userOrderDetailsLists);
		}

		return super.result(model);
	}

	/**
	 * 购物车展示
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("show_shopping_cart")
	@ResponseBody
	public Result showShoppingCart(HttpServletRequest request, HttpServletResponse response, Model model) {

		User user = getUserFromSession(request);

		if (user != null) {
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("userId", user.getId());
			queryMap.put("isShopCart", Boolean.TRUE);

			List<UserOrderDetails> userOrderDetailsList = userOrderDetailsService.selectCartByCondition(queryMap);

			// 转换商品图片路径
			for (UserOrderDetails userOrderDetails : userOrderDetailsList) {

				if (StringUtils.isNotEmpty(userOrderDetails.getGoodsPic())) {
					userOrderDetails.setGoodsPic(ImgPathUtil.getGoodImgPath(userOrderDetails.getGoodsId().toString(),
							userOrderDetails.getGoodsPic(), Boolean.FALSE));
				}
			}

			model.addAttribute("userOrderDetailsList", userOrderDetailsList);
		}

		return super.result(model);
	}

	/**
	 * 批量删除购物车
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("delete_shopping_cart")
	@ResponseBody
	public Result deleteShoppingCart(HttpServletRequest request, HttpServletResponse response, Model model) {

		String[] ids = request.getParameterValues("ids");
		for (String id : ids) {
			userOrderDetailsService.deleteCartItemById(Long.parseLong(id));
		}
		return super.result(model);
	}

	/**
	 * 查询购物车商品数量
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年12月1日
	 *
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("query_shopping_cart_number")
	@ResponseBody
	public Result queryShoppingCartNumber(HttpServletRequest request, HttpServletResponse response, Model model) {

		String goodsId = request.getParameter("goods_id");

		User user = getUserFromSession(request);

		Integer shopCartNumber = 0;

		if (user != null) {
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("userId", user.getId());
			queryMap.put("isShopCart", Boolean.TRUE);
			queryMap.put("goodsId", goodsId);

			List<UserOrderDetails> userOrderDetailsList = userOrderDetailsService.selectCartByCondition(queryMap);

			for (UserOrderDetails userOrderDetails : userOrderDetailsList) {
				shopCartNumber += userOrderDetails.getNum();
			}
		}

		model.addAttribute("shopCartNumber", shopCartNumber);

		return super.result(model);
	}

}
