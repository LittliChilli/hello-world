package com.shop.service.goods.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.common.ConstantBasic;
import com.shop.common.Result;
import com.shop.common.Utils;
import com.shop.common.enums.GoodsTypeEnum;
import com.shop.common.imgutil.ImgPathUtil;
import com.shop.common.imgutil.ImgSaveUtil;
import com.shop.dao.basic.GoodsMapper;
import com.shop.entity.basic.Goods;
import com.shop.entity.basic.GoodsExample;
import com.shop.entity.basic.GoodsExample.Criteria;
import com.shop.service.goods.GoodsService;

@Service
public class GoodsServiceImpl implements GoodsService {

	@Autowired
	GoodsMapper goodsMapper;

	@Override
	public List<Goods> queryGoodsByCondition(Map<String, String> map) {
		List<Goods> goodsList = this.selectGoodsByCondition(map);
		for (Goods goods : goodsList) {
			// 获取商品列表展示图
			getGoodsPicByGoods(goods);
		}
		return goodsList;
	}

	@Override
	public Goods getGoodsById(Long goodsId) {
		return goodsMapper.selectByPrimaryKey(goodsId);
	}

	@Override
	public List<Goods> queryAllEffectiveGoods() {
		GoodsExample example = new GoodsExample();
		Criteria criteria = example.createCriteria();
		criteria.andIsDeleteEqualTo(false);

		return goodsMapper.selectByExample(example);
	}

	@Override
	public Goods getGoodsPicByGoods(Goods goods) {
		if (StringUtils.isNotEmpty(goods.getGoodsPic())) {
			goods.setGoodsPic(ImgPathUtil.getGoodImgPath(goods.getId().toString(), goods.getGoodsPic(), false));
		}
		if (StringUtils.isNotEmpty(goods.getGoodsDetailsShowPic())) {
			goods.setGoodsDetailsShowPic(
					ImgPathUtil.getGoodImgPath(goods.getId().toString(), goods.getGoodsDetailsShowPic(), false));
		}
		if (StringUtils.isNotEmpty(goods.getGoodsDetailsPic1())) {
			goods.setGoodsDetailsPic1(
					ImgPathUtil.getGoodImgPath(goods.getId().toString(), goods.getGoodsDetailsPic1(), false));
		}
		if (StringUtils.isNotEmpty(goods.getGoodsDetailsPic2())) {
			goods.setGoodsDetailsPic2(
					ImgPathUtil.getGoodImgPath(goods.getId().toString(), goods.getGoodsDetailsPic2(), false));
		}
		if (StringUtils.isNotEmpty(goods.getGoodsDetailsPic3())) {
			goods.setGoodsDetailsPic3(
					ImgPathUtil.getGoodImgPath(goods.getId().toString(), goods.getGoodsDetailsPic3(), false));
		}
		if (StringUtils.isNotEmpty(goods.getGoodsDetailsPic4())) {
			goods.setGoodsDetailsPic4(
					ImgPathUtil.getGoodImgPath(goods.getId().toString(), goods.getGoodsDetailsPic4(), false));
		}
		if (StringUtils.isNotEmpty(goods.getGoodsDetailsPic5())) {
			goods.setGoodsDetailsPic5(
					ImgPathUtil.getGoodImgPath(goods.getId().toString(), goods.getGoodsDetailsPic5(), false));
		}
		if (StringUtils.isNotEmpty(goods.getGoodsDetailsPic6())) {
			goods.setGoodsDetailsPic6(
					ImgPathUtil.getGoodImgPath(goods.getId().toString(), goods.getGoodsDetailsPic6(), false));
		}
		if (StringUtils.isNotEmpty(goods.getGoodsDetailsPic7())) {
			goods.setGoodsDetailsPic7(
					ImgPathUtil.getGoodImgPath(goods.getId().toString(), goods.getGoodsDetailsPic7(), false));
		}

		if (StringUtils.isNotEmpty(goods.getGoodsDetailsPic8())) {
			goods.setGoodsDetailsPic8(
					ImgPathUtil.getGoodImgPath(goods.getId().toString(), goods.getGoodsDetailsPic8(), false));
		}
		if (StringUtils.isNotEmpty(goods.getGoodsDetailsPic9())) {
			goods.setGoodsDetailsPic9(
					ImgPathUtil.getGoodImgPath(goods.getId().toString(), goods.getGoodsDetailsPic9(), false));
		}
		if (StringUtils.isNotEmpty(goods.getGoodsDetailsPic10())) {
			goods.setGoodsDetailsPic10(
					ImgPathUtil.getGoodImgPath(goods.getId().toString(), goods.getGoodsDetailsPic10(), false));
		}
		return goods;
	}

	@Override
	public Goods getDetailById(Long id) {
		Goods goods = getGoodsById(id);
		return getGoodsPicByGoods(goods);
	}
	
