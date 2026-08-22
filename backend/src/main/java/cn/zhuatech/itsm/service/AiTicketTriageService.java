/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.itsm.service;

import cn.zhuatech.itsm.ai.OpenAiCompatibleGateway;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class AiTicketTriageService {
    private final OpenAiCompatibleGateway gateway;
    public AiTicketTriageService(OpenAiCompatibleGateway gateway) { this.gateway = gateway; }

    public Result triage(Request request) {
        String text = (request.title() + " " + request.description()).toLowerCase(Locale.ROOT);
        String category;
        String queue;
        if (containsAny(text, "勒索", "钓鱼", "病毒", "泄露", "攻击", "security", "phishing")) {
            category = "SECURITY"; queue = "安全响应组";
        } else if (containsAny(text, "密码", "登录", "权限", "账号", "access", "password")) {
            category = "ACCESS"; queue = "身份与权限组";
        } else if (containsAny(text, "网络", "断网", "vpn", "dns", "network")) {
            category = "NETWORK"; queue = "网络运维组";
        } else if (containsAny(text, "数据库", "sql", "慢查询", "database")) {
            category = "DATABASE"; queue = "数据库组";
        } else {
            category = "APPLICATION"; queue = "应用支持组";
        }

        int urgency = 10;
        List<String> actions = new ArrayList<>();
        if (Boolean.TRUE.equals(request.serviceDown())) { urgency += 45; actions.add("立即确认服务范围并启动恢复流程"); }
        if (request.affectedUsers() >= 100) { urgency += 25; actions.add("建立影响用户清单并发布状态通知"); }
        if (Boolean.TRUE.equals(request.vipUser())) { urgency += 10; actions.add("同步客户经理或业务负责人"); }
        if ("SECURITY".equals(category)) { urgency += 30; actions.add("隔离风险资产并保全证据"); }
        urgency = Math.min(100, urgency);
        String priority = urgency >= 80 ? "P1" : urgency >= 55 ? "P2" : urgency >= 30 ? "P3" : "P4";
        if (actions.isEmpty()) actions.add("收集复现步骤、截图、时间点和环境信息");

        String context = "标题=%s，分类=%s，优先级=%s，队列=%s，描述=%s"
            .formatted(request.title(), category, priority, queue, request.description());
        var enhanced = gateway.complete("你是 IT 服务台分诊助手，请生成首响话术、排障问题和安全注意事项。", context);
        var metadata = gateway.metadata();
        return new Result(category, priority, queue, urgency, List.copyOf(actions),
            enhanced.orElse("已分派至%s，建议先执行：%s".formatted(queue, actions.getFirst())),
            enhanced.isPresent() ? "EXTERNAL_MODEL" : "LOCAL_RULES", metadata.provider(), metadata.model());
    }

    private boolean containsAny(String text, String... keywords) {
        for (String keyword : keywords) if (text.contains(keyword)) return true;
        return false;
    }

    public record Request(@NotBlank String title, @NotBlank @Size(max = 8000) String description,
                          @Min(0) int affectedUsers, @NotNull Boolean vipUser, @NotNull Boolean serviceDown) {}
    public record Result(String category, String priority, String assignmentQueue, int urgencyScore,
                         List<String> firstResponseActions, String suggestedReply,
                         String aiMode, String provider, String model) {}
}
