package edu.gwu.cs.ds;

import java.security.SecureRandom;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

public class DataStructures {

    public static void main(String[] args) {
        priorityQueueExample();
        stackExample();
        queueExample();
    }

    private static void priorityQueueExample() {
        PriorityQueue<Integer> queue = new PriorityQueue<Integer>();
        Random random = new SecureRandom();
        int expCount = 4000000;
        for (int i = 0; i < expCount; i++) {
            queue.add(random.nextInt());
        }
        int lastPopped = Integer.MIN_VALUE;
        long startTime = System.currentTimeMillis();
        while (!queue.isEmpty()) {
            int next = queue.poll();
            if (next < lastPopped) {
                throw new RuntimeException("Heap property broken!");
            }
            lastPopped = next;
        }
        System.out.println("Elapsed time: " + (System.currentTimeMillis() - startTime));
    }

    private static void queueExample() {
        Queue<String> queue = new PriorityQueue<>();
        queue.add("one");
        queue.add("two");
        queue.add("three");
        String queueHead = queue.remove(); // Returns “one”
        System.out.println("queueHead: " + queueHead);
        queueHead = queue.remove(); // Returns “one”
        System.out.println("queueHead: " + queueHead);
    }

    private static void stackExample() {
        Stack<String> stack = new Stack<String>();
        stack.push("one");
        stack.push("two");
        stack.push("three");
        String stackTop = stack.pop(); // Returns “three”
        System.out.println("stackTop: " + stackTop);
        stackTop = stack.pop(); // Returns “three”
        System.out.println("stackTop: " + stackTop);
    }

}
