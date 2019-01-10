// The Go, Jail, and Free Parking Spaces are both NoEventSpaces since they 
// do nothing when someone lands on them.

package Monopoly;

public class NoEventSpace implements Space {
	private int spaceType;
	
	/* Takes in an int representing the type of NoEventSpace.
	 * 0 represents Go, 1 represents Jail, and any other int 
	 * represents Free Parking.
	 */
	public NoEventSpace(int type) {
		this.spaceType = type;
	}
	
	// NoEventSpaces do nothing when a player lands on it. The collection 
	// of money for passing Go is handled by the Player when they move.
	@Override
	public void actionOnPlayer(Player person) {
		
	}
	
	public String toString() {
		if (this.spaceType == 0) return "Go";
		else if (this.spaceType == 1) return "Jail";
		else return "FreeParking";
	}
}
