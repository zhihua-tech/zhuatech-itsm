/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.itsm;

import cn.zhuatech.itsm.service.MajorIncidentTriageService;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
class MajorIncidentTriageServiceTests {
    private final MajorIncidentTriageService service = new MajorIncidentTriageService();

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test void classifiesSecurityIncidentAsP1() {
        var result = service.triage(new MajorIncidentTriageService.Request(80, 1, false, true, false, 10));
        assertThat(result.severity()).isEqualTo("P1");
        assertThat(result.executiveCommunicationRequired()).isTrue();
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test void classifiesSmallIncidentWithWorkaroundAsP3() {
        var result = service.triage(new MajorIncidentTriageService.Request(20, 0, false, false, true, 15));
        assertThat(result.severity()).isEqualTo("P3");
        assertThat(result.warRoomRequired()).isFalse();
    }
}
