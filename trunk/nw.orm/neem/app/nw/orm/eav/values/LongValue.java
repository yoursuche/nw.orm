package nw.orm.eav.values;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import nw.orm.eav.EavValue;

@Entity
@Table(name="LONG_VAL")
public class LongValue extends EavValue{

	private static final long serialVersionUID = 5853035053034366287L;
	
	@Column(name = "VALUE", nullable = true)
	private Long value;

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}
}
