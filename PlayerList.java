/* 
 * This class keeps track of all of the Players playing and their 
 * win/lose status. A Player loses when they have no money and they
 * wins when all other Players lose.
 */

package Monopoly;

import java.util.*;

public class PlayerList {
	private int numPlayers;
	private List<Player> participants;
	private Player winner;
	
	// This constructor creates a game with the argument number of
	// players and a client-specified Board.
	public PlayerList(int numPlayers) {
		this.winner = null;
		this.numPlayers = numPlayers;
		this.participants = new ArrayList<>();
		for (int i = 0; i < numPlayers; i++) {
			participants.add(new Player());
		}
	}
	
	// Getter for the number of players.
	public int getNumPlayers() {
		return numPlayers;
	}
	
	// Takes a number as a parameter and returns the corresponding player.
	public Player getPlayer(int playerNum) {
		try {
			return this.participants.get(playerNum);
		} catch (Exception e) {
			System.out.println("This player is not in the current game.");
			return null;
		}
	}
	
	// This returns null until there is only one player remaining, 
	// in which case it will return the last player.
	public Player getWinner() {
		return this.winner;
	}
	
	// Sets the winner of the game to be the Player passed as an argument.
	public void setWinner(Player person) {
		this.winner = person;
	}
	
	// Removes a losing player from the participants list. This method
	// MUST be called at the end of every turn that a player's balance
	// goes zero or negative.
	public void removePlayer(Player person) {
		participants.remove(person);
		this.numPlayers--;
	}
	
	// Returns a copy of the participants List.
	public List<Player> getParticipants() {
		List<Player> players = new ArrayList<>();
		for (Player player : this.participants) {
			players.add(player);
		}
		return players;
	}
}
