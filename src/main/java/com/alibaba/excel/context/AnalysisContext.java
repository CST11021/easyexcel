package com.alibaba.excel.context;

import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.ExcelHeadProperty;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;

import java.io.InputStream;
import java.util.List;

/**
 *
 * A context is the main anchorage point of a excel reader.
 * @author jipengfei
 */
public interface AnalysisContext {

    /**
     * Custom attribute
     */
    Object getCustom();

    /**
     * 获取当前要解析的页签
     *
     * @return current analysis sheet
     */
    Sheet getCurrentSheet();

    /**
     * 设置当前要解析的页签
     *
     * @param sheet
     */
    void setCurrentSheet(Sheet sheet);

    /**
     * 获取excel类型
     *
     * @return excel type
     */
    ExcelTypeEnum getExcelType();

    /**
     * 获取文件输入流
     *
     * @return file io
     */
    InputStream getInputStream();

    /**
     * 获取解析监听器
     *
     * @return listener
     */
    AnalysisEventListener getEventListener();

    /**
     * 获取当前解析的行号
     *
     * @return
     */
    Integer getCurrentRowNum();

    /**
     * 设置当前要解析的行号
     *
     * @param row
     */
    void setCurrentRowNum(Integer row);

    /**
     * 获取所有行，可能不准确
     *
     * @return
     */
    @Deprecated
    Integer getTotalCount();

    /**
     * get total row ,Data may be inaccurate
     *
     * @param totalCount
     */
    void setTotalCount(Integer totalCount);

    /**
     * 获取excel的head
     *
     * @return
     */
    ExcelHeadProperty getExcelHeadProperty();

    /**
     *
     * @param clazz
     * @param headOneRow
     */
    void buildExcelHeadProperty(Class<? extends BaseRowModel> clazz, List<String> headOneRow);

    /**
     * if need to short match the content
     *
     * @return
     */
    boolean trim();

    /**
     * 设置当前行的解析结果
     *
     * @param result
     */
    void setCurrentRowAnalysisResult(Object result);

    /**
     * 获取当前行的解析结果
     *
     * @return
     */
    Object getCurrentRowAnalysisResult();

    /**
     * 中断执行
     */
    void interrupt();

    /**
     * 是否使用1904Window日期系统
     *
     * @return
     */
    boolean use1904WindowDate();

    /**
     * 是否使用1904Window日期系统
     *
     * @param use1904WindowDate
     */
    void setUse1904WindowDate(boolean use1904WindowDate);
}
