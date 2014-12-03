package fi.rivermouth.talous.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;

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
	@OneToMany(fetch = FetchType.LAZY)
	@Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	private List<BankAccount> bankAccounts;

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
	
	public List<BankAccount> getBankAccounts() {
		return bankAccounts;
	}

	public void setBankAccounts(List<BankAccount> bankAccounts) {
		this.bankAccounts = bankAccounts;
	}

	@Override
	public String getKind() {
		return "company";
	}
	
}
