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
	
	@OneToMany(fetch = FetchType.EAGER)
	@Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	private List<BankAccount> bankAccounts;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "payer")
	@Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	private List<Bill> billsToPay;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "biller")
	@Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	private List<Bill> billsToBill;

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

	public List<Bill> getBillsToPay() {
		return billsToPay;
	}

	public void setBillsToPay(List<Bill> billsToPay) {
		this.billsToPay = billsToPay;
	}

	public List<Bill> getBillsToBill() {
		return billsToBill;
	}

	public void setBillsToBill(List<Bill> billsToBill) {
		this.billsToBill = billsToBill;
	}

	@Override
	public String getKind() {
		return "company";
	}
	
}
