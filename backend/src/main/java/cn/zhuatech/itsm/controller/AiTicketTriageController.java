/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.itsm.controller;
import cn.zhuatech.itsm.common.ApiResponse;
import cn.zhuatech.itsm.service.AiTicketTriageService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/itsm/ai")
public class AiTicketTriageController {
    private final AiTicketTriageService service;
    public AiTicketTriageController(AiTicketTriageService service) { this.service = service; }
    @PostMapping("/ticket-triage")
    public ApiResponse<AiTicketTriageService.Result> triage(@Valid @RequestBody AiTicketTriageService.Request request) {
        return ApiResponse.ok(service.triage(request));
    }
}
