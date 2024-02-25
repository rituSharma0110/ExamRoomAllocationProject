package com.roomallocation.ExamRoomAllocation.controller;

import static com.roomallocation.ExamRoomAllocation.constants.ExamRoomAllocationConstants.BASE_URL;
import static com.roomallocation.ExamRoomAllocation.constants.ExamRoomAllocationConstants.HEALTHCHECK_URL;
import static com.roomallocation.ExamRoomAllocation.constants.ExamRoomAllocationConstants.GENERATE_SEATING_ARRANGEMENT;
import static com.roomallocation.ExamRoomAllocation.constants.ExamRoomAllocationConstants.GENERATE_COUNT_FILE;
import static com.roomallocation.ExamRoomAllocation.constants.ExamRoomAllocationConstants.GENERATE_OTHER_FILES;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.Controller;

import com.roomallocation.ExamRoomAllocation.service.ExamRoomAllocationService;

import ch.qos.logback.classic.Logger;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = BASE_URL, tags = { "This is the controller for the Exam Room Allocation Service" })
@RestController
@RequestMapping(BASE_URL)
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ExamRoomAllocationController {
	
	private static final Logger logger = (Logger) LoggerFactory.getLogger(ExamRoomAllocationController.class);
	

	@Autowired
	private ExamRoomAllocationService examRoomAllocationService;
	                                           
	// Basic Health check 
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping(value = HEALTHCHECK_URL, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Health Check", notes = "This is the health check service.")
	public ResponseEntity<String> healthCheck() {
		final String methodName = "healthCheck()";
		logger.info("{}: Checking Health of Service", methodName);
		String healthCheck = new String();
		try {
			healthCheck = examRoomAllocationService.healthCheck();
		}catch(Exception e)
		{
			return new ResponseEntity<String>(healthCheck,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>(healthCheck,HttpStatus.OK);
	}
	
	/*
	 * This is for generating seating arrangement plan
	 */
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(value = GENERATE_SEATING_ARRANGEMENT)
	@ApiOperation(value = "Generate Seating Arrangement pdf", notes = "This is for generating excel/pdf of seating arrangement")
	public ResponseEntity<String> generateSeatingArrangement(
			@RequestParam("file") MultipartFile [] files,
			@RequestParam("dateSheetFile") MultipartFile dateSheetFile,
			@RequestParam("hallFile") MultipartFile hallFile,
			@RequestParam(value = "batchMapFile", required=false) MultipartFile batchMapFile){
		final String methodName = "generateSeatingArrangement()";
		logger.info("{} : Generate Seating Arrangement ",  methodName);
		String response = new String();
		try {
			
			response = examRoomAllocationService.generateSeatingArrangement(files, dateSheetFile, hallFile, 
					batchMapFile);
		} catch (Exception e) {
			return new ResponseEntity<String>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>(response,HttpStatus.OK);
	}
	
	/*
	 * This is for generating count of male and female students for each master registration file
	 */
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(value = GENERATE_COUNT_FILE)
	@ApiOperation(value = "Generate Student Counts Excel", notes = "This is for generating excel/pdf of seating arrangement")
	public ResponseEntity<String> generateCountFile(
			@RequestParam("file") MultipartFile [] files,
			@RequestParam(value = "suspendFile", required=false) MultipartFile suspendedStuFile){
		final String methodName = "generateCountFile()";
		logger.info("{} : Generate Student Counts Excel ",  methodName);
		String response = new String();
		try {
			
			response = examRoomAllocationService.generateCountFile(files, suspendedStuFile);
		} catch (Exception e) {
			return new ResponseEntity<String>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>(response,HttpStatus.OK);
	}
	
	/*
	 * This is for generating other files such as attendance sheets, seat matrix and seating display 
	 */
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(value = GENERATE_OTHER_FILES)
	@ApiOperation(value = "Generate files such as attendance sheets, seat matrix and seating display ", notes = "This is for generating excel/pdf of seating arrangement")
	public ResponseEntity<String> generateOtherFiles(
			@RequestParam(value = "outputFile", required=false) MultipartFile outputFile,
			@RequestParam("file") MultipartFile [] files,
			@RequestParam("dateSheetFile") MultipartFile dateSheetFile,
			@RequestParam("hallFile") MultipartFile hallFile,
			@RequestParam(value = "suspendFile", required=false) MultipartFile suspendedStuFile,
			@RequestParam(value = "matrixFile", required=false) MultipartFile matrixFile,
			@RequestParam(value = "batchMapFile", required=false) MultipartFile batchMapFile
			){
		final String methodName = "generategenerateOtherFilesCountFile()";
		logger.info("{} : Generate Student Counts Excel ",  methodName);
		String response = new String();
		try {
			
			response = examRoomAllocationService.generateOtherFiles(files, dateSheetFile, hallFile, matrixFile, suspendedStuFile,
					batchMapFile, outputFile);
		} catch (Exception e) {
			return new ResponseEntity<String>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>(response,HttpStatus.OK);
	}
	
	@Controller
	public class WebController {
	   @RequestMapping(value = "/index")
	   public String index() {
	      return "index";
	   }
	}
	
}