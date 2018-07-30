package cn.caizhaoke.getavailablepaylist;

import cn.caizhaoke.outpayservice.PaymentRemoteServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.RecursiveTask;

/**
 * @author zhaoke_cai@163.com
 * @date 2018/7/30
 * @description 实时调用远程服务获取用户支付方式可用性列表的任务分解
 */
@Service
@Slf4j
public class SubTask extends RecursiveTask<CopyOnWriteArrayList<PayServiceObject>> {
    /**
     * 阈值
     */
    static final int THRESHOLD = 2;
    /**
     * 支付方式服务名称
     */
    CopyOnWriteArrayList<PayServiceObject> arrayList;

    int start;
    int end;

    public SubTask(CopyOnWriteArrayList<PayServiceObject> arrayList, int start, int end) {
        this.arrayList = arrayList;
        this.start = start;
        this.end = end;
    }

    @Override
    protected CopyOnWriteArrayList<PayServiceObject> compute() {
        // 如果支付方式列表小于阈值，则直接发送请求
        if (end - start <= THRESHOLD) {
            for (int i = start; i < end; i++) {
                Boolean isEnable = new PaymentRemoteServiceImpl().isEnabled(arrayList.get(i).getName()).getIsEnable();
                //该支付方式服务不可用，则设为false
                if (!isEnable) {
//                    log.info("不可用服务：" + arrayList.get(i));
                    arrayList.get(i).setAvailable(Boolean.FALSE);
                }
            }
            //log.info(String.format("发送请求 %d~~%d", start, end));
            return arrayList;
        }
        // 如果支付方式列表大于阈值，则划分为多个小任务进行执行（迭代二分模式划分）
        int middle = (end + start) / 2;
        //log.info(String.format("划分 %d~%d ==> %d~%d, %d~%d", start, end, start, middle, middle, end));
        SubTask subTask1 = new SubTask(this.arrayList, start, middle);
        SubTask subTask2 = new SubTask(this.arrayList, middle, end);

        invokeAll(subTask1, subTask2);
        subTask1.join();
        subTask2.join();

        //返回结果
        return arrayList;
    }
}
