package com.demo.config.swagger;

import com.demo.utils.ClassInterfaceUtils;
import com.googlecode.jsonrpc4j.JsonRpcMethod;
import com.googlecode.jsonrpc4j.JsonRpcService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import springfox.documentation.RequestHandler;
import springfox.documentation.spi.service.RequestHandlerProvider;

import javax.servlet.ServletContext;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;
import static springfox.documentation.spi.service.contexts.Orderings.byPatternsCondition;
import static springfox.documentation.spring.web.paths.Paths.ROOT;

/**
 * @author whets
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@Conditional(JsonRpcPresentInClassPathCondition.class)
public class JsonRpcRequestHandlerProvider implements RequestHandlerProvider {
	private static final Logger logger = LoggerFactory.getLogger(JsonRpcRequestHandlerProvider.class);
	private final String contextPath;
	private final ApplicationContext applicationContext;
	private final Map<String, JsonRpcRequest> methods;

	@Autowired
	public JsonRpcRequestHandlerProvider(ApplicationContext applicationContext,
			Optional<ServletContext> servletContext) {
		this.contextPath = servletContext.map(ServletContext::getContextPath).orElse(ROOT);
		this.applicationContext = applicationContext;
		this.methods = handleMethods();
		logger.debug("JsonRpcRequestHandlerProvider ok");
	}

	public Map<String, JsonRpcRequest> handleMethods() {
		Map<String, JsonRpcRequest> methods = new HashMap<>();
		Map<String, Object> serviceMaps = applicationContext.getBeansWithAnnotation(JsonRpcService.class);
		for (Map.Entry<String, Object> api : serviceMaps.entrySet()) {
			Class<?> apiClass = ClassInterfaceUtils.findAnnotatedType(api.getValue().getClass(), JsonRpcService.class);
			if (apiClass == null)
				continue;

			JsonRpcService jsonRpcService = apiClass.getAnnotation(JsonRpcService.class);
			if (apiClass.isAnnotationPresent(Api.class)) {
				Api apiService = apiClass.getAnnotation(Api.class);
				if (apiService.hidden()) {
					continue;
				}
			}
			Method[] apiMethods = apiClass.getMethods();
			for (Method method : apiMethods) {
				String apiName = method.getName();
				if (method.isAnnotationPresent(JsonRpcMethod.class)) {
					JsonRpcMethod jsonRpcMethod = method.getAnnotation(JsonRpcMethod.class);
					apiName = jsonRpcMethod.value();
				}
				if (method.isAnnotationPresent(ApiOperation.class)) {
					ApiOperation apiMethod = method.getAnnotation(ApiOperation.class);
					if (apiMethod.hidden()) {
						continue;
					}
				}
				methods.put(jsonRpcService.value() + "." + apiName,
						JsonRpcRequest.builder().contextPath(contextPath).serviceClass(apiClass)
								.serviceName(jsonRpcService.value()).apiName(apiName).apiMethod(method).build());
			}
		}
		return methods;
	}

	@Override
	public List<RequestHandler> requestHandlers() {
		return this.methods.entrySet().stream().map(toRequestHandler())
				.sorted(byPatternsCondition()).collect(toList());
	}

	private Function<Map.Entry<String, JsonRpcRequest>, RequestHandler> toRequestHandler() {
		return input -> new JsonRpcRequestHandler(contextPath, input.getValue());
	}
}
