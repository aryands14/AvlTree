package avl;

import java.util.Comparator;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.*;

/**
 * Created with IntelliJ IDEA. User: Antonio J. Nebro Date: 08/07/13
 */

@DisplayName("An AVL Tree")
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

  @Nested
  public class states{
    @DisplayName("can be Empty")
    @Test
    public void testAvlIsEmpty() throws Exception {
      assertTrue(avlTree.avlIsEmpty(), "TestAvlIsEmpty");

      avlTree.insertTop(new AvlNode(5));
      assertFalse(avlTree.avlIsEmpty(), "TestAvlIsEmpty");
    }

    @DisplayName("can compere nodes")
    @Test
    public void testCompareNodes() throws Exception {
      AvlNode<Integer> node1 = new AvlNode<Integer>(4);
      AvlNode<Integer> node2 = new AvlNode<Integer>(5);
      AvlNode<Integer> node3 = new AvlNode<Integer>(5);

      assertEquals(-1, avlTree.compareNodes(node1, node2), "testCompareNodes");
      assertEquals(1, avlTree.compareNodes(node3, node1), "testCompareNodes");
      assertEquals(0, avlTree.compareNodes(node2, node3), "testCompareNodes");
    }

  }

  @Nested
  @DisplayName("when inserting")
  public class inserting{

    @DisplayName("a valid element")
    @Test
    public void testInsertValidElement(){
      AvlNode<Integer> node = new AvlNode<>(5);
      avlTree.insertTop(node);

      AvlNode<Integer> actualValue = avlTree.getTop();
      assertThat(actualValue).isEqualTo(node);
    }

    @DisplayName("a invalid element")
    @Test
    public void testInsertInvalidElement(){
      AvlNode<Integer> node = null;
      avlTree.insertTop(node);

      AvlNode<Integer> actualValue = avlTree.getTop();
      assertNull(actualValue);
    }


    @DisplayName("at the top")
    @Test
    public void testInsertTop() throws Exception {
      AvlNode<Integer> node = new AvlNode(4);
      avlTree.insertTop(node);
      assertEquals( node, avlTree.getTop(), "TestInsertTop");
      String tree = " | 4";
      assertEquals(tree, avlTree.toString(),"TestInsertTop");
    }

    @DisplayName("a different element")
    @Test
    public void testInsertDifferentElement(){
      AvlNode<Integer> root = new AvlNode<Integer>(1);
      Integer node = 2;

      avlTree.insertTop(root);
      avlTree.insert(node);

      AvlNode<Integer> actualValue = avlTree.findSuccessor(root);
      assertThat(actualValue.getItem()).isEqualTo(node);
    }

    @DisplayName("an equals element")
    @Test
    public void testInsertEqualsElement(){
      AvlNode<Integer> root = new AvlNode<Integer>(1);
      Integer node = 1;

      avlTree.insertTop(root);
      avlTree.insert(node);

      AvlNode<Integer> actualValue = avlTree.findSuccessor(root);
      assertNull(actualValue);
    }


    @DisplayName("right and left elements after top")
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

    @DisplayName("left element")
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

    @DisplayName("right element")
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
     * Testing adding 7 - 4 - 3
     *
     * @throws Exception
     */
    @DisplayName("inserting 7,4 and 3")
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
    @DisplayName("inserting 7,10 and 14")
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
    @DisplayName("inserting 7,4,3,2 and 1")
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

    @DisplayName("inserting 7,8,9,10 and 11")
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
    @DisplayName("left and right element and rebalance")
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
    @DisplayName("left node and rebalance")
    @Test
    public void testInsertingRightLeftNodeAndRebalance() throws Exception {
      AvlNode<Integer> node1, node2, node3;

      node1 = new AvlNode<Integer>(7);
      avlTree.insertAvlNode(node1);

      node2 = new AvlNode<Integer>(9);
      avlTree.insertAvlNode(node2);

      node3 = new AvlNode<Integer>(8);
      avlTree.insertAvlNode(node3);

      assertThat(avlTree.getTop()).isEqualTo(node3);
      assertThat(node3.getLeft()).isEqualTo(node1);
      assertThat(node3.getRight()).isEqualTo(node2);

      assertThat(avlTree.getTop().getHeight()).isEqualTo(1);
      assertThat(avlTree.getTop().getLeft().getHeight()).isEqualTo(0);
      assertThat(avlTree.getTop().getRight().getHeight()).isEqualTo(0);
      assertThat(avlTree.height(node2.getLeft())).isEqualTo(-1);
      assertThat(avlTree.height(node2.getRight())).isEqualTo(-1);
      assertThat(avlTree.height(node1.getLeft())).isEqualTo(-1);
      assertThat(avlTree.height(node1.getRight())).isEqualTo(-1);

      String tree = " | 8 | 7 | 9";
      assertThat(avlTree.toString()).isEqualTo(tree);
    }

    /**
     * Test adding 7 - 4 - 9 - 3 - 5
     *
     * @throws Exception
     */
    @DisplayName("inserting 7,4,9,3 and 5")
    @Test
    public void testHeightAndBalanceOfASimpleBalancedTree() throws Exception {
      AvlNode<Integer> node1, node2, node3, node4, node5;

      node1 = new AvlNode<Integer>(7);
      avlTree.insertAvlNode(node1);
      assertThat(node1.getHeight()).isEqualTo(0);
      assertThat(avlTree.getBalance(node1)).isEqualTo(0);

      node2 = new AvlNode<Integer>(4);
      avlTree.insertAvlNode(node2);
      assertThat(node2.getHeight()).isEqualTo(0);
      assertThat(node1.getHeight()).isEqualTo(1);
      assertThat(avlTree.getBalance(node1)).isEqualTo(-1);
      assertThat(avlTree.getBalance(node2)).isEqualTo(0);

      node3 = new AvlNode<Integer>(9);
      avlTree.insertAvlNode(node3);
      assertThat(node3.getHeight()).isEqualTo(0);
      assertThat(node1.getHeight()).isEqualTo(1);
      assertThat(avlTree.getBalance(node1)).isEqualTo(0);
      assertThat(avlTree.getBalance(node3)).isEqualTo(0);

      node4 = new AvlNode<Integer>(3);
      avlTree.insertAvlNode(node4);
      assertThat(node4.getHeight()).isEqualTo(0);
      assertThat(node2.getHeight()).isEqualTo(1);
      assertThat(node1.getHeight()).isEqualTo(2);
      assertThat(avlTree.getBalance(node2)).isEqualTo(-1);
      assertThat(avlTree.getBalance(node1)).isEqualTo(-1);
      assertThat(avlTree.getBalance(node4)).isEqualTo(0);

      node5 = new AvlNode<Integer>(5);
      avlTree.insertAvlNode(node5);
      assertThat(node5.getHeight()).isEqualTo(0);
      assertThat(node2.getHeight()).isEqualTo(1);
      assertThat(node1.getHeight()).isEqualTo(2);
      assertThat(avlTree.getBalance(node2)).isEqualTo(0);
      assertThat(avlTree.getBalance(node1)).isEqualTo(-1);
      assertThat(avlTree.getBalance(node5)).isEqualTo(0);

      String tree = " | 7 | 4 | 3 | 5 | 9";
      assertThat(avlTree.toString()).isEqualTo(tree);
    }




  }






  /*
  @Test
  public void testInsertingTheFirstElement() throws Exception {
    AvlNode<Integer> node = new AvlNode<Integer>(6) ;
    avlTree_.insertAvlNode(node);
    assertEquals("testInsertingTheFirstElement", node, avlTree_.getTop());
  }
  */

  @DisplayName("can search closest node")
  @Test
  public void testSearchClosestNode() throws Exception {
    int result;
    AvlNode<Integer> node = new AvlNode<Integer>(7);
    result = avlTree.searchClosestNode(node);
    assertThat(result).isEqualTo(0);
    avlTree.insertAvlNode(node);

    node = new AvlNode<Integer>(4);
    result = avlTree.searchClosestNode(node);
    assertThat(result).isEqualTo(-1);
    avlTree.insertAvlNode(node);

    node = new AvlNode<Integer>(9);
    result = avlTree.searchClosestNode(node);
    assertThat(result).isEqualTo(1);
    avlTree.insertAvlNode(node);

    node = new AvlNode<Integer>(6);
    result = avlTree.searchClosestNode(node);
    assertThat(result).isEqualTo(1);
    avlTree.insertAvlNode(node);

    node = new AvlNode<Integer>(8);
    result = avlTree.searchClosestNode(node);
    assertThat(result).isEqualTo(-1);
    avlTree.insertAvlNode(node);

    String tree = " | 7 | 4 | 6 | 9 | 8";
    assertThat(avlTree.toString()).isEqualTo(tree);
  }

  @DisplayName("can search nodes")
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

    assertThat(avlTree.search(7)).isEqualTo(node1);
    assertThat(avlTree.search(9)).isEqualTo(node2);
    assertThat(avlTree.search(8)).isEqualTo(node3);
    assertThat(avlTree.searchNode(new AvlNode<Integer>(2)).getItem()).isEqualTo((Integer) 2);
    assertThat(avlTree.search(2)).isEqualTo(node4);
    assertThat(avlTree.search(3)).isEqualTo(node5);
    assertThat(avlTree.search(14)).isNull();
    assertThat(avlTree.search(0)).isNull();

    String tree = " | 8 | 3 | 2 | 7 | 9";
    assertThat(avlTree.toString()).isEqualTo(tree);
  }

  @DisplayName("can find successors")
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
    assertThat( avlTree.findSuccessor(node)).isEqualTo(avlTree.search(10));
    node = avlTree.search(10);
    assertThat( avlTree.findSuccessor(node)).isEqualTo(avlTree.search(12));
    node = avlTree.search(14);
    assertThat( avlTree.findSuccessor(node)).isEqualTo(avlTree.search(20));

    String tree = " | 20 | 8 | 4 | 12 | 10 | 14 | 22 | 24";
    assertThat(avlTree.toString()).isEqualTo(tree);
  }

  @DisplayName("when deleting")
  @Nested
  public class deleting{

    @DisplayName("leaf nodes")
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
      assertThat(node3.getRight()).isEqualTo(null);
      assertThat(node3.getHeight()).isEqualTo(0);
      assertThat(avlTree.getTop().getHeight()).isEqualTo(2);
      assertThat(avlTree.toString()).isEqualTo(" | 7 | 2 | 9 | 8");

      avlTree.delete(8); // left leaf node
      assertThat(node2.getLeft()).isEqualTo(null);
      assertThat(node2.getHeight()).isEqualTo(0);
      assertThat(avlTree.getTop().getHeight()).isEqualTo(1);
      assertThat(avlTree.toString()).isEqualTo(" | 7 | 2 | 9");

      avlTree.delete(2); // left leaf node
      assertThat(node1.getLeft()).isEqualTo(null);
      assertThat(node1.getHeight()).isEqualTo(1);
      assertThat(avlTree.toString()).isEqualTo(" | 7 | 9");

      avlTree.delete(9); // right leaf node
      assertThat(node1.getRight()).isEqualTo(null);
      assertThat(node1.getHeight()).isEqualTo(0);
      assertThat(avlTree.toString()).isEqualTo(" | 7");

      avlTree.delete(7); // left leaf node
      assertThat(avlTree.getTop()).isEqualTo(null);
      assertThat(avlTree.toString()).isEqualTo("");
    }

    @DisplayName("nodes with one leaf")
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
      assertThat(avlTree.toString()).isEqualTo(tree);

      avlTree.delete(2);
      assertThat(node1.getLeft().getItem()).isEqualTo(node3.getItem());

      assertThat(node3.getRight()).isEqualTo(null);
      assertThat(node3.getHeight()).isEqualTo(0);
      assertThat(avlTree.getTop().getHeight()).isEqualTo(2);
      assertThat(avlTree.toString()).isEqualTo(" | 7 | 3 | 9 | 8");

      avlTree.delete(9);
      assertThat(node1.getRight().getItem()).isEqualTo(node2.getItem());
      assertThat(node2.getLeft()).isEqualTo(null);

      assertThat(node2.getHeight()).isEqualTo(0);
      assertThat(avlTree.getTop().getHeight()).isEqualTo(1);
      assertThat(avlTree.toString()).isEqualTo(" | 7 | 3 | 8");
    }

    @DisplayName("nodes with two leaves")
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
      assertThat(avlTree.toString()).isEqualTo(expected);

      avlTree.delete(12);
      node = avlTree.search(8);
      assertThat((int) node.getRight().getItem()).isEqualTo(14);

      assertThat(avlTree.toString()).isEqualTo(" | 20 | 8 | 4 | 14 | 10 | 22 | 24");

      avlTree.delete(8);

      assertThat((int) avlTree.getTop().getLeft().getItem()).isEqualTo(10);

      assertThat(avlTree.toString()).isEqualTo(" | 20 | 10 | 4 | 14 | 22 | 24");
    }

    @DisplayName("and rebalancing")
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

      assertThat(avlTree.getTop().getHeight()).isEqualTo(3);
      avlTree.delete(22);
      assertThat((int) avlTree.getTop().getItem()).isEqualTo(12);
      assertThat(avlTree.getTop().getLeft()).isEqualTo(avlTree.search(8));
      assertThat(avlTree.getTop().getRight()).isEqualTo(avlTree.search(20));
    }

    @DisplayName("top nodes")
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

      assertThat(3).isEqualTo(avlTree.getTop().getHeight());
      avlTree.delete(20);
      assertThat(" | 12 | 8 | 4 | 10 | 22 | 14 | 24").isEqualTo(avlTree.toString());
    }

  }

  @Test
  @DisplayName("search returns null node when finding non existent element")
  void testSearchWhenNodeDoesNotExistInTree() {
    avlTree.insert(1);
    avlTree.insert(2);
    avlTree.insert(3);
    assertThat(avlTree.search(4)).isNull();
  }

  @Test
  @DisplayName("search returns null node when tree is empty")
  void testSearchOnNullTree() {
    assertThat(avlTree.search(4)).isNull();
  }

  @Test
  @DisplayName("search returns correct node when searching existing node.")
  void testSearchWhenNodeExistsInTree() {
    avlTree.insert(1);
    avlTree.insert(2);
    avlTree.insert(3);
    AvlNode<Integer> node = avlTree.search(3);

    Integer expectedValue = 3;
    Integer actualValue = node.getItem();

    assertThat(node).isNotNull();
    assertThat(expectedValue).isEqualTo(actualValue);
  }

}