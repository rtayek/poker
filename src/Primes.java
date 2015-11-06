import java.util.*;
public class Primes {
	public static void main(String[] arguments) {
		final long n=2147483647/10;//1l<<32;
		final long limit=(long)Math.floor(Math.sqrt(n));
		System.out.println(Integer.MAX_VALUE);
		System.out.println(n);
		Map<Long,Boolean> map=new TreeMap<Long,Boolean>();
		sieve2(n,limit,map);
		SortedSet<Long> primes=new TreeSet<Long>(); 
		for(long i:map.keySet())
			if(map.get(i))
				primes.add(i);
		System.out.println(primes.size()+" primes");
		//System.out.println(primes);
	}
	private static void sieve2(final long n,final long limit,Map<Long,Boolean> map) {
		map.put(2l,true);
		for(long i=3;i<=n;i+=2)
			map.put(i,true);
		for(long i=3;i<=limit;i+=2) {
			System.out.println(i);
			if(map.get(i))
				for(long j=i*i;j<=n;j+=i)
					map.put(j,false);
		}
	}

	private static void sieve1(final long n,final long limit,Map<Long,Boolean> map) {
		for(long i=2;i<=n;i++)
			map.put(i,true);
		for(long i=2;i<=limit;i++) {
			System.out.println(i);
			if(map.get(i))
				for(long j=i*i;j<=n;j+=i)
					map.put(j,false);
		}
	}
}
