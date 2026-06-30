package io.weli.pattern.chain;

/**
 * 抽象处理者（Handler）：定义处理请求的接口，并持有下一个处理者的引用。
 * <p>
 * 每个具体处理者只关心自己权限范围内的请求；处理不了就转给后继者。
 * </p>
 */
public abstract class Approver {

    private Approver next;

    protected abstract String roleName();

    protected abstract String approvalLimit();

    /**
     * 串联责任链，返回后继者以便链式调用。
     */
    public Approver linkWith(Approver next) {
        this.next = next;
        System.out.println("  链路：" + roleName() + " -> " + next.roleName());
        return next;
    }

    public void approve(ApprovalRequest request) {
        System.out.println("[" + roleName() + "] 收到申请：申请人=" + request.getRequester()
                + "，金额=" + request.getAmount() + " 元，本级别上限=" + approvalLimit());

        if (canHandle(request)) {
            System.out.println("[" + roleName() + "] 在权限范围内，开始审批");
            handle(request);
            System.out.println("[" + roleName() + "] 审批完成");
        } else if (next != null) {
            System.out.println("[" + roleName() + "] 超出权限，转交 " + next.roleName());
            next.approve(request);
        } else {
            System.out.println("[" + roleName() + "] 已是链末端且无法处理，驳回："
                    + request.getRequester() + " 的 " + request.getAmount() + " 元申请");
        }
    }

    protected abstract boolean canHandle(ApprovalRequest request);

    protected abstract void handle(ApprovalRequest request);
}
