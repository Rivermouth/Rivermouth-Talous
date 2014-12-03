package fi.rivermouth.talous.domain;

import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;

@Entity
public class BankAccount extends BaseEntity {
	
	@NotBlank
	private String number;
	@NotBlank
	private String shortcode;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getShortcode() {
		return shortcode;
	}

	public void setShortcode(String shortcode) {
		this.shortcode = shortcode;
	}

	@Override
	public String getKind() {
		return "bank account";
	}

}
