package cn.caizhaoke.outpayservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * @author zhaoke_cai@163.com
 * @date 2018/7/30
 * @description 服务请求接口实现
 */
@Service
@Slf4j
public class PaymentRemoteServiceImpl implements PaymentRemoteService {
    @Override
    public ConsultResult isEnabled(String paymentType) {
        Boolean isEnable = new Random().nextBoolean();
        if (isEnable) {
//            log.info(paymentType + "支付调用成功！");
            return new ConsultResult(isEnable, null);
        }
        try {
            //模拟网络通信市场10ms
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        log.info(paymentType + "支付调用失败！");
        return new ConsultResult(isEnable, paymentType + "支付调用失败，返回错误！");
    }
}
