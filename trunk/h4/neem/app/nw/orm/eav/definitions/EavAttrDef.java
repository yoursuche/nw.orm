package nw.orm.eav.definitions;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import nw.orm.core.REntity;
import nw.orm.eav.EavList;
import nw.orm.eav.enums.FieldDataType;
import nw.orm.eav.enums.GuiType;

// TODO: Auto-generated Javadoc
/**
 * Attribute Meta Data.
 * Defines Data Type, Display Type
 * @author Ogwara O. Rowland
 *
 */
@Entity
@Table(name = "EAV_ATTR_DEF")
public class EavAttrDef extends REntity{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5398192974248001224L;
	
	/** The data type. */
	@Enumerated(EnumType.STRING)
	@Column(name = "DATA_TYPE")
	private FieldDataType dataType; // data type
	
	/** The display type. */
	@Enumerated(EnumType.STRING)
	@Column(name = "DISPLAY_TYPE")
	private GuiType displayType; // display type
	
	/** The list items. */
	@ManyToOne(optional = false)
	@JoinColumn(name = "EAV_LIST_FK")
	private EavList listItems;

	/**
	 * Gets the data type.
	 *
	 * @return the data type
	 */
	public FieldDataType getDataType() {
		return dataType;
	}

	/**
	 * Sets the data type.
	 *
	 * @param dataType the new data type
	 */
	public void setDataType(FieldDataType dataType) {
		this.dataType = dataType;
	}

	/**
	 * Gets the display type.
	 *
	 * @return the display type
	 */
	public GuiType getDisplayType() {
		return displayType;
	}

	/**
	 * Sets the display type.
	 *
	 * @param displayType the new display type
	 */
	public void setDisplayType(GuiType displayType) {
		this.displayType = displayType;
	}

	/**
	 * Gets the list items.
	 *
	 * @return the list items
	 */
	public EavList getListItems() {
		return listItems;
	}

	/**
	 * Sets the list items.
	 *
	 * @param listItems the new list items
	 */
	public void setListItems(EavList listItems) {
		this.listItems = listItems;
	}
}
