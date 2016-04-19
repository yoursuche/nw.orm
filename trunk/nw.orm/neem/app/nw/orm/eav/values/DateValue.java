package nw.orm.eav.values;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import nw.orm.eav.EavValue;

@Entity
@Table(name = "DATE_VAL")
public class DateValue extends EavValue {
	
	private static final long serialVersionUID = 1099196834781609499L;
	
	@Column(name = "VALUE", nullable = true)
	private Date value;

	public Date getValue() {
		return value;
	}

	public void setValue(Date value) {
		this.value = value;
	}
}
