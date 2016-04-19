package nw.orm.eav.values;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import nw.orm.eav.EavValue;

@Entity
@Table(name = "INT_VAL")
public class IntValue extends EavValue {
	
	private static final long serialVersionUID = 2359837806817637385L;
	
	@Column(name = "VALUE", nullable = true)
	private Integer value;

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

}
