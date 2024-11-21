package AI.TinySummary.services;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ResponseFormatter {

    // Example: Parse a string into a structured JSON format
    public static String formatToJson(
            String conditionDescription,
            String consultationRequestTinySummary,
            String consultationRequestCreatedDate,
            String consultationTinySummary,
            String patientNote) {
        try {
            // Create a JSON-like structure
            Map<String, Object> inputMap = Map.of(
                    "ConsultationRequest", Map.of(
                            "ConditionDescription", conditionDescription,
                            "ConsultationRequestTinySummary", consultationRequestTinySummary,
                            "ConsultationRequestCreatedDate", consultationRequestCreatedDate
                    ),
                    "ConsultationInformation", Map.of(
                            "ConsultationTinySummary", consultationTinySummary
                    ),
                    "PatientNotes", patientNote
            );

            // Convert to JSON
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(inputMap);
        } catch (Exception e) {
            throw new RuntimeException("Failed to format input to JSON", e);
        }
    }


    // Example: Generate bullet points from the raw AI response
    public static List<String> formatToBulletPoints(String aiResponse) {
        // Split response into lines or bullets (assuming AI separates them with newlines)
        String[] lines = aiResponse.split("\\n");

        // Filter empty lines and trim spaces
        return Arrays.stream(lines)
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .collect(Collectors.toList());
    }

}
