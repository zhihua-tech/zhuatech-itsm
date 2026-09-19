/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.itsm;
import cn.zhuatech.itsm.ai.OpenAiCompatibleGateway;
import cn.zhuatech.itsm.service.AiTicketTriageService;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
class AiTicketTriageServiceTests {
    private final AiTicketTriageService service = new AiTicketTriageService(
        new OpenAiCompatibleGateway("local", "https://api.deepseek.com", "deepseek-chat", ""));
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test void escalatesSecurityOutage() {
        var result = service.triage(new AiTicketTriageService.Request("疑似勒索病毒导致服务中断",
            "文件被加密，多个部门无法访问业务系统", 500, true, true));
        assertThat(result.category()).isEqualTo("SECURITY");
        assertThat(result.priority()).isEqualTo("P1");
        assertThat(result.assignmentQueue()).isEqualTo("安全响应组");
    }
}
