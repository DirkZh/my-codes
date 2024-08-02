package com.dirk.zh.cal24;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Calculator {

    private String expression;
    private int nodeIndex;
    private List<Node> nodes;

    public int compute(String expression) {

        this.expression = expression;
        this.nodeIndex = 0;
        this.nodes = new ArrayList<>();

        checkExpression();
        buildNodes();

        Node ast = plusOrMinus();

        System.out.println("\n==============" + expression + "============开始计算");
        int result = compute(ast);
        System.out.println(expression + " = " + result);
        System.out.println("==============" + expression + "============计算结束\n");

        return result;
    }

    /**
     * 检查表达式是否合法
     */
    private void checkExpression() {

        // 1.检查括号是否成对
        LinkedList<Character> stack = new LinkedList<>();
        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);
            if (isLeftParen(ch)) {
                stack.addLast(ch);
            } else if (isRightParen(ch)) {
                Character lastCh = stack.pollLast();
                if (lastCh == null) {
                    throw new IllegalArgumentException("缺少左括号：" + expression.substring(0, i));
                }
            }
        }

        if (!stack.isEmpty()) {
            throw new IllegalArgumentException("缺少右括号：" + expression);
        }

    }

    /**
     * 将计算表达式转为计算节点
     */
    private void buildNodes() {

        char[] chs = expression.toCharArray();
        for (int i = 0; i < chs.length; i++) {
            char ch = chs[i];
            if (Character.isSpaceChar(ch)) {
                continue;
            }
            if (Character.isDigit(ch)) {
                int sum = 0;
                do {
                    sum = sum * 10 + Character.digit(ch, 10);
                } while (i < chs.length - 1 && Character.isDigit((ch = chs[++i])));
                nodes.add(new NumNode(sum));
                if (i <= chs.length - 1 && ch != ' ') {
                    nodes.add(new OperNode(ch));
                }
            } else {
                nodes.add(new OperNode(ch));
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
            return ComputeUtil.compute(left, right, oper);
        }
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
        if (nextNode != null) {
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
        if (nextNode != null) {
            nodeIndex--;
        }

        return expr;
    }

    private Node nextNode() {
        if (nodeIndex == nodes.size()) {
            return null;
        }

        Node node = nodes.get(nodeIndex++);

        if (isLeftParen(node)) {
            Node subAst = plusOrMinus();
            Node nexNode = nextNode();
            if (isRightParen(nexNode)) {
                node = subAst;
            }
        }

        return node;
    }

    private boolean isProduct(Node node) {
        return node instanceof OperNode && isProduct(((OperNode) node).oper);
    }

    private boolean isLeftParen(char ch) {
        return '(' == ch;
    }

    private boolean isRightParen(char ch) {
        return ')' == ch;
    }

    private boolean isLeftParen(Node node) {
        return node instanceof OperNode && isLeftParen(((OperNode) node).oper);
    }

    private boolean isRightParen(Node node) {
        return node instanceof OperNode && isRightParen(((OperNode) node).oper);
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
