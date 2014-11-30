package fi.rivermouth.talous.domain;

import javax.persistence.Entity;

import fi.rivermouth.talous.model.Address;

@Entity
public class Employee extends AbstractPerson {

	public Employee() {}
	
	public Employee(Name name) {
		super(name);
	}
	
	public Employee(Name name, String role, Address address, String email, String tel) {
		super(name, role, address, email, tel);
	}
	
}
