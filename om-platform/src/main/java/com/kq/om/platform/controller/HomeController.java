package com.kq.om.platform.controller;

import com.kq.om.platform.component.UnifiedCertificationComponent;
import com.kq.om.platform.dto.CertAuthDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author kq
 * @date 2021-05-08 8:25
 * @since 2020-0630
 */

@Controller
public class HomeController {

    protected static Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private UnifiedCertificationComponent unifiedCertificationComponent;


    @RequestMapping("/home")
    public String home(){
        return "html/home.html";
    }

    @RequestMapping("/index")
    public String index(){
        return "index.html";
    }

    public static final String CERT_AUTH_KEY = "cert_auth_key";

    @RequestMapping("/login")
    public String login(HttpServletRequest request) throws Exception{

        logger.info("loginc is called.");

        // 这个到时候可以保存到redis
        HttpSession session = request.getSession();
        String sessionId = session.getId();



        String oauthCode = request.getParameter("oauthCode");
        String oauthSession = request.getParameter("oauthSession");

        if(StringUtils.isEmpty(oauthCode) && StringUtils.isEmpty(oauthSession)) {

            return "redirect:"+unifiedCertificationComponent.getCertificationPortalLoginUrl();

        }

        CertAuthDto authDto = (CertAuthDto)session.getAttribute(CERT_AUTH_KEY);
        logger.info("sessionId={} authDto={}",sessionId,authDto);

//        oauthCode = authDto.getOauthCode();
//        oauthSession = authDto.getOauthSession();

        if(authDto==null) {
            authDto = new CertAuthDto();
            authDto.setOauthCode(oauthCode);
            authDto.setOauthSession(oauthSession);
        }

        // 获取token
        String token = unifiedCertificationComponent.getToken(oauthSession,oauthCode);
        authDto.setToken(token);
        session.setAttribute(CERT_AUTH_KEY,authDto);
        logger.info("sessionId = {}, oauthCode={}  oauthSession={}",sessionId,oauthCode,oauthSession);

        // 验证token
        boolean result = unifiedCertificationComponent.checkToken(oauthSession,token);
        logger.info("=============================开始验证token  result={}",result);

        return "html/home.html";
    }


}
