package com.yue.Controller;

import com.yue.Pojo.User;
import com.yue.utils.LoginCacheUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

/**
 * @author 岳贺伟
 * @description
 * @data
 */
@Controller
@RequestMapping("view")
public class ViewController {
    @GetMapping ("login")
    public String toLogin(@RequestParam(required = false ,defaultValue = "") String target , HttpSession session,@CookieValue (required = false,value = "TOKEN") Cookie cookie){
        //首先判断target是否为空，为空就跳转到首页进行登录，

        if (StringUtils.isEmpty(target)){
               target="http://www.codeshop.com:9010";
           }
        //target不为空，判断是否登陆过，然后跳转到来的页面
               if(cookie!=null){
                   String value = cookie.getValue();
                   User user = LoginCacheUtil.loginMap.get(value);
                   if (user!=null){
                       return "redirect:"+ target;
                   }

               }

        session.setAttribute("target",target);
        return "login";
    }

}
