package com.roomallocation.ExamRoomAllocation.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import com.roomallocation.ExamRoomAllocation.vo.DatesheetVO;
import com.roomallocation.ExamRoomAllocation.vo.HallDataVO;
import com.roomallocation.ExamRoomAllocation.vo.StudentVO;

@Component
public class GenerateAlgo {
	
	public void generateAlgo(ArrayList<DatesheetVO> dateSheetList, ArrayList<HallDataVO> hallDataList, 
			List<StudentVO> studentList) {
		ArrayList<Integer> listOfStudents = new ArrayList<>();
		ArrayList<Integer> hallCapacity = new ArrayList<>();
		for(int i = 0; i < dateSheetList.size(); i++) {
			int count = 0;
			for(int j = 0; j<studentList.get(i).getCourses().size(); j++) {
				if(studentList.get(i).getCourses().get(j).equals(dateSheetList.get(i).getSubjectCode())) {
					count++;
				}
			
			}
			listOfStudents.add(count);
		}
		
		for(int i = 0; i < hallDataList.size(); i++) {
			int val = Integer.valueOf(hallDataList.get(i).getCapacity());
			hallCapacity.add(val);
			}
		
		while(hallDataList.size()!=0) {
			Collections.sort(listOfStudents,  Collections.reverseOrder());
			Collections.sort(hallCapacity,  Collections.reverseOrder());
		}
		
	}

}
