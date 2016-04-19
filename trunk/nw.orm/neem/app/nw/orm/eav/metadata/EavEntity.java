package nw.orm.eav.metadata;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import nw.orm.core.IEntity;

@Entity
@Table(name = "EAV_ENTITY")
public class EavEntity extends IEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 588331126208702704L;

	@Column(name = "NAME", unique = true, nullable = false)
	private String name;
	
	@Column(name = "DESCRIPTION", length = 256, nullable = true)
	private String description;
	
	@OneToMany(mappedBy = "entity", cascade = CascadeType.ALL)
	private Set<EavField> fields;

	public Set<EavField> getFields() {
		return fields;
	}

	public void setFields(Set<EavField> fields) {
		if(fields == null){
			fields = new HashSet<EavField>();
		}
		this.fields = fields;
	}
	
	public void addField(EavField field) {
		this.getFields().add(field);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
