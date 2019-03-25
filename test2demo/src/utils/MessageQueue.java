package utils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MessageQueue {
    private static volatile BlockingQueue<DataMsg> queue = new LinkedBlockingQueue();
    private static MessageQueue messageQueue;

    private MessageQueue() {
    }

    public static MessageQueue getInstance() {
        if (messageQueue == null) {
            messageQueue = new MessageQueue();
        }

        return messageQueue;
    }

    public DataMsg getMessage() {
        return queue.isEmpty() ? null : (DataMsg) queue.poll();
    }

    public void addMessage(DataMsg message) {
        queue.offer(message);
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public int size() {
        return queue.size();
    }

    public static void main(String[] args) {
        MessageQueue queue = getInstance();
        System.out.println(queue.hashCode());
    }
}

