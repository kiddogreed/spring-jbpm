package com.jrrd.jbpmdemo.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

@DisplayName("LeaveRequestDTO Tests")
class LeaveRequestDTOTest {

    private ObjectMapper objectMapper;
    private LeaveRequestDTO dto;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        dto = new LeaveRequestDTO();
    }

    @Test
    @DisplayName("Should create DTO with default constructor")
    void shouldCreateDTOWithDefaultConstructor() {
        // Act
        LeaveRequestDTO newDto = new LeaveRequestDTO();

        // Assert
        assertNotNull(newDto);
        assertNull(newDto.getEmployeeName());
        assertEquals(0, newDto.getDaysRequested()); // int defaults to 0
    }

    @Test
    @DisplayName("Should set and get employee name")
    void shouldSetAndGetEmployeeName() {
        // Arrange
        String expectedName = "John Doe";

        // Act
        dto.setEmployeeName(expectedName);

        // Assert
        assertEquals(expectedName, dto.getEmployeeName());
    }

    @Test
    @DisplayName("Should set and get days requested")
    void shouldSetAndGetDaysRequested() {
        // Arrange
        int expectedDays = 5;

        // Act
        dto.setDaysRequested(expectedDays);

        // Assert
        assertEquals(expectedDays, dto.getDaysRequested());
    }

    @Test
    @DisplayName("Should handle null employee name")
    void shouldHandleNullEmployeeName() {
        // Act
        dto.setEmployeeName(null);

        // Assert
        assertNull(dto.getEmployeeName());
    }

    @Test
    @DisplayName("Should handle zero days requested")
    void shouldHandleZeroDaysRequested() {
        // Act
        dto.setDaysRequested(0);

        // Assert
        assertEquals(0, dto.getDaysRequested());
    }

    @Test
    @DisplayName("Should serialize to JSON correctly")
    void shouldSerializeToJsonCorrectly() throws Exception {
        // Arrange
        dto.setEmployeeName("Jane Smith");
        dto.setDaysRequested(7);

        // Act
        String json = objectMapper.writeValueAsString(dto);

        // Assert
        assertNotNull(json);
        assertTrue(json.contains("\"employeeName\":\"Jane Smith\""));
        assertTrue(json.contains("\"daysRequested\":7"));
    }

    @Test
    @DisplayName("Should deserialize from JSON correctly")
    void shouldDeserializeFromJsonCorrectly() throws Exception {
        // Arrange
        String json = "{\"employeeName\":\"Bob Wilson\",\"daysRequested\":10}";

        // Act
        LeaveRequestDTO result = objectMapper.readValue(json, LeaveRequestDTO.class);

        // Assert
        assertNotNull(result);
        assertEquals("Bob Wilson", result.getEmployeeName());
        assertEquals(10, result.getDaysRequested());
    }

    @Test
    @DisplayName("Should handle empty JSON object")
    void shouldHandleEmptyJsonObject() throws Exception {
        // Arrange
        String json = "{}";

        // Act
        LeaveRequestDTO result = objectMapper.readValue(json, LeaveRequestDTO.class);

        // Assert
        assertNotNull(result);
        assertNull(result.getEmployeeName());
        assertEquals(0, result.getDaysRequested()); // int defaults to 0
    }

    @Test
    @DisplayName("Should handle JSON with only employee name")
    void shouldHandleJsonWithOnlyEmployeeName() throws Exception {
        // Arrange
        String json = "{\"employeeName\":\"Partial Data Employee\"}";

        // Act
        LeaveRequestDTO result = objectMapper.readValue(json, LeaveRequestDTO.class);

        // Assert
        assertNotNull(result);
        assertEquals("Partial Data Employee", result.getEmployeeName());
        assertEquals(0, result.getDaysRequested()); // int defaults to 0
    }

    @Test
    @DisplayName("Should handle JSON with only days requested")
    void shouldHandleJsonWithOnlyDaysRequested() throws Exception {
        // Arrange
        String json = "{\"daysRequested\":3}";

        // Act
        LeaveRequestDTO result = objectMapper.readValue(json, LeaveRequestDTO.class);

        // Assert
        assertNotNull(result);
        assertNull(result.getEmployeeName());
        assertEquals(3, result.getDaysRequested());
    }

    @Test
    @DisplayName("Should handle edge case values")
    void shouldHandleEdgeCaseValues() {
        // Test with edge case values
        dto.setEmployeeName("");
        dto.setDaysRequested(0);

        assertEquals("", dto.getEmployeeName());
        assertEquals(0, dto.getDaysRequested());

        // Test with negative days
        dto.setDaysRequested(-1);
        assertEquals(-1, dto.getDaysRequested());

        // Test with very large number
        dto.setDaysRequested(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, dto.getDaysRequested());
    }

    @Test
    @DisplayName("Should handle special characters in employee name")
    void shouldHandleSpecialCharactersInEmployeeName() throws Exception {
        // Arrange
        String specialName = "José María O'Connor-Smith";
        dto.setEmployeeName(specialName);

        // Act - Serialize and deserialize
        String json = objectMapper.writeValueAsString(dto);
        LeaveRequestDTO result = objectMapper.readValue(json, LeaveRequestDTO.class);

        // Assert
        assertEquals(specialName, result.getEmployeeName());
    }
}
