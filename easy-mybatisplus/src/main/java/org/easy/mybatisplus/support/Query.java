
package org.easy.mybatisplus.support;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 分页工具
 *
 */
@Data
@Accessors(chain = true)
@ApiModel(description = "查询条件")
public class Query {

	/**
	 * 当前页
	 */
	@ApiModelProperty(value = "当前页")
	private Integer current;
	public Integer getCurrent(){
		if(page!=null){
			return page;
		}
		return current;
	}

	/**
	 * 每页的数量
	 */
	@ApiModelProperty(value = "每页的数量")
	private Integer size;
	public Integer getSize(){
		if(limit!=null){
			return limit;
		}
		return size;
	}

	/**
	 * 排序的字段名
	 */
	@ApiModelProperty(hidden = true)
	private String ascs;

	/**
	 * 排序方式
	 */
	@ApiModelProperty(hidden = true)
	private String descs;

	private Integer page;//: 5
	private Integer start;//: 40
	private Integer limit;//: 10

}
