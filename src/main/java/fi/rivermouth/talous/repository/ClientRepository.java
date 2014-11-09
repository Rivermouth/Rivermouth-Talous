package fi.rivermouth.talous.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fi.rivermouth.talous.domain.Client;
import fi.rivermouth.talous.domain.User;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
	public List<Client> findByParentId(Long parentId);
}
