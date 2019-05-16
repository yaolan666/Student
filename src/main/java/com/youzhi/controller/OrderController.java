package com.youzhi.controller;

import com.jfinal.core.Controller;
import com.jfinal.kit.JMap;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.SqlPara;
import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import com.youzhi.model.Goods;
import com.youzhi.model.Student;

import java.util.List;

public class OrderController extends Controller {

    public void cartGoods(){
        Integer goods_id = getParaToInt();
        Integer student_ID = getSessionAttr("login_Id");
        System.out.println("goods_id:"+goods_id);
        System.out.println("student_ID:"+student_ID);
        //根据商品id查询商品名称
        String sql = new Goods().dao().getSql("select_goodsName_byId");
        Goods goods = new Goods().dao().findFirst(sql,goods_id);
        String goodsName = goods.get("goodsName");
        //向购物车表中插入数据
        Record cart = new Record().set("c_goods_id",goods_id).set("c_goods_name",goodsName).set("c_student_id",student_ID);
        Db.save("cart", cart);
        redirect("/goods/addGoodsSuccess.html");
    }

}
