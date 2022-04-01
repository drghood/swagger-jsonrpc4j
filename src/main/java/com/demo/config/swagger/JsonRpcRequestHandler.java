package com.demo.config.swagger;

import com.demo.dtos.RpcException;
import com.demo.dtos.RpcResponse;
import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.classmate.TypeResolver;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import springfox.documentation.RequestHandler;
import springfox.documentation.RequestHandlerKey;
import springfox.documentation.service.ResolvedMethodParameter;
import springfox.documentation.spring.wrapper.NameValueExpression;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.*;

import static java.util.Optional.ofNullable;

/**
 * @author whets
 */
public class JsonRpcRequestHandler implements RequestHandler {
	private static final TypeResolver typeResolver = new TypeResolver();
	private final String contextPath;
	private final JsonRpcRequest request;
	private static RequestBody requestBodyAnnotation;

	static {
		try {
			Method method = JsonRpcRequestHandler.class.getMethod("getRequestBody", String.class);
			requestBodyAnnotation = method.getParameters()[0].getAnnotation(RequestBody.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public JsonRpcRequestHandler(String contextPath, JsonRpcRequest request) {
		this.contextPath = contextPath;
		this.request = request;
	}

	@Override
	public HandlerMethod getHandlerMethod() {
		throw new RpcException("Deprecated", "Deprecated");
	}

	@Override
	public RequestHandler combine(RequestHandler other) {
		return this;
	}

	@Override
	public Class<?> declaringClass() {
		return request.getServiceClass();
	}

	@Override
	public boolean isAnnotatedWith(Class<? extends Annotation> annotation) {
		return null != AnnotationUtils.findAnnotation(request.getApiMethod(), annotation);
	}

	@Override
	public springfox.documentation.spring.wrapper.PatternsRequestCondition<?> getPatternsCondition() {
		return new JsonRpcPatternsRequestConditionWrapper(contextPath + "/" + request.getServiceName(),
				new PatternsRequestCondition(request.getApiName()));
	}

	@Override
	public String groupName() {
		return "[JsonRpc4J] " + contextPath + "/" + request.getServiceName();
	}

	@Override
	public String getName() {
		return request.getApiName();
	}

	@Override
	public Set<RequestMethod> supportedMethods() {
		return Collections.singleton(RequestMethod.POST);
	}

	@Override
	public Set<MediaType> produces() {
		return Collections.singleton(MediaType.APPLICATION_JSON);
	}

	@Override
	public Set<MediaType> consumes() {
		return Collections.singleton(MediaType.APPLICATION_JSON);
	}

	@Override
	public Set<NameValueExpression<String>> headers() {
		return Collections.emptySet();
	}

	@Override
	public Set<NameValueExpression<String>> params() {
		return Collections.emptySet();
	}

	@Override
	public <T extends Annotation> Optional<T> findAnnotation(Class<T> annotation) {
		return ofNullable(AnnotationUtils.findAnnotation(request.getApiMethod(), annotation));
	}

	@Override
	public RequestHandlerKey key() {
		return new RequestHandlerKey(getPatternsCondition().getPatterns(), supportedMethods(), consumes(), produces());
	}

	@Override
	public springfox.documentation.spring.wrapper.RequestMappingInfo<?> getRequestMapping() {
		throw new RpcException("Deprecated", "Deprecated");
	}

	@Override
	public List<ResolvedMethodParameter> getParameters() {
		Parameter[] params = request.getApiMethod().getParameters();
		List<ResolvedMethodParameter> var8 = new ArrayList<>();
		if (params != null && params.length > 0) {
			int i = 0;
			for (Parameter param : params) {
				List<Annotation> allAnno = new ArrayList<>();
				allAnno.add(requestBodyAnnotation);
				allAnno.addAll(Arrays.asList(param.getAnnotations()));
				var8.add(new ResolvedMethodParameter(i++, param.getName(), allAnno,
						typeResolver.resolve(param.getType(), new Type[0])));
			}
		}
		return var8;
	}

	public static void getRequestBody(@RequestBody String body) {
	}

	@Override
	public ResolvedType getReturnType() {
		return typeResolver.resolve(RpcResponse.class, request.getApiMethod().getGenericReturnType());
	}

	@Override
	public <T extends Annotation> Optional<T> findControllerAnnotation(Class<T> annotation) {
		return ofNullable(AnnotationUtils.findAnnotation(request.getServiceClass(), annotation));
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", JsonRpcRequestHandler.class.getSimpleName() + "{", "}")
				.add("requestMapping=" + request.getServiceName()).add("handlerMethod=" + request.getApiName())
				.add("key=" + key()).toString();
	}
}
