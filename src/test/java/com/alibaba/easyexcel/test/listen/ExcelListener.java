package com.alibaba.easyexcel.test.listen;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.List;

public class ExcelListener extends AnalysisEventListener {

    private List<Object> data = new ArrayList<Object>();

    @Override
    public void invoke(Object object, AnalysisContext context) {
        System.out.println(context.getCurrentSheet());
        // 小于100行时，将数据放入内存，大于100行时做其他处理
        if (data.size() <= 100) {
            data.add(object);
        } else {
            doSomething();
            data = new ArrayList<Object>();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        doSomething();
    }

    public void doSomething() {
        for (Object o : data) {
            System.out.println(o);
        }
    }
}
