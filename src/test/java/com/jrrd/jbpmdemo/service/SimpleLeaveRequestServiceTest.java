package com.jrrd.jbpmdemo.service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.jrrd.jbpmdemo.model.LeaveRequest;

@DisplayName("SimpleLeaveRequestService Tests")
class SimpleLeaveRequestServiceTest {

    private SimpleLeaveRequestService service;

    @BeforeEach
    void setUp() {
        service = new SimpleLeaveRequestService();
    }

    @Nested
    @DisplayName("Create Leave Request Tests")
    class CreateLeaveRequestTests {

        @Test
        @DisplayName("Should create leave request with auto-approval for 5 days or less")
        void shouldCreateAutoApprovedRequest() {
            // Arrange
            String employeeName = "John Doe";
            int daysRequested = 3;

            // Act
            String requestId = service.createLeaveRequest(employeeName, daysRequested);

            // Assert
            assertNotNull(requestId, "Request ID should not be null");
            assertFalse(requestId.isEmpty(), "Request ID should not be empty");

            // Verify the request was stored and auto-approved
            LeaveRequest storedRequest = service.getLeaveRequest(requestId);
            assertNotNull(storedRequest);
            assertEquals(employeeName, storedRequest.getEmployeeName());
            assertEquals(daysRequested, storedRequest.getDaysRequested());
            assertTrue(storedRequest.getApproved(), "Should be auto-approved for 3 days");
            assertNotNull(storedRequest.getApprovalDate());
        }

        @Test
        @DisplayName("Should create leave request without auto-approval for more than 5 days")
        void shouldCreatePendingRequest() {
            // Arrange
            String employeeName = "Jane Smith";
            int daysRequested = 10;

            // Act
            String requestId = service.createLeaveRequest(employeeName, daysRequested);

            // Assert
            assertNotNull(requestId);

            // Verify the request was stored but not approved
            LeaveRequest storedRequest = service.getLeaveRequest(requestId);
            assertNotNull(storedRequest);
            assertEquals(employeeName, storedRequest.getEmployeeName());
            assertEquals(daysRequested, storedRequest.getDaysRequested());
            assertFalse(storedRequest.getApproved(), "Should not be auto-approved for 10 days");
            assertNull(storedRequest.getApprovalDate());
        }

        @Test
        @DisplayName("Should create multiple requests with unique IDs")
        void shouldCreateMultipleRequestsWithUniqueIds() {
            // Arrange & Act
            String id1 = service.createLeaveRequest("Employee 1", 2);
            String id2 = service.createLeaveRequest("Employee 2", 8);
            String id3 = service.createLeaveRequest("Employee 3", 5);

            // Assert
            assertNotEquals(id1, id2);
            assertNotEquals(id2, id3);
            assertNotEquals(id1, id3);

            // Verify all requests are stored
            assertNotNull(service.getLeaveRequest(id1));
            assertNotNull(service.getLeaveRequest(id2));
            assertNotNull(service.getLeaveRequest(id3));
        }

        @Test
        @DisplayName("Should handle boundary case at exactly 5 days")
        void shouldHandleBoundaryCaseAtFiveDays() {
            // Arrange & Act
            String requestId = service.createLeaveRequest("Boundary Employee", 5);

            // Assert
            LeaveRequest request = service.getLeaveRequest(requestId);
            assertTrue(request.getApproved(), "Exactly 5 days should be auto-approved");
            assertNotNull(request.getApprovalDate());
        }

        @Test
        @DisplayName("Should handle edge case with zero days")
        void shouldHandleZeroDays() {
            // Arrange & Act
            String requestId = service.createLeaveRequest("Zero Days Employee", 0);

            // Assert
            LeaveRequest request = service.getLeaveRequest(requestId);
            assertTrue(request.getApproved(), "Zero days should be auto-approved");
        }
    }

    @Nested
    @DisplayName("Get Leave Request Tests")
    class GetLeaveRequestTests {

