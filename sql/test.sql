-- 建库语句
create database if not exists `hp-oj-db` default character set utf8mb4 collate utf8mb4_unicode_ci;
use `hp-oj-db`;

set names utf8mb4;
set foreign_key_checks = 0;

-- 用户表
create table if not exists `sys_user`
(
    `id`          bigint auto_increment comment 'id' primary key,
    `username`    varchar(256)                       not null comment '用户名',
    `password`    varchar(128)                       not null comment '密码',
    `nickname`    varchar(256)                       not null comment '昵称',
    `phone`       varchar(20)                        null comment '电话',
    `email`       varchar(128)                       null comment '邮箱',
    `create_time` datetime default current_timestamp null comment '创建时间',
    `update_time` datetime default current_timestamp not null on update current_timestamp comment '更新时间',
    `deleted`     tinyint                            not null default 0 comment '逻辑删除',
    unique index `idx_username` (`username` asc),
    unique index `idx_phone` (`phone` asc),
    unique index `idx_email` (`email` asc)
) engine = innodb
  character set = utf8mb4
  collate = utf8mb4_unicode_ci
  row_format = dynamic;

-- 角色表
create table if not exists `sys_role`
(
    `id`          bigint auto_increment comment '主键' primary key,
    `name`        varchar(256)                       null comment '角色名称',
    `description` varchar(512)                       null comment '角色描述',
    `create_time` datetime default current_timestamp null comment '创建时间',
    `update_time` datetime default current_timestamp not null on update current_timestamp comment '更新时间',
    `deleted`     tinyint                            not null default 0 comment '逻辑删除'
) engine = innodb
  character set = utf8mb4
  collate = utf8mb4_unicode_ci
  row_format = dynamic;

-- 用户角色关系表
create table if not exists `sys_user_role`
(
    `user_id` bigint not null comment '用户ID',
    `role_id` bigint not null comment '角色ID'
) engine = innodb
  character set = utf8mb4
  collate = utf8mb4_unicode_ci
  row_format = dynamic;

-- 题目表
create table if not exists `biz_question`
(
    `id`           bigint auto_increment comment 'id' primary key,
    `title`        varchar(512)                       null comment '标题',
    `content`      text                               null comment '内容',
    `tags`         varchar(1024)                      null comment '标签列表（json 数组）',
    `answer`       text                               null comment '题目答案',
    `score`        int      default 0                 not null comment '题目分数',
    `submit_num`   int      default 0                 not null comment '题目提交数',
    `accepted_num` int      default 0                 not null comment '题目通过数',
    `judge_case`   text                               null comment '判题用例（json 数组）',
    `judge_config` text                               null comment '判题配置（json 对象）',
    `user_id`      bigint                             not null comment '创建用户 id',
    `create_time`  datetime default current_timestamp not null comment '创建时间',
    `update_time`  datetime default current_timestamp not null on update current_timestamp comment '更新时间',
    `deleted`      tinyint  default 0                 not null comment '是否删除',
    index `idx_user_id` (`user_id`)
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
) comment '题目提交' collate = utf8mb4_unicode_ci;

-- 用户积分表
create table if not exists `biz_user_score`
(
    `id`          bigint auto_increment comment 'id' primary key,
    `user_id`     bigint                             not null comment '用户 id',
    `score`       int                                not null comment '积分',
    `description` varchar(512)                       null comment '描述',
    `create_time` datetime default current_timestamp not null comment '创建时间',
    `update_time` datetime default current_timestamp not null on update current_timestamp comment '更新时间',
    `deleted`     tinyint                            not null default 0 comment '逻辑删除',
    index `idx_user_id` (`user_id`)
) comment '用户积分' collate = utf8mb4_unicode_ci;

