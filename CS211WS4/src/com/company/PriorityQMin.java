package com.company;
import java.util.Collections;

import java.util.PriorityQueue;

public class PriorityQMin {
    PriorityQueue<Integer> pq;

    public PriorityQMin(){
        pq = new PriorityQueue<Integer>(Collections.reverseOrder());
    }

    public void insert(int[] x) {
        for (int i = 0; i < x.length; i++) {
            pq.offer(x[i]);
        }
    }

    public int peek() {
        return pq.peek();
    }

    public int extractMin() {
        return pq.poll();
    }

    public int getSize() {
        return pq.size();
    }

    public void print() {
        System.out.println(pq);
    }

    public static void main(String[] args) {
        int[] arrA = { 12, 6, 22, 91, 14, 3, 8, 2 };
        PriorityQMin i = new PriorityQMin();
        i.insert(arrA);
        i.print();
        System.out.println("Max Element in the Priority Queue: "
                + i.extractMin());
        System.out.println("Max Element in the Priority Queue: "
                + i.extractMin());
        System.out.println("Max Element in the Priority Queue: "
                + i.extractMin());
        System.out.println("Priority Queue Size: " + i.getSize());
    }
}