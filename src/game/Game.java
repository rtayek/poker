package game;

import poker.holdem.SpecificHoldemHand;
import equipment.Card;
import equipment.Deck;

public class Game {
	public enum Type {
		holdem, draw, lowball, Kuhn
	}

	public enum Limits {
		limit, potLimit, noLimit
	}

	Game() {
		table = new Table(Type.holdem, new Ante(Blinds.oneTwo, 0), Limits.noLimit, 10);
		for (int i = 0; i < table.seats; i++)
			table.players[i] = new Player();
	}

	void deal() {
		for (int i = 0; i < table.seats; i++) {
			int dealTo = 0;
			if (table.players[dealTo] != null) {
				Card card1 = deck.draw();
				Card card2 = deck.draw();
				SpecificHoldemHand hand = card1.rank().ordinal() >= card2.rank().ordinal()
						? new SpecificHoldemHand(card1, card2)
						: new SpecificHoldemHand(card2, card1);
				table.players[dealTo].hand = hand;
				System.out.println("dealt a " + hand + " (" + hand.holdemHand + ") to player " + dealTo);
			} else
				System.out.println("dealing out player " + dealTo);
		}
	}

	void run() {
		System.out.println("running");
		System.out.println(this);
		table.moveButton();
		if (table.players() >= 2) {
			deck.shuffle();
			table.pot = 0;
			table.ante();
			// put in the blinds
			deal();
			// add more here
			table.pot = 0;
		}
	}

	@Override
	public String toString() {
		String s = table + "\n";
		for (Player player : table.players)
			s += player + "\n";
		return s;
	}

	public static void main(String[] args) {
		new Game().run();
	}

	final Table table;
	final Deck deck = new Deck();
}
