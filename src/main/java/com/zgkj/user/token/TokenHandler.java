package com.zgkj.user.token;


import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by 韩明泽 on 2017/8/17.
 */
@Component
public class TokenHandler {

    static Map<String, String> springmvc_token = null;
    /**
     * 生成一个唯一值token
     * @param session
     * @return
     */
    public synchronized static String generateGUID(HttpSession session) {
        String token = "";
        try {
            Object obj =  session.getAttribute("SPRINGMVC.TOKEN");
            if(obj!=null){
                springmvc_token = (Map<String,String>)session.getAttribute("SPRINGMVC.TOKEN");
            }

            else {
                springmvc_token = new HashMap<String, String>();
                token = new BigInteger(165, new Random()).toString(36)
                        .toUpperCase();
                springmvc_token.put(Constants.DEFAULT_TOKEN_NAME + "." + token, token);
            }
            session.setAttribute("SPRINGMVC.TOKEN", springmvc_token);
            Constants.TOKEN_VALUE = token;

        } catch (IllegalStateException e) {

        }
        return token;
    }

    /**
     * 验证表单token值和session中的token值是否一致
     * @param request
     * @return
     */
    public static boolean validToken(HttpServletRequest request) {
        String inputToken = getInputToken(request);

        if (inputToken == null) {

            return false;
        }

        HttpSession session = request.getSession();
        Map<String, String> tokenMap = (Map<String, String>)session.getAttribute("SPRINGMVC.TOKEN");
        if (tokenMap == null || tokenMap.size() < 1) {
            return false;
        }
        String sessionToken = tokenMap.get(Constants.DEFAULT_TOKEN_NAME + "."
                + inputToken);
        if (!inputToken.equals(sessionToken)) {
            return false;
        }
        tokenMap.remove(Constants.DEFAULT_TOKEN_NAME + "." + inputToken);
        session.setAttribute("SPRINGMVC.TOKEN", tokenMap);
        return true;
    }

    /**
     * 获取表单中token值
     * @param request
     * @return
     */
    public static String getInputToken(HttpServletRequest request) {
        Map params = request.getParameterMap();

        if (!params.containsKey(Constants.DEFAULT_TOKEN_NAME)) {
            return null;
        }
        String[] tokens = (String[]) (String[]) params
                .get(Constants.DEFAULT_TOKEN_NAME);
        if ((tokens == null) || (tokens.length < 1)) {

            return null;
        }

        return tokens[0];
    }
}
