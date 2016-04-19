package nw.orm.eav;

import javax.persistence.Entity;
import javax.persistence.Table;

import nw.orm.core.REntity;

// TODO: Auto-generated Javadoc
/**
 * The Class EavListItem.
 */
@Entity
@Table(name = "EAV_LIST_VALUE")
public class EavListItem extends REntity{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7046228201840882894L;
	
	/** The list item. */
	private String listItem;

	/**
	 * Gets the list item.
	 *
	 * @return the list item
	 */
	public String getListItem() {
		return listItem;
	}

	/**
	 * Sets the list item.
	 *
	 * @param listItem the new list item
	 */
	public void setListItem(String listItem) {
		this.listItem = listItem;
	}

}
