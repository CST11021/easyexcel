package com.alibaba.excel.analysis;

import com.alibaba.excel.metadata.Sheet;

import java.util.List;

/**
 * Excel file analyser
 *
 * @author jipengfei
 */
public interface ExcelAnalyser {

    /**
     * 解析指定的页签
     *
     * @param sheetParam
     */
    void analysis(Sheet sheetParam);

    /**
     * 解析所有页签
     */
    void analysis();

    /**
     * 获取workbook的所有页签
     *
     * @return
     */
    List<Sheet> getSheets();

}
