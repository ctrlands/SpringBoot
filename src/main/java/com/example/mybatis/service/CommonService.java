package com.example.mybatis.service;

import com.example.mybatis.entity.Stu;
import com.example.mybatis.mapper.Common;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 业务层，控制业务，主要负责业务逻辑模块的逻辑应用设计
 */
@Service
/*@service 服务（注入dao）
用于标注服务层，主要用来进行业务的逻辑处理*/
public class CommonService {
    @Autowired
/*  @Autowired 注释
    它可以对类成员变量、方法及构造函数进行标注，完成自动装配的工作。
    通过 @Autowired的使用来消除 set ，get方法。*/
    public Common commonmapper;

    // 用户名是否存在
    public String isExistName(String name) { return commonmapper.isExistName(name); }

    // 用户名密码是否匹配
    public String login(String name, String pwd) {
        return commonmapper.login(name,pwd);
    }



    // 注册
    public String register(String name, String pwd) {
        return commonmapper.register(name,pwd);
    }

    // 所有用户信息
    public List<Stu> stuAllInfo(){
        return  commonmapper.getInfo();
    }

    // 用户数量
    public String countStu() {
        return commonmapper.countStu();
    }
    // 分页
    public List<Stu> stuPage(Integer page, Integer pageSize) {
        return commonmapper.stuPage(page,pageSize);
    }

    // 查询ByName
    public List<Stu> QueryByName(String name){
        return  commonmapper.QueryByName(name);
    }
    // 删除ByName
    public String DelByName(String name){
        return commonmapper.DelByName(name);
    }
    // 编辑ById
    public List<Stu> updateInfo(String name, String pwd, String id){
        return commonmapper.updateInfo(name, pwd, id);
    }


}
