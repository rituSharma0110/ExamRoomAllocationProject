package com.roomallocation.ExamRoomAllocation.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.roomallocation.ExamRoomAllocation.vo.AlgoOutputVO;
import com.roomallocation.ExamRoomAllocation.vo.DatesheetVO;
import com.roomallocation.ExamRoomAllocation.vo.HallDataVO;
import com.roomallocation.ExamRoomAllocation.vo.RowColVO;
import com.roomallocation.ExamRoomAllocation.vo.StudentOutputVO;
import com.roomallocation.ExamRoomAllocation.vo.StudentVO;
import com.roomallocation.ExamRoomAllocation.vo.SuspendedStuVO;


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
        
        for(int i = 0; i < studentList.size(); i++) {
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
			String endTime, String examDate, List<StudentVO> studentList, ArrayList<HallDataVO> list) throws JsonProcessingException {
		
		HashMap<String, String> controlContext = new HashMap<>();
		for(int i = 0 ; i < list.size(); i++) {
			controlContext.put(list.get(i).getRoomName(), list.get(i).getControlContext());
		}
        
        XSSFSheet sheet = seatingChart.createSheet(shift);
		
        Row headingRow = sheet.createRow(0);
        Font headerFont = seatingChart.createFont();
        headerFont.setFontHeightInPoints((short)16);
        headerFont.setFontName("Calibri");
        headerFont.setBold(true);
        headerFont.setUnderline((byte) 1);
        // Fonts are set into a style so create a new one to use.
        CellStyle firstCellStyle = seatingChart.createCellStyle();
        firstCellStyle.setFont(headerFont);
        firstCellStyle.setAlignment(HorizontalAlignment.CENTER);
        // Create a cell and put a value in it.
        Cell firsCell = headingRow.createCell(0);
        firsCell.setCellValue("DEI Faculty Of Engineering");
        firsCell.setCellStyle(firstCellStyle);
        
		Row firstHeaderRow = sheet.createRow(1);
		Row secHeaderRow = sheet.createRow(2);
		Row thirdHeaderRow = sheet.createRow(3);
		Row fourthHeaderRow = sheet.createRow(4);
		Row fiftHeaderRow = sheet.createRow(5);
		
		Font font = seatingChart.createFont();
        font.setFontName("Calibri");
        font.setBold(true);
        // Fonts are set into a style so create a new one to use.
        CellStyle style = seatingChart.createCellStyle();
        style.setFont(font);
        style.setBorderRight(CellStyle.BORDER_MEDIUM);
        sheet.addMergedRegion(new CellRangeAddress(
                0, //first row (0-based)
                0, //last row  (0-based)
                0, //first column (0-based)
                batch.size()+2  //last column  (0-based)
        ));
        
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
		
		for (int i  = 1 ; i < 6 ; i++) {
			Row headers = sheet.getRow(i);
			for (int j = 0 ; j < 2; j++) {
				headers.getCell(j).setCellStyle(style);
			}
		}
		
		CellStyle otherStyle = seatingChart.createCellStyle();
        otherStyle.setBorderBottom(CellStyle.BORDER_MEDIUM);
		
		int j = 2;
        for (int i = 0; i < batch.size() ; i++,j++) {
			Cell cell = firstHeaderRow.createCell(j);
			cell.setCellValue(batch.get(i));
//			cell.setCellStyle(otherStyle);
        }
        Cell totalCell = firstHeaderRow.createCell(batch.size()+2);
        totalCell.setCellValue("TOTAL");
        totalCell.setCellStyle(style);
        j=2;
        for (int i = 0 ; i < batchSub.size(); i++, j++) {
        	Cell cell = secHeaderRow.createCell(j);
			cell.setCellValue(batchSub.get(i));
			cell.setCellStyle(otherStyle);
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
        secHeaderRow.createCell(batchSub.size() + 2).setCellStyle(otherStyle);;
        
        j = 2;
        int l = 6;
        HashSet <String> controlAdded = new HashSet<>();
        MultiValueMap<String, List<String>>  valueMap = outputMap;
        for (int k = 0 ; k< valueMap.size(); k++, l++) {
        int total = 0;
    	Row dataRow = sheet.createRow(l);
    	if(!controlAdded.contains(controlContext.get(valueMap.keySet().toArray()[k].toString()))) {
    		controlAdded.add(controlContext.get(valueMap.keySet().toArray()[k].toString()));
    		sheet.addMergedRegion(new CellRangeAddress(
                    l, //first row (0-based)
                    l, //last row  (0-based)
                    0, //first column (0-based)
                    batch.size()+2  //last column  (0-based)
            ));
    		dataRow.createCell(0).setCellValue(controlContext.get(valueMap.keySet().toArray()[k].toString()));
            CellStyle controlStyle = seatingChart.createCellStyle();
    		controlStyle.setAlignment(HorizontalAlignment.CENTER);
    		controlStyle.setFont(font);
    		dataRow.getCell(0).setCellStyle(controlStyle);
    		k--;
    		continue;
    	}
    	dataRow.createCell(1).setCellValue(valueMap.keySet().toArray()[k].toString());
        	for (int i = 0 ; i < batchSub.size(); i++) {
        		if( valueMap.values().toArray()[k].toString().contains(batchSub.get(i))) {
        			int ind = valueMap.values().toArray()[k].toString().indexOf(batchSub.get(i));
        			String valString = valueMap.values().toArray()[k].toString().substring(ind+8, ind+10);
        			valString = valString.replace(",","");
        			valString = valString.replace("]", "");
        			int val = Integer.valueOf(valString);
        			total += val;
        			if(val!=0) {
        				dataRow.createCell(2+i).setCellValue(val);
        			}
        		}
	        }
        	dataRow.createCell(2+batch.size()).setCellValue(total);
        }
        CellRangeAddress regionOne = new CellRangeAddress(1, l, 0, 2+batch.size());
        CellRangeAddress regionTwo = new CellRangeAddress(6, l, 0, 2+batch.size());
        RegionUtil.setBorderBottom(CellStyle.BORDER_MEDIUM, regionOne, sheet, seatingChart);
        RegionUtil.setBorderLeft(CellStyle.BORDER_MEDIUM, regionOne, sheet, seatingChart);
        RegionUtil.setBorderRight(CellStyle.BORDER_MEDIUM, regionOne, sheet, seatingChart);
        RegionUtil.setBorderTop(CellStyle.BORDER_MEDIUM, regionOne, sheet, seatingChart);
        RegionUtil.setBorderBottom(CellStyle.BORDER_MEDIUM, regionTwo, sheet, seatingChart);
        RegionUtil.setBorderLeft(CellStyle.BORDER_MEDIUM, regionTwo, sheet, seatingChart);
        RegionUtil.setBorderRight(CellStyle.BORDER_MEDIUM, regionTwo, sheet, seatingChart);
        RegionUtil.setBorderTop(CellStyle.BORDER_MEDIUM, regionTwo, sheet, seatingChart);
       
       
        FileOutputStream out;
		try {
			out = new FileOutputStream( new File("C:\\Users\\This pc\\Downloads\\Seating plan.xlsx"));
			 
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
			HashMap<String, String> batchAndCourse, ArrayList<HallDataVO> list, String shift, String startTime, List<SuspendedStuVO> suspendList) {
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
		HashMap<String, String> controlContext = new HashMap<>();
		for(int i = 0 ; i < list.size(); i++) {
			hallMap.put(list.get(i).getRoomName(), list.get(i).getGender());
			controlContext.put(list.get(i).getRoomName(), list.get(i).getControlContext());
		}
		
		ArrayList<String> suspendedRollNo = new ArrayList<>();
		if(suspendList!=null) {
			for(int i = 0 ; i < suspendList.size(); i++) {
				suspendedRollNo.add(suspendList.get(i).getRollNo());
			}
			
		}
		
		
		XSSFWorkbook workbook = new XSSFWorkbook();
		int numberOfSheets = 1;
        for(int i = 0; i < outputList.size(); i++) {
        	
        	for(int j = 0 ; j < outputList.get(i).getValues().size(); j++){// this will loop 6 times
        		int numberOfStudents = Integer.valueOf(outputList.get(i).getValues().get(j+1));
        		if(numberOfStudents != 0) {
        			String batch = batchAndCourse.get(outputList.get(i).getValues().get(j));
        			String roomName = outputList.get(i).getClassRoom();
        			
        			if(batch.endsWith("BT")) {
//        				System.out.println("Inside BT");
        				ArrayList<String> names = new ArrayList<>();
    	        		ArrayList<String> rollNo = new ArrayList<>();
    	        		ArrayList<String> branch = new ArrayList<>();
    	        		String genderAllowed = hallMap.get(outputList.get(i).getClassRoom());
            			for(int studentCounter = 0, l=0 ;  studentCounter<students.size(); studentCounter++) {
            				if(!students.get(studentCounter).isAdded() && l<numberOfStudents &&
            						students.get(studentCounter).getStudent().getCourses().contains(outputList.get(i).getValues().get(j))) {
            					if(students.get(studentCounter).getStudent().getGender().equals(genderAllowed)) {
            						names.add(students.get(studentCounter).getStudent().getName());
            						rollNo.add(students.get(studentCounter).getStudent().getRollNumber());
            						branch.add(students.get(studentCounter).getStudent().getBranch());
//            						System.out.println(students.get(studentCounter).getStudent().getBranch());
            						students.get(studentCounter).setAdded(true);
            						l++;
            						
            					}
            				}
            			}
            			
            			// OPTIMIZE FURTHER USE ARRAYLIST<ARRAYLIST> OR OBJECT
            			ArrayList<String> mechanical = new ArrayList<String>();
            			ArrayList<String> electrical = new ArrayList<String>();
            			ArrayList<String> footwear = new ArrayList<String>();
            			ArrayList<String> civil = new ArrayList<String>();
            			ArrayList<String> agriculture = new ArrayList<String>();
            			
            			ArrayList<String> mechRollNo = new ArrayList<String>();
            			ArrayList<String> elRollNo = new ArrayList<String>();
            			ArrayList<String> fwRollNo = new ArrayList<String>();
            			ArrayList<String> clRollNo = new ArrayList<String>();
            			ArrayList<String> agRollNo = new ArrayList<String>();
            			for(int k = 0; k < branch.size(); k++) {
            				if(branch.get(k).substring(0,1).equalsIgnoreCase("M")) {
            					mechanical.add(names.get(k));
            					mechRollNo.add(rollNo.get(k));
            				}else if(branch.get(k).substring(0,1).equalsIgnoreCase("E")) {
            					electrical.add(names.get(k));
            					elRollNo.add(rollNo.get(k));
            				}else if(branch.get(k).substring(0,1).equalsIgnoreCase("C")) {
            					civil.add(names.get(k));
            					clRollNo.add(rollNo.get(k));
            				}else if(branch.get(k).substring(0,1).equalsIgnoreCase("F")) {
            					footwear.add(names.get(k));
            					fwRollNo.add(rollNo.get(k));
            				}else if(branch.get(k).substring(0,1).equalsIgnoreCase("A")) {
            					agriculture.add(names.get(k));
            					agRollNo.add(rollNo.get(k));
            				}
            			}
            			
//            			System.out.println(mechanical.size());
            			if(mechanical.size()!=0) {
            				XSSFSheet spreadsheet = workbook.createSheet(controlContext.get(roomName) + " " + numberOfSheets);
            				numberOfSheets++;
            				addStyleToSheet(spreadsheet, workbook, shift, startTime);
            				createSeparateSheet(workbook, spreadsheet, mechanical, batch, outputList, i, mechRollNo, genderAllowed, j, suspendedRollNo );
            			}
            			if(electrical.size()!=0) {
            				XSSFSheet spreadsheet = workbook.createSheet(controlContext.get(roomName) + " " + numberOfSheets);
            				numberOfSheets++;
            				addStyleToSheet(spreadsheet, workbook, shift, startTime);
            				createSeparateSheet(workbook, spreadsheet, electrical, batch, outputList, i, elRollNo, genderAllowed, j, suspendedRollNo );
            			}
            			if(civil.size()!=0) {
            				XSSFSheet spreadsheet = workbook.createSheet(controlContext.get(roomName) + " " + numberOfSheets);
            				numberOfSheets++;
            				addStyleToSheet(spreadsheet, workbook, shift, startTime);
            				createSeparateSheet(workbook, spreadsheet, civil, batch, outputList, i, clRollNo, genderAllowed, j, suspendedRollNo );
            			}
            			if(footwear.size()!=0) {
            				XSSFSheet spreadsheet = workbook.createSheet(controlContext.get(roomName) + " " + numberOfSheets);
            				numberOfSheets++;
            				addStyleToSheet(spreadsheet, workbook, shift, startTime);
            				createSeparateSheet(workbook, spreadsheet, footwear, batch, outputList, i, fwRollNo, genderAllowed, j, suspendedRollNo );
            			}
            			if(agriculture.size()!=0) {
            				XSSFSheet spreadsheet = workbook.createSheet(controlContext.get(roomName) + " " + numberOfSheets);
            				numberOfSheets++;
            				addStyleToSheet(spreadsheet, workbook, shift, startTime);
            				createSeparateSheet(workbook, spreadsheet, agriculture, batch, outputList, i, agRollNo, genderAllowed, j, suspendedRollNo );
            			}
//            			XSSFSheet spreadsheet = workbook.createSheet(batch + " " + roomName);
        			}else {
        				
            			XSSFSheet spreadsheet = workbook.createSheet(controlContext.get(roomName) + " " + numberOfSheets);
            			numberOfSheets++;
            			// FIRST ROW CREATE WITH STYLES 
            			addStyleToSheet(spreadsheet, workbook, shift, startTime);
            			
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
            			
            			// this is to set style for Sno. col
            			CellStyle snoCellStyle = workbook.createCellStyle();
            			snoCellStyle.setAlignment(HorizontalAlignment.CENTER);
            			snoCellStyle.setFont(cellFonts);
            			snoCellStyle.setBorderTop(BorderStyle.THIN);
            			snoCellStyle.setBorderRight(BorderStyle.THIN);
            			snoCellStyle.setBorderBottom(BorderStyle.THIN);
            			snoCellStyle.setBorderLeft(BorderStyle.THIN);
            			
            			CellStyle style = workbook.createCellStyle();
            			 style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
            		     style.setFillPattern(CellStyle.SOLID_FOREGROUND);
            			// filling data
            			Row dataRow = null;
            			for (int rowCounter = 0 ; rowCounter< names.size(); rowCounter++) {
            				
            				dataRow = spreadsheet.createRow(rowCounter+5);
            				dataRow.createCell(0).setCellValue(String.valueOf(batch));
            				dataRow.createCell(1).setCellValue(outputList.get(i).getClassRoom());
            				dataRow.createCell(2).setCellValue(rowCounter + 1);
            				dataRow.createCell(4).setCellValue(names.get(rowCounter));
            				dataRow.createCell(3).setCellValue(rollNo.get(rowCounter));
            				seatingMap.put( rollNo.get(rowCounter), outputList.get(i).getClassRoom());
            				dataRow.createCell(5).setCellValue(genderAllowed);
            				dataRow.createCell(6).setCellValue("");
            				dataRow.createCell(7).setCellValue("");
            				dataRow.createCell(8).setCellValue(outputList.get(i).getValues().get(j));
            				for(int l = 0 ; l<=8 ; l++) {
            					dataRow.getCell(l).setCellStyle(otherCellStyle);
            				}
            				dataRow.getCell(2).setCellStyle(snoCellStyle);
            				if(suspendedRollNo.size()!=0 && suspendedRollNo.contains(rollNo.get(rowCounter))) {
            					for(int l = 0 ; l<=8 ; l++) {
                					dataRow.getCell(l).setCellStyle(style);
                				}
            				}
            				
            			}
            			spreadsheet.autoSizeColumn(1);
            			spreadsheet.autoSizeColumn(2);
            			spreadsheet.setColumnWidth(4, 5000);
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
		int j = 1;
		for(int i = 1;i < studentList.size(); i++) {
			if(seatingMap.containsKey(studentList.get(i).getRollNumber())) {
				dataRow = spreadsheet.createRow(j+1);
				j++;
				dataRow.createCell(0).setCellValue(studentList.get(i).getRollNumber());
				dataRow.createCell(1).setCellValue(seatingMap.get(studentList.get(i).getRollNumber()));
			}
		}
		seatingMap.clear();
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
	
	public void addStyleToSheet(XSSFSheet spreadsheet, XSSFWorkbook workbook, String shift, String startTime) {
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
	}
	
	public void createSeparateSheet(XSSFWorkbook workbook, XSSFSheet spreadsheet, ArrayList<String> names, String batch,
			ArrayList<AlgoOutputVO> outputList, int i, ArrayList<String> rollNo, String genderAllowed, int j, ArrayList<String> suspendedRollNo) {
		
		Font cellFonts = workbook.createFont();
		CellStyle otherCellStyle = workbook.createCellStyle();
		otherCellStyle.setFont(cellFonts);
		otherCellStyle.setBorderTop(BorderStyle.THIN);
		otherCellStyle.setBorderRight(BorderStyle.THIN);
		otherCellStyle.setBorderBottom(BorderStyle.THIN);
		otherCellStyle.setBorderLeft(BorderStyle.THIN);
		
		// this is to set style for Sno. col
		CellStyle snoCellStyle = workbook.createCellStyle();
		snoCellStyle.setAlignment(HorizontalAlignment.CENTER);
		snoCellStyle.setFont(cellFonts);
		snoCellStyle.setBorderTop(BorderStyle.THIN);
		snoCellStyle.setBorderRight(BorderStyle.THIN);
		snoCellStyle.setBorderBottom(BorderStyle.THIN);
		snoCellStyle.setBorderLeft(BorderStyle.THIN);
		
		CellStyle style = workbook.createCellStyle();
		 style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
	     style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		Row dataRow = null;
		for (int rowCounter = 0 ; rowCounter< names.size(); rowCounter++) {
			dataRow = spreadsheet.createRow(rowCounter+5);
			dataRow.createCell(0).setCellValue(String.valueOf(batch));
			dataRow.createCell(1).setCellValue(outputList.get(i).getClassRoom());
			dataRow.createCell(2).setCellValue(rowCounter + 1);
			dataRow.createCell(4).setCellValue(names.get(rowCounter));
			dataRow.createCell(3).setCellValue(rollNo.get(rowCounter));
			seatingMap.put( rollNo.get(rowCounter), outputList.get(i).getClassRoom());
			dataRow.createCell(5).setCellValue(genderAllowed);
			dataRow.createCell(6).setCellValue("");
			dataRow.createCell(7).setCellValue("");
			dataRow.createCell(8).setCellValue(outputList.get(i).getValues().get(j));
			for(int l = 0 ; l<=8 ; l++) {
				dataRow.getCell(l).setCellStyle(otherCellStyle);
			}
			dataRow.getCell(2).setCellStyle(snoCellStyle);
			if(suspendedRollNo.size()!=0 && suspendedRollNo.contains(rollNo.get(rowCounter))) {
				for(int l = 0 ; l<=8 ; l++) {
					dataRow.getCell(l).setCellStyle(style);
				}
			}
			
		}
		spreadsheet.autoSizeColumn(1);
		spreadsheet.autoSizeColumn(2);
		spreadsheet.setColumnWidth(4, 5000);
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

	public void createMatrix(ArrayList<AlgoOutputVO> outputList, List<StudentVO> studentList,
			ArrayList<DatesheetVO> dateSheetList, HashMap<String, String> batchAndCourse, ArrayList<HallDataVO> list,
			String shift, String startTime, List<RowColVO> matrixList) {
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
		HashMap<String, String> controlContext = new HashMap<>();
		for(int i = 0 ; i < list.size(); i++) {
			hallMap.put(list.get(i).getRoomName(), list.get(i).getGender());
			controlContext.put(list.get(i).getRoomName(), list.get(i).getControlContext());
		}
		XSSFWorkbook workbook = new XSSFWorkbook();
        for(int i = 0; i < outputList.size(); i++) {
        	
        	String roomName = outputList.get(i).getClassRoom();
        	
        	int ind = -1;
			for(int l = 0; l< matrixList.size(); l++ ) {
				if(matrixList.get(l).getRoomName().equals(roomName)) {
					ind = l;
					break;
				}
			}
			int rows = Integer.valueOf(matrixList.get(ind).getRows());
			int cols = Integer.valueOf(matrixList.get(ind).getCols());
				
			XSSFSheet spreadsheet = workbook.createSheet(controlContext.get(roomName) + " - "  + roomName);
			
			Row headerRow = spreadsheet.createRow(0);
			Font headerfont = workbook.createFont();
			headerfont.setBold(true);
			Row dataRow = null;
			
			HashMap<Integer, String> names = new HashMap<>();
			HashMap<Integer, String> rollNo = new HashMap<>();
			HashMap<Integer, String> subjectCode = new HashMap<>();
			int numberOfStudents = 0;
			int totalSub = 0;
        	for(int j = 0 ; j < outputList.get(i).getValues().size(); j++){// this will loop 6 times
        		numberOfStudents = Integer.valueOf(outputList.get(i).getValues().get(j+1));
        		if(numberOfStudents != 0) {
        			int index = 0;
        			
        			String genderAllowed = hallMap.get(outputList.get(i).getClassRoom());
        			for(int studentCounter = 0, l=0 ;  studentCounter<students.size(); studentCounter++) {
        				if(!students.get(studentCounter).isAdded() && l<numberOfStudents &&
        						students.get(studentCounter).getStudent().getCourses().contains(outputList.get(i).getValues().get(j))) {
        					if(students.get(studentCounter).getStudent().getGender().equals(genderAllowed)) {
        						names.put(index+totalSub, students.get(studentCounter).getStudent().getName());
        						rollNo.put(index+totalSub, students.get(studentCounter).getStudent().getRollNumber());
        						subjectCode.put(index+totalSub, outputList.get(i).getValues().get(j));
        						students.get(studentCounter).setAdded(true);
        						l++;
        						index+=3;
        						
        					}
        				}
        			}
        			
        		}
        		j++;
        		totalSub++;
            			
        	}
        	CellStyle style = workbook.createCellStyle();
        	style.setWrapText(true);
        	style.setBorderTop(BorderStyle.THIN);
        	style.setBorderRight(BorderStyle.THIN);
        	style.setBorderBottom(BorderStyle.THIN);
        	style.setBorderLeft(BorderStyle.THIN);
        	for(int l = 0 ;l< cols; l++) {
        		CellStyle cs = workbook.createCellStyle();
        		cs.setFont(headerfont);
				headerRow.createCell(2*l+2).setCellValue("Column-" + (l+1));
				headerRow.getCell(2*l+2).setCellStyle(cs);
				spreadsheet.setColumnWidth(2*l+2, 5000);
			}
        	
			for(int l = 1, counter = 0;l<= rows; l++ ) {
				dataRow = spreadsheet.createRow(l);
				for(int k=0 ;k< cols; k++ , counter++) {
					if(counter<rows*cols) {
						if( rollNo.get(counter)==null) {
							dataRow.createCell(2*k+2).setCellValue("-");
							dataRow.getCell(2*k+2).setCellStyle(style);
						}else {
							dataRow.createCell(2*k+2).setCellValue(rollNo.get(counter) + " " + subjectCode.get(counter));
							dataRow.getCell(2*k+2).setCellStyle(style);
							
						}
					}
				}
			}
        	
            
        }
        
        // Write the output to a file
        FileOutputStream out;
		try {
			out = new FileOutputStream( new File("C:\\Users\\This pc\\Downloads\\" + shift + " Matrix plan.xlsx"));
			 
			workbook.write(out);
	        out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
	}

}
