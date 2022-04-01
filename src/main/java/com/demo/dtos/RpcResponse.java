package com.demo.dtos;

import lombok.*;

/**
 * @author whets
 */
@lombok.Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RpcResponse<T> {
	private String id;
	@Builder.Default
	private String jsonrpc = "2.0";
	private Error error;
	private T result;
}