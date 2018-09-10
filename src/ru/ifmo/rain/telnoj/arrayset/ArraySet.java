package ru.ifmo.rain.telnoj.arrayset;

import java.util.*;

public class ArraySet<T extends Comparable<T>> extends AbstractSet<T> implements NavigableSet<T> {
    private List<T> list;
    private Comparator<? super T> comparator;

    public ArraySet() {
        list = new ArrayList<>();
        comparator = null;
    }

    public ArraySet(Collection<? extends T> collection) {
        this(collection, null);
    }

    public ArraySet(Collection<? extends T> collection, Comparator<? super T> comparator) {
        TreeSet<T> set = new TreeSet<>(comparator);
        set.addAll(collection);
        list = new ArrayList<>(set);
        this.comparator = comparator;
    }

    private ArraySet(List<T> list, Comparator<? super T> comparator) {
        this.list = list;
        this.comparator = comparator;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            Iterator<T> iterator = list.iterator();

            public boolean hasNext() {
                return iterator.hasNext();
            }

            public T next() {
                return iterator.next();
            }
        };
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean contains(Object o) {
        int index = collectionsBinarySearch((T) o);
        return index >= 0;
    }

    @Override
    public T lower(T t) {
        int index = getIndex(t);
        if (index > 0) return list.get(index - 1);
        return null;
    }

    @Override
    public T floor(T t) {
        int index = getGreaterIndex(t);
        if (index == 0) return null;
        return list.get(index - 1);
    }

    @Override
    public T ceiling(T t) {
        int index = getIndex(t);
        if (index < list.size()) return list.get(index);
        return null;
    }

    @Override
    public T higher(T t) {
        int index = getGreaterIndex(t);
        if (index == list.size()) return null;
        return list.get(index);
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
    public NavigableSet<T> descendingSet() {
        ListIterator<T> iterator = this.list.listIterator(list.size());
        ArrayList<T> list = new ArrayList<>(this.list.size());
        while (iterator.hasPrevious()) {
            list.add(iterator.previous());
        }
        return new ArraySet<>(list, comparator.reversed());
    }

    @Override
    public Iterator<T> descendingIterator() {
        return descendingSet().iterator();
    }

    @Override
    public ArraySet<T> headSet(T toElement, boolean inclusive) {
        if (!inclusive) return headSet(toElement);
        return derivedHeadSet(getGreaterIndex(toElement));
    }

    @Override
    public ArraySet<T> tailSet(T fromElement, boolean inclusive) {
        if (inclusive) return tailSet(fromElement);
        return derivedTailSet(getGreaterIndex(fromElement));
    }

    @Override
    public ArraySet<T> subSet(T fromElement, boolean fromInclusive, T toElement, boolean toInclusive) {
        return headSet(toElement, toInclusive).tailSet(fromElement, fromInclusive);
    }

    @Override
    public Comparator<? super T> comparator() {
        return comparator;
    }

    @Override
    public ArraySet<T> headSet(T toElement) {
        return derivedHeadSet(getIndex(toElement));
    }

    @Override
    public ArraySet<T> tailSet(T fromElement) {
        return derivedTailSet(getIndex(fromElement));
    }

    @Override
    public ArraySet<T> subSet(T fromElement, T toElement) {
        return headSet(toElement).tailSet(fromElement);
    }

    @Override
    public T first() {
        return getElementIfNotEmpty(0);
    }

    @Override
    public T last() {
        return getElementIfNotEmpty(list.size() - 1);
    }

    @Override
    public int size() {
        return list.size();
    }

    private ArraySet<T> derivedSet(List<T> list) {
        return new ArraySet<>(list, comparator);
    }

    private int collectionsBinarySearch(T element) {
        return Collections.binarySearch(list, element, comparator);
    }

    private T getElementIfNotEmpty(int index) {
        if (list.isEmpty()) throw new NoSuchElementException();
        return list.get(index);
    }

    private int getIndex(T element) {
        int index = collectionsBinarySearch(element);
        if (index < 0) index = -index - 1;
        return index;
    }

    private int getGreaterIndex(T element) {
        int index = collectionsBinarySearch(element);
        if (index < 0) index = -index - 1;
        else index++;
        return index;
    }

    private ArraySet<T> derivedHeadSet(int index) {
        return derivedSet(list.subList(0, index));
    }

    private ArraySet<T> derivedTailSet(int index) {
        return derivedSet(list.subList(index, list.size()));
    }
}
