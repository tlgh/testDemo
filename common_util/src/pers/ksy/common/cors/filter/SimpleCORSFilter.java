package pers.ksy.common.cors.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import pers.ksy.common.StringUtil;

/**
 * 
 * 简单跨域请求拦截器
 *
 * <p>
 * detailed comment
 * 
 * @author ksy 2015年8月3日
 * @see
 * @since 1.3.2
 */
public class SimpleCORSFilter implements Filter {
    private String allowOrigin = "*";
    private String allowMethods = "POST, GET, OPTIONS, DELETE, PUT";
    private String allowHeaders = "Content-Type,Cache-Control,Pragma";
    private String maxAge = "3600";

    public void doFilter(ServletRequest req, ServletResponse res,
            FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", allowOrigin);
        response.setHeader("Access-Control-Allow-Methods", allowMethods);
        response.setHeader("Access-Control-Max-Age", maxAge);
        response.setHeader("Access-Control-Allow-Headers", allowHeaders);
        chain.doFilter(req, res);
    }

    public void init(FilterConfig filterConfig) {
        Enumeration<String> enumeration = filterConfig.getInitParameterNames();
        List<String> params = new ArrayList<>();
        while (enumeration.hasMoreElements()) {
            params.add(enumeration.nextElement());
        }
        if (params.contains("allowOrigin")) {
            this.allowOrigin = filterConfig.getInitParameter("allowOrigin");
        }
        if (params.contains("allowMethods")) {
            this.allowMethods = filterConfig.getInitParameter("allowMethods");
        }
        if (params.contains("allowHeaders")) {
            this.allowHeaders = filterConfig.getInitParameter("allowHeaders");
        }
        if (params.contains("maxAge")) {
            this.maxAge = filterConfig.getInitParameter("maxAge");
        }
    }

    public void destroy() {
    }
}