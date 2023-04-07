package com.roomallocation.ExamRoomAllocation.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import com.roomallocation.ExamRoomAllocation.vo.AlgoOutputVO;
import com.roomallocation.ExamRoomAllocation.vo.DatesheetVO;
import com.roomallocation.ExamRoomAllocation.vo.HallDataVO;
import com.roomallocation.ExamRoomAllocation.vo.StudentOutputVO;
import com.roomallocation.ExamRoomAllocation.vo.StudentVO;

import io.github.millij.poi.util.Spreadsheet;

@Component
public class GenerateExcelUtil {
	
	HashMap<String, String> seatingMap = new HashMap<String, String>();
	
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
	
	public byte [] createAttendanceList(ArrayList<AlgoOutputVO> outputList, List<StudentVO> studentList, ArrayList<DatesheetVO> dateSheetList,
			HashMap<String, String> batchAndCourse, ArrayList<HallDataVO> list, String shift, String startTime) {
		ArrayList<StudentOutputVO> students = new ArrayList<>();
		for (int i = 0 ; i< studentList.size(); i++) {
			StudentOutputVO student = new StudentOutputVO();
			if(studentList.get(i)!=null ) {
				student.setStudent(studentList.get(i));
				student.setAdded(false);
				students.add(student);
			}
		}
		
		HashMap<String, String> hallMap = new HashMap<>();
		for(int i = 0 ; i < list.size(); i++) {
			hallMap.put(list.get(i).getRoomName(), list.get(i).getGender());
		}
		
		XSSFWorkbook workbook = new XSSFWorkbook();
        for(int i = 0; i < outputList.size(); i++) {
        	
        	for(int j = 0 ; j < outputList.get(i).getValues().size(); j++){// this will loop 6 times
        		int numberOfStudents = Integer.valueOf(outputList.get(i).getValues().get(j+1));
        		if(numberOfStudents != 0) {
        			String batch = batchAndCourse.get(outputList.get(i).getValues().get(j));
        			String roomName = outputList.get(i).getClassRoom();
	        		XSSFSheet spreadsheet = workbook.createSheet(batch + " " + roomName);
	            	// FIRST ROW CREATE WITH STYLES 
	                Row firstRow = spreadsheet.createRow(0);
	                // Create a new font and alter it.
	                Font font = workbook.createFont();
	                font.setFontHeightInPoints((short)16);
	                font.setFontName("Calibri");
	                font.setBold(true);
	                font.setUnderline((byte) 1);
	                // Fonts are set into a style so create a new one to use.
	                CellStyle style = workbook.createCellStyle();
	                style.setFont(font);
	                style.setAlignment(HorizontalAlignment.CENTER);
	                // Create a cell and put a value in it.
	                Cell firsCell = firstRow.createCell(0);
	                firsCell.setCellValue("DEI Faculty Of Engineering");
	                firsCell.setCellStyle(style);
	                
	                // SECOND ROW CREATE WITH STYLES
	                Row secRow = spreadsheet.createRow(1);
	        	    style.setAlignment(HorizontalAlignment.CENTER);
	        	    Cell secCell = secRow.createCell(0);
	        	    StringBuilder secondHeader = new StringBuilder("CT-2-");
	        	    secondHeader.append(shift + "-");
	        	    secondHeader.append(startTime);
	        	    secCell.setCellValue(secondHeader.toString());
	        	    secCell.setCellStyle(style);
	                
	                spreadsheet.addMergedRegion(new CellRangeAddress(
	                        0, //first row (0-based)
	                        0, //last row  (0-based)
	                        0, //first column (0-based)
	                        8  //last column  (0-based)
	                ));
	                spreadsheet.addMergedRegion(new CellRangeAddress(
	                        1, //first row (0-based)
	                        1, //last row  (0-based)
	                        0, //first column (0-based)
	                        8  //last column  (0-based)
	                ));
	//        		System.out.println(batch);
	//        		System.out.println(outputList.get(i).getValues().get(j));
	        		Row headerRow = spreadsheet.createRow(4);
	        		Font headerfont = workbook.createFont();
	        		headerfont.setBold(true);
	        		
	        		headerRow.createCell(0).setCellValue("Class");
	        		headerRow.createCell(1).setCellValue("Room");
	        		headerRow.createCell(2).setCellValue("S No.");
	        		headerRow.createCell(3).setCellValue("Roll No.");
	        		headerRow.createCell(4).setCellValue("Student Name");
	        		headerRow.createCell(5).setCellValue("Gender");
	        		headerRow.createCell(6).setCellValue("Signature");
	        		headerRow.createCell(7).setCellValue("B-Copies");
	        		headerRow.createCell(8).setCellValue("Subject");
	        		CellStyle cellStyle = workbook.createCellStyle();
	        		cellStyle.setFont(headerfont);
	        		cellStyle.setBorderTop(BorderStyle.THIN);
	        		cellStyle.setBorderRight(BorderStyle.THIN);
	        		cellStyle.setBorderBottom(BorderStyle.THIN);
	        		cellStyle.setBorderLeft(BorderStyle.THIN);
	        		for(int l = 0; l<=8; l++)
	        			headerRow.getCell(l).setCellStyle(cellStyle);
	        		
	        		
	        		Font cellFonts = workbook.createFont();
	        		CellStyle otherCellStyle = workbook.createCellStyle();
	        		otherCellStyle.setFont(cellFonts);
	        		otherCellStyle.setBorderTop(BorderStyle.THIN);
	        		otherCellStyle.setBorderRight(BorderStyle.THIN);
	        		otherCellStyle.setBorderBottom(BorderStyle.THIN);
	        		otherCellStyle.setBorderLeft(BorderStyle.THIN);
	        		
	        		ArrayList<String> names = new ArrayList<>();
	        		ArrayList<String> rollNo = new ArrayList<>();
	        		String genderAllowed = hallMap.get(outputList.get(i).getClassRoom());
        			for(int studentCounter = 0, l=0 ;  studentCounter<students.size(); studentCounter++) {
        				if(!students.get(studentCounter).isAdded() && l<numberOfStudents &&
        						students.get(studentCounter).getStudent().getCourses().contains(outputList.get(i).getValues().get(j))) {
        					if(students.get(studentCounter).getStudent().getGender().equals(genderAllowed)) {
        						names.add(students.get(studentCounter).getStudent().getName());
        						rollNo.add(students.get(studentCounter).getStudent().getRollNumber());
        						students.get(studentCounter).setAdded(true);
        						l++;
        						
        					}
        				}
        			}
        			
        			Row dataRow = null;
	        		for (int rowCounter = 0 ; rowCounter< names.size(); rowCounter++) {
	        			dataRow = spreadsheet.createRow(rowCounter+5);
	        			dataRow.createCell(0).setCellValue(String.valueOf(batch));
	        			dataRow.createCell(1).setCellValue(outputList.get(i).getClassRoom());
	        			dataRow.createCell(2).setCellValue(rowCounter + 1);
	        			dataRow.createCell(3).setCellValue(names.get(rowCounter));
	        			dataRow.createCell(4).setCellValue(rollNo.get(rowCounter));
	        			seatingMap.put( rollNo.get(rowCounter), outputList.get(i).getClassRoom());
	        			dataRow.createCell(5).setCellValue(genderAllowed);
	        			dataRow.createCell(6).setCellValue("");
	        			dataRow.createCell(7).setCellValue("");
	        			dataRow.createCell(8).setCellValue(outputList.get(i).getValues().get(j));
	        			for(int l = 0 ; l<=8 ; l++) {
	        				dataRow.getCell(l).setCellStyle(otherCellStyle);
	        			}
	        			
	        			
	        		}
        			
	        		Row totalRow = spreadsheet.createRow(names.size() + 7);
	        		totalRow.createCell(0).setCellValue("Total");
	        		totalRow.createCell(7).setCellValue("Invigilator's Sign");
	        		spreadsheet.addMergedRegion(new CellRangeAddress(names.size() + 7,names.size() + 7,0,1));
	        		spreadsheet.addMergedRegion(new CellRangeAddress(names.size() + 7,names.size() + 7,7,8));
	        		Row presentRow = spreadsheet.createRow(names.size() + 8);
	        		presentRow.createCell(0).setCellValue("Present");
	        		spreadsheet.addMergedRegion(new CellRangeAddress(names.size() + 8,names.size() + 8,0,1));
	        		
	        		Row absentRow = spreadsheet.createRow(names.size() + 9);
	        		absentRow.createCell(0).setCellValue("Absent");
	        		spreadsheet.addMergedRegion(new CellRangeAddress(names.size() + 9,names.size() + 9,0,1));
	        		
        			
        		}
        		j++;
        		
        		
        	
	        	
            
        	}
        }
        
        // Write the output to a file
        FileOutputStream out;
		try {
			out = new FileOutputStream( new File("C:\\Users\\This pc\\Downloads\\" + shift + " Attendance plan.xlsx"));
			 
			workbook.write(out);
	        out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
		return null;
        
		
	}
	
	public byte [] createSeatingList(List<StudentVO> studentList, String shift) {
		XSSFWorkbook workbook = new XSSFWorkbook();
		
		XSSFSheet spreadsheet = workbook.createSheet();
		Row headerRow = spreadsheet.createRow(0);
		headerRow.createCell(0).setCellValue("Roll Number");
		headerRow.createCell(1).setCellValue("Room Name");
		Row dataRow = null;
		for(int i = 1;i < studentList.size(); i++) {
			dataRow = spreadsheet.createRow(i+1);
			dataRow.createCell(0).setCellValue(studentList.get(i).getRollNumber());
			dataRow.createCell(1).setCellValue(seatingMap.get(studentList.get(i).getRollNumber()));
		}
		
		 FileOutputStream out;
			try {
				out = new FileOutputStream( new File("C:\\Users\\This pc\\Downloads\\" + shift + "-Seating List.xlsx"));
				 
				workbook.write(out);
		        out.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	        
			return null;
		
	}

}
