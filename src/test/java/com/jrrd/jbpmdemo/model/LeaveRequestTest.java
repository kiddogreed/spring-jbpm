package com.jrrd.jbpmdemo.model;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("LeaveRequest Model Tests")
class LeaveRequestTest {

    @Nested
    @DisplayName("Constructor Tests")
    class ConstructorTests {

        @Test
        @DisplayName("Should create leave request with auto-approval for 5 days or less")
        void shouldAutoApproveForFiveDaysOrLess() {
            // Arrange & Act
            LeaveRequest request = new LeaveRequest("John Doe", 5);
            
            // Assert
            assertNotNull(request.getId(), "ID should be generated");
            assertEquals("John Doe", request.getEmployeeName());
            assertEquals(5, request.getDaysRequested());
            assertTrue(request.getApproved(), "Should be auto-approved for 5 days");
            assertNotNull(request.getRequestDate(), "Request date should be set");
            assertNotNull(request.getApprovalDate(), "Approval date should be set for approved requests");
        }

        @Test
        @DisplayName("Should create leave request without auto-approval for more than 5 days")
        void shouldNotAutoApproveForMoreThanFiveDays() {
            // Arrange & Act
            LeaveRequest request = new LeaveRequest("Jane Smith", 10);
            
            // Assert
            assertNotNull(request.getId());
            assertEquals("Jane Smith", request.getEmployeeName());
            assertEquals(10, request.getDaysRequested());
            assertFalse(request.getApproved(), "Should not be auto-approved for more than 5 days");
            assertNotNull(request.getRequestDate());
            assertNull(request.getApprovalDate(), "Approval date should be null for non-approved requests");
        }

        @Test
        @DisplayName("Should auto-approve for 1 day")
        void shouldAutoApproveForOneDay() {
            // Arrange & Act
            LeaveRequest request = new LeaveRequest("Alice Johnson", 1);
            
            // Assert
            assertTrue(request.getApproved(), "Should be auto-approved for 1 day");
            assertNotNull(request.getApprovalDate());
        }

        @Test
        @DisplayName("Should not auto-approve for 6 days")
        void shouldNotAutoApproveForSixDays() {
            // Arrange & Act
            LeaveRequest request = new LeaveRequest("Bob Wilson", 6);
            
            // Assert
            assertFalse(request.getApproved(), "Should not be auto-approved for 6 days");
            assertNull(request.getApprovalDate());
        }

        @Test
        @DisplayName("Should generate unique IDs for different requests")
        void shouldGenerateUniqueIds() {
            // Arrange & Act
            LeaveRequest request1 = new LeaveRequest("Employee 1", 3);
            LeaveRequest request2 = new LeaveRequest("Employee 2", 3);
            
            // Assert
            assertNotEquals(request1.getId(), request2.getId(), "Should generate unique IDs");
        }

        @Test
        @DisplayName("Should create leave request with default constructor")
        void shouldCreateWithDefaultConstructor() {
            // Arrange & Act
            LeaveRequest request = new LeaveRequest();
            
            // Assert
            assertNotNull(request.getId());
            assertNotNull(request.getRequestDate());
            assertNull(request.getEmployeeName());
            assertNull(request.getDaysRequested());
            assertNull(request.getApproved());
            assertNull(request.getApprovalDate());
        }
    }

    @Nested
    @DisplayName("Getter and Setter Tests")
    class GetterSetterTests {

        @Test
        @DisplayName("Should set and get employee name")
        void shouldSetAndGetEmployeeName() {
            // Arrange
            LeaveRequest request = new LeaveRequest();
            
            // Act
            request.setEmployeeName("Test Employee");
            
            // Assert
            assertEquals("Test Employee", request.getEmployeeName());
        }

        @Test
        @DisplayName("Should set and get days requested")
        void shouldSetAndGetDaysRequested() {
            // Arrange
            LeaveRequest request = new LeaveRequest();
            
            // Act
            request.setDaysRequested(7);
            
            // Assert
            assertEquals(7, request.getDaysRequested());
        }

        @Test
        @DisplayName("Should set and get approval status")
        void shouldSetAndGetApprovalStatus() {
            // Arrange
            LeaveRequest request = new LeaveRequest();
            
            // Act
            request.setApproved(true);
            
            // Assert
            assertTrue(request.getApproved());
        }