-- 用户收藏表
create table if not exists `biz_user_collect`
(
    `id`          bigint auto_increment comment 'id' primary key,
    `user_id`     bigint                             not null comment '用户 id',
    `question_id` bigint                             not null comment '题目 id',
    `create_time` datetime default current_timestamp not null comment '创建时间',
    `update_time` datetime default current_timestamp not null on update current_timestamp comment '更新时间',
    `deleted`     tinyint                            not null default 0 comment '逻辑删除',
    index `idx_user_id` (`user_id`),
    index `idx_question_id` (`question_id`)
) comment '用户收藏' collate = utf8mb4_unicode_ci;

-- 插入数据
insert into `sys_user`
values (1796573229276762113, 'admin', '$2a$10$rFEbLSe2n5O.sVeT80UTeetChNS2y1rEaxqycQA4WLxh6RRns.4M6', '通用Vue模板',
        '13376899999', 'example@em.com', '2024-06-01 00:03:31', '2024-06-10 00:41:17', 0);
insert into `sys_user`
values (1810926353372811266, 'user', '$2a$10$IFMShDid0iRdTd76Yh2x6eLwjC5dCPYWGXwiT0qmJG.urqHKhSHHq', '测试用户', NULL,
        'example@qq.com', NULL, '2024-07-10 14:37:42', 0);
insert into `sys_user`
values (1810928438768177154, '学生登录', '$2a$10$I.76.hs1phI7lXwQrK631u1qHf54QzZUD9sxgfV46amjb6RsxmAMS', 'ANNA', NULL,
        '2513773079@qq.com', NULL, '2024-07-10 14:45:59', 0);

insert into `biz_question`
values (1800187443323846658, '正则表达式匹配',
        '给你一个字符串 `s` 和一个字符规律 `p`，请你来实现一个支持 `\'.\'` 和 `\'*\'` 的正则表达式匹配。\n\n- `\'.\'` 匹配任意单个字符\n- `\'*\'` 匹配零个或多个前面的那一个元素\n\n所谓匹配，是要涵盖 **整个** 字符串 `s`的，而不是部分字符串。\n\n \n\n**示例 1：**\n\n**输入：**s = \"aa\", p = \"a\"\n**输出：**false\n**解释：**\"a\" 无法匹配 \"aa\" 整个字符串。\n\n**示例 2:**\n\n**输入：**s = \"aa\", p = \"a*\"\n**输出：**true\n**解释：**因为 \'*\' 代表可以匹配零个或多个前面的那一个元素, 在这里前面的元素就是 \'a\'。因此，字符串 \"aa\" 可被视为 \'a\' 重复了一次。\n\n**示例 3：**\n\n**输入：**s = \"ab\", p = \".*\"\n**输出：**true\n**解释：**\".*\" 表示可匹配零个或多个（\'*\'）任意字符（\'.\'）。',
        '[\"递归\",\"字符串\",\"动态规划\"]',
        '```python \n`class Solution:\n    def isMatch(self, s: str, p: str) -> bool:\n        m, n = len(s) + 1, len(p) + 1\n        dp = [[False] * n for _ in range(m)]\n        dp[0][0] = True\n        # 初始化首行\n        for j in range(2, n, 2):\n            dp[0][j] = dp[0][j - 2] and p[j - 1] == \'*\'\n        # 状态转移\n        for i in range(1, m):\n            for j in range(1, n):\n                if p[j - 1] == \'*\':\n                    if dp[i][j - 2]: dp[i][j] = True                              # 1.\n                    elif dp[i - 1][j] and s[i - 1] == p[j - 2]: dp[i][j] = True   # 2.\n                    elif dp[i - 1][j] and p[j - 2] == \'.\': dp[i][j] = True        # 3.\n                else:\n                    if dp[i - 1][j - 1] and s[i - 1] == p[j - 1]: dp[i][j] = True # 1.\n                    elif dp[i - 1][j - 1] and p[j - 1] == \'.\': dp[i][j] = True    # 2.\n        return dp[-1][-1]\n`\n\n',
        3, 0, 0, '[{\"output\":\"false\",\"input\":\"s=\\\"aa\\\",p=\\\"a\\\"\"}]',
        '{\"stackLimit\":500,\"timeLimit\":34,\"memoryLimit\":500}', 1796573229276762113, '2024-06-10 23:25:07',
        '2024-06-10 23:25:07', 0);
