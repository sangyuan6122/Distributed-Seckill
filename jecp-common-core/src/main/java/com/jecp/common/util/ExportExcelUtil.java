package com.jecp.common.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;

import com.jecp.common.model.TreeAttributes;

/**
 * 利用开源组件POI3.0.2动态导出EXCEL文档 转载时请保留以下信息，注明出处！
 * 
 * @author leno
 * @version v1.0
 * @param <T>
 *            应用泛型，代表任意一个符合javabean风格的类
 *            注意这里为了简单起见，boolean型的属性xxx的get器方式为getXxx(),而不是isXxx()
 *            byte[]表jpg格式的图片数据
 */
public class ExportExcelUtil<T>
{
	public void exportExcel(Set<String> fields,Collection<T> dataset, OutputStream out,List<CellRangeAddress> cellRangeAddress){
	    exportExcel("测试POI导出EXCEL文档", null,fields, dataset, out,cellRangeAddress, "yyyy-MM-dd");
	}
    public void exportExcel(String title,String[] headers,Set<String> fields, Collection<T> dataset,
    		OutputStream out,List<CellRangeAddress> cellRangeAddress){
        exportExcel(title, headers,fields, dataset, out,cellRangeAddress, "yyyy-MM-dd");
    }
    public void exportExcel(String[] headers,Set<String> fields, Collection<T> dataset,
            OutputStream out,List<CellRangeAddress> cellRangeAddress, String pattern){
        exportExcel("测试POI导出EXCEL文档", headers,fields, dataset, out,cellRangeAddress, pattern);
    }

    /**
     * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上
     * 
     * @param title
     *            表格标题名
     * @param headers
     *            表格属性列名数组
     * @param dataset
     *            需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
     *            javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
     * @param out
     *            与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
     * @param pattern
     *            如果有时间数据，设定输出格式。默认为"yyy-MM-dd"
     */
    @SuppressWarnings("unchecked")
    public void exportExcel(String title, String[] headers,Set<String> setfields,
            Collection<T> dataset, OutputStream out,List<CellRangeAddress> clist,String pattern){
    	// 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth((short) 10);
        // 生成一个样式
        HSSFCellStyle style = workbook.createCellStyle();
        // 设置这些样式
        style.setFillForegroundColor(HSSFColor.GOLD.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.BORDER_THIN);
        // 生成一个字体
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.BLACK.index);
        font.setFontHeightInPoints((short) 10);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 把字体应用到当前的样式
        style.setFont(font);
        // 生成并设置另一个样式
        HSSFCellStyle style2 = workbook.createCellStyle();
        style2.setFillForegroundColor(HSSFColor.WHITE.index);
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style2.setBorderBottom(HSSFCellStyle.ALIGN_LEFT);
        style2.setBorderLeft(HSSFCellStyle.ALIGN_LEFT);
        style2.setBorderRight(HSSFCellStyle.ALIGN_LEFT);
        style2.setBorderTop(HSSFCellStyle.ALIGN_LEFT);
        style2.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        style2.setVerticalAlignment(HSSFCellStyle.ALIGN_LEFT);
        // 生成另一个字体
        HSSFFont font2 = workbook.createFont();
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        // 把字体应用到当前的样式
        style2.setFont(font2);

//        // 声明一个画图的顶级管理器
//        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
//        // 定义注释的大小和位置,详见文档
//        HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0,
//                0, 0, 0, (short) 4, 2, (short) 6, 5));
//        // 设置注释内容
//        comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));
//        // 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
//        comment.setAuthor("leno");

        // 产生表格标题行
        HSSFRow row = sheet.createRow(0);
        for (short i = 0; i < headers.length; i++){
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(style);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }

