package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet Filter implementation class PriorityFilter
 */
@WebFilter("/*")
public class PriorityFilter implements Filter {

    public PriorityFilter() {
    }

	public void destroy() {
		// TODO Auto-generated method stub
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
			HttpServletRequest req = (HttpServletRequest) request;
			HttpServletResponse resp = (HttpServletResponse) response;
			String uri = req.getRequestURI();
			if(uri.indexOf("before.jsp")!=-1||
					uri.indexOf("create.jsp")!=-1||
					uri.indexOf("design.jsp")!=-1||
					uri.indexOf("member.jsp")!=-1||
					uri.indexOf("reference.jsp")!=-1||
					uri.indexOf("user.jsp")!=-1){
				if(req.getSession().getAttribute("account")==null){
					resp.sendRedirect("index.jsp");
					return;
				}
			}
			chain.doFilter(request, response);
		// pass the request along the filter chain
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
