package avl;

import java.util.Comparator;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Created with IntelliJ IDEA. User: Antonio J. Nebro Date: 08/07/13
 */
public class AvlTreeTest {

  AvlTree<Integer> avlTree;
  Comparator<?> comparator;

  @BeforeEach
  public void setUp() throws Exception {
    comparator = Comparator.comparingInt((Integer o) -> o);
    avlTree = new AvlTree(comparator);
  }

  @AfterEach
  public void tearDown() throws Exception {
    avlTree = null;
    comparator = null;
  }

  @Test
  public void testAvlIsEmpty() throws Exception {
    assertTrue(avlTree.avlIsEmpty(), "TestAvlIsEmpty");

    avlTree.insertTop(new AvlNode(5));
    assertFalse(avlTree.avlIsEmpty(), "TestAvlIsEmpty");
  }

  @Test
  public void testInsertTop() throws Exception {
    AvlNode<Integer> node = new AvlNode(4);
    avlTree.insertTop(node);
    assertEquals( node, avlTree.getTop(), "TestInsertTop");
    String tree = " | 4";
    assertEquals(tree, avlTree.toString(),"TestInsertTop");
  }

  @Test
  public void testCompareNodes() throws Exception {
    AvlNode<Integer> node1 = new AvlNode<Integer>(4);
    AvlNode<Integer> node2 = new AvlNode<Integer>(5);
    AvlNode<Integer> node3 = new AvlNode<Integer>(5);

    assertEquals(-1, avlTree.compareNodes(node1, node2), "testCompareNodes");
    assertEquals(1, avlTree.compareNodes(node3, node1), "testCompareNodes");
    assertEquals(0, avlTree.compareNodes(node2, node3), "testCompareNodes");
  }

  /*
  @Test
  public void testInsertingTheFirstElement() throws Exception {
    AvlNode<Integer> node = new AvlNode<Integer>(6) ;
    avlTree_.insertAvlNode(node);
    assertEquals("testInsertingTheFirstElement", node, avlTree_.getTop());
  }
  */

  @Test
  public void testInsertingRightAndLeftElementsJustAfterTop() throws Exception {
    AvlNode<Integer> node = new AvlNode<Integer>(6);
    avlTree.insertAvlNode(node);
    AvlNode<Integer> nodeLeft = new AvlNode<Integer>(4);
    AvlNode<Integer> nodeRight = new AvlNode<Integer>(9);

    assertEquals(-1, avlTree.searchClosestNode(nodeLeft), "testInsertingSecondSmallerElement");
    assertEquals(node, nodeLeft.getClosestNode(), "testInsertingSecondSmallerElement");
    assertEquals(+1, avlTree.searchClosestNode(nodeRight), "testInsertingSecondSmallerElement");
    assertEquals(node, nodeRight.getClosestNode(), "testInsertingSecondSmallerElement");
    assertEquals(0, avlTree.searchClosestNode(node), "testInsertingSecondSmallerElement");

    node.setLeft(nodeLeft);
    node.setRight(nodeRight);
    AvlNode<Integer> nodeRightLeft = new AvlNode<Integer>(7);
    avlTree.searchClosestNode(nodeRightLeft);
    assertEquals(-1,
        avlTree.searchClosestNode(nodeRightLeft), "testInsertingSecondSmallerElement");
    assertEquals(nodeRight, nodeRightLeft.getClosestNode(), "testInsertingSecondSmallerElement");

    AvlNode<Integer> nodeLeftRight = new AvlNode<Integer>(5);
    assertEquals(1, avlTree.searchClosestNode(nodeLeftRight), "testInsertingSecondSmallerElement");
    assertEquals(nodeLeft, nodeLeftRight.getClosestNode(), "testInsertingSecondSmallerElement");

    String tree = " | 6 | 4 | 9";
    assertEquals(tree, avlTree.toString(),"testInsertingSecondSmallerElement");
  }

