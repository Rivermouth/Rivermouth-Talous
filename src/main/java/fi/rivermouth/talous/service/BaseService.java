package fi.rivermouth.talous.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Service;

import fi.rivermouth.talous.domain.BaseEntity;

@Service
public abstract class BaseService<T extends BaseEntity, ID extends Serializable> implements BaseServiceInterface<T, ID> {

	public long count() {
		return getRepository().count();
	}
	
	public T get(ID id) {
		return getRepository().findOne(id);
	}
	
	public List<T> list() {
		return getRepository().findAll();
	}

	public void delete(ID id) {
		getRepository().delete(id);
	}
	
	public void delete(Iterable<? extends T> entities) {
		getRepository().delete(entities);
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
	
	@SuppressWarnings("unchecked")
	public <S extends T> boolean exists(S entity) {
		if (entity == null || entity.getId() == null) return false;
		return getRepository().exists((ID) entity.getId());
	}
	
	/**
	 * Create entities that does not already exists @see exists()
	 * @param entities
	 * @return
	 */
	public <S extends T> Iterable<S> create(Iterable<S> entities) {
		while (entities.iterator().hasNext()) {
			if (exists(entities.iterator().next())) {
				entities.iterator().remove();
			}
		}
		return getRepository().save(entities);
	}
	
	/**
	 * Create entity only if it does not already exists @see exists()
	 * @param entity
	 * @return
	 */
	public <S extends T> S create(S entity) {
		if (exists(entity)) return null;
		return getRepository().save(entity);
	}
	
	/**
	 * Update entities that already exists @see exists()
	 * @param entities
	 * @return Iterable<S>
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
	 * Update entity only if entity already exists @see exists()
	 * @param entity
	 * @return <S> or null if entity does not exists
	 */
	public <S extends T> S update(S entity) {
		if (!exists(entity)) return null;
		return getRepository().save(entity);
	}
	
	/**
	 * Update or create entities that are unique @see isUnique()
	 * @param entities
	 * @return
	 */
	public <S extends T> Iterable<S> save(Iterable<S> entities) {
		while (entities.iterator().hasNext()) {
			if (!isUnique(entities.iterator().next())) {
				entities.iterator().remove();
			}
		}
		return getRepository().save(entities);
	}

	/**
	 * Update or create entity if unique @see isUnique()
	 * @param entity
	 * @return <S> or null if not unique
	 */
	public <S extends T> S save(S entity) {
		if (!isUnique(entity)) return null;
		return getRepository().save(entity);
	}
	
	public <S extends T> boolean isUnique(S entity) {
		return !exists(entity);
	}
	
}
