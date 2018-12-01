package com.alibaba.excel.metadata;

import org.apache.poi.ss.usermodel.CellStyle;

import java.util.HashMap;
import java.util.Map;

/**
 * Excel基础模型
 *
 * @author jipengfei
 */
public class BaseRowModel {

    /** Map<行号, 样式对象> */
    private Map<Integer, CellStyle> cellStyleMap = new HashMap<Integer, CellStyle>();

    /**
     * 添加指定行的样式
     *
     * @param row           行号
     * @param cellStyle     样式对象
     */
    public void addStyle(Integer row, CellStyle cellStyle) {
        cellStyleMap.put(row, cellStyle);
    }

    /**
     * 获取指定行号的样式对象
     *
     * @param row
     * @return
     */
    public CellStyle getStyle(Integer row) {
        return cellStyleMap.get(row);
    }

    public Map<Integer, CellStyle> getCellStyleMap() {
        return cellStyleMap;
    }

    public void setCellStyleMap(Map<Integer, CellStyle> cellStyleMap) {
        this.cellStyleMap = cellStyleMap;
    }
}
