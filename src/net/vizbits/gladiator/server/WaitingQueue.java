package net.vizbits.gladiator.server;

import java.util.ArrayList;
import java.util.List;

public class WaitingQueue {
  private List<String> queue;

  public WaitingQueue() {
    queue = new ArrayList<String>();
  }

  public void clear() {
    synchronized (queue) {
      queue.clear();
    }
  }

  public String dequeue() {
    synchronized (queue) {
      if (queue.size() == 0)
        return null;
      return queue.remove(0);
    }
  }

  public void enqueue(String username) {
    synchronized (queue) {
      queue.add(username);
    }
  }

  public boolean hasWaiting() {
    synchronized (queue) {
      return queue.size() > 0;
    }
  }

  public int size() {
    synchronized (queue) {
      return queue.size();
    }
  }

  public int positionInQueue(String username) {
    synchronized (queue) {
      return queue.indexOf(username);
    }
  }


}
