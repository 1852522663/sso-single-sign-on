package com.yue.Controller;

import com.sun.deploy.net.HttpResponse;
import com.yue.Pojo.User;
import com.yue.utils.LoginCacheUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * @author 岳贺伟
 * @description
 * @data
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    //模拟数据库数据
    private static Set<User> dbUsers;
    static {
        dbUsers=new HashSet<>();
        dbUsers.add(new User(0,"yuehewei","123456"));
        dbUsers.add(new User(1,"yuehewei1","1234567"));
        dbUsers.add(new User(2,"yuehewei2","12345"));
        dbUsers.add(new User(3,"yuehewei3","1234"));
        dbUsers.add(new User(4,"yuehewei4","123"));

    }
@PostMapping
   public  String doLogin(User user, HttpSession session, HttpServletResponse response){
       Object target = session.getAttribute("target");
       //判读这个用户是否存在
       Optional<User> user1 = dbUsers.stream().filter(dbUsers -> dbUsers.getUsername().equals(user.getUsername()) &&
               dbUsers.getPassword().equals(user.getPassword())

       ).findFirst();

       /*
        * 1.当这个用户存在，我们需要给他生成一个随机的uuid作为token
        * 2.并把token作为key，user作为value，存在缓存中
        * 3.并把token交割cookie，并设置该cookie可跨域的域名
        * 4.将cookie返还给客户端
        */
       if (user1.isPresent()){
           String token = UUID.randomUUID().toString();
           LoginCacheUtil.loginMap.put(token,user1.get());
           Cookie cookie = new Cookie("TOKEN",token);
           //跨域共享cookie的方法:setDomain()
           cookie.setDomain("codeshop.com");
           response.addCookie(cookie);
       }else {
           session.setAttribute("msg","用户名或者密码错误");

           return "login";
       }
       return "redirect:"+target;

   }
    @GetMapping("info")
    @ResponseBody
    /*
     *功能描述
     * 得到token，并在缓存中找到key=token的用户并返回
     */
    public ResponseEntity<User> getUserInfo(String token){
        if (!StringUtils.isEmpty(token)){
            User user = LoginCacheUtil.loginMap.get(token);
            return ResponseEntity.ok(user);
        }else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }


    }

}
