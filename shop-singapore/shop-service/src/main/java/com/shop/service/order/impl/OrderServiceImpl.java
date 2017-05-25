package com.shop.service.order.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.common.ConstantBasic;
import com.shop.common.Utils;
import com.shop.common.dateutil.DateUtil;
import com.shop.common.excel.ExcelUtil;
import com.shop.dao.basic.GoodsMapper;
import com.shop.dao.basic.UserMapper;
import com.shop.dao.basic.UserOrderDetailsMapper;
import com.shop.dao.basic.UserOrderMapper;
import com.shop.dao.basic.UserReceiptAddressMapper;
import com.shop.dao.ext.ShoppingCartMapperExt;
import com.shop.entity.basic.Goods;
import com.shop.entity.basic.TeamMemberRecord;
import com.shop.entity.basic.TeamOrderRecord;
import com.shop.entity.basic.User;
import com.shop.entity.basic.UserExample;
import com.shop.entity.basic.UserOrder;
import com.shop.entity.basic.UserOrderDetails;
import com.shop.entity.basic.UserOrderDetailsExample;
import com.shop.entity.basic.UserOrderExample;
import com.shop.entity.basic.UserOrderExample.Criteria;
import com.shop.entity.basic.UserReceiptAddress;
import com.shop.entity.excel.ShoppingExportExcel;
import com.shop.service.goods.GoodsService;
import com.shop.service.order.OrderService;
import com.shop.service.userOrderDateils.UserOrderDetailsService;

import jxl.Workbook;
import jxl.write.Alignment;
import jxl.write.Border;
import jxl.write.BorderLineStyle;
import jxl.write.Label;
import jxl.write.NumberFormat;
import jxl.write.NumberFormats;
import jxl.write.VerticalAlignment;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

