/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80031 (8.0.31)
 Source Host           : localhost:3306
 Source Schema         : database

 Target Server Type    : MySQL
 Target Server Version : 80031 (8.0.31)
 File Encoding         : 65001

 Date: 04/06/2024 23:23:17
*/

CREATE DATABASE IF NOT EXISTS `hp-oj-db` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `hp-oj-db`;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`
(
    `id`          bigint auto_increment                                         NOT NULL COMMENT '主键' primary key,
    `username`    varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL     DEFAULT NULL,
    `password`    varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL     DEFAULT NULL,
    `nickname`    varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL     DEFAULT NULL,
#     `role`        varchar(128)                                                           default 'user' not null comment '角色 ''用户|管理员|禁用'' user|admin|ban',
    `phone`       varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL     DEFAULT NULL,
    `email`       varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL     DEFAULT NULL,
    `create_time` datetime                                                               default CURRENT_TIMESTAMP NULL DEFAULT NULL,
    `update_time` datetime                                                               default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    `deleted`     tinyint                                                       NOT NULL DEFAULT 0 COMMENT '逻辑删除',
    UNIQUE INDEX `sys_user_pk` (`username` ASC) USING BTREE,
    UNIQUE INDEX `sys_user_pk2` (`phone` ASC) USING BTREE,
    UNIQUE INDEX `sys_user_pk3` (`email` ASC) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = DYNAMIC;

-- 角色表
CREATE TABLE `sys_role`
(
    `id`          bigint auto_increment NOT NULL COMMENT '主键' primary key,
    `name`        varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色名称',
    `description` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色描述',
    `create_time` datetime default CURRENT_TIMESTAMP NULL DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     tinyint NOT NULL DEFAULT 0 COMMENT '逻辑删除'
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = DYNAMIC;

-- 用户角色关系表
CREATE TABLE `sys_user_role`
(
    `user_id` bigint NOT NULL COMMENT '用户ID',
    `role_id` bigint NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`user_id`, `role_id`)
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = DYNAMIC;

-- 题目表
create table if not exists biz_question
(
    id           bigint auto_increment comment 'id' primary key,
    title        varchar(512)                       null comment '标题',
    content      text                               null comment '内容',
    tags         varchar(1024)                      null comment '标签列表（json 数组）',
    answer       text                               null comment '题目答案',
    score        int      default 0                 not null comment '题目分数',
    submit_num   int      default 0                 not null comment '题目提交数',
    accepted_num int      default 0                 not null comment '题目通过数',
    judge_case   text                               null comment '判题用例（json 数组）',
    judge_config text                               null comment '判题配置（json 对象）',
    user_id      bigint                             not null comment '创建用户 id',
    create_time  datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time  datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted      tinyint  default 0                 not null comment '是否删除',
    index idx_user_id (user_id)
) comment '题目' collate = utf8mb4_unicode_ci;

-- 题目提交表
create table if not exists biz_question_submit
(
    id          bigint auto_increment comment 'id' primary key,
    language    varchar(128)                       not null comment '编程语言',
    code        text                               not null comment '用户代码',
    judge_info  text                               null comment '判题信息（json 对象）',
    status      int      default 0                 not null comment '判题状态（0 - 待判题、1 - 判题中、2 - 成功、3 - 失败）',
    question_id bigint                             not null comment '题目 id',
    user_id     bigint                             not null comment '创建用户 id',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted     tinyint  default 0                 not null comment '是否删除',
    index idx_question_id (question_id),
    index idx_user_id (user_id)
) comment '题目提交';

-- 用户积分表
CREATE TABLE `biz_user_score`
(
    `user_id` bigint NOT NULL COMMENT '用户ID',
    `score` int NOT NULL COMMENT '积分值',
    `reason` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '积分获取原因 通过答题获得积分',
    `score_time` datetime default CURRENT_TIMESTAMP NOT NULL COMMENT '积分获取时间',
    PRIMARY KEY (`user_id`)
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = DYNAMIC;



-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user`
VALUES (1796573229276762113, 'admin', '$2a$10$rFEbLSe2n5O.sVeT80UTeetChNS2y1rEaxqycQA4WLxh6RRns.4M6', '通用Vue模板', 0,
        13376899999, 'example@em.com', '2024-06-01 00:03:31', '2024-06-01 00:03:31', 0);

SET FOREIGN_KEY_CHECKS = 1;

