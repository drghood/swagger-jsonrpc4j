package com.demo.config.swagger;

import lombok.*;

import java.lang.reflect.Method;

/**
 * @author whets
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class JsonRpcRequest {

	private String contextPath;
	private String basePackage;
	private String serviceName;
	private Class<?> serviceClass;
	private String apiName;
	private Method apiMethod;
}
