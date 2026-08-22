/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.researchagent.controller;
import cn.zhuatech.researchagent.agent.AgentRuntime;
import cn.zhuatech.researchagent.common.ApiResponse;
import cn.zhuatech.researchagent.dto.ResearchAgentDto.*;
import cn.zhuatech.researchagent.service.ResearchAgentService;
import cn.zhuatech.researchagent.service.ResearchEvidenceService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
@RestController @RequestMapping("/api/shopfloor") @PreAuthorize("hasAnyRole('DOMAIN_USER','ADMIN')")
public class WorkspaceController {
 private final ResearchAgentService service; private final AgentRuntime runtime; private final ResearchEvidenceService domainAgent;
 public WorkspaceController(ResearchAgentService service,AgentRuntime runtime,ResearchEvidenceService domainAgent){this.service=service;this.runtime=runtime;this.domainAgent=domainAgent;}
 @GetMapping("/dashboard") public ApiResponse<Dashboard> dashboard(){return ApiResponse.ok(service.shopfloorDashboard());}
 @PostMapping("/work-orders/{id}/reports") public ApiResponse<ReportResult> report(@PathVariable Long id,@Valid @RequestBody ReportRequest request){return ApiResponse.ok("反馈提交成功",service.report(id,request));}
 @PostMapping("/agent-preview") public ApiResponse<AgentRuntime.AgentResult> preview(@RequestBody Map<String,String> body){return ApiResponse.ok(runtime.run(new AgentRuntime.AgentRequest(body.getOrDefault("objective","汇总行业信号并形成可溯源研究草稿"),Map.of("mode","demo","approval","required"))));}
 @PostMapping("/evidence-review") public ApiResponse<ResearchEvidenceService.EvidenceDecision> domainAction(@Valid @RequestBody ResearchEvidenceService.EvidenceRequest request){return ApiResponse.ok("研究证据质量检查完成",domainAgent.inspect(request));}
}
