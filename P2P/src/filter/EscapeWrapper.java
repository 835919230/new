package filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang3.StringEscapeUtils;

public class EscapeWrapper extends HttpServletRequestWrapper implements
		HttpServletRequest {

	public EscapeWrapper(HttpServletRequest request) {
		super(request);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getParameter(String name) {
		// TODO Auto-generated method stub
		String value = getRequest().getParameter(name);
		return StringEscapeUtils.escapeHtml4(value);
	}
	
}
