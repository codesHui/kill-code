package com.imi.support;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.imi.base.annotate.ReportColumn;
import com.imi.entity.products.Product;
import com.imi.entity.setting.MemberUser;
import com.imi.model.excel.Demo;
import com.imi.model.excel.Risk05OPMessageExcel;
import com.imi.model.excel.Risk05PIMessageExcel;
import com.imi.model.excel.Risk08CTMessageExcel;
import com.imi.model.excel.Risk08OVMessageExcel;
import com.imi.model.excel.Risk09IEMessageExcel;
import com.softvan.utils.ReflectionUtils;

/**
 * 导入导出工具类
 */

public class ImportExport {

	public enum Type {
		selected, limit
	}

	private static Logger logger = LoggerFactory.getLogger(ImportExport.class);

	/**
	 * 导出单个sheet的Excel文件
	 * 
	 * @param <T>
	 */
	public static <T> Workbook exportExcel(SXSSFWorkbook workbook, String title,
			List entities, Class<T> clazz) {
		// 表格
		Sheet sheet = workbook.createSheet(title);
		// 表头
		Row header = sheet.createRow(0); 
		CellStyle titleStyle = getTitleStyle(workbook);
		final Field[] fields = getSortedFields(clazz.getName());
		// 设置单元格类型
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		System.out.println("fields.length"+fields.length);
		for (int i = 0; i < fields.length; i++) {
			ReportColumn annotation = fields[i]
					.getAnnotation(ReportColumn.class);
			Cell cell = header.createCell(i);
			RichTextString text = new XSSFRichTextString(
					annotation.columnName());
			cell.setCellValue(text);
			cell.setCellStyle(titleStyle);
			map.put(fields[i].getName(), annotation.converter());
		}

		// 内容
		for (int i = 0; i < entities.size(); i++) {
			
			Object entity = entities.get(i);
			Row row = sheet.createRow(i + 1);
			CellStyle style = getStyle(workbook, i);
			for (int j = 0; j < fields.length; j++) {
				Field field = fields[j];
				Cell acell =row.createCell(j);
				acell.setCellStyle(style);
				String name = field.getName().replaceAll("_", ".");
				Object v=ReflectionUtils.getTierFieldValue(entity, name);
				//是否转换
				if(map.get(field.getName())){
					try {
		//				System.out.println("sssssssssssssss"+map.get(field.getName()));
						Method method = clazz.getMethod("convert"+ StringUtils.capitalize(field.getName()),Object.class,String.class);
						Object vv = method.invoke(clazz.newInstance(),new Object[]{entity,String.valueOf(v)});
						String context = vv == null ? "" : vv.toString();
						setValue(acell ,context);
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					} catch (SecurityException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					} catch (InstantiationException e) {
						e.printStackTrace();
					}
					}else{
						setValue(acell ,v);	
					}
				

			}
		}

		return workbook;
	}

