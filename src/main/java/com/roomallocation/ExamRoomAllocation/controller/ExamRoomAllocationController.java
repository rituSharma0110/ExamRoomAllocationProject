package com.roomallocation.ExamRoomAllocation.controller;

import static com.roomallocation.ExamRoomAllocation.constants.ExamRoomAllocationConstants.BASE_URL;

import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.qos.logback.classic.Logger;
import io.swagger.annotations.Api;

@Api(value = BASE_URL, tags = { "This is the controller for the Exam Room Allocation Service" })
@RestController
@RequestMapping(BASE_URL)
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ExamRoomAllocationController {
	
	private static final Logger logger = (Logger) LoggerFactory.getLogger(ExamRoomAllocationController.class);
	
	// Basic Health check to be added

	

}
