package com.company;

public class Main {

    public static void main(String[] args) {//Runs the main program
        int[] arrA = { 12, 6, 22, 91, 14, 3, 8, 2 };
        PriorityQMin i = new PriorityQMin();
        i.insert(arrA);
        i.print();
        System.out.println("Min Element in the Priority Queue: "
                + i.extractMin());
        System.out.println("Min Element in the Priority Queue: "
                + i.extractMin());
        System.out.println("Min Element in the Priority Queue: "
                + i.extractMin());
        System.out.println("Priority Queue Size: " + i.getSize());
    }
}
