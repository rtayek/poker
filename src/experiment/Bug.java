package experiment;
public class Bug {
	public static void main(String[] args) {
		String string="a";
		for(int j=0;j<5;j++) {
			System.out.print(string+" "+(j+1));
			for(int i=0;i<13;i++)
				System.out.print(i+" ");
			System.out.println();
		}
	}
}
