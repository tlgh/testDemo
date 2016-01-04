package pers.ksy.common;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelUtil {

	public static HSSFWorkbook createSimpleExcel(Map<String, String> headMap,
			List list) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet(); // 产生工作表对象
		buildSimpleSheet(sheet, headMap, list);
		return workbook;
	}

	private static void buildSimpleSheet(HSSFSheet sheet,
			Map<String, String> headMap, List list) {
		String[] hreadNames = headMap.keySet().toArray(new String[] {});
		// 生成表头
		HSSFRow headRow = sheet.createRow(0);
		for (int j = 0; j < hreadNames.length; j++) {
			HSSFCell cell = headRow.createCell(j);
			cell.setCellValue(headMap.get(hreadNames[j]));
		}
		// 生成内容
		for (int i = 1; i <= list.size(); i++) {
			// 产生一行
			HSSFRow row = sheet.createRow(i);
			Object obj = list.get(i - 1);
			for (int j = 0; j < hreadNames.length; j++) {
				String headName = hreadNames[j];
				// 产生第一个单元格
				HSSFCell cell = row.createCell((short) j);
				// 设置单元格内容为字符串型
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				// 往第一个单元格中写入信息
				String value = "";
				try {
					Object v  = BeanUtil.getFieldValue(obj, headName);
					if (null != v) {
						value = v.toString();
					}
				}  catch (NoSuchFieldException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				cell.setCellValue(value);
			}
		}
	}

	public static HSSFWorkbook createSimpleExcel(Map<String, String> headMap,
			Map<String, List> sheetMap) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		int sheetIndex = 0;
		for (String sheetName : sheetMap.keySet()) {
			HSSFSheet sheet = workbook.createSheet(); // 产生工作表对象
			workbook.setSheetName(sheetIndex, sheetName);
			buildSimpleSheet(sheet, headMap, sheetMap.get(sheetName));
			sheetIndex++;
		}
		return workbook;
	}

}
