//https://svncvpr.in.tum.de/cvpr-ros-pkg/trunk/cvpr-tools/symboliccpp/include/symboliccpp/multinomial.h
import java.util.*;
public class Combinations {
	public static void main(String[] args) {
		test1();
		test2();
	}
	public static void test1() {
		Combination c=new Combination(4,2);
		while(c.hasNext()) {
			Integer[] a=c.next();
			System.out.println(Arrays.toString(a));
		}
	}
	public static void test2() {
		Scanner scanner=new Scanner(System.in);
		System.out.print("Enter n: ");
		int n=scanner.nextInt();
		System.out.print("Enter r: ");
		int r=scanner.nextInt();
		scanner.close();
		Combination c=new Combination(n,r);
		System.out.println("Here are all the ways you can combine "+r+" choices among "+n+" objects:");
		int counter=0;
		while(c.hasNext()) {
			Integer[] next=c.next();
			System.out.println(Arrays.toString(next));
			counter++;
		}
		System.out.println("total = "+n+"C"+r+" = "+counter);
	}
}
class Combination {
	public Combination(int n,int r) {
		this.n=n;
		this.r=r;
		index=new int[r];
		for(int i=0;i<r;i++)
			index[i]=i;
	}
	public boolean hasNext() {
		return hasNext;
	}
	// The algorithm is from Applied Combinatorics, by Alan Tucker.
	private void moveIndex() {
		int i=rightmostIndexBelowMax();
		if(i>=0) {
			index[i]=index[i]+1;
			for(int j=i+1;j<r;j++)
				index[j]=index[j-1]+1;
		} else hasNext=false;
	}
	public Integer[] next() {
		if(!hasNext)
			return null;
		Integer[] result=new Integer[r];
		for(int i=0;i<r;i++)
			result[i]=index[i];
		moveIndex();
		return result;
	}
	private int rightmostIndexBelowMax() {
		for(int i=r-1;i>=0;i--)
			if(index[i]<n-r+i)
				return i;
		return -1;
	}
	private int n,r;
	private int[] index;
	private boolean hasNext=true;
}

