package com.roomallocation.ExamRoomAllocation.vo;

import io.github.millij.poi.ss.model.annotations.Sheet;
import io.github.millij.poi.ss.model.annotations.SheetColumn;

@Sheet
public class DatesheetVO {
	
	
	@SheetColumn("Date")
	private String date;
	
	@SheetColumn("Shift")
	private String shift;
	
	@SheetColumn("Start Time")
	private String startTime;
	
	@SheetColumn("End Time")
	private String endTime;
	
	@SheetColumn("Batch Name")
	private String batchName;
	
	@SheetColumn("Subject Code")
	private String subjectCode;
	
	private String examName;
	
	private String drawingSubjFlag;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getShift() {
		return shift;
	}
	public void setShift(String shift) {
		this.shift = shift;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getBatchName() {
		return batchName;
	}
	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}
	public String getSubjectCode() {
		return subjectCode;
	}
	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}
	public String getDrawingSubjFlag() {
		return drawingSubjFlag;
	}
	public void setDrawingSubjFlag(String drawingSubjFlag) {
		this.drawingSubjFlag = drawingSubjFlag;
	}
	public String getExamName() {
		return examName;
	}
	public void setExamName(String examName) {
		this.examName = examName;
	}
	
	

}
