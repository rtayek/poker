package experiment;

import java.util.Arrays;

class I {
	I(int i) {
		this.i=i;
	}
	@Override public String toString() {
		return ""+i;
	}
	int i;
}
public class Sort5 {
	static void swap(I x,I y) {
		int temp=x.i;
		x.i=y.i;
		y.i=temp;
	}
	static I[] sort(I A,I B,I C,I D,I E) {
		if (A.i>B.i) swap(A,B);
		if (C.i>D.i) swap(C,D);
		if (A.i>C.i) swap(A,C);
		swap(B,D);
		if (E.i>C.i) if (E.i>D.i) // A C D E
		if (B.i>D.i) if (B.i>E.i) return new I[] {A,C,D,E,B};
		else return new I[] {A,C,D,B,E};
		else if (B.i<C.i) return new I[] {A,B,C,D,E};
		else return new I[] {A,C,B,D,E};
		else // A C E D
		if (B.i>E.i) if (B.i>D.i) return new I[] {A,C,E,D,B};
		else return new I[] {A,C,E,B,D};
		else if (B.i<C.i) return new I[] {A,B,C,E,D};
		else return new I[] {A,C,B,E,D};
		else if (E.i<A.i) // E A C D
		if (B.i>C.i) if (B.i>D.i) return new I[] {E,A,C,D,B};
		else return new I[] {E,A,C,B,D};
		else return new I[] {E,A,B,C,D};
		else // A E C D
		if (B.i>C.i) if (B.i>D.i) return new I[] {A,E,C,D,B};
		else return new I[] {A,E,C,B,D};
		else if (B.i<E.i) return new I[] {A,B,E,C,D};
		else return new I[] {A,E,B,C,D};
	}
}
