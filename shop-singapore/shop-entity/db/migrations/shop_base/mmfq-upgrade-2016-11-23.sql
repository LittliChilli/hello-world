-- ----------------------------
-- Table structure for mmfq_admin
-- ----------------------------
DROP TABLE IF EXISTS `mmfq_admin`;
CREATE TABLE `mmfq_admin` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `login_name` varchar(255) NOT NULL DEFAULT '' COMMENT '登录名',
  `login_passward` varchar(255) NOT NULL COMMENT '登录密码',
  `nice_name` varchar(255) DEFAULT '' COMMENT '昵称',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='管理员表';

-- ----------------------------
-- Table structure for mmfq_goods
-- ----------------------------
DROP TABLE IF EXISTS `mmfq_goods`;
CREATE TABLE `mmfq_goods` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `category_id` bigint(20) NOT NULL COMMENT '类别Id',
  `goods_sn` varchar(255) NOT NULL COMMENT '商品编号',
  `goods_name` varchar(255) NOT NULL COMMENT '商品名称',
  `goods_price` double(10,2) NOT NULL COMMENT '商品价格',
  `goods_sold_number` int(10) DEFAULT NULL COMMENT '商品售出数量',
  `goods_pic` varchar(255) DEFAULT NULL COMMENT '商品列表展示图',
  `goods_details_show_pic` varchar(255) DEFAULT NULL COMMENT '商品详情广告图',
  `goods_details_pic1` varchar(255) DEFAULT NULL COMMENT '商品详情介绍图1',
  `goods_details_pic2` varchar(255) DEFAULT NULL COMMENT '商品详情图介绍2',
  `goods_details_pic3` varchar(255) DEFAULT NULL COMMENT '商品详情图介绍3',
  `goods_details_pic4` varchar(255) DEFAULT NULL COMMENT '商品详情图介绍4',
  `goods_details_pic5` varchar(255) DEFAULT NULL COMMENT '商品详情图介绍5',
  `goods_details_pic6` varchar(255) DEFAULT NULL COMMENT '商品详情图介绍6',
  `goods_details_pic7` varchar(255) DEFAULT NULL COMMENT '商品详情图介绍7',
  `goods_details_pic8` varchar(255) DEFAULT NULL COMMENT '商品详情图介绍8',
  `goods_details_pic9` varchar(255) DEFAULT NULL COMMENT '商品详情图介绍9',
  `goods_details_pic10` varchar(255) DEFAULT NULL COMMENT '商品详情图介绍10',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品表';

-- ----------------------------
-- Table structure for mmfq_user
-- ----------------------------
DROP TABLE IF EXISTS `mmfq_user`;
CREATE TABLE `mmfq_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `login_name` varchar(255) NOT NULL DEFAULT '' COMMENT '登录名',
  `nick_name` varchar(255) DEFAULT '' COMMENT '昵称',
  `login_password` varchar(255) NOT NULL DEFAULT '' COMMENT '登录密码',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Table structure for mmfq_user_order
-- ----------------------------
DROP TABLE IF EXISTS `mmfq_user_order`;
CREATE TABLE `mmfq_user_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `receive_address` varchar(255) NOT NULL DEFAULT '' COMMENT '收货地址',
  `order_sn` varchar(255) NOT NULL DEFAULT '' COMMENT '订单编号',
  `order_name` varchar(255) NOT NULL DEFAULT '' COMMENT '订单名称',
  `order_price` double(10,2) NOT NULL COMMENT '订单价格',
  `order_status` varchar(255) NOT NULL DEFAULT '' COMMENT '订单状态     1.采购中-buying；2.已采购-alreadyBuy；3.确认收货-confirmReceived；4.已取消-cancel',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `goods_id` bigint(20) NOT NULL COMMENT '商品Id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户订单表';

-- ----------------------------
-- Table structure for mmfq_user_receipt_address
-- ----------------------------
DROP TABLE IF EXISTS `mmfq_user_receipt_address`;
CREATE TABLE `mmfq_user_receipt_address` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户Id',
  `receipt_address` varchar(255) NOT NULL DEFAULT '' COMMENT '收货地址',
  `receiver` varchar(255) NOT NULL DEFAULT '' COMMENT '收货人',
  `telephone` varchar(255) NOT NULL DEFAULT '' COMMENT '手机号',
  `is_default` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否默认收货地址',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户收货地址表';



-- ----------------------------
-- Table structure for mmfq_user_shopping_cart
-- ----------------------------
DROP TABLE IF EXISTS `mmfq_user_shopping_cart`;
CREATE TABLE `mmfq_user_shopping_cart` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户Id',
  `goods_id` bigint(20) NOT NULL COMMENT '商品Id',
  `goods_price` double(10,2) NOT NULL COMMENT '商品单价',
   `goods_pic` varchar(255) DEFAULT NULL COMMENT '商品列表展示图',
	`order_sn` varchar(255) DEFAULT '' COMMENT '订单编号',
	`num` int(10) NOT NULL DEFAULT 1 COMMENT '商品数量',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `order_confirm_time` datetime NOT NULL COMMENT '订单确认时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户购物车表';


-- ----------------------------
-- Table structure for mmfq_goods_category
-- ----------------------------
DROP TABLE IF EXISTS `mmfq_goods_category`;
CREATE TABLE `mmfq_goods_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(255) NOT NULL DEFAULT '' COMMENT '类别名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品购物车表';