  @Test
  public void testInsertingLeftElement() throws Exception {
    AvlNode<Integer> node = new AvlNode<Integer>(6);
    avlTree.insertAvlNode(node);
    AvlNode<Integer> nodeLeft = new AvlNode<Integer>(4);
    avlTree.insertAvlNode(nodeLeft);

    assertEquals(node, nodeLeft.getParent(), "testInsertingLeftElement");
    assertEquals(nodeLeft, node.getLeft(), "testInsertingLeftElement");

    String tree = " | 6 | 4";
    assertEquals(tree, avlTree.toString(),"testInsertingLeftElement");
  }

  @Test
  public void testSearchClosestNode() throws Exception {
    int result;
    AvlNode<Integer> node = new AvlNode<Integer>(7);
    result = avlTree.searchClosestNode(node);
    assertEquals(0, result, "testSearchClosestNode");
    avlTree.insertAvlNode(node);

    node = new AvlNode<Integer>(4);
    result = avlTree.searchClosestNode(node);
    assertEquals(-1, result, "testSearchClosestNode");
    avlTree.insertAvlNode(node);

    node = new AvlNode<Integer>(9);
    result = avlTree.searchClosestNode(node);
    assertEquals(1, result, "testSearchClosestNode");
    avlTree.insertAvlNode(node);

    node = new AvlNode<Integer>(6);
    result = avlTree.searchClosestNode(node);
    assertEquals(1, result, "testSearchClosestNode");
    avlTree.insertAvlNode(node);

    node = new AvlNode<Integer>(8);
    result = avlTree.searchClosestNode(node);
    assertEquals(-1, result, "testSearchClosestNode");
    avlTree.insertAvlNode(node);

    String tree = " | 7 | 4 | 6 | 9 | 8";
    assertEquals(tree, avlTree.toString(),"testSearchClosestNode");
  }

  @Test
  public void testInsertingRightElement() throws Exception {
    AvlNode<Integer> node = new AvlNode<Integer>(6);
    avlTree.insertAvlNode(node);
    AvlNode<Integer> nodeRight = new AvlNode<Integer>(9);
    avlTree.insertAvlNode(nodeRight);

    assertEquals(node, nodeRight.getParent(), "testInsertingRightElement");
    assertEquals(nodeRight, node.getRight(), "testInsertingRightElement");

    String tree = " | 6 | 9";
    assertEquals(tree, avlTree.toString(),"testInsertingRightElement");
  }

  /**
   * Test adding 7 - 4 - 9 - 3 - 5
   *
   * @throws Exception
   */
  @Test
  public void testHeightAndBalanceOfASimpleBalancedTree() throws Exception {
    AvlNode<Integer> node1, node2, node3, node4, node5;

    node1 = new AvlNode<Integer>(7);
    avlTree.insertAvlNode(node1);
    assertEquals(0, node1.getHeight(), "testHeightOfASimpleBalancedTree");
    assertEquals(0, avlTree.getBalance(node1), "testHeightOfASimpleBalancedTree");

    node2 = new AvlNode<Integer>(4);
    avlTree.insertAvlNode(node2);
    assertEquals(0, node2.getHeight(),"testHeightOfASimpleBalancedTree");
    assertEquals(1, node1.getHeight(), "testHeightOfASimpleBalancedTree");
    assertEquals(-1, avlTree.getBalance(node1), "testHeightOfASimpleBalancedTree");
    assertEquals(0, avlTree.getBalance(node2), "testHeightOfASimpleBalancedTree");

    node3 = new AvlNode<Integer>(9);
    avlTree.insertAvlNode(node3);
    assertEquals(0, node3.getHeight(), "testHeightOfASimpleBalancedTree");
    assertEquals(1, node1.getHeight(), "testHeightOfASimpleBalancedTree");
    assertEquals(0, avlTree.getBalance(node1), "testHeightOfASimpleBalancedTree");
    assertEquals(0, avlTree.getBalance(node3), "testHeightOfASimpleBalancedTree");

    node4 = new AvlNode<Integer>(3);
    avlTree.insertAvlNode(node4);
    assertEquals(0, node4.getHeight(), "testHeightOfASimpleBalancedTree");
    assertEquals(1, node2.getHeight(), "testHeightOfASimpleBalancedTree");
    assertEquals(2, node1.getHeight(), "testHeightOfASimpleBalancedTree");
    assertEquals(-1, avlTree.getBalance(node2), "testHeightOfASimpleBalancedTree");
    assertEquals(-1, avlTree.getBalance(node1), "testHeightOfASimpleBalancedTree");
    assertEquals(0, avlTree.getBalance(node4), "testHeightOfASimpleBalancedTree");

    node5 = new AvlNode<Integer>(5);
    avlTree.insertAvlNode(node5);
    assertEquals(0, node5.getHeight(), "testHeightOfASimpleBalancedTree");
    assertEquals(1, node2.getHeight(), "testHeightOfASimpleBalancedTree");
    assertEquals(2, node1.getHeight(), "testHeightOfASimpleBalancedTree");
    assertEquals(0, avlTree.getBalance(node2));
    assertEquals(-1, avlTree.getBalance(node1), "testHeightOfASimpleBalancedTree");
    assertEquals(0, avlTree.getBalance(node5), "testHeightOfASimpleBalancedTree");

    String tree = " | 7 | 4 | 3 | 5 | 9";
    assertEquals(tree, avlTree.toString(),"testHeightOfASimpleBalancedTree");
  }

