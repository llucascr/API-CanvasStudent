package com.api.canvas.student.service.tools;

import com.api.canvas.student.dto.response.userSubjectGrade.GradesForSubjectResponse;
import com.api.canvas.student.dto.response.userSubjectGrade.SubjectGradeByUserResponse;
import com.api.canvas.student.service.UserSubjectGradeService;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PagedModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class AssistantTools {

    private final UserSubjectGradeService userSubjectGradeService;

    private final UserContext userContext;

    @Tool("Calcula a média ponderada das notas de uma disciplina específica de um usuário. " +
            "Retorna um JSON com a lista de notas e pesos, a média ponderada " +
            "e a situação APROVADO se a média for >= 6.0 ou REPROVADO caso contrário.")
    public String calculateGrade(
            @P("Nome da disciplina extraído da mensagem do usuário") String subjectName
    ) {

        Long userId = userContext.getUserId();

        List<GradesForSubjectResponse> subjects = userSubjectGradeService
                .findAllGradesForSubjects(userId, subjectName);

        BigDecimal media = subjects.stream()
                .map(GradesForSubjectResponse::grade)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(new BigDecimal(2), 2, RoundingMode.HALF_UP);

        return String.format("""
        {
            "grades": [%s],
            "weightedAverage": %s,
            "status": "%s"
        }
        """,
                subjects.stream()
                        .map(s -> String.format("""
                        {"grade": %s, "weight": %s}""",
                                s.grade(), s.weight()))
                        .collect(Collectors.joining(", ")),
                media,
                media.compareTo(new BigDecimal("6.0")) >= 0 ? "APPROVED" : "FAILED"
        );

    }

}
