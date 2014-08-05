package nw.orm.base;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;
import java.lang.reflect.Modifier;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.persistence.Version;

@MappedSuperclass
public abstract class Entity<T> implements Serializable, Comparable<Entity<T>>{
	
	private static final long serialVersionUID = -5965442215210696967L;
	
	@Column(name = "ACTIVE", nullable = false, insertable = true, updatable = true)
	private boolean active = true;

	@Column(name = "DELETED", nullable = false, insertable = true, updatable = true)
	private boolean deleted;

	@Column(name = "CREATE_DATE", nullable = false, insertable = true, updatable = false)
	private Date createDate = new Date();

	@Version
	@Column(name = "LAST_MODIFIED", nullable = false, insertable = true, updatable = true)
	private Date lastModified = new Date();

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getLastModified() {
		return lastModified;
	}

	/**
	 * Indicates if the argument is of the same type and all values are equal.
	 *
	 * @param object
	 *            The target object to compare with
	 * @return boolean True if both objects a 'equal'
	 */
	@SuppressWarnings("unchecked")
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof Entity)) {
			return false;
		}
		final Entity<T> that = (Entity<T>) object;
		if (this.getPk() == null || that.getPk() == null
				|| !this.getPk().equals(that.getPk())) {
			return false;
		}
		return true;
	}

	public abstract T getPk();

	/**
	 * Returns a hash code value for the object
	 *
	 * @return int The hash code value
	 */
	public int hashCode() {
		int hashCode = 0;
		hashCode = 29 * hashCode + (getPk() == null ? 0 : getPk().hashCode());
		return hashCode;
	}

	@Override
	public int compareTo(Entity<T> o) {
		int cmp = 0;
		if (this.getPk() != null && (this.getPk() == o.getPk() || this.getPk().equals(o.getPk()))) {
			cmp = 0;
		}else
			cmp = -1;
		return cmp;
	}
	
	public void markDeleted(){
		
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		String newLine = System.getProperty("line.separator");

		result.append(this.getClass().getName());
		result.append(" Object {");
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

	public String getTableName() {
		Table table = getClass().getAnnotation(Table.class);
		String tableName = table.name();
		return tableName;
	}

}