        // 遍历集合数据，产生数据行
        Iterator<T> it = dataset.iterator();
        int index = 0;
        HSSFFont font3 = workbook.createFont();
        font3.setColor(HSSFColor.BLACK.index);
        while (it.hasNext()){
            index++;
            row = sheet.createRow(index);
            T t = (T) it.next();
            // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
            Field[] fieldsall = t.getClass().getDeclaredFields();
            List<Field> fields = new ArrayList<Field>();//哪些列不需要导出
            for(short i = 0; i < fieldsall.length; i++){
            	Field field = fieldsall[i];                
                String fieldName = field.getName();
                if(setfields.contains(fieldName)){
                	continue;
                }else{
                	fields.add(field);
                }
            }
            
            for (short i = 0; i < fields.size(); i++){
            	Field field = fields.get(i);                
                String fieldName = field.getName();
                HSSFCell cell = row.createCell(i);
                cell.setCellStyle(style2);
                
                String getMethodName = "get"
                        + fieldName.substring(0, 1).toUpperCase()
                        + fieldName.substring(1);
                if(Character.isUpperCase(fieldName.charAt(1))) {
                	getMethodName = "get"+ fieldName;
                }
                try
                {
                    Class tCls = t.getClass();
                    Method getMethod = tCls.getMethod(getMethodName,new Class[]{});
                    Object value = getMethod.invoke(t, new Object[]{});
                    // 判断值的类型后进行强制类型转换
                    String textValue = null;
                    // if (value instanceof Integer) {
                    // int intValue = (Integer) value;
                    // cell.setCellValue(intValue);
                    // } else if (value instanceof Float) {
                    // float fValue = (Float) value;
                    // textValue = new HSSFRichTextString(
                    // String.valueOf(fValue));
                    // cell.setCellValue(textValue);
                    // } else if (value instanceof Double) {
                    // double dValue = (Double) value;
                    // textValue = new HSSFRichTextString(
                    // String.valueOf(dValue));
                    // cell.setCellValue(textValue);
                    // } else if (value instanceof Long) {
                    // long longValue = (Long) value;
                    // cell.setCellValue(longValue);
                    // }
                    if(value==null){
                    	value="";
                    }else if (value instanceof Boolean){
                        boolean bValue = (Boolean) value;
                        textValue = "男";
                        if (!bValue)
                        {
                            textValue = "女";
                        }
                    }else if (value instanceof java.util.Date||value instanceof java.sql.Timestamp
                    		||value instanceof java.sql.Date){
                     
                        textValue = TimeUtil.timeToStr(value);
                    }else if (value instanceof byte[]){
                        // 有图片时，设置行高为60px;
                        row.setHeightInPoints(60);
                        // 设置图片所在列宽度为80px,注意这里单位的一个换算
                        sheet.setColumnWidth(i, (short) (35.7 * 80));
                        // sheet.autoSizeColumn(i);
                        byte[] bsValue = (byte[]) value;
                        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,
                                1023, 255, (short) 6, index, (short) 6, index);
                        anchor.setAnchorType(2);
//                        patriarch.createPicture(anchor, workbook.addPicture(
//                                bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
                    }else{
                        // 其它数据类型都当作字符串简单处理
                        textValue = value.toString();
                    }
                    // 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
                    if (textValue != null){
                        Pattern p = Pattern.compile("^//d+(//.//d+)?$");
                        Matcher matcher = p.matcher(textValue);
                        if (matcher.matches()){
                            // 是数字当作double处理
                            cell.setCellValue(Double.parseDouble(textValue));
                        }else{
                            HSSFRichTextString richString = new HSSFRichTextString(textValue);                            
                            richString.applyFont(font3);
                            cell.setCellValue(richString);
                        }
                    }
                    for(int k=0;clist!=null&&k<clist.size();k++){//合并单元格
                    	CellRangeAddress cellRangeAddress=clist.get(k);
                    	sheet.addMergedRegion(cellRangeAddress);
                    }
                }catch (SecurityException e){
                    e.printStackTrace();
                }catch (NoSuchMethodException e){
                    e.printStackTrace();
                }catch (IllegalArgumentException e){
                    e.printStackTrace();
                }catch (IllegalAccessException e){
                    e.printStackTrace();
                }catch (InvocationTargetException e){
                    e.printStackTrace();
                }finally{
                    // 清理资源
                }
            }
        }
        try{
            workbook.write(out);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        // 测试学生
        ExportExcelUtil<TreeAttributes> ex = new ExportExcelUtil<TreeAttributes>();
        String[] headers =
        { "学号", "姓名", "年龄", "性别", "出生日期" };
        List<TreeAttributes> dataset = new ArrayList<TreeAttributes>();
        TreeAttributes t1=new TreeAttributes();
        t1.setA1("a1");
        t1.setA2("a2");
        dataset.add(t1);
        dataset.add(t1);
        dataset.add(t1);
        List<CellRangeAddress> list=new ArrayList<>();
        list.add(new CellRangeAddress(1,2,1,1));
        try
        {
        	File dir = new File("C://11");
        	if(!dir.exists()) {
        		dir.mkdir();
        	}
            OutputStream out = new FileOutputStream("C://11/a.xls");
            Set<String> setfields=new HashSet<String>();
            setfields.add("a2");
            ex.exportExcel("测试导出",headers,setfields, dataset, out,list);          
            out.close();
            JOptionPane.showMessageDialog(null, "导出成功!");
            System.out.println("excel导出成功！");
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}