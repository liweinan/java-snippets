package io.weli.pattern.chain;

/**
 * 具体处理者（Concrete Handler）：组长，可审批 1000 元及以下。
 */
public class TeamLeader extends Approver {

    private static final int LIMIT = 1_000;

    @Override
    protected String roleName() {
        return "组长";
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
        System.out.println("组长审批通过：" + request.getRequester() + " 的 " + request.getAmount() + " 元");
    }
}
