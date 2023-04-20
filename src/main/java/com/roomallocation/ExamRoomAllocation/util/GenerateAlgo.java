package com.roomallocation.ExamRoomAllocation.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.roomallocation.ExamRoomAllocation.vo.AlgoOutputVO;
import com.roomallocation.ExamRoomAllocation.vo.DatesheetVO;
import com.roomallocation.ExamRoomAllocation.vo.HallDataVO;
import com.roomallocation.ExamRoomAllocation.vo.StudentVO;

@Component
public class GenerateAlgo {
	
	public MultiValueMap<String, List<String>> generateAlgo(ArrayList<DatesheetVO> dateSheetList, ArrayList<HallDataVO> hallDataList, 
			List<StudentVO> studentList) {
		HashMap<String, Integer> listOfGirlsStudents = new HashMap<>();
		HashMap<String, Integer> listOfBoysStudents = new HashMap<>();
		HashMap<String, Integer> hallCapacityForMale = new HashMap<>();
		HashMap<String, Integer> hallCapacityForFemale = new HashMap<>();
		for(int i = 1; i < dateSheetList.size(); i++) {
			int countGirls = 0;
			int countBoys = 0;
			for(int j = 1; j<studentList.size(); j++) {
				for(int k = 1; k< studentList.get(j).getCourses().size(); k++) {
					if(studentList.get(j).getCourses().get(k).equals(dateSheetList.get(i).getSubjectCode())&&studentList.get(j).getGender().equalsIgnoreCase("m")) {
					countBoys++;
					}
					else if(studentList.get(j).getCourses().get(k).equals(dateSheetList.get(i).getSubjectCode())&&studentList.get(j).getGender().equalsIgnoreCase("f")) {
					countGirls++;
					}	
				}
			}
			listOfBoysStudents.put(dateSheetList.get(i).getSubjectCode(), countBoys);
			listOfGirlsStudents.put(dateSheetList.get(i).getSubjectCode(), countGirls);
		}

		
		int val = 0;
		for(int i = 0; i < hallDataList.size(); i++) {
			if(hallDataList.get(i).getIsHallAvailable().substring(0,1).equalsIgnoreCase("Y")&&hallDataList.get(i).getGender().equalsIgnoreCase("m")) {
				val = Integer.valueOf(hallDataList.get(i).getCapacity());
				hallCapacityForMale.put(hallDataList.get(i).getRoomName(), val);
			}
			else if(hallDataList.get(i).getIsHallAvailable().substring(0,1).equalsIgnoreCase("Y")&&hallDataList.get(i).getGender().equalsIgnoreCase("f")) {
				val = Integer.valueOf(hallDataList.get(i).getCapacity());
				hallCapacityForFemale.put(hallDataList.get(i).getRoomName(), val);
			}
		}
		

		MultiValueMap<String, List<String>> outputMap = new LinkedMultiValueMap<>();
		
		while(hallCapacityForFemale.size()!=0) {
			ArrayList<String> courseList = new ArrayList<>();
			
			LinkedHashMap<String, Integer> studentMap = listOfGirlsStudents.entrySet()
		            .stream()
		            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
		            .collect(Collectors.toMap(
		                Map.Entry::getKey,
		                Map.Entry::getValue,
		                (oldValue, newValue) -> oldValue, LinkedHashMap::new));
			
			LinkedHashMap<String, Integer> hallMap = hallCapacityForFemale.entrySet()
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
			int a,b,c;
			a = studentMap.get(studentMap.keySet().toArray()[0]);
			b = studentMap.get(studentMap.keySet().toArray()[1]);
			c = studentMap.get(studentMap.keySet().toArray()[2]);
			if(a!=0 && b!=0 && c!=0) {		
				int r = (hallMap.get(hallMap.keySet().toArray()[0]))%3;
				int p = (hallMap.get(hallMap.keySet().toArray()[0]))/3;
				String x = null, y = null, z = null;
				for (Map.Entry<String, Integer> entry : studentMap.entrySet()) {
	                if (entry.getValue().equals(a)) {
	                    x = entry.getKey();
	                }
				}
	            studentMap.remove(x);
	            for (Map.Entry<String, Integer> entry : studentMap.entrySet()) {
	                if (entry.getValue().equals(b)) {
	                    y = entry.getKey();
	                }
	            }
	            studentMap.remove(y);
	            for (Map.Entry<String, Integer> entry : studentMap.entrySet()) {
	                if (entry.getValue().equals(c)) {
	                    z = entry.getKey();
	                }
	            }
	            studentMap.remove(z);
				if(r==1){
					if(a>=(p+1)) {
						listOfGirlsStudents.replace(x, a-(p+1));
						courseList.add(x);
						courseList.add(String.valueOf(p+1));
					}
					else {
						listOfGirlsStudents.replace(x, 0);
						courseList.add(x);
						courseList.add(String.valueOf(a));
					}
					if(b>=(p)) {
						listOfGirlsStudents.replace(y, b-p);
						courseList.add(y);
						courseList.add(String.valueOf(p));
					}
					else {
						listOfGirlsStudents.replace(y, 0);
						courseList.add(y);
						courseList.add(String.valueOf(b));
					}
					if(c>=(p)) {
						listOfGirlsStudents.replace(z, c-p);
						courseList.add(z);
						courseList.add(String.valueOf(p));
					}
					else {
						listOfGirlsStudents.replace(z, 0);
						courseList.add(z);
						courseList.add(String.valueOf(c));
					}		
				}
				else if(r==2){
					if(a>=(p+1)) {
						listOfGirlsStudents.replace(x, a-(p+1));
						courseList.add(x);
						courseList.add(String.valueOf((p+1)));
					}
					else {
						listOfGirlsStudents.replace(x, 0);
						courseList.add(x);
						courseList.add(String.valueOf((a)));
					}
					if(b>=(p+1)) {
						listOfGirlsStudents.replace(y, b-(p+1));
						courseList.add(y);
						courseList.add(String.valueOf((p+1)));
					}
					else {
						listOfGirlsStudents.replace(y, 0);
						courseList.add(y);
						courseList.add(String.valueOf((b)));
					}
					if(c>=(p)) {
						listOfGirlsStudents.replace(z, c-p);
						courseList.add(z);
						courseList.add(String.valueOf((p)));
					}
					else {
						listOfGirlsStudents.replace(z, 0);
						courseList.add(z);
						courseList.add(String.valueOf((c)));
					}		
				}
				else{
					if(a>=(p)) {
						listOfGirlsStudents.replace(x, a-p);
						courseList.add(x);
						courseList.add(String.valueOf((p)));
					}
					else {
						listOfGirlsStudents.replace(x, 0);
						courseList.add(x);
						courseList.add(String.valueOf((a)));
					}
					if(b>=(p)) {
						listOfGirlsStudents.replace(y, b-p);
						courseList.add(y);
						courseList.add(String.valueOf((p)));
					}
					else {
						listOfGirlsStudents.replace(y, 0);
						courseList.add(y);
						courseList.add(String.valueOf((b)));
					}
					if(c>=(p)) {
						listOfGirlsStudents.replace(z, c-p);
						courseList.add(z);
						courseList.add(String.valueOf((p)));
					}
					else {
						listOfGirlsStudents.replace(z, 0);
						courseList.add(z);
						courseList.add(String.valueOf((c)));
					}		
				}
			}
			
			else if((a!=0 && b!=0 && c==0)||(a==0 && b!=0 && c!=0)||(a!=0 && b==0 && c!=0)) {
				int r = (hallMap.get(hallMap.keySet().toArray()[0]))%2;
				int p = (hallMap.get(hallMap.keySet().toArray()[0]))/2;
				int m=0, n=0;
				if(a==0) {
					m=b;
					n=c;
				}
				else if(b==0) {
					m=a;
					n=c;
				}
				else if(c==0) {
					m=a;
					n=b;
				}
				String x = null, y = null;
				for (Map.Entry<String, Integer> entry : studentMap.entrySet()) {
	                if (entry.getValue().equals(m)) {
	                    x = entry.getKey();
	                }
				}
	            studentMap.remove(x);
	            for (Map.Entry<String, Integer> entry : studentMap.entrySet()) {
	                if (entry.getValue().equals(n)) {
	                    y = entry.getKey();
	                }
	            }
	            studentMap.remove(y);
				if(r==1){
					if(m>=(p+1)) {
						listOfGirlsStudents.replace(x, m-(p+1));
						courseList.add(x);
						courseList.add(String.valueOf(p+1));
					}
					else {
						listOfGirlsStudents.replace(x, 0);
						courseList.add(x);
						courseList.add(String.valueOf(m));
					}
					if(n>=(p)) {
						listOfGirlsStudents.replace(y, n-p);
						courseList.add(y);
						courseList.add(String.valueOf(p));
					}
					else {
						listOfGirlsStudents.replace(y, 0);
						courseList.add(y);
						courseList.add(String.valueOf(n));
					}	
				}
				else{
					if(m>=(p)) {
						listOfGirlsStudents.replace(x, m-p);
						courseList.add(x);
						courseList.add(String.valueOf((p)));
					}
					else {
						listOfGirlsStudents.replace(x, 0);
						courseList.add(x);
						courseList.add(String.valueOf((m)));
					}
					if(n>=(p)) {
						listOfGirlsStudents.replace(y, n-p);
						courseList.add(y);
						courseList.add(String.valueOf((p)));
					}
					else {
						listOfGirlsStudents.replace(y, 0);
						courseList.add(y);
						courseList.add(String.valueOf((n)));
					}
				}
			}
			else if((a!=0 && b==0 && c==0)||(a==0 && b==0 && c!=0)||(a==0 && b==0 && c!=0)) {
				int p = (hallMap.get(hallMap.keySet().toArray()[0]));
				int m=0;
				if(a!=0) {
					m=a;
				}
				else if(b!=0) {
					m=b;
				}
				else if(c!=0) {
					m=c;
				}
				String x = null;
				for (Map.Entry<String, Integer> entry : studentMap.entrySet()) {
	                if (entry.getValue().equals(m)) {
	                    x = entry.getKey();
	                }
				}
	            studentMap.remove(x);
					if(m>=(p)) {
						listOfGirlsStudents.replace(x, m-p);
						courseList.add(x);
						courseList.add(String.valueOf((p)));
					}
					else {
						listOfGirlsStudents.replace(x, 0);
						courseList.add(x);
						courseList.add(String.valueOf((m)));
					}
				}
			else {
				break;
			}
			outputMap.add(room, courseList);
			hallCapacityForFemale.remove(room);
		}
		
		while(hallCapacityForMale.size()!=0) {
			ArrayList<String> courseList = new ArrayList<>();
			
			LinkedHashMap<String, Integer> studentMap = listOfBoysStudents.entrySet()
		            .stream()
		            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
		            .collect(Collectors.toMap(
		                Map.Entry::getKey,
		                Map.Entry::getValue,
		                (oldValue, newValue) -> oldValue, LinkedHashMap::new));
			
			LinkedHashMap<String, Integer> hallMap = hallCapacityForMale.entrySet()
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
			int a,b,c;
			a = studentMap.get(studentMap.keySet().toArray()[0]);
			b = studentMap.get(studentMap.keySet().toArray()[1]);
			c = studentMap.get(studentMap.keySet().toArray()[2]);
			if(a!=0 && b!=0 && c!=0) {	
				int r = (hallMap.get(hallMap.keySet().toArray()[0]))%3;
				int p = (hallMap.get(hallMap.keySet().toArray()[0]))/3;
				String x = null, y = null, z = null;
//				for (Map.Entry<String, Integer> entry : studentMap.entrySet()) {
//	                if (entry.getValue().equals(a)) {
//	                    x = entry.getKey();
//	                }
//	                if (entry.getValue().equals(b)) {
//	                    y = entry.getKey();
//	                }
//	                if (entry.getValue().equals(c)) {
//	                    z = entry.getKey();
//	                }
//	            }
				
				for (Map.Entry<String, Integer> entry : studentMap.entrySet()) {
	                if (entry.getValue().equals(a)) {
	                    x = entry.getKey();
	                }
				}
	            studentMap.remove(x);
	            for (Map.Entry<String, Integer> entry : studentMap.entrySet()) {
	                if (entry.getValue().equals(b)) {
	                    y = entry.getKey();
	                }
	            }
	            studentMap.remove(y);
	            for (Map.Entry<String, Integer> entry : studentMap.entrySet()) {
	                if (entry.getValue().equals(c)) {
	                    z = entry.getKey();
	                }
	            }
	            studentMap.remove(z);
			
				
	//			System.out.println(x + " : " + y + " : " + z + " : " + room);
				if(r==1){
					if(a>=(p+1)) {
						listOfBoysStudents.replace(x, a-(p+1));
						courseList.add(x);
						courseList.add(String.valueOf(p+1));
	//					outputMap.add(room, courseList);	
					}
					else {
						listOfBoysStudents.replace(x, 0);
						courseList.add(x);
						courseList.add(String.valueOf(a));
	//					outputMap.add(room, courseList);
					}
					if(b>=(p)) {
						listOfBoysStudents.replace(y, b-p);
						courseList.add(y);
						courseList.add(String.valueOf(p));
	//					outputMap.add(room, courseList);
					}
					else {
						listOfBoysStudents.replace(y, 0);
						courseList.add(y);
						courseList.add(String.valueOf(b));
	//					outputMap.add(room, courseList);
					}
					if(c>=(p)) {
						listOfBoysStudents.replace(z, c-p);
						courseList.add(z);
						courseList.add(String.valueOf(p));
	//					outputMap.add(room, courseList);
					}
					else {
						listOfBoysStudents.replace(z, 0);
						courseList.add(z);
						courseList.add(String.valueOf(c));
	//					outputMap.add(room, courseList);
					}		
				}
				else if(r==2){
					if(a>=(p+1)) {
						listOfBoysStudents.replace(x, a-(p+1));
						courseList.add(x);
						courseList.add(String.valueOf((p+1)));
	//					outputMap.add(room, courseList);
					}
					else {
						listOfBoysStudents.replace(x, 0);
						courseList.add(x);
						courseList.add(String.valueOf((a)));
	//					outputMap.add(room, courseList);
					}
					if(b>=(p+1)) {
						listOfBoysStudents.replace(y, b-(p+1));
						courseList.add(y);
						courseList.add(String.valueOf((p+1)));
	//					outputMap.add(room, courseList);
					}
					else {
						listOfBoysStudents.replace(y, 0);
						courseList.add(y);
						courseList.add(String.valueOf((b)));
	//					outputMap.add(room, courseList);
					}
					if(c>=(p)) {
						listOfBoysStudents.replace(z, c-p);
						courseList.add(z);
						courseList.add(String.valueOf((p)));
	//					outputMap.add(room, courseList);
					}
					else {
						listOfBoysStudents.replace(z, 0);
						courseList.add(z);
						courseList.add(String.valueOf((c)));
	//					outputMap.add(room, courseList);
					}		
				}
				else{
					if(a>=(p)) {
						listOfBoysStudents.replace(x, a-p);
						courseList.add(x);
						courseList.add(String.valueOf((p)));
	//					outputMap.add(room, courseList);
					}
					else {
						listOfBoysStudents.replace(x, 0);
						courseList.add(x);
						courseList.add(String.valueOf((a)));
	//					outputMap.add(room, courseList);
					}
					if(b>=(p)) {
						listOfBoysStudents.replace(y, b-p);
						courseList.add(y);
						courseList.add(String.valueOf((p)));
	//					outputMap.add(room, courseList);
					}
					else {
						listOfBoysStudents.replace(y, 0);
						courseList.add(y);
						courseList.add(String.valueOf((b)));
	//					outputMap.add(room, courseList);
					}
					if(c>=(p)) {
						listOfBoysStudents.replace(z, c-p);
						courseList.add(z);
						courseList.add(String.valueOf((p)));
	//					outputMap.add(room, courseList);
					}
					else {
						listOfBoysStudents.replace(z, 0);
						courseList.add(z);
						courseList.add(String.valueOf((c)));
	//					outputMap.add(room, courseList);
					}		
				}
			}
			
			else if((a!=0 && b!=0 && c==0)||(a==0 && b!=0 && c!=0)||(a!=0 && b==0 && c!=0)) {
				int r = (hallMap.get(hallMap.keySet().toArray()[0]))%2;
				int p = (hallMap.get(hallMap.keySet().toArray()[0]))/2;
				int m=0, n=0;
				if(a==0) {
					m=b;
					n=c;
				}
				else if(b==0) {
					m=a;
					n=c;
				}
				else if(c==0) {
					m=a;
					n=b;
				}
				String x = null, y = null;
				for (Map.Entry<String, Integer> entry : studentMap.entrySet()) {
	                if (entry.getValue().equals(m)) {
	                    x = entry.getKey();
	                }
				}
	            studentMap.remove(x);
	            for (Map.Entry<String, Integer> entry : studentMap.entrySet()) {
	                if (entry.getValue().equals(n)) {
	                    y = entry.getKey();
	                }
	            }
	            studentMap.remove(y);
				if(r==1){
					if(m>=(p+1)) {
						listOfBoysStudents.replace(x, m-(p+1));
						courseList.add(x);
						courseList.add(String.valueOf(p+1));
					}
					else {
						listOfBoysStudents.replace(x, 0);
						courseList.add(x);
						courseList.add(String.valueOf(m));
					}
					if(n>=(p)) {
						listOfBoysStudents.replace(y, n-p);
						courseList.add(y);
						courseList.add(String.valueOf(p));
					}
					else {
						listOfBoysStudents.replace(y, 0);
						courseList.add(y);
						courseList.add(String.valueOf(n));
					}	
				}
				else{
					if(m>=(p)) {
						listOfBoysStudents.replace(x, m-p);
						courseList.add(x);
						courseList.add(String.valueOf((p)));
					}
					else {
						listOfBoysStudents.replace(x, 0);
						courseList.add(x);
						courseList.add(String.valueOf((m)));
					}
					if(n>=(p)) {
						listOfBoysStudents.replace(y, n-p);
						courseList.add(y);
						courseList.add(String.valueOf((p)));
					}
					else {
						listOfBoysStudents.replace(y, 0);
						courseList.add(y);
						courseList.add(String.valueOf((n)));
					}
				}
			}
			else if((a!=0 && b==0 && c==0)||(a==0 && b==0 && c!=0)||(a==0 && b==0 && c!=0)) {
				int p = (hallMap.get(hallMap.keySet().toArray()[0]));
				int m=0;
				if(a!=0) {
					m=a;
				}
				else if(b!=0) {
					m=b;
				}
				else if(c!=0) {
					m=c;
				}
				String x = null;
				for (Map.Entry<String, Integer> entry : studentMap.entrySet()) {
	                if (entry.getValue().equals(m)) {
	                    x = entry.getKey();
	                }
				}
	            studentMap.remove(x);
					if(m>=(p)) {
						listOfBoysStudents.replace(x, m-p);
						courseList.add(x);
						courseList.add(String.valueOf((p)));
					}
					else {
						listOfBoysStudents.replace(x, 0);
						courseList.add(x);
						courseList.add(String.valueOf((m)));
					}
				}
			else {
				break;
			}
		
			outputMap.add(room, courseList);
			hallCapacityForMale.remove(room);
		
		}
//		for(Entry<String, List<Object>> m  : outputMap.entrySet()){    
//            System.out.println(m.getKey()+" "+m.getValue());    
//        }
		for(Map.Entry m : outputMap.entrySet()){    
            System.out.println(m.getKey()+" "+m.getValue());    
        }
		
//		 MultiValueMap<String, List<String>>  valueMap = outputMap;
//	       Set<String> valueSet = valueMap.keySet();
//	       
//	       for(String str: valueSet) {
//               System.out.println(valueMap.get(str).get(0));
//       }
		
		return outputMap;
	}
	
