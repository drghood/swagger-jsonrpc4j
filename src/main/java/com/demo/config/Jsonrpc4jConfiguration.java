package com.demo.config;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImplExporter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author whets
 */
@Configuration
public class Jsonrpc4jConfiguration {
	@Bean
	@ConditionalOnMissingBean(AutoJsonRpcServiceImplExporter.class)
	public static AutoJsonRpcServiceImplExporter rpcServiceImplExporter() {
		return new AutoJsonRpcServiceImplExporter();
	}
}
