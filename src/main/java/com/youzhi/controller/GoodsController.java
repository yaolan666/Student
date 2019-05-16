package com.youzhi.controller;

import com.beust.jcommander.Strings;
import com.jfinal.aop.Inject;
import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;
import com.youzhi.constant.DateUtil;
import com.youzhi.model.Goods;
import com.youzhi.model.Student;
import com.youzhi.service.FileService;
import com.youzhi.service.GoodService;
import com.youzhi.service.StudentService;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.text.ParseException;
import java.util.List;
import java.io.IOException;

public class GoodsController extends Controller {
    @Inject
    GoodService service;

    //获取数据库数据传送到前端
    public void index() {
        String search = getPara("search");
        setAttr("goodsPage", service.paginate(getParaToInt(0, 1), 5, search));
        if (!Strings.isStringEmpty(search)) {
            setAttr("search", search);
        } else {
            setAttr("search", "");

        }
        System.out.println("session的账号" + getSessionAttr("loginStudent"));
        List<Student> list = new Student().dao().find("select name from student where phone='" + getSessionAttr("loginStudent") + "'");
        String name = list.get(0).get("name");
        setAttr("phone", getSessionAttr("loginStudent"));
        setAttr("student_id", getSessionAttr("login_Id"));
        setAttr("name", name);

        render("goodsList.html");
    }

    //删除商品
    public void deleteGoods() {
        service.deleteById(getParaToInt());
        redirect("/goods");
    }

    //修改商品前查询数据放到前端修改框里
    public void editGoods() {
        setAttr("goods", service.findById(getParaToInt()));
        setAttr("imgurl", PropKit.get("update.src"));
        render("editGoods.html");

    }

    //修改商品修改后的数据提交到数据库
    public void updateGoods() throws ParseException {
        UploadFile file = getFile();
        if (file != null) {
            //获取原始名字
            String fileName = file.getOriginalFileName();
            //获取文件后缀
            String type = fileName.substring(fileName.lastIndexOf("."));
            System.out.println();
            //将文件名字改成时间戳
            fileName = DateUtil.dateUtil();
            //得到文件上传的路径
            String path = file.getUploadPath();
            //覆盖原来的文件
            boolean flag = file.getFile().renameTo(new File(path + "\\" + fileName + type));
            System.out.println(fileName);
            fileName = "images/" + fileName + type;
            Record goods = Db.findById("goods", getPara("goods.id")).set("image", fileName).set("goodsName", getPara("goods.goodsName")).set("price", getPara("goods.price")).set("number", getPara("goods.number"));
            Db.update("goods", goods);
//        Goods goods = getBean(Goods.class);
//        goods.update();
            redirect("/goods");
        }else {
            Record goods = Db.findById("goods", getPara("goods.id")).set("image",getPara("goods.image") ).set("goodsName", getPara("goods.goodsName")).set("price", getPara("goods.price")).set("number", getPara("goods.number"));
            Db.update("goods", goods);
            redirect("/goods");

        }
    }

    //增加商品
    public void addGoods() throws ParseException {
        UploadFile file = getFile();

        if (file != null) {
            //获取原始名字
            String fileName = file.getOriginalFileName();
            //获取文件后缀
            String type = fileName.substring(fileName.lastIndexOf("."));
            //将文件名字改成时间戳
            fileName = DateUtil.dateUtil();
            String path = file.getUploadPath();
            boolean flag = file.getFile().renameTo(new File(path + "\\" + fileName + type));
            System.out.println(fileName);
            fileName = "images/" + fileName + type;
            Record goods = new Record().set("image", fileName).set("goodsName", getPara("goodsName")).set("price", getPara("price")).set("number", getPara("number"));
            Db.save("goods", goods);
//        String goodsName = getPara("goodsName");
//        List<Goods> list = new Goods().dao().find("select id from goods where goodsName='"+goodsName+"'");
//        String id = list.get(0).get("id");
//        setSessionAttr("id",id);
            redirect("/goods");
        } else {
            Record goods = new Record().set("goodsName", getPara("goodsName")).set("price", getPara("price")).set("number", getPara("number"));
            Db.save("goods", goods);
            redirect("/goods");
        }
    }

    //单独上传图片查询商品的id根据增加图片
    public void query_goodId() {
        Integer id = getParaToInt();
        System.out.println(id);
        setAttr("id", id);
        render("upload.html");
    }

    //图片上传方法
    public void upload() throws ParseException {
        UploadFile file = getFile();
        //获取原始名字
        String fileName = file.getOriginalFileName();
        //获取文件后缀
        String type = fileName.substring(fileName.lastIndexOf("."));
        //将文件名字改成时间戳
        fileName = DateUtil.dateUtil();

        String id = getPara("goodId");
        String path = file.getUploadPath();
        boolean flag = file.getFile().renameTo(new File(path + "\\" + fileName + type));
        System.out.println(fileName);
        fileName = "images/" + fileName + type;
        //存入数数据库
        Db.update("update goods set image='" + fileName + "'where id='" + id + "'");
        redirect("/goods/uploadSuccess.html");
//        redirect("/goods");
    }
}
