package com.youzhi.controller;

import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.aop.Inject;
import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;
import com.youzhi.interceptor.DemoInterceptor;
import com.youzhi.model.Student;
import com.youzhi.service.FileService;
import com.youzhi.service.StudentService;

import java.io.File;
import java.io.IOException;
import java.util.UUID;


@Before(DemoInterceptor.class)
public class StudentController extends Controller {

    @Inject
    StudentService service;

    public void index(){
        setAttr("stuPage",service.paginate(getParaToInt(0,1),10));
        render("student.html");
    }
//    public  void save(){
//        String new_password = getPara("password");
//        new_password =Encrypt.str2MD5(new_password);
//        Record stu = new Record().set("phone",getParaToInt("phone")).set("name",getPara("name")).set("password",new_password).set("sex",getPara("sex")).set("age",getPara("age"));
//        Db.save("student",stu);
//        redirect("/stu");
//    }
    public void delete(){
        service.deleteById(getParaToInt());
        redirect("/stu");
    }
    public void edit(){
        setAttr("stu",service.findById(getParaToInt()));
    }
    public void update(){
//        Student student = service.findById(getParaToInt("stu.id"));
//        student.set("studentID",getPara("stu.studentID")).set("name", getPara("stu.name")).set("password", getPara("stu.password")).set("sex", getPara("stu.sex")).set("age", getPara("stu.age"));
//        student.update();

        Student student = getBean(Student.class);
        student.update();
        redirect("/stu");
    }
    //文件上传方法
    public void new_upload(){
        UploadFile file = getFile();
//        file.getFile();
//        String path = file.getUploadPath();
        render("/验证码.html");
    }

}
