package cp12.simplecollections.tests;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import cp12.simplecollections.SimpleCollection;
import cp12.simplecollections.level1.PriorityStack;
import cp12.simplecollections.level1.StackNode;

/**
 * @author Mr. Hapke
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PriorityStackTest extends SimpleCollectionTest {

	public PriorityStackTest() {
		super(false, false);
	}

	@Override
	protected SimpleCollection<Integer> createCollection() {
		return new PriorityStack<Integer>();
	}

	@Override
	protected SimpleCollection<String> createCollectionString() {
		return new PriorityStack<String>();
	}

	@Override
	protected SimpleCollection<Double> createCollectionDouble() {
		return new PriorityStack<Double>();
	}

	@Override
	protected void addDefaults(boolean testAsYouGo) {
		PriorityStack<Integer> stack = (PriorityStack<Integer>) a;
		boolean changed;
		if (testAsYouGo) {
			assertEquals(0, a.size());
		}

		changed = stack.add(3, 22);
		if (testAsYouGo) {
			assertTrue(changed);
			assertEquals(1, a.size());
			scan(a);
		}

		changed = stack.add(1, 41);
		if (testAsYouGo) {
			assertTrue(changed);
			assertEquals(2, a.size());
			scan(a);
		}

		changed = stack.add(2, 81);
		if (testAsYouGo) {
			assertTrue(changed);
			assertEquals(3, a.size());
			scan(a);
		}

		changed = stack.add(3, 37);
		if (testAsYouGo) {
			assertTrue(changed);
			assertEquals(4, a.size());
			scan(a);
		}

		changed = stack.add(3, 45);
		if (testAsYouGo) {
			assertTrue(changed);
			assertEquals(5, a.size());
			scan(a);
		}

		changed = stack.add(2, 65);
		if (testAsYouGo) {
			assertTrue(changed);
			assertEquals(6, a.size());
			scan(a);
		}
	}

	@Test
	public void testPop() {
		PriorityStack<Integer> stack = (PriorityStack<Integer>) a;
		addDefaults(true);
		assertEquals(22, stack.pop().intValue());
		scan(a);
		assertEquals(37, stack.pop().intValue());
		scan(a);
		assertEquals(45, stack.pop().intValue());
		scan(a);
		assertEquals(81, stack.pop().intValue());
		scan(a);
		assertEquals(65, stack.pop().intValue());
		scan(a);
		assertEquals(41, stack.pop().intValue());
		scan(a);
	}

	@Test
	public void testAddDuplicateSamePriority () {
		PriorityStack<Integer> stack = (PriorityStack<Integer>) a;
		stack.add(3, 1);
		stack.add(3, 4);
		stack.add(3, 5);
		stack.add(3, 7);
		
		scan(a);

		stack.add(3, 4);
		
		StackNode<Integer> top = stack.getTop();
		StackNode<Integer> second  = top.getNext();
		StackNode<Integer> third = second.getNext();
		StackNode<Integer> fourth = third.getNext();
		
		assertEquals(1, top.getData().intValue());
		int secondData = second.getData().intValue();
		if (secondData == 4) {
			assertEquals(5, third.getData().intValue());
			assertEquals(7, fourth.getData().intValue());
		} else if (secondData == 5) {
			assertEquals(7, third.getData().intValue());
			assertEquals(4, fourth.getData().intValue());
		} else {
			fail("Second element must be 4 or 5");
		}
		
		scan(a);
	}
	
	@Test
	public void testAddDuplicate() {
		PriorityStack<Integer> stack = (PriorityStack<Integer>) a;
		addDefaults(true);

		// find the Node with value 81
		StackNode<Integer> p2EightyOne = stack.getTop();
		while (p2EightyOne != null && p2EightyOne.getData().intValue() != 81) {
			p2EightyOne = p2EightyOne.getNext();
		}
		
		stack.add(4, 81);
		scan(a);

		StackNode<Integer> p4EightyOne = stack.getTop();
		while (p4EightyOne != null && p4EightyOne.getData().intValue() != 81) {
			p4EightyOne = p4EightyOne.getNext();
		}

		// check that the P4 node is the same one being reused, at the new priority
		assertTrue(p2EightyOne == p4EightyOne);
	}
	
	@Test
	public void testAddDefaultPriority() {
		PriorityStack<Integer> stack = (PriorityStack<Integer>) a;
		assertEquals(0, a.size());
		stack.add(7, 17);
		stack.add(7, 27);
		stack.add(7, 37);
		assertEquals(3, a.size());
		
		Integer popped = stack.pop();
		assertEquals(17, popped.intValue());
		assertEquals(2, a.size());

		stack.add(4, 44);
		stack.add(6, 56);
		stack.add(5, 65);
		assertEquals(5, a.size());

		stack.add(74);
		stack.add(84);
		stack.add(94);
		
		assertEquals(8, a.size());

		assertEquals(27, stack.pop().intValue());
		assertEquals(37, stack.pop().intValue());
		assertEquals(56, stack.pop().intValue());
		assertEquals(65, stack.pop().intValue());
		assertEquals(44, stack.pop().intValue());
		assertEquals(74, stack.pop().intValue());
		assertEquals(84, stack.pop().intValue());
		assertEquals(94, stack.pop().intValue());
		
		assertNull(stack.pop());
	}

	@Override
	@SuppressWarnings("unchecked")
	protected <T extends Comparable<T>> void scan(SimpleCollection<T> structure) {
		if (structure == null || structure.isEmpty())
			return;

		PriorityStack<Integer> stack = (PriorityStack<Integer>) structure;
		StackNode<Integer> target = stack.getTop();
		int priority;
		StackNode<Integer> prev = null;
		int lastPriority = -1;

		while (target != null) {
			priority = target.getPriority();
			if (prev != null && priority > lastPriority) {
				fail("Previous" + nodeToString(prev) + " has higher priority than Target" + nodeToString(target));
			}

			target = target.getNext();
			lastPriority = priority;

		}
	}

	public static String nodeToString(StackNode<Integer> n) {
		return "(" + n.getPriority() + ", " + n.getData() + ")";
	}

}
