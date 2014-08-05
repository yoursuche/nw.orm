package nw.orm.eav.definitions;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import nw.orm.base.REntity;
import nw.orm.eav.EavList;
import nw.orm.eav.enums.FieldDataType;
import nw.orm.eav.enums.GuiType;

/**
 * Attribute Meta Data.
 * Defines Data Type, Display Type
 * @author Ogwara O. Rowland
 *
 */
@Entity
@Table(name = "EAV_ATTR_DEF")
public class EavAttrDef extends REntity{

	private static final long serialVersionUID = 5398192974248001224L;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "DATA_TYPE")
	private FieldDataType dataType; // data type
	
	@Enumerated(EnumType.STRING)
	@Column(name = "DISPLAY_TYPE")
	private GuiType displayType; // display type
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "EAV_LIST_FK")
	private EavList listItems;

	public FieldDataType getDataType() {
		return dataType;
	}

	public void setDataType(FieldDataType dataType) {
		this.dataType = dataType;
	}

	public GuiType getDisplayType() {
		return displayType;
	}

	public void setDisplayType(GuiType displayType) {
		this.displayType = displayType;
	}

	public EavList getListItems() {
		return listItems;
	}

	public void setListItems(EavList listItems) {
		this.listItems = listItems;
	}
}
