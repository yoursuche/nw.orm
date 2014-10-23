package nw.orm.eav.values;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import nw.orm.eav.EavValue;

@Entity
@Table(name = "BYTE_AREA_VAL")
public class ByteArrayValue extends EavValue {

	private static final long serialVersionUID = 5379887179638806200L;
	
	@Column(name = "VALUE", nullable = true, columnDefinition = "bytea")
	private byte[] value;

	public byte[] getValue() {
		return value;
	}

	public void setValue(byte[] value) {
		this.value = value;
	}
}