        @Test
        @DisplayName("Should return null for non-existent request ID")
        void shouldReturnNullForNonExistentId() {
            // Arrange
            String nonExistentId = "non-existent-id";

            // Act
            LeaveRequest result = service.getLeaveRequest(nonExistentId);

            // Assert
            assertNull(result, "Should return null for non-existent ID");
        }

        @Test
        @DisplayName("Should return correct request for valid ID")
        void shouldReturnCorrectRequestForValidId() {
            // Arrange
            String employeeName = "Test Employee";
            int daysRequested = 4;
            String requestId = service.createLeaveRequest(employeeName, daysRequested);

            // Act
            LeaveRequest result = service.getLeaveRequest(requestId);

            // Assert
            assertNotNull(result);
            assertEquals(requestId, result.getId());
            assertEquals(employeeName, result.getEmployeeName());
            assertEquals(daysRequested, result.getDaysRequested());
        }

        @Test
        @DisplayName("Should handle null request ID gracefully")
        void shouldHandleNullRequestId() {
            // Act
            LeaveRequest result = service.getLeaveRequest(null);

            // Assert
            assertNull(result, "Should return null for null ID");
        }
    }

    @Nested
    @DisplayName("Get All Leave Requests Tests")
    class GetAllLeaveRequestsTests {

        @Test
        @DisplayName("Should return empty list when no requests exist")
        void shouldReturnEmptyListWhenNoRequests() {
            // Act
            List<LeaveRequest> result = service.getAllLeaveRequests();

            // Assert
            assertNotNull(result);
            assertTrue(result.isEmpty(), "Should return empty list when no requests exist");
        }

        @Test
        @DisplayName("Should return all stored requests")
        void shouldReturnAllStoredRequests() {
            // Arrange
            String id1 = service.createLeaveRequest("Employee 1", 2);
            String id2 = service.createLeaveRequest("Employee 2", 8);
            String id3 = service.createLeaveRequest("Employee 3", 5);

            // Act
            List<LeaveRequest> result = service.getAllLeaveRequests();

            // Assert
            assertNotNull(result);
            assertEquals(3, result.size(), "Should return all 3 requests");

            // Verify all requests are present
            List<String> returnedIds = result.stream()
                    .map(LeaveRequest::getId)
                    .toList();
            assertTrue(returnedIds.contains(id1));
            assertTrue(returnedIds.contains(id2));
            assertTrue(returnedIds.contains(id3));
        }

        @Test
        @DisplayName("Should return correct request details in list")
        void shouldReturnCorrectRequestDetailsInList() {
            // Arrange
            service.createLeaveRequest("Auto Approved", 3);
            service.createLeaveRequest("Pending Approval", 10);

            // Act
            List<LeaveRequest> result = service.getAllLeaveRequests();

            // Assert
            assertEquals(2, result.size());

            // Find and verify auto-approved request
            LeaveRequest autoApproved = result.stream()
                    .filter(r -> "Auto Approved".equals(r.getEmployeeName()))
                    .findFirst()
                    .orElse(null);
            assertNotNull(autoApproved);
            assertTrue(autoApproved.getApproved());

            // Find and verify pending request
            LeaveRequest pending = result.stream()
                    .filter(r -> "Pending Approval".equals(r.getEmployeeName()))
                    .findFirst()
                    .orElse(null);
            assertNotNull(pending);
            assertFalse(pending.getApproved());
        }
    }

    @Nested
    @DisplayName("Approve Leave Request Tests")
    class ApproveLeaveRequestTests {

        @Test
        @DisplayName("Should approve existing pending request")
        void shouldApproveExistingPendingRequest() {
            // Arrange
            String requestId = service.createLeaveRequest("Pending Employee", 10);
            LeaveRequest initialRequest = service.getLeaveRequest(requestId);
            assertFalse(initialRequest.getApproved(), "Should initially be pending");

            // Act
            boolean result = service.approveLeaveRequest(requestId);

            // Assert
            assertTrue(result, "Approval should succeed");

            // Verify the request is now approved
            LeaveRequest approvedRequest = service.getLeaveRequest(requestId);
            assertTrue(approvedRequest.getApproved());
            assertNotNull(approvedRequest.getApprovalDate());
        }

