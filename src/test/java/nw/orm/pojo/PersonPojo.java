package nw.orm.pojo;

import nw.orm.entity.Sex;

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
