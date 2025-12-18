package com.Bstackdemo.utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XLUtilities {

	public static FileInputStream fi;
	public static FileOutputStream fo;
	public static XSSFWorkbook workbook;
	public static XSSFSheet sheet;
	public static XSSFRow row;
	public static XSSFCell cell;
	public static CellStyle style;
	String path;

	public XLUtilities(String path) {
	    this.path = path;
	}

	public int getRowCount(String sheetName) throws IOException {
	    fi = new FileInputStream(path);
	    workbook = new XSSFWorkbook(fi);
	    sheet = workbook.getSheet(sheetName);
	    int rowcount = sheet.getLastRowNum();
	    workbook.close();
	    fi.close();
	    return rowcount;
	}
	
	public int getCellCount(String sheetName, int rownum) throws IOException {
	    fi = new FileInputStream(path);
	    workbook = new XSSFWorkbook(fi);
	    sheet = workbook.getSheet(sheetName);
	    row = sheet.getRow(rownum);
	    int cellcount = row.getLastCellNum();
	    workbook.close();
	    fi.close();
	    return cellcount;
	}

	public String getCellData(String sheetName, int rownum, int column) throws IOException {
	    fi = new FileInputStream(path);
	    workbook = new XSSFWorkbook(fi);
	    sheet = workbook.getSheet(sheetName);
	    row = sheet.getRow(rownum);
	    cell = row.getCell(column);

	    DataFormatter formatter = new DataFormatter();
	    String data;
	    try {
	        data = formatter.formatCellValue(cell); 
	        // Returns the formatted value of a cell as a String regardless of its type
	    } catch (Exception e) {
	        data = "";
	    }

	    workbook.close();
	    fi.close();
	    return data;
	}

	public static void setCellData(String xlfile, String xlsheet, int rownum, int column, String data) throws IOException {
	    fi = new FileInputStream(xlfile);
	    workbook = new XSSFWorkbook(fi);
	    sheet = workbook.getSheet(xlsheet);
	    row = sheet.getRow(rownum);

	    cell = row.createCell(column);
	    cell.setCellValue(data);

	    fo = new FileOutputStream(xlfile);
	    workbook.write(fo);
	    workbook.close();
	    fi.close();
	    fo.close();
	}

	public static void fillGreenColor(String xlfile, String xlsheet, int rownum, int column) throws IOException {
	    fi = new FileInputStream(xlfile);
	    workbook = new XSSFWorkbook(fi);
	    sheet = workbook.getSheet(xlsheet);
	    row = sheet.getRow(rownum);
	    cell = row.getCell(column);

	    style = workbook.createCellStyle();
	    style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
	    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

	    cell.setCellStyle(style);
	    fo = new FileOutputStream(xlfile);
	    workbook.write(fo);
	    workbook.close();
	    fi.close();
	    fo.close();
	}

	public static void fillRedColor(String xlfile, String xlsheet, int rownum, int column) throws IOException {
	    fi = new FileInputStream(xlfile);
	    workbook = new XSSFWorkbook(fi);
	    sheet = workbook.getSheet(xlsheet);
	    row = sheet.getRow(rownum);
	    cell = row.getCell(column);

	    style = workbook.createCellStyle();
	    style.setFillForegroundColor(IndexedColors.RED.getIndex());
	    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

	    cell.setCellStyle(style);
	    fo = new FileOutputStream(xlfile);
	    workbook.write(fo);
	    workbook.close();
	    fi.close();
	    fo.close();
	}

	
}