  /**
   * Testing adding 7 - 4 - 3
   *
   * @throws Exception
   */
  @Test
  public void testInsertingLeftLeftNodeAndRebalance() throws Exception {
    AvlNode<Integer> node1, node2, node3, node4, node5;

    node1 = new AvlNode<Integer>(7);
    avlTree.insertAvlNode(node1);
    assertEquals(0, node1.getHeight(), "testInsertingLeftLeftNodeAndRebalance");
    assertEquals(0, avlTree.getBalance(node1), "testInsertingLeftLeftNodeAndRebalance");

    node2 = new AvlNode<Integer>(4);
    avlTree.insertAvlNode(node2);
    assertEquals(0, node2.getHeight(), "testInsertingLeftLeftNodeAndRebalance");
    assertEquals(1, node1.getHeight(), "testInsertingLeftLeftNodeAndRebalance");
    assertEquals(-1, avlTree.getBalance(node1), "testInsertingLeftLeftNodeAndRebalance");
    assertEquals(0, avlTree.getBalance(node2), "testInsertingLeftLeftNodeAndRebalance");

    node3 = new AvlNode<Integer>(3);
    avlTree.insertAvlNode(node3);
    assertEquals(node2, avlTree.getTop(), "testInsertingLeftLeftNodeAndRebalance");
    assertEquals(node3, node2.getLeft(), "testInsertingLeftLeftNodeAndRebalance");
    assertEquals(node1, node2.getRight(), "testInsertingLeftLeftNodeAndRebalance");

    assertEquals(1, avlTree.getTop().getHeight(), "testInsertingLeftLeftNodeAndRebalance");
    assertEquals(0,
        avlTree.getTop().getLeft().getHeight(), "testInsertingLeftLeftNodeAndRebalance");
    assertEquals(0,
        avlTree.getTop().getRight().getHeight(), "testInsertingLeftLeftNodeAndRebalance");
    assertEquals(-1, avlTree.height(node1.getLeft()), "testInsertingLeftLeftNodeAndRebalance");
    assertEquals(-1, avlTree.height(node1.getRight()), "testInsertingLeftLeftNodeAndRebalance");
    assertEquals(-1, avlTree.height(node3.getLeft()), "testInsertingLeftLeftNodeAndRebalance");
    assertEquals(-1, avlTree.height(node3.getRight()), "testInsertingLeftLeftNodeAndRebalance");

    String tree = " | 4 | 3 | 7";
    assertEquals(tree, avlTree.toString(),"testInsertingLeftLeftNodeAndRebalance");
  }

