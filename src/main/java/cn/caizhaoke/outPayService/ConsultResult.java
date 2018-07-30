package cn.caizhaoke.outPayService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * @author zhaoke_cai@163.com
 * @date 2018/7/30
 * @description 服务请求结果对象
 */
@NoArgsConstructor
@AllArgsConstructor
public class ConsultResult {
    /**
     * 咨询结果是否可用
     */
    private boolean isEnable;

    /**
     * 错误码
     */
    private String errorCode;

    public boolean getIsEnable() {
        return isEnable;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
