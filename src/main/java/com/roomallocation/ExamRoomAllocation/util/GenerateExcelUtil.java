package com.roomallocation.ExamRoomAllocation.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import com.roomallocation.ExamRoomAllocation.vo.DatesheetVO;
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
			out = new FileOutputStream( new File("C:\\Users\\This pc\\Downloads\\student_details" + year + ".xlsx"));
			 
	        workbook.write(out);
	        out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

       
        System.out.println("Write to excel sheet done....");
        
	}
	
	public byte [] seatingChart(MultiValueMap<String, List<String>> outputMap, ArrayList<String> batch, 
			ArrayList<String> batchSub, String shift, XSSFWorkbook seatingChart, String startTime, 
			String endTime, String examDate, List<StudentVO> studentList, MultiValueMap<String, List<String>> outputMap2) {
		
        
        XSSFSheet sheet = seatingChart.createSheet(shift);
		
		Row firstHeaderRow = sheet.createRow(0);
		Row secHeaderRow = sheet.createRow(1);
		Row thirdHeaderRow = sheet.createRow(2);
		Row fourthHeaderRow = sheet.createRow(3);
		Row fiftHeaderRow = sheet.createRow(4);
		firstHeaderRow.createCell(0).setCellValue("CT 2");
		firstHeaderRow.createCell(1).setCellValue("Branch");
		secHeaderRow.createCell(0).setCellValue(examDate);
		secHeaderRow.createCell(1).setCellValue("Course");
		thirdHeaderRow.createCell(0).setCellValue(shift.substring(0, 7));
		thirdHeaderRow.createCell(1).setCellValue("Boys");
		fourthHeaderRow.createCell(0).setCellValue(startTime);
		fourthHeaderRow.createCell(1).setCellValue("Girls");
		fiftHeaderRow.createCell(0).setCellValue(endTime);
		fiftHeaderRow.createCell(1).setCellValue("Total");
		int j = 2;
        for (int i = 0; i < batch.size() ; i++,j++) {
			Cell cell = firstHeaderRow.createCell(j);
			cell.setCellValue(batch.get(i));
        }
        
        j=2;
        for (int i = 0 ; i < batchSub.size(); i++, j++) {
        	Cell cell = secHeaderRow.createCell(j);
			cell.setCellValue(batchSub.get(i));
			int girlsCount = 0;
			int boysCount = 0;
			for(int k = 0; k < studentList.size(); k++) {
	    		if(studentList.get(k).getCourses().contains(batchSub.get(i)) && studentList.get(k).getGender().equalsIgnoreCase("m")){
	    			boysCount++;
	    		}else if(studentList.get(k).getCourses().contains(batchSub.get(i)) && studentList.get(k).getGender().equalsIgnoreCase("f")){
	    			girlsCount++;
	    		}
        	}
			thirdHeaderRow.createCell(j).setCellValue(boysCount);
			fourthHeaderRow.createCell(j).setCellValue(girlsCount);
			fiftHeaderRow.createCell(j).setCellValue(girlsCount + boysCount);
			
        }
        
        j = 2;
        int l = 5;
        MultiValueMap<String, List<String>>  valueMap = outputMap;
        for (int k = 0 ; k< valueMap.size(); k++, l++) {
    	Row dataRow = sheet.createRow(l);
    	dataRow.createCell(1).setCellValue(valueMap.keySet().toArray()[k].toString());
        	for (int i = 0 ; i < batchSub.size(); i++) {
        		if( valueMap.values().toArray()[k].toString().contains(batchSub.get(i))) {
        			int ind = valueMap.values().toArray()[k].toString().indexOf(batchSub.get(i));
        			String valString = valueMap.values().toArray()[k].toString().substring(ind+8, ind+10);
        			valString = valString.replace(",","");
        			valString = valString.replace("]", "");
        			int val = Integer.valueOf(valString);
        			if(val!=0) {
        				dataRow.createCell(2+i).setCellValue(val);
        			}
        		}
	        }
        }
        
       
       
        FileOutputStream out;
		try {
			out = new FileOutputStream( new File("C:\\Users\\This pc\\Downloads\\seating plan.xlsx"));
			 
			seatingChart.write(out);
	        out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

       
        System.out.println("Write to excel sheet done....");
		
		return null;
		
	}
	
//	public void createSheet(XSSFSheet sheet, ArrayList<String> batch) {
//		
//		
//	}

}
