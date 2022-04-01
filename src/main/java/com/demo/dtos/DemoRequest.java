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
public class DemoRequest {
	private String name;
	private String gender;
}
