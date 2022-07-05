package designpatterns.interceptingfilter;

import java.util.ArrayList;
import java.util.List;

public class FilterChain {
	private List<Filter> filters = new ArrayList<>();
	private RequestHandler handler;

	public void addFilter(Filter filter) {
		filters.add(filter);
	}

	public void execute(String request) {
		for (Filter filter : filters) {
			filter.execute(request);
		}

		handler.execute(request);
	}

	public void setHandler(RequestHandler newHandler) {
		this.handler = newHandler;
	}
}
