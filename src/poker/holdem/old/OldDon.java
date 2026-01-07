package poker.holdem.old;



public class OldDon {
	void ESAD() { System.err.println("ERROR"); System.exit(1); }
	static int playes=5;
	static String[] r2aa=new String[] { "A","2","3","4","5","6","7","8","9","T","J","Q","K","A","*","?" };
	static String[] lr2aa=new String[] { "Ace","Duece","Trey","Four","Five","Six","Seven","Eight","Nine","Ten","Jack","Queen","King","Ace","Joker","Not a card" };
	static String[] s2aa=new String[] { "c","d","h","s","?" };
	static String[] ls2aa=new String[] { "Clubs","Diamonds","Hearts","Spades","Not a suit" };
	static String[] h2aa=new String[] { "n","1","2","3","s","f","p","4","S","5","?" };
	static String[] lh2aa=new String[]{"No pair","One pair","Two pair","Three of a kind","Straight","Flush","Full house","Four of a kind","Straight flush","Five of a kind","Not a hand"};
	static String r2a(int x) { return(r2aa[x]); }
	static String lr2a(int x) { return(lr2aa[x]); }
	static String s2a(int x) { return(s2aa[x]); }
	static String ls2a(int x) { return(ls2aa[x]); }
	static String h2a(int x) { return(h2aa[x]); }
	static String lh2a(int x) { return(lh2aa[x]); }
	static int a2r(char c) {
		return switch (c) {
			case 'a' -> Constant.ace_low;
			case '2' -> Constant.duece;
			case '3' -> Constant.trey;
			case '4' -> Constant.four;
			case '5' -> Constant.five;
			case '6' -> Constant.six;
			case '7' -> Constant.seven;
			case '8' -> Constant.eight;
			case '9' -> Constant.nine;
			case 'T' -> Constant.ten;
			case 'J' -> Constant.jack;
			case 'Q' -> Constant.queen;
			case 'K' -> Constant.king;
			case 'A' -> Constant.ace_high;
			case '*' -> Constant.joker;
			case '?' -> Constant.nac;
			default -> Constant.nac;
		};
	}
	int a2s(char c) {
		return switch (c) {
			case 'c' -> Constant.clubs;
			case 'd' -> Constant.diamonds;
			case 'h' -> Constant.hearts;
			case 's' -> Constant.spades;
			case '?' -> Constant.nas;
			default -> Constant.nas;
		};
	}
	int a2h(char c) {
		return switch (c) {
			case 'n' -> Constant.no_pair;
			case '1' -> Constant.one_pair;
			case '2' -> Constant.two_pair;
			case '3' -> Constant.three_of_a_kind;
			case 's' -> Constant.straight;
			case 'f' -> Constant.flush;
			case 'p' -> Constant.full_house;
			case '4' -> Constant.four_of_a_kind;
			case 'S' -> Constant.straight_flush;
			case '5' -> Constant.five_of_a_kind;
			case '?' -> Constant.nah;
			default -> Constant.nah;
		};
	}
static boolean verbose,werbose,xerbose,yerbose,zerbose,aerbose;
static boolean board,fourth,fifth,distribution;
static int report_frequency,highest_card_in_deck;
static boolean[][] deck=new boolean[Constant.nac][Constant.nas];
static String output_name;

static void initialize(String[] argument)
{
int i,p;
int r;
int s; // was SUIT
String a;
verbose=werbose=xerbose=yerbose=zerbose=aerbose=false;
board=false;
fourth=fifth=false;
report_frequency=1000;
distribution=false;
highest_card_in_deck=Constant.king;
for(r=Constant.ace_low;r<=Constant.king;r++)
for(s=Constant.clubs;s<=Constant.spades;s++)
	deck[r][s]=true;
p=0;
output_name="";
//lsetup();  // what did this do?
for(i=0;i<argument.length;i++)
	if(argument[i].startsWith("-"))
		{
		if(argument[i].length()<2) continue;
		switch(argument[i].charAt(1))
			{
			case 'v': verbose=true; break;
			case 'w': werbose=true; break;
			case 'x': xerbose=true; break;
			case 'y': yerbose=true; break;
			case 'z': zerbose=true; break;
			case 'b': board=true; break;
			case '4': fourth=true; break;
			case '5': fifth=true; break;
			case '0': report_frequency=1; break;
			case '1': report_frequency=10; break;
			case '2': report_frequency=100; break;
			case 's': highest_card_in_deck=Constant.six; break;
			case 't': highest_card_in_deck=Constant.five; break;
			case 'd': distribution=true; break;
			default: break;
			}
		}
		else
		{
		output_name+=argument[i]+'_';
		/*
		player[p].hr[FIRST]=a2r(a[0]);
		if(player[p].hr[FIRST]==ACE_HIGH)
			player[p].hr[FIRST]=ACE_LOW;
		player[p].hs[FIRST]=a2s(a[1]);
		remove(player[p].hr[FIRST],player[p].hs[FIRST]);
		player[p].hr[SECOND]=a2r(a[2]);
		if(player[p].hr[SECOND]==ACE_HIGH)
			player[p].hr[SECOND]=ACE_LOW;
		player[p].hs[SECOND]=a2s(a[3]);
		remove(player[p].hr[SECOND],player[p].hs[SECOND]);
   			p++;
		*/
		}
if(fifth)
	fourth=true;
/*
players=p;
end_of_players=player+players;
number_of_cards=4*(highest_card_in_deck+1);
predicted_flops=c(number_of_cards-2*players,3);
predicted_fourths=c(number_of_cards-2*players,4);
predicted_fifths=c(number_of_cards-2*players,5);
getbu("da_binary");
for(p=0;p<players;p++)
	print_hole_cards(player[p]);
if(players)
	printf("\n");
*/
}
public static void main(String[] argument)
	{
	int i,p;
	initialize(argument);
	//flop();
	//report();
	//if(flops!=predicted_flops) ESAD();
	//if(fourths!=predicted_fourths) ESAD();
	//if(fifths!=predicted_fifths) ESAD();
	/*
	for(p=0;p<players;p++)
		{
		print_hole_cards(p);
		printf("\n");
		for(i=0;i<NAH;i++)
			printf("%8d %s\n",player[p].type[i],lh2a(i));
		}
	*/
	}
}
/*
short lookup();
struct hand other[OTHERS];
struct hand flush[FLUSHES];
struct hand *the_hand;
struct frequency { long wf,tf,lf,w4,t4,l4,w5,t5,l5,wb,tb,lb; };
struct aplayer
	{
	RANK hr[2];			// hole rank
	SUIT hs[2];			// hole suit
	RANK fr[5];			// flop rank
	SUIT fs[5];			// flop suit
	short fh,b4h,b5h,bh;
	struct hand  *fi,*b4i,*b5i;	// indices 
	struct frequency total;
	short type[NAH];
	struct frequency individual[TOTAL];
	};
RANK rank[5];
SUIT suit[5];
static struct aplayer player[PLAYERS];
static struct aplayer *end_of_players;
static char output_name[256];
static BOOLEAN deck[NAC][NAS];
static int players;
static int playern;
static long predicted_flops,predicted_fourths,predicted_fifths;
static BOOLEAN fourth,fifth,verbose,werbose,xerbose,yerbose,zerbose,aerbose,board;
static int report_frequency;
static BOOLEAN distribution;
static RANK highest_card_in_deck;
static int number_of_cards;
static RANK r1,r2,r3,r4,r5;
static SUIT s1,s2,s3,s4,s5;
static long flops,fourths,fifths;
static BOOLEAN anybody_improved,any_fifth_improvement;
static short best_flop_hand,best_hand;
static short ties,target;
static remove(r,s) RANK r; SUIT s;
	{ if(deck[r][s]) deck[r][s]=FALSE; else { fprintf(stderr,"already removed\n"); exit(1); } }
static insert(r,s) RANK r; SUIT s;
	{ if(!deck[r][s]) deck[r][s]=true; else { fprintf(stderr,"already inserted\n"); exit(1); } }
static print_hole_cards(p)
	struct aplayer *p;
	{
	printf("%s%s%s%s ",r2a(p->hr[FIRST]),s2a(p->hs[FIRST]),r2a(p->hr[SECOND]),s2a(p->hs[SECOND]));
	}
static print_hand_ranks(p)
	RANK *p;
	{
	printf(" %s%s%s%s%s",r2a(p[FIRST]),r2a(p[SECOND]),r2a(p[THIRD]),r2a(p[FOURTH]),r2a(p[FIFTH]));
	}
#define COPY rank[0]=p->fr[0], suit[0]=p->fs[0], rank[1]=p->fr[1], suit[1]=p->fs[1], rank[2]=p->fr[2], suit[2]=p->fs[2], rank[3]=p->fr[3], suit[3]=p->fs[3], rank[4]=p->fr[4], suit[4]=p->fs[4]
struct five { char x[5]; };
#define COPY	*(struct five *)&rank[0]=(*(struct five *)&(p->fr[0])),*(struct five *)&suit[0]=(*(struct five *)&(p->fs[0]))
static pfifth()
	{
	struct aplayer *p;
	for(r5=r4;r5>=ACE_LOW;r5--)
	for(s5=CLUBS;s5<=SPADES;s5++)
	if(deck[r5][s5]&&(s4<s5||r4>r5))
		{
		fifths++;
		best_hand=TOTAL;
		anybody_improved=FALSE;
		for(p=player;p<end_of_players;p++)
			{
			register short n;
			register short b5h;
			register struct hand *b5i;
			b5h=p->b4h;
			b5i=p->b4i;
			COPY;
			rank[1-1]=r5; suit[1-1]=s5;
			n=lookup();
			if(n<b5h)
				{ b5i=the_hand; b5h=n;}
			COPY;
			rank[2-1]=r5; suit[2-1]=s5;
			n=lookup();
			if(n<b5h)
				{ b5i=the_hand; b5h=n;}
			COPY;
			rank[3-1]=r5; suit[3-1]=s5;
			n=lookup();
			if(n<b5h)
				{ b5i=the_hand; b5h=n;}
			COPY;
			rank[4-1]=r5; suit[4-1]=s5;
			n=lookup();
			if(n<b5h)
				{ b5i=the_hand; b5h=n;}
			COPY;
			rank[5-1]=r5; suit[5-1]=s5;
			n=lookup();
			if(n<b5h)
				{ b5i=the_hand; b5h=n;}
			COPY;
			rank[1-1]=r4; suit[1-1]=s4; rank[2-1]=r5; suit[2-1]=s5;
			n=lookup(); 
			if(n<b5h)
				{ b5i=the_hand; b5h=n;}
			p->bh=n; // playing the board 
			COPY;
			rank[1-1]=r4; suit[1-1]=s4; rank[3-1]=r5; suit[3-1]=s5;
			n=lookup();
			if(n<b5h)
				{ b5i=the_hand; b5h=n;}
			COPY;
			rank[1-1]=r4; suit[1-1]=s4; rank[4-1]=r5; suit[4-1]=s5;
			n=lookup();
			if(n<b5h)
				{ b5i=the_hand; b5h=n;}
			COPY;
			rank[1-1]=r4; suit[1-1]=s4; rank[5-1]=r5; suit[5-1]=s5;
			n=lookup();
			if(n<b5h)
				{ b5i=the_hand; b5h=n;}
			COPY;
			rank[2-1]=r4; suit[2-1]=s4; rank[3-1]=r5; suit[3-1]=s5;
			n=lookup();
			if(n<b5h)
				{ b5i=the_hand; b5h=n;}
			COPY;
			rank[2-1]=r4; suit[2-1]=s4; rank[4-1]=r5; suit[4-1]=s5;
			n=lookup();
			if(n<b5h)
				{ b5i=the_hand; b5h=n;}
			COPY;
			rank[2-1]=r4; suit[2-1]=s4; rank[5-1]=r5; suit[5-1]=s5;
			n=lookup();
			if(n<b5h)
				{ b5i=the_hand; b5h=n;}
			COPY;
			rank[3-1]=r4; suit[3-1]=s4; rank[4-1]=r5; suit[4-1]=s5;
			n=lookup();
			if(n<b5h)
				{ b5i=the_hand; b5h=n;}
			COPY;
			rank[3-1]=r4; suit[3-1]=s4; rank[5-1]=r5; suit[5-1]=s5;
			n=lookup();
			if(n<b5h)
				{ b5i=the_hand; b5h=n;}
			COPY;
			rank[4-1]=r4; suit[4-1]=s4; rank[5-1]=r5; suit[5-1]=s5;
			n=lookup();
			if(n<b5h)
				{ b5i=the_hand; b5h=n;}
			if(best_hand>b5h) best_hand=b5h;
			if(b5h<p->b4h)
				anybody_improved=true;
			p->b5h=b5h;
			p->b5i=b5i;
			}
		ties=0;
		for(p=player;p<end_of_players;p++)
			if(p->b5h<best_hand) ESAD
			else if(p->b5h==best_hand) ties++;
		// hack for board 
		if(board)
			{
			aerbose=FALSE;
			for(p=player;p<end_of_players;p++)
				if(p->b5h==p->bh)
					aerbose=true;
			}
		// end of hack for board 
		if(xerbose&&anybody_improved||zerbose||aerbose)
			for(p=player;p<end_of_players;p++)
				{
				if(p==player)
					printf("%s%s %s%s",r2a(r4),s2a(s4),r2a(r5),s2a(s5));
				if(p->b5h<p->b4h)
					{
					any_fifth_improvement=true;
					print_hand_ranks(p->b5i->c);
					printf(" %s",h2a(p->b5i->type));
					}
					else printf("        ");
				}
		for(p=player,playern=0;p<end_of_players;p++,playern++)
			if(p->b5h==best_hand)
				if(ties==1)
					{
					if(xerbose&&anybody_improved||zerbose||aerbose)
						printf("%3d",playern);
					p->individual[p->b5h].w5++;
					p->total.w5++;
					if(p->b5h==p->bh)
						{
						p->individual[p->b5h].wb++;
						p->total.wb++;
						}
					}
					else
					{
					p->individual[p->b5h].t5++;
					p->total.t5++;
					if(p->b5h==p->bh)
						{
						p->individual[p->b5h].tb++;
						p->total.tb++;
						}
					}
				else
				{
				p->individual[p->b5h].l5++;
				p->total.l5++;
				if(p->b5h==p->bh)
					{
					p->individual[p->b5h].lb++;
					p->total.lb++;
					}
				}
		if(xerbose&&ties>1) printf("%3d",-ties);
		if(xerbose&&anybody_improved||zerbose||aerbose) printf(" ");
		}
	if(xerbose&&any_fifth_improvement||zerbose||aerbose) printf("\n");
	}
static pfourth()
	{
	register struct aplayer *p;
	for(r4=r3;r4>=ACE_LOW;r4--)
	for(s4=CLUBS;s4<=SPADES;s4++)
	if(deck[r4][s4]&&(s3<s4||r3>r4))
		{
		remove(r4,s4);	// the fourth card of the flop
		fourths++;
		best_hand=TOTAL;
		anybody_improved=FALSE;
		for(p=player;p<end_of_players;p++)
			{
			int x;
			p->b4h=p->fh;
			p->b4i=p->fi;
			for(x=1;x<=5;x++)
				{
				short n;
				COPY;
				rank[x-1]=r4; suit[x-1]=s4; // replace the x'th card with the fourth street card
				n=lookup();
				if(n<p->b4h)
					{ anybody_improved=true; p->b4i=the_hand; p->b4h=n;}
				}
			if(best_hand>p->b4h)
				best_hand=p->b4h;
			}
		ties=0;
		for(p=player;p<end_of_players;p++)
			if(p->b4h<best_hand) ESAD
			else if(p->b4h==best_hand) ties++;
		if(werbose&&anybody_improved||yerbose)
			for(p=player;p<end_of_players;p++)
				{
				if(p==player)
					printf("   %s%s",r2a(r4),s2a(s4));
				if(p->b4h<p->fh)
					{
					print_hand_ranks(p->b4i->c);
					printf(" %s",h2a(p->b4i->type));
					}
					else printf("        ");
				}
		for(p=player,playern=0;p<end_of_players;p++,playern++)
			if(p->b4h==best_hand)
				if(ties==1)
					{
					if(werbose&&anybody_improved||yerbose)
						printf("%3d",playern);
					p->individual[p->b4h].w4++;
					p->total.w4++;
					}
					else
					{
					p->individual[p->b4h].t4++;
					p->total.t4++;
					}
				else
				{
				p->individual[p->b4h].l4++;
				p->total.l4++;
				}
		if(werbose&&ties>1) printf("%3d",-ties);
		if(werbose&&anybody_improved||yerbose) printf("\n");
		any_fifth_improvement=FALSE;
		if(fifth)
			pfifth();
		insert(r4,s4);	// the fourth card of the flop
		}
	}
static flop()
	{
	short i;
	register struct aplayer *p;
	target=0;
	flops=fourths=fifths=0;
	for(p=player;p<end_of_players;p++)
		{
		struct frequency *t;
		p->fr[FIRST]=p->hr[FIRST]; p->fs[FIRST]=p->hs[FIRST];
		p->fr[SECOND]=p->hr[SECOND]; p->fs[SECOND]=p->hs[SECOND];
		t=(&p->total);
		t->wf=t->tf=t->lf=t->w4=t->t4=t->l4=t->w5=t->t5=t->l5=t->wb=t->tb=t->lb=0;
		for(i=0;i<NAH;i++)
			p->type[i]=0;
		}
	for(r1=highest_card_in_deck;r1>=ACE_LOW;r1--)
	for(s1=CLUBS;s1<=SPADES;s1++)
	if(deck[r1][s1])
		{
		remove(r1,s1);	// the first card of the flop/
		for(p=player;p<end_of_players;p++)
			{ p->fr[THIRD]=r1; p->fs[THIRD]=s1; }
		for(r2=r1;r2>=ACE_LOW;r2--)
		for(s2=CLUBS;s2<=SPADES;s2++)
		if(deck[r2][s2]&&(s1<s2||r1>r2))
			{
			remove(r2,s2);	// the second card of the flop
			for(p=player;p<end_of_players;p++)
				{ p->fr[FOURTH]=r2; p->fs[FOURTH]=s2; }
			for(r3=r2;r3>=ACE_LOW;r3--)
			for(s3=CLUBS;s3<=SPADES;s3++)
			if(deck[r3][s3]&&(s2<s3||r2>r3))
				{
				remove(r3,s3);	// the third card of the flop
				flops++;
				best_flop_hand=TOTAL;
				// printf("%s%s %s%s %s%s\n",r2a(r1),s2a(s1),r2a(r2),s2a(s2),r2a(r3),s2a(s3));
				for(p=player;p<end_of_players;p++)
					{
					int n;
					p->fr[FIFTH]=r3; p->fs[FIFTH]=s3;
					COPY;
					n=lookup();
					p->fi=the_hand;
					p->fh=n;
					p->type[the_hand->type]++;
					if(n<best_flop_hand) best_flop_hand=n;
					// add other properties here
					if(verbose)
						{
						if(p==player) printf("      "); else printf(" ");
						print_hand_ranks(rank);
						printf(" %s",h2a(the_hand->type));
						}
					}
				ties=0;
				for(p=player;p<end_of_players;p++)
					if(p->fh<best_flop_hand) ESAD
					else if(p->fh==best_flop_hand) ties++;
				for(p=player,playern=0;p<end_of_players;p++,playern++)
					if(p->fh==best_flop_hand)
						if(ties==1)
							{
							if(verbose)
								printf("%3d",playern);
							p->individual[p->fh].wf++;
							p->total.wf++;
							}
							else
							{
							p->individual[p->fh].tf++;
							p->total.tf++;
							}
						else
						{
						p->individual[p->fh].lf++;
						p->total.lf++;
						}
				if(verbose&&ties>1) printf("%3d",-ties);
				if(verbose)
					printf("  %s%s %s%s %s%s %5d %7.5f\n",r2a(r1),s2a(s1),r2a(r2),s2a(s2),r2a(r3),s2a(s3),flops,(float)flops/(float)predicted_flops);
				if(fourth)
					pfourth();
				if(fourth&&(flops%report_frequency==0))
					report();
				insert(r3,s3);	// the third card of the flop
				}
			insert(r2,s2);	// the second card of the flop
			}
		insert(r1,s1);	// the first card of the flop
		}
	}
static distribution_report()
	{
	}
static report()
	{
	struct aplayer *p;
	for(p=player;p<end_of_players;p++)
		{
		fprintf(stdout,"%s%s%s%s ",r2a(p->hr[FIRST]),s2a(p->hs[FIRST]),r2a(p->hr[SECOND]),s2a(p->hs[SECOND]));
		fprintf(stdout,"%8d wins,%8d ties,%8d losses (flop)\n",p->total.wf,p->total.tf,p->total.lf);
		}
	for(p=player;p<end_of_players;p++)
		{
		fprintf(stdout,"%s%s%s%s ",r2a(p->hr[FIRST]),s2a(p->hs[FIRST]),r2a(p->hr[SECOND]),s2a(p->hs[SECOND]));
		fprintf(stdout,"%8d wins,%8d ties,%8d losses (fourth street)\n",p->total.w4,p->total.t4,p->total.l4);
		}
	for(p=player;p<end_of_players;p++)
		{
		fprintf(stdout,"%s%s%s%s ",r2a(p->hr[FIRST]),s2a(p->hs[FIRST]),r2a(p->hr[SECOND]),s2a(p->hs[SECOND]));
		fprintf(stdout,"%8d wins,%8d ties,%8d losses (fifth street)\n",p->total.w5,p->total.t5,p->total.l5);
		}
	for(p=player;p<end_of_players;p++)
		{
		fprintf(stdout,"%s%s%s%s ",r2a(p->hr[FIRST]),s2a(p->hs[FIRST]),r2a(p->hr[SECOND]),s2a(p->hs[SECOND]));
		fprintf(stdout,"%8d wins,%8d ties,%8d losses (board)\n",p->total.wb,p->total.tb,p->total.lb);
		}
	fprintf(stdout,"%9d flops  ,%9d %5.2f\n",flops,predicted_flops,flops/(float)predicted_flops);
	fprintf(stdout,"%9d fourths,%9d %5.2f\n",fourths,predicted_fourths,fourths/(float)predicted_fourths);
	fprintf(stdout,"%9d fifths ,%9d %5.2f\n",fifths,predicted_fifths,fifths/(float)predicted_fifths);
	}
	*/
