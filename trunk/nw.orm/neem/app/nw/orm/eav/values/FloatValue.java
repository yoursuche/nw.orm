package nw.orm.eav.values;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import nw.orm.eav.EavValue;

@Entity
@Table(name = "FLOAT_VAL")
public class FloatValue extends EavValue {

	private static final long serialVersionUID = -217560342040100960L;
	
	@Column(name = "VALUE", nullable = true)
	private Float value;

	public Float getValue() {
		return value;
	}

	public void setValue(Float value) {
		this.value = value;
	}
}
