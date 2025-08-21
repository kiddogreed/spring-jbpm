package com.jrrd.jbpmdemo.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class LeaveRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String id;
    private String employeeName;
    private Integer daysRequested;
    private Boolean approved;
    private LocalDateTime requestDate;
    private LocalDateTime approvalDate;
    
    public LeaveRequest() {
        this.id = UUID.randomUUID().toString();
        this.requestDate = LocalDateTime.now();
    }
    
    public LeaveRequest(String employeeName, Integer daysRequested) {
        this();
        this.employeeName = employeeName;
        this.daysRequested = daysRequested;
        // Auto-approve if days requested are <= 5
        this.approved = daysRequested <= 5;
        if (approved) {
            this.approvalDate = LocalDateTime.now();
        }
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Integer getDaysRequested() {
        return daysRequested;
    }

    public void setDaysRequested(Integer daysRequested) {
        this.daysRequested = daysRequested;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
        if (Boolean.TRUE.equals(approved) && approvalDate == null) {
            this.approvalDate = LocalDateTime.now();
        }
    }
    
    public LocalDateTime getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDateTime requestDate) {
        this.requestDate = requestDate;
    }

    public LocalDateTime getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(LocalDateTime approvalDate) {
        this.approvalDate = approvalDate;
    }
    
    @Override
    public String toString() {
        return "LeaveRequest [id=" + id + ", employeeName=" + employeeName + ", daysRequested=" + daysRequested 
               + ", approved=" + approved + ", requestDate=" + requestDate + ", approvalDate=" + approvalDate + "]";
    }
}
