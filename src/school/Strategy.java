package school;
import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.min;
import java.io.File;
import java.util.function.BinaryOperator;
import java.util.function.Function;
class Strategy {
	Strategy(double p11,double p12,double p21,double p22) {
		this(new double[][] { {p11,p12}, {p21,p22}});
	}
	Strategy(double[][] payoffMatrix) {
		this.rows=payoffMatrix.length;
		this.columns=payoffMatrix[0].length;
		this.payoffMatrix=payoffMatrix;
		colMaxes=new double[columns];
		rowMins=new double[rows];
		colMaxIndices=new int[columns];
		rowMinIndices=new int[rows];
		minMax();
		calculate();
	}
	void minMax() {
		min=Double.MAX_VALUE;
		maxMin=-Double.MAX_VALUE; // max of row mins
		for(int i=0;i<rows;i++) {
			double rowMin=Double.MAX_VALUE;
			for(int j=0;j<columns;j++)
				if(payoffMatrix[i][j]<rowMin) {
					rowMin=min(rowMin,payoffMatrix[i][j]);
					rowMinIndices[i]=j;
				}
			rowMins[i]=rowMin;
			min=min(min,rowMin);
			if(rowMin>maxMin) {
				maxMin=max(maxMin,rowMin);
				maxMinIndex=i;
			}
		}
		max=-Double.MAX_VALUE;
		minMax=Double.MAX_VALUE; // min of column maxes
		for(int j=0;j<columns;j++) {
			double colMax=-Double.MAX_VALUE;
			for(int i=0;i<rows;i++)
				if(payoffMatrix[i][j]>colMax) {
					colMax=max(colMax,payoffMatrix[i][j]);
					colMaxIndices[j]=i;
				}
			colMaxes[j]=colMax;
			max=max(max,colMax);
			if(colMax<minMax) {
				minMax=min(minMax,colMax);
				minMaxIndex=j;
			}
		}
	}
	void calculate() {
		double p11=payoffMatrix[0][0];
		double p12=payoffMatrix[0][columns-1];
		double p21=payoffMatrix[rows-1][0];
		double p22=payoffMatrix[rows-1][columns-1];
		if(abs(maxMin-minMax)<E.epsilon) {
			hasASaddlePoint=true;
			value=maxMin;
		} else {
			red2Oddment=abs(p11-p21);
			red1Oddment=abs(p12-p22);
			blue2Oddment=abs(p11-p12);
			blue1Oddment=abs(p21-p22);
			redAgaintsBlue1=(red1Oddment*p11+red2Oddment*p12)/(red1Oddment+red2Oddment);
			redAgaintsBlue2=(red1Oddment*p21+red2Oddment*p22)/(red1Oddment+red2Oddment);
			if(abs(redAgaintsBlue1-redAgaintsBlue2)>=E.epsilon) System.err.println("oops");
			value=redAgaintsBlue1;
		}
	}
	void printInfo() {
		if(hasASaddlePoint) {
			System.out.printf("saddle point, value of game is: %8.4f\n",minMax);
			System.out.println("saddel point is at row "+minMaxIndex+", column "+maxMinIndex);
		} else {
			System.out.println("no saddle point, mixed strategy");
			System.out.printf("Red  %6.4f:%6.4f (%6.4f,%6.4f)\n",red1Oddment,red2Oddment,E.p1(red1Oddment,red2Oddment),E.p2(red1Oddment,red2Oddment));
			System.out.printf("Blue %6.4f:%6.4f (%6.4f,%6.4f)\n",blue1Oddment,blue2Oddment,E.p1(blue1Oddment,blue2Oddment),E.p2(blue1Oddment,blue2Oddment));
			System.out.printf("red against blue1: %8.4f\n",redAgaintsBlue1);
			System.out.printf("red against blue2: %8.4f\n",redAgaintsBlue2);
		}
		System.out.printf("value of game is: %8.4f\n",value);
	}
	void print(Function<Double,String> rowLabel,Function<Double,String> columnLabel,Function<Double,String> formatter) {
		int rows=payoffMatrix.length,columns=payoffMatrix[0].length;
		System.out.println("                    Red Strategy");
		System.out.print("          ");
		for(int j=0;j<columns;j++)
			System.out.print(columnLabel.apply(1.*j));
		System.out.print("  rowMins    index");
		System.out.println();
		System.out.println("  Blue");
		for(int i=0;i<rows;i++) {
			System.out.print(rowLabel.apply(1.*i));
			for(int j=0;j<columns;j++)
				System.out.printf("%8.4f  ",payoffMatrix[i][j]); // ???
			System.out.printf(" %8.4f",rowMins[i]);
			System.out.printf(" %8d",rowMinIndices[i]);
			System.out.println();
		}
		System.out.println();
		System.out.print("colMaxes  ");
		for(int j=0;j<columns;j++)
			System.out.printf("%8.4f  ",colMaxes[j]);
		System.out.println();
		System.out.print("index     ");
		for(int j=0;j<columns;j++)
			System.out.printf("%8d  ",colMaxIndices[j]);
		System.out.println();
		System.out.printf("payoff range is from %8.4f to %8.4f\n",min,max);
		System.out.println("Red minMax: "+minMax+", Blue maxMin: "+maxMin);
	}
	void print() {
		print(rowLabel,columnLabel,formatter);
	}
	static double[][] generate(double[] x,double[] y,BinaryOperator<Double> payoff) {
		double[][] matrix=new double[y.length][x.length];
		generate(x,y,payoff,matrix);
		return matrix;
	}
	static void generate(double[] x,double[] y,BinaryOperator<Double> payoff,double[][] matrix) {
		for(int i=0;i<y.length;i++)
			for(int j=0;j<x.length;j++)
				matrix[i][j]=payoff.apply(x[j],y[i]);
	}
	String toLongString(double[] x,double[] y) {
		StringBuffer sb=new StringBuffer(rows*columns*20);
		for(int i=0;i<rows;i++)
			for(int j=0;j<columns;j++)
				sb.append(""+x[j]+" "+y[i]+" "+payoffMatrix[i][j]+"\n");
		return sb.toString();
	}
	public static void main(String[] args) {
		int outs=9,remaining=52-4;
		double p=outs/(double)remaining;
		E e=new E(p,2,1);
		System.out.println(e);
		double p11=e.e2(0,0);
		double p12=e.e2(1,0);
		double p21=e.e2(0,1);
		double p22=e.e2(1,1);
		Strategy strategy=new Strategy(p11,p12,p21,p22);
		strategy.print();
		strategy.printInfo();
		System.out.println("----");
		double p1Red=E.p1(e.pot,e.bet);
		System.out.println("pot of "+e.pot+" is offerring red "+(e.pot)+":"+e.bet+"("+p1Red+")");
		double p1Blue=E.p1(e.pot+e.bet,e.bet);
		System.out.println("a bet of "+e.bet+" is offering blue "+(e.pot+e.bet)+":"+e.bet+"("+p1Blue+")");
		printMore(e,strategy);
		Loop xLoop=new Loop(0,1,.2);
		Loop yLoop=new Loop(0,1,.2);
		BinaryOperator<Double> binaryOperator=(x,y) -> e.e2(x,y);
		System.out.println("--------------------------------------------");
		System.out.println(e);
		double[] x=xLoop.toArray();
		double[] y=yLoop.toArray();
		double[][] payoffMatrix=Strategy.generate(x,y,binaryOperator);
		Strategy strategy2=new Strategy(payoffMatrix);
		if(false) com.tayek.utilities.Utilities.toFile(strategy2.toLongString(x,y),new File(new File("plot"),"1.dat"));
		// strategy2.print();
		strategy2.printInfo();
		System.out.println("----");
		double p1Redx=E.p1(e.pot,e.bet);
		System.out.println("pot of "+e.pot+" is offerring red "+(e.pot)+":"+e.bet+"("+p1Redx+")");
		double p1Bluex=E.p1(e.pot+e.bet,e.bet);
		System.out.println("a bet of "+e.bet+" is offering blue "+(e.pot+e.bet)+":"+e.bet+"("+p1Bluex+")");
		printMore(e,strategy2);
		double pBlue1y=1-E.p1(strategy.blue1Oddment,strategy.blue2Oddment);
		System.out.println("---------");
		System.out.println("pRed1x="+p1Redx+", "+pBlue1y+" "+(p1Red-pBlue1y));			
		// school.E.strategy(0.8125,0.,0.6250,1.4375);
	}
	private static void printMore(E e,Strategy strategy) {
		if(strategy.hasASaddlePoint) {
			System.out.println("saddle point");
			if(strategy.minMaxIndex==0) System.out.println(" Red should bluff with probablility 0.");
			else System.out.println("unknown!!");
			if(strategy.maxMinIndex==0) System.out.println("Blue should  call with probablility 0.");
			else System.out.println("unknown!!");
		} else {
			System.out.println("mixed strategy");
			double pRed1=E.p1(strategy.red1Oddment,strategy.red2Oddment);
			double pBlue1=E.p1(strategy.blue1Oddment,strategy.blue2Oddment);
			if(!(Double.isNaN(pRed1)||Double.isNaN(pBlue1))) {
				System.out.printf("pRed1=%8.4f, pBlue1=%8.4f\n",pRed1,pBlue1);
				System.out.printf("e2(1-pRed1,1-pBlue1)=%8.4f\n",e.e2(1-pRed1,1-pBlue1));
				System.out.printf(" Red should bluff with probablility %8.4f\n",(1-pRed1));
				System.out.printf("Blue should  call with probablility %8.4f\n",(1-pBlue1));
			} else {
				System.out.println("e2(pRed1,pBlue1)="+Double.NaN);
				System.out.println("!!!!!!!!!!!!!!!!!!!!");
			}
		}
	}
	final int rows,columns;
	final double[][] payoffMatrix;
	final double[] rowMins,colMaxes;
	final int[] rowMinIndices,colMaxIndices;
	double minMax,maxMin;
	int minMaxIndex,maxMinIndex;
	double min,max;
	boolean hasASaddlePoint;
	double red1Oddment,red2Oddment;
	double blue1Oddment,blue2Oddment;
	double redAgaintsBlue1,redAgaintsBlue2;
	double value;
	final Function<Double,String> rowLabel=y -> String.format("%8.4f  ",y);
	final Function<Double,String> columnLabel=x -> String.format("%8.4f  ",x);
	final Function<Double,String> formatter=x -> String.format("%8.4f  ",x);
}
