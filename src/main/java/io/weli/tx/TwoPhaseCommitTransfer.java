package io.weli.tx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TwoPhaseCommitTransfer {
    // 数据库连接信息
    private static final String DB_URL = "jdbc:mysql://localhost:3306/bank";
    private static final String USER = "username";
    private static final String PASS = "password";

    // 事务状态
    private enum TransactionState {
        PREPARE, COMMIT, ROLLBACK
    }

    // 转账方法
    public boolean transfer(String fromAccount, String toAccount, double amount) {
        Connection fromConn = null;
        Connection toConn = null;
        boolean success = false;

        try {
            // 1. 获取两个账户的独立连接
            fromConn = DriverManager.getConnection(DB_URL, USER, PASS);
            toConn = DriverManager.getConnection(DB_URL, USER, PASS);

            // 关闭自动提交，开始事务
            fromConn.setAutoCommit(false);
            toConn.setAutoCommit(false);

            // 2. 第一阶段：准备阶段 (Prepare Phase)
            boolean fromPrepared = preparePhase(fromConn, fromAccount, -amount);
            boolean toPrepared = preparePhase(toConn, toAccount, amount);

            if (fromPrepared && toPrepared) {
                // 3. 第二阶段：提交阶段 (Commit Phase)
                commitPhase(fromConn);
                commitPhase(toConn);
                success = true;
                System.out.println("转账成功: " + amount + " 从 " + fromAccount + " 到 " + toAccount);
            } else {
                // 准备阶段失败，执行回滚
                rollback(fromConn, toConn, fromPrepared, toPrepared);
                System.out.println("转账失败: 准备阶段未通过");
            }

        } catch (SQLException e) {
            // 发生异常，执行回滚
            rollback(fromConn, toConn, false, false);
            System.err.println("转账过程中发生异常: " + e.getMessage());
        } finally {
            // 关闭连接
            closeConnection(fromConn);
            closeConnection(toConn);
        }

        return success;
    }

    // 准备阶段
    private boolean preparePhase(Connection conn, String account, double amount) throws SQLException {
        // 1. 检查账户是否存在
        if (!accountExists(conn, account)) {
            return false;
        }

        // 2. 检查余额是否足够(对于转出账户)
        if (amount < 0 && !hasSufficientBalance(conn, account, -amount)) {
            return false;
        }

        // 3. 预扣款/预存款 (实际业务中可能使用临时表或状态字段)
        String sql = "UPDATE accounts SET balance = balance + ?, prepared = ?, state = ? WHERE account_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, amount);
            pstmt.setBoolean(2, true); // 标记为准备状态
            pstmt.setString(3, TransactionState.PREPARE.name());
            pstmt.setString(4, account);

            int rows = pstmt.executeUpdate();
            return rows == 1;
        }
    }

    // 提交阶段
    private void commitPhase(Connection conn) throws SQLException {
        // 更新状态为已提交
        String sql = "UPDATE accounts SET prepared = false, state = ? WHERE prepared = true";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, TransactionState.COMMIT.name());
            pstmt.executeUpdate();
        }
        conn.commit();
    }

    // 回滚策略
    private void rollback(Connection fromConn, Connection toConn, boolean fromPrepared, boolean toPrepared) {
        // 策略1: 简单回滚 - 直接回滚所有连接
        simpleRollback(fromConn, toConn);

        /*
         * 策略2: 精确回滚 - 只回滚已准备的连接
         * if (fromPrepared) {
         *     try {
         *         fromConn.rollback();
         *     } catch (SQLException e) {
         *         System.err.println("回滚fromConn失败: " + e.getMessage());
         *     }
         * }
         * if (toPrepared) {
         *     try {
         *         toConn.rollback();
         *     } catch (SQLException e) {
         *         System.err.println("回滚toConn失败: " + e.getMessage());
         *     }
         * }
         */

        /*
         * 策略3: 补偿事务 - 对于已经提交的事务进行反向操作
         * 需要额外的逻辑来检查哪些操作已经提交并执行反向操作
         */
    }

    // 简单回滚实现
    private void simpleRollback(Connection... connections) {
        for (Connection conn : connections) {
            if (conn != null) {
                try {
                    // 更新状态为已回滚
                    String sql = "UPDATE accounts SET prepared = false, state = ? WHERE prepared = true";
                    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        pstmt.setString(1, TransactionState.ROLLBACK.name());
                        pstmt.executeUpdate();
                    }
                    conn.rollback();
                } catch (SQLException e) {
                    System.err.println("回滚失败: " + e.getMessage());
                }
            }
        }
    }

    // 检查账户是否存在
    private boolean accountExists(Connection conn, String account) throws SQLException {
        String sql = "SELECT 1 FROM accounts WHERE account_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, account);
            return pstmt.executeQuery().next();
        }
    }

    // 检查余额是否足够
    private boolean hasSufficientBalance(Connection conn, String account, double amount) throws SQLException {
        String sql = "SELECT balance FROM accounts WHERE account_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, account);
            try (var rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("balance") >= amount;
                }
                return false;
            }
        }
    }

    // 关闭连接
    private void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("关闭连接失败: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        TwoPhaseCommitTransfer transfer = new TwoPhaseCommitTransfer();
        boolean result = transfer.transfer("A123", "B456", 100.00);
        System.out.println("转账结果: " + (result ? "成功" : "失败"));
    }
}
