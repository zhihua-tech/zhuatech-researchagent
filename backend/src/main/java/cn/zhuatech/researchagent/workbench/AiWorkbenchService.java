/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.researchagent.workbench;
import org.springframework.stereotype.Service;
import java.math.*;
import java.util.*;
import static cn.zhuatech.researchagent.workbench.WorkbenchInput.*;
/** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
@Service
public class AiWorkbenchService {
    /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
    public Map<String,Object> execute(Map<String,Object> r){
        validate(r);

        keys(r,"sources","claims");Set<String> sources=new TreeSet<>(),used=new TreeSet<>(),claims=new HashSet<>();List<String> unsupported=new ArrayList<>();List<Map<String,Object>> unknown=new ArrayList<>();
        for(var row:rows(r,"sources",0,200)){keys(row,"id","url","excerpt");String id=id(row,"id");if(!sources.add(id))throw new IllegalArgumentException("来源编号重复");text(row,"excerpt",4000);java.net.URI uri;try{uri=java.net.URI.create(text(row,"url",2000));}catch(IllegalArgumentException e){throw new IllegalArgumentException("来源地址无效");}if(!Set.of("https","http").contains(uri.getScheme())||uri.getHost()==null||uri.getUserInfo()!=null)throw new IllegalArgumentException("来源须为无凭据的HTTP(S)地址");}
        for(var row:rows(r,"claims",0,200)){keys(row,"id","text","sourceIds");String id=id(row,"id");text(row,"text",2000);if(!claims.add(id))throw new IllegalArgumentException("结论编号重复");var refs=strings(row,"sourceIds",0,100);if(new HashSet<>(refs).size()!=refs.size())throw new IllegalArgumentException("结论引用重复");boolean supported=false;for(String ref:refs)if(sources.contains(ref)){used.add(ref);supported=true;}else unknown.add(Map.of("claimId",id,"sourceId",ref));if(!supported)unsupported.add(id);}
        Collections.sort(unsupported);return Map.of("complete",!claims.isEmpty()&&unsupported.isEmpty()&&unknown.isEmpty(),"unsupportedClaims",unsupported,"unknownReferences",unknown,"unusedSources",sources.stream().filter(x->!used.contains(x)).toList(),"sourceCount",sources.size(),"claimCount",claims.size(),"verifiesTruth",false,"fetchesSources",false);

    }
}
