package fi.rivermouth.talous.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fi.rivermouth.talous.domain.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}
