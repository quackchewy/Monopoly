/* 
 * This interface represents a square on the Board.
 */

package Monopoly;

public interface Space {	
	// Each class implementing a Space must perform an action when 
	// a player lands on it.
	public void actionOnPlayer(Player visitor);	
}
