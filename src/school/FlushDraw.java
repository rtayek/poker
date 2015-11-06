package school;
import static java.lang.Math.*;
import java.util.function.BinaryOperator;
import com.tayek.utilities.Pair;
public class FlushDraw { // one pair vs 4 flush
	// convention in
	// http://www.amazon.com/The-Compleat-Strategyst-Strategy-Mathematics/dp/0486251012
	// is that the payoff is for blue, so my row mins and col maxes are wrong!
	// maybe not as things seem to agree sort of.
	private static void calculateExpectationMatrix(E e,final int n,double[][] e1s,double[][] e2s,double xFactor,double yFactor) {
		double pBluff;
		double pCall;
		double e1;
		double e2;
		for(int i=0;i<=n;i++) {
			System.out.printf("%3d ",(int)(i*100./n/yFactor));
			pCall=1.*i/n/yFactor;
			for(int j=0;j<=n;j++) {
				//pBluff=(1-p)*j/n/xFactor; // is this correct?
				pBluff=1.*j/n/xFactor; // is this correct?
				e1=e.e1(pBluff,pCall);
				// e1=e1(p,pCall,pBluff,pot);
				e1s[i][j]=e1;
				e2=e.e2(pBluff,pCall); // e[i][j] is e2(p,j,i)!!!
				// e2=e2(p,pCall,pBluff,pot); // try this
				e2s[i][j]=e2;
				System.out.printf("%6.3f/%6.3f    ",e1,e2);
				// System.out.printf("%+5.2f    ",e1);
			}
			System.out.println();
		}
	}
	private static Pair<Double,Double> minMax(final int n,double[][] es,double[] rowMins,double[] colMaxes) {
		double maxMin=-Double.MAX_VALUE; // max of row mins
		for(int i=0;i<=n;i++) {
			double rowMin=Double.MAX_VALUE;
			for(int j=0;j<=n;j++) {
				rowMin=min(rowMin,es[i][j]);
				// System.out.println("e[i][j]="+e[i][j]+", maxMin="+maxMin+", rowMin="+rowMin);
			}
			rowMins[i]=rowMin;
			maxMin=max(maxMin,rowMin);
		}
		double minMax=Double.MAX_VALUE; // min of column maxes
		for(int j=0;j<=n;j++) {
			double colMax=-Double.MAX_VALUE;
			for(int i=0;i<=n;i++)
				colMax=max(colMax,es[i][j]);
			minMax=min(minMax,colMax);
			colMaxes[j]=colMax;
		}
		Pair<Double,Double> pair=new Pair<>(minMax,maxMin);
		return pair;
	}
	void run() {
		
	}
	public static void main(String[] args) {
		int outs=9,remaining=52-4;
		double p=outs/(double)remaining;
		double odds=(remaining-outs)/(double)outs;
		E e=new E(p,.0001,1);
		System.out.println(e);
		// warmup(p,odds,pot);
		final int n=5;
		double xFactor=1,yFactor=1;
		double[][] e1s=new double[n+1][n+1];
		double[][] e2s=new double[n+1][n+1];
		double[] rowMins=new double[n+1];
		double[] colMaxes=new double[n+1];
		System.out.print("     ");
		for(int j=0;j<=n;j++)
			System.out.printf("%3d      ",(int)(j*100./n/xFactor));
		System.out.println();
		System.out.println("     bluff frequency");
		calculateExpectationMatrix(e,n,e1s,e2s,xFactor,yFactor);
		System.out.println();
		// print(n,e1s,e2s);
		Pair<Double,Double> pair=minMax(n,e2s,rowMins,colMaxes);
		System.out.println("max of row mins maxMin="+pair.second);
		System.out.println("min of col maxs minMax="+pair.first);
		System.out.println();
		System.out.println("                bluff frequency");
		System.out.print("            ");
		for(int j=0;j<=n;j++)
			System.out.printf("%3d       ",(int)(j*100./n/xFactor));
		System.out.println(" rowMin");
		for(int i=0;i<=n;i++) {
			System.out.printf("  %3d     ",(int)(i*100./n/yFactor));
			for(int j=0;j<=n;j++) {
				System.out.printf("%8.4f  ",e2s[i][j]);
			}
			System.out.printf(" %8.4f",rowMins[i]);
			System.out.println();
		}
		System.out.print("\ncolMax ");
		for(int j=0;j<=n;j++)
			System.out.printf("%+6.3f   ",colMaxes[j]);
		System.out.println();
		// print the rowmins and col maxs out!
		System.out.println("-----------------------------------------------------");
		System.out.println(e);
		MyIterator<Double> iX=new MyIterator<Double>(0.,1.,(x,y) -> x.compareTo(y),x -> x+.2);
		MyIterator<Double> iY=new MyIterator<Double>(0.,1.,(x,y) -> x.compareTo(y),y -> y+.2);
		BinaryOperator<Double> binaryOperator=(x,y) -> e.e2(x,y);
		Matrix matrix=new Matrix(iX,iY,binaryOperator);
		matrix.print();
		//for(int j=0;j<5;j++)
			//System.out.println(e2s[1][j]+" "+minMax.payoffMatrix[1][j]);
		school.E.strategy(matrix.payoffMatrix);
		
	}
}