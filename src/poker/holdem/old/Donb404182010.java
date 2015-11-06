package poker.holdem.old;
import java.util.*;
import java.text.*;
import equipment.*;
import lookup.OldLookup;
import poker.*;
public class Donb404182010 {
	static class WTL {
		long wins,ties,losses;
	}
	static class Frequency {
		final WTL flop=new WTL();
		final WTL fourth=new WTL();
		final WTL fifth=new WTL();
		final WTL board=new WTL();
	};
	class Player {
		String holeCards() {
			return ""+holeRanks[0].toCharacter()+holeSuits[0].toCharacter()+holeRanks[1].toCharacter()+holeSuits[1].toCharacter();
		}
		void copyFlopHand() {
			for(int i=0;i<5;i++) {
				ranks[i]=flopRanks[i];
				suits[i]=flopSuits[i];
				// if(rank[r]==Rank.aceLow) rank[r]=Rank.aceHigh;
			}
		}
		String holeCards2() {
			return Card.instance(holeRanks[0],holeSuits[0]).toCharacters()+Card.instance(holeRanks[1],holeSuits[1]).toCharacters();
		}
		String info(final WTL wtl) {
			final double total=wtl.wins+wtl.ties+wtl.losses;
			return wtl.wins+" wins,"+wtl.ties+" ties,"+wtl.losses+" losses"+' '+decimalFormat.format(wtl.wins/total)+' '+decimalFormat.format(wtl.ties/total)
					+' '+decimalFormat.format(wtl.losses/total);
		}
		String flopInfo() {
			return holeCards2()+' '+info(total.flop)+" (flop)";
		}
		String fourthInfo() {
			return holeCards2()+' '+info(total.fourth)+" (fourth)";
		}
		String fifthInfo() {
			return holeCards2()+' '+info(total.fifth)+" (fifth)";
		}
		String boardInfo() {
			return holeCards()+' '+info(total.board)+" (board)";
		}
		int id;
		final Rank[] holeRanks=new Rank[2];
		final Suit[] holeSuits=new Suit[2];
		final Rank[] flopRanks=new Rank[5];
		final Suit[] flopSuits=new Suit[5];
		short flopHand,bestFourthHand,bestFifthHand,boardHand;
		final Frequency total=new Frequency();
		final short type[]=new short[PokerHand.HighType.values().length];
		final Frequency[] individual=new Frequency[Donb404182010.total];
		{
			for(int i=0;i<individual.length;i++)
				individual[i]=new Frequency();
		}
	}
	void remove(Rank r,Suit s) {
		if(deck[r.ordinal()][s.ordinal()]) deck[r.ordinal()][s.ordinal()]=false;
		else {
			throw new RuntimeException(Card.instance(r,s)+" is already removed");
		}
	}
	void insert(Rank r,Suit s) {
		if(!deck[r.ordinal()][s.ordinal()]) deck[r.ordinal()][s.ordinal()]=true;
		else {
			throw new RuntimeException(Card.instance(r,s)+" is already inserted");
		}
	}
	void init(String[] argument) {
		verbose=werbose=xerbose=yerbose=zerbose=aerbose=false;
		board=false;
		fourth=fifth=false;
		report_frequency=100000; // should never report
		distribution=false;
		highest_card_in_deck=Rank.king;
		System.out.println(Rank.values().length);
		System.out.println(Suit.values().length);
		for(int i=0;i<Rank.values().length;i++)
			for(int j=0;j<Suit.values().length;j++)
				deck[i][j]=true;
		output_name="";
		for(int i=0;i<argument.length;i++)
			switch (argument[i].charAt(0)) {
				case '-':
					switch (argument[i].charAt(1)) {
						case 'v':
							verbose=true;
							break;
						case 'w':
							werbose=true;
							break;
						case 'x':
							xerbose=true;
							break;
						case 'y':
							yerbose=true;
							break;
						case 'z':
							zerbose=true;
							break;
						case 'b':
							board=true;
							break;
						case '4':
							fourth=true;
							break;
						case '5':
							fifth=true;
							break;
						case '0':
							report_frequency=1;
							break;
						case '1':
							report_frequency=10;
							break;
						case '2':
							report_frequency=100;
							break;
						case 's':
							highest_card_in_deck=Rank.six;
							break;
						case 't':
							highest_card_in_deck=Rank.five;
							break;
						case 'd':
							distribution=true;
							break;
						default:
							throw new RuntimeException(argument[i]+" is an illeagal option");
					}
					break;
				default:
					final Rank rr=Rank.fromCharacter(argument[i].charAt(0));
					if(rr==null) throw new RuntimeException(argument[i]+" is an illegal argument");
					output_name+=argument[i]+'_';
					final Player p=new Player();
					players.add(p);
					p.id=players.size();
					p.holeRanks[0]=Rank.fromCharacter(argument[i].charAt(0));
					if(p.holeRanks[0]==Rank.ace) p.holeRanks[0]=Rank.aceLow;
					p.holeSuits[0]=Suit.fromCharacter(argument[i].charAt(1));
					remove(p.holeRanks[0],p.holeSuits[0]);
					p.holeRanks[1]=Rank.fromCharacter(argument[i].charAt(2));
					if(p.holeRanks[1]==Rank.ace) p.holeRanks[1]=Rank.aceLow;
					p.holeSuits[1]=Suit.fromCharacter(argument[i].charAt(3));
					remove(p.holeRanks[1],p.holeSuits[1]);
			}
		if(fifth) fourth=true;
		number_of_cards=4*(highest_card_in_deck.ordinal()); // check this!
		System.out.println("number_of_cards="+number_of_cards);
		predicted_flops=Cards.c(number_of_cards-2*players.size(),3);
		predicted_fourths=Cards.c(number_of_cards-2*players.size(),4);
		predicted_fifths=Cards.c(number_of_cards-2*players.size(),5);
		for(Player p:players)
			System.out.println("player "+' '+p.holeCards());
		if(players.size()>0) System.out.println(", players="+players.size());
	}
	void pfifth(final Rank r4,final Suit s4) {
		for(Rank r5:EnumSet.range(r4,highest_card_in_deck))
			for(Suit s5:EnumSet.range(Suit.clubs,Suit.spades))
				if(deck[r5.ordinal()][s5.ordinal()]&&(s4.ordinal()<s5.ordinal()||r4.ordinal()<r5.ordinal())) {
					fifths++;
					best_hand=total;
					anybody_improved=false;
					short n;
					for(Player p:players) {
						short bestFifthHand=p.bestFourthHand;
						p.copyFlopHand();
						ranks[0]=r5;
						suits[0]=s5;
						n=(short)OldLookup.lookup(ranks,Suit.areSuited(suits));
						if(n<bestFifthHand) bestFifthHand=n;
						p.copyFlopHand();
						ranks[1]=r5;
						suits[1]=s5;
						n=(short)OldLookup.lookup(ranks,Suit.areSuited(suits));
						if(n<bestFifthHand) bestFifthHand=n;
						p.copyFlopHand();
						ranks[2]=r5;
						suits[2]=s5;
						n=(short)OldLookup.lookup(ranks,Suit.areSuited(suits));
						if(n<bestFifthHand) bestFifthHand=n;
						p.copyFlopHand();
						ranks[3]=r5;
						suits[3]=s5;
						n=(short)OldLookup.lookup(ranks,Suit.areSuited(suits));
						if(n<bestFifthHand) bestFifthHand=n;
						p.copyFlopHand();
						ranks[4]=r5;
						suits[4]=s5;
						n=(short)OldLookup.lookup(ranks,Suit.areSuited(suits));
						if(n<bestFifthHand) bestFifthHand=n;
						p.copyFlopHand();
						ranks[0]=r4;
						suits[0]=s4;
						ranks[1]=r5;
						suits[1]=s5;
						n=(short)OldLookup.lookup(ranks,Suit.areSuited(suits));
						if(n<bestFifthHand) bestFifthHand=n;
						p.boardHand=n; /* playing the board */
						p.copyFlopHand();
						ranks[0]=r4;
						suits[0]=s4;
						ranks[2]=r5;
						suits[2]=s5;
						n=(short)OldLookup.lookup(ranks,Suit.areSuited(suits));
						if(n<bestFifthHand) bestFifthHand=n;
						p.copyFlopHand();
						ranks[0]=r4;
						suits[0]=s4;
						ranks[3]=r5;
						suits[3]=s5;
						n=(short)OldLookup.lookup(ranks,Suit.areSuited(suits));
						if(n<bestFifthHand) bestFifthHand=n;
						p.copyFlopHand();
						ranks[0]=r4;
						suits[0]=s4;
						ranks[4]=r5;
						suits[4]=s5;
						n=(short)OldLookup.lookup(ranks,Suit.areSuited(suits));
						if(n<bestFifthHand) bestFifthHand=n;
						p.copyFlopHand();
						ranks[1]=r4;
						suits[1]=s4;
						ranks[2]=r5;
						suits[2]=s5;
						n=(short)OldLookup.lookup(ranks,Suit.areSuited(suits));
						if(n<bestFifthHand) bestFifthHand=n;
						p.copyFlopHand();
						ranks[1]=r4;
						suits[1]=s4;
						ranks[3]=r5;
						suits[3]=s5;
						n=(short)OldLookup.lookup(ranks,Suit.areSuited(suits));
						if(n<bestFifthHand) bestFifthHand=n;
						p.copyFlopHand();
						ranks[1]=r4;
						suits[1]=s4;
						ranks[4]=r5;
						suits[4]=s5;
						n=(short)OldLookup.lookup(ranks,Suit.areSuited(suits));
						if(n<bestFifthHand) bestFifthHand=n;
						p.copyFlopHand();
						ranks[2]=r4;
						suits[2]=s4;
						ranks[3]=r5;
						suits[3]=s5;
						n=(short)OldLookup.lookup(ranks,Suit.areSuited(suits));
						if(n<bestFifthHand) bestFifthHand=n;
						p.copyFlopHand();
						ranks[2]=r4;
						suits[2]=s4;
						ranks[4]=r5;
						suits[4]=s5;
						n=(short)OldLookup.lookup(ranks,Suit.areSuited(suits));
						if(n<bestFifthHand) bestFifthHand=n;
						p.copyFlopHand();
						ranks[3]=r4;
						suits[3]=s4;
						ranks[4]=r5;
						suits[4]=s5;
						if(ranks[0]==Rank.aceLow&&ranks[1]==Rank.king&&ranks[2]==Rank.aceLow&&ranks[3]==Rank.deuce&&ranks[4]==Rank.trey) {
							System.out.println("fifth: "+Arrays.asList(ranks)+" "+fifths);
						}
						if(fifths==52) {
							System.out.println("fifths="+fifths);
						}
						n=(short)OldLookup.lookup(ranks,Suit.areSuited(suits));
						if(fifths==52) {
							Card[] cards=OldLookup.inverseLookup(n);
							System.out.println(fifths+" "+Arrays.asList(cards));
						}
						if(n<bestFifthHand) bestFifthHand=n;
						if(best_hand>bestFifthHand) best_hand=bestFifthHand;
						if(bestFifthHand<p.bestFourthHand) anybody_improved=true;
						p.bestFifthHand=bestFifthHand;
					}
					ties=0;
					for(Player p:players)
						if(p.bestFifthHand<best_hand) throw new RuntimeException("p.b5h<best_hand");
						else if(p.bestFifthHand==best_hand) ties++;
					/* hack for board */
					if(board) {
						aerbose=false;
						for(Player p:players)
							if(p.bestFifthHand==p.boardHand) aerbose=true;
					}
					/* end of hack for board */
					if(xerbose&&anybody_improved||zerbose||aerbose) for(Player p:players) {
						if(p==players.get(0)) System.out.print(r4.toCharacter()+s4.toCharacter()+' '+r5.toCharacter()+s5.toCharacter());
						if(p.bestFifthHand<p.bestFourthHand) {
							any_fifth_improvement=true;
							final Card[] c=OldLookup.inverseLookup(p.bestFifthHand); // slow
						System.out.print(Rank.toString(Cards.ranks(c))+' '+PokerHand.HighType.type(p.bestFifthHand));
					} else System.out.print("        ");
				}
					for(Player p:players)
						if(p.bestFifthHand==best_hand) if(ties==1) {
							if(xerbose&&anybody_improved||zerbose||aerbose) System.out.print(p.id);
							p.individual[p.bestFifthHand].fifth.wins++;
							p.total.fifth.wins++;
							if(p.bestFifthHand==p.boardHand) {
								p.individual[p.bestFifthHand].board.wins++;
								p.total.board.wins++;
							}
						} else {
							p.individual[p.bestFifthHand].fifth.ties++;
							p.total.fifth.ties++;
							if(p.bestFifthHand==p.boardHand) {
								p.individual[p.bestFifthHand].board.ties++;
								p.total.board.ties++;
							}
						}
						else {
							p.individual[p.bestFifthHand].fifth.losses++;
							p.total.fifth.losses++;
							if(p.bestFifthHand==p.boardHand) {
								p.individual[p.bestFifthHand].board.losses++;
								p.total.board.losses++;
							}
						}
					if(xerbose&&ties>1) System.out.print(-ties);
					if(xerbose&&anybody_improved||zerbose||aerbose) System.out.print(" ");
				}
		if(xerbose&&any_fifth_improvement||zerbose||aerbose) System.out.print("\n");
	}
	void fourth(final Rank r3,final Suit s3) {
		for(Rank r4:EnumSet.range(r3,highest_card_in_deck))
			for(Suit s4:EnumSet.range(Suit.clubs,Suit.spades))
				if(deck[r4.ordinal()][s4.ordinal()]&&(s3.ordinal()<s4.ordinal()||r3.ordinal()<r4.ordinal())) {
					remove(r4,s4); /* the fourth card of the flop */
					fourths++;
					best_hand=total; // should this be total+1?
					anybody_improved=false;
					for(Player p:players) {
						p.bestFourthHand=p.flopHand;
						for(int i=0;i<5;i++) {
							p.copyFlopHand();
							ranks[i]=r4;
							suits[i]=s4; /* replace the x'th card with the fourth street card */
							try {
								final short n=(short)OldLookup.lookup(ranks,Suit.areSuited(suits));
								if(n<p.bestFourthHand) {
									anybody_improved=true;
									// player[i].b4i=the_hand;
									p.bestFourthHand=n;
								}
							} catch(Exception e) {
								System.out.println("lookup fails on: "+Rank.toString(ranks)+' '+Suit.toString(suits));
								p.copyFlopHand();
								System.out.println("flop hand for player is: "+Rank.toString(p.flopRanks)+Suit.toString(p.flopSuits));
							}
						}
						if(best_hand>p.bestFourthHand) best_hand=(short)p.bestFourthHand;
					}
					ties=0;
					for(Player p:players)
						if(p.bestFourthHand<best_hand) throw new RuntimeException("player[i].b4h<best_hand");
						else if(p.bestFourthHand==best_hand) ties++;
					if(werbose&&anybody_improved||yerbose) for(Player p:players) {
						if(p==players.get(0)) System.out.print("   "+r4.toCharacter()+s4.toCharacter());
						if(p.bestFourthHand<p.flopHand) {
							final Card[] c=OldLookup.inverseLookup(p.bestFourthHand); // slow
						System.out.print(Rank.toString(Cards.ranks(c))+' '+PokerHand.HighType.type(p.bestFourthHand));
					} else System.out.print("        ");
				}
					for(Player p:players)
						if(p.bestFourthHand==best_hand) if(ties==1) {
							if(werbose&&anybody_improved||yerbose) System.out.print(p.id);
							p.individual[p.bestFourthHand].fourth.wins++;
							p.total.fourth.wins++;
						} else {
							p.individual[p.bestFourthHand].fourth.ties++;
							p.total.fourth.ties++;
						}
						else {
							p.individual[p.bestFourthHand].fourth.losses++;
							p.total.fourth.losses++;
						}
					if(werbose&&ties>1) System.out.print(-ties);
					if(werbose&&anybody_improved||yerbose) System.out.println();
					any_fifth_improvement=false;
					if(fifth) pfifth(r4,s4);
					insert(r4,s4); /* the fourth card of the flop */
				}
	}
	void flop() {
		target=0;
		flops=fourths=fifths=0;
		for(Player p:players) {
			Frequency t;
			p.flopRanks[0]=p.holeRanks[0];
			p.flopSuits[0]=p.holeSuits[0];
			p.flopRanks[1]=p.holeRanks[1];
			p.flopSuits[1]=p.holeSuits[1];
			t=p.total;
			t.flop.wins=t.flop.ties=t.flop.losses=t.fourth.wins=t.fourth.ties=t.fourth.losses=t.fifth.wins=t.fifth.ties=t.fifth.losses=t.board.wins=t.board.ties=t.board.losses=0;
			for(PokerHand.HighType type:PokerHand.HighType.values())
				p.type[type.ordinal()]=0;
		}
		for(Rank r1:EnumSet.range(Rank.aceLow,highest_card_in_deck))
			for(Suit s1:EnumSet.range(Suit.clubs,Suit.spades))
				if(deck[r1.ordinal()][s1.ordinal()]) {
					remove(r1,s1); /* the first card of the flop */
					for(Player p:players) {
						p.flopRanks[2]=r1;
						p.flopSuits[2]=s1;
					}
					for(Rank r2:EnumSet.range(r1,highest_card_in_deck))
						for(Suit s2:EnumSet.range(Suit.clubs,Suit.spades))
							if(deck[r2.ordinal()][s2.ordinal()]&&(s1.ordinal()<s2.ordinal()||r1.ordinal()<r2.ordinal())) {
								remove(r2,s2); /* the second card of the flop */
								for(Player p:players) {
									p.flopRanks[3]=r2;
									p.flopSuits[3]=s2;
								}
								for(Rank r3:EnumSet.range(r2,highest_card_in_deck))
									for(Suit s3:EnumSet.range(Suit.clubs,Suit.spades))
										if(deck[r3.ordinal()][s3.ordinal()]&&(s2.ordinal()<s3.ordinal()||r2.ordinal()<r3.ordinal())) {
											remove(r3,s3); /* the third card of the flop */
											flops++;
											best_flop_hand=total;
											/* printf("%s%s %s%s %s%s\n",r2a(r1),s2a(s1),r2a(r2),s2a(s2),r2a(r3),s2a(s3)); */
											for(Player p:players) {
												p.flopRanks[4]=r3;
												p.flopSuits[4]=s3;
												p.copyFlopHand();
												final short n=(short)OldLookup.lookup(ranks,Suit.areSuited(suits));
												p.flopHand=n;
												p.type[PokerHand.HighType.type(n).ordinal()]++;
												if(n<best_flop_hand) best_flop_hand=(short)n;
												/* add other properties here */
												if(verbose) {
													if(p==players.get(0)) System.out.print("      ");
													else System.out.print(" ");
													System.out.print(Rank.toString(ranks));
													System.out.print(PokerHand.HighType.type(n));
												}
											}
											ties=0;
											for(Player p:players)
												if(p.flopHand<best_flop_hand) throw new RuntimeException("player[i].fh<best_flop_hand");
												else if(p.flopHand==best_flop_hand) ties++;
											for(Player p:players)
												if(p.flopHand==best_flop_hand) if(ties==1) {
													if(verbose) System.out.print(p.id);
													p.individual[p.flopHand].flop.wins++;
													p.total.flop.wins++;
												} else {
													p.individual[p.flopHand].flop.ties++;
													p.total.flop.ties++;
												}
												else {
													p.individual[p.flopHand].flop.losses++;
													p.total.flop.losses++;
												}
											if(verbose&&ties>1) System.out.print(-ties);
											if(verbose)
												System.out.println(""+r1.toCharacter()+s1.toCharacter()+' '+r2.toCharacter()+s2.toCharacter()+' '
														+r3.toCharacter()+s3.toCharacter()+' '+flops+' '+(flops/(float)predicted_flops));
											if(fourth) fourth(r3,s3);
											if(fourth&&(flops%report_frequency==0)) report();
											insert(r3,s3); /* the third card of the flop */
										}
								insert(r2,s2); /* the second card of the flop */
							}
					insert(r1,s1); /* the first card of the flop */
				}
	}
	void report() {
		System.out.println("report");
		for(Player p:players)
			System.out.println(p.flopInfo());
		for(Player p:players)
			System.out.println(p.fourthInfo());
		for(Player p:players)
			System.out.println(p.fifthInfo());
		for(Player p:players)
			System.out.println(p.boardInfo());
		System.out.println(flops+" flops  ,"+predicted_flops+" "+flops/(float)predicted_flops);
		System.out.println(fourths+" fourths  ,"+predicted_fourths+" "+fourths/(float)predicted_fourths);
		System.out.println(fifths+" fifths  ,"+predicted_fifths+" "+fifths/(float)predicted_fifths);
	}
	public static void main(String[] args) {
		final Donb404182010 don=new Donb404182010();
		don.init(args);
		don.flop();
		don.report();
		if(don.flops!=don.predicted_flops) System.out.println("don.flops!=don.predicted_flops");
		if(don.fourths!=don.predicted_fourths) System.out.println("don.fourths!=don.predicted_fourths");
		if(don.fifths!=don.predicted_fifths) System.out.println("don.fifths!=don.predicted_fifths");
		/*
		 * for(p=0;p<players;p++) { print_hole_cards(p); printf("\n"); for(i=0;i<NAH;i++) printf("%8d %s\n",player[p].type[i],lh2a(i)); }
		 */
	}
	final boolean[][] deck=new boolean[Rank.values().length][Suit.values().length];
	boolean verbose,werbose,xerbose,yerbose,zerbose,aerbose;
	boolean board,fourth,fifth,distribution;
	String output_name;
	Rank highest_card_in_deck;
	int report_frequency;
	final int maxPlayers=5;
	final List<Player> players=new ArrayList<Player>();
	int number_of_cards;
	long predicted_flops,predicted_fourths,predicted_fifths;
	final Rank[] ranks=new Rank[5];
	final Suit[] suits=new Suit[5];
	long flops,fourths,fifths;
	boolean anybody_improved,any_fifth_improvement;
	short best_flop_hand,best_hand;
	short ties,target;
	static final int highHands=7462,wild=13,total=highHands+wild;
	static final DecimalFormat decimalFormat=new DecimalFormat("###.##%");
}
