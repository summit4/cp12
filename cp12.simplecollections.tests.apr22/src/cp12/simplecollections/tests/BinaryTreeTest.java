package cp12.simplecollections.tests;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import cp12.simplecollections.SimpleCollection;
import cp12.simplecollections.level2.BinaryTree;
import cp12.simplecollections.level2.TreeNode;

/**
 * @author Mr. Hapke
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BinaryTreeTest extends SimpleCollectionTest {

	public BinaryTreeTest() {
		super(false, true);
	}

	@Override
	protected SimpleCollection<Integer> createCollection() {
		return new BinaryTree<Integer>();
	}

	@Override
	protected SimpleCollection<String> createCollectionString() {
		return new BinaryTree<String>();
	}

	@Override
	protected SimpleCollection<Double> createCollectionDouble() {
		return new BinaryTree<Double>();
	}

	@Override
	protected <T extends Comparable<T>> void scan(SimpleCollection<T> structure) {
		BinaryTree<T> tree = (BinaryTree<T>) structure;
		scan(tree.getRoot());
	}

	public <T extends Comparable<T>> void scan(TreeNode<T> target) {
		scanTreeIter(target);
		scanTreeRec(target);
	}

	protected <T extends Comparable<T>> void scanTreeRec(TreeNode<T> target) {
		scanTreeRecursive(target, new HashSet<T>());
	}

	protected <T extends Comparable<T>> void scanTreeRecursive(TreeNode<T> target, Set<T> previouslyVisited) {
		if (target == null)
			return;
		T targetVal = target.getData();
		assertNotNull("TreeNodes should always have the data filled in (non-null)", targetVal);

		TreeNode<T> left = target.getLeft();
		if (left != null) {
			T leftData = left.getData();
			if (previouslyVisited.contains(leftData)) {
				fail("Cycle detected: " + leftData + " is in the tree more than once.");
			}
			previouslyVisited.add(leftData);

			assertNotNull(leftData);
			assertTrue(leftData.compareTo(targetVal) < 0);
			scanTreeRecursive(left, previouslyVisited);
		}
		TreeNode<T> right = target.getRight();
		if (right != null) {
			T rightData = right.getData();
			if (previouslyVisited.contains(rightData)) {
				fail("Cycle detected: " + rightData + " is in the tree more than once.");
			}
			previouslyVisited.add(rightData);

			assertNotNull(rightData);
			assertTrue(rightData.compareTo(targetVal) > 0);
			scanTreeRecursive(right, previouslyVisited);
		}
	}

	protected <T extends Comparable<T>> void scanTreeIter(TreeNode<T> target) {
		scanTreeIterative(target, new HashSet<T>());
	}

	protected <T extends Comparable<T>> void scanTreeIterative(TreeNode<T> target, Set<T> previouslyVisited) {
		if (target == null)
			return;
		T targetVal = target.getData();
		assertNotNull("TreeNodes should always have the data filled in (non-null)", targetVal);

		List<TreeNode<T>> everythingLeft = new ArrayList<>();
		TreeNode<T> left = target.getLeft();
		if (left != null)
			everythingLeft.add(left);

		while (everythingLeft.size() > 0) {
			TreeNode<T> node = everythingLeft.get(0);
			everythingLeft.remove(node);

			T val = node.getData();
			previouslyVisited.add(val);
			assertTrue(val.compareTo(targetVal) < 0);

			if (node.getLeft() != null) {
				T leftVal = node.getLeft().getData();
				if (previouslyVisited.contains(leftVal)) {
					fail("Cycle detected: " + leftVal + " is in the tree more than once.");
				}
				everythingLeft.add(node.getLeft());
			}

			if (node.getRight() != null) {

				T rightVal = node.getRight().getData();
				if (previouslyVisited.contains(rightVal)) {
					fail("Cycle detected: " + rightVal + " is in the tree more than once.");
				}
				everythingLeft.add(node.getRight());
			}
		}

		List<TreeNode<T>> everythingRight = new ArrayList<>();
		TreeNode<T> right = target.getRight();
		if (right != null)
			everythingRight.add(right);
		while (everythingRight.size() > 0) {
			TreeNode<T> node = everythingRight.get(0);
			everythingRight.remove(node);

			T val = node.getData();
			previouslyVisited.add(val);
			assertTrue(val.compareTo(targetVal) > 0);

			if (node.getLeft() != null) {
				T leftVal = node.getLeft().getData();
				if (previouslyVisited.contains(leftVal)) {
					fail("Cycle detected: " + leftVal + " is in the tree more than once.");
				}
				everythingRight.add(node.getLeft());
			}

			if (node.getRight() != null) {
				T rightVal = node.getRight().getData();
				if (previouslyVisited.contains(rightVal)) {
					fail("Cycle detected: " + rightVal + " is in the tree more than once.");
				}
				everythingRight.add(node.getRight());
			}
		}
	}

	@Test
	public void testRemove2() {
		a.add(5);
		a.add(12);
		a.add(9);
		a.add(21);
		a.add(19);
		a.add(25);
		assertEquals(6, a.size());
		scan(a);

		a.remove(12);
		scan(a);
		assertEquals(5, a.size());

	}

	@Test
	public void testDepthFirstTraversal() {
		addDefaults(false);
		BinaryTree<Integer> tree = (BinaryTree<Integer>) a;
		String dfs = tree.depthFirstTraversal();
		assertEquals("22,37,41,45,65,81", dfs);
	}

	@Test
	public void testBreadthFirstTraversal() {
		addDefaults(false);
		BinaryTree<Integer> tree = (BinaryTree<Integer>) a;
		assertEquals("45,37,65,22,41,81", tree.breadthFirstTraversal());
	}

	public void testRemoveExtremeLeftHasARightChild() {
		a.add(23);
		a.add(12);
		a.add(31);
		a.add(9);
		a.add(16);
		a.add(25);
		a.add(43);
		a.add(5);
		a.add(24);
		a.add(30);
		a.add(41);
		a.add(64);
		a.add(29);
		a.add(27);
		a.add(28);

		BinaryTree<Integer> tree = (BinaryTree<Integer>) a;
		TreeNode<Integer> root = tree.getRoot();

		assertEquals(23, root.getData().intValue());
		assertEquals(31, root.getRight().getData().intValue());
		assertEquals(25, root.getRight().getLeft().getData().intValue());
		assertEquals(24, root.getRight().getLeft().getLeft().getData().intValue());
		assertEquals(30, root.getRight().getLeft().getRight().getData().intValue());

		scan(a);
		assertEquals(15, a.size());

		a.remove(25);

		scan(a);
		assertEquals(14, a.size());
	}

	public void testRemoveExtremeLeftParentIsTheTarget() {
		a.add(5);
		a.add(12);
		a.add(9);
		a.add(21);
		a.add(25);
		scan(a);
		assertEquals(5, a.size());

		a.remove(12);
		scan(a);
		assertEquals(4, a.size());

	}

	public void testRemoveExtremeLeftHasARightChildWithChildren() {
		a.add(23);
		a.add(12);
		a.add(70);
		a.add(9);
		a.add(16);
		a.add(30);
		a.add(80);
		a.add(5);
		a.add(24);
		a.add(60);
		a.add(79);
		a.add(81);
		a.add(50);
		a.add(31);
		a.add(51);
		a.add(37);
		a.add(34);
		a.add(38);

		BinaryTree<Integer> tree = (BinaryTree<Integer>) a;
		TreeNode<Integer> root = tree.getRoot();

		assertEquals(23, root.getData().intValue());
		assertEquals(70, root.getRight().getData().intValue());
		assertEquals(30, root.getRight().getLeft().getData().intValue());
		assertEquals(24, root.getRight().getLeft().getLeft().getData().intValue());
		assertEquals(60, root.getRight().getLeft().getRight().getData().intValue());
		// 31 is the minRight, with children 37,34,38
		assertEquals(31, root.getRight().getLeft().getRight().getLeft().getLeft().getData().intValue());
		assertEquals(37, root.getRight().getLeft().getRight().getLeft().getLeft().getRight().getData().intValue());

		assertEquals(18, a.size());

		a.remove(30);

		scan(a);
		assertEquals(17, a.size());
	}

	public void testRemoveAndScan() {
		a.add(23);
		a.add(12);
		a.add(70);
		a.add(9);
		a.add(16);
		a.add(30);
		a.add(80);
		a.add(5);
		a.add(24);
		a.add(60);
		a.add(79);
		a.add(26);
		a.add(50);
		a.add(31);
		a.add(51);
		a.add(37);
		a.add(34);
		a.add(38);

		BinaryTree<Integer> tree = (BinaryTree<Integer>) a;
		TreeNode<Integer> root = tree.getRoot();

		assertEquals(23, root.getData().intValue());
		assertEquals(70, root.getRight().getData().intValue());
		assertEquals(30, root.getRight().getLeft().getData().intValue());
		assertEquals(24, root.getRight().getLeft().getLeft().getData().intValue());
		assertEquals(60, root.getRight().getLeft().getRight().getData().intValue());
		// 31 is the minRight, with children 37,34,38
		assertEquals(31, root.getRight().getLeft().getRight().getLeft().getLeft().getData().intValue());
		assertEquals(37, root.getRight().getLeft().getRight().getLeft().getLeft().getRight().getData().intValue());

		assertEquals(18, a.size());

		scan(a);
		a.remove(30);

		TreeNode<Integer> replacement = root.getRight().getLeft();

		scan(replacement);
		scan(a);
		assertEquals(17, a.size());
	}

	@Test
	public void testRemoveOneChild() {
		addDefaults(false);
		assertEquals(6, a.size());
		assertTrue(a.contains(45));
		assertTrue(a.contains(37));
		assertTrue(a.contains(22));
		assertTrue(a.contains(41));
		assertTrue(a.contains(65));
		assertTrue(a.contains(81));

		a.remove(65);

		scan(a);

		assertEquals(5, a.size());
		assertTrue(a.contains(45));
		assertTrue(a.contains(37));
		assertTrue(a.contains(22));
		assertTrue(a.contains(41));
		assertFalse(a.contains(65));
		assertTrue(a.contains(81));

		BinaryTree<Integer> tree = (BinaryTree<Integer>) a;
		TreeNode<Integer> root = tree.getRoot();

		assertEquals(45, root.getData().intValue());
		TreeNode<Integer> eightyOne = root.getRight();
		assertEquals(81, eightyOne.getData().intValue());
		assertNull(eightyOne.getLeft());
		assertNull(eightyOne.getRight());
	}

	@Test
	public void testRemoveRootOneChild() {
		assertEquals(0, a.size());

		a.add(46);
		scan(a);
		assertEquals(1, a.size());

		a.add(81);
		scan(a);
		assertEquals(2, a.size());

		a.remove(46);
		scan(a);
		assertEquals(1, a.size());

		BinaryTree<Integer> tree = (BinaryTree<Integer>) a;
		TreeNode<Integer> root = tree.getRoot();

		assertEquals(81, root.getData().intValue());
	}

}
