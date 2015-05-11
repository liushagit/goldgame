/**
 * SuperStarLogin
 * com.orange.superstar.login.exception
 * LoginException.java
 */
package com.orange.goldgame.exception;

import com.juice.orange.game.exception.JuiceException;

/**
 * @author shaojieque
 * 2013-5-3
 */
public class LoginException extends JuiceException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LoginException(String message) {
        super(message);
    }

    public LoginException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginException(Throwable cause) {
        super(cause);
    }
}
