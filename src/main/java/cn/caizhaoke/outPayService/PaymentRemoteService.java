package cn.caizhaoke.outPayService;

import cn.caizhaoke.outPayService.ConsultResult;

/**
 * @author zhaoke_cai@163.com
 * @date 2018/7/30
 * @description 服务请求接口
 */
public interface PaymentRemoteService {
    ConsultResult isEnabled(String paymentType);
}
