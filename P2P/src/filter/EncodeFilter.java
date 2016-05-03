package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;

@WebFilter(asyncSupported = true, 
		   urlPatterns = { "/*" },
		   initParams={@WebInitParam(name = "characterEncoding", value = "UTF-8"),@WebInitParam(name = "enable", value = "true")})
public class EncodeFilter implements Filter {
	private String characterEncoding;
	private boolean enable;
    
	public EncodeFilter() {
    	
    }

	public void destroy() {
		characterEncoding=null;
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if(enable&&characterEncoding!=null){
			request.setCharacterEncoding(characterEncoding);
			response.setCharacterEncoding(characterEncoding);
		}
		HttpServletRequest requestWrapper = new EscapeWrapper((HttpServletRequest)request);
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
		characterEncoding = fConfig.getInitParameter("characterEncoding");
		enable = "true".equalsIgnoreCase(fConfig.getInitParameter("enable").trim());
	}

}