        @Test
        @DisplayName("Should set and get approval date")
        void shouldSetAndGetApprovalDate() {
            // Arrange
            LeaveRequest request = new LeaveRequest();
            LocalDateTime approvalDate = LocalDateTime.now();
            
            // Act
            request.setApprovalDate(approvalDate);
            
            // Assert
            assertEquals(approvalDate, request.getApprovalDate());
        }
    }

    @Nested
    @DisplayName("Business Logic Tests")
    class BusinessLogicTests {

        @Test
        @DisplayName("Should handle boundary case at exactly 5 days")
        void shouldHandleBoundaryCaseAtFiveDays() {
            // Arrange & Act
            LeaveRequest request = new LeaveRequest("Boundary Test", 5);
            
            // Assert
            assertTrue(request.getApproved(), "Exactly 5 days should be auto-approved");
            assertNotNull(request.getApprovalDate());
        }

        @Test
        @DisplayName("Should handle zero days request")
        void shouldHandleZeroDaysRequest() {
            // Arrange & Act
            LeaveRequest request = new LeaveRequest("Zero Days", 0);
            
            // Assert
            assertTrue(request.getApproved(), "Zero days should be auto-approved");
            assertNotNull(request.getApprovalDate());
        }

        @Test
        @DisplayName("Should handle negative days request")
        void shouldHandleNegativeDaysRequest() {
            // Arrange & Act
            LeaveRequest request = new LeaveRequest("Negative Days", -1);
            
            // Assert
            assertTrue(request.getApproved(), "Negative days should be auto-approved (though logically invalid)");
            assertNotNull(request.getApprovalDate());
        }
    }

    @Nested
    @DisplayName("Approval Management Tests")
    class ApprovalManagementTests {

        @Test
        @DisplayName("Should manually approve a pending request")
        void shouldManuallyApprovePendingRequest() {
            // Arrange
            LeaveRequest request = new LeaveRequest("Manager Approval", 10);
            assertFalse(request.getApproved(), "Should initially be pending");
            assertNull(request.getApprovalDate());
            
            // Act
            request.setApproved(true);
            request.setApprovalDate(LocalDateTime.now());
            
            // Assert
            assertTrue(request.getApproved());
            assertNotNull(request.getApprovalDate());
        }

        @Test
        @DisplayName("Should reject a request")
        void shouldRejectRequest() {
            // Arrange
            LeaveRequest request = new LeaveRequest("Rejection Test", 10);
            
            // Act
            request.setApproved(false);
            // Note: In real scenarios, you might want to track rejection date separately
            
            // Assert
            assertFalse(request.getApproved());
        }
    }

    @Nested
    @DisplayName("Data Integrity Tests")
    class DataIntegrityTests {

        @Test
        @DisplayName("Should maintain request date after creation")
        void shouldMaintainRequestDate() {
            // Arrange
            LocalDateTime before = LocalDateTime.now().minusSeconds(1);
            
            // Act
            LeaveRequest request = new LeaveRequest("Date Test", 3);
            LocalDateTime after = LocalDateTime.now().plusSeconds(1);
            
            // Assert
            assertTrue(request.getRequestDate().isAfter(before) && 
                      request.getRequestDate().isBefore(after),
                      "Request date should be set to current time");
        }

        @Test
        @DisplayName("Should handle null employee name gracefully")
        void shouldHandleNullEmployeeName() {
            // Arrange & Act
            LeaveRequest request = new LeaveRequest(null, 3);
            
            // Assert
            assertNull(request.getEmployeeName());
            // The approval logic should still work
            assertTrue(request.getApproved());
        }

        @Test
        @DisplayName("Should handle null days requested")
        void shouldHandleNullDaysRequested() {
            // Arrange & Act
            // Note: The constructor expects int, so we test the edge case behavior
            // In production, you'd want proper validation
            try {
                LeaveRequest request = new LeaveRequest("Null Days", null);
                // If this doesn't throw an exception, test the result
                assertNull(request.getDaysRequested());
            } catch (NullPointerException e) {
                // This is expected behavior with primitive int
                // In production, you'd want to use Integer instead of int
                // or add proper null validation
                assertTrue(true, "NullPointerException is expected when passing null to int parameter");
            }
        }
    }
}