  /**
   * Testing adding 7 - 10 - 14
   *
   * @throws Exception
   */
  @Test
  public void testInsertingRightRightNodeAndRebalance() throws Exception {
    AvlNode<Integer> node1, node2, node3, node4, node5;

    node1 = new AvlNode<Integer>(7);
    avlTree.insertAvlNode(node1);
    assertEquals(0, node1.getHeight(), "testInsertingRightRightNodeAndRebalance");
    assertEquals(0, avlTree.getBalance(node1), "testInsertingRightRightNodeAndRebalance");

    node2 = new AvlNode<Integer>(10);
    avlTree.insertAvlNode(node2);
    assertEquals(0, node2.getHeight(), "testInsertingRightRightNodeAndRebalance");
    assertEquals(1, node1.getHeight(), "testInsertingRightRightNodeAndRebalance");
    assertEquals(1, avlTree.getBalance(node1), "testInsertingRightRightNodeAndRebalance");
    assertEquals(0, avlTree.getBalance(node2), "testInsertingRightRightNodeAndRebalance");

    node3 = new AvlNode<Integer>(14);
    avlTree.insertAvlNode(node3);
    assertEquals(node2, avlTree.getTop(), "testInsertingRightRightNodeAndRebalance");
    assertEquals(node1, node2.getLeft(), "testInsertingRightRightNodeAndRebalance");
    assertEquals(node3, node2.getRight(), "testInsertingRightRightNodeAndRebalance");

    assertEquals(1, avlTree.getTop().getHeight(), "testInsertingRightRightNodeAndRebalance");
    assertEquals(0, avlTree.getTop().getLeft().getHeight(), "testInsertingRightRightNodeAndRebalance");
    assertEquals( 0,
        avlTree.getTop().getRight().getHeight(), "testInsertingRightRightNodeAndRebalance");
    assertEquals(-1, avlTree.height(node1.getLeft()), "testInsertingRightRightNodeAndRebalance");
    assertEquals(-1, avlTree.height(node1.getRight()), "testInsertingRightRightNodeAndRebalance");
    assertEquals(-1, avlTree.height(node3.getLeft()), "testInsertingRightRightNodeAndRebalance");
    assertEquals(-1, avlTree.height(node3.getRight()), "testInsertingRightRightNodeAndRebalance");

    String tree = " | 10 | 7 | 14";
    assertEquals(tree, avlTree.toString(),"testInsertingRightRightNodeAndRebalance");
  }

  /**
   * Testing adding 7 - 4 - 3 - 2 - 1
   *
   * @throws Exception
   */
  @Test
  public void testInserting7_4_3_2_1() throws Exception {
    AvlNode<Integer> node1, node2, node3, node4, node5;

    node1 = new AvlNode<Integer>(7);
    node2 = new AvlNode<Integer>(4);
    node3 = new AvlNode<Integer>(3);
    node4 = new AvlNode<Integer>(2);
    node5 = new AvlNode<Integer>(1);

    avlTree.insertAvlNode(node1);
    avlTree.insertAvlNode(node2);
    avlTree.insertAvlNode(node3);
    avlTree.insertAvlNode(node4);
    avlTree.insertAvlNode(node5);

    assertEquals(node2, avlTree.getTop(), "testInserting7_4_3_2_1");
    assertEquals(node4, node2.getLeft(), "testInserting7_4_3_2_1");
    assertEquals(node1, node2.getRight(),"testInserting7_4_3_2_1");
    assertEquals(node5, node4.getLeft(),"testInserting7_4_3_2_1");
    assertEquals(node3, node4.getRight(),"testInserting7_4_3_2_1");
    assertEquals(0, node1.getHeight(),"testInserting7_4_3_2_1");
    assertEquals(2, node2.getHeight(),"testInserting7_4_3_2_1");
    assertEquals(1, node4.getHeight(),"testInserting7_4_3_2_1");

    String tree = " | 4 | 2 | 1 | 3 | 7";
    assertEquals(tree, avlTree.toString(),"testInserting7_4_3_2_1");
  }

