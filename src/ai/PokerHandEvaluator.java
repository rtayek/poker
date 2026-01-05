package ai;
import java.io.*;
import java.util.*;

public class PokerHandEvaluator {

    // Enum for ranks and suits
    enum Rank {
        TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE;
    }

    enum Suit {
        HEARTS, DIAMONDS, CLUBS, SPADES;
    }

    // Card class
    static class Card {
        Rank rank;
        Suit suit;

        public Card(String card) {
            String rankStr = card.substring(0, card.length() - 1).toUpperCase();
            String suitStr = card.substring(card.length() - 1).toUpperCase();

            this.rank = Rank.valueOf(rankStr);
            this.suit = Suit.valueOf(suitStr);
        }
    }

    public static void main(String[] args) {
        List<Card> hand = new ArrayList<>();

        // Read cards from file or input (for simplicity, using a predefined list here)
        try {
            BufferedReader reader = new BufferedReader(new FileReader("cards.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                hand.add(new Card(line.trim()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Now determine the hand type
        System.out.println("Poker Hand: " + evaluateHand(hand));
    }

    // Evaluate the hand type
    public static String evaluateHand(List<Card> hand) {
        if (hand.size() != 5) {
            return "Invalid hand, must have 5 cards.";
        }

        // Sort the cards by rank
        Collections.sort(hand, Comparator.comparing(card -> card.rank.ordinal()));

        boolean isFlush = isFlush(hand);
        boolean isStraight = isStraight(hand);

        if (isFlush && isStraight) {
            if (isRoyalFlush(hand)) {
                return "Royal Flush";
            } else {
                return "Straight Flush";
            }
        } else if (isFourOfAKind(hand)) {
            return "Four of a Kind";
        } else if (isFullHouse(hand)) {
            return "Full House";
        } else if (isFlush) {
            return "Flush";
        } else if (isStraight) {
            return "Straight";
        } else if (isThreeOfAKind(hand)) {
            return "Three of a Kind";
        } else if (isTwoPair(hand)) {
            return "Two Pair";
        } else if (isOnePair(hand)) {
            return "One Pair";
        } else {
            return "High Card";
        }
    }

    // Check if it's a flush
    public static boolean isFlush(List<Card> hand) {
        Suit suit = hand.get(0).suit;
        for (Card card : hand) {
            if (card.suit != suit) {
                return false;
            }
        }
        return true;
    }

    // Check if it's a straight
    public static boolean isStraight(List<Card> hand) {
        for (int i = 1; i < hand.size(); i++) {
            if (hand.get(i).rank.ordinal() != hand.get(i - 1).rank.ordinal() + 1) {
                return false;
            }
        }
        return true;
    }

    // Check for Royal Flush (Ace high straight flush)
    public static boolean isRoyalFlush(List<Card> hand) {
        return hand.get(0).rank == Rank.TEN && hand.get(1).rank == Rank.JACK &&
                hand.get(2).rank == Rank.QUEEN && hand.get(3).rank == Rank.KING &&
                hand.get(4).rank == Rank.ACE;
    }

    // Check for Four of a Kind
    public static boolean isFourOfAKind(List<Card> hand) {
        return hasNOfAKind(hand, 4);
    }

    // Check for Full House
    public static boolean isFullHouse(List<Card> hand) {
        return hasNOfAKind(hand, 3) && hasNOfAKind(hand, 2);
    }

    // Check for Three of a Kind
    public static boolean isThreeOfAKind(List<Card> hand) {
        return hasNOfAKind(hand, 3);
    }

    // Check for Two Pair
    public static boolean isTwoPair(List<Card> hand) {
        return countPairs(hand) == 2;
    }

    // Check for One Pair
    public static boolean isOnePair(List<Card> hand) {
        return countPairs(hand) == 1;
    }

    // Helper method to check for n of a kind
    public static boolean hasNOfAKind(List<Card> hand, int n) {
        Map<Rank, Integer> rankCount = new HashMap<>();
        for (Card card : hand) {
            rankCount.put(card.rank, rankCount.getOrDefault(card.rank, 0) + 1);
        }

        for (int count : rankCount.values()) {
            if (count == n) {
                return true;
            }
        }
        return false;
    }

    // Helper method to count pairs
    public static int countPairs(List<Card> hand) {
        Map<Rank, Integer> rankCount = new HashMap<>();
        for (Card card : hand) {
            rankCount.put(card.rank, rankCount.getOrDefault(card.rank, 0) + 1);
        }

        int pairCount = 0;
        for (int count : rankCount.values()) {
            if (count == 2) {
                pairCount++;
            }
        }
        return pairCount;
    }
}
