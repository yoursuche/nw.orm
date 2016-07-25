package nw.orm.core;

public abstract class NwormEntity<T> extends Entity implements Comparable<NwormEntity<T>>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4136501870517506844L;

	/**
	 * Retrieves the primary key for the entry.
	 *
	 * @return the pk
	 */
	public abstract T getPk();
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals
	 */
	@Override
	@SuppressWarnings("unchecked")
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof Entity)) {
			return false;
		}
		final NwormEntity<T> that = (NwormEntity<T>) object;
		if (this.getPk() == null || that.getPk() == null
				|| !this.getPk().equals(that.getPk())) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashcode
	 */
	@Override
	public int hashCode() {
		int hashCode = 0;
		hashCode = 29 * hashCode + (getPk() == null ? 0 : getPk().hashCode());
		return hashCode;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(NwormEntity<T> o) {
		int cmp = 0;
		if (this.getPk() != null && (this.getPk() == o.getPk() || this.getPk().equals(o.getPk()))) {
			cmp = 0;
		}else
			cmp = -1;
		return cmp;
	}
	
	

}
