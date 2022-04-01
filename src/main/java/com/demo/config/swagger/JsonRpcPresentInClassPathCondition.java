package com.demo.config.swagger;

import springfox.documentation.common.ClassPresentInClassPathCondition;

/**
 * @author whets
 */
public class JsonRpcPresentInClassPathCondition extends ClassPresentInClassPathCondition {
	@Override
	protected String getClassName() {
		return "com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl";
	}
}
