package com.zgkj.user.controller;

import com.zgkj.user.Mail.MailService;
import com.zgkj.user.Utils.InvertCodeGenerator;
import com.zgkj.user.Utils.MD5;
import com.zgkj.user.pojo.User;
import com.zgkj.user.service.UserService;
import com.zgkj.user.token.TokenHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import static com.zgkj.user.Utils.Util_img.generateVerifyCode;
import static com.zgkj.user.Utils.Util_img.outputImage;

/**
 * Created by 韩明泽 on 2017/8/4.
 * 控制层
 */
@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private MailService mailService;
    /**
     * 进入登陆html的方法
     * @return
     */
    @RequestMapping("goLogin")
    public String goLogin(){
        return "login";
    }
    /**
     * 进入注册html的方法
     * @return
     */
    @RequestMapping("goRegister")
    public String goRegister(HttpSession session, HttpServletRequest request)throws Exception{
        File dir = new File("C:\\Users\\韩明泽\\images");
        int w = 200, h = 80;
        String verifyCode = generateVerifyCode(4);
        System.out.println(verifyCode);
        File file = new File(dir, verifyCode +System.currentTimeMillis()+ ".jpg");
        System.out.println(file.getName());
        System.out.println(file.getPath());
        session.setAttribute("verifyCode",file.getName());
        outputImage(w, h, file, verifyCode);
        TokenHandler.generateGUID(session);
        return "register";
    }
    /**
     * 进入找回密码界面
     * @return
     */
    @RequestMapping("goRetrievePwd")
    public String goRetrievePwd(){
        return "retrievePwd";
    }

    /**
     * 进入个人中心界面
     * @return
     */
    @RequestMapping("goMySetting")
    public String goMySetting(){
        return "mySetting";
    }
    /**
     * 登陆
     * @param user
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "login",method = RequestMethod.POST)
    public String login(
            User user,Model model,
            HttpSession session) throws Exception{
        String newPwd = MD5.EncoderByMd5(user.getPassword());
        User loginUser = userService.findByUsernameAndPassword(user.getUsername(), newPwd);
        User byEmailAndPassword = userService.findByEmailAndPassword(user.getUsername(), newPwd);
        User byPhoneAndPassword = userService.findByPhoneAndPassword(user.getUsername(), newPwd);
        if(loginUser!=null&&loginUser.getActiv()!=0){
            session.setAttribute("user",loginUser);
            return "success";
        }
        else if(byEmailAndPassword!=null&&byEmailAndPassword.getActiv()!=0){
            session.setAttribute("user",byEmailAndPassword);
            return "success";
        }
        else if(byPhoneAndPassword!=null&&byPhoneAndPassword.getActiv()!=0){
            session.setAttribute("user",byPhoneAndPassword);
            return "success";
        }
        else {
            model.addAttribute("errorMsg", "http://169.254.29.36:6201/user/goRetrievePwd");
            return "login";
        }
    }
    /**
     *  ajax用户名校验
     * @param username
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "findUserByName",method = RequestMethod.POST)
    public void findUserByName (String username, HttpServletResponse response)throws Exception{
      response.setCharacterEncoding("utf-8");
      PrintWriter writer = response.getWriter();
      writer.print(userService.findByUsername(username));
    }
    /**
     * ajax手机号校验
     * @param phone
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "findUserByPhone",method = RequestMethod.POST)
    public void findUserByPhone (String phone, HttpServletResponse response)throws Exception{
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        writer.print(userService.findByPhone(phone));
    }
    /**
     * ajaxEmail校验
     * @param email
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "findUserByEmail",method = RequestMethod.POST)
    public void findUserByEmail (String email, HttpServletResponse response)throws Exception{
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        writer.print(userService.findByEmail(email));
    }
    /**
     * 验证码异步刷新
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "flushImages",method = RequestMethod.POST)
    @ResponseBody
    public String flushImages(HttpSession session) throws Exception{
        File dir = new File("C:\\Users\\韩明泽\\images");
        int w = 200, h = 80;
        String verifyCode = generateVerifyCode(4);
        session.setAttribute("verStr",verifyCode);
        File file = new File(dir, verifyCode +System.currentTimeMillis()+ ".jpg");
        outputImage(w, h, file, verifyCode);
        return "http://169.254.29.36:8000/images/"+file.getName();
    }
    /**
     * ajax验证码校验
     * @param verCode
     * @param session
     * @return
     */
    @RequestMapping(value = "checkVerCode",method = RequestMethod.POST)
    @ResponseBody
    public String checkVerCode(String verCode,HttpSession session){
        String verifyCodeN = (String) session.getAttribute("verStr");
        String verifyCodeO = (String) session.getAttribute("verifyCode");
        String vCodeN = verifyCodeN.substring(0, 4);
        String vCodeO = verifyCodeO.substring(0, 4);
        if(verCode.equalsIgnoreCase(vCodeN)||verCode.equalsIgnoreCase(vCodeO)){
            return "1";
        }
        return "2";
    }

    /**
     * 注册
     * @param user
     * @return
     */
    @RequestMapping(value ="register",method = RequestMethod.POST)
    public String register(User user) throws Exception{
        user.setUuid(user.getUsername()+UUID.randomUUID());
        user.setPassword(MD5.EncoderByMd5(user.getPassword()));
        long createTime=System.currentTimeMillis();
        user.setCreatetime(createTime);
        userService.save(user);
        User user1 = userService.findByUuid(user.getUuid());
        Date date=new Date(createTime);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
        String cTime = sdf.format(date);
        mailService.sendSimpleMail(user.getEmail(),
                "superfix邮箱激活验证","<!DOCTYPE html>" +
                        "<html lang=\"en\">\n" +
                        "<head>" +
                        "    <meta charset=\"UTF-8\">\n" +
                        "    <title>邮箱激活</title>" +
                        "</head>" +
                        "<body>" +
                        "<h2 style='color:blueviolet;'>激活账号</h2><br/><p>你好！"+user.getUsername()+"   " +
                        "欢迎注册账号</p><br/><p>请在5分钟内点击下面的链接完成账号激活，如果不是本人操作请忽略本信息</p><br/>" +
                        "<a href='http://169.254.29.36:6201/user/emailActivate/"+user.getUuid()+"/"+user1.getId()+"'>https://www.superfix/user/"+user.getUuid()+".com</a><br/>" +
                        "<p>注册时间:"+cTime+"</p>"+
                        "</body>" +
                        "</html>");
                  return "redirect:goRegister";
    }
    /**
     * 激活账号
     * @param uuid
     * @param id
     * @return
     */
    @RequestMapping(value="emailActivate/{uuid}/{id}",method = RequestMethod.GET)
    public String emailActivate(@PathVariable String uuid,@PathVariable Integer id){
        User user = userService.findByUuidAndId(uuid, id);
          if(user!=null){
              user.setActiv(1);
              userService.save(user);
          }
        long nowTime = System.currentTimeMillis();
        User user1= userService.findById(id);
          if((nowTime)-(user1.getCreatetime())>=300000L){
              user1.setActiv(0);
              userService.save(user1);
          }
        return "ok";
    }

    /**
     * 三种条件验证User
     * @param account
     * @return
     */
    @RequestMapping(value = "findUserByAccount",method = RequestMethod.POST)
    @ResponseBody
    public String findUserByAccount(String account){
        String byUsername = userService.findByUsername(account);
        String byEmail = userService.findByEmail(account);
        String byPhone = userService.findByPhone(account);
        if(byUsername.equals("1")){
            return byUsername;
        }
        else if(byEmail.equals("1")){
            return byEmail;
        }
        else if(byPhone.equals("1")){
            return byPhone;
        }
        else{
            return "nonentity";
        }
    }
    /**
     * 根据username和email验证用户
     * @param email
     * @param account
     * @return
     */
    @RequestMapping(value ="findUserByEmailAndUsername/{email}/{account}")
    @ResponseBody
    public String findUserByEmailAndUsername(@PathVariable String email,@PathVariable String account,HttpSession session){
        System.out.println(email+"     "+account);
        User user = userService.findByEmailAndUsername(email, account);
        if(user!=null){
            System.out.println("user不为空");
            String str="1";
            String newPwd=InvertCodeGenerator.genCodes();
              session.setAttribute("newPwd",newPwd);

            mailService.sendSimpleMail(email,"superfix重置密码","" +
                    "<!DOCTYPE html>" +
                    "<html lang='en'>" +
                    "<head>" +
                    "<meta charset='UTF-8'>" +
                    "<title>重置密码</title>" +
                    "</head>" +
                    "<body>" +
                    "<h2 style='color:blueviolet;'>重置密码</h2><br/>" +
                    "请记住这个验证码"+"&nbsp;&nbsp;<h4 style='color:red;'>"+newPwd+"</h4>"+
                    "<br/>如果不是本人请忽略本次文本内容"+
                    "</body>" +
                    "</html>");
            return str;
        }
        return "nonentity";
    }
    /**
     * ajax验证验证码
     * @param code
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value ="resetPwd",method = RequestMethod.POST)
    @ResponseBody
    public String resetPwd(String code,HttpSession session) throws Exception{
        String newPwd = (String) session.getAttribute("newPwd");
        if (code.equals(newPwd)){
           //
            return "1";
        }
           return "2";
    }

    /**
     * 重置密码
     * @param email
     * @param code
     * @return
     * @throws Exception
     */
    @RequestMapping(value="resetPwdSuccess",method = RequestMethod.POST)
    public String resetPwdSuccess(String email,String code) throws Exception{
           User user = userService.findUserByEmail(email);
          user.setActiv(1);
          user.setPassword(MD5.EncoderByMd5(code));
          userService.save(user);
          return "redirect:goLogin";
    }
    /**
     * 个人中心管理
     * @param user
     * @return
     */
    @RequestMapping(value="saveOrUpdateUser",method=RequestMethod.POST)
    public String saveOrUpdateUser(User user){
          userService.save(user);
          return "redirect:goLogin";
    }
    /**
     * 验证重复提交
     * @param request
     * @return
     */
    @RequestMapping(value = "saveUser",method = RequestMethod.POST)
    public String saveUser(HttpServletRequest request,Model model){
        boolean validToken = TokenHandler.validToken(request);
        if(!validToken){
            return "msg";
        }
        return "forward:register";
    }
}

