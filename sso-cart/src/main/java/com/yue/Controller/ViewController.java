package com.yue.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author 岳贺伟
 * @description
 * @data
 */
@Controller
@RequestMapping("view")
public class ViewController {

    @Autowired
    RestTemplate restTemplate;

    private  final  String LOGIN_INFO="http://login.codeshop.com:9000/login/info?token=";
    @GetMapping ("index")
    public String toIndex(@CookieValue(required = false,value = "TOKEN") Cookie cookie, HttpSession session){
        if ( cookie!=null){
            String token = cookie.getValue();

            if (!StringUtils.isEmpty(token)){
                Map user = restTemplate.getForObject(LOGIN_INFO +token, Map.class);
                session.setAttribute("loginUser",user);
            }

        }
        return "index";
    }
}
