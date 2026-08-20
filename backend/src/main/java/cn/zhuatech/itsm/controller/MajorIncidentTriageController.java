/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.itsm.controller;

import cn.zhuatech.itsm.common.ApiResponse;
import cn.zhuatech.itsm.service.MajorIncidentTriageService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/itsm/insights")
public class MajorIncidentTriageController {
    private final MajorIncidentTriageService service;
    public MajorIncidentTriageController(MajorIncidentTriageService service) { this.service = service; }

    @PostMapping("/major-incident-triage")
    public ApiResponse<MajorIncidentTriageService.Result> triage(
        @Valid @RequestBody MajorIncidentTriageService.Request request) {
        return ApiResponse.ok(service.triage(request));
    }
}
