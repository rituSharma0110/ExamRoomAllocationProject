package com.roomallocation.ExamRoomAllocation.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.roomallocation.ExamRoomAllocation.service.ExamRoomAllocationService;
import com.roomallocation.ExamRoomAllocation.vo.*;

@Component
public class ReadExcelUtil {
	
	private static final Logger logger = (Logger) LoggerFactory.getLogger(ExamRoomAllocationService.class);
	
	public List<DatesheetVO> getDateSheetList(MultipartFile dateSheetFile){
//		final String FILE_NAME = "C:\\Users\\This pc\\Downloads\\Datesheet.xlsx";
		 try {
//			  FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
			  XSSFWorkbook workbook = new XSSFWorkbook(dateSheetFile.getInputStream());
			  XSSFSheet worksheet = workbook.getSheetAt(0);
			  
			  DataFormatter formatter = new DataFormatter();
			  
			  //Getting number of rows in a sheet
			  int rows = worksheet.getLastRowNum();
			  System.out.println(rows);
			  
			  //Initializing student list of Student VO object 
			  List<DatesheetVO> dateSheetList = new ArrayList<>();
			  
			  // looping through each row
			  for(int rowCounter = 0; rowCounter<rows ; rowCounter++) {
				  // Getting student data of each roll number (each row)
				  DatesheetVO dateSheetObj = new DatesheetVO();
				  XSSFRow row = worksheet.getRow(rowCounter);
				  XSSFRow firstRow = worksheet.getRow(0);
				  int cols = worksheet.getRow(rowCounter).getLastCellNum();
				  for(int colCounter = 0; colCounter<cols ; colCounter++) {
					  XSSFCell cell =  row.getCell(colCounter);
//					  logger.info("Inside read excel util");
					  // to skip error when col is null
					  if(cell==null) {
						  
					  }else {
						 
						 //First cell object to get headers 
						  XSSFCell firstCell = firstRow.getCell(colCounter);
						 if(firstCell==null) {
							 continue;
						 }
						 
						 //Getting values from each col
						 if(firstCell.getStringCellValue().equals("Date")) {
							 dateSheetObj.setDate(formatter.formatCellValue(cell));
						 }
						 
						 if(firstCell.getStringCellValue().equals("Shift")) {
							 dateSheetObj.setShift((formatter.formatCellValue(cell)));
						 }
						 
						 if(firstCell.getStringCellValue().equals("Start Time")) {
							 dateSheetObj.setStartTime(formatter.formatCellValue(cell));
						 }
						 
						 if(firstCell.getStringCellValue().equals("End Time")) {
							 dateSheetObj.setEndTime(formatter.formatCellValue(cell));
						 }
						 
						 if(firstCell.getStringCellValue().equals("Batch Name")) {
							 dateSheetObj.setBatchName(formatter.formatCellValue(cell));
						 }
						 
						 if(firstCell.getStringCellValue().equals("Subject Code")) {
							 dateSheetObj.setSubjectCode(formatter.formatCellValue(cell));
						 }
						 
					  }
					
				  }
				  dateSheetList.add(rowCounter, dateSheetObj);
				 
				
			  }
			  return dateSheetList;
	  
		} catch (Exception e) {
			  logger.error(e.getMessage());
			
		}
		 return null;
	}
	
	public List<HallDataVO> getHallDataList(MultipartFile hallFile){
//		final String FILE_NAME = "C:\\Users\\This pc\\Downloads\\Hall Data.xlsx";
		 try {
//			  FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
			  XSSFWorkbook workbook = new XSSFWorkbook(hallFile.getInputStream());
			  XSSFSheet worksheet = workbook.getSheetAt(0);
			  
			  DataFormatter formatter = new DataFormatter();
			  
			  
			  //Getting number of rows in a sheet
			  int rows = worksheet.getLastRowNum();
			  System.out.println(rows);
			  
			  //Initializing student list of Student VO object 
			  List<HallDataVO> hallDataList = new ArrayList<>();
			  
			  // looping through each row
			  for(int rowCounter = 0; rowCounter<rows ; rowCounter++) {
				  // Getting student data of each roll number (each row)
				  HallDataVO hallDatatObj = new HallDataVO();
				  XSSFRow row = worksheet.getRow(rowCounter);
				  XSSFRow firstRow = worksheet.getRow(0);
				  
				  int cols = worksheet.getRow(rowCounter).getLastCellNum();
				  for(int colCounter = 0; colCounter<cols ; colCounter++) {
					  XSSFCell cell =  row.getCell(colCounter);
//					  logger.info("Inside read excel util");
					  // to skip error when col is null
					  if(cell==null) {
						  
					  }else {
						 
						 //First cell object to get headers 
						  XSSFCell firstCell = firstRow.getCell(colCounter);
						 if(firstCell==null) {
							 continue;
						 }
						 
						 //Getting values from each col
						 if(firstCell.getStringCellValue().equals("Room Name")) {
							 hallDatatObj.setRoomName(formatter.formatCellValue(cell));
//							 logger.info(formatter.formatCellValue(cell));
						 }
						 
						 if(firstCell.getStringCellValue().equals("Faculty")) {
							 hallDatatObj.setFaculty(formatter.formatCellValue(cell));
						 }
						 
						 if(firstCell.getStringCellValue().equals("Gender")) {
							 hallDatatObj.setGender(formatter.formatCellValue(cell));
						 }
						 
						 if(firstCell.getStringCellValue().equals("Capacity")) {
//							 System.out.println(cell.getNumericCellValue());
							 hallDatatObj.setCapacity(formatter.formatCellValue(cell));
						 }
						 
						 if(firstCell.getStringCellValue().equals("Hall Available")) {
							 hallDatatObj.setIsHallAvailable(formatter.formatCellValue(cell));
						 }
						 
						 
					  }
					
				  }
				  hallDataList.add(rowCounter, hallDatatObj);
				 
				
			  }
			  return hallDataList;
	  
		} catch (Exception e) {
			  logger.error(e.getMessage());
			
		}
		 return null;
	}
	

}
