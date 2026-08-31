/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.itsm.service;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
@Service
public class MajorIncidentClosureGovernanceService {
    public Assessment assess(Request request) {
        List<String> blockers=new ArrayList<>(); List<String> actions=new ArrayList<>();
        if(!request.serviceRestored()) blockers.add("业务服务尚未恢复");
        if(!request.monitoringStable()) blockers.add("恢复后监控稳定期未达标");
        if(!request.stakeholderCommunicationComplete()) blockers.add("客户与管理层终结通报未完成");
        if(!request.timelineCaptured()) actions.add("补齐事件时间线和关键决策记录");
        if(!request.rootCauseKnown()) actions.add("创建问题记录并推进根因分析");
        if(!request.correctiveActionsOwned()) actions.add("为纠正措施指定负责人和期限");
        if(!request.problemRecordLinked()) actions.add("关联问题、变更和知识记录");
        if(request.securityIncident()&&!request.securityReviewComplete()) blockers.add("安全事件复核与证据保全未完成");
        Decision decision=!blockers.isEmpty()?Decision.HOLD:!actions.isEmpty()?Decision.REVIEW:Decision.CLOSE;
        return new Assessment(request.incidentNo(),decision,List.copyOf(blockers),List.copyOf(actions));
    }
    public record Request(@NotBlank String incidentNo,boolean serviceRestored,boolean monitoringStable,
                          boolean stakeholderCommunicationComplete,boolean timelineCaptured,
                          boolean rootCauseKnown,boolean correctiveActionsOwned,boolean problemRecordLinked,
                          boolean securityIncident,boolean securityReviewComplete){}
    public record Assessment(String incidentNo,Decision decision,List<String> blockers,List<String> actions){}
    public enum Decision{CLOSE,REVIEW,HOLD}
}
