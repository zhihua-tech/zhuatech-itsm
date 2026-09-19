/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.itsm;

import cn.zhuatech.itsm.service.SlaBreachForecastService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
class SlaBreachForecastServiceTests {
    private final SlaBreachForecastService service = new SlaBreachForecastService();

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test
    void predictsBreachFromWorkAndQueueDelay() {
        var result = service.forecast(new SlaBreachForecastService.Request(
            "INC-2026-1001", 180, 240, 80, 3, "P1"));

        assertEquals(290, result.projectedResolutionMinutes());
        assertEquals(-50, result.bufferMinutes());
        assertEquals("BREACH_LIKELY", result.decision());
        assertTrue(result.actions().size() >= 2);
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test
    void keepsLowLoadTicketOnTrack() {
        var result = service.forecast(new SlaBreachForecastService.Request(
            "INC-2026-1002", 20, 240, 30, 1, "P3"));

        assertEquals("ON_TRACK", result.decision());
    }
}
