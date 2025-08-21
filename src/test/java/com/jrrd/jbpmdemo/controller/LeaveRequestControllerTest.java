package com.jrrd.jbpmdemo.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jrrd.jbpmdemo.dto.LeaveRequestDTO;
import com.jrrd.jbpmdemo.model.LeaveRequest;
import com.jrrd.jbpmdemo.service.SimpleLeaveRequestService;

/**
 * Tests for LeaveRequestController REST endpoints.
 * 
 * DISABLED: Due to Mockito compatibility issues with Java 23.
 * Byte Buddy (used by Mockito) doesn't officially support Java 23 yet.
 * To enable these tests, either:
 * 1. Use Java 17/21 instead of Java 23, or
 * 2. Add -Dnet.bytebuddy.experimental=true to JVM arguments, or 
 * 3. Update to newer Mockito version when Java 23 support is available
 */
@Disabled("Mockito compatibility issues with Java 23 - Byte Buddy limitation")
@WebMvcTest(LeaveRequestController.class)
@DisplayName("LeaveRequestController Integration Tests")
class LeaveRequestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SimpleLeaveRequestService leaveRequestService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Should create leave request successfully")
    void shouldCreateLeaveRequestSuccessfully() throws Exception {
        // Arrange
        LeaveRequestDTO dto = new LeaveRequestDTO();
        dto.setEmployeeName("John Doe");
        dto.setDaysRequested(3);
        
        String expectedRequestId = "test-request-id-123";
        when(leaveRequestService.createLeaveRequest(anyString(), anyInt()))
                .thenReturn(expectedRequestId);

        // Act & Assert
        mockMvc.perform(post("/api/leave")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Leave request created with ID: " + expectedRequestId));

        // Verify service was called
        verify(leaveRequestService).createLeaveRequest("John Doe", 3);
    }

    @Test
    @DisplayName("Should return leave request for valid ID")
    void shouldReturnLeaveRequestForValidId() throws Exception {
        // Arrange
        String requestId = "valid-request-id";
        LeaveRequest leaveRequest = new LeaveRequest("John Doe", 3);
        
        when(leaveRequestService.getLeaveRequest(requestId)).thenReturn(leaveRequest);

        // Act & Assert
        mockMvc.perform(get("/api/leave/{id}", requestId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.employeeName").value("John Doe"))
                .andExpect(jsonPath("$.daysRequested").value(3))
                .andExpect(jsonPath("$.approved").value(true));
    }

    @Test
    @DisplayName("Should return 404 for non-existent request ID")
    void shouldReturn404ForNonExistentId() throws Exception {
        // Arrange
        String nonExistentId = "non-existent-id";
        when(leaveRequestService.getLeaveRequest(nonExistentId)).thenReturn(null);

        // Act & Assert
        mockMvc.perform(get("/api/leave/{id}", nonExistentId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Leave request not found"));
    }

    @Test
    @DisplayName("Should return all leave requests")
    void shouldReturnAllLeaveRequests() throws Exception {
        // Arrange
        LeaveRequest request1 = new LeaveRequest("Employee 1", 2);
        LeaveRequest request2 = new LeaveRequest("Employee 2", 8);
        List<LeaveRequest> requests = Arrays.asList(request1, request2);
        
        when(leaveRequestService.getAllLeaveRequests()).thenReturn(requests);

        // Act & Assert
        mockMvc.perform(get("/api/leave"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].employeeName").value("Employee 1"))
                .andExpect(jsonPath("$[1].employeeName").value("Employee 2"));
    }

    @Test
    @DisplayName("Should return empty array when no requests exist")
    void shouldReturnEmptyArrayWhenNoRequests() throws Exception {
        // Arrange
        when(leaveRequestService.getAllLeaveRequests()).thenReturn(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(get("/api/leave"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    @DisplayName("Should approve leave request successfully")
    void shouldApproveLeaveRequestSuccessfully() throws Exception {
        // Arrange
        String requestId = "request-to-approve";
        when(leaveRequestService.approveLeaveRequest(requestId)).thenReturn(true);

        // Act & Assert
        mockMvc.perform(put("/api/leave/{id}/approve", requestId))
                .andExpect(status().isOk())
                .andExpect(content().string("Leave request approved"));

        verify(leaveRequestService).approveLeaveRequest(requestId);
    }

    @Test
    @DisplayName("Should return 404 for non-existent request ID on approve")
    void shouldReturn404ForNonExistentIdOnApprove() throws Exception {
        // Arrange
        String nonExistentId = "non-existent-id";
        when(leaveRequestService.approveLeaveRequest(nonExistentId)).thenReturn(false);

        // Act & Assert
        mockMvc.perform(put("/api/leave/{id}/approve", nonExistentId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Leave request not found"));
    }

    @Test
    @DisplayName("Should reject leave request successfully")
    void shouldRejectLeaveRequestSuccessfully() throws Exception {
        // Arrange
        String requestId = "request-to-reject";
        when(leaveRequestService.rejectLeaveRequest(requestId)).thenReturn(true);

        // Act & Assert
        mockMvc.perform(put("/api/leave/{id}/reject", requestId))
                .andExpect(status().isOk())
                .andExpect(content().string("Leave request rejected"));

        verify(leaveRequestService).rejectLeaveRequest(requestId);
    }

    @Test
    @DisplayName("Should return 404 for non-existent request ID on reject")
    void shouldReturn404ForNonExistentIdOnReject() throws Exception {
        // Arrange
        String nonExistentId = "non-existent-id";
        when(leaveRequestService.rejectLeaveRequest(nonExistentId)).thenReturn(false);

        // Act & Assert
        mockMvc.perform(put("/api/leave/{id}/reject", nonExistentId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Leave request not found"));
    }

    @Test
    @DisplayName("Should return 400 for invalid JSON")
    void shouldReturn400ForInvalidJson() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/api/leave")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ invalid json }"))
                .andExpect(status().isBadRequest());
    }
}
