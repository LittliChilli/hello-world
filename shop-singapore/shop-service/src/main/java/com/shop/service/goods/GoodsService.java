package com.shop.service.goods;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.shop.common.Result;
import com.shop.entity.basic.Goods;

public interface GoodsService {

	/**
	 * 查询所有商品
	 * 
	 * @return
	 */

	List<Goods> queryGoodsByCondition(Map<String, String> map);

	/**
	 * 根据商品Id查询商品
	 * 
	 * @param goodsId
	 * @return
	 */
	Goods getGoodsById(Long goodsId);

	/**
	 * 添加商品列表图片
	 * 
	 * @param goodsId
	 * @return
	 */
	Goods getGoodsPicByGoods(Goods goods);

	/**
	 * 获取商品详情
	 * 
	 * @param id
	 * @return
	 */
	Goods getDetailById(Long id);

	/**
	 * 
	 * 分页查询商品列表
	 * 
	 * lion 2016年11月30日
	 * 
	 * @param queryMap
	 * @return
	 */
	Map<String, Object> queryGoodsListByPage(Map<String, String> queryMap);

	/**
	 * 
	 * 添加商品信息
	 * 
	 * lion 2016年11月30日
	 * 
	 * @param strMap
	 * @param picMap
	 */
	Result addGoods(Map<String, String> strMap, Map<String, MultipartFile> picMap);

	/**
	 * 添加商品图片
	 * 
	 * @version
	 * @author hyj
	 * @date:2016年11月30日
	 * @param goods
	 * @return String
	 */
	String uploadGoodPic(MultipartFile goodsPic, Goods goods, String index);

	/**
	 * 更新商品
	 * 
	 * @version
	 * @author hyj
	 * @date:2016年12月1日
	 * @param goods
	 *            void
	 */
	void updateGoodsByGoods(Goods goods);

	/**
	 * 查询所有有效的商品
	 * 
	 * @version
	 * @author zousheng
	 * @date:2016年12月5日
	 *
	 * @return
	 */
	List<Goods> queryAllEffectiveGoods();
}
