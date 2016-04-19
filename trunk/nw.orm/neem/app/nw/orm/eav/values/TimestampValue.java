package nw.orm.eav.values;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import nw.orm.eav.EavValue;

@Entity
@Table(name = "TIMESTAMP_VAL")
public class TimestampValue extends EavValue {
	
	private static final long serialVersionUID = -3466357983533502788L;
	
	@Column(name = "VALUE", nullable = true)
	private Timestamp value;

	public Timestamp getValue() {
		return value;
	}

	public void setValue(Timestamp value) {
		this.value = value;
	}
}
