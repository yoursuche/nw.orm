package nw.orm.examples.pojo;

public class TestPox {
	
	private Integer age;
	
	private Long size;

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
	
	@Override
	public String toString() {
		return "{AGE: " + age + ", SIZE: " + size +" }";
	}

}