@SuppressWarnings("deprecation")
@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private UserOrderMapper userOrderMapper;

	@Autowired
	private ShoppingCartMapperExt shoppingCartMapperExt;

	@Autowired
	UserReceiptAddressMapper addressMapper;

	@Autowired
	GoodsMapper goodsMapper;

	@Autowired
	GoodsService goodsService;

	@Autowired
	UserOrderMapper orderMapper;

	@Autowired
	UserOrderDetailsService userOrderDetailsService;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private UserOrderDetailsMapper userOrderDetailsMapper;

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> queryOrderListByPage(Map<String, String> queryMap) {
		Map<String, Object> dateMap = new HashMap<String, Object>();
		List<Map<String, Object>> orderList = new ArrayList<Map<String, Object>>();
		String orderSn = queryMap.get("orderSn");
		String orderStatus = queryMap.get("orderStatus");
		String startTime = queryMap.get("startTime");
		String endTime = queryMap.get("endTime");
		String pageNum = queryMap.get("pageNum");
		String receiptAddress = queryMap.get("receiptAddress");
		UserOrderExample userOrderExample = new UserOrderExample();
		Criteria userOrderCriteria = userOrderExample.createCriteria();
		if (StringUtils.isNotEmpty(orderSn)) {
			userOrderCriteria.andOrderSnEqualTo(orderSn);
		}
		if (StringUtils.isNotEmpty(orderStatus)) {
			userOrderCriteria.andOrderStatusEqualTo(orderStatus);
		}
		if (StringUtils.isNotEmpty(receiptAddress)) {
			userOrderCriteria.andReceiveAddressLike("%" + receiptAddress + "%");
		}
		userOrderCriteria
				.andCreateTimeGreaterThanOrEqualTo(DateUtil.getFormatDate(startTime, DateUtil.DateTime_Format));
		userOrderCriteria.andCreateTimeLessThanOrEqualTo(DateUtil.getFormatDate(endTime, DateUtil.DateTime_Format));
		userOrderExample.setOrderByClause("create_time Desc");
		PageHelper.startPage(Integer.parseInt(pageNum), 10);
		List<UserOrder> userOrderList = userOrderMapper.selectByExample(userOrderExample);
		PageInfo<UserOrder> pageInfo = new PageInfo<UserOrder>(userOrderList);
		dateMap.put("pageInfo", pageInfo);
		if (CollectionUtils.isNotEmpty(userOrderList)) {
			for (UserOrder userOrder : userOrderList) {
				Map<String, Object> orderData = new HashMap<String, Object>();
				Map<String, Object> receiveAddressMap = (Map<String, Object>) JSONObject
						.parse(userOrder.getReceiveAddress());
				userOrder.setReceiveAddress(receiveAddressMap.get("receiptAddress") + "   "
						+ receiveAddressMap.get("receiver") + "   " + receiveAddressMap.get("telephone"));
				orderData.put("orderInfo", userOrder);
				UserOrderDetailsExample userOrderDetailsExample = new UserOrderDetailsExample();
				userOrderDetailsExample.createCriteria().andOrderSnEqualTo(userOrder.getOrderSn());
				List<UserOrderDetails> userOrderDetailsList = userOrderDetailsMapper
						.selectByExample(userOrderDetailsExample);
				orderData.put("userOrderDetailsList", userOrderDetailsList);
				UserExample userExample = new UserExample();
				userExample.createCriteria().andIdEqualTo(userOrder.getUserId());
				List<User> userList = userMapper.selectByExample(userExample);
				orderData.put("user", userList.get(0));
				orderList.add(orderData);
			}
		}
		dateMap.put("orderList", orderList);
		return dateMap;
	}

	@Override
	public void cancelUserOrderByOrderId(Long orderId) {
		UserOrder userOrder = new UserOrder();
		userOrder.setId(orderId);
		userOrder.setOrderStatus("cancel");
		userOrder.setUpdateTime(DateUtil.getNow());
		userOrderMapper.updateByPrimaryKeySelective(userOrder);
	}

	@Override
	public void confirmUserOrderReceivedById(Long orderId) {
		UserOrder userOrder = new UserOrder();
		userOrder.setId(orderId);
		userOrder.setOrderStatus("confirmReceived");
		userOrder.setUpdateTime(DateUtil.getNow());
		userOrderMapper.updateByPrimaryKeySelective(userOrder);
	}

	@Override
	public List<ShoppingExportExcel> findShoppingListForExportExcel(String startTime, String endTime,
			String orderStatus, String receiptAddress) {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("startTime", DateUtil.getFormatDate(startTime, DateUtil.DateTime_Format));
		queryMap.put("endTime", DateUtil.getFormatDate(endTime, DateUtil.DateTime_Format));
		queryMap.put("orderStatus", orderStatus);
		queryMap.put("receiptAddress", receiptAddress);
		List<ShoppingExportExcel> shoppingList = shoppingCartMapperExt.selectShoppingList(queryMap);
		return shoppingList;
	}

	@Override
	public Integer queryOrdeNumByOrderStatus(Long userId, String orderStatus) {

		UserOrderExample example = new UserOrderExample();
		example.createCriteria().andUserIdEqualTo(userId).andOrderStatusEqualTo(orderStatus);

		return userOrderMapper.countByExample(example);
	}

	@Override
	public List<Map<String, Object>> findDeliveryListForExportExcel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> confirmOrder(List<UserOrderDetails> cartList, UserReceiptAddress userReceiptAddress) {

		// 订单编码
		String orderSn = Utils.getNextOrderSn(ConstantBasic.OrderTypeCode.SINGAPORE_ORDER_CODE);
		return confirmOrder(cartList, userReceiptAddress, orderSn);
	}

	/**
	 * 用户统一下单入口
	 * 
	 * @param cartList
	 * @param userReceiptAddress
	 * @param orderSn
	 * @return
	 */
	private Map<String, Object> confirmOrder(List<UserOrderDetails> cartList, UserReceiptAddress userReceiptAddress,
			String orderSn) {

		Double orderPrice = 0.00d;
		Long userId = null;
		for (UserOrderDetails cart : cartList) {
			Goods goods = goodsService.getGoodsById(cart.getGoodsId());

			orderPrice = orderPrice + (cart.getGoodsPrice() * cart.getNum());
			userId = cart.getUserId();

			cart.setOrderSn(orderSn);
			cart.setUpdateTime(DateUtil.getNow());
			cart.setOrderConfirmTime(new Date());
			cart.setGoodsPic(goods.getGoodsPic());
			cart.setGoodsPrice(goods.getGoodsPrice());
			cart.setGoodsName(goods.getGoodsName());
			userOrderDetailsMapper.updateByPrimaryKeySelective(cart);

			// 更新goods表数据
			goods.setGoodsSoldNumber(goods.getGoodsSoldNumber() + cart.getNum());
			goods.setUpdateTime(new Date());
			goodsService.updateGoodsByGoods(goods);
		}

		UserOrder order = new UserOrder();
		order.setOrderPrice(orderPrice);
		order.setOrderSn(orderSn);
		order.setOrderStatus("buying");
		order.setReceiveAddress(JSONObject.toJSONString(userReceiptAddress));
		order.setCreateTime(DateUtil.getNow());
		order.setUpdateTime(DateUtil.getNow());
		order.setUserId(userId);
		orderMapper.insertSelective(order);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put(UserOrder.class.getSimpleName(), order);
		return map;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void createOrder(List<String> idList, UserOrder order) {
		order.setOrderStatus("alreadyBuy");
		order.setUpdateTime(new Date());
		orderMapper.updateByPrimaryKeySelective(order);
		UserOrderDetails shoppingCart = null;
		for (String cartId : idList) {
			shoppingCart = userOrderDetailsMapper.selectByPrimaryKey(Long.parseLong(cartId));
			Goods goods = goodsMapper.selectByPrimaryKey(shoppingCart.getGoodsId());
			shoppingCart.setOrderSn(order.getOrderSn());
			shoppingCart.setUpdateTime(new Date());
			shoppingCart.setOrderConfirmTime(new Date());
			shoppingCart.setGoodsPic(goods.getGoodsPic());
			shoppingCart.setGoodsPrice(goods.getGoodsPrice());
			userOrderDetailsMapper.updateByPrimaryKeySelective(shoppingCart);
			goods.setGoodsSoldNumber(goods.getGoodsSoldNumber() + shoppingCart.getNum());
			goodsMapper.updateByPrimaryKeySelective(goods);
		}
	}

	@Override
	public List<UserOrder> queryOrderByCondition(Map<String, String> queryMap) {
		UserOrderExample example = new UserOrderExample();
		Criteria criteria = example.createCriteria();
		if (StringUtils.isNotEmpty(queryMap.get("id"))) {
			criteria.andIdEqualTo(Long.parseLong(queryMap.get("id")));
		}
		if (StringUtils.isNotEmpty(queryMap.get("orderSn"))) {
			criteria.andOrderSnEqualTo(queryMap.get("orderSn"));
		}
		if (StringUtils.isNotEmpty(queryMap.get("userId"))) {
			criteria.andUserIdEqualTo(Long.parseLong(queryMap.get("userId")));
		}
		if (StringUtils.isNotEmpty(queryMap.get("orderStatus"))) {
			if ("2".equals(queryMap.get("orderStatus"))) {
				List<String> statusList = new ArrayList<String>();
				statusList.add("alreadyBuy");
				statusList.add("confirmReceived");
				criteria.andOrderSnIn(statusList);
			} else {
				criteria.andOrderStatusEqualTo(queryMap.get("orderStatus"));
			}
		}
		return orderMapper.selectByExample(example);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, Object> orderConfirmDirect(Map<String, Object> map) {

		Long goodsId = (Long) map.get("goodsId");
		User user = (User) map.get(User.class.getSimpleName());
		Integer number = (Integer) map.get("number");

		Goods goods = goodsService.getGoodsById(goodsId);

		UserOrderDetails shoppingCart = new UserOrderDetails();
		shoppingCart.setGoodsId(goods.getId());
		shoppingCart.setNum(number);
		shoppingCart.setUpdateTime(new Date());
		shoppingCart.setCreateTime(new Date());
		shoppingCart.setUserId(user.getId());
		shoppingCart.setGoodsPrice(goods.getGoodsPrice());
		shoppingCart.setGoodsName(goods.getGoodsName());
		shoppingCart.setGoodsPic(goods.getGoodsPic());

		shoppingCart = userOrderDetailsService.addUserOrderDetail(shoppingCart);

		List<UserOrderDetails> cartList = new ArrayList<UserOrderDetails>();
		cartList.add(shoppingCart);

		UserReceiptAddress userReceiptAddress = (UserReceiptAddress) map.get(UserReceiptAddress.class.getSimpleName());
		Map<String, Object> resultMap = confirmOrder(cartList, userReceiptAddress);
		resultMap.put(UserOrderDetails.class.getSimpleName(), shoppingCart);
		return resultMap;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, Object> orderConfirmTeamGoods(TeamOrderRecord teamOrderRecord, TeamMemberRecord teamMemberRecord,
			Integer number) {

		UserOrderDetails shoppingCart = new UserOrderDetails();
		shoppingCart.setGoodsId(teamOrderRecord.getGoodsId());
		shoppingCart.setNum(number);
		shoppingCart.setUpdateTime(new Date());
		shoppingCart.setCreateTime(new Date());
		shoppingCart.setUserId(teamMemberRecord.getUserId());
		shoppingCart.setGoodsPrice(teamOrderRecord.getGoodsPrice());
		shoppingCart.setGoodsName(teamOrderRecord.getGoodsName());
		shoppingCart.setGoodsPic(teamOrderRecord.getGoodsPic());

		shoppingCart = userOrderDetailsService.addUserOrderDetail(shoppingCart);

		List<UserOrderDetails> cartList = new ArrayList<UserOrderDetails>();
		cartList.add(shoppingCart);

		UserReceiptAddress userReceiptAddress = JSONObject.parseObject(teamMemberRecord.getReceiveAddress(),
				UserReceiptAddress.class);

		Map<String, Object> resultMap = confirmOrder(cartList, userReceiptAddress, teamMemberRecord.getOrderSn());
		resultMap.put(UserOrderDetails.class.getSimpleName(), shoppingCart);
		return resultMap;
	}

	@Override
	public void cancelOrderByOrderSn(Map<String, Object> map) {
		UserOrderExample example = new UserOrderExample();
		example.createCriteria().andOrderSnEqualTo((String) map.get("orderSn"))
				.andUserIdEqualTo((Long) map.get("userId"));
		List<UserOrder> orderList = userOrderMapper.selectByExample(example);
		if (CollectionUtils.isNotEmpty(orderList)) {
			UserOrder order = orderList.get(0);
			order.setOrderStatus("cancel");
			order.setUpdateTime(new Date());
			userOrderMapper.updateByPrimaryKeySelective(order);
		}
	}

	@Override
	public void confirmUserOrderAlreadyBuyById(Long orderId) {
		UserOrder userOrder = new UserOrder();
		userOrder.setId(orderId);
		userOrder.setOrderStatus("alreadyBuy");
		userOrder.setUpdateTime(DateUtil.getNow());
		userOrderMapper.updateByPrimaryKeySelective(userOrder);
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public void exportDeliveryListExcel(Map<String, String> queryMap, HttpServletResponse response) {
		List<Map<String, Object>> orderList = new ArrayList<Map<String, Object>>();
		String orderSn = queryMap.get("orderSn");
		String orderStatus = queryMap.get("orderStatus");
		String startTime = queryMap.get("startTime");
		String endTime = queryMap.get("endTime");
		String receiptAddress = queryMap.get("receiptAddress");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		UserOrderExample userOrderExample = new UserOrderExample();
		Criteria userOrderCriteria = userOrderExample.createCriteria();
		if (StringUtils.isNotEmpty(orderSn)) {
			userOrderCriteria.andOrderSnEqualTo(orderSn);
		}
		if (StringUtils.isNotEmpty(orderStatus)) {
			userOrderCriteria.andOrderStatusEqualTo(orderStatus);
		}
		if (StringUtils.isNotEmpty(receiptAddress)) {
			userOrderCriteria.andReceiveAddressLike("%" + receiptAddress + "%");
		}
		userOrderCriteria
				.andCreateTimeGreaterThanOrEqualTo(DateUtil.getFormatDate(startTime, DateUtil.DateTime_Format));
		userOrderCriteria.andCreateTimeLessThanOrEqualTo(DateUtil.getFormatDate(endTime, DateUtil.DateTime_Format));
		userOrderExample.setOrderByClause("create_time Desc");
		List<UserOrder> userOrderList = userOrderMapper.selectByExample(userOrderExample);
		// 以下开始输出到EXCEL
		try {
			/** **********创建工作簿************ */
			// String separator = File.separator;
			File excelFile = new File("D:/" + "配送信息.xls");
			// File excelFile = new File("D:" + separator + fileName);
			if (excelFile.exists()) {
				excelFile.delete();
			}
			excelFile.getParentFile().mkdirs();
			excelFile.createNewFile();
			WritableWorkbook workbook = Workbook.createWorkbook(excelFile);

			/** **********创建工作表************ */
			WritableSheet sheet = workbook.createSheet("配送信息", 0);

			/** **********设置纵横打印（默认为纵打）、打印纸***************** */
			jxl.SheetSettings sheetset = sheet.getSettings();
			sheetset.setProtected(true);

			/** ************设置单元格字体************** */
			WritableFont NormalFont = new WritableFont(WritableFont.ARIAL, 10);
			// WritableFont BoldFont = new WritableFont(WritableFont.ARIAL, 10,
			// WritableFont.BOLD);

			/** ************以下设置三种单元格样式，灵活备用************ */
			// 用于标题居中
			// 普通text 或String类型
			// WritableCellFormat wcf_center = new WritableCellFormat(BoldFont);
			// wcf_center.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
			// wcf_center.setVerticalAlignment(VerticalAlignment.CENTRE); //
			// 文字垂直对齐
			// wcf_center.setAlignment(Alignment.CENTRE); // 文字水平对齐
			// wcf_center.setWrap(false); // 文字是否换行

			// Integer类型
			WritableCellFormat integerFormat = new WritableCellFormat(NormalFont, NumberFormats.INTEGER);
			integerFormat.setBorder(Border.NONE, BorderLineStyle.THIN);

			// Double类型
			NumberFormat doubleFormat = new NumberFormat("0.00");
			WritableCellFormat doublewcf = new WritableCellFormat(NormalFont, doubleFormat);
			doublewcf.setBorder(Border.NONE, BorderLineStyle.THIN);

			// 用于正文居左
			WritableCellFormat wcf_left = new WritableCellFormat(NormalFont);
			wcf_left.setBorder(Border.NONE, BorderLineStyle.THIN); // 线条
			wcf_left.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
			wcf_left.setAlignment(Alignment.LEFT); // 文字水平对齐
			wcf_left.setWrap(true); // 文字是否换行
			wcf_left.setBorder(Border.ALL, BorderLineStyle.THIN, jxl.format.Colour.BLACK);

			/** ***************以下是EXCEL正文数据********************* */
			if (CollectionUtils.isNotEmpty(userOrderList)) {
				int i = 1;
				for (UserOrder userOrder : userOrderList) {
					Map<String, Object> orderData = new HashMap<String, Object>();
					Map<String, Object> receiveAddressMap = (Map<String, Object>) JSONObject
							.parse(userOrder.getReceiveAddress());
					orderData.put("orderInfo", userOrder);
					orderData.put("receiveAddressMap", receiveAddressMap);
					UserOrderDetailsExample userOrderDetailsExample = new UserOrderDetailsExample();
					userOrderDetailsExample.createCriteria().andOrderSnEqualTo(userOrder.getOrderSn());
					List<UserOrderDetails> userOrderDetailsList = userOrderDetailsMapper
							.selectByExample(userOrderDetailsExample);
					orderData.put("userOrderDetailsList", userOrderDetailsList);
					orderList.add(orderData);
					// 设置单元格宽度
					sheet.setColumnView(1, 70);
					// 订单号
					sheet.addCell(new Label(1, i, "订单号：" + userOrder.getOrderSn(), wcf_left));
					i++;
					// 下单时间
					sheet.addCell(new Label(1, i, "下单时间：" + sdf.format(userOrder.getCreateTime()), wcf_left));
					i++;
					// 订单详情
					for (UserOrderDetails userOrderDetails : userOrderDetailsList) {
						sheet.addCell(new Label(1, i, userOrderDetails.getGoodsName() + "   "
								+ userOrderDetails.getGoodsPrice() + " × " + userOrderDetails.getNum(), wcf_left));
						i++;
					}
					// 订单价格
					sheet.addCell(new Label(1, i, "订单价格：" + userOrder.getOrderPrice(), wcf_left));
					i++;
					// 收获地址
					sheet.addCell(new Label(1, i, "收获地址：" + receiveAddressMap.get("receiptAddress"), wcf_left));
					i++;
					sheet.addCell(new Label(1, i,
							"收货人：" + receiveAddressMap.get("receiver") + "   " + receiveAddressMap.get("telephone"),
							wcf_left));
					i = i + 3;
				}
			}
			workbook.write();
			workbook.close();
			// 下载excel文件
			ExcelUtil.downLoad(excelFile.getPath(), response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateUserOrderInfo(UserOrder userOrder) {
		userOrder.setUpdateTime(DateUtil.getNow());
		userOrderMapper.updateByPrimaryKeySelective(userOrder);
	}

}
