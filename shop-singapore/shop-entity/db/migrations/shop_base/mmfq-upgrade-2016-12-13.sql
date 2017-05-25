alter table `mmfq_goods` 
   add column `goods_type` varchar(10) DEFAULT '1' NOT NULL COMMENT '商品类型参考枚举类: GoodsTypeEnum' after `goods_price`, 
   add column `goods_group_num_limit` int(3) DEFAULT '0' NULL COMMENT '拼团人数限制' after `goods_type`, 
   add column `goods_group_time` int(5) NULL COMMENT '拼团有效时间(单位/小时)' after `goods_group_num_limit`,
   change `goods_sold_number` `goods_sold_number` int(10) NULL  comment '商品售出数量';
   

drop table if exists mmfq_team_member_record;
CREATE TABLE `mmfq_team_member_record` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`team_group_sn`  varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '拼团编号(默认是时间戳)' ,
`goods_id`  bigint(20) NOT NULL COMMENT '商品ID' ,
`goods_name`  varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品名称' ,
`goods_price`  double(10,2) NULL DEFAULT NULL COMMENT '商品价格' ,
`goods_pic`  varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品展示图片' ,
`total_person_num`  int(10) NULL DEFAULT NULL COMMENT '总人数' ,
`buy_person_num`  int(10) NULL DEFAULT NULL COMMENT '已参与人数' ,
`create_time`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
`end_time`  datetime NULL DEFAULT NULL COMMENT '截止时间' ,
`is_finished`  int(1) NULL DEFAULT 0 COMMENT '0-未成团  1-已成团' ,
`finished_time`  datetime NULL DEFAULT NULL COMMENT '成团时间' ,
`team_leader_id`  bigint(20) NULL DEFAULT NULL COMMENT '团长ID' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='开团记录表'
ROW_FORMAT=COMPACT;


   
drop table if exists mmfq_team_order_record;
CREATE TABLE `mmfq_team_order_record` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`order_sn`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单编号' ,
`user_id`  bigint(20) NOT NULL COMMENT '用户id' ,
`team_id`  bigint(20) NOT NULL COMMENT '所参团的团id' ,
`receive_address`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '收货地址信息' ,
`teanm_order_status`  int(2) NOT NULL COMMENT '订单状态 0-未支付 1-已支付(拼团中) 2-拼团成功 3-拼团失败 4-拼团取消' ,
`create_time`  datetime NOT NULL COMMENT '创建时间' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='参团记录表'
ROW_FORMAT=COMPACT;
   
   
   
   
   
   
   
   
   
   
   