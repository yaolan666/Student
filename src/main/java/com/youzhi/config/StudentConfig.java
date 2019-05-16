package com.youzhi.config;

import com.jfinal.config.*;
import com.jfinal.core.JFinal;
import com.jfinal.json.FastJsonFactory;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.redis.RedisPlugin;
import com.jfinal.server.undertow.UndertowServer;
import com.jfinal.template.Engine;
import com.mysql.cj.x.protobuf.MysqlxCrud;
import com.youzhi.controller.GoodsController;
import com.youzhi.controller.LoginController;
import com.youzhi.controller.OrderController;
import com.youzhi.controller.StudentController;
import com.youzhi.model._MappingKit;
import com.youzhi.routes.AddRoutes;


public class StudentConfig extends JFinalConfig {
    static Prop p;
      public static void main(String[] args) {
        UndertowServer.start(StudentConfig.class);

      }

    static void loadConfig(){
        if(p==null){
            //优先加载配置文件
            p = PropKit.useFirstFound("properties/student_test_config.txt");
        }
    }
    @Override
    public void configConstant(Constants me) {
        loadConfig();
        me.setDevMode(p.getBoolean("devMode",false));
        me.setInjectDependency(true);
        me.setJsonFactory(new FastJsonFactory());
        //文件上传读取配置文件的路径
        me.setBaseUploadPath(PropKit.get("upload.directory"));
    }

    @Override
    public void configRoute(Routes me) {
        me.add("/stu" , StudentController.class);
        me.add("/log" , LoginController.class);
        me.add("/goods" , GoodsController.class);
        me.add("/order" , OrderController.class);

        me.add(new AddRoutes());
    }


    @Override
    public void configEngine(Engine me) {
        me.addSharedFunction("/common/_paginate.html");
    }
    /**
     * 配置插件
     */
    @Override
    public void configPlugin(Plugins me) {
        DruidPlugin druidPlugin = new DruidPlugin(p.get("jdbcUrl"),p.get("user"),p.get("password").trim());
        me.add(druidPlugin);
        ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
        _MappingKit.mapping(arp);
        arp.addSqlTemplate("/sql/demo.sql");
        me.add(arp);
    }

    public static DruidPlugin createDruidPlugin(){
        loadConfig();
        return new DruidPlugin(p.get("jdbcUrl"),p.get("user"),p.get("password").trim());
    }

    private static RedisPlugin createRedisPlugin(){
        return new RedisPlugin(PropKit.get("redis.cache"),PropKit.get("redis.host"),PropKit.get("redis.password")) ;}
    @Override
    public void configInterceptor(Interceptors interceptors) {
    }

    @Override
    public void configHandler(Handlers handlers) {

    }

}
