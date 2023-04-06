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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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

	MultiValueMap<String, List<String>> outputMap = new LinkedMultiValueMap<>();
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
//		 final String FILE_NAME = "C:\\Users\\This pc\\Downloads\\B.Tech SM7 1.10.xls";
		
		 //Initializing student list of Student VO object 
		 List<StudentVO> studentList = new ArrayList<>();
		
		 Arrays.asList(files).stream().forEach(file -> {
             try {
            	 List<StudentVO> list = readExcelUtil.getStudentList(file, courseSet);
            	 studentList.addAll(list);
            	 
             } catch (IOException e) {

             }
         });
		 try {
			  
//			  logger.info(studentList.get(1).getCourses().toString());
//			  logger.info(studentList.get(300).getCourses().toString());
			  
//			  logger.info(Arrays.toString(courseSet.toArray()));
			  ArrayList<ArrayList<HallDataVO>> hallDataList = new ArrayList<ArrayList<HallDataVO>>();
			  XSSFWorkbook workbook = new XSSFWorkbook(hallFile.getInputStream());
			  ArrayList<DatesheetVO> dateSheetList = (ArrayList<DatesheetVO>) readExcelUtil.getDateSheetList(dateSheetFile);
			  
			  XSSFWorkbook seatingChart = new XSSFWorkbook();
			  for (int i = 0; i < workbook.getNumberOfSheets(); i++)
			  {
				  ArrayList<String> batch = new ArrayList<>();
				  ArrayList<String> batchSub = new ArrayList<>();
				  StringBuilder shift = new StringBuilder("Shift ");
				  String startTime = null;
				  String endTime = null;
				  for(int j = 0 ; j < dateSheetList.size(); j++) {
						if(dateSheetList.get(j).getShift()!= null) {
							if(dateSheetList.get(j).getShift().equals(String.valueOf(i+1))) {
								batch.add(dateSheetList.get(j).getBatchName());
								batchSub.add(dateSheetList.get(j).getSubjectCode());
								startTime = dateSheetList.get(j).getStartTime();
								endTime = dateSheetList.get(j).getEndTime();
						}
						
					}
				  }
				  shift.append((i+1) + " ").append(dateSheetList.get(1).getDate().replace("/", "-") + " ");
				  XSSFSheet sheet = workbook.getSheetAt(i);
				  ArrayList<HallDataVO> list  = readExcelUtil.getHallDataList(sheet);
				  hallDataList.add(list);
				  String examDate = dateSheetList.get(1).getDate().replace("/", "-");
				  outputMap = generateAlgorithm.generateAlgo(dateSheetList, list, studentList);
				  excelUtil.seatingChart(outputMap, batch, batchSub, new String(shift), seatingChart, startTime, endTime, 
						  examDate, studentList, outputMap);
			  }
			  
			  ObjectMapper mapper = new ObjectMapper();
//			  String dateList = mapper.writeValueAsString(dateSheetList);
//			  String hallList = mapper.writeValueAsString(hallDataList);
//			  logger.info(dateList);
//			  logger.info(hallList);
			  
			 
//			  
	  
		} catch (Exception e) {
			  logger.error(e.getMessage());
			
		}
		 return null;
	}

}
