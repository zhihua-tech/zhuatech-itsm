/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.itsm.controller;

import cn.zhuatech.itsm.common.ApiResponse;
import cn.zhuatech.itsm.service.MajorIncidentTriageService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@RestController
@RequestMapping("/api/itsm/insights")
public class MajorIncidentTriageController {
    private final MajorIncidentTriageService service;
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public MajorIncidentTriageController(MajorIncidentTriageService service) { this.service = service; }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @PostMapping("/major-incident-triage")
    public ApiResponse<MajorIncidentTriageService.Result> triage(
        @Valid @RequestBody MajorIncidentTriageService.Request request) {
        return ApiResponse.ok(service.triage(request));
    }
}
