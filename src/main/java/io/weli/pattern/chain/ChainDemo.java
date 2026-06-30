package io.weli.pattern.chain;

/**
 * 责任链模式演示。
 * <p>
 * 角色对照：
 * <ul>
 *   <li>{@link ApprovalRequest} — 请求对象，在链上传递</li>
 *   <li>{@link Approver} — 抽象处理者，定义处理接口与后继者链接</li>
 *   <li>{@link TeamLeader} / {@link Manager} / {@link Director} — 具体处理者，按权限逐级处理</li>
 * </ul>
 * 客户端只需把请求交给链头，不必知道最终由谁审批；
 * 新增审批级别时插入新处理者即可，无需修改调用方。
 * </p>
 */
public class ChainDemo {

    public static void main(String[] args) {
        System.out.println("=== 组装审批责任链 ===");
        Approver chain = new TeamLeader();
        chain.linkWith(new Manager()).linkWith(new Director());
        System.out.println();

        submit(chain, new ApprovalRequest("张三", 500));
        submit(chain, new ApprovalRequest("李四", 5_000));
        submit(chain, new ApprovalRequest("王五", 50_000));
    }

    private static void submit(Approver chain, ApprovalRequest request) {
        System.out.println(">>> 提交申请：" + request.getRequester() + "，" + request.getAmount() + " 元");
        chain.approve(request);
        System.out.println();
    }
}
