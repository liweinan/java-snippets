package io.weli.pattern.chain;

/**
 * 具体处理者（Concrete Handler）：经理，可审批 10000 元及以下。
 */
public class Manager extends Approver {

    private static final int LIMIT = 10_000;

    @Override
    protected String roleName() {
        return "经理";
    }

    @Override
    protected String approvalLimit() {
        return LIMIT + " 元";
    }

    @Override
    protected boolean canHandle(ApprovalRequest request) {
        return request.getAmount() <= LIMIT;
    }

    @Override
    protected void handle(ApprovalRequest request) {
        System.out.println("经理审批通过：" + request.getRequester() + " 的 " + request.getAmount() + " 元");
    }
}
