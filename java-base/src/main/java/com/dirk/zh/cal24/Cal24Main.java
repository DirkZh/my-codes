package com.dirk.zh.cal24;

import java.util.ArrayList;
import java.util.List;

public class Cal24Main {

    public static void main(String[] args) {
        int result = new Cal24Main().compute("1+ 2*3*4/2-5-6");
        System.out.println("result = " + result);
    }

    int nodeIndex;
    List<Node> nodes = new ArrayList<>();

    public int compute(String expression) {

        buildNodes(expression.toCharArray());

        Node ast = plusOrMinus();

        return compute(ast);
    }

    /**
     * 将计算表达式转为计算节点
     */
    private void buildNodes(char[] chs) {

        for (int i = 0; i < chs.length; i++) {
            char ch = chs[i];
            if (Character.isDigit(ch)) {
                int sum = 0;
                do {
                    sum = sum * 10 + Character.digit(ch, 10);
                } while (i < chs.length - 1 && Character.isDigit((ch = chs[++i])));
                nodes.add(new NumNode(sum));
                if (i < chs.length - 1 && ch!=' ') {
                    nodes.add(new OperNode(ch));
                }
            }
        }

    }

    private int compute(Node node) {

        if (node instanceof NumNode) {
            return ((NumNode) node).value;
        } else {
            OperNode operNode = (OperNode) node;
            int left = compute(operNode.left);
            int right = compute(operNode.right);
            char oper = operNode.oper;
            return compute(left, right, oper);
        }
    }

    private int compute(int left, int right, char oper) {
        int result = 0;
        if (oper == '+') {
            result = left + right;
            System.out.println(left + "+" + right + "=" + result);
        } else if (oper == '-') {
            result = left - right;
            System.out.println(left + "-" + right + "=" + result);
        } else if (oper == '*') {
            result = left * right;
            System.out.println(left + "*" + right + "=" + result);
        } else if (oper == '/') {
            result = left / right;
            System.out.println(left + "/" + right + "=" + result);
        }
        return result;
    }

    /**
     * 构造加法或者减法的节点
     */
    private Node plusOrMinus() {

        Node expr = multiplyOrDivide();

        Node nextNode = nextNode();
        while (isSum(nextNode)) {
            OperNode operNode = (OperNode) nextNode;
            operNode.left = expr;
            operNode.right = multiplyOrDivide();
            expr = nextNode;
            nextNode = nextNode();
        }
        if (nodeIndex < nodes.size()) {
            nodeIndex--;
        }

        return expr;
    }


    /**
     * 构造乘法或者除法的节点
     */
    private Node multiplyOrDivide() {

        Node expr = nextNode();

        Node nextNode = nextNode();
        while (isProduct(nextNode)) {
            OperNode operNode = (OperNode) nextNode;
            operNode.left = expr;
            operNode.right = nextNode();
            expr = nextNode;
            nextNode = nextNode();
        }
        // 节点索引判断不是乘除，需要退回一个位置
        if (nodeIndex < nodes.size()) {
            nodeIndex--;
        }

        return expr;
    }

    private Node nextNode() {
        if (nodeIndex == nodes.size()) {
            return null;
        }

        return nodes.get(nodeIndex++);
    }

    private boolean isProduct(Node node) {
        return node instanceof OperNode && isProduct(((OperNode) node).oper);
    }

    private boolean isSum(Node node) {
        return node instanceof OperNode && isSum(((OperNode) node).oper);
    }

    private boolean isProduct(char ch) {
        return ch == '*' || ch == '/';
    }

    private boolean isSum(char ch) {
        return ch == '+' || ch == '-';
    }

}
