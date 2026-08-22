/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.itsm.config;

import cn.zhuatech.itsm.model.*;
import cn.zhuatech.itsm.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner seed(SupportGroupRepository groups, ServiceTicketRepository tickets,
                           ConfigurationItemRepository configurationItems, SlaReviewRepository reviews,
                           UserRepository users, PasswordEncoder encoder) {
        return args -> {
            if (groups.count() > 0) return;
            SupportGroup app = groups.save(new SupportGroup("SG-APP", "企业应用支持组", "信息技术部", 240));
            SupportGroup infra = groups.save(new SupportGroup("SG-INFRA", "基础设施支持组", "信息技术部", 180));
            SupportGroup security = groups.save(new SupportGroup("SG-SEC", "信息安全响应组", "风险管理部", 96));

            ServiceTicket t1 = tickets.save(new ServiceTicket("INC-260801-018", "ERP-ORDER", "ERP 销售订单提交缓慢", app, 12, 8, 1, LocalDate.now().plusDays(1), ServiceTicket.Status.RUNNING, "P2"));
            ServiceTicket t2 = tickets.save(new ServiceTicket("REQ-260801-021", "IAM-ACCESS", "新员工研发环境权限开通", security, 8, 3, 0, LocalDate.now().plusDays(2), ServiceTicket.Status.RUNNING, "P3"));
            ServiceTicket t3 = tickets.save(new ServiceTicket("CHG-260802-006", "NET-CORE", "核心交换机固件变更", infra, 16, 0, 0, LocalDate.now().plusDays(4), ServiceTicket.Status.RELEASED, "P2"));
            ServiceTicket t4 = tickets.save(new ServiceTicket("INC-260731-015", "MAIL-GW", "邮件网关投递延迟", infra, 10, 10, 1, LocalDate.now(), ServiceTicket.Status.COMPLETED, "P1"));

            configurationItems.saveAll(List.of(
                new ConfigurationItem("CI-ERP-PRD", "ERP 生产应用集群", app, ConfigurationItem.Status.RUNNING, 96),
                new ConfigurationItem("CI-NET-CORE", "园区核心交换网络", infra, ConfigurationItem.Status.RUNNING, 91),
                new ConfigurationItem("CI-MAIL-GW", "邮件安全网关", infra, ConfigurationItem.Status.ALARM, 67),
                new ConfigurationItem("CI-IAM-01", "统一身份认证平台", security, ConfigurationItem.Status.IDLE, 82)
            ));
            reviews.saveAll(List.of(
                new SlaReview("SLA-260801-032", t1, "响应时限复核", 4, 0, SlaReview.Result.PASSED, "服务台"),
                new SlaReview("SLA-260801-011", t2, "审批完整性", 3, 0, SlaReview.Result.PASSED, "周妍"),
                new SlaReview("SLA-260731-018", t4, "解决时限复盘", 5, 1, SlaReview.Result.FAILED, "沈清和"),
                new SlaReview("SLA-260802-003", t3, "变更窗口确认", 2, 0, SlaReview.Result.PENDING, "陆承")
            ));
            String demo = encoder.encode("Demo@2026");
            users.saveAll(List.of(
                new UserAccount("operator", demo, "陆承", UserAccount.Role.AGENT, "SG-APP"),
                new UserAccount("planner", demo, "沈清和", UserAccount.Role.SERVICE_MANAGER, null),
                new UserAccount("quality", demo, "周妍", UserAccount.Role.QUALITY, null),
                new UserAccount("admin", encoder.encode("ZhuaTech@2026"), "系统管理员", UserAccount.Role.ADMIN, null)
            ));
        };
    }
}
