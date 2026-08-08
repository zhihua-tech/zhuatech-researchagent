/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.researchagent.agent;
import org.springframework.stereotype.Component; import java.util.List; import java.util.Map;
/** 企业研究情报智能体平台运行边界；默认演示执行器不连接真实模型、业务系统或外部通信渠道。 */
public interface AgentRuntime {
 AgentResult run(AgentRequest request);
 record AgentRequest(String objective,Map<String,String> context){}
 record AgentStep(String name,String status,String evidence){}
 record AgentResult(String runtime,String summary,List<AgentStep> steps,Map<String,Object> metrics){}
}
@Component class DemoAgentRuntime implements AgentRuntime {
 public AgentResult run(AgentRequest request){
  return new AgentResult("research-evidence-demo","已完成多源检索、来源分级和事实交叉验证，趋势预测等待研究负责人确认。",List.of(new AgentStep("研究拆题","COMPLETED","形成 6 个可验证子问题"),new AgentStep("来源核验","COMPLETED","引用 12 个一级来源"),new AgentStep("观点发布","PENDING","等待研究负责人审阅")),Map.of("evidenceItems",24,"suggestedActions",4,"objectiveLength",request.objective().length()));
 }
}
