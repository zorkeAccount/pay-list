package cn.caizhaoke.getAvailablePayList;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhaoke_cai@163.com
 * @date 2018/7/30
 * @description 支付服务方式是否可用对象，默认可用
 */
@Data
public class PayServiceObject implements Serializable {
    private static final long serialVersionUID = -3385248925578107078L;

    /**
     * 支付方式名称，测试代码中随机生成
     */
    private String name;
    /**
     * 支付方式服务对象，默认认为均可用，考虑到认为是可用的服务是相对较多的，因此此处用以存储结果的对象默认为可用减少操作
     */
    private boolean isAvailable = true;

    public PayServiceObject(String name) {
        this.name = name;
    }
}