  /**
   * Testing adding 7 - 4 - 3 - 2 - 1
   *
   * @throws Exception
   */
  @Test
  public void testInserting7_8_9_10_11() throws Exception {
    AvlNode<Integer> node1, node2, node3, node4, node5;

    node1 = new AvlNode<Integer>(7);
    node2 = new AvlNode<Integer>(8);
    node3 = new AvlNode<Integer>(9);
    node4 = new AvlNode<Integer>(10);
    node5 = new AvlNode<Integer>(11);

    avlTree.insertAvlNode(node1);
    avlTree.insertAvlNode(node2);
    avlTree.insertAvlNode(node3);
    avlTree.insertAvlNode(node4);
    avlTree.insertAvlNode(node5);

    assertEquals(node2, avlTree.getTop(),"testInserting7_8_9_10_11");
    assertEquals(node4, node2.getRight(),"testInserting7_8_9_10_11");
    assertEquals(node1, node2.getLeft(),"testInserting7_8_9_10_11");
    assertEquals(node5, node4.getRight(),"testInserting7_8_9_10_11");
    assertEquals(node3, node4.getLeft(),"testInserting7_8_9_10_11");
    assertEquals(2, avlTree.getTop().getHeight(),"testInserting7_8_9_10_11");
    assertEquals(1, node4.getHeight(),"testInserting7_8_9_10_11");
    assertEquals(0, node1.getHeight(),"testInserting7_8_9_10_11");

    String tree = " | 8 | 7 | 10 | 9 | 11";
    assertEquals(tree, avlTree.toString(),"testInserting7_8_9_10_11");
  }

  /**
   * Testing adding 7 - 2 - 3
   *
   * @throws Exception
   */
  @Test
  public void testInsertingLeftRightNodeAndRebalance() throws Exception {
    AvlNode<Integer> node1, node2, node3;

    node1 = new AvlNode<Integer>(7);
    avlTree.insertAvlNode(node1);

    node2 = new AvlNode<Integer>(2);
    avlTree.insertAvlNode(node2);

    node3 = new AvlNode<Integer>(3);
    avlTree.insertAvlNode(node3);

    assertEquals(node3, avlTree.getTop(),"testInsertingLeftRightNodeAndRebalance");
    assertEquals(node2, node3.getLeft(),"testInsertingLeftRightNodeAndRebalance");
    assertEquals(node1, node3.getRight(),"testInsertingLeftRightNodeAndRebalance");

    assertEquals(1, avlTree.getTop().getHeight(),"testInsertingLeftRightNodeAndRebalance");
    assertEquals(0,
        avlTree.getTop().getLeft().getHeight(),"testInsertingLeftRightNodeAndRebalance");
    assertEquals(0,
        avlTree.getTop().getRight().getHeight(),"testInsertingLeftRightNodeAndRebalance");
    assertEquals(-1, avlTree.height(node2.getLeft()),"testInsertingLeftRightNodeAndRebalance");
    assertEquals(-1, avlTree.height(node2.getRight()),"testInsertingLeftRightNodeAndRebalance");
    assertEquals(-1, avlTree.height(node1.getLeft()),"testInsertingLeftRightNodeAndRebalance");
    assertEquals(-1, avlTree.height(node1.getRight()),"testInsertingLeftRightNodeAndRebalance");

    String tree = " | 3 | 2 | 7";
    assertEquals(tree, avlTree.toString(),"testInsertingLeftRightNodeAndRebalance");
  }

