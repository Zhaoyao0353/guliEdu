package com.atguigu.demo.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import org.junit.Before;

import javax.swing.event.AncestorListener;
import java.util.Map;

/**
 * @program: guli_parent
 * @description:
 * @author: Mr.Zhao
 * @create: 2020-12-20 17:17
 **/
public class ExcelListenter extends AnalysisEventListener<DemoDate>{
    @Override
    public void invoke(DemoDate o, AnalysisContext analysisContext) {
        System.out.println("====="+o);
    }

    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表提"+headMap);
    }
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

}
