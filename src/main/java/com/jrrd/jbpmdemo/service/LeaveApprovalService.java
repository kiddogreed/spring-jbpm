package com.jrrd.jbpmdemo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LeaveApprovalService {

    private static final Logger logger = LoggerFactory.getLogger(LeaveApprovalService.class);

    /**
     * Auto approve leave requests
     * 
     * @param employeeName the employee requesting leave
     * @param daysRequested the number of days requested
     * @return true for auto-approval
     */
    public boolean autoApprove(String employeeName, Integer daysRequested) {
        logger.info("Auto approving leave request for {} for {} days", employeeName, daysRequested);
        return true;
    }
}
