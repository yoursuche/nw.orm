/* 
 * Copyright 2013 - 2015, Neemworks Nigeria <nw.orm@nimworks.com>
 Permission to use, copy, modify, and distribute this software for any
 purpose with or without fee is hereby granted, provided that the above
 copyright notice and this permission notice appear in all copies.

 THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
 ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
 OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */
package nw.orm.core.query;

import org.hibernate.criterion.Criterion;

// TODO: Auto-generated Javadoc
/**
 * Short hand for building projection aliases used for joins.
 *
 * @author Ogwara O. Rowland
 */
public class QueryAlias {

	/** The association path. */
	private String associationPath;

	/** The alias. */
	private String alias;

	/** The join type. */
	private int joinType = -1;

	/** The with clause. */
	private Criterion withClause;


	/**
	 * Instantiates a new query alias.
	 *
	 * @param associationPath the association path
	 * @param alias the alias
	 */
	public QueryAlias(String associationPath, String alias) {
		super();
		this.associationPath = associationPath;
		this.alias = alias;
	}


	/**
	 * Instantiates a new query alias.
	 *
	 * @param associationPath the association path
	 * @param alias the alias
	 * @param joinType the join type
	 */
	public QueryAlias(String associationPath, String alias, int joinType) {
		super();
		this.associationPath = associationPath;
		this.alias = alias;
		this.joinType = joinType;
	}


	/**
	 * Instantiates a new query alias.
	 *
	 * @param associationPath the association path
	 * @param alias the alias
	 * @param joinType the join type
	 * @param withClause the with clause
	 */
	public QueryAlias(String associationPath, String alias, int joinType,
			Criterion withClause) {
		super();
		this.associationPath = associationPath;
		this.alias = alias;
		this.joinType = joinType;
		this.withClause = withClause;
	}


	/**
	 * Gets the association path.
	 *
	 * @return the association path
	 */
	public String getAssociationPath() {
		return associationPath;
	}

	/**
	 * Sets the association path.
	 *
	 * @param associationPath the new association path
	 */
	public void setAssociationPath(String associationPath) {
		this.associationPath = associationPath;
	}

	/**
	 * Gets the alias.
	 *
	 * @return the alias
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * Sets the alias.
	 *
	 * @param alias the new alias
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}

	/**
	 * Gets the join type.
	 *
	 * @return the join type
	 */
	public int getJoinType() {
		return joinType;
	}

	/**
	 * Sets the join type.
	 *
	 * @param joinType the new join type
	 */
	public void setJoinType(int joinType) {
		this.joinType = joinType;
	}

	/**
	 * Gets the with clause.
	 *
	 * @return the with clause
	 */
	public Criterion getWithClause() {
		return withClause;
	}

	/**
	 * Sets the with clause.
	 *
	 * @param withClause the new with clause
	 */
	public void setWithClause(Criterion withClause) {
		this.withClause = withClause;
	}

}
