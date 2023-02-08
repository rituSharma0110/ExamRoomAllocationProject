package com.roomallocation.ExamRoomAllocation.vo;

import java.util.ArrayList;

import io.github.millij.poi.ss.model.annotations.Sheet;
import io.github.millij.poi.ss.model.annotations.SheetColumn;

@Sheet
public class StudentVO {
	@SheetColumn("Student Name")
	private String name;
	
	@SheetColumn("Roll Number")
	private String rollNumber;
	
	@SheetColumn("Subjects")
	private ArrayList<String> courses;
	
	@SheetColumn("Branch")
	private String branch;
	
	@SheetColumn("Specialization")
	private String specialization;
	
	@SheetColumn("Entity")
	private String faculty;
	
	@SheetColumn("Gender")
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
	public void setCourses(ArrayList<String> courses) {
		this.courses = courses;
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
