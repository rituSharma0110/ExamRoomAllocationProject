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
import com.roomallocation.ExamRoomAllocation.vo.dummyHallList;

@Component
public class GenerateAlgo {
	
	public MultiValueMap<String, List<String>> generateAlgo(ArrayList<DatesheetVO> dateSheetList, ArrayList<HallDataVO> hallDataList, 
			List<StudentVO> studentList, int shiftNumber) {
		HashMap<String, Integer> listOfGirlsStudents = new HashMap<>();
		HashMap<String, Integer> listOfBoysStudents = new HashMap<>();
//		HashMap<String, Integer> listOfDHGirls = new HashMap<>();
//		HashMap<String, Integer> listOfDHBoys = new HashMap<>();
		HashMap<String, Integer> hallCapacityForMale = new HashMap<>();
		HashMap<String, Integer> hallCapacityForFemale = new HashMap<>();
//		HashMap<String, Integer> DHCapacityForMale = new HashMap<>();
//		HashMap<String, Integer> DHCapacityForFemale = new HashMap<>();
		ArrayList<dummyHallList> dummyHallDataList = new ArrayList<>();
		for(int i=0; i< hallDataList.size();i++) {
			dummyHallList var = new dummyHallList();
			var.setHallData(hallDataList.get(i));
			var.setAdded(false);
			dummyHallDataList.add(var);
		}
		for(int i = 1; i < dateSheetList.size() ; i++) {
			if(dateSheetList.get(i).getShift().equals(String.valueOf(shiftNumber))) {
				int countGirls = 0;
				int countBoys = 0;
//				int countDHGirls = 0;
//				int countDHBoys = 0;
				for(int j = 0; j<studentList.size(); j++) {
					for(int k = 0; k< studentList.get(j).getCourses().size(); k++) {
						if(studentList.get(j).getCourses().get(k).equals(dateSheetList.get(i).getSubjectCode())&&studentList.get(j).getGender().equalsIgnoreCase("m") && dateSheetList.get(i).getDrawingSubjFlag().equals("0")) {
							countBoys++;
						}
						else if(studentList.get(j).getCourses().get(k).equals(dateSheetList.get(i).getSubjectCode())&&studentList.get(j).getGender().equalsIgnoreCase("f")) {
							countGirls++;
						}	
//						else if(studentList.get(j).getCourses().get(k).equals(dateSheetList.get(i).getSubjectCode())&&studentList.get(j).getGender().equalsIgnoreCase("m") && dateSheetList.get(i).getDrawingSubjFlag().equals("1")) {
//							countDHBoys++;
//						}
//						else if(studentList.get(j).getCourses().get(k).equals(dateSheetList.get(i).getSubjectCode())&&studentList.get(j).getGender().equalsIgnoreCase("f") && dateSheetList.get(i).getDrawingSubjFlag().equals("1")) {
//							countDHGirls++;
//						}	
					}
				}
				listOfBoysStudents.put(dateSheetList.get(i).getSubjectCode(), countBoys);
				listOfGirlsStudents.put(dateSheetList.get(i).getSubjectCode(), countGirls);
//				listOfDHBoys.put(dateSheetList.get(i).getSubjectCode(), countDHBoys);
//				listOfDHGirls.put(dateSheetList.get(i).getSubjectCode(), countDHGirls);
			}
			
		}

		
		int val = 0;
		for(int i = 0; i < hallDataList.size(); i++) {
			if(hallDataList.get(i).getIsHallAvailable().substring(0,1).equalsIgnoreCase("Y")&&hallDataList.get(i).getGender().equalsIgnoreCase("m")&&hallDataList.get(i).getDrawingSeatsAvaliable().equals("0")) {
				val = Integer.valueOf(hallDataList.get(i).getCapacity());
				hallCapacityForMale.put(hallDataList.get(i).getRoomName(), val);
			}
			else if(hallDataList.get(i).getIsHallAvailable().substring(0,1).equalsIgnoreCase("Y")&&hallDataList.get(i).getGender().equalsIgnoreCase("f")) {
				val = Integer.valueOf(hallDataList.get(i).getCapacity());
				hallCapacityForFemale.put(hallDataList.get(i).getRoomName(), val);
			}
//			else if(hallDataList.get(i).getIsHallAvailable().substring(0,1).equalsIgnoreCase("Y")&&hallDataList.get(i).getGender().equalsIgnoreCase("m")&&hallDataList.get(i).getDrawingSeatsAvaliable().equals("1")) {
//				val = Integer.valueOf(hallDataList.get(i).getCapacity());
//				DHCapacityForMale.put(hallDataList.get(i).getRoomName(), val);
//			}
//			else if(hallDataList.get(i).getIsHallAvailable().substring(0,1).equalsIgnoreCase("Y")&&hallDataList.get(i).getGender().equalsIgnoreCase("f")&&hallDataList.get(i).getDrawingSeatsAvaliable().equals("1")) {
//				val = Integer.valueOf(hallDataList.get(i).getCapacity());
//				DHCapacityForFemale.put(hallDataList.get(i).getRoomName(), val);
//			}
		}
		

		MultiValueMap<String, List<String>> outputMap = new LinkedMultiValueMap<>();
		
//		while(DHCapacityForMale.size()!=0) {
//			
//			ArrayList<String> courseList = new ArrayList<>();
//			
//			LinkedHashMap<String, Integer> studentMap = listOfDHBoys.entrySet()
//		            .stream()
//		            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
//		            .collect(Collectors.toMap(
//		                Map.Entry::getKey,
//		                Map.Entry::getValue,
//		                (oldValue, newValue) -> oldValue, LinkedHashMap::new));
//			
//			LinkedHashMap<String, Integer> hallMap = DHCapacityForMale.entrySet()
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
//			int a;
//			a = studentMap.get(studentMap.keySet().toArray()[0]);
//			String x = null;
//			for (Map.Entry<String, Integer> entry : studentMap.entrySet()) {
//                if (entry.getValue().equals(a)) {
//                    x = entry.getKey();
//                }
//			}
//			if(count >= a) {
//				listOfDHBoys.replace(x, 0);
//				courseList.add(x);
//				courseList.add(String.valueOf(a));
//			}
//			else {
//				listOfDHBoys.replace(x, a-count);
//				courseList.add(x);
//				courseList.add(String.valueOf(count));
//			}
//			outputMap.add(room, courseList);
//			DHCapacityForMale.remove(room);	
//		}
		System.out.println("Loop starting...");
		boolean isDrawingGirlsAvailabe = true;
		while(hallCapacityForFemale.size()!=0) {
			ArrayList<String> courseList = new ArrayList<>();
			while(isDrawingGirlsAvailabe){
				for(int i = 1; i < dateSheetList.size() ; i++) {
					if(dateSheetList.get(i).getShift().equals(String.valueOf(shiftNumber))) {
						int value = 0;
						String room = null;
						int DHGCount=0;
						String subCode = null;
						for(int j = 0; j<studentList.size(); j++) {
							for(int k = 0; k< studentList.get(j).getCourses().size(); k++) {
								if(studentList.get(j).getCourses().get(k).equals(dateSheetList.get(i).getSubjectCode())&&studentList.get(j).getGender().equalsIgnoreCase("f") && dateSheetList.get(i).getDrawingSubjFlag().equals("1")) {
									DHGCount++;
								}	
							}
						}
						System.out.println(DHGCount);
						if(DHGCount!=0) {
							subCode=dateSheetList.get(i).getSubjectCode();
							int index=0;
							while(DHGCount!=0) {
								ArrayList<String> courseDHList = new ArrayList<>();
								for(int m = 0; m < dummyHallDataList.size(); m++) {
									if(dummyHallDataList.get(m).getHallData().getIsHallAvailable().substring(0,1).equalsIgnoreCase("Y")&&dummyHallDataList.get(m).getHallData().getGender().equalsIgnoreCase("f")&&dummyHallDataList.get(m).getHallData().getDrawingSeatsAvaliable().equals("1")&&!dummyHallDataList.get(m).isAdded()) {
										value = Integer.valueOf(dummyHallDataList.get(m).getHallData().getCapacity());
										room = dummyHallDataList.get(m).getHallData().getRoomName();
										index=m;
									}
								}
								System.out.println("Room : " + room + " has " + value + " capacity");
								if(DHGCount>=value) {
									listOfGirlsStudents.replace(subCode, DHGCount-value);
									courseDHList.add(subCode);
									courseDHList.add(String.valueOf(value));
									outputMap.add(room, courseDHList);
									hallCapacityForFemale.remove(room);
									dummyHallDataList.get(index).setAdded(true);
									DHGCount-=value;
								}
								else if (DHGCount<value){
									listOfGirlsStudents.remove(subCode);
									courseDHList.add(subCode);
									courseDHList.add(String.valueOf(DHGCount));
									outputMap.add(room, courseDHList);
									hallCapacityForFemale.replace(room, value-DHGCount);
									dummyHallDataList.get(index).getHallData().setCapacity(String.valueOf(value-DHGCount));
									DHGCount=0;
								}
							}
						}
//						else
//							continue;
				}
					
			}
			isDrawingGirlsAvailabe = false;

			System.out.print("In Loop...");
		}

			System.out.print("Loop ending...");
			
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
			int a,b,c,d;
			a = studentMap.get(studentMap.keySet().toArray()[0]);
			b = studentMap.get(studentMap.keySet().toArray()[1]);
			c = studentMap.get(studentMap.keySet().toArray()[2]);
			d = studentMap.get(studentMap.keySet().toArray()[3]);
			String remain = null;
			
			for (Map.Entry<String, Integer> entry : studentMap.entrySet()) {
                if (entry.getValue().equals(d)) {
                	remain = entry.getKey();
                }
            }
            studentMap.remove(remain);
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
						count-=(p+1);
					}
					else {
						listOfGirlsStudents.replace(x, 0);
						courseList.add(x);
						courseList.add(String.valueOf(a));
						count-=a;
					}
					if(b>=(p)) {
						listOfGirlsStudents.replace(y, b-p);
						courseList.add(y);
						courseList.add(String.valueOf(p));
						count-=p;
					}
					else {
						listOfGirlsStudents.replace(y, 0);
						courseList.add(y);
						courseList.add(String.valueOf(b));
						count-=b;
					}
					if(c>=(p)) {
						listOfGirlsStudents.replace(z, c-p);
						courseList.add(z);
						courseList.add(String.valueOf(p));
						count-=p;
					}
					else {
						listOfGirlsStudents.replace(z, 0);
						courseList.add(z);
						courseList.add(String.valueOf(c));
						count-=c;
					}		
				}
				else if(r==2){
					if(a>=(p+1)) {
						listOfGirlsStudents.replace(x, a-(p+1));
						courseList.add(x);
						courseList.add(String.valueOf((p+1)));
						count-=(p+1);
					}
					else {
						listOfGirlsStudents.replace(x, 0);
						courseList.add(x);
						courseList.add(String.valueOf((a)));
						count-=a;
					}
					if(b>=(p+1)) {
						listOfGirlsStudents.replace(y, b-(p+1));
						courseList.add(y);
						courseList.add(String.valueOf((p+1)));
						count-=(p+1);
					}
					else {
						listOfGirlsStudents.replace(y, 0);
						courseList.add(y);
						courseList.add(String.valueOf((b)));
						count-=b;
					}
					if(c>=(p)) {
						listOfGirlsStudents.replace(z, c-p);
						courseList.add(z);
						courseList.add(String.valueOf((p)));
						count-=p;
					}
					else {
						listOfGirlsStudents.replace(z, 0);
						courseList.add(z);
						courseList.add(String.valueOf((c)));
						count-=c;
					}		
				}
				else{
					if(a>=(p)) {
						listOfGirlsStudents.replace(x, a-p);
						courseList.add(x);
						courseList.add(String.valueOf((p)));
						count-=p;
					}
					else {
						listOfGirlsStudents.replace(x, 0);
						courseList.add(x);
						courseList.add(String.valueOf((a)));
						count-=a;
					}
					if(b>=(p)) {
						listOfGirlsStudents.replace(y, b-p);
						courseList.add(y);
						courseList.add(String.valueOf((p)));
						count-=p;
					}
					else {
						listOfGirlsStudents.replace(y, 0);
						courseList.add(y);
						courseList.add(String.valueOf((b)));
						count-=b;
					}
					if(c>=(p)) {
						listOfGirlsStudents.replace(z, c-p);
						courseList.add(z);
						courseList.add(String.valueOf((p)));
						count-=p;
					}
					else {
						listOfGirlsStudents.replace(z, 0);
						courseList.add(z);
						courseList.add(String.valueOf((c)));
						count-=c;
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
						count-=(p+1);
					}
					else {
						listOfGirlsStudents.replace(x, 0);
						courseList.add(x);
						courseList.add(String.valueOf(m));
						count-=m;
					}
					if(n>=(p)) {
						listOfGirlsStudents.replace(y, n-p);
						courseList.add(y);
						courseList.add(String.valueOf(p));
						count-=p;
					}
					else {
						listOfGirlsStudents.replace(y, 0);
						courseList.add(y);
						courseList.add(String.valueOf(n));
						count-=n;
					}	
				}
				else{
					if(m>=(p)) {
						listOfGirlsStudents.replace(x, m-p);
						courseList.add(x);
						courseList.add(String.valueOf((p)));
						count-=p;
					}
					else {
						listOfGirlsStudents.replace(x, 0);
						courseList.add(x);
						courseList.add(String.valueOf((m)));
						count-=m;
					}
					if(n>=(p)) {
						listOfGirlsStudents.replace(y, n-p);
						courseList.add(y);
						courseList.add(String.valueOf((p)));
						count-=p;
					}
					else {
						listOfGirlsStudents.replace(y, 0);
						courseList.add(y);
						courseList.add(String.valueOf((n)));
						count-=n;
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
						count-=p;
					}
					else {
						listOfGirlsStudents.replace(x, 0);
						courseList.add(x);
						courseList.add(String.valueOf((m)));
						count-=m;
					}
				}
			else {
				break;
			}
//			outputMap.add(room, courseList);
//			if(count==0) {
//				hallCapacityForFemale.remove(room);
//			}
//			else {
//				hallCapacityForFemale.replace(room, count);	
//			}
			
