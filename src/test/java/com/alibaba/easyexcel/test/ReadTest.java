package com.alibaba.easyexcel.test;

import com.alibaba.easyexcel.test.listen.ExcelListener;
import com.alibaba.easyexcel.test.model.ReadModel;
import com.alibaba.easyexcel.test.model.ReadModel2;
import com.alibaba.easyexcel.test.util.FileUtil;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.Sheet;
import com.sun.tools.javac.util.StringUtils;
import org.apache.poi.util.StringUtil;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReadTest {

    @Test
    public void t() {





        final long cutTime = System.currentTimeMillis();



        InputStream in = FileUtil.getResourcesFileInputStream("库存地点_(1).xlsx");

        final Set<String> macList = new HashSet<String>();
        new ExcelReader(in, null, new AnalysisEventListener() {

            @Override
            public void invoke(Object o, AnalysisContext analysisContext) {

                List<String> column = (List<String>) o;

                String company_code = column.get(0) == null ? "" : column.get(0);
                String code = column.get(2) == null ? "" : column.get(2);
                String storage_name = column.get(3) == null ? "" : column.get(3);
                String supplier_code = column.get(7) == null ? "" : column.get(7);
                String factory_code = column.get(4) == null ? "" : column.get(4);
                String supplier_type = getSupplierTypeName(column.get(6));
                long gmt_create = cutTime;
                long gmt_modified = cutTime;

                StringBuffer sb = new StringBuffer("('");
                sb.append(company_code).append("', '");
                sb.append(code).append("', '");
                sb.append(storage_name).append("', '");
                sb.append(supplier_code).append("', '");
                sb.append(factory_code).append("', '");
                sb.append(supplier_type).append("', ");
                sb.append(gmt_create).append(", ");
                sb.append(gmt_modified).append("), ");

                System.out.println(sb.toString());
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {

            }
        }).read();

        System.out.println(macList);
    }

    // AGREEMENT_FACTORY("1", "协议工厂"),
    //         PURCHASING_SUPPLIER("2", "采购供应商"),
    //         FINISHED_WAREHOUSE("3", "成品仓库"),
    //         TUYA_WAREHOUSE("4", "涂鸦仓库"),
    //         OTHER("5", "其他"),
    public String getSupplierTypeName(String name) {
        if (name == null) {
            return "";
        }

        if ("代工厂".equals(name.trim())) {
            return "1";
        } else if ("采购供应商".equals(name.trim())) {
            return "2";
        }  else if ("成品仓库".equals(name.trim())) {
            return "3";
        }  else if ("涂鸦仓库".equals(name.trim())) {
            return "4";
        }
        return "";
    }

    /**
     * 07版本excel读数据量少于1千行数据，内部采用回调方法.
     *
     * @throws IOException 简单抛出异常，真实环境需要catch异常,同时在finally中关闭流
     */
    @Test
    public void simpleReadListStringV2007() throws IOException {
        InputStream inputStream = FileUtil.getResourcesFileInputStream("2007.xlsx");
        // headLineMun表示表头占用的行数，这里设置为0，则解析时也会把表头解析出来，如果设置为1，则从第二行开始解析
        List<Object> data = EasyExcelFactory.read(inputStream, new Sheet(1, 0));
        inputStream.close();
        print(data);
    }

    /**
     * 07版本excel读数据量少于1千行数据自动转成javamodel，内部采用回调方法.
     *
     * @throws IOException 简单抛出异常，真实环境需要catch异常,同时在finally中关闭流
     */
    @Test
    public void simpleReadJavaModelV2007() throws IOException {
        InputStream inputStream = FileUtil.getResourcesFileInputStream("2007.xlsx");
        List<Object> data = EasyExcelFactory.read(inputStream, new Sheet(2, 1, ReadModel.class));
        inputStream.close();
        print(data);
    }

    /**
     * 07版本excel读数据量大于1千行，内部采用回调方法.
     *
     * @throws IOException 简单抛出异常，真实环境需要catch异常,同时在finally中关闭流
     */
    @Test
    public void saxReadListStringV2007() throws IOException {
        InputStream inputStream = FileUtil.getResourcesFileInputStream("2007.xlsx");
        ExcelListener excelListener = new ExcelListener();
        EasyExcelFactory.readBySax(inputStream, new Sheet(1, 1), excelListener);
        inputStream.close();

    }

    /**
     * 07版本excel读数据量大于1千行，内部采用回调方法.
     *
     * @throws IOException 简单抛出异常，真实环境需要catch异常,同时在finally中关闭流
     */
    @Test
    public void saxReadJavaModelV2007() throws IOException {
        InputStream inputStream = FileUtil.getResourcesFileInputStream("2007.xlsx");
        ExcelListener excelListener = new ExcelListener();
        EasyExcelFactory.readBySax(inputStream, new Sheet(2, 1, ReadModel.class), excelListener);
        inputStream.close();
    }

    /**
     * 07版本excel读取sheet
     *
     * @throws IOException 简单抛出异常，真实环境需要catch异常,同时在finally中关闭流
     */
    @Test
    public void saxReadSheetsV2007() throws IOException {
        InputStream inputStream = FileUtil.getResourcesFileInputStream("2007.xlsx");
        ExcelReader excelReader = EasyExcelFactory.getReader(inputStream, new ExcelListener());
        List<Sheet> sheets = excelReader.getSheets();

        // 解析每个页签
        for (Sheet sheet : sheets) {
            if (sheet.getSheetNo() == 1) {
                excelReader.read(sheet);
            } else if (sheet.getSheetNo() == 2) {
                sheet.setHeadLineMun(1);
                sheet.setClazz(ReadModel.class);
                excelReader.read(sheet);
            } else if (sheet.getSheetNo() == 3) {
                sheet.setHeadLineMun(1);
                sheet.setClazz(ReadModel2.class);
                excelReader.read(sheet);
            }
        }
        inputStream.close();
    }


    // ----------------
    // 测试 2003
    // ----------------


    /**
     * 03版本excel读数据量少于1千行数据，内部采用回调方法.
     *
     * @throws IOException 简单抛出异常，真实环境需要catch异常,同时在finally中关闭流
     */
    @Test
    public void simpleReadListStringV2003() throws IOException {
        InputStream inputStream = FileUtil.getResourcesFileInputStream("2003.xls");
        List<Object> data = EasyExcelFactory.read(inputStream, new Sheet(1, 0));
        inputStream.close();
        print(data);
    }

    /**
     * 03版本excel读数据量少于1千行数据转成javamodel，内部采用回调方法.
     *
     * @throws IOException 简单抛出异常，真实环境需要catch异常,同时在finally中关闭流
     */
    @Test
    public void simpleReadJavaModelV2003() throws IOException {
        InputStream inputStream = FileUtil.getResourcesFileInputStream("2003.xls");
        List<Object> data = EasyExcelFactory.read(inputStream, new Sheet(2, 1, ReadModel.class));
        inputStream.close();
        print(data);
    }

    /**
     * 03版本excel读数据量大于1千行数据，内部采用回调方法.
     *
     * @throws IOException 简单抛出异常，真实环境需要catch异常,同时在finally中关闭流
     */
    @Test
    public void saxReadListStringV2003() throws IOException {
        InputStream inputStream = FileUtil.getResourcesFileInputStream("2003.xls");
        ExcelListener excelListener = new ExcelListener();
        EasyExcelFactory.readBySax(inputStream, new Sheet(2, 1), excelListener);
        inputStream.close();
    }

    /**
     * 03版本excel读数据量大于1千行数据转成javamodel，内部采用回调方法.
     *
     * @throws IOException 简单抛出异常，真实环境需要catch异常,同时在finally中关闭流
     */
    @Test
    public void saxReadJavaModelV2003() throws IOException {
        InputStream inputStream = FileUtil.getResourcesFileInputStream("2003.xls");
        ExcelListener excelListener = new ExcelListener();
        EasyExcelFactory.readBySax(inputStream, new Sheet(2, 1, ReadModel.class), excelListener);
        inputStream.close();
    }

    /**
     * 00版本excel读取sheet
     *
     * @throws IOException 简单抛出异常，真实环境需要catch异常,同时在finally中关闭流
     */
    @Test
    public void saxReadSheetsV2003() throws IOException {
        InputStream inputStream = FileUtil.getResourcesFileInputStream("2003.xls");
        ExcelListener excelListener = new ExcelListener();
        ExcelReader excelReader = EasyExcelFactory.getReader(inputStream, excelListener);
        List<Sheet> sheets = excelReader.getSheets();
        System.out.println();
        for (Sheet sheet : sheets) {
            if (sheet.getSheetNo() == 1) {
                excelReader.read(sheet);
            } else {
                sheet.setHeadLineMun(2);
                sheet.setClazz(ReadModel.class);
                excelReader.read(sheet);
            }
        }
        inputStream.close();
    }

    public void print(List<Object> datas) {
        int i = 0;
        for (Object ob : datas) {
            System.out.println(i++);
            System.out.println(ob);
        }
    }

}
