package nw.orm.eav;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Table;

import nw.orm.core.REntity;

// TODO: Auto-generated Javadoc
/**
 * The Class EavList.
 */
@Entity
@Table(name = "EAV_LIST_VALUE")
public class EavList extends REntity{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3627883708512479442L;
	
	/** The list values. */
	private Set<EavListItem> listValues;

	/**
	 * Gets the list values.
	 *
	 * @return the list values
	 */
	public Set<EavListItem> getListValues() {
		if(listValues == null)
			return new HashSet<EavListItem>();
		return listValues;
	}

	/**
	 * Sets the list values.
	 *
	 * @param listValues the new list values
	 */
	public void setListValues(Set<EavListItem> listValues) {
		this.listValues = listValues;
	}
}
