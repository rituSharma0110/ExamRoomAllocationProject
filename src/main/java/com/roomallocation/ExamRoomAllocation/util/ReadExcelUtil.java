package com.roomallocation.ExamRoomAllocation.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roomallocation.ExamRoomAllocation.service.ExamRoomAllocationService;
import com.roomallocation.ExamRoomAllocation.vo.*;

@Component
public class ReadExcelUtil {
	
	@Autowired
	GenerateExcelUtil excelUtil;
	
	private static final Logger logger = (Logger) LoggerFactory.getLogger(ExamRoomAllocationService.class);
	
	public List<DatesheetVO> getDateSheetList(MultipartFile dateSheetFile, List<BatchMapping> mappingList){
		 try {
			  XSSFWorkbook workbook = new XSSFWorkbook(dateSheetFile.getInputStream());
			  XSSFSheet worksheet = workbook.getSheetAt(0);
			  
			  DataFormatter formatter = new DataFormatter();
			  
			  //Getting number of rows in a sheet
			  int rows = worksheet.getLastRowNum();
			  System.out.println(rows);
			  
			  //Initializing student list of Student VO object 
			  List<DatesheetVO> dateSheetList = new ArrayList<>();
			  
			  XSSFRow headerRow = worksheet.getRow(0);
			  String examName = headerRow.getCell(0).getStringCellValue();
			  logger.info(examName);
			  // looping through each row
			  for(int rowCounter = 1; rowCounter<=rows ; rowCounter++) {
				  String abbreviation = "";
				  // Getting student data of each roll number (each row)
				  DatesheetVO dateSheetObj = new DatesheetVO();
				  dateSheetObj.setExamName(examName);
				  XSSFRow row = worksheet.getRow(rowCounter);
				  XSSFRow firstRow = worksheet.getRow(1);
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
							 for(int i = 0; i< mappingList.size(); i++) {
								 if(mappingList.get(i).getAbbreviation().equals(formatter.formatCellValue(cell).substring(1))) {
									 abbreviation = formatter.formatCellValue(cell).substring(1);
									 break;
								 }
							 }
							 dateSheetObj.setBatchName(formatter.formatCellValue(cell));
						 }
						 
						 if(firstCell.getStringCellValue().equals("Subject Code")) {
							 String batchName = formatter.formatCellValue(cell) + "" + abbreviation;
							 dateSheetObj.setSubjectCode(batchName);
						 }
						 
						 if(firstCell.getStringCellValue().equals("Drawing Subject")) {
							 dateSheetObj.setDrawingSubjFlag(formatter.formatCellValue(cell));
						 }
					  }
					
				  }
				  dateSheetList.add(dateSheetObj);
				 
				
			  }
			 
			  return dateSheetList;
	  
		} catch (Exception e) {
			  logger.error(e.getMessage());
			
		}
		 return null;
	}
	
	public ArrayList<HallDataVO> getHallDataList(XSSFSheet worksheet){
//		final String FILE_NAME = "C:\\Users\\This pc\\Downloads\\Hall Data.xlsx";
		 try {
//			  FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
//			  XSSFWorkbook workbook = new XSSFWorkbook(sheet.getInputStream());
//			  XSSFSheet worksheet = workbook.getSheetAt(0);
			  
			  DataFormatter formatter = new DataFormatter();
			  
			  
			  //Getting number of rows in a sheet
			  int rows = worksheet.getLastRowNum();
			  System.out.println(rows);
			  
			  //Initializing student list of Student VO object 
			  ArrayList<HallDataVO> hallDataList = new ArrayList<>();
			  
			  // looping through each row
			  for(int rowCounter = 0; rowCounter<=rows ; rowCounter++) {
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
						 
						 if(firstCell.getStringCellValue().equals("Control")) {
							 hallDatatObj.setControlContext(formatter.formatCellValue(cell));
						 }
						 
						 if(firstCell.getStringCellValue().equals("Drawing Seats Available")) {
							 hallDatatObj.setDrawingSeatsAvaliable(formatter.formatCellValue(cell));
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
	
	public List<StudentVO> getStudentList(MultipartFile file, List<BatchMapping> mappingList) throws IOException{
		logger.info("start");
		 HashSet<String> courseSet = new HashSet<>();
		 List<StudentVO> studentList = new ArrayList<>();
		 DataFormatter formatter = new DataFormatter();
		 final	String FILE_NAME = file.getOriginalFilename();
	   	 final String fileExtension = FILE_NAME.substring(FILE_NAME.lastIndexOf(".") + 1);
	   	 String semester = "";
	   	 
	   	String mapperName = "";
	   	 if(fileExtension.equals("xls")) {
	   		 
	   		 HSSFWorkbook workbook = new HSSFWorkbook(file.getInputStream());
	   		 HSSFSheet worksheet = workbook.getSheetAt(0);
	   		 
	   		 mapperName = workbook.getSheetAt(0).getRow(1).getCell(CellReference.convertColStringToIndex("E")).getStringCellValue().replace("Report for Program:- ", "");
	   		 
//	   		 System.out.println("MapperName :  " + mapperName );
	   		 boolean isOtherThanBT = false;
	   		 String abbreviation = "";
	   		 for(int i = 0; i< mappingList.size(); i++) {
//	   			 System.out.println((mappingList.get(i).getBatchName()));
	   			 if(mappingList.get(i).getBatchName().equals(mapperName)) {
	   				 isOtherThanBT = true;
	   				 abbreviation = mappingList.get(i).getAbbreviation();
//	   				 System.out.println(abbreviation);
	   				 break;
	   			 }
	   		 }
	   		 
	   		 //Getting number of rows in a sheet
	   		 int rows = worksheet.getLastRowNum();
	   		 System.out.println(rows);
	   		 
	   		 // this is to find row from where headers starts
	   		 int startRow = 0;
	   		 for(int i = 0; i <= rows; i ++) {
	   			HSSFRow row = worksheet.getRow(i); 
	   			
	   			if(row == null || row.toString() == "" || row.toString() == "null") {
	   				continue;
	   			}
   				HSSFCell cell =  row.getCell(0);
   				if(cell == null) {
   					continue;
   				}
   				if(cell.getStringCellValue().equals("SL.No.")) {
   					startRow = i;
   					break;
	   			}
	   			 
	   		 }
	   		 
	   		 int subjCounter = 0;
	   		 List<StudentVO> currentStudentList = new ArrayList<>();
	   		 
	   		 // looping through each row
	   		 for(int rowCounter = startRow; rowCounter<=rows ; rowCounter++) {
	   			 // Getting student data of each roll number (each row)
	   			 StudentVO student = new StudentVO();
	   			 
	   			 HSSFRow row = worksheet.getRow(rowCounter);
	   			 HSSFRow firstRow = worksheet.getRow(startRow);
	   			 int cols = worksheet.getRow(rowCounter).getLastCellNum();
	   			 
	   			 subjCounter = firstRow.getLastCellNum();
	   			 List<String> subjects = new ArrayList<>();
	   			 
	   			 if(rowCounter != startRow ) {
		   			 for(int colCounter = 0; colCounter<subjCounter ; colCounter++) {
		   				 HSSFCell cell =  row.getCell(colCounter);
		   				 
		   				 //First cell object to get headers 
		   				 HSSFCell firstCell = firstRow.getCell(colCounter);
		   				 
		   				 // to skip error when col is null
		   				 if(cell==null) {
		   					 continue;
		   				 }else {
		   					 
		   					 
		   					 //Getting values from each col
		   					 if(firstCell.getStringCellValue().equals("Entity")) {
		   						 student.setFaculty(formatter.formatCellValue(cell));
		   					 }
		   					 
		   					 if(firstCell.getStringCellValue().equals("Branch")) {
		   						 student.setBranch(formatter.formatCellValue(cell));
		   					 }
		   					 
		   					 if(firstCell.getStringCellValue().equals("Student Name")) {
		   						 student.setName(formatter.formatCellValue(cell));
		   					 }
		   					 
		   					 if(firstCell.getStringCellValue().equals("Gender")) {
		   						 student.setGender(formatter.formatCellValue(cell));
		   					 }
		   					 
		   					 if(firstCell.getStringCellValue().equals("Specialization")) {
		   						 student.setSpecialization(formatter.formatCellValue(cell));
		   					 }
		   					 
		   					 if(firstCell.getStringCellValue().equals("Roll Number")) {
		   						 student.setRollNumber(formatter.formatCellValue(cell));
		   					 }
		   					 
		   					 
		   				 }
		   				 
		   			 }
		   			}
		   			if(rowCounter != startRow ) {
		   				// this is for getting subjects for each student
			   			 for(int colCounter = subjCounter-1; colCounter<=cols ; colCounter++) {
			   				 HSSFCell cell =  row.getCell(colCounter);
			   				 if(cell == null || StringUtils.isNumeric(cell.getStringCellValue())) {
			   					 continue;
			   				 }else {
			   					 if(isOtherThanBT) {
			   						 String subjectName = cell.getStringCellValue() + "" + abbreviation;
			   						 subjects.add(subjectName);
			   						 student.setCourses(subjects);
			   						 courseSet.add(subjectName);
//			   						 System.out.println("student: " +  subjectName);
			   					 }else if(!isOtherThanBT){
			   						 subjects.add( cell.getStringCellValue());
			   						 student.setCourses(subjects); 
//			   						 System.out.println("other :" +  cell.getStringCellValue() + "" + abbreviation);
			   						 courseSet.add(cell.getStringCellValue());
			   					 }
			   					 // Hashset for getting all the subjects which will be used to create header for the student_details sheet
			   				 }
			   				 
			   			 }
	   				}
	   			
		   		 if(rowCounter!=startRow) {
		   			 
		   			 studentList.add( student);
		   			 currentStudentList.add(student);
		   		 }	
//	   			
	   			 
	   		 }
	   		
//   			System.out.println(studentList.get(1).getCourses().get(272));
	   		 // this is for getting year -- any other way??
	   		 semester = currentStudentList.get(1).getCourses().get(1).substring(3,4);
//	   		 int yearInd = FILE_NAME.indexOf("SM");
//	   		 String year = FILE_NAME.substring(yearInd+2,yearInd+3);
	   		 ArrayList<String> courseList = new ArrayList<>(courseSet);
	   		 // this will create sorted excel sheet
	   		 excelUtil.createSortedExcel(courseList, studentList, Integer.valueOf(semester), mapperName);
	   	 }else {
	   		 
	   		 XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
	   		 XSSFSheet worksheet = workbook.getSheetAt(0);
	   		 
	   		 //Getting number of rows in a sheet
	   		 int rows = worksheet.getLastRowNum();
	   		 System.out.println(rows);
	   		 
	   		 // this is to find row from where headers starts
	   		 int startRow = 0;
	   		 for(int i = 0; i <= rows; i ++) {
	   			XSSFRow row = worksheet.getRow(i); 
	   			
	   			if(row == null || row.toString() == "" || row.toString() == "null") {
	   				continue;
	   			}
   				XSSFCell cell =  row.getCell(0);
   				if(cell == null) {
   					continue;
   				}
   				if(cell.getStringCellValue().equals("SL.No.")) {
   					startRow = i;
   					break;
	   			}
	   			 
	   		 }
	   		 
	   		 int subjCounter = 0;
	   		 List<StudentVO> currentStudentList = new ArrayList<>();
	   		 
	   	// looping through each row
	   		 for(int rowCounter = startRow; rowCounter<=rows ; rowCounter++) {
	   			 // Getting student data of each roll number (each row)
	   			 StudentVO student = new StudentVO();
	   			 
	   			 XSSFRow row = worksheet.getRow(rowCounter);
	   			 XSSFRow firstRow = worksheet.getRow(startRow);
	   			 int cols = worksheet.getRow(rowCounter).getLastCellNum();
	   			 
	   			 subjCounter = firstRow.getLastCellNum();
	   			 List<String> subjects = new ArrayList<>();
	   			 
	   			 if(rowCounter != startRow ) {
		   			 for(int colCounter = 0; colCounter<subjCounter ; colCounter++) {
		   				 XSSFCell cell =  row.getCell(colCounter);
		   				 
		   				 //First cell object to get headers 
		   				 XSSFCell firstCell = firstRow.getCell(colCounter);
		   				 
		   				 // to skip error when col is null
		   				 if(cell==null) {
		   					 continue;
		   				 }else {
		   					 
		   					 
		   					 //Getting values from each col
		   					 if(firstCell.getStringCellValue().equals("Entity")) {
		   						 student.setFaculty(formatter.formatCellValue(cell));
		   					 }
		   					 
		   					 if(firstCell.getStringCellValue().equals("Branch")) {
		   						 student.setBranch(formatter.formatCellValue(cell));
		   					 }
		   					 
		   					 if(firstCell.getStringCellValue().equals("Student Name")) {
		   						 student.setName(formatter.formatCellValue(cell));
		   					 }
		   					 
		   					 if(firstCell.getStringCellValue().equals("Gender")) {
		   						 student.setGender(formatter.formatCellValue(cell));
		   					 }
		   					 
		   					 if(firstCell.getStringCellValue().equals("Specialization")) {
		   						 student.setSpecialization(formatter.formatCellValue(cell));
		   					 }
		   					 
		   					 if(firstCell.getStringCellValue().equals("Roll Number")) {
		   						 student.setRollNumber(formatter.formatCellValue(cell));
		   					 }
		   					 
		   				 }
		   				 
		   			 }
		   			}
	   			 logger.info(String.valueOf(subjCounter));
		   			if(rowCounter != startRow ) {
		   				// this is for getting subjects for each student
			   			 for(int colCounter = subjCounter-1; colCounter<=cols ; colCounter++) {
			   				 XSSFCell cell =  row.getCell(colCounter);
			   				 if(cell == null || StringUtils.isNumeric(cell.getStringCellValue())) {
			   					 continue;
			   				 }else {
			   					 subjects.add(cell.getStringCellValue());
			   					 student.setCourses(subjects);
			   					 // Hashset for getting all the subjects which will be used to create header for the student_details sheet
			   					 courseSet.add(cell.getStringCellValue());
			   				 }
			   				 
			   			 }
	   				}
		   		 
	   			 studentList.add( student);
	   			 currentStudentList.add(student);
	   			 
	   		 }
	   		 // this is for getting year -- any other way??
	   		 semester = currentStudentList.get(1).getCourses().get(1).substring(3,4);
//	   		 int yearInd = FILE_NAME.indexOf("SM");
//	   		 String year = FILE_NAME.substring(yearInd+2,yearInd+3);
	   		 ArrayList<String> courseList = new ArrayList<>(courseSet);
	   		 // this will create sorted excel sheet
	   		 excelUtil.createSortedExcel(courseList, currentStudentList, Integer.valueOf(semester), mapperName);
	   		
	   		 return studentList;
	   		 
	   	 }
	   	 ObjectMapper mapper = new ObjectMapper();
		 System.out.println(studentList.get(1).getCourses().get(2));
		return studentList;
	}

	public List<RowColVO> getMatrix(MultipartFile matrixFile) {
		try {
			  XSSFWorkbook workbook = new XSSFWorkbook(matrixFile.getInputStream());
			  XSSFSheet worksheet = workbook.getSheetAt(0);
			  
			  DataFormatter formatter = new DataFormatter();
			  
			  //Getting number of rows in a sheet
			  int rows = worksheet.getLastRowNum();
			  System.out.println(rows);
			  
			  //Initializing student list of Student VO object 
			  List<RowColVO> rowColList = new ArrayList<>();
			  
			  // looping through each row
			  for(int rowCounter = 0; rowCounter<rows ; rowCounter++) {
				  // Getting student data of each roll number (each row)
				  RowColVO rowColVO = new RowColVO();
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
							 rowColVO.setRoomName(formatter.formatCellValue(cell));
						 }
						 
						 if(firstCell.getStringCellValue().equals("Rows")) {
							 rowColVO.setRows((formatter.formatCellValue(cell)));
						 }
						 
						 if(firstCell.getStringCellValue().equals("Columns")) {
							 rowColVO.setCols(formatter.formatCellValue(cell));
						 }
						 
					  }
					
				  }
				  rowColList.add(rowCounter, rowColVO);
				 
				
			  }
			  ObjectMapper mapper = new ObjectMapper();
			  System.out.println(mapper.writeValueAsString(rowColList));
			  return rowColList;
	  
		} catch (Exception e) {
			  logger.error(e.getMessage());
			
		}
		 return null;
	}
	
	
	public List<SuspendedStuVO> getSuspendList(MultipartFile suspendedStuFile) {
		try {
			  XSSFWorkbook workbook = new XSSFWorkbook(suspendedStuFile.getInputStream());
			  XSSFSheet worksheet = workbook.getSheetAt(0);
			  
			  DataFormatter formatter = new DataFormatter();
			  
			  //Getting number of rows in a sheet
			  int rows = worksheet.getLastRowNum();
			  System.out.println(rows);
			  
			  int startRow = 0;
		   		 for(int i = 0; i <= rows; i ++) {
		   			XSSFRow row = worksheet.getRow(i); 
		   			
		   			if(row == null || row.toString() == "" || row.toString() == "null") {
		   				continue;
		   			}
	   				XSSFCell cell =  row.getCell(0);
	   				if(cell == null) {
	   					continue;
	   				}
	   				if(formatter.formatCellValue(cell).equals("SL.No.")) {
	   					startRow = i;
	   					break;
		   			}
		   			 
		   		 }
			  
		   		 System.out.println(startRow);
			  //Initializing student list of Student VO object 
			  List<SuspendedStuVO> suspendList = new ArrayList<>();
			  
			  // looping through each row
			  for(int rowCounter = startRow; rowCounter<=rows ; rowCounter++) {
				  // Getting student data of each roll number (each row)
				  SuspendedStuVO suspendedVO = new SuspendedStuVO();
				  XSSFRow row = worksheet.getRow(rowCounter);
		   		  XSSFRow firstRow = worksheet.getRow(startRow);
		   		  int cols = worksheet.getRow(rowCounter).getLastCellNum();
				  System.out.println(cols);
				  for(int colCounter = 0; colCounter<cols ; colCounter++) {
					  XSSFCell cell =  row.getCell(colCounter);
					  if(cell==null) {
						  
					  }else {
						 
						 //First cell object to get headers 
						  XSSFCell firstCell = firstRow.getCell(colCounter);
						 if(firstCell==null) {
							 continue;
						 }
						 
						 //Getting values from each col
						 if(firstCell.getStringCellValue().equals("Roll Number")) {
							 suspendedVO.setRollNo(String.valueOf(formatter.formatCellValue(cell)));
						 }
						 
						 if(firstCell.getStringCellValue().equals("Student Name")) {
							 suspendedVO.setStudentName((formatter.formatCellValue(cell)));
						 }
						 
					  }
					
				  }
				  
				  
				  suspendList.add(suspendedVO);
				 
				
			  }
			  ObjectMapper mapper = new ObjectMapper();
			  System.out.println(mapper.writeValueAsString(suspendList));
			  return suspendList;
//	  
		} catch (Exception e) {
			  logger.error(e.getMessage());
			
		}
		 return null;
	}

	public void createCountExcel(MultipartFile file, List<SuspendedStuVO> suspendList) throws IOException {
		// TODO Auto-generated method stub
		 HashSet<String> courseSet = new HashSet<>();
		 logger.info("Creating Count Excel File");
		 List<StudentVO> studentList = new ArrayList<>();
		 DataFormatter formatter = new DataFormatter();
		 final	String FILE_NAME = file.getOriginalFilename();
	   	 final String fileExtension = FILE_NAME.substring(FILE_NAME.lastIndexOf(".") + 1);
	   	 
	   	 
	   	ArrayList<String> suspendedRollNo = new ArrayList<>();
		if(suspendList!=null) {
			for(int i = 0 ; i < suspendList.size(); i++) {
				suspendedRollNo.add(suspendList.get(i).getRollNo());
			}
			
		}
		
	   	 if(fileExtension.equals("xls")) {
	   		 
	   		 HSSFWorkbook workbook = new HSSFWorkbook(file.getInputStream());
	   		 HSSFSheet worksheet = workbook.getSheetAt(0);
	   		 
	   		 //Getting number of rows in a sheet
	   		 int rows = worksheet.getLastRowNum();
	   		 System.out.println(rows);
	   		 
	   		 // this is to find row from where headers starts
	   		 int startRow = 0;
	   		 for(int i = 0; i <= rows; i ++) {
	   			HSSFRow row = worksheet.getRow(i); 
	   			
	   			if(row == null || row.toString() == "" || row.toString() == "null") {
	   				continue;
	   			}
  				HSSFCell cell =  row.getCell(0);
  				if(cell == null) {
  					continue;
  				}
  				if(cell.getStringCellValue().equals("SL.No.")) {
  					startRow = i;
  					break;
	   			}
	   			 
	   		 }
	   		 
	   		 int subjCounter = 0;
	   		 List<StudentVO> currentStudentList = new ArrayList<>();
	   		 
	   		 // looping through each row
	   		 for(int rowCounter = startRow; rowCounter<=rows ; rowCounter++) {
	   			 // Getting student data of each roll number (each row)
	   			 StudentVO student = new StudentVO();
	   			 
	   			 HSSFRow row = worksheet.getRow(rowCounter);
	   			 HSSFRow firstRow = worksheet.getRow(startRow);
	   			 int cols = worksheet.getRow(rowCounter).getLastCellNum();
	   			 
	   			 subjCounter = firstRow.getLastCellNum();
	   			 List<String> subjects = new ArrayList<>();
	   			 
	   			 if(rowCounter != startRow ) {
		   			 for(int colCounter = 0; colCounter<subjCounter ; colCounter++) {
		   				 HSSFCell cell =  row.getCell(colCounter);
		   				 
		   				 //First cell object to get headers 
		   				 HSSFCell firstCell = firstRow.getCell(colCounter);
		   				 
		   				 // to skip error when col is null
		   				 if(cell==null) {
		   					 continue;
		   				 }else {
		   					 
		   					 //Getting values from each col
		   					 if(firstCell.getStringCellValue().equals("Gender")) {
		   						 student.setGender(formatter.formatCellValue(cell));
		   					 }
		   					 if(firstCell.getStringCellValue().equals("Roll Number")) {
		   						 student.setRollNumber(formatter.formatCellValue(cell));
		   					 }
		   				 }
		   				 
		   			 }
		   			}
		   			if(rowCounter != startRow ) {
		   				// this is for getting subjects for each student
			   			 for(int colCounter = subjCounter-1; colCounter<=cols ; colCounter++) {
			   				 HSSFCell cell =  row.getCell(colCounter);
			   				 if(cell == null || StringUtils.isNumeric(cell.getStringCellValue())) {
			   					 continue;
			   				 }else {
			   					 subjects.add(cell.getStringCellValue());
			   					 student.setCourses(subjects);
			   					 // Hashset for getting all the subjects which will be used to create header for the student_details sheet
			   					 courseSet.add(cell.getStringCellValue());
			   				 }
			   				 
			   			 }
	   				}
	   			
		   		 if(rowCounter!=startRow) {
		   			 
		   			 studentList.add( student);
		   			 currentStudentList.add(student);
		   		 }	
	   			 
	   		 }
	   		
	   		 ArrayList<String> courseList = new ArrayList<>(courseSet);
	   		 ArrayList<StudentCount> countList = new ArrayList<>();
	   		 for(int i = 0; i < courseList.size(); i++) {
	   			 StudentCount countObj = new StudentCount();
	   			 int boysCount = 0;
	   			 int girlsCount = 0;
	   			 for(int j = 0 ; j < studentList.size(); j++) {
	   				 if(studentList.get(j).getCourses().contains(courseList.get(i)) && studentList.get(j).getGender().equalsIgnoreCase("M")
	   						 && !suspendedRollNo.contains(studentList.get(j).getRollNumber())) {
	   					 boysCount++;
	   				 }else if(studentList.get(j).getCourses().contains(courseList.get(i)) && studentList.get(j).getGender().equalsIgnoreCase("F")
	   						&& !suspendedRollNo.contains(studentList.get(j).getRollNumber())) {
	   					 girlsCount++;
	   				 }
	   			 }
	   			countObj.setBoysCount(boysCount);
	   			countObj.setGirlsCount(girlsCount);
	   			countObj.setCourse(courseList.get(i));
	   			
	   			countList.add(countObj);
	   		 }
	   		 
	   		 String fileName =  FILE_NAME.substring(0, FILE_NAME.lastIndexOf(".") );
	   		 // this will create count excel sheet
	   		 excelUtil.createCountExcel(countList, fileName);
	   		 
	   	 }else {
	   		 
	   		 XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
	   		 XSSFSheet worksheet = workbook.getSheetAt(0);
	   		 
	   		 //Getting number of rows in a sheet
	   		 int rows = worksheet.getLastRowNum();
	   		 System.out.println(rows);
	   		 
	   		 // this is to find row from where headers starts
	   		 int startRow = 0;
	   		 for(int i = 0; i <= rows; i ++) {
	   			XSSFRow row = worksheet.getRow(i); 
	   			
	   			if(row == null || row.toString() == "" || row.toString() == "null") {
	   				continue;
	   			}
  				XSSFCell cell =  row.getCell(0);
  				if(cell == null) {
  					continue;
  				}
  				if(cell.getStringCellValue().equals("SL.No.")) {
  					startRow = i;
  					break;
	   			}
	   			 
	   		 }
	   		 
	   		 int subjCounter = 0;
	   		 List<StudentVO> currentStudentList = new ArrayList<>();
	   		 
	   		 // looping through each row
	   		 for(int rowCounter = startRow; rowCounter<=rows ; rowCounter++) {
	   			 // Getting student data of each roll number (each row)
	   			 StudentVO student = new StudentVO();
	   			 
	   			 XSSFRow row = worksheet.getRow(rowCounter);
	   			 XSSFRow firstRow = worksheet.getRow(startRow);
	   			 int cols = worksheet.getRow(rowCounter).getLastCellNum();
	   			 
	   			 subjCounter = firstRow.getLastCellNum();
	   			 List<String> subjects = new ArrayList<>();
	   			 
	   			 if(rowCounter != startRow ) {
		   			 for(int colCounter = 0; colCounter<subjCounter ; colCounter++) {
		   				 XSSFCell cell =  row.getCell(colCounter);
		   				 
		   				 //First cell object to get headers 
		   				 XSSFCell firstCell = firstRow.getCell(colCounter);
		   				 
		   				 // to skip error when col is null
		   				 if(cell==null) {
		   					 continue;
		   				 }else {
		   					 
		   					 //Getting values from each col
		   					 if(firstCell.getStringCellValue().equals("Gender")) {
		   						 student.setGender(formatter.formatCellValue(cell));
		   					 }
		   					 if(firstCell.getStringCellValue().equals("Roll Number")) {
		   						 student.setRollNumber(formatter.formatCellValue(cell));
		   					 }
		   					 
		   				 }
		   				 
		   			 }
		   			}
	   			 logger.info(String.valueOf(subjCounter));
		   			if(rowCounter != startRow ) {
		   				// this is for getting subjects for each student
			   			 for(int colCounter = subjCounter-1; colCounter<=cols ; colCounter++) {
			   				 XSSFCell cell =  row.getCell(colCounter);
			   				 if(cell == null || StringUtils.isNumeric(cell.getStringCellValue())) {
			   					 continue;
			   				 }else {
			   					 subjects.add(cell.getStringCellValue());
			   					 student.setCourses(subjects);
			   					 // Hashset for getting all the subjects which will be used to create header for the student_details sheet
			   					 courseSet.add(cell.getStringCellValue());
			   				 }
			   				 
			   			 }
	   				}
		   		 
	   			 studentList.add( student);
	   			 currentStudentList.add(student);
	   			 
	   			ArrayList<String> courseList = new ArrayList<>(courseSet);
		   		 ArrayList<StudentCount> countList = new ArrayList<>();
		   		 for(int i = 0; i < courseList.size(); i++) {
		   			 StudentCount countObj = new StudentCount();
		   			 int boysCount = 0;
		   			 int girlsCount = 0;
		   			 for(int j = 0 ; j < studentList.size(); j++) {
		   				 if(studentList.get(j).getCourses().contains(courseList.get(i)) && studentList.get(j).getGender().equalsIgnoreCase("M")
		   						&& !suspendedRollNo.contains(studentList.get(j).getRollNumber())) {
		   					 boysCount++;
		   				 }else if(studentList.get(j).getCourses().contains(courseList.get(i)) && studentList.get(j).getGender().equalsIgnoreCase("F")
		   						&& !suspendedRollNo.contains(studentList.get(j).getRollNumber())) {
		   					 girlsCount++;
		   				 }
		   			 }
		   			countObj.setBoysCount(boysCount);
		   			countObj.setGirlsCount(girlsCount);
		   			countObj.setCourse(courseList.get(i));
		   			
		   			countList.add(countObj);
		   		 }
		   		 
		   		 String fileName =  FILE_NAME.substring(0, FILE_NAME.lastIndexOf(".") );
		   		 // this will create count excel sheet
		   		 excelUtil.createCountExcel(countList, fileName);
	   		 
	   		 }
	   	 }
	}

	public List<BatchMapping> getMappingList(MultipartFile batchMapFile) {
		try {
		 XSSFWorkbook workbook = new XSSFWorkbook(batchMapFile.getInputStream());
		  XSSFSheet worksheet = workbook.getSheetAt(0);
		  
		  DataFormatter formatter = new DataFormatter();
		  
		  //Getting number of rows in a sheet
		  int rows = worksheet.getLastRowNum();
		  System.out.println(rows);
		  
		  //Initializing student list of Student VO object 
		  List<BatchMapping> batchList = new ArrayList<>();
		  
		  XSSFRow headerRow = worksheet.getRow(0);
		  // looping through each row
		  for(int rowCounter = 0; rowCounter<=rows ; rowCounter++) {
			  // Getting student data of each roll number (each row)
			  BatchMapping batchMapObj = new BatchMapping();
			  XSSFRow row = worksheet.getRow(rowCounter);
			  XSSFRow firstRow = worksheet.getRow(0);
			  int cols = worksheet.getRow(rowCounter).getLastCellNum();
			  for(int colCounter = 0; colCounter<cols ; colCounter++) {
				  XSSFCell cell =  row.getCell(colCounter);
				  // to skip error when col is null
				  if(cell==null) {
					  
				  }else {
					 
					 //First cell object to get headers 
					  XSSFCell firstCell = firstRow.getCell(colCounter);
					 if(firstCell==null) {
						 continue;
					 }
					 
					 //Getting values from each col
					 if(firstCell.getStringCellValue().equals("Batch")) {
						 batchMapObj.setBatchName(formatter.formatCellValue(cell));
					 }
					 
					 if(firstCell.getStringCellValue().equals("Abbr")) {
						 batchMapObj.setAbbreviation((formatter.formatCellValue(cell)));
					 }
					 
					 
				  }
				
			  }
			  batchList.add(batchMapObj);
			 
			
		  }
		 
		  ObjectMapper mapper = new ObjectMapper();
		  System.out.println(mapper.writeValueAsString(batchList));	  
		  return batchList;
 
	} catch (Exception e) {
		  logger.error(e.getMessage());
		
	}
	 return null;
	}

	public ArrayList<AlgoOutputVO> readOutputFile(MultipartFile outputFile, List<BatchMapping> mappingList) throws IOException {
		// TODO Auto-generated method stub
		  XSSFWorkbook workbook = new XSSFWorkbook(outputFile.getInputStream());
		  XSSFSheet worksheet = workbook.getSheetAt(0);
		  
		  ArrayList<String> abbreviation = new ArrayList<>();
		  for(int i = 0; i<mappingList.size(); i++) {
			  abbreviation.add(mappingList.get(i).getAbbreviation());
		  }
		  
		  DataFormatter formatter = new DataFormatter();
		  
		  //Getting number of rows in a sheet
		  int rows = worksheet.getLastRowNum();
		  System.out.println(rows);
		  
		  //Initializing student list of Student VO object 
		  ArrayList<AlgoOutputVO> outputList = new ArrayList<>();
		  
		  // looping through each row
		  for(int rowCounter = 7; rowCounter<rows ; rowCounter++) {
			  // Getting student data of each roll number (each row)
			  AlgoOutputVO algoObj = new AlgoOutputVO();
			  XSSFRow row = worksheet.getRow(rowCounter);
			  if(row.getCell(0).getStringCellValue().equals("EN") || row.getCell(0).getStringCellValue().equals("AR")) {
				  continue;
			  }
			  algoObj.setClassRoom(row.getCell(1).getStringCellValue());
			  System.out.println(row.getCell(1).getStringCellValue());
			  XSSFRow firstRow = worksheet.getRow(1);
			  XSSFRow secondRow = worksheet.getRow(2);
			  int cols = worksheet.getRow(rowCounter).getLastCellNum();
			  ArrayList<String> list = new ArrayList<>();
			  for(int colCounter = 2; colCounter<cols-1 ; colCounter++) {
//				  System.out.println(colCounter);
				  XSSFCell cell =  row.getCell(colCounter);
				  // to skip error when col is null
				  if(cell==null) {
//					  System.out.println("null");
				  }else {
//					 System.out.println(formatter.formatCellValue(cell));
					  if(!abbreviation.contains(firstRow.getCell(colCounter).getStringCellValue().substring(1))) {
						  list.add(secondRow.getCell(colCounter).getStringCellValue());
					  }else {
						  list.add(secondRow.getCell(colCounter).getStringCellValue()+ "" + firstRow.getCell(colCounter).getStringCellValue().substring(1));
					  }
					 list.add(formatter.formatCellValue(cell));
					 algoObj.setValues(list);
					 
				  }
				
			  }
			  outputList.add(algoObj);
			 
			
		  }
		 
		  ObjectMapper mapper = new ObjectMapper();
		  System.out.println(mapper.writeValueAsString(outputList));	  
		  return outputList;
		
	}
	   	
	

}
