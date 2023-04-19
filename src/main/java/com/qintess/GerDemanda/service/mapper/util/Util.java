package com.qintess.GerDemanda.service.mapper.util;

import javax.servlet.http.HttpServletRequest;

public class Util {

    public static String getAppUrl(HttpServletRequest request) {
        String baseUrl = request.getScheme() + "://" + request.getServerName();
        if (request.getServerPort() != 80 && request.getServerPort() != 443) {
            baseUrl += ":" + request.getServerPort();
        }
        return baseUrl;
    }

}

