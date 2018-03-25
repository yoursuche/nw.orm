package nw.orm.pojo;

import java.io.Serializable;

public class ResultPojo {
	
	private Long rowCount;
	private Long max;
	private Serializable age;
	public Long getRowCount() {
		return rowCount;
	}
	public void setRowCount(Long rowCount) {
		this.rowCount = rowCount;
	}
	public Long getMax() {
		return max;
	}
	public void setMax(Long max) {
		this.max = max;
	}
	public Serializable getAge() {
		return age;
	}
	public void setAge(Serializable age) {
		this.age = age;
	}
	

}