	/**
	 * 导出多个sheet的Excel文件
	 * 
	 * @param <T>
	 */
	public static <T> Workbook exportExcel2(SXSSFWorkbook workbook,
			List reportPolicy08CTList,List reportPolicy08OVList,List reportPolicy09IEList,List reportPolicy05PIList,List reportPolicy05OPList, Class<? extends Risk08CTMessageExcel> class1,Class<? extends Risk08OVMessageExcel> class2,Class<? extends Risk09IEMessageExcel> class3,Class<? extends Risk05PIMessageExcel> class4,Class<? extends Risk05OPMessageExcel> class5) {
		// 表格 08CT集装箱保险、08OV远洋船舶险 、09IE进出口货运险 、05PI保赔保险、05OP船舶油污责任险
		if(reportPolicy08OVList.size()>=0){
			Sheet sheet = workbook.createSheet("远洋船舶险");
			// 表头
			Row header = sheet.createRow(0); 
			manySheet(workbook,class2,header,reportPolicy08OVList,sheet);
		}
		
		if(reportPolicy05PIList.size()>=0){
			Sheet sheet = workbook.createSheet("保赔保险");
			Row header = sheet.createRow(0); 
			manySheet(workbook,class4,header,reportPolicy05PIList,sheet);
		}
		
		if(reportPolicy09IEList.size()>=0){
			Sheet sheet = workbook.createSheet("进出口货运险");
			// 表头
			Row header = sheet.createRow(0); 
			manySheet(workbook,class3,header,reportPolicy09IEList,sheet);
		}
		
		if(reportPolicy08CTList.size()>=0){
			Sheet sheet = workbook.createSheet("集装箱保险");
			// 表头
			Row header = sheet.createRow(0); 
			manySheet(workbook,class1,header,reportPolicy08CTList,sheet);
		}
		
		if(reportPolicy05OPList.size()>=0){
			Sheet sheet = workbook.createSheet("船舶油污责任险");
			Row header = sheet.createRow(0); 
			manySheet(workbook,class5,header,reportPolicy05OPList,sheet);
		}
		return workbook;
	}
	public static <T> void manySheet(SXSSFWorkbook workbook,Class<T> clazz,Row header,List entities,Sheet sheet){
		CellStyle titleStyle = getTitleStyle(workbook);
		final Field[] fields = getSortedFields(clazz.getName());
		// 设置单元格类型
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		for (int i = 0; i < fields.length; i++) {
			ReportColumn annotation = fields[i]
					.getAnnotation(ReportColumn.class);
			Cell cell = header.createCell(i);
			RichTextString text = new XSSFRichTextString(
					annotation.columnName());
			cell.setCellValue(text);
			cell.setCellStyle(titleStyle);
			map.put(fields[i].getName(), annotation.converter());
		}

		// 内容
		for (int i = 0; i < entities.size(); i++) {
			
			Object entity = entities.get(i);
			Row row = sheet.createRow(i + 1);
			CellStyle style = getStyle(workbook, i);
			for (int j = 0; j < fields.length; j++) {
				Field field = fields[j];
				Cell acell =row.createCell(j);
				acell.setCellStyle(style);
				String name = field.getName().replaceAll("_", ".");
				Object v=ReflectionUtils.getTierFieldValue(entity, name);
				//是否转换
				if(map.get(field.getName())){
					try {
		//				System.out.println("sssssssssssssss"+map.get(field.getName()));
						Method method = clazz.getMethod("convert"+ StringUtils.capitalize(field.getName()),Object.class,String.class);
						Object vv = method.invoke(clazz.newInstance(),new Object[]{entity,String.valueOf(v)});
						String context = vv == null ? "" : vv.toString();
						setValue(acell ,context);
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					} catch (SecurityException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					} catch (InstantiationException e) {
						e.printStackTrace();
					}
					}else{
						setValue(acell ,v);	
					}
				

			}
		}
	}
	/**
	 * exce表头单元格样式处理
	 * 
	 * @param workbook
	 * @return
	 */
	public static CellStyle getTitleStyle(Workbook workbook) {
		// 产生Excel表头
		CellStyle titleStyle = workbook.createCellStyle();
		titleStyle.setBorderLeft(CellStyle.BORDER_THIN); // 左边框
		titleStyle.setBorderRight(CellStyle.BORDER_THIN); // 右边框
		titleStyle.setBorderBottom(CellStyle.BORDER_THIN); // 底边框
		titleStyle.setBorderTop(CellStyle.BORDER_THIN); // 顶边框
		titleStyle.setAlignment(CellStyle.ALIGN_CENTER);
		titleStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index); // 填充的背景颜色
		titleStyle.setFillPattern(CellStyle.SOLID_FOREGROUND); // 填充图案

