package com.alibaba.excel.event;

import com.alibaba.excel.context.AnalysisContext;

/**
 *
 *
 * @author jipengfei
 */
public abstract class AnalysisEventListener<T> {

    /**
     * 当解析一行时会来调用该函数
     *
     * @param object  表示当前行解析的结果对象
     * @param context 解析上下文对象
     */
    public abstract void invoke(T object, AnalysisContext context);

    /**
     * 解析完所有后调用
     *
     * @param context
     */
    public abstract void doAfterAllAnalysed(AnalysisContext context);
}
