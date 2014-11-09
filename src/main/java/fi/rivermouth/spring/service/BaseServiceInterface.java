package fi.rivermouth.spring.service;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseServiceInterface<T, ID extends Serializable> {
	public JpaRepository<T, ID> getRepository();
}
