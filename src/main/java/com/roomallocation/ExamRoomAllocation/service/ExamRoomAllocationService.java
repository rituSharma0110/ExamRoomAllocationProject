package com.roomallocation.ExamRoomAllocation.service;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.stereotype.Service;

import com.roomallocation.ExamRoomAllocation.vo.StudentVO;

import io.github.millij.poi.ss.reader.XlsReader;

@Service
public class ExamRoomAllocationService {
	
	@Autowired
	BuildProperties buildProperties;
	
	private static final Logger logger = (Logger) LoggerFactory.getLogger(ExamRoomAllocationService.class);

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

	public String generateSeatingArrangement() {
		// TODO Auto-generated method stub
		 final String FILE_NAME = "C:\\Users\\This pc\\Downloads\\B.Tech SM7 1.10.xls";
		 
		 try {
			  FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
			  HSSFWorkbook workbook = new HSSFWorkbook(excelFile);
			  HSSFSheet worksheet = workbook.getSheetAt(0);
			  
			  int rows = worksheet.getLastRowNum();
			  System.out.println(rows);
			  
			  
			  for(int rowConter = 0; rowConter<=rows ; rowConter++) {
				  HSSFRow row = worksheet.getRow(rowConter);
				  int cols = worksheet.getRow(rowConter).getLastCellNum();
				  for(int colConter = 0; colConter<=cols ; colConter++) {
					  HSSFCell cell =  row.getCell(colConter);
					  if(cell==null) {
						  
					  }else {
					  System.out.print(cell.getStringCellValue() + "--");
					  }
					 
	               
					  
					
				  }
			  }
			  final File xlsxFile = new File(FILE_NAME);
			  final XlsReader reader = new XlsReader();
			  List<StudentVO> students = reader.read(StudentVO.class, xlsxFile);
			  
		} catch (Exception e) {
			  logger.error(e.getMessage());
			
		}
		 return null;
	}

}
