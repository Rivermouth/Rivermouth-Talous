package fi.rivermouth.talous.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;

import fi.rivermouth.spring.entity.Responsable;
import fi.rivermouth.talous.model.Address;

@Entity
public abstract class AbstractCompany extends BaseEntity implements Responsable {

	@Column(unique = true)
	private String name;
	@Column(unique = true)
	private String vatNumber;
	@Embedded
	private Address address;
	
	public AbstractCompany() {
	}
	
	public AbstractCompany(String name) {
		this.name = name;
	}
	
	public AbstractCompany(String name, String vatNumber, Address address) {
		this(name);
		this.vatNumber = vatNumber;
		this.address = address;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getVatNumber() {
		return vatNumber;
	}

	public void setVatNumber(String vatNumber) {
		this.vatNumber = vatNumber;
	}
	
	public Address getAddress() {
		return address;
	}
	
	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public String getKind() {
		return "company";
	}
	
}
