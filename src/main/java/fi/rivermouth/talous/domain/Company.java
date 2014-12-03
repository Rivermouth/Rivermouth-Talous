package fi.rivermouth.talous.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;

import fi.rivermouth.talous.model.Address;

@Entity
public class Company extends AbstractCompany {
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "payer")
	@Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	private List<Bill> billsToPay;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "biller")
	@Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	private List<Bill> billsToBill;

	public Company() {
		super();
	}

	public Company(String name) {
		super(name);
	}

	public Company(String name, String vatNumber, Address address) {
		super(name, vatNumber, address);
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

}
