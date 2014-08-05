package nw.orm.eav;

import javax.persistence.Entity;
import javax.persistence.Table;

import nw.orm.base.REntity;

@Entity
@Table(name = "EAV_LIST_VALUE")
public class EavListItem extends REntity{
	
	private static final long serialVersionUID = -7046228201840882894L;
	
	private String listItem;

	public String getListItem() {
		return listItem;
	}

	public void setListItem(String listItem) {
		this.listItem = listItem;
	}

}
