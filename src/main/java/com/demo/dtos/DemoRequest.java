package com.demo.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @author whets
 */
@lombok.Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class DemoRequest {
	@ApiModelProperty("what's your name")
	private String name;
	@ApiModelProperty("what's your gender")
	private String gender;
}
