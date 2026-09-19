/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.itsm.controller;
import cn.zhuatech.itsm.common.ApiResponse; import cn.zhuatech.itsm.service.MajorIncidentClosureGovernanceService;
import jakarta.validation.Valid; import org.springframework.web.bind.annotation.*;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@RestController @RequestMapping("/api/enterprise/itsm")
public class MajorIncidentClosureGovernanceController{
 private final MajorIncidentClosureGovernanceService service;
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 public MajorIncidentClosureGovernanceController(MajorIncidentClosureGovernanceService service){this.service=service;}
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 @PostMapping("/major-incident-closure") public ApiResponse<MajorIncidentClosureGovernanceService.Assessment> assess(@Valid @RequestBody MajorIncidentClosureGovernanceService.Request request){return ApiResponse.ok("重大事件关闭评估完成",service.assess(request));}
}
