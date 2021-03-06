package com.example.mybatis.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.mybatis.entity.LoginEnum;
import com.example.mybatis.entity.Stu;
import com.example.mybatis.entity.VerCode;
import com.example.mybatis.service.CommonService;
import com.example.mybatis.service.LoginRequired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 负责具体的业务模块流程的控制，即页面访问控制，调用service层里面的接口控制具体的业务流程
 */
@Controller
public class CommonController {
    @Autowired
    public CommonService commonService;

    public final String md5Str = "personalMd5";

    public String loginState = "";

    //添加初始化登录页面的转发配置
    @RequestMapping(value = "/", method = { RequestMethod.GET } )
    public String login() {
        // 在地址栏输入localhost:8080/ 的时候会跳转到login文件夹下面的login.html页面
        return "/login/login";
    }

    @RequestMapping(value = "/register", method = { RequestMethod.GET } )
    public String register () {
        return "/login/register";
    }

    /*该value的值loginPage要与login.html页面中form中的action值一样，
    是通过form表单提交数据，即点击登录按钮时，提交数据，发送请求*/
    @RequestMapping(value = "/loginPage", method = { RequestMethod.POST, RequestMethod.GET })
    @ResponseBody
    public String login (HttpServletRequest request, HttpSession session) {
        String mymsg = "";
        // String id = request.getParameter(“id“);该代码是获取前台页面中用户输入的用户名的值，getParameter后面的id要与form表单的标签(input等)中的name值相同。
        String name = request.getParameter("name");
        String pwd = request.getParameter("pwd");
        System.out.println("name---" + name + "---pwd---" + pwd);
        if ( name == "" || pwd == "" ) {
            mymsg = "用户名和密码不能为空";
            request.setAttribute("mymsg", mymsg);
             //throw new LoginException("3","2","4");
             throw new LoginException(LoginEnum.NOTALLOWNULL);
            //return "/login/login";
           // return mymsg;
        }
        // MD5签名
        String sqlPwd = md5Str +pwd;
        String inSqlPwd = DigestUtils.md5DigestAsHex(sqlPwd.getBytes());

        //用户名和密码匹配
        String matchRight = commonService.login(name, inSqlPwd);
        System.out.println("matchRight---" + matchRight);
        // 昵称是否存在
        String isExistName = commonService.isExistName(name);
        if(isExistName == null){
            mymsg = "账号不存在，请重新输入";
            request.setAttribute("mymsg", mymsg );
            throw new LoginException(LoginEnum.ERRID);
            // return "/login/login";
            // return mymsg;
        } else {
            if ( matchRight == null ) {
                mymsg = "用户密码错误";
                request.setAttribute("mymsg", mymsg );
                throw new LoginException(LoginEnum.ERRPWD);
                // return mymsg;
            } else {
                session.setAttribute("name", matchRight );
                throw new LoginException(LoginEnum.SUCCESS);
               // return "";
            }
        }
    }

    @RequestMapping(value = "/registerPage", method = { RequestMethod.POST })
    public String register (HttpServletRequest request) {
        String regmsg = "";
        String name = request.getParameter("name");
        String pwd = request.getParameter("pwd");
        String repwd = request.getParameter("repwd");

        if (!pwd.equals(repwd)) {
            regmsg = "密码不一致";
            request.setAttribute("regmsg", regmsg);
            return "/login/register";
        }

        // MD5签名
        String sqlPwd = md5Str +pwd;
        String inSqlPwd = DigestUtils.md5DigestAsHex(sqlPwd.getBytes());

        String sqlName = name.toLowerCase();

        if ( name == "" || pwd == "" || repwd == "") {
            regmsg = "不允许为空";
            request.setAttribute("regmsg", regmsg);
            return "/login/register";
        }

        String isExist = commonService.isExistName(sqlName);

        if ( isExist != null){
            regmsg = "已存在该账号";
            request.setAttribute("regmsg", regmsg);
            return "/login/register";
        } else {
            Stu stu = new Stu();
            stu.setName(sqlName);
            stu.setPwd(inSqlPwd);
            commonService.register(stu.name,stu.pwd);
            return "/login/login";
        }
    }

    @RequestMapping(value = "/index", method = {RequestMethod.POST, RequestMethod.GET} )
    @LoginRequired
    public String loginindex () {
        return "/login/index";
    }

    // 所有用户信息
    @RequestMapping(value = "/getInfo", method = {RequestMethod.POST, RequestMethod.GET})
    @LoginRequired
    public String getInfo (HttpServletRequest request) {
        List<Stu> stuInfoList = commonService.stuAllInfo();
        request.setAttribute("stuInfoList",stuInfoList);
        String stuInfo = JSON.toJSONString(stuInfoList);
        request.setAttribute("stuInfo", stuInfo);

        String total = commonService.countStu();
        request.setAttribute("total", total);

        return "/kendoui/index";
    }
    // 所有用户信息 Ajax
    @RequestMapping(value = "/getInfoAjax", method = {RequestMethod.POST, RequestMethod.GET})
    @LoginRequired
    @ResponseBody
    public String getInfoAjax (HttpServletRequest request) {
        List<Stu> stuInfoList = commonService.stuAllInfo();

        String stuInfo = JSON.toJSONString(stuInfoList);

        String total = commonService.countStu();
        request.setAttribute("total", total);

        return stuInfo;
    }


