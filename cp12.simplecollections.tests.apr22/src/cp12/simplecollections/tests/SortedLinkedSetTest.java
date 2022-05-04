package cp12.simplecollections.tests;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import cp12.simplecollections.ListNode;
import cp12.simplecollections.SimpleCollection;
import cp12.simplecollections.level1.SortedLinkedSet;

/**
 * @author Mr. Hapke
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SortedLinkedSetTest extends SimpleIndexedCollectionTest {

	public SortedLinkedSetTest() {
		super(false, true);
	}

	@Override
	protected SimpleCollection<Integer> createCollection() {
		return new SortedLinkedSet<Integer>();
	}

	@Override
	protected SimpleCollection<String> createCollectionString() {
		return new SortedLinkedSet<String>();
	}

	@Override
	protected SimpleCollection<Double> createCollectionDouble() {
		return new SortedLinkedSet<Double>();
	}

	@Override
	protected <T extends Comparable<T>> void scan(SimpleCollection<T> structure) {
		SortedLinkedSet<T> list = (SortedLinkedSet<T>) structure;
		ListNode<T> target = list.getHead();
//		ListNode<T> prevShouldBe = null;

		T max = null;

		while (target != null) {
			T data = target.getData();
			assertNotNull(data);
			if (max != null) {
				assertTrue(data.compareTo(max) >= 0);
			}
			max = data;

//			assertEquals(prevShouldBe, target.getPrev());

//			prevShouldBe = target;
			target = target.getNext();
		}

//		assertEquals(prevShouldBe, list.getTail());
	}

	@Override
	@Test
	public void testFindIndex() {
		addDefaults(false);
		assertEquals(0, b.findIndex(22));
		assertEquals(1, b.findIndex(37));
		assertEquals(2, b.findIndex(41));
		assertEquals(3, b.findIndex(45));
		assertEquals(4, b.findIndex(65));
		assertEquals(5, b.findIndex(81));

		assertEquals(-1, b.findIndex(-37));
	}

	@Test
	public void testTraverseForward() {
		addDefaults(false);
		SortedLinkedSet<Integer> sdll = (SortedLinkedSet<Integer>) a;
		String f = sdll.traverseForward();
		assertEquals("22,37,41,45,65,81", f);
	}

	@Override
	@Test
	public void testFindIndexFromIndex() {
		addDefaults(false);
		assertEquals(2, b.findIndex(41));
		assertEquals(2, b.findIndex(41, 2));
		assertEquals(-1, b.findIndex(41, 3));
	}

	@Override
	@Test
	public void testFindIndexWithRemoval() {
		addDefaults(false);
		assertEquals(4, b.findIndex(65));
		assertEquals(5, b.findIndex(81));
		scan(b);

		b.remove(65);

		assertEquals(-1, b.findIndex(65));
		assertEquals(4, b.findIndex(81));
		scan(b);
	}

	@Test
	public void testRemoveOnly() {
		SortedLinkedSet<Integer> sdll = (SortedLinkedSet<Integer>) a;
		assertEquals(0, a.size());
		assertNull(sdll.getHead());
//		assertNull(sdll.getTail());
		scan(sdll);

		a.add(43);
		assertEquals(1, a.size());
		assertNotNull(sdll.getHead());
//		assertNotNull(sdll.getTail());
		scan(sdll);

		a.remove(43);
		assertEquals(0, a.size());
		assertNull(sdll.getHead());
//		assertNull(sdll.getTail());
		scan(sdll);
	}

	@Override
	@Test
	public void testClear() {

		addDefaults(false);
		SortedLinkedSet<Integer> sdll = (SortedLinkedSet<Integer>) a;
		assertNotNull(sdll.getHead());
//		assertNotNull(sdll.getTail());

		a.clear();
		assertNull(sdll.getHead());
//		assertNull(sdll.getTail());
	}

	@Override
	@Test
	public void testGet() {
		addDefaults(false);
		acceptEither(b.get(0).intValue(), 22, 81);
		acceptEither(b.get(1).intValue(), 37, 65);
		acceptEither(b.get(2).intValue(), 41, 45);
		acceptEither(b.get(3).intValue(), 45, 41);
		acceptEither(b.get(4).intValue(), 65, 37);
		acceptEither(b.get(5).intValue(), 81, 22);
	}
}
