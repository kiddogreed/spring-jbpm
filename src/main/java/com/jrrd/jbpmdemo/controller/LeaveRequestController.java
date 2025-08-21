package com.jrrd.jbpmdemo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jrrd.jbpmdemo.dto.LeaveRequestDTO;
import com.jrrd.jbpmdemo.model.LeaveRequest;
import com.jrrd.jbpmdemo.service.SimpleLeaveRequestService;

@RestController
@RequestMapping("/api")
public class LeaveRequestController {
   private static final Logger logger = LoggerFactory.getLogger(LeaveRequestController.class);
   private final SimpleLeaveRequestService leaveRequestService;
   
   public LeaveRequestController(SimpleLeaveRequestService leaveRequestService) {
       this.leaveRequestService = leaveRequestService;
   }
   
   @PostMapping("/leave")
   public ResponseEntity<String> requestLeave(@RequestBody LeaveRequestDTO dto) {
       logger.info("Received leave request for {} with {} days", dto.getEmployeeName(), dto.getDaysRequested());
       String requestId = leaveRequestService.createLeaveRequest(dto.getEmployeeName(), dto.getDaysRequested());
       return ResponseEntity.ok("Leave request created with ID: " + requestId);
   }
   
   @GetMapping("/leave/{id}")
   public ResponseEntity<?> getLeaveRequest(@PathVariable String id) {
       LeaveRequest request = leaveRequestService.getLeaveRequest(id);
       if (request == null) {
           return ResponseEntity.notFound().build();
       }
       return ResponseEntity.ok(request);
   }
   
   @GetMapping("/leave")
   public ResponseEntity<List<LeaveRequest>> getAllLeaveRequests() {
       return ResponseEntity.ok(leaveRequestService.getAllLeaveRequests());
   }
   
   @PutMapping("/leave/{id}/approve")
   public ResponseEntity<String> approveLeaveRequest(@PathVariable String id) {
       boolean success = leaveRequestService.approveLeaveRequest(id);
       if (success) {
           return ResponseEntity.ok("Leave request approved");
       }
       return ResponseEntity.notFound().build();
   }
   
   @PutMapping("/leave/{id}/reject")
   public ResponseEntity<String> rejectLeaveRequest(@PathVariable String id) {
       boolean success = leaveRequestService.rejectLeaveRequest(id);
       if (success) {
           return ResponseEntity.ok("Leave request rejected");
       }
       return ResponseEntity.notFound().build();
   }
}
