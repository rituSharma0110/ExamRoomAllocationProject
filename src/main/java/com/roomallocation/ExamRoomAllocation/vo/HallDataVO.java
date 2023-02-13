package com.roomallocation.ExamRoomAllocation.vo;

import io.github.millij.poi.ss.model.annotations.Sheet;
import io.github.millij.poi.ss.model.annotations.SheetColumn;

@Sheet
public class HallDataVO {

	@SheetColumn("Room Name")
	private String roomName;
	
	@SheetColumn("Faculty")
	private String faculty;
	
	@SheetColumn("Gender")
	private String gender;
	
	@SheetColumn("Capacity")
	private String capacity;
	
	@SheetColumn("Hall Available")
	private String isHallAvailable;
	
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
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
	public String getCapacity() {
		return capacity;
	}
	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}
	public String getIsHallAvailable() {
		return isHallAvailable;
	}
	public void setIsHallAvailable(String isHallAvailable) {
		this.isHallAvailable = isHallAvailable;
	}
	
	
}
