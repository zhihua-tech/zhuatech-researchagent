/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.researchagent.service;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;

import java.util.List;

/** 研究证据评估：结论必须区分事实、推断和预测，并保留来源。 */
@Service
public class ResearchEvidenceService {
    public record EvidenceRequest(
            @NotBlank String topic,
            @Min(0) @Max(1000) int sourceCount,
            @Min(0) @Max(1000) int primarySourceCount,
            boolean containsPrediction,
            boolean publicationApproved) {}

    public record EvidenceDecision(
            boolean publishAllowed,
            String route,
            int confidence,
            List<String> controls) {}

    public EvidenceDecision inspect(EvidenceRequest request) {
        int confidence = request.sourceCount() == 0 ? 0
                : Math.min(100, 45 + request.primarySourceCount() * 55 / request.sourceCount());
        boolean review = confidence < 75 || request.containsPrediction();
        boolean allowed = !review || request.publicationApproved();
        String route = request.sourceCount() < 3 ? "EVIDENCE_GAP"
                : request.containsPrediction() ? "FORECAST_REVIEW"
                : confidence < 75 ? "SOURCE_REVIEW" : "DRAFT_READY";
        return new EvidenceDecision(allowed, route, confidence, List.of(
                "优先引用监管、企业公告和原始研究",
                "标记发布日期、适用区域和信息时效",
                "预测和战略建议必须经过研究负责人复核"));
    }
}
