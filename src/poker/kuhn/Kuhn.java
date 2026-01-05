package poker.kuhn;

import equipment.Card;
import equipment.Cards;
import equipment.Deck;

public class Kuhn {
	static class Hand extends Cards {
		public Hand(Card[] cards) {
			super(cards);
		}
	}

	public static void main(String[] arguments) {
	}

	static final Card[] cards = new Card[] { Card.jackOfClubs, Card.queenOfClubs, Card.kingOfClubs, };
	static final Deck deck = new Deck(cards);
}
