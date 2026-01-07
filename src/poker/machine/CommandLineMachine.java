package poker.machine;
public interface CommandLineMachine {
	void bet();
	void deal();
	void draw();
	void toggleHold(int n);
	State state();
	int maxBets();
}