	public ArrayList<AlgoOutputVO> generateOutput(ArrayList<DatesheetVO> dateSheetList, ArrayList<HallDataVO> hallDataList, 
			List<StudentVO> studentList) {
		
		HashMap<String, Integer> listOfGirlsStudents = new HashMap<>();
		HashMap<String, Integer> listOfBoysStudents = new HashMap<>();
		HashMap<String, Integer> hallCapacityForMale = new HashMap<>();
		HashMap<String, Integer> hallCapacityForFemale = new HashMap<>();
		for(int i = 1; i < dateSheetList.size(); i++) {
			int countGirls = 0;
			int countBoys = 0;
			for(int j = 1; j<studentList.size(); j++) {
				for(int k = 1; k< studentList.get(j).getCourses().size(); k++) {
					if(studentList.get(j).getCourses().get(k).equals(dateSheetList.get(i).getSubjectCode())&&studentList.get(j).getGender().equalsIgnoreCase("m")) {
					countBoys++;
					}
					else if(studentList.get(j).getCourses().get(k).equals(dateSheetList.get(i).getSubjectCode())&&studentList.get(j).getGender().equalsIgnoreCase("f")) {
					countGirls++;
					}	
				}
			}
			listOfBoysStudents.put(dateSheetList.get(i).getSubjectCode(), countBoys);
			listOfGirlsStudents.put(dateSheetList.get(i).getSubjectCode(), countGirls);
		}

		
		int val = 0;
		for(int i = 0; i < hallDataList.size(); i++) {
			if(hallDataList.get(i).getIsHallAvailable().substring(0,1).equalsIgnoreCase("Y")&&hallDataList.get(i).getGender().equalsIgnoreCase("m")) {
				val = Integer.valueOf(hallDataList.get(i).getCapacity());
				hallCapacityForMale.put(hallDataList.get(i).getRoomName(), val);
			}
			else if(hallDataList.get(i).getIsHallAvailable().substring(0,1).equalsIgnoreCase("Y")&&hallDataList.get(i).getGender().equalsIgnoreCase("f")) {
				val = Integer.valueOf(hallDataList.get(i).getCapacity());
				hallCapacityForFemale.put(hallDataList.get(i).getRoomName(), val);
			}
		}
		

//		MultiValueMap<String, List<String>> outputMap = new LinkedMultiValueMap<>();
		ArrayList<AlgoOutputVO> outputList = new ArrayList<>();
		
		while(hallCapacityForFemale.size()!=0) {
			ArrayList<String> courseList = new ArrayList<>();
			
			LinkedHashMap<String, Integer> studentMap = listOfGirlsStudents.entrySet()
		            .stream()
		            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
		            .collect(Collectors.toMap(
		                Map.Entry::getKey,
		                Map.Entry::getValue,
		                (oldValue, newValue) -> oldValue, LinkedHashMap::new));
			
			LinkedHashMap<String, Integer> hallMap = hallCapacityForFemale.entrySet()
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
			int a,b,c;
			a = studentMap.get(studentMap.keySet().toArray()[0]);
			b = studentMap.get(studentMap.keySet().toArray()[1]);
			c = studentMap.get(studentMap.keySet().toArray()[2]);
			if(a!=0 && b!=0 && c!=0) {		
				int r = (hallMap.get(hallMap.keySet().toArray()[0]))%3;
				int p = (hallMap.get(hallMap.keySet().toArray()[0]))/3;
				String x = null, y = null, z = null;
				for (Map.Entry<String, Integer> entry : studentMap.entrySet()) {
	                if (entry.getValue().equals(a)) {
	                    x = entry.getKey();
	                }
				}
	            studentMap.remove(x);
	            for (Map.Entry<String, Integer> entry : studentMap.entrySet()) {
	                if (entry.getValue().equals(b)) {
	                    y = entry.getKey();
	                }
	            }
	            studentMap.remove(y);
	            for (Map.Entry<String, Integer> entry : studentMap.entrySet()) {
	                if (entry.getValue().equals(c)) {
	                    z = entry.getKey();
	                }
	            }
	            studentMap.remove(z);
				if(r==1){
					if(a>=(p+1)) {
						listOfGirlsStudents.replace(x, a-(p+1));
						courseList.add(x);
						courseList.add(String.valueOf(p+1));
					}
					else {
						listOfGirlsStudents.replace(x, 0);
						courseList.add(x);
						courseList.add(String.valueOf(a));
					}
					if(b>=(p)) {
						listOfGirlsStudents.replace(y, b-p);
						courseList.add(y);
						courseList.add(String.valueOf(p));
					}
					else {
						listOfGirlsStudents.replace(y, 0);
						courseList.add(y);
						courseList.add(String.valueOf(b));
					}
					if(c>=(p)) {
						listOfGirlsStudents.replace(z, c-p);
						courseList.add(z);
						courseList.add(String.valueOf(p));
					}
					else {
						listOfGirlsStudents.replace(z, 0);
						courseList.add(z);
						courseList.add(String.valueOf(c));
					}		
				}
				else if(r==2){
					if(a>=(p+1)) {
						listOfGirlsStudents.replace(x, a-(p+1));
						courseList.add(x);
						courseList.add(String.valueOf((p+1)));
					}
					else {
						listOfGirlsStudents.replace(x, 0);
						courseList.add(x);
						courseList.add(String.valueOf((a)));
					}
					if(b>=(p+1)) {
						listOfGirlsStudents.replace(y, b-(p+1));
						courseList.add(y);
						courseList.add(String.valueOf((p+1)));
					}
					else {
						listOfGirlsStudents.replace(y, 0);
						courseList.add(y);
						courseList.add(String.valueOf((b)));
					}
					if(c>=(p)) {
						listOfGirlsStudents.replace(z, c-p);
						courseList.add(z);
						courseList.add(String.valueOf((p)));
					}
					else {
						listOfGirlsStudents.replace(z, 0);
						courseList.add(z);
						courseList.add(String.valueOf((c)));
					}		
				}
				else{
					if(a>=(p)) {
						listOfGirlsStudents.replace(x, a-p);
						courseList.add(x);
						courseList.add(String.valueOf((p)));
					}
					else {
						listOfGirlsStudents.replace(x, 0);
						courseList.add(x);
						courseList.add(String.valueOf((a)));
					}
					if(b>=(p)) {
						listOfGirlsStudents.replace(y, b-p);
						courseList.add(y);
						courseList.add(String.valueOf((p)));
					}
					else {
						listOfGirlsStudents.replace(y, 0);
						courseList.add(y);
						courseList.add(String.valueOf((b)));
					}
					if(c>=(p)) {
						listOfGirlsStudents.replace(z, c-p);
						courseList.add(z);
						courseList.add(String.valueOf((p)));
					}
					else {
						listOfGirlsStudents.replace(z, 0);
						courseList.add(z);
						courseList.add(String.valueOf((c)));
					}		
				}
			}
			
			else if((a!=0 && b!=0 && c==0)||(a==0 && b!=0 && c!=0)||(a!=0 && b==0 && c!=0)) {
				int r = (hallMap.get(hallMap.keySet().toArray()[0]))%2;
				int p = (hallMap.get(hallMap.keySet().toArray()[0]))/2;
				int m=0, n=0;
				if(a==0) {
					m=b;
					n=c;
				}
				else if(b==0) {
					m=a;
					n=c;
				}
				else if(c==0) {
					m=a;
					n=b;
				}
				String x = null, y = null;
				for (Map.Entry<String, Integer> entry : studentMap.entrySet()) {
	                if (entry.getValue().equals(m)) {
	                    x = entry.getKey();
	                }
				}
	            studentMap.remove(x);
	            for (Map.Entry<String, Integer> entry : studentMap.entrySet()) {
	                if (entry.getValue().equals(n)) {
	                    y = entry.getKey();
	                }
	            }
	            studentMap.remove(y);
				if(r==1){
					if(m>=(p+1)) {
						listOfGirlsStudents.replace(x, m-(p+1));
						courseList.add(x);
						courseList.add(String.valueOf(p+1));
					}
					else {
						listOfGirlsStudents.replace(x, 0);
						courseList.add(x);
						courseList.add(String.valueOf(m));
					}
					if(n>=(p)) {
						listOfGirlsStudents.replace(y, n-p);
						courseList.add(y);
						courseList.add(String.valueOf(p));
					}
					else {
						listOfGirlsStudents.replace(y, 0);
						courseList.add(y);
						courseList.add(String.valueOf(n));
					}	
				}
				else{
					if(m>=(p)) {
						listOfGirlsStudents.replace(x, m-p);
						courseList.add(x);
						courseList.add(String.valueOf((p)));
					}
					else {
						listOfGirlsStudents.replace(x, 0);
						courseList.add(x);
						courseList.add(String.valueOf((m)));
					}
					if(n>=(p)) {
						listOfGirlsStudents.replace(y, n-p);
						courseList.add(y);
						courseList.add(String.valueOf((p)));
					}
					else {
						listOfGirlsStudents.replace(y, 0);
						courseList.add(y);
						courseList.add(String.valueOf((n)));
					}
				}
			}
			else if((a!=0 && b==0 && c==0)||(a==0 && b==0 && c!=0)||(a==0 && b==0 && c!=0)) {
				int p = (hallMap.get(hallMap.keySet().toArray()[0]));
				int m=0;
				if(a!=0) {
					m=a;
				}
				else if(b!=0) {
					m=b;
				}
				else if(c!=0) {
					m=c;
				}
				String x = null;
				for (Map.Entry<String, Integer> entry : studentMap.entrySet()) {
	                if (entry.getValue().equals(m)) {
	                    x = entry.getKey();
	                }
				}
	            studentMap.remove(x);
					if(m>=(p)) {
						listOfGirlsStudents.replace(x, m-p);
						courseList.add(x);
						courseList.add(String.valueOf((p)));
					}
					else {
						listOfGirlsStudents.replace(x, 0);
						courseList.add(x);
						courseList.add(String.valueOf((m)));
					}
				}
			else {
				break;
			}

			AlgoOutputVO valueObject = new AlgoOutputVO();
			valueObject.setClassRoom(room);
			valueObject.setValues(courseList);
			outputList.add(valueObject);
//			outputMap.add(room, courseList);
//			System.out.println(hallCapacity.size());
			hallCapacityForFemale.remove(room);			
		}
		
		while(hallCapacityForMale.size()!=0) {
			ArrayList<String> courseList = new ArrayList<>();
			
			LinkedHashMap<String, Integer> studentMap = listOfBoysStudents.entrySet()
		            .stream()
		            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
		            .collect(Collectors.toMap(
		                Map.Entry::getKey,
		                Map.Entry::getValue,
		                (oldValue, newValue) -> oldValue, LinkedHashMap::new));
			
			LinkedHashMap<String, Integer> hallMap = hallCapacityForMale.entrySet()
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
			int a,b,c;
			a = studentMap.get(studentMap.keySet().toArray()[0]);
			b = studentMap.get(studentMap.keySet().toArray()[1]);
			c = studentMap.get(studentMap.keySet().toArray()[2]);
			if(a!=0 && b!=0 && c!=0) {	
				int r = (hallMap.get(hallMap.keySet().toArray()[0]))%3;
				int p = (hallMap.get(hallMap.keySet().toArray()[0]))/3;
				String x = null, y = null, z = null;
//				for (Map.Entry<String, Integer> entry : studentMap.entrySet()) {
//	                if (entry.getValue().equals(a)) {
//	                    x = entry.getKey();
//	                }
//	                if (entry.getValue().equals(b)) {
//	                    y = entry.getKey();
//	                }
//	                if (entry.getValue().equals(c)) {
//	                    z = entry.getKey();
//	                }
//	            }
				
				for (Map.Entry<String, Integer> entry : studentMap.entrySet()) {
	                if (entry.getValue().equals(a)) {
	                    x = entry.getKey();
	                }
				}
	            studentMap.remove(x);
	            for (Map.Entry<String, Integer> entry : studentMap.entrySet()) {
	                if (entry.getValue().equals(b)) {
	                    y = entry.getKey();
	                }
	            }
	            studentMap.remove(y);
	            for (Map.Entry<String, Integer> entry : studentMap.entrySet()) {
	                if (entry.getValue().equals(c)) {
	                    z = entry.getKey();
	                }
	            }
	            studentMap.remove(z);
			
				
	//			System.out.println(x + " : " + y + " : " + z + " : " + room);
				if(r==1){
					if(a>=(p+1)) {
						listOfBoysStudents.replace(x, a-(p+1));
						courseList.add(x);
						courseList.add(String.valueOf(p+1));
	//					outputMap.add(room, courseList);	
					}
					else {
						listOfBoysStudents.replace(x, 0);
						courseList.add(x);
						courseList.add(String.valueOf(a));
	//					outputMap.add(room, courseList);
					}
					if(b>=(p)) {
						listOfBoysStudents.replace(y, b-p);
						courseList.add(y);
						courseList.add(String.valueOf(p));
	//					outputMap.add(room, courseList);
					}
					else {
						listOfBoysStudents.replace(y, 0);
						courseList.add(y);
						courseList.add(String.valueOf(b));
	//					outputMap.add(room, courseList);
					}
					if(c>=(p)) {
						listOfBoysStudents.replace(z, c-p);
						courseList.add(z);
						courseList.add(String.valueOf(p));
	//					outputMap.add(room, courseList);
					}
					else {
						listOfBoysStudents.replace(z, 0);
						courseList.add(z);
						courseList.add(String.valueOf(c));
	//					outputMap.add(room, courseList);
					}		
				}
				else if(r==2){
					if(a>=(p+1)) {
						listOfBoysStudents.replace(x, a-(p+1));
						courseList.add(x);
						courseList.add(String.valueOf((p+1)));
	//					outputMap.add(room, courseList);
					}
					else {
						listOfBoysStudents.replace(x, 0);
						courseList.add(x);
						courseList.add(String.valueOf((a)));
	//					outputMap.add(room, courseList);
					}
					if(b>=(p+1)) {
						listOfBoysStudents.replace(y, b-(p+1));
						courseList.add(y);
						courseList.add(String.valueOf((p+1)));
	//					outputMap.add(room, courseList);
					}
					else {
						listOfBoysStudents.replace(y, 0);
						courseList.add(y);
						courseList.add(String.valueOf((b)));
	//					outputMap.add(room, courseList);
					}
					if(c>=(p)) {
						listOfBoysStudents.replace(z, c-p);
						courseList.add(z);
						courseList.add(String.valueOf((p)));
	//					outputMap.add(room, courseList);
					}
					else {
						listOfBoysStudents.replace(z, 0);
						courseList.add(z);
						courseList.add(String.valueOf((c)));
	//					outputMap.add(room, courseList);
					}		
				}
				else{
					if(a>=(p)) {
						listOfBoysStudents.replace(x, a-p);
						courseList.add(x);
						courseList.add(String.valueOf((p)));
	//					outputMap.add(room, courseList);
					}
					else {
						listOfBoysStudents.replace(x, 0);
						courseList.add(x);
						courseList.add(String.valueOf((a)));
	//					outputMap.add(room, courseList);
					}
					if(b>=(p)) {
						listOfBoysStudents.replace(y, b-p);
						courseList.add(y);
						courseList.add(String.valueOf((p)));
	//					outputMap.add(room, courseList);
					}
					else {
						listOfBoysStudents.replace(y, 0);
						courseList.add(y);
						courseList.add(String.valueOf((b)));
	//					outputMap.add(room, courseList);
					}
					if(c>=(p)) {
						listOfBoysStudents.replace(z, c-p);
						courseList.add(z);
						courseList.add(String.valueOf((p)));
	//					outputMap.add(room, courseList);
					}
					else {
						listOfBoysStudents.replace(z, 0);
						courseList.add(z);
						courseList.add(String.valueOf((c)));
	//					outputMap.add(room, courseList);
					}		
				}
			}
			
			else if((a!=0 && b!=0 && c==0)||(a==0 && b!=0 && c!=0)||(a!=0 && b==0 && c!=0)) {
				int r = (hallMap.get(hallMap.keySet().toArray()[0]))%2;
				int p = (hallMap.get(hallMap.keySet().toArray()[0]))/2;
				int m=0, n=0;
				if(a==0) {
					m=b;
					n=c;
				}
				else if(b==0) {
					m=a;
					n=c;
				}
				else if(c==0) {
					m=a;
					n=b;
				}
				String x = null, y = null;
				for (Map.Entry<String, Integer> entry : studentMap.entrySet()) {
	                if (entry.getValue().equals(m)) {
	                    x = entry.getKey();
	                }
				}
	            studentMap.remove(x);
	            for (Map.Entry<String, Integer> entry : studentMap.entrySet()) {
	                if (entry.getValue().equals(n)) {
	                    y = entry.getKey();
	                }
	            }
	            studentMap.remove(y);
				if(r==1){
					if(m>=(p+1)) {
						listOfBoysStudents.replace(x, m-(p+1));
						courseList.add(x);
						courseList.add(String.valueOf(p+1));
					}
					else {
						listOfBoysStudents.replace(x, 0);
						courseList.add(x);
						courseList.add(String.valueOf(m));
					}
					if(n>=(p)) {
						listOfBoysStudents.replace(y, n-p);
						courseList.add(y);
						courseList.add(String.valueOf(p));
					}
					else {
						listOfBoysStudents.replace(y, 0);
						courseList.add(y);
						courseList.add(String.valueOf(n));
					}	
				}
				else{
					if(m>=(p)) {
						listOfBoysStudents.replace(x, m-p);
						courseList.add(x);
						courseList.add(String.valueOf((p)));
					}
					else {
						listOfBoysStudents.replace(x, 0);
						courseList.add(x);
						courseList.add(String.valueOf((m)));
					}
					if(n>=(p)) {
						listOfBoysStudents.replace(y, n-p);
						courseList.add(y);
						courseList.add(String.valueOf((p)));
					}
					else {
						listOfBoysStudents.replace(y, 0);
						courseList.add(y);
						courseList.add(String.valueOf((n)));
					}
				}
			}
			else if((a!=0 && b==0 && c==0)||(a==0 && b==0 && c!=0)||(a==0 && b==0 && c!=0)) {
				int p = (hallMap.get(hallMap.keySet().toArray()[0]));
				int m=0;
				if(a!=0) {
					m=a;
				}
				else if(b!=0) {
					m=b;
				}
				else if(c!=0) {
					m=c;
				}
				String x = null;
				for (Map.Entry<String, Integer> entry : studentMap.entrySet()) {
	                if (entry.getValue().equals(m)) {
	                    x = entry.getKey();
	                }
				}
	            studentMap.remove(x);
					if(m>=(p)) {
						listOfBoysStudents.replace(x, m-p);
						courseList.add(x);
						courseList.add(String.valueOf((p)));
					}
					else {
						listOfBoysStudents.replace(x, 0);
						courseList.add(x);
						courseList.add(String.valueOf((m)));
					}
				}
			else {
				break;
			}
		
			AlgoOutputVO valueObject = new AlgoOutputVO();
			valueObject.setClassRoom(room);
			valueObject.setValues(courseList);
			outputList.add(valueObject);
//			System.out.println(hallCapacity.size());
			hallCapacityForMale.remove(room);
		
		}
		
//		while(hallCapacityForFemale.size()!=0) {
//			ArrayList<String> courseList = new ArrayList<>();
//			
//			LinkedHashMap<String, Integer> studentMap = listOfGirlsStudents.entrySet()
//		            .stream()
//		            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
//		            .collect(Collectors.toMap(
//		                Map.Entry::getKey,
//		                Map.Entry::getValue,
//		                (oldValue, newValue) -> oldValue, LinkedHashMap::new));
//			
//			LinkedHashMap<String, Integer> hallMap = hallCapacityForFemale.entrySet()
//		            .stream()
//		            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
//		            .collect(Collectors.toMap(
//		                Map.Entry::getKey,
//		                Map.Entry::getValue,
//		                (oldValue, newValue) -> oldValue, LinkedHashMap::new));
//			
//			int count;
//			count = hallMap.get(hallMap.keySet().toArray()[0]);
//			String room = null;
//			for (Map.Entry<String, Integer> entry : hallMap.entrySet()) {
//				if (entry.getValue().equals(count)) {
//					room = entry.getKey();
//				}
//			}
//			int r = (hallMap.get(hallMap.keySet().toArray()[0]))%3;
//			int p = (hallMap.get(hallMap.keySet().toArray()[0]))/3;
//			int a,b,c;
//			String x = null, y = null, z = null;
//			a = studentMap.get(studentMap.keySet().toArray()[0]);
//			b = studentMap.get(studentMap.keySet().toArray()[1]);
//			c = studentMap.get(studentMap.keySet().toArray()[2]);
//			
////			for (Map.Entry<String, Integer> entry : studentMap.entrySet()) {
////                if (entry.getValue().equals(a)) {
////                    x = entry.getKey();
////                }
////                if (entry.getValue().equals(b)) {
////                    y = entry.getKey();
////                }
////                if (entry.getValue().equals(c)) {
////                    z = entry.getKey();
////                }
////            }
//			
//			for (Map.Entry<String, Integer> entry : studentMap.entrySet()) {
//                if (entry.getValue().equals(a)) {
//                    x = entry.getKey();
//                }
//			}
//            studentMap.remove(x);
//            for (Map.Entry<String, Integer> entry : studentMap.entrySet()) {
//                if (entry.getValue().equals(b)) {
//                    y = entry.getKey();
//                }
//            }
//            studentMap.remove(y);
//            for (Map.Entry<String, Integer> entry : studentMap.entrySet()) {
//                if (entry.getValue().equals(c)) {
//                    z = entry.getKey();
//                }
//            }
//            studentMap.remove(z);
//			
////			System.out.println(x + " : " + y + " : " + z + " : " + room);
//			if(r==1){
//				if(a>=(p+1)) {
//					listOfGirlsStudents.replace(x, a-(p+1));
//					courseList.add(x);
//					courseList.add(String.valueOf(p+1));
////					outputMap.add(room, courseList);	
//				}
//				else {
//					listOfGirlsStudents.replace(x, 0);
//					courseList.add(x);
//					courseList.add(String.valueOf(a));
////					outputMap.add(room, courseList);
//				}
//				if(b>=(p+1)) {
//					listOfGirlsStudents.replace(y, b-p);
//					courseList.add(y);
//					courseList.add(String.valueOf(p));
////					outputMap.add(room, courseList);
//				}
//				else {
//					listOfGirlsStudents.replace(y, 0);
//					courseList.add(y);
//					courseList.add(String.valueOf(b));
////					outputMap.add(room, courseList);
//				}
//				if(c>=(p+1)) {
//					listOfGirlsStudents.replace(z, c-p);
//					courseList.add(z);
//					courseList.add(String.valueOf(p));
////					outputMap.add(room, courseList);
//				}
//				else {
//					listOfGirlsStudents.replace(z, 0);
//					courseList.add(z);
//					courseList.add(String.valueOf(c));
////					outputMap.add(room, courseList);
//				}		
//			}
//			else if(r==2){
//				if(a>=(p+1)) {
//					listOfGirlsStudents.replace(x, a-(p+1));
//					courseList.add(x);
//					courseList.add(String.valueOf((p+1)));
////					outputMap.add(room, courseList);
//				}
//				else {
//					listOfGirlsStudents.replace(x, 0);
//					courseList.add(x);
//					courseList.add(String.valueOf((a)));
////					outputMap.add(room, courseList);
//				}
//				if(b>=(p+1)) {
//					listOfGirlsStudents.replace(y, b-(p+1));
//					courseList.add(y);
//					courseList.add(String.valueOf((p+1)));
////					outputMap.add(room, courseList);
//				}
//				else {
//					listOfGirlsStudents.replace(y, 0);
//					courseList.add(y);
//					courseList.add(String.valueOf((b)));
////					outputMap.add(room, courseList);
//				}
//				if(c>=(p+1)) {
//					listOfGirlsStudents.replace(z, c-p);
//					courseList.add(z);
//					courseList.add(String.valueOf((p)));
////					outputMap.add(room, courseList);
//				}
//				else {
//					listOfGirlsStudents.replace(z, 0);
//					courseList.add(z);
//					courseList.add(String.valueOf((c)));
////					outputMap.add(room, courseList);
//				}		
//			}
//			else{
//				if(a>=(p+1)) {
//					listOfGirlsStudents.replace(x, a-p);
//					courseList.add(x);
//					courseList.add(String.valueOf((p)));
////					outputMap.add(room, courseList);
//				}
//				else {
//					listOfGirlsStudents.replace(x, 0);
//					courseList.add(x);
//					courseList.add(String.valueOf((a)));
////					outputMap.add(room, courseList);
//				}
//				if(b>=(p+1)) {
//					listOfGirlsStudents.replace(y, b-p);
//					courseList.add(y);
//					courseList.add(String.valueOf((p)));
////					outputMap.add(room, courseList);
//				}
//				else {
//					listOfGirlsStudents.replace(y, 0);
//					courseList.add(y);
//					courseList.add(String.valueOf((b)));
////					outputMap.add(room, courseList);
//				}
//				if(c>=(p+1)) {
//					listOfGirlsStudents.replace(z, c-p);
//					courseList.add(z);
//					courseList.add(String.valueOf((p)));
////					outputMap.add(room, courseList);
//				}
//				else {
//					listOfGirlsStudents.replace(z, 0);
//					courseList.add(z);
//					courseList.add(String.valueOf((c)));
////					outputMap.add(room, courseList);
//				}		
//			}
//			AlgoOutputVO valueObject = new AlgoOutputVO();
//			valueObject.setClassRoom(room);
//			valueObject.setValues(courseList);
//			outputList.add(valueObject);
////			outputMap.add(room, courseList);
////			System.out.println(hallCapacity.size());
//			hallCapacityForFemale.remove(room);
//		
//		}
//		
//		while(hallCapacityForMale.size()!=0) {
//			ArrayList<String> courseList = new ArrayList<>();
//			
//			LinkedHashMap<String, Integer> studentMap = listOfBoysStudents.entrySet()
//		            .stream()
//		            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
//		            .collect(Collectors.toMap(
//		                Map.Entry::getKey,
//		                Map.Entry::getValue,
//		                (oldValue, newValue) -> oldValue, LinkedHashMap::new));
//			
//			LinkedHashMap<String, Integer> hallMap = hallCapacityForMale.entrySet()
//		            .stream()
//		            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
//		            .collect(Collectors.toMap(
//		                Map.Entry::getKey,
//		                Map.Entry::getValue,
//		                (oldValue, newValue) -> oldValue, LinkedHashMap::new));
//			
//			int count;
//			count = hallMap.get(hallMap.keySet().toArray()[0]);
//			String room = null;
//			for (Map.Entry<String, Integer> entry : hallMap.entrySet()) {
//				if (entry.getValue().equals(count)) {
//					room = entry.getKey();
//				}
//			}
//			int r = (hallMap.get(hallMap.keySet().toArray()[0]))%3;
//			int p = (hallMap.get(hallMap.keySet().toArray()[0]))/3;
//			int a,b,c;
//			String x = null, y = null, z = null;
//			a = studentMap.get(studentMap.keySet().toArray()[0]);
//			b = studentMap.get(studentMap.keySet().toArray()[1]);
//			c = studentMap.get(studentMap.keySet().toArray()[2]);
//			
////			for (Map.Entry<String, Integer> entry : studentMap.entrySet()) {
////                if (entry.getValue().equals(a)) {
////                    x = entry.getKey();
////                }
////                if (entry.getValue().equals(b)) {
////                    y = entry.getKey();
////                }
////                if (entry.getValue().equals(c)) {
////                    z = entry.getKey();
////                }
////            }
//			
//			for (Map.Entry<String, Integer> entry : studentMap.entrySet()) {
//                if (entry.getValue().equals(a)) {
//                    x = entry.getKey();
//                }
//			}
//            studentMap.remove(x);
//            for (Map.Entry<String, Integer> entry : studentMap.entrySet()) {
//                if (entry.getValue().equals(b)) {
//                    y = entry.getKey();
//                }
//            }
//            studentMap.remove(y);
//            for (Map.Entry<String, Integer> entry : studentMap.entrySet()) {
//                if (entry.getValue().equals(c)) {
//                    z = entry.getKey();
//                }
//            }
//            studentMap.remove(z);
//			
////			System.out.println(x + " : " + y + " : " + z + " : " + room);
//			if(r==1){
//				if(a>=(p+1)) {
//					listOfBoysStudents.replace(x, a-(p+1));
//					courseList.add(x);
//					courseList.add(String.valueOf(p+1));
////					outputMap.add(room, courseList);	
//				}
//				else {
//					listOfBoysStudents.replace(x, 0);
//					courseList.add(x);
//					courseList.add(String.valueOf(a));
////					outputMap.add(room, courseList);
//				}
//				if(b>=(p+1)) {
//					listOfBoysStudents.replace(y, b-p);
//					courseList.add(y);
//					courseList.add(String.valueOf(p));
////					outputMap.add(room, courseList);
//				}
//				else {
//					listOfBoysStudents.replace(y, 0);
//					courseList.add(y);
//					courseList.add(String.valueOf(b));
////					outputMap.add(room, courseList);
//				}
//				if(c>=(p+1)) {
//					listOfBoysStudents.replace(z, c-p);
//					courseList.add(z);
//					courseList.add(String.valueOf(p));
////					outputMap.add(room, courseList);
//				}
//				else {
//					listOfBoysStudents.replace(z, 0);
//					courseList.add(z);
//					courseList.add(String.valueOf(c));
////					outputMap.add(room, courseList);
//				}		
//			}
//			else if(r==2){
//				if(a>=(p+1)) {
//					listOfBoysStudents.replace(x, a-(p+1));
//					courseList.add(x);
//					courseList.add(String.valueOf((p+1)));
////					outputMap.add(room, courseList);
//				}
//				else {
//					listOfBoysStudents.replace(x, 0);
//					courseList.add(x);
//					courseList.add(String.valueOf((a)));
////					outputMap.add(room, courseList);
//				}
//				if(b>=(p+1)) {
//					listOfBoysStudents.replace(y, b-(p+1));
//					courseList.add(y);
//					courseList.add(String.valueOf((p+1)));
////					outputMap.add(room, courseList);
//				}
//				else {
//					listOfBoysStudents.replace(y, 0);
//					courseList.add(y);
//					courseList.add(String.valueOf((b)));
////					outputMap.add(room, courseList);
//				}
//				if(c>=(p+1)) {
//					listOfBoysStudents.replace(z, c-p);
//					courseList.add(z);
//					courseList.add(String.valueOf((p)));
////					outputMap.add(room, courseList);
//				}
//				else {
//					listOfBoysStudents.replace(z, 0);
//					courseList.add(z);
//					courseList.add(String.valueOf((c)));
////					outputMap.add(room, courseList);
//				}		
//			}
//			else{
//				if(a>=(p+1)) {
//					listOfBoysStudents.replace(x, a-p);
//					courseList.add(x);
//					courseList.add(String.valueOf((p)));
////					outputMap.add(room, courseList);
//				}
//				else {
//					listOfBoysStudents.replace(x, 0);
//					courseList.add(x);
//					courseList.add(String.valueOf((a)));
////					outputMap.add(room, courseList);
//				}
//				if(b>=(p+1)) {
//					listOfBoysStudents.replace(y, b-p);
//					courseList.add(y);
//					courseList.add(String.valueOf((p)));
////					outputMap.add(room, courseList);
//				}
//				else {
//					listOfBoysStudents.replace(y, 0);
//					courseList.add(y);
//					courseList.add(String.valueOf((b)));
////					outputMap.add(room, courseList);
//				}
//				if(c>=(p+1)) {
//					listOfBoysStudents.replace(z, c-p);
//					courseList.add(z);
//					courseList.add(String.valueOf((p)));
////					outputMap.add(room, courseList);
//				}
//				else {
//					listOfBoysStudents.replace(z, 0);
//					courseList.add(z);
//					courseList.add(String.valueOf((c)));
////					outputMap.add(room, courseList);
//				}		
//			}
//			AlgoOutputVO valueObject = new AlgoOutputVO();
//			valueObject.setClassRoom(room);
//			valueObject.setValues(courseList);
//			outputList.add(valueObject);
////			System.out.println(hallCapacity.size());
//			hallCapacityForMale.remove(room);
//		
//		}
//		for(Entry<String, List<Object>> m  : outputMap.entrySet()){    
//            System.out.println(m.getKey()+" "+m.getValue());    
//        }
//		for(Map.Entry m : outputMap.entrySet()){    
//            System.out.println(m.getKey()+" "+m.getValue());    
//        }
//		
//		 MultiValueMap<String, List<String>>  valueMap = outputMap;
//	       Set<String> valueSet = valueMap.keySet();
//	       
//	       for(String str: valueSet) {
//               System.out.println(valueMap.get(str).get(0));
//       }
//		
//		return outputMap;
		
		
		
		return outputList;
	}
}



