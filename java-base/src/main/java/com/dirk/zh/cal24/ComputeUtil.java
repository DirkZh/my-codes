package com.dirk.zh.cal24;

/**
 * @author 周立伟
 * @since 2024-08-02 15:25
 */
public class ComputeUtil {

    public static int compute(int left, int right, char oper) {
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

}
