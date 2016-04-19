package nw.orm.eav.values;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import nw.orm.eav.EavValue;

@Entity
@Table(name = "DOUBLE_VAL")
public class DoubleValue extends EavValue {
	
	private static final long serialVersionUID = 6558689694028819965L;
	
	@Column(name = "VALUE", nullable = true)
	private Double value;

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

}
