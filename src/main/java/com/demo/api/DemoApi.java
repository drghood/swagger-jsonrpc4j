package com.demo.api;

import com.demo.dtos.DemoRequest;
import com.demo.dtos.DemoResponse;
import com.googlecode.jsonrpc4j.JsonRpcMethod;
import com.googlecode.jsonrpc4j.JsonRpcService;
import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Service;

/**
 * @author whets
 */
@Service
@JsonRpcService("demo")
@AutoJsonRpcServiceImpl
@Api("sample API")
public class DemoApi {

	@JsonRpcMethod("greeting")
	@ApiOperation("say hello and tell him/her which toilet to use")
	public DemoResponse hello(DemoRequest req) {
		return DemoResponse.builder().greeting("hello " + req.getName())
				.toilet("please use '" + ("女".equals(req.getGender()) ?
						"F" : "M") + "' toilet").build();
	}
}