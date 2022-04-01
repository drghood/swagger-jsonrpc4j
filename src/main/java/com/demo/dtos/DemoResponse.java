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
public class DemoResponse {
	private String greeting;
	private String toilet;
}
