package nw.orm.eav.presentation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import nw.orm.eav.enums.GuiType;

// TODO: Auto-generated Javadoc
/**
 * The Class GuiReference.
 */
@Entity
@Table(name = "GUI_DISPLAY")
public class GuiReference {
	
	/** The display type. */
	@Enumerated(EnumType.STRING)
	@Column(name = "DISPLAY_TYPE", nullable = false)
	private GuiType displayType;

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

}
