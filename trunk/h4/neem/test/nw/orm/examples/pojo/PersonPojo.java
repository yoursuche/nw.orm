package nw.orm.examples.pojo;

import nw.orm.examples.model.enums.Sex;

public class PersonPojo {
	
	private Sex sex;
	
	private int age;

	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

}
