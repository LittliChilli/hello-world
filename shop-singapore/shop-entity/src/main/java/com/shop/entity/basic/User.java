package com.shop.entity.basic;

import java.util.Date;

public class User {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mmfq_user.id
     *
     * @mbggenerated Wed Nov 23 16:18:05 CST 2016
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mmfq_user.login_name
     *
     * @mbggenerated Wed Nov 23 16:18:05 CST 2016
     */
    private String loginName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mmfq_user.nick_name
     *
     * @mbggenerated Wed Nov 23 16:18:05 CST 2016
     */
    private String nickName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mmfq_user.login_password
     *
     * @mbggenerated Wed Nov 23 16:18:05 CST 2016
     */
    private String loginPassword;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mmfq_user.create_time
     *
     * @mbggenerated Wed Nov 23 16:18:05 CST 2016
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mmfq_user.update_time
     *
     * @mbggenerated Wed Nov 23 16:18:05 CST 2016
     */
    private Date updateTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mmfq_user.id
     *
     * @return the value of mmfq_user.id
     *
     * @mbggenerated Wed Nov 23 16:18:05 CST 2016
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mmfq_user.id
     *
     * @param id the value for mmfq_user.id
     *
     * @mbggenerated Wed Nov 23 16:18:05 CST 2016
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mmfq_user.login_name
     *
     * @return the value of mmfq_user.login_name
     *
     * @mbggenerated Wed Nov 23 16:18:05 CST 2016
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mmfq_user.login_name
     *
     * @param loginName the value for mmfq_user.login_name
     *
     * @mbggenerated Wed Nov 23 16:18:05 CST 2016
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mmfq_user.nick_name
     *
     * @return the value of mmfq_user.nick_name
     *
     * @mbggenerated Wed Nov 23 16:18:05 CST 2016
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mmfq_user.nick_name
     *
     * @param nickName the value for mmfq_user.nick_name
     *
     * @mbggenerated Wed Nov 23 16:18:05 CST 2016
     */
    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mmfq_user.login_password
     *
     * @return the value of mmfq_user.login_password
     *
     * @mbggenerated Wed Nov 23 16:18:05 CST 2016
     */
    public String getLoginPassword() {
        return loginPassword;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mmfq_user.login_password
     *
     * @param loginPassword the value for mmfq_user.login_password
     *
     * @mbggenerated Wed Nov 23 16:18:05 CST 2016
     */
    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword == null ? null : loginPassword.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mmfq_user.create_time
     *
     * @return the value of mmfq_user.create_time
     *
     * @mbggenerated Wed Nov 23 16:18:05 CST 2016
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mmfq_user.create_time
     *
     * @param createTime the value for mmfq_user.create_time
     *
     * @mbggenerated Wed Nov 23 16:18:05 CST 2016
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mmfq_user.update_time
     *
     * @return the value of mmfq_user.update_time
     *
     * @mbggenerated Wed Nov 23 16:18:05 CST 2016
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mmfq_user.update_time
     *
     * @param updateTime the value for mmfq_user.update_time
     *
     * @mbggenerated Wed Nov 23 16:18:05 CST 2016
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}