package com.youzhi.controller;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.youzhi.constant.CheckPasswordUtil;
import com.youzhi.constant.CheckPhoneUtil;
import com.youzhi.constant.Md5Util;
import com.youzhi.model.Goods;
import com.youzhi.model.Student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginController extends Controller {

    //      注册方法，密码MD5转换，存入数据库
    public void register() {
        if (CheckPasswordUtil.passValidate(getPara("pass"))) {
            String new_password = getPara("pass");
            System.out.println(new_password);
            //MD5密码加密
            new_password = Md5Util.encode(new_password);
            System.out.println(new_password);
            Record stu = new Record().set("phone", getPara("phone")).set("name", getPara("name")).set("password", new_password).set("sex", getPara("sex")).set("age", getPara("age"));
            Db.save("student", stu);
            renderJson("s", "success");
        } else {
            renderJson("s", "fault");
        }
    }

    // 登录验证:查询数据库是否存在账号
    public void ajaxLogin() {
        //获取前台数据
        String new_phone = getPara("phone");
        String new_password = getPara("pass");
        //MD5加密
        new_password = Md5Util.encode(new_password);
        //查询数据库
        System.out.println(new_password);
        List<Student> list = new Student().dao().find("select password from student where phone='" + new_phone + "'");
        String pass = list.get(0).get("password");
        //查询数据库查询登录的用户的id放入session中
        List<Student> list_id = new Student().dao().find("select id from student where phone='" + new_phone + "'");
        Integer login_Student_Id = list_id.get(0).get("id");
        if (new_password.equals(pass)) {
            //将登录着的手机号放入session中
            setSessionAttr("loginStudent", new_phone);
            //将登录者的id放入session中
            setSessionAttr("login_Id",login_Student_Id);
            renderJson("s", "success");
        } else {
            renderJson("f", "fault");
        }

    }

    //登录时检查用户是否存在
    public void checkLogin() {
        String new_phone = getPara("phone");
        List<Student> list = new Student().dao().find("select * from student where phone='" + new_phone + "'");
        if (list.size() == 0) {
            renderJson("news", "手机号不存在");
        } else {
            renderJson("news", "");
        }
    }

    //修改密码的方法
    public void modifyPass() {
        if (CheckPasswordUtil.passValidate(getPara("password2"))) {
            String new_phone = getPara("phone");
            String new_password = getPara("password2");
            new_password = Md5Util.encode(new_password);
            Db.update("update student set password='" + new_password + "' where phone='" + new_phone + "'");
//        redirect("/login/Login.html");
            renderJson("s", "success");
        }else{
            renderJson("s", "fault");
        }
    }
    //修改密码检查原密码是否正确
    public void checkPass() {
        String pass1 = getPara("pass");
        pass1 = Md5Util.encode(pass1);
        String phone = getPara("phone");
        List<Student> list = new Student().dao().find("select password from student where phone='" + phone + "'");
        String pass2 = list.get(0).get("password");
        System.out.println(pass1);
        System.out.println(pass2);
        if (pass1.equals(pass2)) {
            renderJson("s", "success");
        } else {
            renderJson("s", "fault");
        }
    }

    //注册用户检查账户是否存在和检查手机号格式是否正确
    public void checkPhone() {
        String new_phone = getPara("phone");
        if (CheckPhoneUtil.phoneValidate(new_phone)) {
            List<Student> list = new Student().dao().find("select * from student where phone='" + new_phone + "'");
            if (list.size() != 0) {
                renderJson("news", "手机号已註冊");
            } else {
                renderJson("news", "手机号未注册");
            }
        } else {
            renderJson("news", "手机号格式错误");
        }
    }

    //修改密码
    public void updatePass() {
        String phone = getSessionAttr("loginStudent");

//        String phone = getPara("phone");
        System.out.println("前台获取的电话号码" + phone);
        setAttr("phone", phone);
        render("/login/modifyPass.html");
    }
}
