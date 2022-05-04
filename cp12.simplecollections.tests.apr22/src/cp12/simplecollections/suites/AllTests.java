package cp12.simplecollections.suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import cp12.simplecollections.tests.ArrayedListTest;
import cp12.simplecollections.tests.BinaryTreeTest;
import cp12.simplecollections.tests.PriorityStackTest;
import cp12.simplecollections.tests.SimpleLinkedListTest;
import cp12.simplecollections.tests.SortedLinkedSetTest;

/**
 * @author Mr. Hapke
 */
@RunWith(Suite.class)
@SuiteClasses({ ArrayedListTest.class, SimpleLinkedListTest.class, SortedLinkedSetTest.class, PriorityStackTest.class, BinaryTreeTest.class })
public class AllTests {

}
