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
package nw.orm.core.exception;

/**
 * Basic exception for nworm
 * @author Ogwara O. Rowland
 *
 */
public class NwormException extends Exception {

	private static final long serialVersionUID = 4162504831974227015L;

	public NwormException() {
		super("nw.orm Exception");
	}

	/**
	 * Constructor
	 * @param msg message to display
	 */
	public NwormException(String msg) {
		this(msg, null);
	}

	/**
	 *
	 * @param msg message to display
	 * @param cause exception
	 */
	public NwormException(String msg, Throwable cause){
		super(msg, cause);
	}

}
