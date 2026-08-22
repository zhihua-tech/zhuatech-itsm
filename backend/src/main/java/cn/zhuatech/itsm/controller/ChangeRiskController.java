/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.itsm.controller;
import cn.zhuatech.itsm.common.ApiResponse; import cn.zhuatech.itsm.service.ChangeRiskService; import jakarta.validation.Valid; import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/admin") public class ChangeRiskController {private final ChangeRiskService service; public ChangeRiskController(ChangeRiskService service){this.service=service;} @PostMapping("/change-risk") public ApiResponse<ChangeRiskService.Result> evaluate(@Valid @RequestBody ChangeRiskService.Request request){return ApiResponse.ok(service.evaluate(request));}}

