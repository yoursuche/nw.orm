package nw.orm.core.query;

import org.hibernate.FetchMode;

public class QueryFetchMode {
	
	private String alias;
	
	private FetchMode fetchMode = FetchMode.DEFAULT;

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public FetchMode getFetchMode() {
		return fetchMode;
	}

	public void setFetchMode(FetchMode fetchMode) {
		this.fetchMode = fetchMode;
	}

}
