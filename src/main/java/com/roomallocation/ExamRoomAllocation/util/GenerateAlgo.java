package com.roomallocation.ExamRoomAllocation.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

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
			int count = 0;
			for(int j = 1; j<studentList.size(); j++) {
				for(int k = 1; k< studentList.get(j).getCourses().size(); k++)
				if(studentList.get(j).getCourses().get(k).equals(dateSheetList.get(i).getSubjectCode())) {
					count++;
				}
					
			}
			listOfStudents.put(dateSheetList.get(i).getSubjectCode(), count);
		}
		
		int val = 0;
		for(int i = 0; i < hallDataList.size(); i++) {
			if(hallDataList.get(i).getIsHallAvailable().substring(0,1).equalsIgnoreCase("Y")) {
				val = Integer.valueOf(hallDataList.get(i).getCapacity());
				hallCapacity.put(hallDataList.get(i).getRoomName(), val);
			}
		}
		
		

		MultiValueMap<String, List<String>> outputMap = new LinkedMultiValueMap<>();
		while(hallCapacity.size()!=0) {
			ArrayList<String> courseList = new ArrayList<>();
			
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
			
			int count;
			count = hallMap.get(hallMap.keySet().toArray()[0]);
			String room = null;
			for (Map.Entry<String, Integer> entry : hallMap.entrySet()) {
				if (entry.getValue().equals(count)) {
					room = entry.getKey();
				}
			}
			int r = (hallMap.get(hallMap.keySet().toArray()[0]))%3;
			int p = (hallMap.get(hallMap.keySet().toArray()[0]))/3;
			int a,b,c;
			String x = null, y = null, z = null;
			a = studentMap.get(studentMap.keySet().toArray()[0]);
			b = studentMap.get(studentMap.keySet().toArray()[1]);
			c = studentMap.get(studentMap.keySet().toArray()[2]);
			
			for (Map.Entry<String, Integer> entry : studentMap.entrySet()) {
                if (entry.getValue().equals(a)) {
                    x = entry.getKey();
                }
                if (entry.getValue().equals(b)) {
                    y = entry.getKey();
                }
                if (entry.getValue().equals(c)) {
                    z = entry.getKey();
                }
            }
//			System.out.println(x + " : " + y + " : " + z + " : " + room);
			if(r==1){
				if(a>=(p+1)) {
					listOfStudents.replace(x, a-(p+1));
					courseList.add(x);
					courseList.add(String.valueOf(p+1));
					outputMap.add(room, courseList);
					
				}
				else {
					listOfStudents.replace(x, 0);
					courseList.add(x);
					courseList.add(String.valueOf(a));
					outputMap.add(room, courseList);
				}
				if(b>=(p+1)) {
					listOfStudents.replace(y, b-p);
					courseList.add(y);
					courseList.add(String.valueOf(p));
					outputMap.add(room, courseList);
				}
				else {
					listOfStudents.replace(y, 0);
					courseList.add(y);
					courseList.add(String.valueOf(b));
					outputMap.add(room, courseList);
				}
				if(c>=(p+1)) {
					listOfStudents.replace(z, c-p);
					courseList.add(z);
					courseList.add(String.valueOf(p));
					outputMap.add(room, courseList);
				}
				else {
					listOfStudents.replace(z, 0);
					courseList.add(z);
					courseList.add(String.valueOf(c));
					outputMap.add(room, courseList);
				}		
			}
			else if(r==2){
				if(a>=(p+1)) {
					listOfStudents.replace(x, a-(p+1));
					courseList.add(x);
					courseList.add(String.valueOf((p+1)));
					outputMap.add(room, courseList);
				}
				else {
					listOfStudents.replace(x, 0);
					courseList.add(x);
					courseList.add(String.valueOf((a)));
					outputMap.add(room, courseList);
				}
				if(b>=(p+1)) {
					listOfStudents.replace(y, b-(p+1));
					courseList.add(y);
					courseList.add(String.valueOf((p+1)));
					outputMap.add(room, courseList);
				}
				else {
					listOfStudents.replace(y, 0);
					courseList.add(y);
					courseList.add(String.valueOf((b)));
					outputMap.add(room, courseList);
				}
				if(c>=(p+1)) {
					listOfStudents.replace(z, c-p);
					courseList.add(z);
					courseList.add(String.valueOf((p)));
					outputMap.add(room, courseList);
				}
				else {
					listOfStudents.replace(z, 0);
					courseList.add(z);
					courseList.add(String.valueOf((c)));
					outputMap.add(room, courseList);
				}		
			}
			else{
				if(a>=(p+1)) {
					listOfStudents.replace(x, a-p);
					courseList.add(z);
					courseList.add(String.valueOf((p)));
					outputMap.add(room, courseList);
				}
				else {
					listOfStudents.replace(x, 0);
					courseList.add(x);
					courseList.add(String.valueOf((a)));
					outputMap.add(room, courseList);
				}
				if(b>=(p+1)) {
					listOfStudents.replace(y, b-p);
					courseList.add(y);
					courseList.add(String.valueOf((p)));
					outputMap.add(room, courseList);
				}
				else {
					listOfStudents.replace(y, 0);
					courseList.add(y);
					courseList.add(String.valueOf((b)));
					outputMap.add(room, courseList);
				}
				if(c>=(p+1)) {
					listOfStudents.replace(z, c-p);
					courseList.add(z);
					courseList.add(String.valueOf((p)));
					outputMap.add(room, courseList);
				}
				else {
					listOfStudents.replace(z, 0);
					courseList.add(z);
					courseList.add(String.valueOf((c)));
					outputMap.add(room, courseList);
				}		
			}
//			System.out.println(hallCapacity.size());
			hallCapacity.remove(room);
		
		}
//		for(Entry<String, List<Object>> m  : outputMap.entrySet()){    
//            System.out.println(m.getKey()+" "+m.getValue());    
//        }
		for(Map.Entry m : outputMap.entrySet()){    
            System.out.println(m.getKey()+" "+m.getValue());    
        }
	}
}



