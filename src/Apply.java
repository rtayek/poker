import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
public class Apply {
    static <X,Y> Collection<Y> map(Collection<X> xs,Function<X,Y> map) {
        ArrayList<Y> ys=new ArrayList<>();
        for(X x:xs)
            ys.add(map.apply(x));
        return ys;
    }
    static <X> Collection<X> filter(Collection<X> xs,Function<X,Boolean> filter) {
        ArrayList<X> filtered=new ArrayList<>();
        for(X x:xs)
            if(filter.apply(x)) filtered.add(x);
        return filtered;
    }
    /*
    static <X,Y> Collection<X> Both(Collection<X> xs,Function<X,Boolean> filter,Function<X,Y> map) {
        if(map==null) return
        Collection<X> filtered=null;
        if(filter!=null) {
            filtered=filter(xs,filter);
            return filtered;
        }
            return Apply.map(filtered,map)
        for(X x:xs)
            if(filter==null||filter.apply(x)) {
                both.add(map.apply(x));
            }
        return filtered;
    }
    */
    public static void main(String[] args) {
        Collection<Integer> xs=Arrays.asList(new Integer[] {1,2,3});
        System.out.println(xs);
        Collection<Integer> filtered=filter(xs,(Integer x)->x%2!=0);
        System.out.println(filtered);
        Collection<String> ys=map(filtered,(Integer x)->"a"+x);
        System.out.println(ys);
    }
}
