# EasyX-Tool
Spring cloud 常用工具类库，将各类库做成starter自动配置模式，真接依赖即用，方便快捷。

## easy-cloud-starter
#### 采用阿里开源服务治理组件nacos替代eureka。
Nacos 致力于帮助您发现、配置和管理微服务。Nacos 提供了一组简单易用的特性集，帮助您实现动态服务发现、服务配置管理、服务及流量管理。
Nacos 帮助您更敏捷和容易地构建、交付和管理微服务平台。 Nacos 是构建以“服务”为中心的现代应用架构(例如微服务范式、云原生范式)的服务基础设施。
https://nacos.io/zh-cn/docs/quick-start.html
#### 集成接口版本控制注解 @VersionMapping，@UrlVersion，@ProducesVersion
```
@RestController
@AllArgsConstructor
@VersionMapping("/dict")
@Api(value = "字典管理", tags = "字典管理")
@UrlVersion("v1")
public class DictController {

	private IDictService dictService;

}
```
#### 集成自定义的错误封装返回
 * -BladeErrorAttributes
 * -BladeErrorController
 * -BladeRestExceptionTranslator
 * -RestExceptionTranslator
  