	private List<Goods> selectGoodsByCondition(Map<String, String> map) {
		GoodsExample example = new GoodsExample();
		Criteria criteria = example.createCriteria();
		if (map != null) {
			if (StringUtils.isNotEmpty(map.get("id"))) {
				criteria.andIdEqualTo(Long.parseLong(map.get("id")));
			}

			if (StringUtils.isNotEmpty(map.get("categoryId"))) {
				criteria.andCategoryIdEqualTo(Long.parseLong(map.get("categoryId")));
			}

			if (StringUtils.isNotEmpty(map.get("is_delete")) && "false".equals(map.get("is_delete"))) {
				criteria.andIsDeleteEqualTo(Boolean.parseBoolean(map.get("is_delete")));
			}

			if (StringUtils.isNotEmpty(map.get("goodsType"))) {
				criteria.andGoodsTypeEqualTo(map.get("goodsType"));
			}
		}
		return goodsMapper.selectByExample(example);
	}

	@Override
	public Map<String, Object> queryGoodsListByPage(Map<String, String> queryMap) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String pageNum = queryMap.get("pageNum");
		String goodsName = queryMap.get("goodsName");
		String goodsTypeId = queryMap.get("goodsTypeId");
		PageHelper.startPage(Integer.parseInt(pageNum), 10);
		GoodsExample example = new GoodsExample();
		Criteria criteria = example.createCriteria();
		if (StringUtils.isNotEmpty(goodsName)) {
			criteria.andGoodsNameLike("%" + goodsName + "%");
		}
		if (StringUtils.isNotEmpty(goodsTypeId)) {
			criteria.andCategoryIdEqualTo(Long.parseLong(goodsTypeId));
		}
		criteria.andIsDeleteEqualTo(false);
		List<Goods> goodsList = goodsMapper.selectByExample(example);
		PageInfo<Goods> pageInfo = new PageInfo<Goods>(goodsList);
		resultMap.put("goodsList", goodsList);
		resultMap.put("pageInfo", pageInfo);
		return resultMap;
	}

	@Override
	public Result addGoods(Map<String, String> strMap, Map<String, MultipartFile> picMap) {

		Result result = new Result();
		String goodsName = strMap.get("goodsName");
		String goodsCategoryId = strMap.get("goodsCategoryId");
		String goodsPrice = strMap.get("goodsPrice");
		String goodsSoldNumber = strMap.get("goodsSoldNumber");
		String goodsTypeCode = strMap.get("goodsTypeCode");
		String teamGroupNum = strMap.get("teamGroupNum");
		String teamGroupTime = strMap.get("teamGroupTime");

		Goods goods = null;

		if (null != strMap.get("operationtype") && "update".equals(strMap.get("operationtype"))) {
			goods = this.getGoodsById(Long.parseLong(strMap.get("goodsId")));
			goods.setGoodsName(goodsName);
			goods.setCategoryId(Long.parseLong(goodsCategoryId));
			goods.setGoodsPrice(Double.parseDouble(goodsPrice));
			goods.setGoodsSoldNumber(StringUtils.isEmpty(goodsSoldNumber) ? 1 : Integer.parseInt(goodsSoldNumber));
			goods.setGoodsType(goodsTypeCode);
			
			if(GoodsTypeEnum.TEAM_GROUP.getCode().equals(goodsTypeCode)){
				goods.setGoodsGroupNumLimit(Integer.parseInt(teamGroupNum));
				goods.setGoodsGroupTime(Integer.parseInt(teamGroupTime));
			}else{
				goods.setGoodsGroupNumLimit(0);
				goods.setGoodsGroupTime(0);
			}
			
		} else {

			MultipartFile goodsPic = picMap.get("goodsPic");
			if (goodsPic.getSize() <= 0) {
				result.setResult("1");
				result.setMsg("列表图展示为空");
				return result;
			}

			MultipartFile goodsDetailsShowPic = picMap.get("goodsDetailsShowPic");
			if (goodsDetailsShowPic.getSize() <= 0) {
				result.setResult("1");
				result.setMsg("详情展示图为空");
				return result;
			}

			goods = new Goods();
			goods.setCategoryId(Long.parseLong(goodsCategoryId));
			goods.setGoodsPrice(Double.parseDouble(goodsPrice));
			goods.setGoodsName(goodsName);
			goods.setGoodsSoldNumber(StringUtils.isEmpty(goodsSoldNumber) ? 1 : Integer.parseInt(goodsSoldNumber));
			goods.setGoodsSn(Utils.getNextOrderSn(ConstantBasic.OrderTypeCode.SINGAPORE_GOODS_SN));
			goods.setGoodsType(goodsTypeCode);
			
			if(GoodsTypeEnum.TEAM_GROUP.getCode().equals(goodsTypeCode)){
				goods.setGoodsGroupNumLimit(Integer.parseInt(teamGroupNum));
				goods.setGoodsGroupTime(Integer.parseInt(teamGroupTime));
			}
			
			goodsMapper.insertSelective(goods);
		}

		goods = saveGoodsPic(picMap, goods);
		goodsMapper.updateByPrimaryKeySelective(goods);

		result.setMsg("操作成功");
		return result;
	}

	@Override
	public String uploadGoodPic(MultipartFile goodsPic, Goods goods, String index) {

		String img_url = System.currentTimeMillis() + index
				+ goodsPic.getOriginalFilename().substring(goodsPic.getOriginalFilename().lastIndexOf("."));

		try {
			ImgSaveUtil.saveFileImg(goodsPic.getInputStream(),
					ImgPathUtil.getGoodImgPath(goods.getId().toString(), img_url, true));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return img_url;
	}

	/**
	 * 保存商品图片，并返回图片名称
	 * 
	 * @version
	 * @author hyj
	 * @date:2016年11月30日
	 * @param picMap
	 * @param goods
	 * @return Goods
	 */
	private Goods saveGoodsPic(Map<String, MultipartFile> picMap, Goods goods) {

		MultipartFile goodsPic = picMap.get("goodsPic");
		if (goodsPic.getSize() > 0) {
			String goodsPicUrl = uploadGoodPic(goodsPic, goods, "1");
			goods.setGoodsPic(goodsPicUrl);
		}

		MultipartFile goodsDetailsShowPic = picMap.get("goodsDetailsShowPic");
		if (goodsDetailsShowPic.getSize() > 0) {
			String goodsDetailsShowPicUrl = uploadGoodPic(goodsDetailsShowPic, goods, "2");
			goods.setGoodsDetailsShowPic(goodsDetailsShowPicUrl);
		}

		MultipartFile goodsDetailsPic1 = picMap.get("goodsDetailsPic1");
		if (goodsDetailsPic1.getSize() > 0) {
			String goodsDetailsPic1Url = uploadGoodPic(goodsDetailsPic1, goods, "3");
			goods.setGoodsDetailsPic1(goodsDetailsPic1Url);
		}

		MultipartFile goodsDetailsPic2 = picMap.get("goodsDetailsPic2");
		if (goodsDetailsPic2.getSize() > 0) {
			String goodsDetailsPic2Url = uploadGoodPic(goodsDetailsPic2, goods, "4");
			goods.setGoodsDetailsPic2(goodsDetailsPic2Url);
		}

		MultipartFile goodsDetailsPic3 = picMap.get("goodsDetailsPic3");
		if (goodsDetailsPic3.getSize() > 0) {
			String goodsDetailsPic3Url = uploadGoodPic(goodsDetailsPic3, goods, "5");
			goods.setGoodsDetailsPic3(goodsDetailsPic3Url);
		}

		MultipartFile goodsDetailsPic4 = picMap.get("goodsDetailsPic4");
		if (goodsDetailsPic4.getSize() > 0) {
			String goodsDetailsPic4Url = uploadGoodPic(goodsDetailsPic4, goods, "6");
			goods.setGoodsDetailsPic4(goodsDetailsPic4Url);
		}

		MultipartFile goodsDetailsPic5 = picMap.get("goodsDetailsPic5");
		if (goodsDetailsPic5.getSize() > 0) {
			String goodsDetailsPic5Url = uploadGoodPic(goodsDetailsPic5, goods, "7");
			goods.setGoodsDetailsPic5(goodsDetailsPic5Url);
		}

		MultipartFile goodsDetailsPic6 = picMap.get("goodsDetailsPic6");
		if (goodsDetailsPic6.getSize() > 0) {
			String goodsDetailsPic6Url = uploadGoodPic(goodsDetailsPic6, goods, "8");
			goods.setGoodsDetailsPic6(goodsDetailsPic6Url);
		}

		MultipartFile goodsDetailsPic7 = picMap.get("goodsDetailsPic7");
		if (goodsDetailsPic7.getSize() > 0) {
			String goodsDetailsPic7Url = uploadGoodPic(goodsDetailsPic7, goods, "9");
			goods.setGoodsDetailsPic7(goodsDetailsPic7Url);
		}

		MultipartFile goodsDetailsPic8 = picMap.get("goodsDetailsPic8");
		if (goodsDetailsPic8.getSize() > 0) {
			String goodsDetailsPic8Url = uploadGoodPic(goodsDetailsPic8, goods, "10");
			goods.setGoodsDetailsPic8(goodsDetailsPic8Url);
		}

		MultipartFile goodsDetailsPic9 = picMap.get("goodsDetailsPic9");
		if (goodsDetailsPic9.getSize() > 0) {
			String goodsDetailsPic9Url = uploadGoodPic(goodsDetailsPic9, goods, "11");
			goods.setGoodsDetailsPic9(goodsDetailsPic9Url);
		}

		MultipartFile goodsDetailsPic10 = picMap.get("goodsDetailsPic10");
		if (goodsDetailsPic10.getSize() > 0) {
			String goodsDetailsPic10Url = uploadGoodPic(goodsDetailsPic10, goods, "12");
			goods.setGoodsDetailsPic10(goodsDetailsPic10Url);
		}
		return goods;
	}

	@Override
	public void updateGoodsByGoods(Goods goods) {
		goodsMapper.updateByPrimaryKeySelective(goods);

	}

}
