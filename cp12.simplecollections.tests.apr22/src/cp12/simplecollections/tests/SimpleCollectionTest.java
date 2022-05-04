package cp12.simplecollections.tests;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import cp12.simplecollections.SimpleCollection;
import junit.framework.TestCase;

/**
 * @author Mr. Hapke
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public abstract class SimpleCollectionTest extends TestCase {

	protected SimpleCollection<Integer> a;
	private boolean reverseOrder;
	private boolean firstOrderCheck;
	private final boolean allowsDuplicates;
	private boolean enforceDuplicateAddReturnsFalse;

	protected abstract SimpleCollection<Integer> createCollection();

	protected abstract SimpleCollection<String> createCollectionString();

	protected abstract SimpleCollection<Double> createCollectionDouble();

	public SimpleCollectionTest(boolean allowsDuplicates, boolean enforceDuplicateAddReturnsFalse) {
		this.allowsDuplicates = allowsDuplicates;
		this.enforceDuplicateAddReturnsFalse = enforceDuplicateAddReturnsFalse;
	}

	@Override
	protected void setUp() throws Exception {
		firstOrderCheck = false;
		a = createCollection();
	}

	protected abstract <T extends Comparable<T>> void scan(SimpleCollection<T> structure);

	protected void addDefaults(boolean testAsYouGo) {
		boolean changed;
		if (testAsYouGo) {
			assertEquals(0, a.size());
		}

		changed = a.add(45);
		if (testAsYouGo) {
			assertTrue(changed);
			scan(a);
			assertEquals(1, a.size());
		}

		changed = a.add(37);
		if (testAsYouGo) {
			assertTrue(changed);
			scan(a);
			assertEquals(2, a.size());
		}

		changed = a.add(22);
		if (testAsYouGo) {
			assertTrue(changed);
			scan(a);
			assertEquals(3, a.size());
		}

		changed = a.add(41);
		if (testAsYouGo) {
			assertTrue(changed);
			scan(a);
			assertEquals(4, a.size());
		}

		changed = a.add(65);
		if (testAsYouGo) {
			assertTrue(changed);
			scan(a);
			assertEquals(5, a.size());
		}

		changed = a.add(81);
		if (testAsYouGo) {
			assertTrue(changed);
			scan(a);
			assertEquals(6, a.size());
		}

	}

	@Override
	protected void tearDown() throws Exception {
		a = null;
	}

	@Test
	public void testRemove() {
		addDefaults(false);
		assertEquals(6, a.size());
		assertTrue(a.contains(45));
		assertTrue(a.contains(37));
		assertTrue(a.contains(22));
		assertTrue(a.contains(41));
		assertTrue(a.contains(65));
		assertTrue(a.contains(81));

		a.remove(37);

		scan(a);
		assertEquals(5, a.size());
		assertTrue(a.contains(45));
		assertFalse(a.contains(37));
		assertTrue(a.contains(22));
		assertTrue(a.contains(41));
		assertTrue(a.contains(65));
		assertTrue(a.contains(81));

		a.remove(65);

		scan(a);
		assertEquals(4, a.size());
		assertTrue(a.contains(45));
		assertFalse(a.contains(37));
		assertTrue(a.contains(22));
		assertTrue(a.contains(41));
		assertFalse(a.contains(65));
		assertTrue(a.contains(81));

		a.remove(22);

		scan(a);
		assertEquals(3, a.size());
		assertTrue(a.contains(45));
		assertFalse(a.contains(37));
		assertFalse(a.contains(22));
		assertTrue(a.contains(41));
		assertFalse(a.contains(65));
		assertTrue(a.contains(81));
	}


	@Test
	public void testRemoveNotFound() {
		addDefaults(false);
		assertEquals(6, a.size());
		boolean found = a.remove(123);
		assertFalse(found);
		assertEquals(6, a.size());
	}
	
	@Test
	public void testRemoveFirstOfDuplicates() {
		if (allowsDuplicates) {
			// this test should only be run on collections that allow duplicates
			a.add(1);

			a.add(2);
			a.add(2);

			a.add(3);
			a.add(3);
			a.add(3);

			scan(a);

			assertEquals(6, a.size());
			assertTrue(a.contains(2));
			a.remove(2);
			assertEquals(5, a.size());
			assertTrue(a.contains(2));
			scan(a);

			a.remove(2);
			assertEquals(4, a.size());
			assertFalse(a.contains(2));
			scan(a);
			
			

			assertTrue(a.contains(3));
			a.remove(3);
			assertEquals(3, a.size());
			assertTrue(a.contains(3));
			scan(a);

			assertTrue(a.contains(3));
			a.remove(3);
			assertEquals(2, a.size());
			assertTrue(a.contains(3));
			scan(a);

			a.remove(3);
			assertEquals(1, a.size());
			assertFalse(a.contains(3));
			scan(a);
		}
	}

	@Test
	/**
	 * Removes the least recently added element from the list
	 */
	public void testRemoveOldest() {
		addDefaults(false);
		assertEquals(6, a.size());
		assertTrue(a.contains(45));
		assertTrue(a.contains(37));
		assertTrue(a.contains(22));
		assertTrue(a.contains(41));
		assertTrue(a.contains(65));
		assertTrue(a.contains(81));

		a.remove(45);

		scan(a);
		assertEquals(5, a.size());
		assertFalse(a.contains(45));
		assertTrue(a.contains(37));
		assertTrue(a.contains(22));
		assertTrue(a.contains(41));
		assertTrue(a.contains(65));
		assertTrue(a.contains(81));
	}

	@Test
	/**
	 * Removes the most recently added element from the list
	 */
	public void testRemoveNewest() {
		addDefaults(false);
		scan(a);
		assertEquals(6, a.size());
		assertTrue(a.contains(45));
		assertTrue(a.contains(37));
		assertTrue(a.contains(22));
		assertTrue(a.contains(41));
		assertTrue(a.contains(65));
		assertTrue(a.contains(81));

		a.remove(81);

		scan(a);
		assertEquals(5, a.size());
		assertTrue(a.contains(45));
		assertTrue(a.contains(37));
		assertTrue(a.contains(22));
		assertTrue(a.contains(41));
		assertTrue(a.contains(65));
		assertFalse(a.contains(81));
	}

	@Test
	public void testContains() {
		addDefaults(false);
		assertTrue(a.contains(45));
		assertTrue(a.contains(37));
		assertTrue(a.contains(81));
		assertFalse(a.contains(43));
		assertFalse(a.contains(0));
		assertFalse(a.contains(-12));
		assertFalse(a.contains(900));
		assertFalse(a.contains(55));
	}

	@Test
	public void testContainsWithStrings() {
		SimpleCollection<String> b = createCollectionString();
		b.add("Y");
		b.add("HELO");
		b.add("THAR");
		b.add("(i'm a pirate)");
		assertEquals(4, b.size());

		// in no particular order
		assertTrue(b.contains("HELO"));
		assertTrue(b.contains("(i'm a pirate)"));
		assertTrue(b.contains("Y"));
		assertFalse(b.contains("walk the plank, scoundrel"));
		assertTrue(b.contains("THAR"));
	}

	@Test
	public void testMaximumStrings() {
		SimpleCollection<String> b = createCollectionString();
		assertNull(b.maximum());

		b.add("Y");
		b.add("HELO");
		b.add("THAR");
		b.add("(i'm a pirate)");
		assertEquals("Y", b.maximum());
	}

	@Test
	public void testMaximumStrings_TestingAsYouAddMore() {
		SimpleCollection<String> b = createCollectionString();
		assertNull(b.maximum());

		b.add("(i'm a pirate)");
		assertEquals("(i'm a pirate)", b.maximum());
		b.add("HELO");
		assertEquals("HELO", b.maximum());
		b.add("Y");
		assertEquals("Y", b.maximum());
		b.add("THAR");
		assertEquals("Y", b.maximum());
	}

	@Test
	public void testSize() {
		addDefaults(false);
		assertEquals(6, a.size());
	}

	@Test
	public void testAdd() {
		addDefaults(true);
		scan(a);
		assertEquals(6, a.size());

		a.add(38);
		scan(a);
		assertEquals(7, a.size());

		a.add(46);
		scan(a);
		assertEquals(8, a.size());

		a.add(67);
		scan(a);
		assertEquals(9, a.size());

		a.add(901);
		scan(a);
		assertEquals(10, a.size());
	}

	@Test
	public void testAdd100Entries() {
		for (int i = 0; i < 100; i++) {
			a.add(i);
			scan(a);
			assertEquals(i + 1, a.size());
		}
	}

	public void testAddAndContains() {
		assertFalse(a.contains(45));
		a.add(45);
		scan(a);
		assertTrue(a.contains(45));

		assertFalse(a.contains(37));
		a.add(37);
		scan(a);
		assertTrue(a.contains(37));

		assertFalse(a.contains(22));
		a.add(22);
		scan(a);
		assertTrue(a.contains(22));

		assertFalse(a.contains(41));
		a.add(41);
		scan(a);
		assertTrue(a.contains(41));

		assertFalse(a.contains(65));
		a.add(65);
		scan(a);
		assertTrue(a.contains(65));

		assertFalse(a.contains(81));
		a.add(81);
		scan(a);
		assertTrue(a.contains(81));

	}

	@Test
	public void testAddDuplicates() {
		addDefaults(true);
		scan(a);
		int expectedSize = 6;
		assertEquals(expectedSize, a.size());

		boolean changed;
		changed = a.add(45);
		if (allowsDuplicates)
			expectedSize++;
		assertEquals(expectedSize, a.size());
		if (enforceDuplicateAddReturnsFalse)
			assertEquals(allowsDuplicates, changed);

		changed = a.add(37);
		if (allowsDuplicates)
			expectedSize++;
		assertEquals(expectedSize, a.size());
		if (enforceDuplicateAddReturnsFalse)
			assertEquals(allowsDuplicates, changed);

		changed = a.add(22);
		if (allowsDuplicates)
			expectedSize++;
		assertEquals(expectedSize, a.size());
		if (enforceDuplicateAddReturnsFalse)
			assertEquals(allowsDuplicates, changed);

		changed = a.add(41);
		if (allowsDuplicates)
			expectedSize++;
		assertEquals(expectedSize, a.size());
		if (enforceDuplicateAddReturnsFalse)
			assertEquals(allowsDuplicates, changed);

		changed = a.add(65);
		if (allowsDuplicates)
			expectedSize++;
		assertEquals(expectedSize, a.size());
		if (enforceDuplicateAddReturnsFalse)
			assertEquals(allowsDuplicates, changed);

		changed = a.add(81);
		if (allowsDuplicates)
			expectedSize++;
		assertEquals(expectedSize, a.size());
		if (enforceDuplicateAddReturnsFalse)
			assertEquals(allowsDuplicates, changed);
		scan(a);
	}

	@Test
	public void testAddWithDecimals() {
		SimpleCollection<Double> d = createCollectionDouble();
		assertEquals(0, d.size());
		d.add(32d);
		scan(d);
		assertEquals(1, d.size());

		d.add(28d);
		scan(d);
		assertEquals(2, d.size());

		d.add(33d);
		scan(d);
		assertEquals(3, d.size());

		d.add(1d);
		scan(d);
		assertEquals(4, d.size());

		d.add(3d);
		assertEquals(5, d.size());
		scan(d);

		d.add(6d);
		scan(d);
		assertEquals(6, d.size());

		d.add(29d);
		scan(d);
		assertEquals(7, d.size());

		d.add(28.5);
		scan(d);
		assertEquals(8, d.size());
	}

	@Test
	public void testClear() {
		addDefaults(false);

		assertTrue(a.contains(45));
		assertTrue(a.contains(37));
		assertTrue(a.contains(45));
		assertFalse(a.contains(43));
		assertFalse(a.contains(0));
		assertFalse(a.contains(-12));
		assertFalse(a.contains(900));
		assertFalse(a.contains(55));

		assertEquals(6, a.size());
		a.clear();
		assertEquals(0, a.size());

		assertFalse(a.contains(45));
		assertFalse(a.contains(37));
		assertFalse(a.contains(45));
		assertFalse(a.contains(43));
		assertFalse(a.contains(0));
		assertFalse(a.contains(-12));
		assertFalse(a.contains(900));
		assertFalse(a.contains(55));

		// clear an already empty one
		a.clear();
		assertEquals(0, a.size());

		assertFalse(a.contains(45));
		assertFalse(a.contains(37));
		assertFalse(a.contains(45));
		assertFalse(a.contains(43));
		assertFalse(a.contains(0));
		assertFalse(a.contains(-12));
		assertFalse(a.contains(900));
		assertFalse(a.contains(55));
	}

	@Test
	public void testMaximumDefaults() {
		addDefaults(false);
		assertEquals(81, a.maximum().intValue());
	}

	@Test
	public void testMaximum_TestingAsYouAddMore() {
		assertNull(a.maximum());
		a.add(15);
		assertEquals(15, a.maximum().intValue());
		a.add(-1);
		assertEquals(15, a.maximum().intValue());
		a.add(50);
		assertEquals(50, a.maximum().intValue());
		a.add(83);
		assertEquals(83, a.maximum().intValue());
		a.add(12);
		assertEquals(83, a.maximum().intValue());
		a.add(16);
		assertEquals(83, a.maximum().intValue());
		a.add(14);
		assertEquals(83, a.maximum().intValue());
	}
	
	@Test
	public void testMaximumEmptyIsNull() {
		assertNull(a.maximum());
		a.add(15);
		assertEquals(15, a.maximum().intValue());
		
		a.remove(15);

		assertNull(a.maximum());
	}

	@Test
	public void testIsEmpty() {
		assertTrue(a.isEmpty());
		addDefaults(false);
		assertFalse(a.isEmpty());
	}

	/*-
	 * Uses firstOrderCheck and reverseOrder to make sure the order of your elements
	 * is in a consistent order.
	 * (ie: newest->oldest, or oldest->newest
	 */
	protected void acceptEither(int i, int forwards, int backwards) {
		if (!firstOrderCheck) {
			if (i == forwards) {
				reverseOrder = false;
				return;
			} else if (i == backwards) {
				reverseOrder = true;
				return;
			} else {
				fail("Did not get either " + forwards + " or " + backwards);
			}
			firstOrderCheck = true;
		} else {
			if (reverseOrder)
				assertEquals(backwards, i);
			else
				assertEquals(forwards, i);
		}
	}

}
