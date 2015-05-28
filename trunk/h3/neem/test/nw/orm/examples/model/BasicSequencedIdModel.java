package nw.orm.examples.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import nw.orm.core.IEntity;

@Entity
@Table(name = "BASIC_SEQ_MODEL")
public class BasicSequencedIdModel extends IEntity {

	private static final long serialVersionUID = 9144498485472808203L;

	@Column(name = "BASIC_STRING", length = 1024)
	private String basicString;

	@Column(name = "BASIC_DATE")
	private Date basicDate;

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

}