insert into `biz_question`
values (1800188991068160001, '二叉树的层序遍历',
        '- 题目描述：\n	- 给你二叉树的根节点 `root` ，返回其节点值的 **层序遍历** 。 （即逐层地，从左到右访问所有节点）。\n- 示例 1：\n\n![](https://assets.leetcode.com/uploads/2021/02/19/tree1.jpg)\n\n- 输入：root = [3,9,20, null, null, 15,7]\n- 输出：[[3],[9,20],[15,7]]',
        '[\"遍历\",\"二叉树\"]',
        '```python\n`# Definition for a binary tree node.\n# class TreeNode:\n#     def __init__(self, val=0, left=None, right=None):\n#         self.val = val\n#         self.left = left\n#         self.right = right\nclass Solution:\n    def levelOrder(self, root: Optional[TreeNode]) -> List[List[int]]:\n        #判断根节点是否为空\n        if root is None:\n            return []\n        #初始化最终答案数组\n        ans=[]\n        #初始化存放当前层节点数组\n        cur=[root]\n        while cur:\n            #存放当前节点的子节点\n            tmd=[]\n            #存放当前节点的值\n            vals=[]\n            #遍历当前层的每一个节点得到他们的子节点以及节点值\n            for node in cur:\n                vals.append(node.val)\n                if node.left:\n                    tmd.append(node.left)\n                if node.right:\n                    tmd.append(node.right)\n            #更新cur\n            cur=tmd\n            #将当前层的节点值加入进最终输出的答案列表\n            ans.append(vals)\n        return ans`\n        \n	',
        3, 0, 0, '[{\"output\":\"[[3],[9,20],[15,7]]\",\"input\":\"[3,9,20,null,null,15,7]\"}]',
        '{\"stackLimit\":500,\"timeLimit\":46,\"memoryLimit\":500}', 1796573229276762113, '2024-06-10 23:31:16',
        '2024-06-10 23:38:48', 0);
insert into `biz_question`
values (1800189734848282625, '二叉树的锯齿形层序遍历',
        '- 题目描述：\n	- 给你二叉树的根节点 `root` ，返回其节点值的 **锯齿形层序遍历** 。（即先从左往右，再从右往左进行下一层遍历，以此类推，层与层之间交替进行）。\n- 示例 1：\n\n![](https://assets.leetcode.com/uploads/2021/02/19/tree1.jpg)\n\n- 输入：root = [3,9,20, null, null, 15,7]\n- 输出：[[3],[20,9],[15,7]]',
        '[\"层序遍历\",\"二叉树\"]',
        '```python\n# Definition for a binary tree node.\n# class TreeNode:\n#     def __init__(self, val=0, left=None, right=None):\n#         self.val = val\n#         self.left = left\n#         self.right = right\nclass Solution:\n    def zigzagLevelOrder(self, root: Optional[TreeNode]) -> List[List[int]]:\n        if root is None:\n            return []\n        q=deque([root])\n        change=0\n        ans=[]\n        #确实直接反转值的那个数组就好了\n        while q:\n            vals=[]\n            l=len(q)\n            for _ in range(l):\n                node=q.popleft()\n                vals.append(node.val)\n                if node.left:q.append(node.left)\n                if node.right:q.append(node.right)\n            \n            ans.append(vals[::-1] if change else vals)\n            #变量不可以直接用减号表示值的反转\n            change=not change\n        return ans\n            \n```',
        3, 0, 0, '[{\"output\":\"[[3],[20,9],[15,7]]\",\"input\":\"[3,9,20,null,null,15,7]\"}]',
        '{\"stackLimit\":500,\"timeLimit\":45,\"memoryLimit\":500}', 1796573229276762113, '2024-06-10 23:34:13',
        '2024-06-10 23:38:17', 0);

