package AI.TinySummary.services;

import AI.TinySummary.api.OpenAIApi;

public class AIService {

    // Method to generate a summary using OpenAI API
    public static String generateSummary(String conditionDescription, String consultationRequestTinySummary, String consultationRequestCreatedDate, String ConsultationTinySummary, String consultationCreatedDate, String patientNote, String patientNoteCreatedDate) {
        String prompt = String.format("""
            Imagine you are a professional General Practitioner,
            now please provide a summary for a patient based on following input: \n
            
            Consultation Request Information: \n
            conditionDescription: %s, \n
            consultationRequestTinySummary: %s, \n
            consultationRequestCreatedDate: %s, \n

            Consultation Information: \n
            ConsultationTinySummary: %s, \n
            consultationCreatedDate: %s, \n
            
            Patient Notes After The Consultation: \n
            Notes: %s \n
            patientNoteCreatedDate: %s \n
            
            You MUST generate the output following format: \n
            [Medical Conditions]: medical condition1, condition diagnosed date, concise while comprehensive reason for condition1; condition2, ... \n
            [Medication]: medication name1, prescribed date1(1 Shows "No date available" if you cannot get or refer from input information. 2 There can be multiple dates prescribed for certain medication, in that case, include all of them); medication 2, ... \n
            [Social History]: [Smoke History]: content; [Alcohol History]: content; [Drug Use History]: content; [Family Member History](only include family members with long term condition): member relation, condition diagnosis, diagnosis date \n
            
            Also, Bear in mind that you MUST NOT hallucinate at all, provide all information based solely on input information and do not provide redundant information
            """,
            conditionDescription, consultationRequestTinySummary, consultationRequestCreatedDate, ConsultationTinySummary, consultationCreatedDate, patientNote, patientNoteCreatedDate);
        return OpenAIApi.getAIPromptResponse(prompt);
    }
    public static String generateSummaryVersion2(String conditionDescription, String consultationRequestTinySummary, String consultationRequestCreatedDate, String ConsultationTinySummary, String consultationCreatedDate, String patientNote, String patientNoteCreatedDate) {
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
            Consultation Request Information: \n
            conditionDescription: summarized and rewritten condition description, \n
            consultationRequestTinySummary: do not change, \n
            consultationRequestCreatedDate: only include date, \n
            
            Consultation Information: \n
            ConsultationTinySummary: do not change, \n
            ConsultationCreatedDate: only include date, \n
            
            Patient Notes After The Consultation: \n
            Notes: concisely and comprehensively summarized and rewritten patient notes, only include information necessarily related to medical consultation and doctor diagnosing, format them in bullet points \n
            patientNoteCreatedDate: only include date \n
            
            Also, Bear in mind that you MUST NOT hallucinate at all, provide all information based solely on input information and do not provide redundant information
            """,
                conditionDescription, consultationRequestTinySummary, consultationRequestCreatedDate, ConsultationTinySummary, consultationCreatedDate, patientNote, patientNoteCreatedDate);
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
