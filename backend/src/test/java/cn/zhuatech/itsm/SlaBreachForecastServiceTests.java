/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.itsm;

import cn.zhuatech.itsm.service.SlaBreachForecastService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SlaBreachForecastServiceTests {
    private final SlaBreachForecastService service = new SlaBreachForecastService();

    @Test
    void predictsBreachFromWorkAndQueueDelay() {
        var result = service.forecast(new SlaBreachForecastService.Request(
            "INC-2026-1001", 180, 240, 80, 3, "P1"));

        assertEquals(290, result.projectedResolutionMinutes());
        assertEquals(-50, result.bufferMinutes());
        assertEquals("BREACH_LIKELY", result.decision());
        assertTrue(result.actions().size() >= 2);
    }

    @Test
    void keepsLowLoadTicketOnTrack() {
        var result = service.forecast(new SlaBreachForecastService.Request(
            "INC-2026-1002", 20, 240, 30, 1, "P3"));

        assertEquals("ON_TRACK", result.decision());
    }
}
