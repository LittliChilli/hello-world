package com.shop.dao.basic;

import com.shop.entity.basic.GoodsCategory;
import com.shop.entity.basic.GoodsCategoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GoodsCategoryMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmfq_goods_category
     *
     * @mbggenerated Tue Nov 29 11:21:34 CST 2016
     */
    int countByExample(GoodsCategoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmfq_goods_category
     *
     * @mbggenerated Tue Nov 29 11:21:34 CST 2016
     */
    int deleteByExample(GoodsCategoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmfq_goods_category
     *
     * @mbggenerated Tue Nov 29 11:21:34 CST 2016
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmfq_goods_category
     *
     * @mbggenerated Tue Nov 29 11:21:34 CST 2016
     */
    int insert(GoodsCategory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmfq_goods_category
     *
     * @mbggenerated Tue Nov 29 11:21:34 CST 2016
     */
    int insertSelective(GoodsCategory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmfq_goods_category
     *
     * @mbggenerated Tue Nov 29 11:21:34 CST 2016
     */
    List<GoodsCategory> selectByExample(GoodsCategoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmfq_goods_category
     *
     * @mbggenerated Tue Nov 29 11:21:34 CST 2016
     */
    GoodsCategory selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmfq_goods_category
     *
     * @mbggenerated Tue Nov 29 11:21:34 CST 2016
     */
    int updateByExampleSelective(@Param("record") GoodsCategory record, @Param("example") GoodsCategoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmfq_goods_category
     *
     * @mbggenerated Tue Nov 29 11:21:34 CST 2016
     */
    int updateByExample(@Param("record") GoodsCategory record, @Param("example") GoodsCategoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmfq_goods_category
     *
     * @mbggenerated Tue Nov 29 11:21:34 CST 2016
     */
    int updateByPrimaryKeySelective(GoodsCategory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmfq_goods_category
     *
     * @mbggenerated Tue Nov 29 11:21:34 CST 2016
     */
    int updateByPrimaryKey(GoodsCategory record);
}