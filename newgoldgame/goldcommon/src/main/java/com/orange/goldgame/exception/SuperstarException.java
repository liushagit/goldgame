/**
 * SuperStarCommon
 * com.orange.superstar.exception
 * SuperstarException.java
 */
package com.orange.goldgame.exception;

import com.juice.orange.game.exception.JuiceException;

/**
 * @author shaojieque
 * 2013-5-6
 */
public class SuperstarException extends JuiceException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//
	public SuperstarException(String message) {
        super(message);
    }

    public SuperstarException(String message, Throwable cause) {
        super(message, cause);
    }

    public SuperstarException(Throwable cause) {
        super(cause);
    }
}
