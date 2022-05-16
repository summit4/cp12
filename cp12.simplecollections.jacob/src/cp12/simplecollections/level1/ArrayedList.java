package cp12.simplecollections.level1;

import java.lang.reflect.Array;

import cp12.simplecollections.SimpleIndexedCollection;

public class ArrayedList<T extends Comparable<T>> extends SimpleIndexedCollection<T> {
	
	private Class<T> cls;
	private T[] data;
	
	
	private int qty = data.length;
	private boolean foundIT;

	public ArrayedList(Class<T> cls) {
	this.cls = cls;
	data = (T[]) Array.newInstance(cls, 10);
	//10 is capacity
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return qty;
	}

	@Override
	public boolean isEmpty() {
		if (qty == 0) {return true;}
		else {return false;}
	}

	@Override
	public boolean contains(T input) {
		
		for (int i = 0; i < qty; i++) {
			if (data[i].equals(input)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean add(T input) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean remove(T input) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public T maximum() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int findIndex(T input, int n) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public T get(int n) {
		// TODO Auto-generated method stub
		return null;
	}

}
