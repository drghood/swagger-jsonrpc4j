package com.demo.config;

import com.googlecode.jsonrpc4j.JsonRpcMethod;
import com.googlecode.jsonrpc4j.JsonRpcService;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Response;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author whets
 */
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
@ConfigurationProperties(prefix = "swagger.info")
public class Swagger2Configuration extends WebMvcConfigurationSupport {

	static {
		System.setProperty("spring.mvc.pathmatch.matching-strategy", "ANT_PATH_MATCHER");
	}

	private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {"classpath:/META-INF/resources/",
			"classpath:/resources/", "classpath:/static/", "classpath:/public/"};

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
		registry.addResourceHandler("/swagger-ui/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

	@Bean
	public Docket api(ApiInfo info) {
		List<Response> responses = new ArrayList<>();
		return new Docket(DocumentationType.OAS_30).apiInfo(info)
				// default responses，status=200 responses
				.globalResponses(HttpMethod.GET, responses).globalResponses(HttpMethod.POST, responses)
				.globalResponses(HttpMethod.PUT, responses).globalResponses(HttpMethod.DELETE, responses)
				.globalResponses(HttpMethod.HEAD, responses).globalResponses(HttpMethod.OPTIONS, responses)
				.globalResponses(HttpMethod.PATCH, responses).globalResponses(HttpMethod.TRACE, responses)
				// ApiSelectorBuilder
				.select()
				// api
				// .apis(RequestHandlerSelectors.basePackage("com.demo"))
				// jsonrpc service
				.apis(RequestHandlerSelectors.withClassAnnotation(JsonRpcService.class))
				// jsonrpc method
				.apis(RequestHandlerSelectors.withMethodAnnotation(JsonRpcMethod.class))
				// url
				.paths(PathSelectors.any()).build();
	}

	@Bean
	public ApiInfo customizeApiInfo() {
		springfox.documentation.service.Contact c = null;
		if (contact != null) {
			c = new springfox.documentation.service.Contact(contact.getName(), contact.getUrl(), contact.getEmail());
		}
		return new ApiInfoBuilder()
				.title(title)
				.description(description)
				.version(version)
				.termsOfServiceUrl(termsOfServiceUrl)
				.license(license).licenseUrl(licenseUrl)
				.contact(c).extensions(vendorExtensions).build();
	}

	private String version = "1.0";
	private String title = "open API";
	private String description = "swagger auto generate by whets";
	private String termsOfServiceUrl;
	private String license;
	private String licenseUrl;
	private Contact contact = new Contact();
	private List<VendorExtension> vendorExtensions = Collections.emptyList();

	public void setVersion(String version) {
		this.version = version;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setTermsOfServiceUrl(String termsOfServiceUrl) {
		this.termsOfServiceUrl = termsOfServiceUrl;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public void setLicenseUrl(String licenseUrl) {
		this.licenseUrl = licenseUrl;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public Contact getContact() {
		return new Contact();
	}

	public void setVendorExtensions(List<VendorExtension> vendorExtensions) {
		this.vendorExtensions = vendorExtensions;
	}

	class Contact {
		private String name = "whets";
		private String url;
		private String email;

		public String getName() {
			return name;
		}

		public String getUrl() {
			return url;
		}

		public String getEmail() {
			return email;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public void setEmail(String email) {
			this.email = email;
		}
	}
}