			if(count!=0) {
				if(d>=count) {
					listOfGirlsStudents.replace(remain, d-count);
					courseList.add(remain);
					courseList.add(String.valueOf((count)));
				}
				else
				{
					listOfGirlsStudents.replace(remain, 0);
					courseList.add(remain);
					courseList.add(String.valueOf((d)));
					count-=d;
				}
		
			}

			outputMap.add(room, courseList);
			hallCapacityForFemale.remove(room);

		}
		
		
		boolean isDrawingBoyssAvailabe = true;
		while(hallCapacityForMale.size()!=0) {
			ArrayList<String> courseList = new ArrayList<>();
			while(isDrawingBoyssAvailabe){
				for(int i = 1; i < dateSheetList.size() ; i++) {
					if(dateSheetList.get(i).getShift().equals(String.valueOf(shiftNumber))) {
						int value = 0;
						String room = null;
						int DHBCount=0;
						String subCode = null;
						for(int j = 0; j<studentList.size(); j++) {
							for(int k = 0; k< studentList.get(j).getCourses().size(); k++) {
								if(studentList.get(j).getCourses().get(k).equals(dateSheetList.get(i).getSubjectCode())&&studentList.get(j).getGender().equalsIgnoreCase("m") && dateSheetList.get(i).getDrawingSubjFlag().equals("1")) {
									DHBCount++;
								}	
							}
						}
						System.out.println(DHBCount);
						if(DHBCount!=0) {
							subCode=dateSheetList.get(i).getSubjectCode();
							int index=0;
							while(DHBCount!=0) {
								ArrayList<String> courseDHList = new ArrayList<>();
								for(int m = 0; m < dummyHallDataList.size(); m++) {
									if(dummyHallDataList.get(m).getHallData().getIsHallAvailable().substring(0,1).equalsIgnoreCase("Y")&&dummyHallDataList.get(m).getHallData().getGender().equalsIgnoreCase("m")&&dummyHallDataList.get(m).getHallData().getDrawingSeatsAvaliable().equals("1")&&!dummyHallDataList.get(m).isAdded()) {
										value = Integer.valueOf(dummyHallDataList.get(m).getHallData().getCapacity());
										room = dummyHallDataList.get(m).getHallData().getRoomName();
										index=m;
									}
								}
								System.out.println("Room : " + room + " has " + value + " capacity");
								if(DHBCount>=value) {
									listOfBoysStudents.replace(subCode, DHBCount-value);
									courseDHList.add(subCode);
									courseDHList.add(String.valueOf(value));
									outputMap.add(room, courseDHList);
									hallCapacityForMale.remove(room);
									dummyHallDataList.get(index).setAdded(true);
									DHBCount-=value;
								}
								else if (DHBCount<value){
									listOfBoysStudents.remove(subCode);
									courseDHList.add(subCode);
									courseDHList.add(String.valueOf(DHBCount));
									outputMap.add(room, courseDHList);
									hallCapacityForMale.replace(room, value-DHBCount);
									dummyHallDataList.get(index).getHallData().setCapacity(String.valueOf(value-DHBCount));
									DHBCount=0;
								}
							}
						}
//						else
//							continue;
				}
					
			}
				isDrawingBoyssAvailabe = false;

			System.out.print("In Loop...");
		}

			System.out.print("Loop ending...");
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
			int a,b,c,d;
			a = studentMap.get(studentMap.keySet().toArray()[0]);
			b = studentMap.get(studentMap.keySet().toArray()[1]);
			c = studentMap.get(studentMap.keySet().toArray()[2]);
			d = studentMap.get(studentMap.keySet().toArray()[3]);
			String remain = null;
			
			for (Map.Entry<String, Integer> entry : studentMap.entrySet()) {
                if (entry.getValue().equals(d)) {
                	remain = entry.getKey();
                }
            }
            studentMap.remove(remain);
			
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
	            if(x.equals("OMH281")) {
					System.out.println("OMH Students: "+listOfBoysStudents.get(x));
				}
	            if(y.equals("OMH281")) {
					System.out.println("OMH Students: "+listOfBoysStudents.get(y));
				}
	            if(z.equals("OMH281")) {
					System.out.println("OMH Students: "+listOfBoysStudents.get(z));
				}
				
//				System.out.println(x + " : " + y + " : " + z + " : " + room);
				if(r==1){
					if(a>=(p+1)) {
						listOfBoysStudents.replace(x, a-(p+1));
						courseList.add(x);
						courseList.add(String.valueOf(p+1));
	//					outputMap.add(room, courseList);	
						count-=(p+1);
					}
					else {
						listOfBoysStudents.replace(x, 0);
						courseList.add(x);
						courseList.add(String.valueOf(a));
	//					outputMap.add(room, courseList);
						count-=a;
					}
					if(b>=(p)) {
						listOfBoysStudents.replace(y, b-p);
						courseList.add(y);
						courseList.add(String.valueOf(p));
	//					outputMap.add(room, courseList);
						count-=p;
					}
					else {
						listOfBoysStudents.replace(y, 0);
						courseList.add(y);
						courseList.add(String.valueOf(b));
	//					outputMap.add(room, courseList);
						count-=b;
					}
					if(c>=(p)) {
						listOfBoysStudents.replace(z, c-p);
						courseList.add(z);
						courseList.add(String.valueOf(p));
	//					outputMap.add(room, courseList);
						count-=p;
					}
					else {
						listOfBoysStudents.replace(z, 0);
						courseList.add(z);
						courseList.add(String.valueOf(c));
	//					outputMap.add(room, courseList);
						count-=c;
					}		
				}
				else if(r==2){
					if(a>=(p+1)) {
						listOfBoysStudents.replace(x, a-(p+1));
						courseList.add(x);
						courseList.add(String.valueOf((p+1)));
	//					outputMap.add(room, courseList);
						count-=(p+1);
					}
					else {
						listOfBoysStudents.replace(x, 0);
						courseList.add(x);
						courseList.add(String.valueOf((a)));
	//					outputMap.add(room, courseList);
						count-=a;
					}
					if(b>=(p+1)) {
						listOfBoysStudents.replace(y, b-(p+1));
						courseList.add(y);
						courseList.add(String.valueOf((p+1)));
	//					outputMap.add(room, courseList);
						count-=(p+1);
					}
					else {
						listOfBoysStudents.replace(y, 0);
						courseList.add(y);
						courseList.add(String.valueOf((b)));
	//					outputMap.add(room, courseList);
						count-=b;
					}
					if(c>=(p)) {
						listOfBoysStudents.replace(z, c-p);
						courseList.add(z);
						courseList.add(String.valueOf((p)));
	//					outputMap.add(room, courseList);
						count-=p;
					}
					else {
						listOfBoysStudents.replace(z, 0);
						courseList.add(z);
						courseList.add(String.valueOf((c)));
	//					outputMap.add(room, courseList);
						count-=c;
					}		
				}
				else{
					if(a>=(p)) {
						listOfBoysStudents.replace(x, a-p);
						courseList.add(x);
						courseList.add(String.valueOf((p)));
	//					outputMap.add(room, courseList);
						count-=p;
					}
					else {
						listOfBoysStudents.replace(x, 0);
						courseList.add(x);
						courseList.add(String.valueOf((a)));
	//					outputMap.add(room, courseList);
						count-=a;
					}
					if(b>=(p)) {
						listOfBoysStudents.replace(y, b-p);
						courseList.add(y);
						courseList.add(String.valueOf((p)));
	//					outputMap.add(room, courseList);
						count-=p;
					}
					else {
						listOfBoysStudents.replace(y, 0);
						courseList.add(y);
						courseList.add(String.valueOf((b)));
	//					outputMap.add(room, courseList);
						count-=b;
					}
					if(c>=(p)) {
						listOfBoysStudents.replace(z, c-p);
						courseList.add(z);
						courseList.add(String.valueOf((p)));
	//					outputMap.add(room, courseList);
						count-=p;
					}
					else {
						listOfBoysStudents.replace(z, 0);
						courseList.add(z);
						courseList.add(String.valueOf((c)));
	//					outputMap.add(room, courseList);
						count-=c;
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
						count-=(p+1);
					}
					else {
						listOfBoysStudents.replace(x, 0);
						courseList.add(x);
						courseList.add(String.valueOf(m));
						count-=m;
					}
					if(n>=(p)) {
						listOfBoysStudents.replace(y, n-p);
						courseList.add(y);
						courseList.add(String.valueOf(p));
						count-=p;
					}
					else {
						listOfBoysStudents.replace(y, 0);
						courseList.add(y);
						courseList.add(String.valueOf(n));
						count-=n;
					}	
				}
				else{
					if(m>=(p)) {
						listOfBoysStudents.replace(x, m-p);
						courseList.add(x);
						courseList.add(String.valueOf((p)));
						count-=p;
					}
					else {
						listOfBoysStudents.replace(x, 0);
						courseList.add(x);
						courseList.add(String.valueOf((m)));
						count-=m;
					}
					if(n>=(p)) {
						listOfBoysStudents.replace(y, n-p);
						courseList.add(y);
						courseList.add(String.valueOf((p)));
						count-=p;
					}
					else {
						listOfBoysStudents.replace(y, 0);
						courseList.add(y);
						courseList.add(String.valueOf((n)));
						count-=n;
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
						count-=p;
					}
					else {
						listOfBoysStudents.replace(x, 0);
						courseList.add(x);
						courseList.add(String.valueOf((m)));
						count-=m;
					}
				}
			else {
				break;
			}
//			if(count!=0) {
//				if(d>=count) {
//				listOfBoysStudents.replace(remain, d-count);
//				courseList.add(remain);
//				courseList.add(String.valueOf((count)));
//				outputMap.add(room, courseList);
//				hallCapacityForMale.remove(room);	
//				}
//
//				else
//				{
//					listOfBoysStudents.replace(remain, 0);
//					courseList.add(remain);
//					courseList.add(String.valueOf((d)));
//					count-=d;
//					outputMap.add(room, courseList);
//					hallCapacityForMale.replace(room, count);
//				}
//		
//			}
//			else {
//			outputMap.add(room, courseList);
//			hallCapacityForMale.remove(room);
//			}
				if(count!=0) {
					if(d>=count) {
					listOfBoysStudents.replace(remain, d-count);
					courseList.add(remain);
					courseList.add(String.valueOf((count)));	
					}

					else
					{
						listOfBoysStudents.replace(remain, 0);
						courseList.add(remain);
						courseList.add(String.valueOf((d)));
						count-=d;
					}
			
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
			List<StudentVO> studentList, int shiftNumber) {
		
		HashMap<String, Integer> listOfGirlsStudents = new HashMap<>();
		HashMap<String, Integer> listOfBoysStudents = new HashMap<>();
		HashMap<String, Integer> listOfDHGirls = new HashMap<>();
		HashMap<String, Integer> listOfDHBoys = new HashMap<>();
		HashMap<String, Integer> hallCapacityForMale = new HashMap<>();
		HashMap<String, Integer> hallCapacityForFemale = new HashMap<>();
		HashMap<String, Integer> DHCapacityForMale = new HashMap<>();
		HashMap<String, Integer> DHCapacityForFemale = new HashMap<>();
		for(int i = 1; i < dateSheetList.size() ; i++) {
			if(dateSheetList.get(i).getShift().equals(String.valueOf(shiftNumber))) {
				int countGirls = 0;
				int countBoys = 0;
				int countDHGirls = 0;
				int countDHBoys = 0;
				for(int j = 1; j<studentList.size(); j++) {
					for(int k = 1; k< studentList.get(j).getCourses().size(); k++) {
						if(studentList.get(j).getCourses().get(k).equals(dateSheetList.get(i).getSubjectCode())&&studentList.get(j).getGender().equalsIgnoreCase("m") && dateSheetList.get(i).getDrawingSubjFlag().equals("0")) {
							countBoys++;
						}
						else if(studentList.get(j).getCourses().get(k).equals(dateSheetList.get(i).getSubjectCode())&&studentList.get(j).getGender().equalsIgnoreCase("f") && dateSheetList.get(i).getDrawingSubjFlag().equals("0")) {
							countGirls++;
						}	
						else if(studentList.get(j).getCourses().get(k).equals(dateSheetList.get(i).getSubjectCode())&&studentList.get(j).getGender().equalsIgnoreCase("m") && dateSheetList.get(i).getDrawingSubjFlag().equals("1")) {
							countDHBoys++;
						}
						else if(studentList.get(j).getCourses().get(k).equals(dateSheetList.get(i).getSubjectCode())&&studentList.get(j).getGender().equalsIgnoreCase("f") && dateSheetList.get(i).getDrawingSubjFlag().equals("1")) {
							countDHGirls++;
						}	
					}
				}
				listOfBoysStudents.put(dateSheetList.get(i).getSubjectCode(), countBoys);
				listOfGirlsStudents.put(dateSheetList.get(i).getSubjectCode(), countGirls);
				listOfDHBoys.put(dateSheetList.get(i).getSubjectCode(), countDHBoys);
				listOfDHGirls.put(dateSheetList.get(i).getSubjectCode(), countDHGirls);
			}
			
		}

		
		int val = 0;
		for(int i = 0; i < hallDataList.size(); i++) {
			if(hallDataList.get(i).getIsHallAvailable().substring(0,1).equalsIgnoreCase("Y")&&hallDataList.get(i).getGender().equalsIgnoreCase("m")&&hallDataList.get(i).getDrawingSeatsAvaliable().equals("0")) {
				val = Integer.valueOf(hallDataList.get(i).getCapacity());
				hallCapacityForMale.put(hallDataList.get(i).getRoomName(), val);
			}
			else if(hallDataList.get(i).getIsHallAvailable().substring(0,1).equalsIgnoreCase("Y")&&hallDataList.get(i).getGender().equalsIgnoreCase("f")&&hallDataList.get(i).getDrawingSeatsAvaliable().equals("0")) {
				val = Integer.valueOf(hallDataList.get(i).getCapacity());
				hallCapacityForFemale.put(hallDataList.get(i).getRoomName(), val);
			}
			else if(hallDataList.get(i).getIsHallAvailable().substring(0,1).equalsIgnoreCase("Y")&&hallDataList.get(i).getGender().equalsIgnoreCase("m")&&hallDataList.get(i).getDrawingSeatsAvaliable().equals("1")) {
				val = Integer.valueOf(hallDataList.get(i).getCapacity());
				DHCapacityForMale.put(hallDataList.get(i).getRoomName(), val);
			}
			else if(hallDataList.get(i).getIsHallAvailable().substring(0,1).equalsIgnoreCase("Y")&&hallDataList.get(i).getGender().equalsIgnoreCase("f")&&hallDataList.get(i).getDrawingSeatsAvaliable().equals("1")) {
				val = Integer.valueOf(hallDataList.get(i).getCapacity());
				DHCapacityForFemale.put(hallDataList.get(i).getRoomName(), val);
			}
		}
		



//		MultiValueMap<String, List<String>> outputMap = new LinkedMultiValueMap<>();
		ArrayList<AlgoOutputVO> outputList = new ArrayList<>();
		
while(DHCapacityForFemale.size()!=0) {
			
			ArrayList<String> courseList = new ArrayList<>();
			
			LinkedHashMap<String, Integer> studentMap = listOfDHGirls.entrySet()
		            .stream()
		            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
		            .collect(Collectors.toMap(
		                Map.Entry::getKey,
		                Map.Entry::getValue,
		                (oldValue, newValue) -> oldValue, LinkedHashMap::new));
			
			LinkedHashMap<String, Integer> hallMap = DHCapacityForFemale.entrySet()
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
			int a;
			a = studentMap.get(studentMap.keySet().toArray()[0]);
			String x = null;
			for (Map.Entry<String, Integer> entry : studentMap.entrySet()) {
                if (entry.getValue().equals(a)) {
                    x = entry.getKey();
                }
			}
			if(count >= a) {
				listOfDHGirls.replace(x, 0);
				courseList.add(x);
				courseList.add(String.valueOf(a));
			}
			else {
				listOfDHGirls.replace(x, a-count);
				courseList.add(x);
				courseList.add(String.valueOf(count));
			}
			AlgoOutputVO valueObject = new AlgoOutputVO();
			valueObject.setClassRoom(room);
			valueObject.setValues(courseList);
			outputList.add(valueObject);
//			System.out.println(hallCapacity.size());
			DHCapacityForFemale.remove(room);	
		}
		
		while(DHCapacityForMale.size()!=0) {
			
			ArrayList<String> courseList = new ArrayList<>();
			
			LinkedHashMap<String, Integer> studentMap = listOfDHBoys.entrySet()
		            .stream()
		            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
		            .collect(Collectors.toMap(
		                Map.Entry::getKey,
		                Map.Entry::getValue,
		                (oldValue, newValue) -> oldValue, LinkedHashMap::new));
			
			LinkedHashMap<String, Integer> hallMap = DHCapacityForMale.entrySet()
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
			int a;
			a = studentMap.get(studentMap.keySet().toArray()[0]);
			String x = null;
			for (Map.Entry<String, Integer> entry : studentMap.entrySet()) {
                if (entry.getValue().equals(a)) {
                    x = entry.getKey();
                }
			}
			if(count >= a) {
				listOfDHBoys.replace(x, 0);
				courseList.add(x);
				courseList.add(String.valueOf(a));
			}
			else {
				listOfDHBoys.replace(x, a-count);
				courseList.add(x);
				courseList.add(String.valueOf(count));
			}
			AlgoOutputVO valueObject = new AlgoOutputVO();
			valueObject.setClassRoom(room);
			valueObject.setValues(courseList);
			outputList.add(valueObject);
//			System.out.println(hallCapacity.size());
			DHCapacityForMale.remove(room);	
		}
		
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
			int a,b,c,d;
			a = studentMap.get(studentMap.keySet().toArray()[0]);
			b = studentMap.get(studentMap.keySet().toArray()[1]);
			c = studentMap.get(studentMap.keySet().toArray()[2]);
			d = studentMap.get(studentMap.keySet().toArray()[3]);
			String remain = null;
			
			for (Map.Entry<String, Integer> entry : studentMap.entrySet()) {
                if (entry.getValue().equals(d)) {
                	remain = entry.getKey();
                }
            }
            studentMap.remove(remain);
			
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
						count-=(p+1);
					}
					else {
						listOfGirlsStudents.replace(x, 0);
						courseList.add(x);
						courseList.add(String.valueOf(a));
						count-=a;
					}
					if(b>=(p)) {
						listOfGirlsStudents.replace(y, b-p);
						courseList.add(y);
						courseList.add(String.valueOf(p));
						count-=p;
					}
					else {
						listOfGirlsStudents.replace(y, 0);
						courseList.add(y);
						courseList.add(String.valueOf(b));
						count-=b;
					}
					if(c>=(p)) {
						listOfGirlsStudents.replace(z, c-p);
						courseList.add(z);
						courseList.add(String.valueOf(p));
						count-=p;
					}
					else {
						listOfGirlsStudents.replace(z, 0);
						courseList.add(z);
						courseList.add(String.valueOf(c));
						count-=c;
					}		
				}
				else if(r==2){
					if(a>=(p+1)) {
						listOfGirlsStudents.replace(x, a-(p+1));
						courseList.add(x);
						courseList.add(String.valueOf((p+1)));
						count-=(p+1);
					}
					else {
						listOfGirlsStudents.replace(x, 0);
						courseList.add(x);
						courseList.add(String.valueOf((a)));
						count-=a;
					}
					if(b>=(p+1)) {
						listOfGirlsStudents.replace(y, b-(p+1));
						courseList.add(y);
						courseList.add(String.valueOf((p+1)));
						count-=(p+1);
					}
					else {
						listOfGirlsStudents.replace(y, 0);
						courseList.add(y);
						courseList.add(String.valueOf((b)));
						count-=b;
					}
					if(c>=(p)) {
						listOfGirlsStudents.replace(z, c-p);
						courseList.add(z);
						courseList.add(String.valueOf((p)));
						count-=p;
					}
					else {
						listOfGirlsStudents.replace(z, 0);
						courseList.add(z);
						courseList.add(String.valueOf((c)));
						count-=c;
					}		
				}
				else{
					if(a>=(p)) {
						listOfGirlsStudents.replace(x, a-p);
						courseList.add(x);
						courseList.add(String.valueOf((p)));
						count-=p;
					}
					else {
						listOfGirlsStudents.replace(x, 0);
						courseList.add(x);
						courseList.add(String.valueOf((a)));
						count-=a;
					}
					if(b>=(p)) {
						listOfGirlsStudents.replace(y, b-p);
						courseList.add(y);
						courseList.add(String.valueOf((p)));
						count-=p;
					}
					else {
						listOfGirlsStudents.replace(y, 0);
						courseList.add(y);
						courseList.add(String.valueOf((b)));
						count-=b;
					}
					if(c>=(p)) {
						listOfGirlsStudents.replace(z, c-p);
						courseList.add(z);
						courseList.add(String.valueOf((p)));
						count-=p;
					}
					else {
						listOfGirlsStudents.replace(z, 0);
						courseList.add(z);
						courseList.add(String.valueOf((c)));
						count-=c;
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
						count-=(p+1);
					}
					else {
						listOfGirlsStudents.replace(x, 0);
						courseList.add(x);
						courseList.add(String.valueOf(m));
						count-=m;
					}
					if(n>=(p)) {
						listOfGirlsStudents.replace(y, n-p);
						courseList.add(y);
						courseList.add(String.valueOf(p));
						count-=p;
					}
					else {
						listOfGirlsStudents.replace(y, 0);
						courseList.add(y);
						courseList.add(String.valueOf(n));
						count-=n;
					}	
				}
				else{
					if(m>=(p)) {
						listOfGirlsStudents.replace(x, m-p);
						courseList.add(x);
						courseList.add(String.valueOf((p)));
						count-=p;
					}
					else {
						listOfGirlsStudents.replace(x, 0);
						courseList.add(x);
						courseList.add(String.valueOf((m)));
						count-=m;
					}
					if(n>=(p)) {
						listOfGirlsStudents.replace(y, n-p);
						courseList.add(y);
						courseList.add(String.valueOf((p)));
						count-=p;
					}
					else {
						listOfGirlsStudents.replace(y, 0);
						courseList.add(y);
						courseList.add(String.valueOf((n)));
						count-=n;
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
						count-=p;
					}
					else {
						listOfGirlsStudents.replace(x, 0);
						courseList.add(x);
						courseList.add(String.valueOf((m)));
						count-=m;
					}
				}
			else {
				break;
			}
			AlgoOutputVO valueObject = new AlgoOutputVO();
			valueObject.setClassRoom(room);
