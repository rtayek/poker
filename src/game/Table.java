package game;
public class Table {
	Table(Game.Type game,Ante ante,Game.Limits limits,int seats) {
		// does he need to know the game?
		// probably not
		// so maybe the game knows the ante and limits?
		// maybe game always has a table?
		this.game=game;
		this.ante=ante;
		this.limits=limits;
		this.seats=seats;
		dealer=seats-1;
		players=new Player[seats];
	}
	public int players() { // with money!
		int n=0;
		for(int i=0;i<seats;i++)
			if(players[i]!=null&&players[i].chips>=ante.value) // what if it's his blind?
				n++;
		return n;
	}

	@Override public String toString() {
		return seats+" handed table";
	}
	int position(int seat) { // calculate position of player in seat
		// should be something like
		int p=(seat-dealer-ante.blinds.n+seats-1)%seats;
		if (p<0) p+=seats;
		// if(p==0) p=seats;
		return p; // 0 is first to act after the blinds
	}
	double pot() {
		return pot;
	}
	void ante() {
		for(Player player:players)
			if(player!=null&&player.chips>=ante.value) {
				player.chips-=ante.value;
				pot+=ante.value;
			}
				
	}
	void moveButton() {
		dealer=++dealer%seats; // what do we do if there is no player in this seat?
	}
	void run() {
		for(int i=0;i<seats;i++,dealer++) {
			System.out.println("\ndealer in seat "+dealer);
			for(int seat=0;seat<seats;seat++)
				System.out.println("dealer: "+dealer+", seat: "+seat+" is position: "+position(seat));
		}
	}
	public static void main(String[] args) {
		new Table(Game.Type.holdem,new Ante(Blinds.oneTwo,0),Game.Limits.noLimit,10).run();
	}
	final Game.Type game;
	final Ante ante;
	final Game.Limits limits;
	final int seats;
	int dealer; // position of the dealer?
	final Player[] players;
	transient double pot;

}
