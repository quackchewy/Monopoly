/* This class represents a Go To Jail Space. This Space sends 
 * a Player to Jail when they land on it.
 */

package Monopoly;

public class GoToJail implements Space {
	int jailLocation;
	
	// Takes in an int representing the index of the GoToJail's
	// corresponding Jail. This is to ensure that the GoToJail 
	//knows where to send the Player.
	public GoToJail(int jailLocation) {
		this.jailLocation = jailLocation;
	}
	
	@Override
	public void actionOnPlayer(Player person) {
		person.goToJail(this.jailLocation);
	}
	
	public String toString() {
		return "GoToJail:" + this.jailLocation;
	}
}
