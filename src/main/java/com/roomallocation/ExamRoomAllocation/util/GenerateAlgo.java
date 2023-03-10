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
		
		

		while(hallDataList.size()!=0) {
			HashMap<String, Integer> studentMap = new HashMap<>();
			HashMap<String, Integer> hallMap = new HashMap<>();
			studentMap = sortInReverse(listOfStudents);
			hallMap = sortInReverse(hallCapacity);
//			Collections.sort(listOfStudents,  Collections.reverseOrder());
//			Collections.sort(hallCapacity,  Collections.reverseOrder());
//			int r=(hallMap.get(0))%3;
//			int p=(hallMap.get(0))/3;
//			if(r==1){
//				if(studentMap.get(0)>=(p+1)) 
//					studentMap.set(0,studentMap.get(0)-(p+1));
//				else
//					listOfStudents.set(0,0);
//				if(listOfStudents.get(1)>=p)
//					listOfStudents.set(1,listOfStudents.get(1)-p);
//				else
//					listOfStudents.set(1,0);
//				if(listOfStudents.get(2)>=p)
//					listOfStudents.set(2,listOfStudents.get(2)-p);
//				else
//					listOfStudents.set(2,0);
//			}
//			else if(r==2){
//				if(listOfStudents.get(0)>=(p+1)) 
//					listOfStudents.set(0,listOfStudents.get(0)-(p+1));
//				else
//					listOfStudents.set(0,0);
//				if(listOfStudents.get(1)>=p)
//					listOfStudents.set(1,listOfStudents.get(1)-(p+1));
//				else
//					listOfStudents.set(1,0);
//				if(listOfStudents.get(2)>=p)
//					listOfStudents.set(2,listOfStudents.get(2)-p);
//				else
//					listOfStudents.set(2,0);
//			}
//			else{
//				if(listOfStudents.get(0)>=(p+1)) 
//					listOfStudents.set(0,listOfStudents.get(0)-p);
//				else
//					listOfStudents.set(0,0);
//				if(listOfStudents.get(1)>=p)
//					listOfStudents.set(1,listOfStudents.get(1)-p);
//				else
//					listOfStudents.set(1,0);
//				if(listOfStudents.get(2)>=p)
//					listOfStudents.set(2,listOfStudents.get(2)-p);
//				else
//					listOfStudents.set(2,0);
//			}
//			for(int i = 0; i < hallCapacity.size()-1; i++) {
//				hallCapacity.set(i, hallCapacity.get(i+1));
//			}
//			hallCapacity.remove(hallCapacity.size()-1);
//			System.out.println(hallCapacity.size());
		}
		
	}
	
	public HashMap<String, Integer> sortInReverse(HashMap<String, Integer> hm) {
		List<Map.Entry<String, Integer> > list =
	               new LinkedList<Map.Entry<String, Integer> >(hm.entrySet());
	 
	        // Sort the list
	        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() {
	            public int compare(Map.Entry<String, Integer> o1,
	                               Map.Entry<String, Integer> o2)
	            {
	                return (o1.getValue()).compareTo(o2.getValue());
	            }
	        });
	         
	        // put data from sorted list to hashmap
	        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
	        for (Map.Entry<String, Integer> aa : list) {
	            temp.put(aa.getKey(), aa.getValue());
	        }
	        
	        HashMap<String, Integer> reversedHashMap = new HashMap<String, Integer>();

	        for (String i : temp.keySet()) {
	            reversedHashMap.put(i, temp.get(i));
	        }
	        
	        return reversedHashMap;
		
	}

}



