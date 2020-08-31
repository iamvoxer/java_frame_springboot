-- 初始化数据库库表数据
--1. 管理用户初始化一个admin用户
INSERT INTO d1_webadmin_user (ID, NAME, PASSWORD,AGE,SEX) SELECT
	'1',
	'admin',
	'e10adc3949ba59abbe56e057f20f883e',
	100,
	0
FROM
	DUAL
WHERE
	NOT EXISTS (
		SELECT
			*
		FROM
			d1_webadmin_user
		WHERE
			NAME = 'admin'
	);
