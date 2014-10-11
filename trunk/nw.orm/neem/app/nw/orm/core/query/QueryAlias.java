package nw.orm.core.query;

import org.hibernate.criterion.Criterion;

/**
 * Short hand for building projection aliases used for joins
 * @author Ogwara O. Rowland
 *
 */
public class QueryAlias {

	private String associationPath;
	private String alias;
	private int joinType;
	private Criterion withClause;


	public QueryAlias(String associationPath, String alias) {
		super();
		this.associationPath = associationPath;
		this.alias = alias;
	}


	public QueryAlias(String associationPath, String alias, int joinType) {
		super();
		this.associationPath = associationPath;
		this.alias = alias;
		this.joinType = joinType;
	}


	public QueryAlias(String associationPath, String alias, int joinType,
			Criterion withClause) {
		super();
		this.associationPath = associationPath;
		this.alias = alias;
		this.joinType = joinType;
		this.withClause = withClause;
	}


	public String getAssociationPath() {
		return associationPath;
	}
	public void setAssociationPath(String associationPath) {
		this.associationPath = associationPath;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public int getJoinType() {
		return joinType;
	}
	public void setJoinType(int joinType) {
		this.joinType = joinType;
	}
	public Criterion getWithClause() {
		return withClause;
	}
	public void setWithClause(Criterion withClause) {
		this.withClause = withClause;
	}

}
