package com.shop.dao.basic;

import com.shop.entity.basic.UserOrderDetails;
import com.shop.entity.basic.UserOrderDetailsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserOrderDetailsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmfq_user_order_details
     *
     * @mbggenerated Wed Nov 30 16:31:51 CST 2016
     */
    int countByExample(UserOrderDetailsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmfq_user_order_details
     *
     * @mbggenerated Wed Nov 30 16:31:51 CST 2016
     */
    int deleteByExample(UserOrderDetailsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmfq_user_order_details
     *
     * @mbggenerated Wed Nov 30 16:31:51 CST 2016
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmfq_user_order_details
     *
     * @mbggenerated Wed Nov 30 16:31:51 CST 2016
     */
    int insert(UserOrderDetails record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmfq_user_order_details
     *
     * @mbggenerated Wed Nov 30 16:31:51 CST 2016
     */
    int insertSelective(UserOrderDetails record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmfq_user_order_details
     *
     * @mbggenerated Wed Nov 30 16:31:51 CST 2016
     */
    List<UserOrderDetails> selectByExample(UserOrderDetailsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmfq_user_order_details
     *
     * @mbggenerated Wed Nov 30 16:31:51 CST 2016
     */
    UserOrderDetails selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmfq_user_order_details
     *
     * @mbggenerated Wed Nov 30 16:31:51 CST 2016
     */
    int updateByExampleSelective(@Param("record") UserOrderDetails record, @Param("example") UserOrderDetailsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmfq_user_order_details
     *
     * @mbggenerated Wed Nov 30 16:31:51 CST 2016
     */
    int updateByExample(@Param("record") UserOrderDetails record, @Param("example") UserOrderDetailsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmfq_user_order_details
     *
     * @mbggenerated Wed Nov 30 16:31:51 CST 2016
     */
    int updateByPrimaryKeySelective(UserOrderDetails record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmfq_user_order_details
     *
     * @mbggenerated Wed Nov 30 16:31:51 CST 2016
     */
    int updateByPrimaryKey(UserOrderDetails record);
}