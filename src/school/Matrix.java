package school;
import static java.lang.Math.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
class Loop {
	public Loop(double min,double max,double delta) {
		this.min=min;
		this.max=max;
		this.delta=delta;
	}
	public double[] toArray() {
		double[] xs=new double[size()];
		int i=0;
		for(double x=min;x<=max;x+=delta,i++)
			xs[i]=x;
		return xs;
	}
	@Override public String toString() {
		return ("("+min+","+max+","+delta+")");
	}
	int size() {
		int n=0;
		for(double x=min;x<=max;x+=delta)
			n++;
		return n;
	}
	double min,max,delta;
}
class Super {
	Super(double[] x,double y[],double[][] payoffMatrix) {
		this.rows=y.length;
		this.columns=x.length;
		xs=new double[columns];
		ys=new double[rows];
		System.arraycopy(x,0,xs,0,x.length);
		System.arraycopy(y,0,ys,0,y.length);
		this.payoffMatrix=new double[rows][columns];
		for(int i=0;i<rows;i++)
			for(int j=0;j<columns;j++)
				this.payoffMatrix[i][j]=payoffMatrix[i][j];
		colMaxes=new double[columns];
		rowMins=new double[rows];
		colMaxIndices=new int[columns];
		rowMinIndices=new int[rows];
	}
	Super(double[] x,double y[],BinaryOperator<Double> payoff) {
		this.rows=y.length;
		this.columns=x.length;
		xs=new double[columns];
		ys=new double[rows];
		System.arraycopy(x,0,xs,0,x.length);
		System.arraycopy(y,0,ys,0,y.length);
		payoffMatrix=new double[rows][columns];
		colMaxes=new double[columns];
		rowMins=new double[rows];
		colMaxIndices=new int[columns];
		rowMinIndices=new int[rows];
		Strategy.generate(x,y,payoff,payoffMatrix);
	}
	Super(int rows,int columns) {
		this.rows=rows;
		this.columns=columns;
		xs=new double[columns];
		ys=new double[rows];
		payoffMatrix=new double[rows][columns];
		colMaxes=new double[columns];
		rowMins=new double[rows];
		colMaxIndices=new int[columns];
		rowMinIndices=new int[rows];
	}
	void minMax() {
		double min=Double.MAX_VALUE;
		double max=-Double.MAX_VALUE;
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
			maxMin=max(maxMin,rowMin);
		}
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
			minMax=min(minMax,colMax);
		}
	}
	void printMatrix() {
		for(int i=0;i<rows;i++) {
			for(int j=0;j<columns;j++)
				System.out.print(formatter.apply(payoffMatrix[i][j])+" ");
			System.out.println();
		}
	}
	void print() {
		print(rowLabel,columnLabel,formatter);
	}
	String toLongString() {
		StringBuffer sb=new StringBuffer(rows*columns*20);
		for(int i=0;i<rows;i++)
			for(int j=0;j<columns;j++)
				sb.append(""+xs[j]+" "+ys[i]+" "+payoffMatrix[i][j]+"\n");
		return sb.toString();
	}
	void print(Function<Double,String> rowLabel,Function<Double,String> columnLabel,Function<Double,String> formatter) {
		System.out.println("                    Red Strategy");
		System.out.print("          ");
		for(int j=0;j<columns;j++)
			System.out.print(columnLabel.apply(xs[j]));
		System.out.print("  rowMins    index");
		System.out.println();
		System.out.println("  Blue");
		for(int i=0;i<rows;i++) {
			System.out.print(rowLabel.apply(ys[i]));
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
		System.out.println("Red minMax: "+minMax+", Blue maxMin: "+maxMin);
		System.out.printf("payoff range is from %8.4f to %8.4f\n",min,max);
	}
	final int rows,columns;
	final double[][] payoffMatrix;
	final double[] xs,ys;
	final double[] rowMins,colMaxes;
	final int[] rowMinIndices,colMaxIndices;
	double minMax,maxMin;
	double min,max;
	Function<Double,String> rowLabel=y -> String.format("%8.4f  ",y);
	Function<Double,String> columnLabel=x -> String.format("%8.4f  ",x);
	Function<Double,String> formatter=x -> String.format("%8.4f  ",x);
}
class Matrix extends Super {
	Matrix(double[] x,double y[],BinaryOperator<Double> payoff) {
		super(x,y,payoff);
		minMax();
	}
	Matrix(Loop x,Loop y,BinaryOperator<Double> payoff) {
		super(x.toArray(),y.toArray(),payoff);
		minMax();
	}
	Matrix(MyIterator<Double> iX,MyIterator<Double> iY,BinaryOperator<Double> payoff) {
		super(iY.size(),iX.size());
		generatePayoffMatrix(iX,iY,payoff,payoffMatrix,xs,ys);
		minMax();
	}
	private static void generatePayoffMatrix(MyIterator<Double> iX,MyIterator<Double> iY,BinaryOperator<Double> payoff,double[][] payoffMatrix,double[] xs,
			double[] ys) {
		iY.rewind();
		for(int i=0;iY.hasNext();i++) {
			double y=iY.next();
			ys[i]=y;
			iX.rewind();
			for(int j=0;iX.hasNext();j++) {
				double x=iX.next();
				if(i==0) xs[j]=x;
				payoffMatrix[i][j]=payoff.apply(x,y);
			}
		}
	}
	public static void main(String[] args) {
		int outs=9,remaining=52-5; // was 25-4
		double p=outs/(double)remaining;
		if(false) {
			E e=new E(p,0,1);
			System.out.println(e);
			MyIterator<Double> iX=new MyIterator<Double>(0.,1.,(x,y) -> x.compareTo(y),x -> x+.2);
			MyIterator<Double> iY=new MyIterator<Double>(0.,1.,(x,y) -> x.compareTo(y),y -> y+.2);
			BinaryOperator<Double> binaryOperator=(x,y) -> e.e2(x,y);
			binaryOperator=(x,y) -> e.e2(x,y);
			Matrix minMax=new Matrix(iX,iY,binaryOperator);
			// minMax.printMatrix();
			minMax.print();
		} else {
			E e=new E(p,.1,1);
			System.out.println(e);
			Loop xLoop=new Loop(0,1,.2);
			Loop yLoop=new Loop(0,1,.2);
			BinaryOperator<Double> binaryOperator=(x,y) -> e.e2(x,y);
			Matrix minMax=new Matrix(xLoop,yLoop,binaryOperator);
			minMax.print();
			// com.tayek.utilities.Utilities.toFile(minMax.toLongString(),new
			// File(new File("plot"),"1.dat"));
			System.out.println("--------------------");
			System.out.println(e);
			Strategy strategy=new Strategy(minMax.payoffMatrix);
			strategy.print();
			strategy.printInfo();
			System.out.println("---------");
			System.out.println("pot of "+e.pot+" is offerring red "+(e.pot)+":1.0");
			System.out.println("a bet of "+e.bet+" is offering blue "+(e.pot+e.bet)+":"+e.bet);
			double pRed1=school.E.p1(strategy.red1Oddment,strategy.red2Oddment);
			double pBlue1=school.E.p1(strategy.blue1Oddment,strategy.blue2Oddment);
			System.out.println("pRed1="+pRed1+", pBleu1="+pBlue1);
			if(!(Double.isNaN(pRed1)||Double.isNaN(pBlue1))) 
				System.out.println("e2(pRed1,pBlue1)="+e.e2(pRed1,pBlue1));
			else 
				System.out.println("e2(pRed1,pBlue1)="+Double.NaN);
			System.out.println("---------");
			//school.E.strategy(0.8125,0.,0.6250,1.4375);
		}
	}
}
