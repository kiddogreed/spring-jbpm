package com.jrrd.jbpmdemo.dto;

/**
 * Data Transfer Object for Leave Request.
 */
public class LeaveRequestDTO {
    private String employeeName;
    private int daysRequested;

    
    public LeaveRequestDTO() {
    }

    public LeaveRequestDTO(String employeeName, int daysRequested) {
        this.employeeName = employeeName;
        this.daysRequested = daysRequested;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public int getDaysRequested() {
        return daysRequested;
    }

    public void setDaysRequested(int daysRequested) {
        this.daysRequested = daysRequested;
    }
}
