/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.researchagent.workbench;
import org.junit.jupiter.api.Test;
import java.math.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.security.authentication.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.http.MediaType;
/** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
class AiWorkbenchServiceTest {
 /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
 @Test void coversAdditionalBoundaryOne(){var r=service.execute(Map.of("sources",List.of(),"claims",List.of(Map.of("id","c","text","claim","sourceIds",List.of("missing")))));assertEquals(false,r.get("complete"));}
 /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
 @Test void coversAdditionalBoundaryTwo(){var r=service.execute(Map.of("sources",List.of(),"claims",List.of()));assertEquals(false,r.get("complete"));}
 private final AiWorkbenchService service=new AiWorkbenchService();
 /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
 private Map<String,Object> sample(){return Map.<String,Object>ofEntries(Map.entry("sources",List.of(Map.<String,Object>ofEntries(Map.entry("id","s1"),Map.entry("url","https://example.com/report"),Map.entry("excerpt","Revenue increased.")))),Map.entry("claims",List.of(Map.<String,Object>ofEntries(Map.entry("id","c1"),Map.entry("text","Revenue grew"),Map.entry("sourceIds",List.of("s1"))),Map.<String,Object>ofEntries(Map.entry("id","c2"),Map.entry("text","Costs dropped"),Map.entry("sourceIds",List.of())))));}
 /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
 @Test void computesBusinessResult(){var result=service.execute(sample());assertEquals(false,result.get("complete"));assertEquals(List.of("c2"),result.get("unsupportedClaims"));}
 /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
 @Test void coversFeatureBoundary(){var bad=Map.<String,Object>of("sources",List.of(Map.of("id","s1","url","javascript:alert(1)","excerpt","text")),"claims",List.of());assertThrows(IllegalArgumentException.class,()->service.execute(bad));}
 /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
 @Test void deterministicAndDoesNotMutateInput(){var input=sample();String before=input.toString();assertEquals(service.execute(input),service.execute(input));assertEquals(before,input.toString());}
 /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
 @Test void rejectsNull(){assertThrows(IllegalArgumentException.class,()->service.execute(null));}
 /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
 @Test void rejectsMissingFields(){assertThrows(IllegalArgumentException.class,()->service.execute(Map.of()));}
 /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
 @Test void rejectsUnknownFields(){var input=new HashMap<>(sample());input.put("__unknown",true);assertThrows(IllegalArgumentException.class,()->service.execute(input));}
 /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
 @Test void rejectsOversizedText(){assertThrows(IllegalArgumentException.class,()->service.execute(Map.of("payload","x".repeat(64001))));}
 /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
 @Test void rejectsDeepInput(){Map<String,Object> v=Map.of("x",1);for(int i=0;i<14;i++)v=Map.of("x",v);final var input=v;assertThrows(IllegalArgumentException.class,()->service.execute(input));}
 /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
 @Test void httpRequiresAuthentication()throws Exception{var mvc=MockMvcBuilders.standaloneSetup(new AiWorkbenchController(service)).build();mvc.perform(post("/api/workbench/citation-ledger").contentType(MediaType.APPLICATION_JSON).content("{\"sources\":[{\"id\":\"s1\",\"url\":\"https://example.com/report\",\"excerpt\":\"Revenue increased.\"}],\"claims\":[{\"id\":\"c1\",\"text\":\"Revenue grew\",\"sourceIds\":[\"s1\"]},{\"id\":\"c2\",\"text\":\"Costs dropped\",\"sourceIds\":[]}]}")).andExpect(status().isUnauthorized());}
 /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
 @Test void httpAllowsAuthenticatedComputation()throws Exception{var mvc=MockMvcBuilders.standaloneSetup(new AiWorkbenchController(service)).build();mvc.perform(post("/api/workbench/citation-ledger").principal(new TestingAuthenticationToken("test-user","unused","ROLE_OPERATOR")).contentType(MediaType.APPLICATION_JSON).content("{\"sources\":[{\"id\":\"s1\",\"url\":\"https://example.com/report\",\"excerpt\":\"Revenue increased.\"}],\"claims\":[{\"id\":\"c1\",\"text\":\"Revenue grew\",\"sourceIds\":[\"s1\"]},{\"id\":\"c2\",\"text\":\"Costs dropped\",\"sourceIds\":[]}]}")).andExpect(status().isOk()).andExpect(jsonPath("$.success").value(true));}
 /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
 @Test void httpRejectsInvalidInput()throws Exception{var mvc=MockMvcBuilders.standaloneSetup(new AiWorkbenchController(service)).build();mvc.perform(post("/api/workbench/citation-ledger").principal(new TestingAuthenticationToken("test-user","unused","ROLE_OPERATOR")).contentType(MediaType.APPLICATION_JSON).content("{}")).andExpect(status().isBadRequest()).andExpect(jsonPath("$.success").value(false));}
 /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
 @Test void rejectsAnonymousAuthenticationObject(){var actor=new AnonymousAuthenticationToken("test-key","anonymous",List.of(new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_ANONYMOUS")));assertEquals(401,new AiWorkbenchController(service).run(sample(),actor).getStatusCode().value());}
}
