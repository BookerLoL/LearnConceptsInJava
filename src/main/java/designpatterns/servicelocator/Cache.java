package designpatterns.servicelocator;

import java.util.HashMap;
import java.util.Map;

public class Cache {
	private Map<String, Service> services = new HashMap<>();

	public Service getService(String serviceName) {
		return services.getOrDefault(serviceName, null);
	}

	public void addService(Service newService) {
		services.computeIfAbsent(newService.getName(), k -> newService);
	}
}
