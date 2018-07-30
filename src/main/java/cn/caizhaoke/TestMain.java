package cn.caizhaoke;

import cn.caizhaoke.getavailablepaylist.PayServiceObject;
import cn.caizhaoke.getavailablepaylist.SubTask;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

/**
 * @author zhaoke_cai@163.com
 * @date 2018/7/30
 * @description 模拟测试多个线程并发，实时请求调用远程服务获取用户支付方式可用性
 */
@Slf4j
public class TestMain {
    /**
     * 用户支付方式列表长度
     */
    private static final int LIST_LENGTH = 10;
    /**
     * 支付方式名称最大长度
     */
    private static final int NAME_LENGTH = 8;
    /**
     * 并发执行的最大线程数
     */
    private static final int MAX_CONCURRENT = 4;

    public static void main(String[] args) {
        //模拟生成用户支付方式的名称列表，如余额、红包、优惠券，代金券等，此处为随机生成名称构建支付方式对象列表
        CopyOnWriteArrayList<PayServiceObject> array = randomList(LIST_LENGTH);

        //Fork Join 线程池并发执行任务
        ForkJoinPool pool = new ForkJoinPool(MAX_CONCURRENT);

        //创建子任务
        ForkJoinTask<CopyOnWriteArrayList<PayServiceObject>> task = new SubTask(array, 0, LIST_LENGTH);

        //时间起始
        long startTime = System.nanoTime();
        //1、并发执行任务并获取结果
        CopyOnWriteArrayList<PayServiceObject> result = pool.invoke(task);
        //时间终止
        long useTime = System.nanoTime() - startTime;
        //1、打印最终结果
        log.info("多线程并发执行，获取用户支付方式服务列表为: " + JSONObject.toJSONString(result) + " ," +
                "共耗时（含网络通信LIST_LENGTH * 10ms） " + useTime + " ns.");

        //2、或者，仅保留可用的支付方式列表
        result.forEach(vo -> {
            if (!vo.isAvailable()) {
                result.remove(vo);
            }
        });
        //2、打印最终结果
        log.info("用户可用的支付方式服务列表: " + JSONObject.toJSONString(result));
    }

    /**
     * 随机生成用户支付方式的名称（大小写英文字母+数字）列表，并存储在CopyOnWriteArrayList并发容器中
     * CopyOnWriteArrayList并发容器适合读多写少场景，且写过程中是复制操作一个新的容器，因此也适合于根据判断结果更新元素
     *
     * @param length
     * @return CopyOnWriteArrayList<String>
     */
    public static CopyOnWriteArrayList<PayServiceObject> randomList(int length) {
        //字符源
        String str = "aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ0123456789";
        Random random = new Random();
        //支付方式名称列表
        List<PayServiceObject> listString = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            StringBuffer stringBuffer = new StringBuffer();
            //支付方式名称长度
            int stringLength = random.nextInt(NAME_LENGTH);
            //生成支付方式名称
            for (int j = 0; j < stringLength; j++) {
                int index = random.nextInt(str.length());
                char c = str.charAt(index);
                stringBuffer.append(c);
            }
            //判断是否重复的支付方式名称
            if (!(listString.contains(stringBuffer.toString()))) {
                listString.add(new PayServiceObject(stringBuffer.toString()));
            } else {
                i--;
            }
        }
        return new CopyOnWriteArrayList<>(listString);
    }
}
