package cp12.simplecollections.tests;

import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import cp12.simplecollections.ListNode;
import cp12.simplecollections.SimpleCollection;
import cp12.simplecollections.SimpleLinkedList;

/**
 * @author Mr. Hapke
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SimpleLinkedListTest extends SimpleIndexedCollectionTest {
	public SimpleLinkedListTest() {
		super(true, true);
	}

	@Override
	protected SimpleCollection<Integer> createCollection() {

		return new SimpleLinkedList<Integer>();
	}

	@Override
	protected SimpleCollection<String> createCollectionString() {
		return new SimpleLinkedList<String>();
	}

	@Override
	protected SimpleCollection<Double> createCollectionDouble() {
		return new SimpleLinkedList<Double>();
	}

	@Override
	protected <T extends Comparable<T>> void scan(SimpleCollection<T> structure) {
		SimpleLinkedList<T> list = (SimpleLinkedList<T>) structure;
		ListNode<T> target = list.getHead();
		while (target != null) {
			assertNotNull(target.getData());

			target = target.getNext();
		}
	}
}
