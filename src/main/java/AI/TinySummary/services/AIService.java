package AI.TinySummary.services;

import AI.TinySummary.api.OpenAIApi;

public class AIService {

    // Method to generate a summary using OpenAI API
    public static String generateSummary(String conditionDescription, String consultationRequestTinySummary, String consultationRequestCreatedDate, String ConsultationTinySummary, String patientNote) {
        String prompt = String.format("""
            Imagine you are a professional General Practitioner,
            now please provide a summary for a patient based on following input: \n
            
            Consultation Request Information: \n
            conditionDescription: %s, \n
            consultationRequestTinySummary: %s, \n
            consultationRequestCreatedDate: %s, \n

            Consultation Information: \n
            ConsultationTinySummary: %s, \n
            
            Patient Notes After The Consultation: \n
            Notes: %s \n
            
            You MUST generate the output following rules: \n
            1 No more than 3 sentences \n
            2 No more than 15 words for each sentence \n
            3 Using standard medical abbreviations \n
            4 Put a question mark ? in front of doubting condition \n
            5 Rewrite to guarantee that generated content are human-readable \n
            
            Also, Bear in mind that you MUST NOT hallucinate at all, provide all information based solely on input information and do not provide redundant information
            """,
            conditionDescription, consultationRequestTinySummary, consultationRequestCreatedDate, ConsultationTinySummary, patientNote);
        return OpenAIApi.getAIPromptResponse(prompt);
    }
    public static String generateSummaryVersion2(String conditionDescription, String consultationRequestTinySummary, String consultationRequestCreatedDate, String ConsultationTinySummary, String patientNote) {
        String prompt = String.format("""
            Imagine you are a professional General Practitioner,
            now please provide a summary for a patient based on following input: \n
            
            Consultation Request Information: \n
            conditionDescription: %s, \n
            consultationRequestTinySummary: %s, \n
            consultationRequestCreatedDate: %s, \n
            
            
            Consultation Information: \n
            ConsultationTinySummary: %s, \n
            
            Patient Notes After The Consultation: \n
            Notes: %s \n
            
            You MUST generate the output following rules: \n
            1 Provide bullets points with each one containing briefly 2-3 words
            2 Using standard medical abbreviations \n
            3 put a question mark ? in front of doubting condition \n
            4 Rewrite to guarantee that generated content are human-readable \n
            
            Also, Bear in mind that you MUST NOT hallucinate at all, provide all information based solely on input information and do not provide redundant information
            """,
                conditionDescription, consultationRequestTinySummary, consultationRequestCreatedDate, ConsultationTinySummary, patientNote);
        return OpenAIApi.getAIPromptResponse(prompt);
    }
    public static String generateSummaryVersion3(String conditionDescription, String consultationRequestTinySummary, String consultationRequestCreatedDate, String ConsultationTinySummary, String consultationCreatedDate, String patientNote, String patientNoteCreatedDate) {
        String prompt = String.format("""
            Imagine you are a professional General Practitioner,
            now please provide a summary for a patient based on following input: \n
            
            Consultation Request Information: \n
            conditionDescription: %s, \n
            consultationRequestTinySummary: %s, \n
            consultationRequestCreatedDate: %s, \n
            
            Consultation Information: \n
            ConsultationTinySummary: %s, \n
            ConsultationCreatedDate: %s, \n
            
            Patient Notes After The Consultation: \n
            Notes: %s \n
            patientNoteCreatedDate: %s \n
            
            You MUST generate the output following format: \n
            include everything mentioned in the input, provide a concise and comprehensive summary for this patient,
            make sure only include information related to medical consultation and diagnosing. format as bullet points.
            If date is mentioned, remember to include them as they provide important factor for doctor during consultation
            
            Also, Bear in mind that you MUST NOT hallucinate at all, provide all information based solely on input information and do not provide redundant information
            """,
                conditionDescription, consultationRequestTinySummary, consultationRequestCreatedDate, ConsultationTinySummary, consultationCreatedDate, patientNote, patientNoteCreatedDate);
        return OpenAIApi.getAIPromptResponse(prompt);
    }
}
