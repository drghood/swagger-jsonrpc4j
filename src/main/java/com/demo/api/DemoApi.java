package com.demo.api;

import com.demo.dtos.DemoRequest;
import com.demo.dtos.DemoResponse;
import com.googlecode.jsonrpc4j.JsonRpcMethod;
import com.googlecode.jsonrpc4j.JsonRpcService;
import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author whets
 */
@Service
@JsonRpcService("demo")
@AutoJsonRpcServiceImpl
public class DemoApi {

	@JsonRpcMethod("greeting")
	public DemoResponse hello(DemoRequest req) {
		return DemoResponse.builder().greeting("hello " + req.getName())
				.toilet("please use '" + ("女".equals(req.getGender()) ?
						"F" : "M") + "' toilet").build();
	}
}