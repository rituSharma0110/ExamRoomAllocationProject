package com.roomallocation.ExamRoomAllocation.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roomallocation.ExamRoomAllocation.util.GenerateAlgo;
import com.roomallocation.ExamRoomAllocation.util.GenerateExcelUtil;
import com.roomallocation.ExamRoomAllocation.util.ReadExcelUtil;
import com.roomallocation.ExamRoomAllocation.vo.AlgoOutputVO;
import com.roomallocation.ExamRoomAllocation.vo.BatchMapping;
import com.roomallocation.ExamRoomAllocation.vo.DatesheetVO;
import com.roomallocation.ExamRoomAllocation.vo.HallDataVO;
import com.roomallocation.ExamRoomAllocation.vo.RowColVO;
import com.roomallocation.ExamRoomAllocation.vo.StudentVO;
import com.roomallocation.ExamRoomAllocation.vo.SuspendedStuVO;


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

	public String generateSeatingArrangement(MultipartFile[] files, MultipartFile dateSheetFile, MultipartFile hallFile, 
			MultipartFile batchMapFile) {
		
		 
		 List<BatchMapping> mappingList = readExcelUtil.getMappingList(batchMapFile);
		
		 //Initializing student list of Student VO object 
		 List<StudentVO> studentList = new ArrayList<>();
		
		 // Reading each master registration file
		 Arrays.asList(files).stream().forEach(file -> {
             try {
            	 List<StudentVO> list = readExcelUtil.getStudentList(file, mappingList);
            	 studentList.addAll(list);
            	
             } catch (IOException e) {

             }
         });
		 System.out.println("Service : " + studentList.get(1).getCourses().get(0));
		 try {
			 
			  // Initializing arrayList 
			  ArrayList<ArrayList<HallDataVO>> hallDataList = new ArrayList<ArrayList<HallDataVO>>();
			  XSSFWorkbook workbook = new XSSFWorkbook(hallFile.getInputStream());
			  
			  // Getting datesheet object list 
			  ArrayList<DatesheetVO> dateSheetList = (ArrayList<DatesheetVO>) readExcelUtil.getDateSheetList(dateSheetFile,
					  mappingList);
			
			  String examName = dateSheetList.get(0).getExamName();
			  // this loops for each shift
			  XSSFWorkbook seatingChart = new XSSFWorkbook();
			  logger.info(String.valueOf(workbook.getNumberOfSheets()));
			  for (int i = 0; i < workbook.getNumberOfSheets(); i++)
			  {
				  ArrayList<String> batch = new ArrayList<>();
				  ArrayList<String> batchSub = new ArrayList<>();
				  StringBuilder shift = new StringBuilder("Shift-");
				  String startTime = null;
				  String endTime = null;
				  HashMap<String, String> batchAndCourse = new HashMap<>();
				  for(int j = 0 ; j < dateSheetList.size(); j++) {
						if(dateSheetList.get(j).getShift()!= null) {
							if(dateSheetList.get(j).getShift().equals(String.valueOf(i+1))) {
								batch.add(dateSheetList.get(j).getBatchName());
								batchSub.add(dateSheetList.get(j).getSubjectCode());
								startTime = dateSheetList.get(j).getStartTime();
								endTime = dateSheetList.get(j).getEndTime();
								batchAndCourse.put( dateSheetList.get(j).getSubjectCode(), dateSheetList.get(j).getBatchName());
						}
							System.out.println("batch & Course are upar waalaaaaa: " + batchAndCourse);
					}
				  }
					
//				  Getting hall data for each shift	
				  XSSFSheet sheet = workbook.getSheetAt(i);
				  ArrayList<HallDataVO> list  = readExcelUtil.getHallDataList(sheet);
				  
				  // shift name and other variables
				  shift.append((char)(i + 'A') + " ").append(dateSheetList.get(1).getDate().replace("/", "-") + " ");
				  hallDataList.add(list);
				  String examDate = dateSheetList.get(1).getDate().replace("/", "-");
				  System.out.println(examDate);
				  
				  // Getting output from room allocation algo
				  MultiValueMap<String, List<String>> outputMap = new LinkedMultiValueMap<>();
				  outputMap = generateAlgorithm.generateAlgo(dateSheetList, list, studentList, (i+1));
				  
				  ObjectMapper mapper = new ObjectMapper();
				  System.out.println(mapper.writeValueAsString(outputMap));
				  
				  // Converting output of room allocation algo to list of objects
				  ArrayList<AlgoOutputVO> outputList = generateAlgorithm.generateOutput(dateSheetList, list, studentList, (i+1));
				  
				  // Creating seating chart 
				  excelUtil.seatingChart(outputMap, batch, batchSub, new String(shift), seatingChart, startTime, endTime, 
						  examDate, studentList, list, examName, mappingList);
				  
				  
				  
			  }
			  
	  
		} catch (Exception e) {
			  logger.error(e.getMessage());
			
		}
		 return null;
	}

	
	public String generateCountFile(MultipartFile[] files, MultipartFile suspendedStuFile) {
		
		//Getting suspended students list -- optional file
		 List<SuspendedStuVO> suspendList = null;
		 if(suspendedStuFile!=null) {
			suspendList =  readExcelUtil.getSuspendList(suspendedStuFile);
		 }
		 
		 // to handle error
		 final List<SuspendedStuVO> finalList = suspendList;
		 
		 Arrays.asList(files).stream().forEach(file -> {
            try {
             readExcelUtil.createCountExcel(file, finalList);
           	
            } catch (IOException e) {

            }
        });
		
		 return null;
	}

	public String generateOtherFiles(MultipartFile[] files, MultipartFile dateSheetFile, MultipartFile hallFile, 
			MultipartFile matrixFile, MultipartFile suspendedStuFile, MultipartFile batchMapFile, MultipartFile outputFile) throws IOException {
		
		List<BatchMapping> mappingList = readExcelUtil.getMappingList(batchMapFile);
		
		List<RowColVO> matrixList = null;
		 if(matrixFile != null) {
			 matrixList = readExcelUtil.getMatrix(matrixFile);
			 
		 }
		 
		 //Getting suspended students list -- optional file
		 List<SuspendedStuVO> suspendList = null;
		 if(suspendedStuFile!=null) {
			suspendList =  readExcelUtil.getSuspendList(suspendedStuFile);
		 }
		 
		
		 //Initializing student list of Student VO object 
		 List<StudentVO> studentList = new ArrayList<>();
		
		 // Reading each master registration file
		 Arrays.asList(files).stream().forEach(file -> {
            try {
           	 List<StudentVO> list = readExcelUtil.getStudentList(file, mappingList);
           	 studentList.addAll(list);
           	
            } catch (IOException e) {

            }
        });
		 System.out.println("Service : " + studentList.get(1).getCourses().get(0));
		 try {
			 
			  // Initializing arrayList 
			  ArrayList<ArrayList<HallDataVO>> hallDataList = new ArrayList<ArrayList<HallDataVO>>();
			  XSSFWorkbook workbook = new XSSFWorkbook(hallFile.getInputStream());
			  
			  // Getting datesheet object list 
			  ArrayList<DatesheetVO> dateSheetList = (ArrayList<DatesheetVO>) readExcelUtil.getDateSheetList(dateSheetFile,
					  mappingList);
			
			  String examName = dateSheetList.get(0).getExamName();
			  // this loops for each shift
			  XSSFWorkbook seatingChart = new XSSFWorkbook();
			  logger.info(String.valueOf(workbook.getNumberOfSheets()));
			  for (int i = 0; i < workbook.getNumberOfSheets(); i++)
			  {
				  ArrayList<AlgoOutputVO> outputList = readExcelUtil.readOutputFile(outputFile, mappingList,i);
				  ArrayList<String> batch = new ArrayList<>();
				  ArrayList<String> batchSub = new ArrayList<>();
				  StringBuilder shift = new StringBuilder("Shift-");
				  String startTime = null;
				  String endTime = null;
				  HashMap<String, String> batchAndCourse = new HashMap<>();
				  for(int j = 0 ; j < dateSheetList.size(); j++) {
						if(dateSheetList.get(j).getShift()!= null) {
							if(dateSheetList.get(j).getShift().equals(String.valueOf(i+1))) {
								batch.add(dateSheetList.get(j).getBatchName());
								batchSub.add(dateSheetList.get(j).getSubjectCode());
								startTime = dateSheetList.get(j).getStartTime();
								endTime = dateSheetList.get(j).getEndTime();
								batchAndCourse.put( dateSheetList.get(j).getSubjectCode(), dateSheetList.get(j).getBatchName());
						}
						
					}
						System.out.println("batch & Course are : " + batchAndCourse);
				  }
					
//				  Getting hall data for each shift	
				  XSSFSheet sheet = workbook.getSheetAt(i);
				  ArrayList<HallDataVO> list  = readExcelUtil.getHallDataList(sheet);
				  
				  
				  // shift name and other variables
				  
				  StringBuilder headerName = new StringBuilder("Shift-");
				  headerName.append((char)(i + 'A') + "        ").append(dateSheetList.get(1).getDate().replace("/", "-") + "       ");
				  
				  shift.append((char)(i + 'A') + " ").append(dateSheetList.get(1).getDate().replace("/", "-") + " ");
				  hallDataList.add(list);
				  
				  // Creates attendance list
				  excelUtil.createAttendanceList(outputList, studentList, batchAndCourse, list, new String(shift),
						  startTime, suspendList, examName, new String(headerName));
				  
				  // If matrix list is not null -- creates intra - room seat allocation
				  if(matrixList != null) {
					  // creates intra - room seat allocation
					  excelUtil.createMatrix(outputList, studentList, batchAndCourse, list, new String(shift),
							  startTime, matrixList);
				  }
				  
				  
				  // creating seating display or seating list
				  excelUtil.createSeatingList(studentList, new String(shift));
				  
			  }
		
		 }catch (Exception e) {
			  logger.error(e.getMessage());
				
		}
		 return null;
	}
}
