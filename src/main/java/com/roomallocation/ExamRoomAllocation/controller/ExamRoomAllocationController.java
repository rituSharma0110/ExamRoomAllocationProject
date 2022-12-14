package com.roomallocation.ExamRoomAllocation.controller;

import static com.roomallocation.ExamRoomAllocation.constants.ExamRoomAllocationConstants.BASE_URL;
import static com.roomallocation.ExamRoomAllocation.constants.ExamRoomAllocationConstants.HEALTHCHECK_URL;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	
	// Basic Health check to be added

	@Autowired
	private ExamRoomAllocationService examRoomAllocationService;
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping(value = HEALTHCHECK_URL, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Health Check", notes = "This is the health check service.")
	public ResponseEntity<String> healthCheck() {
		final String methodName = "healthCheck()";
		logger.info("{}: Checking Health of Shop Service", methodName);
		String healthCheck = new String();
		try {
			healthCheck = examRoomAllocationService.healthCheck();
		}catch(Exception e)
		{
			return new ResponseEntity<String>(healthCheck,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>(healthCheck,HttpStatus.OK);
	}

}
