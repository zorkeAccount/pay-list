package cn.caizhaoke.outpayservice;

/**
 * @author zhaoke_cai@163.com
 * @date 2018/7/30
 * @description 服务请求接口
 */
public interface PaymentRemoteService {
    /**
     * 远程调用服务方法，判断用户支付方式是否可用
     *
     * @param paymentType
     * @return ConsultResult
     */
    ConsultResult isEnabled(String paymentType);
}
