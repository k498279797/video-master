package com.vxiaokang.video.bean;

import java.util.ArrayList;
import java.util.List;

public class KeyBoardUtils {
    public static List<KeyBoardBean> dataList = new ArrayList<KeyBoardBean>();
    static {
        dataList.add(new KeyBoardBean("Ctrl+S","保存PPT"));
        dataList.add(new KeyBoardBean("Ctrl+Shift+S","另存当前PPT"));
        dataList.add(new KeyBoardBean("Ctrl+N","新建空白PPT"));
        dataList.add(new KeyBoardBean("Ctrl+M","新建一页PPT"));
        dataList.add(new KeyBoardBean("Ctrl+W/Ctrl+F4","关闭PPT"));
        dataList.add(new KeyBoardBean("Ctrl+B","文字变粗"));
        dataList.add(new KeyBoardBean("Ctrl+I","文字倾斜"));
        dataList.add(new KeyBoardBean("Ctrl+U","文字下面划线"));
        dataList.add(new KeyBoardBean("Ctrl+Z","撤销"));
        dataList.add(new KeyBoardBean("Ctrl+Y","重做"));
        dataList.add(new KeyBoardBean("Ctrl+]","字号增大"));
        dataList.add(new KeyBoardBean("Ctrl+[","字号缩小"));
        dataList.add(new KeyBoardBean("Shift+F3","英文字母大小写转换"));
        dataList.add(new KeyBoardBean("Ctrl+L","文字左对齐"));
        dataList.add(new KeyBoardBean("Ctrl+R","文字右对齐"));
        dataList.add(new KeyBoardBean("Ctrl+J","文字分散对齐"));
        dataList.add(new KeyBoardBean("Ctrl+E","文字居中对齐"));
        dataList.add(new KeyBoardBean("Ctrl+E","文字居中对齐"));
        dataList.add(new KeyBoardBean("Shift+缩放对象","图形等比例缩放"));
        dataList.add(new KeyBoardBean("Shift+移动对象","图形水平或垂直移动"));
        dataList.add(new KeyBoardBean("Ctrl+缩放对象","页面整体缩放"));
        dataList.add(new KeyBoardBean("Ctrl+Shift+对象","对象中心点等比例缩放"));
        dataList.add(new KeyBoardBean("Ctrl+D","创建副本"));
        dataList.add(new KeyBoardBean("Ctrl+G","组合元素"));
        dataList.add(new KeyBoardBean("Ctrl+Shift+G","取消组合"));
        dataList.add(new KeyBoardBean("Ctrl+Shift+C","复制"));
        dataList.add(new KeyBoardBean("Ctrl+Shift+V","粘贴"));
        dataList.add(new KeyBoardBean("F4","重复上一步操作"));
        dataList.add(new KeyBoardBean("Ctrl+鼠标滚轮","缩放画布"));
        dataList.add(new KeyBoardBean("Alt+F9","显示或隐藏网格线"));
        dataList.add(new KeyBoardBean("Alt+F10","显示或隐藏“选择”窗口"));
      //  dataList.add(new KeyBoardBean("",""));
    }

    public static List<KeyBoardBean> getDataList(){
        return dataList;
    }
}
