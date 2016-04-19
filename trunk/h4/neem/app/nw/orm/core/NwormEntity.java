package nw.orm.core;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;
import java.lang.reflect.Modifier;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.persistence.Version;

import nw.orm.core.annotations.Developer;

/**
 * NwormEntity is a shorthand for creating entities. It comes with extra properties
 *
 * This class should be extended for customized base entities
 *
 * @author Ogwara O. Rowland
 * @param <T> Datatype to represent the primary key
 */
@Developer(name = "Ogwara O. Rowland", date = "")
@MappedSuperclass
public abstract class NwormEntity<T> implements Serializable, Comparable<NwormEntity<T>>{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5965442215210696967L;

	/** Specified whether the entry is active active. */
	@Column(name = "ACTIVE", nullable = false, insertable = true, updatable = true)
	private boolean active = true;

	/** Denotes whether the entry has been deleted. Use for the purposes of soft delete.
	 * meaning the entry is not actually deleted, but just marked as an item to be ignored
	 */
	@Column(name = "DELETED", nullable = false, insertable = true, updatable = true)
	private boolean deleted;

	/** Denotes the date of creation. */
	@Column(name = "CREATE_DATE", nullable = false, insertable = true, updatable = false)
	private Date createDate = new Date();

	/** Denotes the last modified date. Updates automatically with successful entry update */
	@Version
	@Column(name = "LAST_MODIFIED", nullable = false, insertable = true, updatable = true)
	private Date lastModified;

	/**
	 * a boolean variable that can be used to activate and deactivate entries.
	 *
	 * @return true if the entry is active, false if it is not
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * Used to activate or deactivate an entry.
	 *
	 * @param active the new active
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * Determines whether the entry should be soft deleted. As in flagged as deleted and ignored by subsequent queries
	 *
	 * @return true, if is deleted
	 */
	public boolean isDeleted() {
		return deleted;
	}

	/**
	 * Deletes or undeletes an entry.
	 *
	 * @param deleted the new deleted
	 */
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	/**
	 * Gets the date of creation.
	 *
	 * @return the date of creation
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * retrieve the last date the entry was modified. This field is auto populated
	 *
	 * @return the last modified
	 */
	public Date getLastModified() {
		return lastModified;
	}

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
		if (!(object instanceof NwormEntity)) {
			return false;
		}
		final NwormEntity<T> that = (NwormEntity<T>) object;
		if (this.getPk() == null || that.getPk() == null
				|| !this.getPk().equals(that.getPk())) {
			return false;
		}
		return true;
	}

	/**
	 * Retrieves the priary key for the entry.
	 *
	 * @return the pk
	 */
	public abstract T getPk();

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

	/**
	 * deletes this entry.
	 */
	public void delete(){
		this.deleted = true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		String newLine = System.getProperty("line.separator");

		result.append(getTableName());
		result.append(" {");
		result.append(newLine);

		// determine fields declared in this class only (no fields of
		// superclass)
		Field[] fields = this.getClass().getDeclaredFields();
		// print field names paired with their values
		for (Field field : fields) {
			if(Modifier.isStatic(field.getModifiers()))
				continue;
			result.append("  ");
			try {
				result.append(field.getName());
				result.append(": ");
				// requires access to private field:Strin

				String name = field.getName();
				String prefix = "get";
				if(field.getType().isAssignableFrom(Boolean.class)){
					prefix = "is";
				}
				name = name.substring(0, 1).toUpperCase() + name.substring(1);

				result.append(this.getClass().getMethod(prefix + name).invoke(this));
			} catch (Exception ex) {
				System.out.println(ex);
			}
			result.append(newLine);
		}
		result.append("}");

		return result.toString();
	}

	/**
	 * Gets the table name.
	 *
	 * @return the table name
	 */
	public String getTableName() {
		String tableName = "";
		try {
			Table table = getClass().getAnnotation(Table.class);
			tableName = table.name();
		} catch (Exception e) {
			e.printStackTrace();
			tableName = getClass().getSimpleName();
		}

		return tableName;
	}

}
