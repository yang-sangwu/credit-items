package com.credit;

import com.jayway.jsonpath.internal.function.numeric.Max;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Stack;

public class TestSuanFa {


    private Node head;

    @Test
    public void MinStack() {
        push(-2);
        push(0);
        push(-3);
        System.out.println(min());
        pop();
        System.out.println(top());
        System.out.println(min());

    }

    public void push(int x) {
        if (head == null) {
            head = new Node(x, x, null);
        } else {
            head = new Node(x, Math.min(x, head.min), head);
        }
    }

    public void pop() {
        head = head.next;
    }

    public int top() {
        return head.val;
    }

    public int min() {
        return head.min;
    }

    private class Node {
        int val;
        int min;
        Node next;

        public Node(int val, int min, Node next) {
            this.val = val;
            this.min = min;
            this.next = next;
        }
    }

    public double trimMean(int[] arr) {
        Arrays.sort(arr);
        int n = arr.length;
        int sum = 0;
        for (int i = n / 20; i <= (n * 19 / 20); i++) {
            sum += arr[i];
        }

        return sum / (n * 9 / 10);
    }

    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(' || s.charAt(i) == '{' || s.charAt(i) == '[') {
                stack.push(s.charAt(i));
            }
            if (s.charAt(i) == ')') {
                if (stack.isEmpty()) return false;
                if (stack.pop() != '(') return false;
            } else if (s.charAt(i) == ']') {
                if (stack.isEmpty()) return false;
                if (stack.pop() != '[') return false;
            } else if (s.charAt(i) == '}') {
                if (stack.isEmpty()) return false;
                if (stack.pop() != '{') return false;
            }
        }
        return stack.isEmpty();

    }

    @Test
    public void test() {
        int[] arr = {1, 3, 5, 6};
        searchInsert(arr, 7);
    }

    public int searchInsert(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        int middle = 0;
        if (nums[right] < target) {
            return right + 1;
        }
        while (left <= right) {
            middle = (left + right) / 2;
            if (nums[middle] < target) {
                left = middle + 1;
            } else {
                right = middle - 1;
            }
        }
        return left;
    }

    @Test
    public void test1() {
        String s = "aa";
        maxLengthBetweenEqualCharacters(s);
    }

    @Test
    public boolean isThree(int n) {
        int num = 0;
        for (int i = 2; i < n; i++) {
            if (n % i == 0) {
                num++;
            }
        }
        if (num == 3) {
            return true;
        }
        return false;
    }

    @Test
    public int findMiddleIndex(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        int i = 1, j = nums.length - 2;
        int left = nums[0], right = nums[j+1];
        while (i < j) {
            if (left<right){
                i++;
            }else if (left>right){
                j--;
            }

        }
        return 0;
    }

    public int maxLengthBetweenEqualCharacters(String s) {
        if (s == null) {
            return -1;
        }
        int res = -1;
        int max = -1;
        for (int i = 0; i < s.length() - 1; i++) {
            for (int j = s.length() - 1; j > i; j--) {
                if (s.charAt(i) == s.charAt(j)) {
                    max = j - i + 1;
                    res = Math.max(max, res);
                    break;
                }
            }
        }
        return res;
    }
}