    @RequestMapping(value = "/getInfoPages", method = {RequestMethod.POST, RequestMethod.GET})
    @LoginRequired
    public String getInfoPages () {
        return "/kendoui/pageAjax";
    }


    // 所有用户信息 分页
    @RequestMapping(value = "/getInfoPage", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    @LoginRequired
    public String getInfoPage (HttpServletRequest request) {
        // 每页数量,从前台ajax获取
        Integer piece = Integer.parseInt( request.getParameter("perPage") );

        // 当前页数,从前台ajax获取
        Integer pages = Integer.parseInt( request.getParameter("currentPage") );
       // System.out.println("当前页数" + pages);

        Integer currentPage = (pages-1)*piece;
       // System.out.println("每页开始序号"+ currentPage);
        // 数据总量 11
        Integer pageAllNum = Integer.parseInt( commonService.countStu() );
        // 一共多少页
        Integer pageNum = pageAllNum / piece;
        if ( pageNum % piece > 0 ) {
            pageNum = pageNum + 1;
        } else {
            pageNum = pageNum;
        }
        System.out.println("一共"+pageNum);
        // 11条数据，每页5条，一共3页
        // 跳转到第4页,
        if ( pages > pageNum || pages <= 0) {
            pages = pageNum;
           // System.out.println("超过总页数"+pages);
        }

        List<Stu> stuPage = commonService.stuPage(currentPage,piece);
        String stuPageInfo = JSON.toJSONString(stuPage);


        // 把符合要求的数据和总页数 两个数据封装为json
        JSONObject jsonOne = new JSONObject();
        JSONObject jsonTwo = new JSONObject();
        JSONObject jsonThree = new JSONObject();

        jsonOne.put("stuPage", stuPage);
        jsonTwo.put("pages", pages);
        jsonThree.put("total", pageAllNum );

        JSONObject jsonFour = new JSONObject();

        jsonFour.putAll(jsonTwo);
        jsonFour.putAll(jsonOne);
        jsonFour.putAll(jsonThree);


        System.out.println(jsonFour.toString());
        String test = JSON.toJSONString(jsonFour);
        request.setAttribute("test", test);


        request.setAttribute("stuPage", stuPageInfo);
        request.setAttribute("pages", pages);

        //return stuPageInfo;
        return test;
    }

    // 根据用户名查询
    @RequestMapping(value = "/QueryByName", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    @LoginRequired
    public String QueryByName(HttpServletRequest request){
        String name = request.getParameter("name");
        List<Stu> stu = commonService.QueryByName(name);
        String stuInfo = JSON.toJSONString(stu);
        request.setAttribute("stuInfo", stuInfo);

        return stuInfo;
    }

    // 根据用户名删除
    @RequestMapping(value = "/DelByName", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    @LoginRequired
    public Integer DelByName(HttpServletRequest request){
        Integer delState;
        // 获取前台ajax传入的name值
        String name = request.getParameter("name");
        // 校验用户是否存在
        String isExist = commonService.isExistName(name);

        if(isExist == null){
            delState = -1;
            request.setAttribute("delState", delState);
            return delState;
        }else{
            commonService.DelByName(name);
            delState = 1;
            request.setAttribute("delmsg", delState);
            return delState;
        }
    }

    // 更新用户信息
    @RequestMapping(value = "/UpdataById", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    @LoginRequired
    public String UpdateInfo(HttpServletRequest request){
        String name = request.getParameter("name");
        String pwd = request.getParameter("pwd");
        String id = request.getParameter("id");

        // MD5签名
        String sqlPwd = md5Str +pwd;
        String inSqlPwd = DigestUtils.md5DigestAsHex(sqlPwd.getBytes());

        commonService.updateInfo(name,inSqlPwd,id);
        List<Stu> stus = commonService.updateInfo(name,inSqlPwd,id);
        String stuInfos = JSON.toJSONString(stus);
        String stateMsg = "1";
        request.setAttribute("stateMsg", stateMsg);

        return stateMsg;
    }

    //  生成验证码
    @RequestMapping(value = "/getVerCode")
    public void GetVerCode(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("image/jpeg"); // 设置响应类型，告诉浏览器的输出内容为图片
            response.setHeader("Pragma", "No-cache"); // 设置响应头信息，告诉浏览器不要缓存此类容
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expire", 0);

            VerCode vercode = new VerCode();
            vercode.getRandcode(request, response); // 输出验证码图片方法


        } catch(Exception e) {

        }

    }
    /**
     * 忘记密码页面校验验证码
     */
    @RequestMapping(value = "/checkVerify", method = RequestMethod.POST, headers = "Accept=application/json")
    @ResponseBody
    public boolean checkVerify(HttpServletRequest request, HttpSession session) {
        try{
            //从session中获取随机数
            String inputStr = request.getParameter("inputStr");

            //String inputStr = requestMap.get("inputStr").toString();
            String random = (String) session.getAttribute("RANDOMVALIDATECODEKEY");
            if (random == null) {
                return false;
            }
            if (random.equals(inputStr)) {
                return true;
            } else {
                return false;
            }
        }catch (Exception e){
            //logger.error("验证码校验失败", e);
            return false;
        }
    }

}