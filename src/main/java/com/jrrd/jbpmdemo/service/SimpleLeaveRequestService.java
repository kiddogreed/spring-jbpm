package com.jrrd.jbpmdemo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.jrrd.jbpmdemo.model.LeaveRequest;

/**
 * Simple service for managing leave requests without Kogito dependencies
 */
@Service
public class SimpleLeaveRequestService {

    private static final Logger logger = LoggerFactory.getLogger(SimpleLeaveRequestService.class);
    
    // In-memory storage for leave requests
    private final Map<String, LeaveRequest> leaveRequests = new ConcurrentHashMap<>();
    
    /**
     * Creates and processes a new leave request
     * 
     * @param employeeName The employee requesting leave
     * @param daysRequested Number of days requested
     * @return ID of the created leave request
     */
    public String createLeaveRequest(String employeeName, int daysRequested) {
        logger.info("Creating leave request for {} with {} days", employeeName, daysRequested);
        
        // Create new request with auto-approval logic
        LeaveRequest request = new LeaveRequest(employeeName, daysRequested);
        String id = request.getId();
        
        // Store the request
        leaveRequests.put(id, request);
        
        logger.info("Leave request created with ID: {} (approved: {})", id, request.getApproved());
        return id;
    }
    
    /**
     * Gets a leave request by ID
     * 
     * @param id The leave request ID
     * @return The LeaveRequest or null if not found
     */
    public LeaveRequest getLeaveRequest(String id) {
        if (id == null) {
            return null;
        }
        return leaveRequests.get(id);
    }
    
    /**
     * Gets all leave requests
     * 
     * @return List of all leave requests
     */
    public List<LeaveRequest> getAllLeaveRequests() {
        return new ArrayList<>(leaveRequests.values());
    }
    
    /**
     * Approves a leave request
     * 
     * @param id The leave request ID
     * @return true if the request was found and approved, false otherwise
     */
    public boolean approveLeaveRequest(String id) {
        if (id == null) {
            return false;
        }
        LeaveRequest request = leaveRequests.get(id);
        if (request != null) {
            request.setApproved(true);
            request.setApprovalDate(java.time.LocalDateTime.now());
            return true;
        }
        return false;
    }
    
    /**
     * Rejects a leave request
     * 
     * @param id The leave request ID
     * @return true if the request was found and rejected, false otherwise
     */
    public boolean rejectLeaveRequest(String id) {
        if (id == null) {
            return false;
        }
        LeaveRequest request = leaveRequests.get(id);
        if (request != null) {
            request.setApproved(false);
            request.setApprovalDate(null); // Clear approval date for rejected requests
            return true;
        }
        return false;
    }
}
