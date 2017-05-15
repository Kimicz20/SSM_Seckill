-- 秒杀执行存储过程
DELIMITER $$ -- console ; 转化 $$
-- 定义存储过程
-- 参数:in 输入参数;out 输出参数
-- row_count() :返回上一条修改类型 的结果: 0:未修改;>0:修改行数;<0 :sql 错误/未执行

CREATE PROCEDURE `seckill`.`execute_seckill`
-- 定义事务的输入/输出参数
  (in v_seckill_id bigint,in v_phone bigint,
    in v_kill_time TIMESTAMP ,out r_result int)
  BEGIN
    DECLARE insert_count int DEFAULT 0;
--     开启事务
    START TRANSACTION;
--     执行秒杀插入操作
    INSERT ignore into success_killed
      (seckill_id,user_phone,create_time)
      VALUES (v_seckill_id,v_phone,v_kill_time);
--     row_count()根据判断插入操作结果
    SELECT row_count() into insert_count;
    IF (insert_count = 0) THEN
      ROLLBACK ;
--       重复秒杀
      SET r_result =-1;
    ELSEIF(insert_count < 0) THEN
      ROLLBACK ;
--       秒杀错误
        SET r_result =-2;
    ELSE
--     更新库存操作
      UPDATE seckill
      SET number = number -1
      WHERE seckill_id = v_seckill_id
        AND end_time > v_kill_time
        AND start_time < v_kill_time
        AND number >0;
      SELECT row_count() into insert_count;
      IF (insert_count = 0) THEN
        ROLLBACK ;
        SET r_result = 0;
      ELSEIF (insert_count < 0) THEN
        ROLLBACK ;
        SET r_result =-2;
      ELSE
        COMMIT ;
        SET r_result = 1;
      END IF;
    END IF;
END;
$$

DELIMITER ;

set @r_result = -3;
-- 执行存储过程
call execute_seckill(1003,18106567080,now(),@r_result);
-- 获取结果
select @r_result;