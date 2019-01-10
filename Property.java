/*
 * A Property represents a Player-purchasable Space. It contains information
 * about the purchase, mortgage, and rent prices of that Property. It also
 * contains information about who the owner of the property is (if there is
 * one), which space the Property is at, and whether it is currently mortgaged 
 * or not. It has operations that are called once a player lands on it.
 */

package Monopoly;

public class Property implements Space {
	private int purchasePrice;
	private int mortgageValue;
	private int rentValue;
	private boolean isMortgaged;
	private Player owner;
	
	public Property(int purchaseCost, int mortgageVal, int rentVal) {
		this.purchasePrice = purchaseCost;
		this.mortgageValue = mortgageVal;
		this.rentValue = rentVal;
		this.isMortgaged = false;
		this.owner = null;
	}
	
	// Call this method when a player lands on this property.
	// Options:
	//		1) If not owned, offer up for purchase.
	//		2) Else if visitor is not the owner and not on mortgage, charge rent.
	//		3) If owned by visitor or mortgaged, do nothing.
	@Override
	public void actionOnPlayer(Player visitor) {
		if (this.getOwner() == null) visitor.purchaseUnownedProperty(this);
		else if (this.getOwner() != visitor && !this.getMortgageState()) this.chargeRent(visitor);
		// else do nothing if the visitor owns the property or the property is mortgaged
	}
	
	// Charges rent to the visitor passed in an argument and gives that money to the owner.
	// If the visitor doesn't have enough money, then just take whatever they have.
	public void chargeRent(Player visitor) {
		visitor.changeBalanceAndReturnPositiveness(-(this.getRentValue()));
		
		// If the visitor couldn't pay all the rent, then the owner can only
		// collect whatever the visitor has left.
		if (visitor.getBalance() < 0) 
			this.owner.changeBalanceAndReturnPositiveness(visitor.getBalance() + this.getRentValue());
		else this.owner.changeBalanceAndReturnPositiveness(this.getRentValue());
	}
	
	// Sets the isMortgaged field to the boolean parameter. Not accessible by
	// the client, only used in a Player's unmortgage and mortgage methods.
	void changeMortgageStateTo(boolean newMortgageState) {
		this.isMortgaged = newMortgageState;
	}
	
	/* Sets the Property owner to be the Player argument. The client is not
	 * allowed to access this method to prevent them from accidentally changing 
	 * the Property's owner without also changing the Player's Properties set. 
	 */
	void changeOwner(Player newOwner) {
		this.owner = newOwner;
	}
	
	// Getter for the Property's purchase price.
	public int getPurchasePrice() {
		return purchasePrice;
	}
	
	// Getter for the Property's mortgage value.
	public int getMortgageValue() {
		return mortgageValue;
	}
	
	// Getter for the Property's rent value.
	public int getRentValue() {
		return rentValue;
	}
	
	// Getter for whether the Property is mortgaged or not.
	public boolean getMortgageState() {
		return this.isMortgaged;
	}
	
	// Getter for the Property's owner.
	public Player getOwner() {
		return this.owner;
	}
	
	public String toString() {
		String str = "";
		str += "property:price:" + purchasePrice + ",mort:" + mortgageValue + ",rent:" + 
			   rentValue + ",isMort:" + isMortgaged + ",owner:" + owner;
		return str;
	}
}
