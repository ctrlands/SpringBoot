package com.example.mybatis.entity;

import org.hibernate.validator.constraints.NotEmpty;

import javax.annotation.Generated;
import javax.validation.constraints.Size;

/**
 * student实体类，字段同数据库一致
 */
public class Stu {
    public String id;
    public String name;
    public String pwd;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public String toString() {
        return "Stu{" +
                "id=' " + id + '\'' +
                ",name=' " + name + '\'' +
                ",pwd' " + pwd + '\'' +
                '}'
                ;
    }
}
