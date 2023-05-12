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
      assertThat(avlTree.avlIsEmpty()).isTrue();
      avlTree.insertTop(new AvlNode(5));
      assertThat(avlTree.avlIsEmpty()).isFalse();
    }

    @DisplayName("can compere nodes")
    @Test
    public void testCompareNodes() throws Exception {
      AvlNode<Integer> node1 = new AvlNode<Integer>(4);
      AvlNode<Integer> node2 = new AvlNode<Integer>(5);
      AvlNode<Integer> node3 = new AvlNode<Integer>(5);

      assertThat(avlTree.compareNodes(node1, node2)).isEqualTo(-1);
      assertThat(avlTree.compareNodes(node3, node1)).isEqualTo(1);
      assertThat(avlTree.compareNodes(node2, node3)).isEqualTo(0);
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
      assertThat(actualValue).isNull();
    }


    @DisplayName("at the top")
    @Test
    public void testInsertTop() throws Exception {
      AvlNode<Integer> node = new AvlNode(4);
      avlTree.insertTop(node);
      assertThat( avlTree.getTop()).isEqualTo( node);
      String tree = " | 4";
      assertThat(avlTree.toString()).isEqualTo( tree);
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
      assertThat(actualValue).isNull();
    }


    @DisplayName("right and left elements after top")
    @Test
    public void testInsertingRightAndLeftElementsJustAfterTop() throws Exception {
      AvlNode<Integer> node = new AvlNode<Integer>(6);
      avlTree.insertAvlNode(node);
      AvlNode<Integer> nodeLeft = new AvlNode<Integer>(4);
      AvlNode<Integer> nodeRight = new AvlNode<Integer>(9);

      assertThat(avlTree.searchClosestNode(nodeLeft)).isEqualTo( -1);
      assertThat(nodeLeft.getClosestNode()).isEqualTo( node);
      assertThat(avlTree.searchClosestNode(nodeRight)).isEqualTo( +1);
      assertThat(nodeRight.getClosestNode()).isEqualTo( node);
      assertThat( avlTree.searchClosestNode(node)).isEqualTo( 0);

      node.setLeft(nodeLeft);
      node.setRight(nodeRight);
      AvlNode<Integer> nodeRightLeft = new AvlNode<Integer>(7);
      avlTree.searchClosestNode(nodeRightLeft);
      assertThat(avlTree.searchClosestNode(nodeRightLeft)).isEqualTo( -1);
      assertThat(nodeRightLeft.getClosestNode()).isEqualTo( nodeRight);

      AvlNode<Integer> nodeLeftRight = new AvlNode<Integer>(5);
      assertThat(avlTree.searchClosestNode(nodeLeftRight)).isEqualTo( 1);
      assertThat(nodeLeftRight.getClosestNode()).isEqualTo( nodeLeft);

      String tree = " | 6 | 4 | 9";
      assertThat(avlTree.toString()).isEqualTo( tree);
    }

    @DisplayName("left element")
    @Test
    public void testInsertingLeftElement() throws Exception {
      AvlNode<Integer> node = new AvlNode<Integer>(6);
      avlTree.insertAvlNode(node);
      AvlNode<Integer> nodeLeft = new AvlNode<Integer>(4);
      avlTree.insertAvlNode(nodeLeft);

      assertThat( nodeLeft.getParent()).isEqualTo( node);
      assertThat( node.getLeft()).isEqualTo( nodeLeft);

      String tree = " | 6 | 4";
      assertThat(avlTree.toString()).isEqualTo( tree);
    }

    @DisplayName("right element")
    @Test
    public void testInsertingRightElement() throws Exception {
      AvlNode<Integer> node = new AvlNode<Integer>(6);
      avlTree.insertAvlNode(node);
      AvlNode<Integer> nodeRight = new AvlNode<Integer>(9);
      avlTree.insertAvlNode(nodeRight);

      assertThat(nodeRight.getParent()).isEqualTo( node);
      assertThat(node.getRight()).isEqualTo( nodeRight);

      String tree = " | 6 | 9";
      assertThat(avlTree.toString()).isEqualTo( tree);
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
      assertThat( node1.getHeight()).isEqualTo( 0);
      assertThat( avlTree.getBalance(node1)).isEqualTo( 0);

      node2 = new AvlNode<Integer>(4);
      avlTree.insertAvlNode(node2);
      assertThat(node2.getHeight()).isEqualTo( 0);
      assertThat(node1.getHeight()).isEqualTo( 1);
      assertThat(avlTree.getBalance(node1)).isEqualTo( -1);
      assertThat( avlTree.getBalance(node2)).isEqualTo( 0);

      node3 = new AvlNode<Integer>(3);
      avlTree.insertAvlNode(node3);
      assertThat(avlTree.getTop()).isEqualTo( node2);
      assertThat( node2.getLeft()).isEqualTo( node3);
      assertThat( node2.getRight()).isEqualTo( node1);

      assertThat( avlTree.getTop().getHeight()).isEqualTo( 1);
      assertThat( avlTree.getTop().getLeft().getHeight()).isEqualTo( 0);
      assertThat( avlTree.getTop().getRight().getHeight()).isEqualTo( 0);
      assertThat( avlTree.height(node1.getLeft())).isEqualTo( -1);
      assertThat( avlTree.height(node1.getRight())).isEqualTo( -1);
      assertThat( avlTree.height(node3.getLeft())).isEqualTo( -1);
      assertThat( avlTree.height(node3.getRight())).isEqualTo( -1);

      String tree = " | 4 | 3 | 7";
      assertThat(avlTree.toString()).isEqualTo( tree);
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
      assertThat( node1.getHeight()).isEqualTo( 0);
      assertThat( avlTree.getBalance(node1)).isEqualTo( 0);

      node2 = new AvlNode<Integer>(10);
      avlTree.insertAvlNode(node2);
      assertThat( node2.getHeight()).isEqualTo( 0);
      assertThat( node1.getHeight()).isEqualTo( 1);
      assertThat( avlTree.getBalance(node1)).isEqualTo( 1);
      assertThat( avlTree.getBalance(node2)).isEqualTo( 0);

      node3 = new AvlNode<Integer>(14);
      avlTree.insertAvlNode(node3);
      assertThat(avlTree.getTop()).isEqualTo( node2);
      assertThat(node2.getLeft()).isEqualTo( node1);
      assertThat(node2.getRight()).isEqualTo( node3);

      assertThat(avlTree.getTop().getHeight()).isEqualTo( 1);
      assertThat( avlTree.getTop().getLeft().getHeight()).isEqualTo( 0);
      assertThat( avlTree.getTop().getRight().getHeight()).isEqualTo( 0);
      assertThat( avlTree.height(node1.getLeft())).isEqualTo( -1);
      assertThat(avlTree.height(node1.getRight())).isEqualTo( -1);
      assertThat(avlTree.height(node3.getLeft())).isEqualTo( -1);
      assertThat(avlTree.height(node3.getRight())).isEqualTo( -1);

      String tree = " | 10 | 7 | 14";
      assertThat(avlTree.toString()).isEqualTo( tree);
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

      assertThat(avlTree.getTop()).isEqualTo( node2);
      assertThat(node2.getLeft()).isEqualTo( node4);
      assertThat(node2.getRight()).isEqualTo( node1);
      assertThat(node4.getLeft()).isEqualTo( node5);
      assertThat(node4.getRight()).isEqualTo( node3);
      assertThat(node1.getHeight()).isEqualTo( 0);
      assertThat(node2.getHeight()).isEqualTo( 2);
      assertThat(node4.getHeight()).isEqualTo( 1);

      String tree = " | 4 | 2 | 1 | 3 | 7";
      assertThat(avlTree.toString()).isEqualTo( tree);
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

      assertThat(avlTree.getTop()).isEqualTo( node2);
      assertThat(node2.getRight()).isEqualTo( node4);
      assertThat(node2.getLeft()).isEqualTo( node1);
      assertThat( node4.getRight()).isEqualTo( node5);
      assertThat( node4.getLeft()).isEqualTo( node3);
      assertThat(avlTree.getTop().getHeight()).isEqualTo( 2);
      assertThat(node4.getHeight()).isEqualTo( 1);
      assertThat(node1.getHeight()).isEqualTo( 0);

      String tree = " | 8 | 7 | 10 | 9 | 11";
      assertThat(avlTree.toString()).isEqualTo( tree);
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

      assertThat(avlTree.getTop()).isEqualTo( node3);
      assertThat(node3.getLeft()).isEqualTo( node2);
      assertThat(node3.getRight()).isEqualTo( node1);

      assertThat(avlTree.getTop().getHeight()).isEqualTo( 1);
      assertThat(avlTree.getTop().getLeft().getHeight()).isEqualTo( 0);
      assertThat(  avlTree.getTop().getRight().getHeight()).isEqualTo( 0);
      assertThat( avlTree.height(node2.getLeft())).isEqualTo( -1);
      assertThat(avlTree.height(node2.getRight())).isEqualTo( -1);
      assertThat(avlTree.height(node1.getLeft())).isEqualTo( -1);
      assertThat(avlTree.height(node1.getLeft())).isEqualTo( -1);
      assertThat(avlTree.height(node1.getRight())).isEqualTo( -1);

      String tree = " | 3 | 2 | 7";
      assertThat(avlTree.toString()).isEqualTo( tree);
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