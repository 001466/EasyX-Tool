
package org.easy.mybatisplus.support;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.easy.tool.util.BeanUtil;
import org.easy.tool.util.Func;
import org.easy.tool.util.StringUtil;


import java.util.Map;

/**
 * 分页工具
 *
 */
public class Condition {

	/**
	 * 转化成mybatis plus中的Page
	 *
	 * @param query
	 * @return
	 */
	public static <T> IPage<T> getPage(Query query) {
		Page<T> page = new Page<>(query.getCurrent()==null?1:query.getCurrent(), query.getSize()==null?10:query.getSize());
		page.setAsc(Func.toStrArray(query.getAscs()));
		page.setDesc(Func.toStrArray(query.getDescs()));
		return page;
	}

	/**
	 * 获取mybatis plus中的QueryWrapper
	 *
	 * @param entity
	 * @param <T>
	 * @return
	 */
	public static <T> QueryWrapper<T> getQueryWrapper(T entity) {
		return new QueryWrapper<>(entity);
	}

	/**
	 * 获取mybatis plus中的QueryWrapper
	 *
	 * @param query
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	public static <T> QueryWrapper<T> getQueryWrapper(Map<String, Object> query, Class<T> clazz) {
		query.remove("current");
		query.remove("size");
		QueryWrapper<T> qw = new QueryWrapper<>();
		qw.setEntity(BeanUtil.newInstance(clazz));
		if (Func.isNotEmpty(query)) {
			query.forEach((k, v) -> {
				if (Func.isNotEmpty(v)) {
					qw.like(StringUtil.humpToUnderline(k), v);
				}
			});
		}
		return qw;
	}

}