        @Test
        @DisplayName("Should return false for non-existent request ID")
        void shouldReturnFalseForNonExistentId() {
            // Act
            boolean result = service.approveLeaveRequest("non-existent-id");

            // Assert
            assertFalse(result, "Should return false for non-existent ID");
        }

        @Test
        @DisplayName("Should handle null request ID gracefully")
        void shouldHandleNullRequestIdForApproval() {
            // Act
            boolean result = service.approveLeaveRequest(null);

            // Assert
            assertFalse(result, "Should return false for null ID");
        }

        @Test
        @DisplayName("Should be able to approve already approved request")
        void shouldApproveAlreadyApprovedRequest() {
            // Arrange
            String requestId = service.createLeaveRequest("Auto Approved", 3);
            LeaveRequest initialRequest = service.getLeaveRequest(requestId);
            assertTrue(initialRequest.getApproved(), "Should be auto-approved initially");

            // Act
            boolean result = service.approveLeaveRequest(requestId);

            // Assert
            assertTrue(result, "Should still return true for already approved request");
        }
    }

    @Nested
    @DisplayName("Reject Leave Request Tests")
    class RejectLeaveRequestTests {

        @Test
        @DisplayName("Should reject existing request")
        void shouldRejectExistingRequest() {
            // Arrange
            String requestId = service.createLeaveRequest("Test Employee", 10);

            // Act
            boolean result = service.rejectLeaveRequest(requestId);

            // Assert
            assertTrue(result, "Rejection should succeed");

            // Verify the request is now rejected
            LeaveRequest rejectedRequest = service.getLeaveRequest(requestId);
            assertFalse(rejectedRequest.getApproved());
            assertNull(rejectedRequest.getApprovalDate(), "Approval date should be null for rejected requests");
        }

        @Test
        @DisplayName("Should return false for non-existent request ID")
        void shouldReturnFalseForNonExistentIdOnReject() {
            // Act
            boolean result = service.rejectLeaveRequest("non-existent-id");

            // Assert
            assertFalse(result, "Should return false for non-existent ID");
        }

        @Test
        @DisplayName("Should handle null request ID gracefully")
        void shouldHandleNullRequestIdForRejection() {
            // Act
            boolean result = service.rejectLeaveRequest(null);

            // Assert
            assertFalse(result, "Should return false for null ID");
        }

        @Test
        @DisplayName("Should be able to reject auto-approved request")
        void shouldRejectAutoApprovedRequest() {
            // Arrange
            String requestId = service.createLeaveRequest("Auto Approved", 3);
            LeaveRequest initialRequest = service.getLeaveRequest(requestId);
            assertTrue(initialRequest.getApproved(), "Should be auto-approved initially");

            // Act
            boolean result = service.rejectLeaveRequest(requestId);

            // Assert
            assertTrue(result, "Should be able to reject even auto-approved request");
            
            // Verify the request is now rejected
            LeaveRequest rejectedRequest = service.getLeaveRequest(requestId);
            assertFalse(rejectedRequest.getApproved());
        }
    }

    @Nested
    @DisplayName("Concurrent Access Tests")
    class ConcurrentAccessTests {

        @Test
        @DisplayName("Should handle concurrent request creation")
        void shouldHandleConcurrentRequestCreation() {
            // This test simulates concurrent access patterns
            // In a real scenario, you'd use multiple threads
            
            // Arrange & Act - Create multiple requests quickly
            String id1 = service.createLeaveRequest("Concurrent 1", 2);
            String id2 = service.createLeaveRequest("Concurrent 2", 7);
            String id3 = service.createLeaveRequest("Concurrent 3", 4);

            // Assert - All should be created successfully
            assertNotNull(service.getLeaveRequest(id1));
            assertNotNull(service.getLeaveRequest(id2));
            assertNotNull(service.getLeaveRequest(id3));

            List<LeaveRequest> allRequests = service.getAllLeaveRequests();
            assertEquals(3, allRequests.size());
        }
    }
}
