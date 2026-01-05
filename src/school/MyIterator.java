package school;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.UnaryOperator;
class MyIterator<T> implements Iterator<T> {
	public MyIterator(T tMin,T tMax,Comparator<T> comparator,UnaryOperator<T> advance) {
		this.tMin=t=tMin;
		this.tMax=tMax;
		this.comparator=comparator;
		this.advance=advance;
		rewind();
	}
	//http://stackoverflow.com/questions/529085/how-to-create-a-generic-array-in-java
	public void toArray(T[] ts) {
		for(int i=0;i<ts.length&&hasNext();i++)
			ts[i]=next();
	}
	public List<T> toList() {
		ArrayList<T> list=new ArrayList<>(size());
		while (hasNext())
			list.add(next());
		return list;
	}
	public void rewind() {
		t=tMin;
		overflow=false;
	}
	public MyIterator(MyIterator<T> other) {
		this(other.tMin,other.tMax,other.comparator,other.advance);
	}
	public int size() {
		rewind();
		int n;
		for(n=0;hasNext();next(),n++)
			;
		return n;
	}
	@Override public boolean hasNext() {
		if(!overflow) if(comparator.compare(tMin,t)<=0) return comparator.compare(t,tMax)<=0;
		return false;
	}
	@Override public T next() {
		T rc=t;
		try {
			t=advance.apply(t);
		} catch(ArithmeticException e) {
			overflow=true;
		}
		return rc;
	}
	public static byte addExact(byte x,byte y) { // underflow?
		byte r=(byte)(x+y);
		if(((x^r)&(y^r))<0) throw new ArithmeticException("integer overflow");
		return r;
	}
	public static <T>Iterable<T> outOf(final Iterable<? extends T> iterable) {
		return new Iterable<T>() {
			@Override public Iterator<T> iterator() {
				return iteratorOutOf(iterable.iterator());
			}
		};
	}
	static <T>Iterator<T> iteratorOutOf(final Iterator<? extends T> iterator) {
		return new Iterator<T>() {
			@Override public boolean hasNext() {
				return iterator.hasNext();
			}
			@Override public T next() {
				return iterator.next();
			}
			@Override public void remove() {
				iterator.remove();
			}
		};
	}
	static <T>void print(Iterator<T> iterator) {
		while (iterator.hasNext())
			System.out.print(iterator.next()+" ");
		System.out.println();
	}
	public static void main(String[] args) {}
	T t;
	boolean overflow;
	final UnaryOperator<T> advance;
	final Comparator<T> comparator;
	final T tMin,tMax;
}
