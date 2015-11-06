import java.text.*;

public class DF {
	public static void main(String[] argument) {
		final Double d=1.23459;
		System.out.println(d);
		System.out.println(decimalFormat.format(d));
	}
	static final DecimalFormat decimalFormat=new DecimalFormat("###.##%");
}
