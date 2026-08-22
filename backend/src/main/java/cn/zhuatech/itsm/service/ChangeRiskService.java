/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.itsm.service;
import jakarta.validation.constraints.*; import org.springframework.stereotype.Service; import java.util.ArrayList; import java.util.List;
@Service public class ChangeRiskService {
    public Result evaluate(Request r){
        int score=Math.min(25,r.affectedServices()*5)+Math.min(20,r.affectedUsers()/500)+Math.min(20,r.recentFailureCount()*7);
        if(!r.rollbackTested())score+=25; if(!r.backupVerified())score+=15; if(!r.monitoringReady())score+=10; if(r.emergency())score+=10; if(r.changeWindowMinutes()<30)score+=8;
        score=Math.min(100,score);
        String decision=!r.rollbackTested()||!r.backupVerified()||score>=75?"REJECT":r.emergency()||r.affectedServices()>3||score>=40?"CAB_REQUIRED":"STANDARD";
        List<String> controls=new ArrayList<>(); if(!r.rollbackTested())controls.add("完成回退方案演练并留存证据"); if(!r.backupVerified())controls.add("验证备份可用性与恢复时间"); if(!r.monitoringReady())controls.add("补充变更后监控和告警观察项"); if(r.recentFailureCount()>0)controls.add("复盘近期同类变更失败原因");
        return new Result(score,decision,!"STANDARD".equals(decision),controls);
    }
    public record Request(@NotBlank String changeNo,@Min(1) int affectedServices,@Min(0) int affectedUsers,
        boolean rollbackTested,boolean backupVerified,boolean monitoringReady,@Min(1) int changeWindowMinutes,
        @Min(0) int recentFailureCount,boolean emergency){}
    public record Result(int riskScore,String decision,boolean approvalRequired,List<String> controls){}
}

