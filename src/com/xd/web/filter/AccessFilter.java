package com.xd.web.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csizg.poc.token.TokenManager;
import com.xd.core.Code;
import com.xd.domain.api.ResponseMsg;
import com.xd.util.GsonUtil;


/**
 * Servlet Filter implementation class AccessFilter
 */
public class AccessFilter implements Filter {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public static final String TOKEN_KEY = "X-Auth-Token";
    /**
     * Default constructor. 
     */
    public AccessFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here

		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		System.out.println("VOIPServer--------------header-token:" + request.getHeader("X-Auth-Token"));
		System.out.println("--------------header-contenttype:" + request.getContentType());
	
		
		/*byte[] bytes = new byte[1000];
		request.getInputStream().read(bytes);
		System.out.println("stream:"+new String(bytes));*/
		/*if (uri.contains("/api") && !uri.contains("/login")) {
			String token = request.getHeader("X-Auth-Token");
			if (StringUtils.isBlank(token)) {
				logger.info("-------用户登录过期-------");
				writeTo(response, buildNoLoginResponse());
				return;
			}
			System.out.println("----------token---:" + token);
			TokenManager tokenManager = TokenManager.getInstance();
			boolean isLogin = tokenManager.verify(token);
			if (!isLogin) {
				logger.info("-------用户登录过期-------");
				writeTo(response, buildNoLoginResponse());
				return;
			}
			logger.info("-------用户请求校验成功-------");
		}*/

		// pass the request along the filter chain
		chain.doFilter(request, response);
	}
	
	private void writeTo(HttpServletResponse response, String json) throws IOException{
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    out.print(json);
	    out.flush();
	    out.close();
	}
	
	private String buildNoLoginResponse(){
		
		 ResponseMsg responseMsg = new ResponseMsg(Code.CODE_TOKEN_OUT, "token过期");;
		
		 return GsonUtil.toJson(responseMsg);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}
	


}
