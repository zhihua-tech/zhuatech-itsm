/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.itsm;

import cn.zhuatech.itsm.service.MajorIncidentTriageService;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class MajorIncidentTriageServiceTests {
    private final MajorIncidentTriageService service = new MajorIncidentTriageService();

    @Test void classifiesSecurityIncidentAsP1() {
        var result = service.triage(new MajorIncidentTriageService.Request(80, 1, false, true, false, 10));
        assertThat(result.severity()).isEqualTo("P1");
        assertThat(result.executiveCommunicationRequired()).isTrue();
    }

    @Test void classifiesSmallIncidentWithWorkaroundAsP3() {
        var result = service.triage(new MajorIncidentTriageService.Request(20, 0, false, false, true, 15));
        assertThat(result.severity()).isEqualTo("P3");
        assertThat(result.warRoomRequired()).isFalse();
    }
}
