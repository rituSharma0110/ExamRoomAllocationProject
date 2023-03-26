package com.roomallocation.ExamRoomAllocation.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roomallocation.ExamRoomAllocation.util.GenerateAlgo;
import com.roomallocation.ExamRoomAllocation.util.GenerateExcelUtil;
import com.roomallocation.ExamRoomAllocation.util.ReadExcelUtil;
import com.roomallocation.ExamRoomAllocation.vo.DatesheetVO;
import com.roomallocation.ExamRoomAllocation.vo.HallDataVO;
import com.roomallocation.ExamRoomAllocation.vo.StudentVO;


@Service
public class ExamRoomAllocationService {
	
	@Autowired
	BuildProperties buildProperties;
	
	@Autowired
	GenerateExcelUtil excelUtil;
	
	@Autowired
	ReadExcelUtil readExcelUtil;
	
	@Autowired
	GenerateAlgo generateAlgorithm;
	
	private static final Logger logger = (Logger) LoggerFactory.getLogger(ExamRoomAllocationService.class);
	
	HashSet<String> courseSet = new HashSet<>();

	public String healthCheck() {
		// TODO Auto-generated method stub
		final String methodName = "healthCheck()";
		logger.info("ExamRoomAllocationService : {}: Performing Health Check", methodName);
		String healthCheck = new String();
		healthCheck = new String("Health Check Successfull" + ",BuildVersion:" + buildProperties.getVersion() + ",BuildTime:"
				+ buildProperties.getTime() + ",BuildGroup:" + buildProperties.getGroup());
		logger.info("Health Check Successfull");
		return healthCheck;
	}

	public String generateSeatingArrangement(MultipartFile[] files, MultipartFile dateSheetFile, MultipartFile hallFile) {
		// TODO Auto-generated method stub
//		 final String FILE_NAME = "C:\\Users\\This pc\\Downloads\\B.Tech SM7 1.10.xls";
		
		 //Initializing student list of Student VO object 
		 List<StudentVO> studentList = new ArrayList<>();
		 DataFormatter formatter = new DataFormatter();
		 Arrays.asList(files).stream().forEach(file -> {
             try {
            	 final	String FILE_NAME = file.getOriginalFilename();
            	 final String fileExtension = FILE_NAME.substring(FILE_NAME.lastIndexOf(".") + 1);
            	 
            	 if(fileExtension.equals("xls")) {
            		 
            		 HSSFWorkbook workbook = new HSSFWorkbook(file.getInputStream());
            		 HSSFSheet worksheet = workbook.getSheetAt(0);
            		 
            		 // this is for getting year -- any other way??
            		 int yearInd = FILE_NAME.indexOf("SM");
            		 String year = FILE_NAME.substring(yearInd+2,yearInd+3);
            		 
            		 //Getting number of rows in a sheet
            		 int rows = worksheet.getLastRowNum();
            		 System.out.println(rows);
            		 
            		 List<StudentVO> currentStudentList = new ArrayList<>();
            		 
            		 // looping through each row
            		 for(int rowCounter = 0; rowCounter<rows ; rowCounter++) {
            			 // Getting student data of each roll number (each row)
            			 StudentVO student = new StudentVO();
            			 HSSFRow row = worksheet.getRow(rowCounter);
            			 HSSFRow firstRow = worksheet.getRow(0);
            			 int cols = worksheet.getRow(rowCounter).getLastCellNum();
            			 List<String> subjects = new ArrayList<>();
            			 for(int colCounter = 0; colCounter<cols ; colCounter++) {
            				 HSSFCell cell =  row.getCell(colCounter);
            				 
            				 // to skip error when col is null
            				 if(cell==null) {
            					 
            				 }else {
            					 
            					 //First cell object to get headers 
            					 HSSFCell firstCell = firstRow.getCell(colCounter);
            					 if(firstCell.getStringCellValue().equals("Subjects")) {
            						 subjects.add(cell.getStringCellValue());
            						 student.setCourses(subjects);
            						 
            						 // Hashset for getting all the subjects which will be used to create header for the student_details sheet
            						 courseSet.add(cell.getStringCellValue());
            						 
            					 }
            					 
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
            			 studentList.add(rowCounter, student);
            			 currentStudentList.add(rowCounter, student);
            			 
            		 }
            		 ArrayList<String> courseList = new ArrayList<>(courseSet);
            		 // this will create sorted excel sheet
            		 excelUtil.createSortedExcel(courseList, currentStudentList, Integer.valueOf(year));
            	 }else {
            		 XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
            		 XSSFSheet worksheet = workbook.getSheetAt(0);
            		 
            		 // this is for getting year -- any other way??
            		 int yearInd = FILE_NAME.indexOf("SM");
            		 String year = FILE_NAME.substring(yearInd+2,yearInd+3);
            		 
            		 //Getting number of rows in a sheet
            		 int rows = worksheet.getLastRowNum();
            		 System.out.println(rows);
            		 
            		 List<StudentVO> currentStudentList = new ArrayList<>();
            		 
            		 // looping through each row
            		 for(int rowCounter = 0; rowCounter<rows ; rowCounter++) {
            			 // Getting student data of each roll number (each row)
            			 StudentVO student = new StudentVO();
            			 XSSFRow row = worksheet.getRow(rowCounter);
            			 XSSFRow firstRow = worksheet.getRow(0);
            			 int cols = worksheet.getRow(rowCounter).getLastCellNum();
            			 List<String> subjects = new ArrayList<>();
            			 for(int colCounter = 0; colCounter<cols ; colCounter++) {
            				 XSSFCell cell =  row.getCell(colCounter);
            				 
            				 // to skip error when col is null
            				 if(cell==null) {
            					 
            				 }else {
            					 
            					 //First cell object to get headers 
            					 XSSFCell firstCell = firstRow.getCell(colCounter);
            					 if(firstCell.getStringCellValue().equals("Subjects")) {
            						 subjects.add(cell.getStringCellValue());
            						 student.setCourses(subjects);
            						 
            						 // Hashset for getting all the subjects which will be used to create header for the student_details sheet
            						 courseSet.add(cell.getStringCellValue());
            						 
            					 }
            					 
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
            			 studentList.add(rowCounter, student);
            			 currentStudentList.add(rowCounter, student);
            			 
            		 }
            		 ArrayList<String> courseList = new ArrayList<>(courseSet);
            		 // this will create sorted excel sheet
            		 excelUtil.createSortedExcel(courseList, currentStudentList, Integer.valueOf(year));
            	 }
             } catch (IOException e) {

             }
         });
		 try {
			  
			  logger.info(studentList.get(1).getCourses().toString());
			  logger.info(studentList.get(300).getCourses().toString());
			  
//			  logger.info(Arrays.toString(courseSet.toArray()));
			  ArrayList<DatesheetVO> dateSheetList = (ArrayList<DatesheetVO>) readExcelUtil.getDateSheetList(dateSheetFile);
			  ArrayList<HallDataVO> hallDataList = (ArrayList<HallDataVO>) readExcelUtil.getHallDataList(hallFile);
			  ObjectMapper mapper = new ObjectMapper();
			  String dateList = mapper.writeValueAsString(dateSheetList);
			  String hallList = mapper.writeValueAsString(hallDataList);
//			  logger.info(dateList);
//			  logger.info(hallList);
			  
			  
			  generateAlgorithm.generateAlgo(dateSheetList, hallDataList, studentList);
			  
			  
	  
		} catch (Exception e) {
			  logger.error(e.getMessage());
			
		}
		 return null;
	}

}