insert into `biz_question_submit`
values (1801052672573423618, 'java',
        'public class Main {\r\n  public static void main(String[] args) {\r\n    System.out.println(Integer.valueOf(args[0]) + Integer.valueOf(args[1]));\r\n  }\r\n}\r\n',
        NULL, 1, 1800188991068160001, 1796573229276762113, '2024-06-13 08:43:15', '2024-06-13 08:43:15', 0);
insert into `biz_question_submit`
values (1801056232895799297, 'java',
        'public class Main {\r\n    public static void main(String[] args) {\r\n        System.out.println(Integer.valueOf(args[0]) + Integer.valueOf(args[1]));\r\n    }\r\n}\r\n',
        NULL, 1, 1800188991068160001, 1796573229276762113, '2024-06-13 08:57:24', '2024-06-13 08:57:24', 0);
insert into `biz_question_submit`
values (1801058408175099905, 'java',
        'public class Main {\r\n    public static void main(String[] args) {\r\n        System.out.println(Integer.valueOf(args[0]) + Integer.valueOf(args[1]));\r\n    }\r\n}\r\n',
        NULL, 1, 1800189734848282625, 1796573229276762113, '2024-06-13 09:06:02', '2024-06-13 09:06:02', 0);
insert into `biz_question_submit`
values (1801063836078432257, 'java',
        'public class Main {\r\n    public static void main(String[] args) {\r\n        System.out.println(Integer.valueOf(args[0]) + Integer.valueOf(args[1]));\r\n    }\r\n}\r\n',
        NULL, 1, 1800188991068160001, 1796573229276762113, '2024-06-13 09:27:36', '2024-06-13 09:27:36', 0);
insert into `biz_question_submit`
values (1801086834055438338, 'java',
        'public class Main {\r\n    public static void main(String[] args) {\r\n        System.out.println(Integer.valueOf(args[0]) + Integer.valueOf(args[1]));\r\n    }\r\n}\r\n',
        NULL, 2, 1800188991068160001, 1796573229276762113, '2024-06-13 10:59:00', '2024-06-13 10:59:00', 0);
insert into `biz_question_submit`
values (1810937563879182337, 'java',
        'public class Solution {\r\n    public boolean isMatch(String s, String p) {\r\n        int m = s.length();\r\n        int n = p.length();\r\n\r\n        boolean[][] f = new boolean[m + 1][n + 1];\r\n        f[0][0] = true;\r\n        for (int i = 0; i <= m; ++i) {\r\n            for (int j = 1; j <= n; ++j) {\r\n                if (p.charAt(j - 1) == \'*\') {\r\n                    f[i][j] = f[i][j - 2];\r\n                    if (matches(s, p, i, j - 1)) {\r\n                        f[i][j] = f[i][j] || f[i - 1][j];\r\n                    }\r\n                } else {\r\n                    if (matches(s, p, i, j)) {\r\n                        f[i][j] = f[i - 1][j - 1];\r\n                    }\r\n                }\r\n            }\r\n        }\r\n        return f[m][n];\r\n    }\r\n\r\n    public boolean matches(String s, String p, int i, int j) {\r\n        if (i == 0) {\r\n            return false;\r\n        }\r\n        if (p.charAt(j - 1) == \'.\') {\r\n            return true;\r\n        }\r\n        return s.charAt(i - 1) == p.charAt(j - 1);\r\n    }\r\n}\r\n',
        NULL, 1, 1800187443323846658, 1810928438768177154, '2024-07-10 15:22:15', '2024-07-10 15:22:15', 0);
