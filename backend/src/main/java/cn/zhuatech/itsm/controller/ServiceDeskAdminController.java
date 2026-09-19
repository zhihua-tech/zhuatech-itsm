/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.itsm.controller; import cn.zhuatech.itsm.common.ApiResponse; import cn.zhuatech.itsm.dto.ItsmDto.*; import cn.zhuatech.itsm.service.ItsmService; import org.springframework.security.access.prepost.PreAuthorize; import org.springframework.web.bind.annotation.*; import java.util.List;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@RestController @RequestMapping("/api/admin") @PreAuthorize("hasAnyRole('SERVICE_MANAGER','QUALITY','ADMIN')") public class ServiceDeskAdminController {private final ItsmService itsm;/**
                                                                                                                                                                                        * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                        */
public ServiceDeskAdminController(ItsmService itsm){this.itsm=itsm;}/**
                                                                                                                                                                                                                                                            * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                                                                                            */
@GetMapping("/dashboard") public ApiResponse<Dashboard> dashboard(){return ApiResponse.ok(itsm.adminDashboard());}/**
                                                                                                                                                                                                                                                                                                                                                                              * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                                                                                                                                                                                                              */
@GetMapping("/work-orders") public ApiResponse<List<ServiceTicketView>> orders(){return ApiResponse.ok(itsm.serviceTickets());}}
