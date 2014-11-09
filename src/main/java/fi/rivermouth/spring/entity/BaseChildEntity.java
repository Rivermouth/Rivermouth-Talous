package fi.rivermouth.spring.entity;

import java.io.Serializable;

import javax.persistence.Entity;

@Entity
public abstract class BaseChildEntity
<PARENT extends BaseEntity<PARENT_ID>, PARENT_ID extends Serializable, 	// Parent
ID extends Serializable>  												// Child (this)
extends BaseEntity<ID> implements ChildEntityInterface<PARENT, PARENT_ID> {

	
	
}
