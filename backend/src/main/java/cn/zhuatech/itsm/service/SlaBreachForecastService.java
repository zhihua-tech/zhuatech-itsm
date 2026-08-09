/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.itsm.service;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SlaBreachForecastService {
    public Result forecast(Request request) {
        int queueDelay = request.currentQueueDepth() * 10;
        int projectedResolutionMinutes = request.elapsedMinutes() + request.remainingWorkMinutes() + queueDelay;
        int bufferMinutes = request.slaMinutes() - projectedResolutionMinutes;
        int consumptionScore = Math.min(60, request.elapsedMinutes() * 60 / request.slaMinutes());
        int overrunScore = Math.min(30, Math.max(0, -bufferMinutes) * 30 / request.slaMinutes());
        int priorityScore = "P1".equals(request.priority()) ? 20 : "P2".equals(request.priority()) ? 10 : 0;
        int riskScore = Math.min(100, consumptionScore + overrunScore + priorityScore);
        String decision = bufferMinutes < 0 ? "BREACH_LIKELY"
            : bufferMinutes < Math.ceil(request.slaMinutes() * .15) ? "AT_RISK" : "ON_TRACK";

        List<String> actions = new ArrayList<>();
        if (request.currentQueueDepth() >= 5) actions.add("转派至低负载支持组或启用协同处理");
        if ("P1".equals(request.priority())) actions.add("启动重大事件沟通节奏并同步业务负责人");
        if (!"ON_TRACK".equals(decision)) actions.add("锁定下一处理人、更新时间点与客户沟通计划");
        if (actions.isEmpty()) actions.add("按当前处理计划推进并持续更新工时");
        return new Result(request.ticketNo(), projectedResolutionMinutes, bufferMinutes,
            riskScore, decision, actions);
    }

    public record Request(@NotBlank String ticketNo, @Min(0) int elapsedMinutes,
                          @Min(1) int slaMinutes, @Min(0) int remainingWorkMinutes,
                          @Min(0) int currentQueueDepth,
                          @Pattern(regexp = "P[1-4]") String priority) {}

    public record Result(String ticketNo, int projectedResolutionMinutes, int bufferMinutes,
                         int riskScore, String decision, List<String> actions) {}
}
