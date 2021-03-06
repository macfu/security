-- 删除数据库
DROP DATABASE mldn ;
-- 创建数据库
CREATE DATABASE mldn DEFAULT CHARACTER SET utf8 ;
-- 使用数据库
USE mldn ;
-- 创建用户表（mid：登录ID、name：真实姓名、password：登录密码、enabled：启用状态）
-- enabled取值有两种：启用（enabled=1）、锁定（enabled=0）
CREATE TABLE member(
	mid				VARCHAR(50)	,
	name				VARCHAR(50) ,
	password			VARCHAR(68)	,
	enabled			INT(1) ,
	CONSTRAINT pk_mid PRIMARY KEY(mid)
) engine=innodb ;
-- 创建角色表（rid：角色ID，也是授权检测的名称、title：角色名称）
CREATE TABLE role (
	rid				VARCHAR(50) ,
	title			VARCHAR(50) ,
	CONSTRAINT pk_rid PRIMARY KEY(rid)
) engine=innodb ;
-- 创建用户角色关联表（mid：用户ID、rid：角色ID）
CREATE TABLE member_role (
	mid				VARCHAR(50) ,
	rid				VARCHAR(50) ,
	CONSTRAINT fk_mid FOREIGN KEY(mid) REFERENCES member(mid) ON DELETE CASCADE ,
	CONSTRAINT fk_rid FOREIGN KEY(rid) REFERENCES role(rid) ON DELETE CASCADE
) engine=innodb ;
-- 增加用户数据（admin/hello、mldn/java）
INSERT INTO member(mid,name,password,enabled) VALUES ('admin','李兴华','{bcrypt}$2a$10$2y7higVhnHCn2L8//r/EVed2zi/LrQ.Y.svV.oeLqUM8xfUx5JWQC',1) ;
INSERT INTO member(mid,name,password,enabled) VALUES ('mldn','魔乐科技','{bcrypt}$2a$10$vjXs780rO3rF8ZAXuBL4..c9icL4JDvr3sweCIU9y/QWiYlHgbKGa',0) ;
-- 增加角色数据
INSERT INTO role(rid,title) VALUES ('ROLE_ADMIN','管理员') ;
INSERT INTO role(rid,title) VALUES ('ROLE_USER','用户') ;
-- 增加用户与角色信息
INSERT INTO member_role(mid,rid) VALUES ('admin','ROLE_ADMIN') ;
INSERT INTO member_role(mid,rid) VALUES ('admin','ROLE_USER') ;
INSERT INTO member_role(mid,rid) VALUES ('mldn','ROLE_USER') ;
-- 提交事务
COMMIT ;




SELECT mid AS username , password,enabled FROM member WHERE mid=?

SELECT mid AS username,rid AS authorities FROM member_role WHERE mid=?