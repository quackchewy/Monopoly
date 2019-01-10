/*
 *  This class provides information about a Player's account balance, Board 
 *  location, imprisonment status, whether the Player has lost the game, and 
 *  all of the Properties owned by the Player. It also handles operations 
 *  such as monetary transactions, Board movement, and dice rolls.
 */

package Monopoly;

import java.util.*;

public class Player {
	private int balance;
	private int location;
	private boolean isImprisoned;
	private Set<Property> properties;
	
	public Player() {
		this.balance = 1500;
		this.location = 0;
		this.isImprisoned = false;
		this.properties = new HashSet<>();
	}
	
	/* Prompts the user if they want to purchase a property. If so, then:
	 * 1) the Player's balance is adjusted accordingly, 
	 * 2) the Property is added to the owner's set of properties,
	 * 3) and the Property's ownership is changed.
	 * 
	 * Clients have no access to this method since the only time 
	 * a Property is purchased is when the Player land on it. The
	 * Property will handle it when its actionOnPlayer() method is
	 * called.
	 */
	public void purchaseUnownedProperty(Property prop) {
		if (prop.getOwner() != null) {
			System.out.println("ERROR: This property already has a owner.");
		} else {
			String buy = promptUserInput("Buy property: " + prop + "?");
			if (buy.toUpperCase().startsWith("Y")) {
				this.changeBalanceAndReturnPositiveness(-(prop.getPurchasePrice()));
				this.properties.add(prop);
				prop.changeOwner(this);
			}
		}
	}
	
	// Helper method to prompt the user to make a decision.
	private static String promptUserInput(String str) {
		System.out.println(str);
		Scanner reader = new Scanner(System.in);
		String returnStr = reader.next();
		return returnStr;
	}
	
	// Returns whether the Player owns the argument Property.
	public boolean hasProperty(Property prop) {
		return this.properties.contains(prop);
	}
	
	/* Changes the Player's balance by the argument amount. Returns
	 * whether the Player's balance is positive after the transaction.
	 */
	public boolean changeBalanceAndReturnPositiveness(int amount) {
		this.balance = this.balance + amount;
		if (this.balance <= 0) return false;
		return true;
	}
	
	// Takes in a property and mortgages it if it is not mortgaged. If it is 
	// already mortgaged, prints an error statement. 
	// Returns whether the Property was successfully mortgaged or not.
	public boolean mortgageProperty(Property prop) {
		if (prop.getMortgageState() == true) {
			System.out.println("ERROR: Property is already mortgaged.");
			return false;
		}
		
		this.changeBalanceAndReturnPositiveness(prop.getMortgageValue());
		prop.changeMortgageStateTo(true);
		return true;
	}
	
	// Takes in a property and unmortgages it if it is mortgaged. If it is not
	// already mortgaged, prints an error statement. 
	// Returns whether the Property was successfully unmortgaged or not.
	public boolean unmortgageProperty(Property prop) {
		if (prop.getMortgageState() == false) {
			System.out.println("ERROR: Property is not on a mortgage.");
			return false;
		}
		
		// Change Player's balance and change the Property's mortgage state.
		this.changeBalanceAndReturnPositiveness(-(prop.getMortgageValue() * 11 / 10));
		prop.changeMortgageStateTo(false);
		return true;
	}
	
	// This method moves the player by the number of steps specified and
	// activates the action of the Space it lands on.
	// It takes in the number of steps to move and the Board as
	// parameters so that it can determine whether it is passing Go and
	// so that it can call the actionOnPlayer() method of the Space
	// that it is landing on.
	public int moveLocation(int numSteps, Board board) {
		if (this.location + numSteps >= board.size()) this.changeBalanceAndReturnPositiveness(200);
		this.location = (this.getLocation() + numSteps) % board.size();
		board.getSpace(this.getLocation()).actionOnPlayer(this);
		return this.location;
	}
	
	// Takes in the location index of the jail and sends the player to 
	// that location while setting the imprisonment status to true.
	public void goToJail(int jailLocation) {
		this.isImprisoned = true;
		this.location = jailLocation;
	}
	
	// Frees the player from jail, putting them in the "Just Visiting" area.
	public void freeFromJail() {
		if (this.isInJail()) {
			int roll1 = rollDice();
			int roll2 = rollDice();
			
			// If the player doesn't roll a double then they pay a fee and are freed after.
			// If they do roll a double then they are automatically freed.
			if (roll1 != roll2) this.changeBalanceAndReturnPositiveness(-50);
						
			this.isImprisoned = false;
		}
		else System.out.println("The player was not in jail.");
	}
	
	// Returns the results of a dice roll, from 1 to 6.
	public static int rollDice() {
		Random rand = new Random();
		return rand.nextInt(6) + 1;
	}
	
	// Gets the results of two dice roll and returns the sum to move.
	public static int rollTwoDiceAndGetDistance() {
		int roll1 = rollDice();
		int roll2 = rollDice();
		return roll1 + roll2;
	}
		
	// Gets a copy of this Player's set of owned Properties.
	public Set<Property> getProperties() {
		Set<Property> props = new HashSet<>();
		for (Property prop : this.properties) props.add(prop);
		return props;
	}
	
	// Getter for the player's account balance.
	public int getBalance() {
		return this.balance;
	}
	
	// Getter for the player's current location.
	public int getLocation() {
		return this.location;
	}
		
	// Getter for isImprisoned field.
	public boolean isInJail() {
		return this.isImprisoned;
	}

	public String toString() {
		return "player:bal:" + this.balance + ",loc:" + this.location + ",isJailed:" + 
				this.isImprisoned;
	}
}
