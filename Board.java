/*
 * This class stores information about and performs actions on the sequence of Spaces 
 * on the Monopoly board. The size of a Monopoly board cannot be changed once it is
 * created, however any Space can be replaced by another one. The Board class provides
 * information about any Space on it.
 */

package Monopoly;

public class Board {
	private static final int NUM_SPACES = 40; 
	private Space[] playfield;
	
	// No argument constructor creates a standard-sized Monopoly board with a 
	// NoEvent space at indices 0 and 20, a Jail at index 10, and a GoToJail at index 30.
	public Board() {
		this(NUM_SPACES);
	}
	
	// This constructor creates a custom-sized board with Go, Jail, Free Parking, and 
	// and GoToJail in their respective corners.
	public Board(int numSpaces) {
		this.playfield = createSpaceArray(numSpaces);
	}
	
	// Creates a board from a pre-made array of Spaces.
	// This allows client most flexibility in building their own board,
	// with custom Spaces and number of Spaces.
	public Board(Space[] spaces) {
		this.playfield = spaces;
	}
	
	// This method takes a number of Spaces and returns a Space array of that size.
	// The Go, Jail, Free Parking, and Go To Jail Spaces are set at each corner of 
	// the board, in that order.
	private static Space[] createSpaceArray(int numSpaces) {
		Space[] spaces = new Space[numSpaces];
		
		for (int i = 0; i < numSpaces; i++) {
			// Go is the very first Space.
			if (i == 0) spaces[i] = new NoEventSpace(0);
			
			// Jail is at 1/4th point on the board.
			else if (i == numSpaces / 4) spaces[i] = new NoEventSpace(1);
			
			// Free Parking is at the halfway point on the board.
			else if (i == numSpaces / 2) spaces[i] = new NoEventSpace(2);
			
			// This GoToJail sends its Players to the Jail at the 1/4th point.
			else if (i == 3 * numSpaces / 4) spaces[i] = new GoToJail(numSpaces / 4);
			
			// Every non-corner space is a property. We initialize each property
			// with a mortgage and rent value proportional to its purchase value.
			else spaces[i] = new Property(
					50 + 10 * i, 
					(50 + 10 * i) / 2, 
					(50 + 10 * i) / 10);
		}	
		return spaces;
	}
	
	// Returns the Space at the index passed as an argument.
	public Space getSpace(int position) {
		try {
			return playfield[position];
		} catch (Exception e) {
			System.out.println("ERROR: This position is not on the board.");
			return null;
		}
	}
	
	// Returns the Board index of the Space passed as an argument. If the Space
	// is not on the board, returns -1.
	public int getSpaceLocation(Space square) {
		for (int i = 0; i < playfield.length; i++) {
			if (playfield[i] == square) return i;
		}
		return -1;
	}
	
	// This method changes any Space on the Board to a customized one. 
	// It takes the replacement Space and the index of the Space being replaced
	// as inputs.
	public void setSpace(int position, Space square) {
		if (position < 0 && position >= this.playfield.length) throw new IndexOutOfBoundsException();
		try {
			playfield[position] = square;
		} catch (Exception e) {
			System.out.println("ERROR: This position is not on the board.");
		}
	}
	
	// Returns the number of Spaces in the Board.
	public int size() {
		return playfield.length;
	}
	
	@Override
	public String toString() {
		String str = "{";
		for (int i = 0; i < playfield.length - 1; i++) {
			str += i + ":" + playfield[i] + ", ";
		}
		str += playfield.length - 1 + ":" + playfield[playfield.length - 1] + "}";
		return str;
	}
}