  /**
   * Testing adding 7 - 9 - 8
   *
   * @throws Exception
   */
  @Test
  public void testInsertingRightLeftNodeAndRebalance() throws Exception {
    AvlNode<Integer> node1, node2, node3;

    node1 = new AvlNode<Integer>(7);
    avlTree.insertAvlNode(node1);

    node2 = new AvlNode<Integer>(9);
    avlTree.insertAvlNode(node2);

    node3 = new AvlNode<Integer>(8);
    avlTree.insertAvlNode(node3);

    assertEquals(node3, avlTree.getTop(),"testInsertingRightLeftNodeAndRebalance");
    assertEquals(node1, node3.getLeft(),"testInsertingRightLeftNodeAndRebalance");
    assertEquals(node2, node3.getRight(),"testInsertingRightLeftNodeAndRebalance");

    assertEquals(1, avlTree.getTop().getHeight(),"testInsertingRightLeftNodeAndRebalance");
    assertEquals( 0,
        avlTree.getTop().getLeft().getHeight(),"testInsertingRightLeftNodeAndRebalance");
    assertEquals(0,
        avlTree.getTop().getRight().getHeight(),"testInsertingRightLeftNodeAndRebalance");
    assertEquals(-1, avlTree.height(node2.getLeft()),"testInsertingRightLeftNodeAndRebalance");
    assertEquals(-1, avlTree.height(node2.getRight()),"testInsertingRightLeftNodeAndRebalance");
    assertEquals(-1, avlTree.height(node1.getLeft()),"testInsertingRightLeftNodeAndRebalance");
    assertEquals(-1, avlTree.height(node1.getRight()),"testInsertingRightLeftNodeAndRebalance");

    String tree = " | 8 | 7 | 9";
    assertEquals(tree, avlTree.toString(),"testInsertingRightLeftNodeAndRebalance");
  }

  @Test
  public void testSearchNode() throws Exception {
    AvlNode<Integer> node1, node2, node3, node4, node5;

    node1 = new AvlNode<Integer>(7);
    avlTree.insertAvlNode(node1);

    node2 = new AvlNode<Integer>(9);
    avlTree.insertAvlNode(node2);

    node3 = new AvlNode<Integer>(8);
    avlTree.insertAvlNode(node3);

    node4 = new AvlNode<Integer>(2);
    avlTree.insertAvlNode(node4);

    node5 = new AvlNode<Integer>(3);
    avlTree.insertAvlNode(node5);

    assertEquals(node1, avlTree.search(7),"testSearchNode");
    assertEquals(node2, avlTree.search(9),"testSearchNode");
    assertEquals(node3, avlTree.search(8),"testSearchNode");
    assertEquals((Integer) 2,
        avlTree.searchNode(new AvlNode<Integer>(2)).getItem(),"testSearchNode");
    assertEquals(node4, avlTree.search(2),"testSearchNode");
    assertEquals(node5, avlTree.search(3),"testSearchNode");
    assertNull(avlTree.search(14),"testInsertNode");
    assertNull(avlTree.search(0),"testSearchNode");

    String tree = " | 8 | 3 | 2 | 7 | 9";
    assertEquals(tree, avlTree.toString(),"testSearchNode");
  }

  @Test
  public void testFindSuccessor() throws Exception {
    AvlNode<Integer> node;

    node = new AvlNode<Integer>(20);
    avlTree.insertAvlNode(node);

    node = new AvlNode<Integer>(8);
    avlTree.insertAvlNode(node);

    node = new AvlNode<Integer>(22);
    avlTree.insertAvlNode(node);

    node = new AvlNode<Integer>(4);
    avlTree.insertAvlNode(node);

    node = new AvlNode<Integer>(12);
    avlTree.insertAvlNode(node);

    node = new AvlNode<Integer>(24);
    avlTree.insertAvlNode(node);

    node = new AvlNode<Integer>(10);
    avlTree.insertAvlNode(node);

    node = new AvlNode<Integer>(14);
    avlTree.insertAvlNode(node);

    node = avlTree.search(8);
    assertEquals(avlTree.search(10), avlTree.findSuccessor(node),"testFindSuccessor");
    node = avlTree.search(10);
    assertEquals(avlTree.search(12), avlTree.findSuccessor(node),"testFindSuccessor");
    node = avlTree.search(14);
    assertEquals(avlTree.search(20), avlTree.findSuccessor(node),"testFindSuccessor");

    String tree = " | 20 | 8 | 4 | 12 | 10 | 14 | 22 | 24";
    assertEquals(tree, avlTree.toString(),"testSearchNode");
  }

