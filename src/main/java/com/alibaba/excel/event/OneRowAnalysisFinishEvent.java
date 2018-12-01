package com.alibaba.excel.event;

import java.util.ArrayList;
import java.util.List;

/**
 * 表示解析完一行数据的事件
 *
 * @author jipengfei
 */
public class OneRowAnalysisFinishEvent {

    /** 表示当前行的数据 */
    private Object data;

    public OneRowAnalysisFinishEvent(Object content) {
        this.data = content;
    }

    public OneRowAnalysisFinishEvent(String[] content, int length) {
        if (content != null) {
            List<String> ls = new ArrayList<String>(length);
            for (int i = 0; i <= length; i++) {
                ls.add(content[i]);
            }
            data = ls;
        }
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
