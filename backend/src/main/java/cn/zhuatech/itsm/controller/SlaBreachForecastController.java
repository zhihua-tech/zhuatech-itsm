/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.itsm.controller;

import cn.zhuatech.itsm.common.ApiResponse;
import cn.zhuatech.itsm.service.SlaBreachForecastService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@RestController
@RequestMapping("/api/itsm/insights")
public class SlaBreachForecastController {
    private final SlaBreachForecastService service;

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public SlaBreachForecastController(SlaBreachForecastService service) {
        this.service = service;
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @PostMapping("/sla-breach-forecast")
    public ApiResponse<SlaBreachForecastService.Result> forecast(
        @Valid @RequestBody SlaBreachForecastService.Request request) {
        return ApiResponse.ok(service.forecast(request));
    }
}
