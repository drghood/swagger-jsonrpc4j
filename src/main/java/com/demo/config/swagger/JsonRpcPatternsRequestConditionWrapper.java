package com.demo.config.swagger;

import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;

import java.util.Set;
import java.util.stream.Collectors;

import static springfox.documentation.spring.web.paths.Paths.maybeChompLeadingSlash;
import static springfox.documentation.spring.web.paths.Paths.maybeChompTrailingSlash;

/**
 * @author whets
 */
public class JsonRpcPatternsRequestConditionWrapper
		implements springfox.documentation.spring.wrapper.PatternsRequestCondition<PatternsRequestCondition> {

	private final String contextPath;
	private final PatternsRequestCondition condition;

	public JsonRpcPatternsRequestConditionWrapper(String contextPath, PatternsRequestCondition condition) {
		this.contextPath = contextPath;
		this.condition = condition;
	}

	@Override
	public springfox.documentation.spring.wrapper.PatternsRequestCondition<?> combine(
			springfox.documentation.spring.wrapper.PatternsRequestCondition<PatternsRequestCondition> other) {
		if (other instanceof JsonRpcPatternsRequestConditionWrapper && !this.equals(other)) {
			return new JsonRpcPatternsRequestConditionWrapper(contextPath,
					condition.combine(((JsonRpcPatternsRequestConditionWrapper) other).condition));
		}
		return this;
	}

	@Override
	public Set<String> getPatterns() {
		return this.condition.getPatterns().stream()
				.map(p -> String.format("%s/%s", maybeChompTrailingSlash(contextPath), maybeChompLeadingSlash(p)))
				.collect(Collectors.toSet());
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof JsonRpcPatternsRequestConditionWrapper) {
			return this.condition.equals(((JsonRpcPatternsRequestConditionWrapper) o).condition);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.condition.hashCode();
	}

	@Override
	public String toString() {
		return this.condition.toString();
	}
}
