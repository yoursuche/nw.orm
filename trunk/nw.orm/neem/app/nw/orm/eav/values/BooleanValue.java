package nw.orm.eav.values;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import nw.orm.eav.EavValue;

@Entity
@Table(name = "BOOL_VAL")
public class BooleanValue extends EavValue {

	private static final long serialVersionUID = -7316677900193250848L;
	
	@Column(name = "VALUE", nullable = true)
	private Boolean value;

	public Boolean getValue() {
		return value;
	}

	public void setValue(Boolean value) {
		this.value = value;
	}

}