insert into `biz_question_submit`
values (1810937603565686785, 'java',
        'public class Solution {\r\n    public boolean isMatch(String s, String p) {\r\n        int m = s.length();\r\n        int n = p.length();\r\n\r\n        boolean[][] f = new boolean[m + 1][n + 1];\r\n        f[0][0] = true;\r\n        for (int i = 0; i <= m; ++i) {\r\n            for (int j = 1; j <= n; ++j) {\r\n                if (p.charAt(j - 1) == \'*\') {\r\n                    f[i][j] = f[i][j - 2];\r\n                    if (matches(s, p, i, j - 1)) {\r\n                        f[i][j] = f[i][j] || f[i - 1][j];\r\n                    }\r\n                } else {\r\n                    if (matches(s, p, i, j)) {\r\n                        f[i][j] = f[i - 1][j - 1];\r\n                    }\r\n                }\r\n            }\r\n        }\r\n        return f[m][n];\r\n    }\r\n\r\n    public boolean matches(String s, String p, int i, int j) {\r\n        if (i == 0) {\r\n            return false;\r\n        }\r\n        if (p.charAt(j - 1) == \'.\') {\r\n            return true;\r\n        }\r\n        return s.charAt(i - 1) == p.charAt(j - 1);\r\n    }\r\n}\r\n',
        NULL, 1, 1800187443323846658, 1810928438768177154, '2024-07-10 15:22:24', '2024-07-10 15:22:24', 0);

insert into `biz_user_score`
values (1800187443323846658, 1796573229276762113, 0, '提交题目', '2024-06-10 23:25:07', '2024-06-10 23:25:07', 0);
insert into `biz_user_score`
values (1800188991068160001, 1796573229276762113, 0, '提交题目', '2024-06-10 23:31:16', '2024-06-10 23:31:16', 0);
insert into `biz_user_score`
values (1800189734848282625, 1796573229276762113, 0, '提交题目', '2024-06-10 23:34:13', '2024-06-10 23:34:13', 0);

insert into `biz_user_collect`
values (1800187443323846658, 1796573229276762113, 1800188991068160001, '2024-06-10 23:25:07', '2024-06-10 23:25:07', 0);
insert into `biz_user_collect`
values (1800188991068160001, 1796573229276762113, 1800189734848282625, '2024-06-10 23:31:16', '2024-06-10 23:31:16', 0);
insert into `biz_user_collect`
values (1800189734848282625, 1796573229276762113, 1800187443323846658, '2024-06-10 23:34:13', '2024-06-10 23:34:13', 0);


-- 2）至少建立2个针对该系统的、有实用性的存储过程； 3）至少建立2个针对该系统的触发器；

-- 存储过程
-- 1. 查询用户提交的题目
drop procedure if exists `sp_query_user_submit`;
delimiter //
create procedure `sp_query_user_submit`(
    in p_user_id bigint
)
begin
    select * from biz_question_submit where user_id = p_user_id;
end;
//
delimiter ;

-- 2. 查询用户收藏的题目
drop procedure if exists `sp_query_user_collect`;
delimiter //
create procedure `sp_query_user_collect`(
    in p_user_id bigint
)
begin
    select * from biz_user_collect where user_id = p_user_id;
end;
//
delimiter ;

-- 触发器
-- 1. 用户提交题目后更新题目提交数
drop trigger if exists `tr_update_question_submit_num`;
delimiter //
create trigger `tr_update_question_submit_num`
    after insert
    on `biz_question_submit`
    for each row
begin
    update biz_question set submit_num = submit_num + 1 where id = new.question_id;
end;
//
delimiter ;

-- 2. 用户提交题目后更新用户积分
drop trigger if exists `tr_update_user_score`;
delimiter //
create trigger `tr_update_user_score`
    after insert
    on `biz_question_submit`
    for each row
begin
    declare question_score int;
    select score into question_score from biz_question where id = new.question_id;
    update biz_user_score set score = score + question_score where user_id = new.user_id;
end;
//
delimiter ;

-- 测试
-- 1. 查询用户提交的题目
call sp_query_user_submit(1796573229276762113);

-- 2. 查询用户收藏的题目
call sp_query_user_collect(1796573229276762113);

