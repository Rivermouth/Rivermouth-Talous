package fi.rivermouth.talous.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fi.rivermouth.spring.entity.BaseEntity;
import fi.rivermouth.talous.domain.AbstractNote;

/**
 * 
 * @author Karri Rasinmäki
 *
 * @param <T> Note
 * @param <V> Entity attached to
 * @param <V_ID> Entity V ID type (extends Serializable)
 */
@Repository
public abstract interface AbstractNoteRepository<T extends AbstractNote<V, V_ID>, V extends BaseEntity<V_ID>, V_ID extends Serializable> 
extends JpaRepository<T, Long> {
	public List<T> findByParentId(V_ID parentId);
}
