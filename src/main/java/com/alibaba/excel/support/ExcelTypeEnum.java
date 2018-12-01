package com.alibaba.excel.support;

import org.apache.poi.poifs.filesystem.FileMagic;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author jipengfei
 */
public enum ExcelTypeEnum {

    /** 文件类型 */
    XLS(".xls"),
    XLSX(".xlsx");

    private String value;

    ExcelTypeEnum(String value) {
        this.setValue(value);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 根据文件输入流获取文件类型
     *
     * @param inputStream   文件输入流
     * @return
     */
    public static ExcelTypeEnum valueOf(InputStream inputStream){
        try {
            if (!inputStream.markSupported()) {
                return null;
            }
            FileMagic fileMagic =  FileMagic.valueOf(inputStream);
            if(FileMagic.OLE2.equals(fileMagic)){
                return XLS;
            }
            if(FileMagic.OOXML.equals(fileMagic)){
                return XLSX;
            }
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
