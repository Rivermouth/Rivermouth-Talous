package fi.rivermouth.spring.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Service;

import fi.rivermouth.spring.entity.BaseEntity;

@Service
public abstract class BaseService<T extends BaseEntity<ID>, ID extends Serializable> implements BaseServiceInterface<T, ID> {

	public long count() {
		return getRepository().count();
	}
	
	public T get(ID id) {
		return getRepository().findOne(id);
	}
	
	public List<T> list() {
		return getRepository().findAll();
	}

	public boolean delete(ID id) {
		if (!exists(id)) return false;
		getRepository().delete(id);
		return true;
	}
	
	public void delete(Iterable<? extends T> entities) {
//		getRepository().delete(entities);
		for(T entity : entities) {
			delete(entity);
		}
	}
	
	public boolean delete(T entity) {
		if (entity == null || entity.getId() == null || !exists(entity)) return false;
		getRepository().delete(entity);
		return true;
	}
	
	public void deleteAll() {
		getRepository().deleteAll();
	}
	
	public boolean exists(ID id) {
		return getRepository().exists(id);
	}
	
	public <S extends T> boolean exists(S entity) {
		if (entity == null || entity.getId() == null) return false;
		return getRepository().exists(entity.getId());
	}
	
	/**
	 * Create entities
	 * @param entities
	 * @return {@code Iterable<S>}
	 */
	public <S extends T> Iterable<S> create(Iterable<S> entities) {
		return getRepository().save(entities);
	}
	
	/**
	 * Create {@code entity}
	 * @param entity
	 * @return {@code entity}
	 */
	public <S extends T> S create(S entity) {
		return getRepository().save(entity);
	}
	
	/**
	 * Update entities that already exists {@code exists(entity)}
	 * @param {@code Iterable<S>}
	 * @return {@code Iterable<S>}
	 */
	public <S extends T> Iterable<S> update(Iterable<S> entities) {
		while (entities.iterator().hasNext()) {
			if (!exists(entities.iterator().next())) {
				entities.iterator().remove();
			}
		}
		return getRepository().save(entities);
	}

	/**
	 * Update {@code entity} only if entity already exists {@code exists(entity)}
	 * @param {@code S entity}
	 * @return {@code S entity} or null if entity does not exists
	 */
	public <S extends T> S update(S entity) {
		if (!exists(entity)) return null;
		return getRepository().save(entity);
	}
	
	/**
	 * Update or create entities
	 * @param entities
	 * @return {@code Iterable<S>}
	 */
	public <S extends T> Iterable<S> save(Iterable<S> entities) {
		return getRepository().save(entities);
	}

	/**
	 * Update or create entity
	 * @param entity
	 * @return {@code S entity}
	 */
	public <S extends T> S save(S entity) {
		return getRepository().save(entity);
	}
	
}
