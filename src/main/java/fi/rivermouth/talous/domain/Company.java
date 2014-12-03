package fi.rivermouth.talous.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;

import fi.rivermouth.talous.model.Address;

@Entity
public class Company extends AbstractCompany {

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
