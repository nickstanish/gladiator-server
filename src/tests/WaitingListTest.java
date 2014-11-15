package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import net.vizbits.gladiator.server.WaitingQueue;

import org.junit.Before;
import org.junit.Test;

public class WaitingListTest {

  WaitingQueue waitingQueue;
  String first = "User1";
  String second = "User2";
  String third = "User3";

  @Before
  public void setUp() throws Exception {
    waitingQueue = new WaitingQueue();
    waitingQueue.enqueue(first);
    waitingQueue.enqueue(second);
    waitingQueue.enqueue(third);
  }

  @Test
  public void testDequeue() {
    assertTrue("Sanity Check", true);
    assertTrue(waitingQueue.dequeue().equals(first));
    assertTrue(waitingQueue.dequeue().equals(second));
    assertTrue(waitingQueue.dequeue().equals(third));
    assertTrue(waitingQueue.dequeue() == null);
  }

  @Test
  public void testEnqueue() {
    waitingQueue.clear();
    waitingQueue.enqueue(first);
    assertTrue(waitingQueue.dequeue().equals(first));
  }

  @Test
  public void testHasWaiting() {
    assertTrue(waitingQueue.hasWaiting());
    waitingQueue.clear();
    assertFalse(waitingQueue.hasWaiting());
    waitingQueue.enqueue(first);
    assertTrue(waitingQueue.hasWaiting());
  }

  @Test
  public void testSize() {
    assertTrue(waitingQueue.size() == 3);
    waitingQueue.dequeue();
    assertTrue(waitingQueue.size() == 2);
    waitingQueue.enqueue(first);
    assertTrue(waitingQueue.size() == 3);
    waitingQueue.clear();
    assertTrue(waitingQueue.size() == 0);
  }

  @Test
  public void testPositionInQueue() {
    assertTrue(waitingQueue.positionInQueue(first) == 0);
    assertTrue(waitingQueue.positionInQueue(second) == 1);
    assertTrue(waitingQueue.positionInQueue(third) == 2);
    waitingQueue.dequeue();
    assertTrue(waitingQueue.positionInQueue(second) == 0);
  }

}
