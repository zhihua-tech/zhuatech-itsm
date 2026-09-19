/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.itsm.service;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Service
public class MajorIncidentTriageService {
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record Request(
        @Min(0) int affectedUsers,
        @Min(0) int criticalServicesDown,
        @NotNull Boolean dataLossSuspected,
        @NotNull Boolean securityIncident,
        @NotNull Boolean workaroundAvailable,
        @Min(0) int elapsedMinutes
    ) {}

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record Result(String severity, boolean warRoomRequired, boolean executiveCommunicationRequired,
                         int nextUpdateMinutes, List<String> actions) {}

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Result triage(Request request) {
        boolean severe = Boolean.TRUE.equals(request.dataLossSuspected())
            || Boolean.TRUE.equals(request.securityIncident())
            || request.criticalServicesDown() >= 2
            || (request.criticalServicesDown() >= 1 && request.affectedUsers() >= 500);
        boolean moderate = request.criticalServicesDown() >= 1 || request.affectedUsers() >= 100
            || (request.elapsedMinutes() >= 60 && !Boolean.TRUE.equals(request.workaroundAvailable()));
        String severity = severe ? "P1" : moderate ? "P2" : "P3";
        boolean warRoom = !"P3".equals(severity);
        boolean executiveComms = "P1".equals(severity);
        int nextUpdate = "P1".equals(severity) ? 15 : "P2".equals(severity) ? 30 : 60;

        List<String> actions = new ArrayList<>();
        if (warRoom) actions.add("建立跨团队事件作战室并指定事件指挥官");
        if (Boolean.TRUE.equals(request.securityIncident())) actions.add("隔离受影响资产并保全安全证据");
        if (Boolean.TRUE.equals(request.dataLossSuspected())) actions.add("暂停破坏性操作并验证备份完整性");
        if (!Boolean.TRUE.equals(request.workaroundAvailable())) actions.add("并行组织临时绕行方案和根因定位");
        actions.add("按分级时限更新状态页和相关方");
        return new Result(severity, warRoom, executiveComms, nextUpdate, List.copyOf(actions));
    }
}
