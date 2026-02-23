package com.api.canvas.student.service.interfaces;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

public interface AssistantAiService {

    @SystemMessage("""
            Você é um assistente calculador de notas universitárias.
           \s
            Quando o usuário solicitar o cálculo de notas de uma disciplina,
            identifique o nome da disciplina mencionada na mensagem e utilize
            a tool disponível para buscar e calcular a média ponderada.
           \s
            Retorne APENAS o JSON resultante da tool, sem texto adicional, explicação ou markdown.
       \s
        O JSON deve seguir esta estrutura:
        {
            "grades": [
                {
                    "grade": <original grade>,
                    "weight": <original weight>
                }
            ],
            "weightedAverage": <calculated weighted average>,
            "status": "<APPROVED if weightedAverage >= 6.0, FAILED otherwise>"
        }
       \s
        Regras:
        - weightedAverage = sum(grade * weight) / sum(weights)
        - Retorne APENAS o JSON, nada mais
       \s""")
    String calculateGrade(@UserMessage String userMessage);

}
