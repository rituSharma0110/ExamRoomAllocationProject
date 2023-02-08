package com.roomallocation.ExamRoomAllocation.vo;

import java.util.ArrayList;
import java.util.List;


public class StudentVO {
	private String name;
	
	private String rollNumber;
	
	private ArrayList<String> courses;
	
	private String branch;
	
	private String specialization;
	
	private String faculty;
	
	private String gender;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRollNumber() {
		return rollNumber;
	}
	public void setRollNumber(String rollNumber) {
		this.rollNumber = rollNumber;
	}
	public ArrayList<String> getCourses() {
		return courses;
	}
	public void setCourses(List<String> subjects) {
		this.courses = (ArrayList<String>) subjects;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getSpecialization() {
		return specialization;
	}
	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}
	public String getFaculty() {
		return faculty;
	}
	public void setFaculty(String faculty) {
		this.faculty = faculty;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	
	

}
