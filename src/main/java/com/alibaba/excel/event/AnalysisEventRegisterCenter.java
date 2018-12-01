package com.alibaba.excel.event;

/**
 * 表示解析过程中的事件注册中心
 *
 * @author jipengfei
 */
public interface AnalysisEventRegisterCenter {

    /**
     * 添加监听
     *
     * @param name     监听器名称
     * @param listener 解析每一行时的回调方法
     */
    void appendLister(String name, AnalysisEventListener listener);

    /**
     * 解析完一行后，通知所有的事件监听器
     *
     * @param event parse event
     */
    void notifyListeners(OneRowAnalysisFinishEvent event);

    /**
     * 清除所有事件监听器
     */
    void cleanAllListeners();
}
