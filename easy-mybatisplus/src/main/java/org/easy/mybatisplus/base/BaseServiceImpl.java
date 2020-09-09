
package org.easy.mybatisplus.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.easy.tool.support.BaseEntity;
import org.easy.tool.util.BeanUtil;
import org.springframework.validation.annotation.Validated;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

/**
 * 业务封装基础类
 *
 * @param <M> mapper
 * @param <T> model
 */
@Validated
public class BaseServiceImpl<M extends BaseMapper<T>, T extends BaseEntity> extends ServiceImpl<M, T> implements BaseService<T> {

	private Class<T> modelClass;

	@SuppressWarnings("unchecked")
	public BaseServiceImpl() {
		Type type = this.getClass().getGenericSuperclass();
		this.modelClass = (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[1];
	}

	@Override
	public boolean save(T entity) {
		entity.setCreateTime(new Date());
		entity.setUpdateTime(new Date());
		return super.save(entity);
	}

	@Override
	public boolean updateById(T entity) {
		entity.setUpdateTime(new Date());
		return super.updateById(entity);
	}

	@Override
	public boolean deleteLogic(List<Integer> ids) {
		T entity = BeanUtil.newInstance(modelClass);
		entity.setUpdateTime(new Date());
		return super.update(entity, Wrappers.<T>update().lambda().in(T::getId, ids)) && super.removeByIds(ids);
	}

}