		return titleStyle;
	}

	/**
	 * 
	 * @param clazz
	 * @param entities
	 * 
	 * @return
	 */

	/**
	 * 
	 * @param clazz
	 * @param title
	 * @param entities
	 *            调用格式： Workbook workbook = ImportExport.exportExcel( name,
	 *            entities);
	 *            response.setContentType("application/octet-stream; charset=utf-8"
	 *            );
	 *            response.setHeader("Content-Disposition","attachment; filename="
	 *            + name + ".xls"); workbook.write(response.getOutputStream());
	 * @return
	 */
	public static <T> Workbook exportExcel(Class<T> clazz, String title,
			List entities) {
		logger.info("导出excel,message.getClass(),集装箱保险, reportPolicyList");
		Workbook workbook = ImportExport.exportExcel(new SXSSFWorkbook(), title,
				entities, clazz);
		return workbook;
	}
	//导出多个sheet08CT集装箱保险、08OV远洋船舶险 、09IE进出口货运险 、05PI保赔保险、05OP船舶油污责任险
	public static <T> Workbook exportExcel2(Class<? extends Risk08CTMessageExcel> class1,Class<? extends Risk08OVMessageExcel> class2,Class<? extends Risk09IEMessageExcel> class3,Class<? extends Risk05PIMessageExcel> class4,Class<? extends Risk05OPMessageExcel> class5,
			List entities08CT,List entities08OV,List entities09IE,List entities05PI,List entities05OP) {
		logger.info("导出excel,message.getClass(),集装箱保险, reportPolicyList");
		Workbook workbook = ImportExport.exportExcel2(new SXSSFWorkbook(),
				entities08CT,entities08OV,entities09IE,entities05PI,entities05OP, class1,class2,class3,class4,class5);
		return workbook;
	}

	private static SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	/**
	 * 设置单元格值
	 */
	private static void setValue(Cell cell, Object value) {
			if (value == null) {
			return;
			}
		
		if (value instanceof String) {
			cell.setCellValue((String) value);
		} else if (value instanceof Boolean) {
			Boolean booleanValue = (Boolean) value;
			cell.setCellValue(booleanValue ? "是" : "否");
		} else if (value instanceof Number)
			cell.setCellValue(Double.parseDouble(String.valueOf(value)));
		else if (value instanceof Date)
			cell.setCellValue(dateFormat.format((Date) value));
		else
			cell.setCellValue(String.valueOf(value));

	}

	/**
	 * 根据全限定类名生成排序好的Field数组
	 * 
	 * @param className
	 * @return
	 * @throws ClassNotFoundException
	 */
	public static Field[] getSortedFields(String className) {
		try {
			// 获取类中所有定义的字段
			Class<?> clasVo = Class.forName(className);
			// fields为长度大于等于0的数组
			Field[] fields = clasVo.getDeclaredFields();

			// 定义集合封装注解字段
			ArrayList<Field> annotationFields = new ArrayList<Field>();
			for (Field field : fields) {
				if (field.isAnnotationPresent(ReportColumn.class)) {
					annotationFields.add(field);
				}
			}
			// 将集合转换为数组，以便后面排序
			Field[] annoArray = {};
			annoArray = annotationFields.toArray(annoArray);
			// 将注解的列进行排序,后面根据该序列进行数据的写入
			Arrays.sort(annoArray, new ColumnComparator());
			return annoArray;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public static CellStyle getStyle(SXSSFWorkbook workbook, int i) {
		// 产生Excel表头
		CellStyle style = workbook.createCellStyle();
		style.setBorderLeft((short) 1); // 左边框
		style.setBorderRight((short) 1); // 右边框
		style.setBorderBottom((short) 1);
		style.setBorderTop((short) 1);
		if (i % 2 == 1) {
			style.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.index); // 填充的背景颜色
			style.setFillPattern(CellStyle.SOLID_FOREGROUND); // 填充图案
		}
		return style;
	}
	
	public static void main(String[] args) {
		List al = new ArrayList();
		Product a1 = new Product();
		MemberUser user = new MemberUser();
		user.setId(1L);
		a1.setId(2l);
		a1.setRiskType("09");
		a1.setRiskName("09IE");
		a1.setUser(user);

		al.add(a1);

		Product a2 = new Product();
		MemberUser user2 = new MemberUser();
		user2.setId(33L);
		a2.setId(33l);
		a2.setUser(user2);
		a2.setRiskType("09");
		a2.setRiskName("09IE");
		al.add(a2);
		al.add(a1);

		try {
			ImportExport.exportExcel(Demo.class, "测速", al).write(
					new FileOutputStream("b.xlsx"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
