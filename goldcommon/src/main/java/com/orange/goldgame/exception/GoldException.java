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
public class GoldException extends JuiceException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//
	public GoldException(String message) {
        super(message);
    }

    public GoldException(String message, Throwable cause) {
        super(message, cause);
    }

    public GoldException(Throwable cause) {
        super(cause);
    }
}
