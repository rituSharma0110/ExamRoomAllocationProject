package com.roomallocation.ExamRoomAllocation.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.roomallocation.ExamRoomAllocation.vo.StudentVO;

@Component
public class GenerateExcelUtil {
	
	public void createSortedExcel(ArrayList courseList, List<StudentVO> studentList, int year) {
		ArrayList<String> heading = new ArrayList<>();
		heading.add("Sl No.");
		heading.add("Student Name");
		heading.add("Roll Number");
		heading.add("Branch");
		heading.add("Specialization");
		heading.add("Year");
		heading.addAll(courseList);
		
		
		XSSFWorkbook workbook = new XSSFWorkbook();
        
        XSSFSheet spreadsheet = workbook.createSheet( "Student details");
        Row headerRow = spreadsheet.createRow(0);
        for (int i = 0; i < heading.size(); i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(heading.get(i));
        }
        
        for(int i = 1; i < studentList.size(); i++) {
        	Row dataRow = spreadsheet.createRow(i + 1);
        	dataRow.createCell(0).setCellValue(String.valueOf((i+1)));
        	dataRow.createCell(1).setCellValue(studentList.get(i).getName());
        	dataRow.createCell(2).setCellValue(studentList.get(i).getRollNumber());
        	dataRow.createCell(3).setCellValue(studentList.get(i).getBranch());
        	dataRow.createCell(4).setCellValue(studentList.get(i).getSpecialization());
        	if(year%2==0) {
        		dataRow.createCell(5).setCellValue(String.valueOf(year/2));
        	}else {
        		dataRow.createCell(5).setCellValue(String.valueOf(year/2+1));
        	}
        	
        	HashSet<String> courseSet = new HashSet<>(studentList.get(i).getCourses());	
        	for(int j = 6 ; j < heading.size();j++) {
        		
	    		if(courseSet.contains(heading.get(j))){
	    			dataRow.createCell(j).setCellValue("Yes");
	    		}else {
	    			dataRow.createCell(j).setCellValue("No");
	    		}
        	}
        } 
        
        
        FileOutputStream out;
		try {
			out = new FileOutputStream( new File("C:\\Users\\saxen\\Downloads\\student_details" + year + ".xlsx"));
			 
	        workbook.write(out);
	        out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

       
        System.out.println("Write to excel sheet done....");
        
	}

}
