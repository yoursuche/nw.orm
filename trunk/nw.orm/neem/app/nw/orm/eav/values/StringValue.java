package nw.orm.eav.values;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import nw.orm.eav.EavValue;

@Entity
@Table(name="STR_VAL")
public class StringValue extends EavValue {
	
	private static final long serialVersionUID = 3333955139431299514L;
	
	@Column(name = "VALUE", nullable = true)
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
