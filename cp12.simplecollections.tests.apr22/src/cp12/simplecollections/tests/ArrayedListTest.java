package cp12.simplecollections.tests;

import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import cp12.simplecollections.SimpleCollection;
import cp12.simplecollections.level1.ArrayedList;

/**
 * @author Mr. Hapke
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ArrayedListTest extends SimpleIndexedCollectionTest {
	public ArrayedListTest() {
		super(true, true);
	}

	@Override
	protected SimpleCollection<Integer> createCollection() {
		return new ArrayedList<Integer>(Integer.class);
	}

	@Override
	protected SimpleCollection<String> createCollectionString() {
		return new ArrayedList<String>(String.class);
	}

	@Override
	protected SimpleCollection<Double> createCollectionDouble() {
		return new ArrayedList<Double>(Double.class);
	}

	@Override
	protected <T extends Comparable<T>> void scan(SimpleCollection<T> structure) {
		ArrayedList<T> list = (ArrayedList<T>) structure;
		int size = structure.size();

		for (int i = 0; i < size; i++)
			assertNotNull(list.get(i));
		
		assertNull(list.get(size));
	}
}
