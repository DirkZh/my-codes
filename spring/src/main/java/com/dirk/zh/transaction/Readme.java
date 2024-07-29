package com.dirk.zh.transaction;

import org.springframework.transaction.support.TransactionSynchronizationManager;

public class Readme {

    public static void main(String[] args) {
        // 判断当前方法是否存在事务
        boolean actualTransactionActive = TransactionSynchronizationManager.isActualTransactionActive();
    }

}
