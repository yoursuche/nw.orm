package nw.orm.eav.presentation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import nw.orm.eav.enums.GuiType;

@Entity
@Table(name = "GUI_DISPLAY")
public class GuiReference {
	
	@Enumerated(EnumType.STRING)
	@Column(name = "DISPLAY_TYPE", nullable = false)
	private GuiType displayType;

	public GuiType getDisplayType() {
		return displayType;
	}

	public void setDisplayType(GuiType displayType) {
		this.displayType = displayType;
	}

}