  @Test
  public void testDeletingLeafNodes() throws Exception {
    AvlNode<Integer> node1, node2, node3, node4, node5;

    node1 = new AvlNode<Integer>(7);
    avlTree.insertAvlNode(node1);

    node2 = new AvlNode<Integer>(9);
    avlTree.insertAvlNode(node2);

    node3 = new AvlNode<Integer>(2);
    avlTree.insertAvlNode(node3);

    node4 = new AvlNode<Integer>(8);
    avlTree.insertAvlNode(node4);

    node5 = new AvlNode<Integer>(3);
    avlTree.insertAvlNode(node5);

    String tree = " | 7 | 2 | 3 | 9 | 8";
    assertEquals(tree, avlTree.toString(),"testDeletingLeafNodes");

    avlTree.delete(3); // right leaf node
    assertEquals(null, node3.getRight(), "testDeletingLeafNodes");
    assertEquals(0, node3.getHeight(), "testDeletingLeafNodes");
    assertEquals(2, avlTree.getTop().getHeight(), "testDeletingLeafNodes");
    assertEquals(" | 7 | 2 | 9 | 8", avlTree.toString(), "testDeletingLeafNodes");

    avlTree.delete(8); // left leaf node
    assertEquals(null, node2.getLeft(),"testDeletingLeafNodes");
    assertEquals(0, node2.getHeight(),"testDeletingLeafNodes");
    assertEquals(1, avlTree.getTop().getHeight(),"testDeletingLeafNodes");
    assertEquals(" | 7 | 2 | 9", avlTree.toString(),"testDeletingLeafNodes");

    avlTree.delete(2); // left leaf node
    assertEquals(null, node1.getLeft(),"testDeletingLeafNodes");
    assertEquals(1, node1.getHeight(),"testDeletingLeafNodes");
    assertEquals(" | 7 | 9", avlTree.toString(),"testDeletingLeafNodes");

    avlTree.delete(9); // right leaf node
    assertEquals(null, node1.getRight(),"testDeletingLeafNodes");
    assertEquals(0, node1.getHeight(),"testDeletingLeafNodes");
    assertEquals(" | 7", avlTree.toString(),"testDeletingLeafNodes");

    avlTree.delete(7); // left leaf node
    assertEquals(null, avlTree.getTop(),"testDeletingLeafNodes");
    assertEquals("", avlTree.toString(),"testDeletingLeafNodes");
  }

  @Test
  public void testDeletingNodesWithOneLeaf() throws Exception {
    AvlNode<Integer> node1, node2, node3, node4, node5;

    node1 = new AvlNode<Integer>(7);
    avlTree.insertAvlNode(node1);

    node2 = new AvlNode<Integer>(9);
    avlTree.insertAvlNode(node2);

    node3 = new AvlNode<Integer>(2);
    avlTree.insertAvlNode(node3);

    node4 = new AvlNode<Integer>(8);
    avlTree.insertAvlNode(node4);

    node5 = new AvlNode<Integer>(3);
    avlTree.insertAvlNode(node5);

    String tree = " | 7 | 2 | 3 | 9 | 8";
    assertEquals(tree, avlTree.toString(),"testDeletingNodesWithOneLeaf");

    avlTree.delete(2);
    assertEquals(node3.getItem(), node1.getLeft().getItem(),"testDeletingNodesWithOneLeaf");
    assertEquals(null, node3.getRight(),"testDeletingNodesWithOneLeaf");
    assertEquals(0, node3.getHeight(),"testDeletingNodesWithOneLeaf");
    assertEquals(2, avlTree.getTop().getHeight(),"testDeletingNodesWithOneLeaf");
    assertEquals(" | 7 | 3 | 9 | 8", avlTree.toString(),"testDeletingNodesWithOneLeaf");

    avlTree.delete(9);
    assertEquals(node2.getItem(), node1.getRight().getItem(),"testDeletingNodesWithOneLeaf");
    assertEquals(null, node2.getLeft(),"testDeletingNodesWithOneLeaf");
    assertEquals(0, node2.getHeight(),"testDeletingNodesWithOneLeaf");
    assertEquals(1, avlTree.getTop().getHeight(),"testDeletingNodesWithOneLeaf");
    assertEquals(" | 7 | 3 | 8", avlTree.toString(),"testDeletingNodesWithOneLeaf");
  }

