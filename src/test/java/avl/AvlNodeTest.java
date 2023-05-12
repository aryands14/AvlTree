package avl;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Created with IntelliJ IDEA. User: Antonio J. Nebro Date: 09/07/13 Time: 15:29
 */
@DisplayName("Testing class AvlNode:")
public class AvlNodeTest {

  private AvlNode<Integer> node;

  @BeforeEach
  public void setUp() throws Exception {
    node = new AvlNode<Integer>(5);
  }

  @AfterEach
  public void tearDown() throws Exception {
    node = null;
  }

  @Test
  @DisplayName("setLeft method inserts the child node on the left of the parent node.")
  public void testHasLeft() {
    assertFalse(node.hasLeft(), "testHasLeft");
    AvlNode<Integer> node2 = new AvlNode<Integer>(6);
    node.setLeft(node2);
    assertTrue(node.hasLeft(), "testHasLeft");
  }

  @Test
  @DisplayName("setRight method inserts the child node on the right of the parent node.")
  public void testHasRight() {
    assertFalse(node.hasRight(), "testHasRight");
    AvlNode<Integer> node2 = new AvlNode<Integer>(6);
    node.setRight(node2);
    assertTrue(node.hasRight(), "testHasRight");
  }

  @Test
  @DisplayName("node only has left child")
  public void testOnlyHasRightChild(){
    node.setRight(new AvlNode<>(6));
    assertTrue(node.hasOnlyARightChild());
  }

  @Test
  @DisplayName("node height is set correctly.")
  public void shouldSetHeight() {
    int expectedHeight = 1000213;
    node.setHeight(expectedHeight);
    assertEquals(expectedHeight, node.getHeight(), "Height is different from expected.");
  }

}
