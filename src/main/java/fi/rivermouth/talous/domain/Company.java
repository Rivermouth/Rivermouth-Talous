package fi.rivermouth.talous.domain;

import javax.persistence.Entity;

import fi.rivermouth.talous.model.Address;

@Entity
public class Company extends AbstractCompany<Long> {

	public Company() {
		super();
	}

	public Company(String name) {
		super(name);
	}

	public Company(String name, String vatNumber, Address address) {
		super(name, vatNumber, address);
	}

}
