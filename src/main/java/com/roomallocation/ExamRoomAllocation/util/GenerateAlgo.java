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
		for(int i = 1; i < dateSheetList.size(); i++) {
			System.out.println(dateSheetList.get(i).getSubjectCode());
			int count = 0;
			for(int j = 1; j<studentList.size(); j++) {
				for(int k = 1; k< studentList.get(j).getCourses().size(); k++)
				if(studentList.get(j).getCourses().get(k).equals(dateSheetList.get(i).getSubjectCode())) {
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
			int r=(hallCapacity.get(0))%3;
			int p=(hallCapacity.get(0))/3;
			if(r==1){
				if(listOfStudents.get(0)>=(p+1)) 
					listOfStudents.set(0,listOfStudents.get(0)-(p+1));
				else
					listOfStudents.set(0,0);
				if(listOfStudents.get(1)>=p)
					listOfStudents.set(1,listOfStudents.get(1)-p);
				else
					listOfStudents.set(1,0);
				if(listOfStudents.get(2)>=p)
					listOfStudents.set(2,listOfStudents.get(2)-p);
				else
					listOfStudents.set(2,0);
			}
			else if(r==2){
				if(listOfStudents.get(0)>=(p+1)) 
					listOfStudents.set(0,listOfStudents.get(0)-(p+1));
				else
					listOfStudents.set(0,0);
				if(listOfStudents.get(1)>=p)
					listOfStudents.set(1,listOfStudents.get(1)-(p+1));
				else
					listOfStudents.set(1,0);
				if(listOfStudents.get(2)>=p)
					listOfStudents.set(2,listOfStudents.get(2)-p);
				else
					listOfStudents.set(2,0);
			}
			else{
				if(listOfStudents.get(0)>=(p+1)) 
					listOfStudents.set(0,listOfStudents.get(0)-p);
				else
					listOfStudents.set(0,0);
				if(listOfStudents.get(1)>=p)
					listOfStudents.set(1,listOfStudents.get(1)-p);
				else
					listOfStudents.set(1,0);
				if(listOfStudents.get(2)>=p)
					listOfStudents.set(2,listOfStudents.get(2)-p);
				else
					listOfStudents.set(2,0);
			}
			for(int i = 0; i < hallCapacity.size()-1; i++) {
				hallCapacity.set(i, hallCapacity.get(i+1));
			}
			hallCapacity.remove(hallCapacity.size()-1);
			System.out.println(hallCapacity.size());
		}
		
	}

}



