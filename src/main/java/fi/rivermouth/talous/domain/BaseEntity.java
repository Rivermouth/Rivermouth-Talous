package fi.rivermouth.talous.domain;

import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;

import org.springframework.stereotype.Component;

@MappedSuperclass
public abstract class BaseEntity extends fi.rivermouth.spring.entity.BaseEntity<Long> {

}
