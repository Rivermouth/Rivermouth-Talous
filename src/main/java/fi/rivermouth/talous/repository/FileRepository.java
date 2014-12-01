package fi.rivermouth.talous.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fi.rivermouth.talous.domain.File;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
	public List<File> findByOwner(Long owner);
	public List<File> findByOwnerAndCollectionIn(Long owner, Collection<String> collections);
	public List<File> findByOwnerAndCollectionNotIn(Long owner, Collection<String> collections);
	public List<File> findByOwnerAndCollectionInAndAttachedTo(Long owner, Collection<String> collections, Long attachedTo);
	public List<File> findByOwnerAndCollectionNotInAndAttachedTo(Long owner, Collection<String> collections, Long attachedTo);
}
