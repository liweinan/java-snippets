package io.weli.pattern.chain;

/**
 * 具体处理者（Concrete Handler）：总监，可审批任意金额。
 */
public class Director extends Approver {

    @Override
    protected String roleName() {
        return "总监";
    }

    @Override
    protected String approvalLimit() {
        return "无上限";
    }

    @Override
    protected boolean canHandle(ApprovalRequest request) {
        return true;
    }

    @Override
    protected void handle(ApprovalRequest request) {
        System.out.println("总监审批通过：" + request.getRequester() + " 的 " + request.getAmount() + " 元");
    }
}
