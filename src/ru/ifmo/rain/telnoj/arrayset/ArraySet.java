package ru.ifmo.rain.telnoj.arrayset;

import java.util.*;

public class ArraySet<T extends Comparable<T>> extends AbstractSet<T> implements NavigableSet<T> {
    private NavigableSet<T> set;
    private Comparator<? super T> comparator;

    public ArraySet() {
        set = new TreeSet<>();
        comparator = null;
    }

    public ArraySet(Collection<? extends T> collection) {
        this(collection, null);
    }

    public ArraySet(Collection<? extends T> collection, Comparator<? super T> comparator) {
        set = new TreeSet<>(comparator);
        set.addAll(collection);
        this.comparator = comparator;
    }

    public ArraySet(Comparator<? super T> comparator, SortedSet<T> set) {
        this.set = (NavigableSet<T>) set;
        this.comparator = comparator;
    }

    @Override
    public T lower(T t) {
        return set.lower(t);
    }

    @Override
    public T floor(T t) {
        return set.floor(t);
    }

    @Override
    public T ceiling(T t) {
        return set.ceiling(t);
    }

    @Override
    public boolean contains(Object o) {
        return set.contains(o);
    }

    @Override
    public T higher(T t) {
        return set.higher(t);
    }

    @Override
    public T pollFirst() {
        throw new UnsupportedOperationException();
    }

    @Override
    public T pollLast() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            Iterator<T> iterator = set.iterator();

            public boolean hasNext() {
                return iterator.hasNext();
            }

            public T next() {
                return iterator.next();
            }
        };
    }

    @Override
    public ArraySet<T> descendingSet() {
        return new ArraySet<>(comparator, set.descendingSet());
    }

    @Override
    public Iterator<T> descendingIterator() {
        return descendingSet().iterator();
    }

    private ArraySet<T> derivedSet(SortedSet<T> set) {
        return new ArraySet<>(comparator, set);
    }

    @Override
    public ArraySet<T> subSet(T fromElement, boolean fromInclusive, T toElement, boolean toInclusive) {
        return derivedSet(set.subSet(fromElement, fromInclusive, toElement, toInclusive));
    }

    @Override
    public ArraySet<T> headSet(T toElement, boolean inclusive) {
        return derivedSet(set.headSet(toElement, inclusive));
    }

    @Override
    public ArraySet<T> tailSet(T fromElement, boolean inclusive) {
        return derivedSet(set.tailSet(fromElement, inclusive));
    }

    @Override
    public Comparator<? super T> comparator() {
        return comparator;
    }

    @Override
    public ArraySet<T> subSet(T fromElement, T toElement) {
        return derivedSet(set.subSet(fromElement, toElement));
    }

    @Override
    public ArraySet<T> headSet(T toElement) {
        return derivedSet(set.headSet(toElement));
    }

    @Override
    public ArraySet<T> tailSet(T fromElement) {
        return derivedSet(set.tailSet(fromElement));
    }

    @Override
    public T first() {
        return set.first();
    }

    @Override
    public T last() {
        return set.last();
    }

    @Override
    public int size() {
        return set.size();
    }
}
