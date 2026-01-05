#include <memory>
#include <string>
#include <algorithm>
#include <functional>
#include <function>
#include <vector>
#include <iterator>
using namespace std;

unique_ptr<int> liftedLength(unique_ptr<string> s) {
    //return unique_ptr<int>(new int(string::length(*s)));
    return unique_ptr<int>(new int(s->length()));
}

unique_ptr<string> p(new string(" Hi! "));
//auto np = fmap<string, int>(&length,fmap<string, string>(&trim, move(p)));

template<class A, class B>
unique_ptr<B> fmap(function<B(A)> f, unique_ptr<A> a) {
    return unique_ptr<B>(new B(f(*a)));
}

vector<int> liftedLength(vector<string> strings) {
    unsigned size = strings.size();
    vector<int> lengths(size);
    for (unsigned i = 0; i < size; ++i)
        lengths[i] = strings[i].length();
    return lengths;
}

vector<int> liftedLength(vector<string> strings) {
    vector<int> lengths;
    transform(strings.begin(), strings.end(), back_inserter(lengths), &length);
    return lengths;
}

template<class A, class B>
vector<B> fmap(function<B(A)> f, vector<A> as) {
    vector<B> bs;
    transform(as.begin(), as.end(), back_inserter(bs), f);
    return bs;
}