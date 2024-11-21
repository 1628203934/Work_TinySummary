package AI.TinySummary.services;

import AI.TinySummary.api.OpenAIApi;

import java.util.Map;

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
            consultationTinySummary: %s, \n
            
            Patient Notes After The Consultation: \n
            notes: %s \n
            
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
        String formattedInput = ResponseFormatter.formatToJson(conditionDescription, consultationRequestTinySummary, consultationRequestCreatedDate, ConsultationTinySummary, patientNote);

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
            3 Put a question mark ? in front of doubting condition \n
            4 Rewrite to guarantee that generated content are human-readable \n
            5 Only include necessary contents helpful for doctors during diagnosing and management \n
            6 Do not include age, gender, allergy or other monograph information \n
            7 Attach dates to condition and medication if mentioned or can be referred
            8 Include family member long term history in bullet points if mentioned
            
            Also, Bear in mind that you MUST NOT hallucinate at all, provide all information based solely on input information and do not provide redundant information
            """,
                conditionDescription, consultationRequestTinySummary, consultationRequestCreatedDate, ConsultationTinySummary, patientNote);
        return OpenAIApi.getAIPromptResponse(prompt);
    }
    public static String generateSummaryVersion3(String conditionDescription, String consultationRequestTinySummary, String consultationRequestCreatedDate, String consultationTinySummary, String patientNote, String conditionDiagnosisListStr) {

        // Construct the detailed prompt
        String prompt = String.format("""
        Imagine you are a professional General Practitioner.
        Based on the following input data, generate a concise, structured summary and analyze family history for long-term conditions:
        
        Consultation Request Information:
        - conditionDescription: %s
        - consultationRequestTinySummary: %s
        - consultationRequestCreatedDate: %s
        
        Consultation Information:
        - consultationTinySummary: %s
        
        Patient Notes After The Consultation:
        - notes: %s
        
        Condition Diagnosis List:
        %s
        
        Your task:
        1. Generate a concise structured summary in JSON format as shown below.
        
        For family history:
        1. Analyze the family history from the notes section to identify family relations and associated long-term conditions.
        2. Match identified conditions with the `conditionDiagnosisList` and return the corresponding `conditionDiagnosesId` for each relation.
        3. If no match is found for a condition, use `null` for the ID.
        4. If a relation have multiple long-term conditions, create a separate entry for each condition.
        5. Only consider relation within range [Mother, Father, Sister, Brother, Spouse, GrandmotherMaternal, GrandfatherMaternal, GrandmotherPaternal, GrandfatherPaternal].
        
        Output JSON format:
        {
            "minimum summary": [
                "Bullet point 1",
                "Bullet point 2",
                ...
            ],
            "smoke history": "One of [NeverSmoked, FormerSmoker, CurrentSmoker, OccasionalSmoker, SecondhandExposure, Unknown]",
            "alcohol history": "One of [NonDrinker, OccasionalDrinker, SocialDrinker, LightDrinker, ModerateDrinker, HeavyDrinker, FormerDrinker, Unknown]",
            "drug use history": "A string containing drug names and dates if mentioned, otherwise 'Unknown'",
            "family history": [
                ["relation1", conditionDiagnosesId1],
                ["relation2", conditionDiagnosesId2],
                ...
            ]
        }
        
        Rules for generating the summary:
        1. Provide bullet points with each containing 2-6 concise words, make sure each bullet point conveys complete and meaningful ideas.
        2. Consolidate bullet points if they are related to the context of the same condition(In this case, you may exceed the words length limitation to convey complete meaning), for example, if two bullet points are like: "Chesty cough, 2 weeks", "Worse at night/morning", you may want to concatenate them since the second point itself does not convey too much meaning
        2. Use standard medical abbreviations for medical terms.
        3. Prefix doubtful conditions with a question mark (?).
        4. Ensure human-readable, professional content.
        5. Include only necessary content helpful for doctors during diagnosing and management.
        6. Exclude age, gender, allergy, or other monograph information.
        7. Attach dates to conditions or medications if mentioned or can be referred to.
        8. Do not include simple symptomatic treatments.
        9. Do not include family member in 'minimum summary', instead provide them in family history section.
        10. You should consider consultation request date as the date when all input information are acquired and hence provide date for conditions and medications.
        
        Do not hallucinate or add redundant information. Only use the information provided.
        """,
                        conditionDescription, consultationRequestTinySummary, consultationRequestCreatedDate,
                        consultationTinySummary, patientNote, conditionDiagnosisListStr);


        // Call the OpenAI API
        return OpenAIApi.getAIPromptResponse(prompt);
    }

}
