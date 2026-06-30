package io.weli.pattern.chain;

/**
 * 请求对象（Request）：在责任链中传递的上下文数据。
 */
public class ApprovalRequest {

    private final String requester;
    private final int amount;

    public ApprovalRequest(String requester, int amount) {
        this.requester = requester;
        this.amount = amount;
    }

    public String getRequester() {
        return requester;
    }

    public int getAmount() {
        return amount;
    }
}
