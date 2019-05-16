#sql ("FuzzyQuery")
  select * from goods where goodsName like ?
#end
#sql ("student_name")
  select name from student where id =?
#end

-- 添加购物车的时间根据商品ID查询商品名字
#sql("select_goodsName_byId")
  select goodsName from goods where id =?
#end
-- #sql("insertIntoCart")
--   insert into cart (c_goods_id, c_goods_name,c_student_id) values(?,'l',2)
-- #end