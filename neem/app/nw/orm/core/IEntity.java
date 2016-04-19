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
package nw.orm.core;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * A base implementation of an Entity that uses a sequence based primary key.
 *
 * @author Ogwara O. Rowland
 */
@MappedSuperclass
public abstract class IEntity extends NwormEntity<Long> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5965442215210696967L;

	/** Primary Key. */
	@Id
	@GeneratedValue
	@Column(name = "PK", nullable = false, insertable = true, updatable = false)
	private Long pk;

	/* (non-Javadoc)
	 * @see nw.orm.core.NwormEntity#getPk()
	 */
	@Override
	public Long getPk() {
		return pk;
	}

	/**
	 * Sets the primary Key.
	 *
	 * @param pk the new primary Key
	 */
	public void setPk(Long pk) {
		this.pk = pk;
	}

}
