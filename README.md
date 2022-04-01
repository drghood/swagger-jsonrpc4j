# swagger-jsonrpc4j

## 1. Coding work

### 1.1 Swagger @Api annotation on @JsonRpcService annotated Service

```java
    @Api("sample")
    @JsonRpcService("demo")
    public class DemoApi{}
```

### 1.2 Swagger @ApiOperation annotation on @JsonRpcMethod annotated Method

```java
@Api("sample")
@JsonRpcService("demo")
public class DemoApi{
    @ApiOperation("say hello and tell him/her which toilet to use")
    @JsonRpcMethod("greeting")
    public DemoResponse hello(DemoRequest req)(){
	    return null;
    }
}
```

### 1.3 Swagger @ApiModel,@ApiModelProperty etc. on Models and Fields

```java
@ApiModel
public class DemoRequest {
	@ApiModelProperty("what's your name")
	private String name;
	@ApiModelProperty("what's your gender")
	private String gender;
}
```

## 2. Use swagger OAS

```url
http://localhost:8080/v2/api-docs
```
or
```url
http://localhost:8080/v3/api-docs
```

## 3. Use swagger-ui

```url
http://localhost:8080/swagger-ui.html
```

## 4. Extension & Reference

The [swagger-bootstrap-ui](https://github.com/xiaoymin/swagger-bootstrap-ui) contains swagger ui extension.
The [swagger-api](https://github.com/swagger-api/swagger-core) contains annotation documentation, samples etc.