package fi.rivermouth.talous.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fi.rivermouth.talous.domain.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

}
