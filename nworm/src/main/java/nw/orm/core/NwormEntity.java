package nw.orm.core;

public abstract class NwormEntity<T extends Comparable<T>> extends Entity implements Comparable<NwormEntity<T>>{
	
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
		
		if(this.getClass() != object.getClass()) {
			return false;
		}
		final NwormEntity<T> that = (NwormEntity<T>) object;
		if (this.getPk() != null && that.getPk() != null
				&& this.getPk().equals(that.getPk())) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashcode
	 */
	@Override
	public int hashCode() {
		int hashCode = getTableName().hashCode();
		hashCode = 29 * hashCode + (getPk() == null ? 0 : getPk().hashCode());
		return hashCode;
	}
	
	@Override
	public int compareTo(NwormEntity<T> o) {
		
		if(this.getPk() == null) {
			return -1;
		}
		return this.getPk().compareTo(o.getPk());
	}
	

}
