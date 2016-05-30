package nw.orm.examples.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import nw.orm.core.REntity;

@Entity
@Table(name = "BASIC_UUID")
public class BasicUUIDModel extends REntity {

	private static final long serialVersionUID = 6999386814421943302L;

	@Column(name = "BASIC_STRING", length = 1024)
	private String basicString;

	@Column(name = "BASIC_DATE")
	private Date basicDate;
	
	@Column(name = "GENERIC_FIELD")
	private Object genField;

	public String getBasicString() {
		return basicString;
	}
	public void setBasicString(String basicString) {
		this.basicString = basicString;
	}
	public Date getBasicDate() {
		return basicDate;
	}
	public void setBasicDate(Date basicDate) {
		this.basicDate = basicDate;
	}
	public Object getGenField() {
		return genField;
	}
	public void setGenField(Object genField) {
		this.genField = genField;
	}
}
