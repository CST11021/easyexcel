package com.alibaba.excel.analysis;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.event.AnalysisEventRegisterCenter;
import com.alibaba.excel.event.OneRowAnalysisFinishEvent;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.util.TypeUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jipengfei
 */
public abstract class BaseSaxAnalyser implements AnalysisEventRegisterCenter, ExcelAnalyser {

    /**
     * 解析excel的上下文对象
     */
    protected AnalysisContext analysisContext;

    /**
     * Map<监听器名称, 监听器>
     */
    private LinkedHashMap<String, AnalysisEventListener> listeners = new LinkedHashMap<String, AnalysisEventListener>();

    /**
     * 开始解析，子类扩展
     */
    protected abstract void execute();

    @Override
    public void appendLister(String name, AnalysisEventListener listener) {
        if (!listeners.containsKey(name)) {
            listeners.put(name, listener);
        }
    }

    @Override
    public void analysis(Sheet sheetParam) {
        execute();
    }

    @Override
    public void analysis() {
        execute();
    }

    @Override
    public void cleanAllListeners() {
        listeners = new LinkedHashMap<String, AnalysisEventListener>();
    }

    @Override
    public void notifyListeners(OneRowAnalysisFinishEvent event) {
        // 设置当前行的解析结果
        analysisContext.setCurrentRowAnalysisResult(event.getData());
        /** Parsing header content **/
        if (analysisContext.getCurrentRowNum() < analysisContext.getCurrentSheet().getHeadLineMun()) {
            if (analysisContext.getCurrentRowNum() <= analysisContext.getCurrentSheet().getHeadLineMun() - 1) {
                analysisContext.buildExcelHeadProperty(null, (List<String>) analysisContext.getCurrentRowAnalysisResult());
            }
        } else {
            List<String> content = converter((List<String>) event.getData());

            /** Parsing Analyze the body content **/
            analysisContext.setCurrentRowAnalysisResult(content);
            if (listeners.size() == 1) {
                analysisContext.setCurrentRowAnalysisResult(content);
            }

            // 通知所有事件监听器
            for (Map.Entry<String, AnalysisEventListener> entry : listeners.entrySet()) {
                entry.getValue().invoke(analysisContext.getCurrentRowAnalysisResult(), analysisContext);
            }
        }
    }

    private List<String> converter(List<String> data) {
        List<String> list = new ArrayList<String>();
        if (data != null) {
            for (String str : data) {
                list.add(TypeUtil.formatFloat(str));
            }
        }
        return list;
    }

}
