/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.itsm.controller;
import cn.zhuatech.itsm.common.ApiResponse;
import cn.zhuatech.itsm.service.AiTicketTriageService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@RestController @RequestMapping("/api/itsm/ai")
public class AiTicketTriageController {
    private final AiTicketTriageService service;
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public AiTicketTriageController(AiTicketTriageService service) { this.service = service; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @PostMapping("/ticket-triage")
    public ApiResponse<AiTicketTriageService.Result> triage(@Valid @RequestBody AiTicketTriageService.Request request) {
        return ApiResponse.ok(service.triage(request));
    }
}
