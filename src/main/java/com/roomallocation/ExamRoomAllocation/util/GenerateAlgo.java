package com.roomallocation.ExamRoomAllocation.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.*; 
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.roomallocation.ExamRoomAllocation.vo.DatesheetVO;
import com.roomallocation.ExamRoomAllocation.vo.HallDataVO;
import com.roomallocation.ExamRoomAllocation.vo.StudentVO;

@Component
public class GenerateAlgo {
	
	public void generateAlgo(ArrayList<DatesheetVO> dateSheetList, ArrayList<HallDataVO> hallDataList, 
			List<StudentVO> studentList) {
		HashMap<String, Integer> listOfStudents = new HashMap<>();
		HashMap<String, Integer> hallCapacity = new HashMap<>();
		for(int i = 1; i < dateSheetList.size(); i++) {
			System.out.println(dateSheetList.get(i).getSubjectCode());
			int count = 0;
			for(int j = 1; j<studentList.size(); j++) {
				for(int k = 1; k< studentList.get(j).getCourses().size(); k++)
				if(studentList.get(j).getCourses().get(k).equals(dateSheetList.get(i).getSubjectCode())) {
					count++;
				}
			
					
			}
			listOfStudents.put(dateSheetList.get(i).getSubjectCode(), count);
		}
		
		for(int i = 0; i < hallDataList.size(); i++) {
			int val = 0;
			if(hallDataList.get(i).getIsHallAvailable().substring(0).equalsIgnoreCase("Y")) {
				val = Integer.valueOf(hallDataList.get(i).getCapacity());
				hallCapacity.put(hallDataList.get(i).getRoomName(), val);
			}
			}
		
		

		while(hallCapacity.size()!=0) {
			
			LinkedHashMap<String, Integer> studentMap = listOfStudents.entrySet()
		            .stream()
		            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
		            .collect(Collectors.toMap(
		                Map.Entry::getKey,
		                Map.Entry::getValue,
		                (oldValue, newValue) -> oldValue, LinkedHashMap::new));
			
			LinkedHashMap<String, Integer> hallMap = hallCapacity.entrySet()
		            .stream()
		            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
		            .collect(Collectors.toMap(
		                Map.Entry::getKey,
		                Map.Entry::getValue,
		                (oldValue, newValue) -> oldValue, LinkedHashMap::new));
			int r=(hallMap.get(hallMap.keySet().toArray()[0]))%3;
			int p=(hallMap.get(hallMap.keySet().toArray()[0]))/3;
			int a,b,c;
			String x=null,y=null,z=null;
			a=studentMap.get(studentMap.keySet().toArray()[0]);
			b=studentMap.get(studentMap.keySet().toArray()[1]);
			c=studentMap.get(studentMap.keySet().toArray()[2]);
			for (Map.Entry<String, Integer> entry : studentMap.entrySet()) {
                if (entry.getValue().equals(a)) {
                    x=entry.getKey();
                }
                if (entry.getValue().equals(b)) {
                    y=entry.getKey();
                }
                if (entry.getValue().equals(c)) {
                    z=entry.getKey();
                }
            }
			if(r==1){
				if(a>=(p+1)) {
					listOfStudents.replace(x, a-(p+1));
				}
				else {
					listOfStudents.replace(x, 0);
				}
				if(b>=(p+1)) {
					listOfStudents.replace(y, b-p);
				}
				else {
					listOfStudents.replace(y, 0);
				}
				if(c>=(p+1)) {
					listOfStudents.replace(z, c-p);
				}
				else {
					listOfStudents.replace(z, 0);
				}		
			}
			else if(r==2){
				if(a>=(p+1)) {
					listOfStudents.replace(x, a-(p+1));
				}
				else {
					listOfStudents.replace(x, 0);
				}
				if(b>=(p+1)) {
					listOfStudents.replace(y, b-(p+1));
				}
				else {
					listOfStudents.replace(y, 0);
				}
				if(c>=(p+1)) {
					listOfStudents.replace(z, c-p);
				}
				else {
					listOfStudents.replace(z, 0);
				}		
			}
			else{
				if(a>=(p+1)) {
					listOfStudents.replace(x, a-p);
				}
				else {
					listOfStudents.replace(x, 0);
				}
				if(b>=(p+1)) {
					listOfStudents.replace(y, b-p);
				}
				else {
					listOfStudents.replace(y, 0);
				}
				if(c>=(p+1)) {
					listOfStudents.replace(z, c-p);
				}
				else {
					listOfStudents.replace(z, 0);
				}		
			}
			int count;
			count=hallMap.get(hallMap.keySet().toArray()[0]);
			String room=null;
			for (Map.Entry<String, Integer> entry : hallMap.entrySet()) {
                if (entry.getValue().equals(count)) {
                	room=entry.getKey();
                }
			for(int i = 0; i < hallDataList.size()-1; i++) {
				hallCapacity.remove(room);
			}
			System.out.println(hallCapacity.size());
		}
		
	}
}
}



