package fi.rivermouth.talous.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fi.rivermouth.talous.domain.File;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
	public List<File> findByOwner(Long owner);
	public List<File> findByOwnerAndCollection(Long owner, String collection);
	public List<File> findByOwnerAndCollectionAndAttachedTo(Long owner, String collection, Long attachedTo);
}
