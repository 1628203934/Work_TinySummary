package AI.TinySummary;

import AI.TinySummary.services.AIService;
import AI.TinySummary.utils.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        String url = Config.getProperty("db.url");
        String user = Config.getProperty("db.user");
        String password = Config.getProperty("db.password");

        String consultationQuery = """
            SELECT
                cr.Id AS ConsultationRequestId,
                cr.ConditionDescription,
                cr.TinySummary AS ConsultationRequestTinySummary,
                cr.CreatedDate AS ConsultationRequestCreatedDate,
                cr.PatientProfileId,
            
                c.Id AS ConsultationId,
                c.TinySummary AS ConsultationTinySummary,
                c.CreatedDate AS ConsultationCreatedDate,
                
                pn.Notes,
                pn.CreatedDate AS PatientNoteCreatedDate
            FROM ConsultationRequests cr
            JOIN Consultations c ON cr.Id = c.ConsultationRequestId
            JOIN PatientNotes pn ON c.Id = pn.ConsultationId
            WHERE cr.Status = 'ConsultationEnded'
            AND cr.TinySummary IS NOT NULL
            AND c.TinySummary IS NOT NULL
            AND Notes IS NOT NULL
        """;

        String conditionDiagnosisQuery = """
            SELECT
                Id,
                SearchTerms
            FROM ConditionDiagnoses
        """;

        try {
            // Load the JDBC driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            // Establish the connection
            Connection connection = DriverManager.getConnection(url, user, password);
//            System.out.println("Connection successful!");

            // Process conditionDiagnoses query and get the condition diagnoses list
            Statement conditionDiagnosesStatement = connection.createStatement();
            ResultSet conditionDiagnosesResultSet = conditionDiagnosesStatement.executeQuery(conditionDiagnosisQuery);

            Map<Integer, String> conditionDiagnosesMap = new HashMap<>();

            while (conditionDiagnosesResultSet.next()) {
                int id = conditionDiagnosesResultSet.getInt("Id");
                String searchTerms = conditionDiagnosesResultSet.getString("SearchTerms");
                conditionDiagnosesMap.put(id, searchTerms);
            }

            // convert the map into string
            StringBuilder conditionDiagnosisListStr = new StringBuilder();
            for (Map.Entry<Integer, String> entry : conditionDiagnosesMap.entrySet()) {
                conditionDiagnosisListStr.append("- ")
                        .append(entry.getValue()) // Search term
                        .append(" (Id: ")
                        .append(entry.getKey()) // Corresponding ID
                        .append(")\n");
            }

            conditionDiagnosesResultSet.close();
            conditionDiagnosesStatement.close();

            System.out.println(conditionDiagnosisListStr.toString());

            // Process consultationQuery
            // Create a statement
            Statement stmt = connection.createStatement();

            // Execute the query
            ResultSet rs = stmt.executeQuery(consultationQuery);

            // Print the results
            int currentResultCount = 0;
            int desiredCount = 1;

            while (rs.next() && currentResultCount < desiredCount) {

                int consultationRequestId = rs.getInt("ConsultationRequestId");
                String conditionDescription = rs.getString("ConditionDescription");
                String consultationRequestCreatedDate = rs.getString("ConsultationRequestCreatedDate");
                String consultationRequestTinySummary = rs.getString("ConsultationRequestTinySummary");
                int patientProfileId = rs.getInt("PatientProfileId");

                String consultationCreatedDate = rs.getString("ConsultationCreatedDate");
                String consultationTinySummary = rs.getString("ConsultationTinySummary");

                String notes = rs.getString("Notes");
                String patientNoteCreatedDate = rs.getString("PatientNoteCreatedDate");

                System.out.println("PatientProfileId: " + patientProfileId);
                System.out.println("ConsultationRequestId: " + consultationRequestId);
                System.out.println("ConditionDescription: " + conditionDescription);
                System.out.println("ConsultationRequestTinySummary: " + consultationRequestTinySummary);
                System.out.println("ConsultationRequestCreatedDate: " + consultationRequestCreatedDate);
                System.out.println("ConsultationTinySummary: " + consultationTinySummary);
                System.out.println("ConsultationCreatedDate: " + consultationCreatedDate);
                System.out.println("Notes: " + notes);
                System.out.println("PatientNoteCreatedDate: " + patientNoteCreatedDate);
                System.out.println("------------------------------");
//                String AIResponse = AIService.generateSummary(conditionDescription, consultationRequestTinySummary, consultationRequestCreatedDate, consultationTinySummary, notes);
//                System.out.println("AI Summary Version1 : \n" + AIResponse);
//                System.out.println("------------------------------");
//                String AIResponseVer2 = AIService.generateSummaryVersion2(conditionDescription, consultationRequestTinySummary, consultationRequestCreatedDate, consultationTinySummary, notes);
//                System.out.println("AI Summary Version2 : \n" + AIResponseVer2);
//                System.out.println("------------------------------");
                String AIResponseVer3 = AIService.generateSummaryVersion3(conditionDescription, consultationRequestTinySummary, consultationRequestCreatedDate, consultationTinySummary, notes, conditionDiagnosisListStr.toString());
                System.out.println("AI Summary Version3 : \n" + AIResponseVer3);
                System.out.println("------------------------------");
                System.out.println("------------------------------");
                currentResultCount++;
                // Process the response and save to DB

            }

            // Clean up
            rs.close();
            stmt.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
