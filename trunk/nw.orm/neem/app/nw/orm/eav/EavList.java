package nw.orm.eav;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Table;

import nw.orm.core.REntity;

@Entity
@Table(name = "EAV_LIST_VALUE")
public class EavList extends REntity{

	private static final long serialVersionUID = -3627883708512479442L;
	
	private Set<EavListItem> listValues;

	public Set<EavListItem> getListValues() {
		if(listValues == null)
			return new HashSet<EavListItem>();
		return listValues;
	}

	public void setListValues(Set<EavListItem> listValues) {
		this.listValues = listValues;
	}
}
