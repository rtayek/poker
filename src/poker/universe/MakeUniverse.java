package poker.universe;
import static poker.Constants.*;
import java.io.*;
import equipment.Rank;
abstract public class MakeUniverse
    { // this may break if we permute ranks
	public static final int aceLow=Rank.aceLow.ordinal();
	public static final int duece=Rank.deuce.ordinal();
	public static final int trey=Rank.trey.ordinal();
	public static final int four=Rank.four.ordinal();
	public static final int five=Rank.five.ordinal();
	public static final int six=Rank.six.ordinal();
	public static final int ten=Rank.ten.ordinal();
	public static final int jack=Rank.jack.ordinal();
	public static final int queen=Rank.queen.ordinal();
	public static final int king=Rank.king.ordinal();
	public static final int ace=Rank.ace.ordinal();
	public static PrintStream out=System.out;
	public static void main(String[] arguments) {
		System.out.println(naturalHighHands);
	}
}