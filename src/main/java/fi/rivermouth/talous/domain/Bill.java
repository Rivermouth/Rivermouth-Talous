package fi.rivermouth.talous.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Bill extends BaseEntity {
	
	private String name;
	
	@OneToOne
	private BankAccount paymentBankAccount;
	
	@ManyToOne
	@JoinColumn(name = "biller_id", nullable = true)
	private Company biller;
	@ManyToOne
	@JoinColumn(name = "payer_id", nullable = true)
	private Company payer;
	
	private Integer invoiceNumber;
	private Integer referenceNumber;
	
	@NotNull
	private Date date;
	private Date dueDate;
	private Integer datesToPay;
	private Double interest; // viivästyskorko
	
	private String info;
	private String remarks;

	@Embedded
	private Total total;
	
	@ElementCollection
	private List<Item> breakdown;
	
	public Bill() {}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BankAccount getPaymentBankAccount() {
		return paymentBankAccount;
	}

	public void setPaymentBankAccount(BankAccount paymentBankAccount) {
		this.paymentBankAccount = paymentBankAccount;
	}

	public Company getBiller() {
		return biller;
	}

	public void setBiller(Company biller) {
		this.biller = biller;
	}

	public Company getPayer() {
		return payer;
	}

	public void setPayer(Company payer) {
		this.payer = payer;
	}

	public Integer getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(Integer invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public Integer getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(Integer referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Integer getDatesToPay() {
		return datesToPay;
	}

	public void setDatesToPay(Integer datesToPay) {
		this.datesToPay = datesToPay;
	}

	public Double getInterest() {
		return interest;
	}

	public void setInterest(Double interest) {
		this.interest = interest;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

//	public Total getTotal() {
//		return total;
//	}
//
//	public void setTotal(Total total) {
//		this.total = total;
//	}

	public List<Item> getBreakdown() {
		return breakdown;
	}

	public void setBreakdown(List<Item> breakdown) {
		this.breakdown = breakdown;
	}

	@Embeddable
	public static class Total {
		private Double total;
		private Double vat;
		
		public Total() {}
		
		public Double getTotal() {
			return total;
		}
		
		public void setTotal(Double total) {
			this.total = total;
		}
		
		public Double getVat() {
			return vat;
		}
		
		public void setVat(Double vat) {
			this.vat = vat;
		}
	}

	@Embeddable
	public static class Item {
		private String description;
		private Double count;
		private String unit;
		private Double price;
		private Double vatPercentage;
		private Double total;
		private Double totalVat;
		
		public Item() {}
		
		public String getDescription() {
			return description;
		}
		
		public void setDescription(String description) {
			this.description = description;
		}
		
		public Double getCount() {
			return count;
		}
		
		public void setCount(Double count) {
			this.count = count;
		}
		
		public String getUnit() {
			return unit;
		}
		
		public void setUnit(String unit) {
			this.unit = unit;
		}
		
		public Double getPrice() {
			return price;
		}
		
		public void setPrice(Double price) {
			this.price = price;
		}
		
		public Double getVatPercentage() {
			return vatPercentage;
		}
		
		public void setVatPercentage(Double vatPercentage) {
			this.vatPercentage = vatPercentage;
		}
		
		public Double getTotal() {
			return total;
		}
		
		public void setTotal(Double total) {
			this.total = total;
		}
		
		public Double getTotalVat() {
			return totalVat;
		}
		
		public void setTotalVat(Double totalVat) {
			this.totalVat = totalVat;
		}
	}
	
	@Override
	public String getKind() {
		return "bill";
	}

}
