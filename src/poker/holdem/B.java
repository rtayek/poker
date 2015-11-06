package poker.holdem;

import java.util.Arrays;

public class B {
	static void f1() {
		Byte[] bytes=new Byte[256];
		byte b=Byte.MIN_VALUE;
		for(int i=0;i<bytes.length;i++)
			bytes[i]=b++;
		System.out.println(Arrays.asList(bytes));
		for(int i=0;i<bytes.length;i++)
			bytes[i]++;
		System.out.println(Arrays.asList(bytes));
		for(int i=0;i<bytes.length;i++)
			bytes[i]--;
		System.out.println(Arrays.asList(bytes));
		
	}
	public static void main(String[] args) {
		f1();
	}
}
