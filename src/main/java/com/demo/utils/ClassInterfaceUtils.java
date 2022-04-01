package com.demo.utils;

import org.apache.commons.lang3.ClassUtils;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * @author whets
 */
public class ClassInterfaceUtils {

	public static Class<?> findAnnotatedType(Class<?> targetType, Class<? extends Annotation> annotationType) {
		Class<?> annoClass = null;
		if (!targetType.isAnnotationPresent(annotationType)) {
			List<Class<?>> supClasses = ClassUtils.getAllInterfaces(targetType);
			for (Class<?> sc : supClasses) {
				if (sc.isAnnotationPresent(annotationType)) {
					annoClass = sc;
					break;
				}
			}
		} else {
			annoClass = targetType;
		}
		return annoClass;
	}
}