  @Test
  public void testDeletingNodesWithTwoLeaves() throws Exception {
    AvlNode<Integer> node;

    node = new AvlNode<Integer>(20);
    avlTree.insertAvlNode(node);

    node = new AvlNode<Integer>(8);
    avlTree.insertAvlNode(node);

    node = new AvlNode<Integer>(22);
    avlTree.insertAvlNode(node);

    node = new AvlNode<Integer>(4);
    avlTree.insertAvlNode(node);

    node = new AvlNode<Integer>(12);
    avlTree.insertAvlNode(node);

    node = new AvlNode<Integer>(24);
    avlTree.insertAvlNode(node);

    node = new AvlNode<Integer>(10);
    avlTree.insertAvlNode(node);

    node = new AvlNode<Integer>(14);
    avlTree.insertAvlNode(node);

    String expected = " | 20 | 8 | 4 | 12 | 10 | 14 | 22 | 24";
    assertEquals(expected, avlTree.toString(),"testDeletingNodesWithTwoLeaves");

    avlTree.delete(12);
    node = avlTree.search(8);
    assertEquals(14, (int) node.getRight().getItem(),"testDeletingNodesWithTwoLeaves");
    assertEquals(" | 20 | 8 | 4 | 14 | 10 | 22 | 24",
        avlTree.toString(),"testDeletingNodesWithTwoLeaves");

    avlTree.delete(8);
    assertEquals(10, (int) avlTree.getTop().getLeft().getItem(),"testDeletingNodesWithTwoLeaves");
    assertEquals(" | 20 | 10 | 4 | 14 | 22 | 24",
        avlTree.toString(),"testDeletingNodesWithTwoLeaves");
  }

  @Test
  public void testDeletingAndRebalancing() throws Exception {
    AvlNode<Integer> node;

    node = new AvlNode<Integer>(20);
    avlTree.insertAvlNode(node);

    node = new AvlNode<Integer>(8);
    avlTree.insertAvlNode(node);

    node = new AvlNode<Integer>(22);
    avlTree.insertAvlNode(node);

    node = new AvlNode<Integer>(4);
    avlTree.insertAvlNode(node);

    node = new AvlNode<Integer>(12);
    avlTree.insertAvlNode(node);

    node = new AvlNode<Integer>(24);
    avlTree.insertAvlNode(node);

    node = new AvlNode<Integer>(10);
    avlTree.insertAvlNode(node);

    node = new AvlNode<Integer>(14);
    avlTree.insertAvlNode(node);

    assertEquals(3, avlTree.getTop().getHeight(),"testDeletingDeepLeafNode");

    avlTree.delete(22);
    assertEquals(12, (int) avlTree.getTop().getItem(),"testDeletingDeepLeafNode");
    assertEquals(avlTree.search(8), avlTree.getTop().getLeft(),"testDeletingDeepLeafNode");
    assertEquals(avlTree.search(20), avlTree.getTop().getRight(),"testDeletingDeepLeafNode");
  }

  @Test
  public void testDeletingTopNode() throws Exception {
    AvlNode<Integer> node;

    node = new AvlNode<Integer>(20);
    avlTree.insertAvlNode(node);

    node = new AvlNode<Integer>(8);
    avlTree.insertAvlNode(node);

    node = new AvlNode<Integer>(22);
    avlTree.insertAvlNode(node);

    node = new AvlNode<Integer>(4);
    avlTree.insertAvlNode(node);

    node = new AvlNode<Integer>(12);
    avlTree.insertAvlNode(node);

    node = new AvlNode<Integer>(24);
    avlTree.insertAvlNode(node);

    node = new AvlNode<Integer>(10);
    avlTree.insertAvlNode(node);

    node = new AvlNode<Integer>(14);
    avlTree.insertAvlNode(node);

    assertEquals(3, avlTree.getTop().getHeight(),"testDeletingTopNode");

    avlTree.delete(20);
    assertEquals(" | 12 | 8 | 4 | 10 | 22 | 14 | 24", avlTree.toString(),"testDeletingTopNode");
  }
}
