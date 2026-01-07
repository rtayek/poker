package poker.machine;
import java.io.IOException;
public abstract class CommandLineBase {
	protected boolean automatic;
	private int autoIndex=2;
	public static final String lineSeparator=System.getProperty("line.separator");
	protected int getChar() {
		int c=-1;
		try {
			c=System.in.read();
		} catch(IOException e) {}
		if(lineSeparator.indexOf(c)!=-1)
			return getChar();
		return c;
	}
	protected int get() {
		int c=-1;
		if(!automatic)
			c=getChar();
		else
			c=switch(++autoIndex%3) {
				case 0 -> 'b';
				case 1 -> 'd';
				default -> 'r';
			};
		return c;
	}
	public static void waitForEnter(String s) {
		System.out.println(s);
		waitForEnter();
	}
	public static void waitForEnter() {
		System.out.println("type enter to continue");
		System.out.flush();
		try {
			System.in.read();
		} catch(IOException e) {}
	}
}
