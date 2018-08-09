package com.example.mybatis.mapper;

import com.example.mybatis.entity.Stu;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Component;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 数据库操作CRUD
 *
 */
@Component
/*@controller 控制器（注入服务）
用于标注控制层，相当于struts中的action层*/
public interface Common {
    /*
    #{id}是用户输入的账户名，通过@Param参数的的方式传递。
    通过用户在前台输入的账户名和密码与数据库比较，返回查询到的name属性。
    引用@Select注解进行查询。如果要进行更新操作，则需要使用@Update注解，
    括号里面是相关的sql语句，通过调用注解下面的方法，便可以实现该sql语句的功能
    */

    // 登陆/注册验证，判断用户名是否存在
    @Select("select name from student where name=#{name}")
    public String isExistName(@Param("name") String name);




    // 登陆验证，判断用户名和密码是否匹配
    @Select("select name from student where name=#{name} and pwd=#{pwd}")
    public String login(@Param("name") String name, @Param("pwd") String pwd);

    // 注册
    @Select("insert into student(name,pwd) values ( #{name}, #{pwd})")
    //@Select("insert into student(name,pwd) values ( #{name}, #{pwd})")
    // public List<Stu> stu(@Param("name") String name, @Param("account") String account, @Param("pwd") String pwd);
     public String register(@Param("name") String name, @Param("pwd") String pwd);

    // 查询所有信息
    @Select("select * from student")
    public List<Stu> getInfo();

    // 用户总数量
    @Select("select count(id) from student")
    public String countStu();

    // 分页查询
    /*
     * page 当前页
     * pageSize 每页数量
     */
    @Select( "select * from student limit #{page},#{pageSize}" )
    public List<Stu> stuPage(@Param("page") Integer page, @Param("pageSize") Integer pageSize);


    /*根据名字查询*/
    @Select( "select * from student where name = #{name}" )
    public List<Stu> QueryByName(@Param("name") String name);

    /*根据名字删除用户*/
    @Select( "delete from student where name=#{name}" )
    public String DelByName(@Param("name") String name);

    /*根据Id编辑信息*/
    @Select( "update student set name=#{name}, pwd=#{pwd} where id=#{id}" )
    public List<Stu> updateInfo(@Param("name") String name, @Param("pwd") String pwd, @Param("id") String id);

}