//			valueObject.setValues(courseList);
//			outputList.add(valueObject);
////			System.out.println(hallCapacity.size());
//			if(count==0) {
//				hallCapacityForFemale.remove(room);
//			}
//			else{
//				hallCapacityForFemale.replace(room, count);
//			}
			if(count!=0) {
				if(d>=count) {
					listOfGirlsStudents.replace(remain, d-count);
					courseList.add(remain);
					courseList.add(String.valueOf((count)));
				}
				else
				{
					listOfGirlsStudents.replace(remain, 0);
					courseList.add(remain);
					courseList.add(String.valueOf((d)));
					count-=d;
				}
		
			}
			valueObject.setValues(courseList);
			outputList.add(valueObject);
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
			int a,b,c,d;
			a = studentMap.get(studentMap.keySet().toArray()[0]);
			b = studentMap.get(studentMap.keySet().toArray()[1]);
			c = studentMap.get(studentMap.keySet().toArray()[2]);
			d = studentMap.get(studentMap.keySet().toArray()[3]);
			String remain = null;
			
			for (Map.Entry<String, Integer> entry : studentMap.entrySet()) {
                if (entry.getValue().equals(d)) {
                	remain = entry.getKey();
                }
            }
            studentMap.remove(remain);
			
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
						count-=(p+1);
					}
					else {
						listOfBoysStudents.replace(x, 0);
						courseList.add(x);
						courseList.add(String.valueOf(a));
	//					outputMap.add(room, courseList);
						count-=a;
					}
					if(b>=(p)) {
						listOfBoysStudents.replace(y, b-p);
						courseList.add(y);
						courseList.add(String.valueOf(p));
	//					outputMap.add(room, courseList);
						count-=p;
					}
					else {
						listOfBoysStudents.replace(y, 0);
						courseList.add(y);
						courseList.add(String.valueOf(b));
	//					outputMap.add(room, courseList);
						count-=b;
					}
					if(c>=(p)) {
						listOfBoysStudents.replace(z, c-p);
						courseList.add(z);
						courseList.add(String.valueOf(p));
	//					outputMap.add(room, courseList);
						count-=p;
					}
					else {
						listOfBoysStudents.replace(z, 0);
						courseList.add(z);
						courseList.add(String.valueOf(c));
	//					outputMap.add(room, courseList);
						count-=c;
					}		
				}
				else if(r==2){
					if(a>=(p+1)) {
						listOfBoysStudents.replace(x, a-(p+1));
						courseList.add(x);
						courseList.add(String.valueOf((p+1)));
	//					outputMap.add(room, courseList);
						count-=(p+1);
					}
					else {
						listOfBoysStudents.replace(x, 0);
						courseList.add(x);
						courseList.add(String.valueOf((a)));
	//					outputMap.add(room, courseList);
						count-=a;
					}
					if(b>=(p+1)) {
						listOfBoysStudents.replace(y, b-(p+1));
						courseList.add(y);
						courseList.add(String.valueOf((p+1)));
	//					outputMap.add(room, courseList);
						count-=(p+1);
					}
					else {
						listOfBoysStudents.replace(y, 0);
						courseList.add(y);
						courseList.add(String.valueOf((b)));
	//					outputMap.add(room, courseList);
						count-=b;
					}
					if(c>=(p)) {
						listOfBoysStudents.replace(z, c-p);
						courseList.add(z);
						courseList.add(String.valueOf((p)));
	//					outputMap.add(room, courseList);
						count-=p;
					}
					else {
						listOfBoysStudents.replace(z, 0);
						courseList.add(z);
						courseList.add(String.valueOf((c)));
	//					outputMap.add(room, courseList);
						count-=c;
					}		
				}
				else{
					if(a>=(p)) {
						listOfBoysStudents.replace(x, a-p);
						courseList.add(x);
						courseList.add(String.valueOf((p)));
	//					outputMap.add(room, courseList);
						count-=p;
					}
					else {
						listOfBoysStudents.replace(x, 0);
						courseList.add(x);
						courseList.add(String.valueOf((a)));
	//					outputMap.add(room, courseList);
						count-=a;
					}
					if(b>=(p)) {
						listOfBoysStudents.replace(y, b-p);
						courseList.add(y);
						courseList.add(String.valueOf((p)));
	//					outputMap.add(room, courseList);
						count-=p;
					}
					else {
						listOfBoysStudents.replace(y, 0);
						courseList.add(y);
						courseList.add(String.valueOf((b)));
	//					outputMap.add(room, courseList);
						count-=b;
					}
					if(c>=(p)) {
						listOfBoysStudents.replace(z, c-p);
						courseList.add(z);
						courseList.add(String.valueOf((p)));
	//					outputMap.add(room, courseList);
						count-=p;
					}
					else {
						listOfBoysStudents.replace(z, 0);
						courseList.add(z);
						courseList.add(String.valueOf((c)));
	//					outputMap.add(room, courseList);
						count-=c;
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
						count-=(p+1);
					}
					else {
						listOfBoysStudents.replace(x, 0);
						courseList.add(x);
						courseList.add(String.valueOf(m));
						count-=m;
					}
					if(n>=(p)) {
						listOfBoysStudents.replace(y, n-p);
						courseList.add(y);
						courseList.add(String.valueOf(p));
						count-=p;
					}
					else {
						listOfBoysStudents.replace(y, 0);
						courseList.add(y);
						courseList.add(String.valueOf(n));
						count-=n;
					}	
				}
				else{
					if(m>=(p)) {
						listOfBoysStudents.replace(x, m-p);
						courseList.add(x);
						courseList.add(String.valueOf((p)));
						count-=p;
					}
					else {
						listOfBoysStudents.replace(x, 0);
						courseList.add(x);
						courseList.add(String.valueOf((m)));
						count-=m;
					}
					if(n>=(p)) {
						listOfBoysStudents.replace(y, n-p);
						courseList.add(y);
						courseList.add(String.valueOf((p)));
						count-=p;
					}
					else {
						listOfBoysStudents.replace(y, 0);
						courseList.add(y);
						courseList.add(String.valueOf((n)));
						count-=n;
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
						count-=p;
					}
					else {
						listOfBoysStudents.replace(x, 0);
						courseList.add(x);
						courseList.add(String.valueOf((m)));
						count-=m;
					}
				}
			else {
				break;
			}	
			
			AlgoOutputVO valueObject = new AlgoOutputVO();
			valueObject.setClassRoom(room);
//			valueObject.setValues(courseList);
//			outputList.add(valueObject);
////			System.out.println(hallCapacity.size());
//			if(count==0) {
//				hallCapacityForMale.remove(room);
//			}
//			else {
//				hallCapacityForMale.replace(room, count);
//			}
			if(count!=0) {
				if(d>=count) {
					listOfBoysStudents.replace(remain, d-count);
					courseList.add(remain);
					courseList.add(String.valueOf((count)));	
				}
				else
				{
					listOfBoysStudents.replace(remain, 0);
					courseList.add(remain);
					courseList.add(String.valueOf((d)));
					count-=d;
				}
		
			}
			valueObject.setValues(courseList);
			outputList.add(valueObject);
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



