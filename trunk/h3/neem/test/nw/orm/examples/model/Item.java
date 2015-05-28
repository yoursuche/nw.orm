package nw.orm.examples.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;





import nw.orm.core.IEntity;

@Entity
@Table(name = "ITEM")
public class Item extends IEntity {

	private static final long serialVersionUID = 660956787631064317L;

	private String name;
	
	@ManyToOne(optional = false, cascade=CascadeType.ALL)
	@JoinColumn(name = "PERSON_FK")
	private Person owner;

	public Person getOwner() {
		return owner;
	}

	public void setOwner(Person owner) {
		this.owner = owner;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
