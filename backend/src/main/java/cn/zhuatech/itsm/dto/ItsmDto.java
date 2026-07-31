/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
package cn.zhuatech.itsm.dto;
import jakarta.validation.constraints.*; import java.time.*; import java.util.List;
public final class ItsmDto { private ItsmDto(){}
    public record Metric(String label,String value,String hint,String tone){}
    public record ServiceTicketView(Long id,String orderNo,String productCode,String productName,String supportGroup,String workshop,int plannedQty,int completedQty,int defectQty,LocalDate dueDate,String status,String batchNo,int progress){}
    public record ConfigurationItemView(String code,String name,String supportGroup,String status,int oee,LocalDateTime lastHeartbeat){}
    public record SlaReviewView(String slaReviewNo,String orderNo,String productName,String slaReviewType,int sampleQty,int defectQty,String result,String inspector){}
    public record Dashboard(List<Metric> metrics,List<ServiceTicketView> serviceTickets,List<ConfigurationItemView> configurationItem,List<SlaReviewView> slaReviews){}
    public record ReportRequest(@NotBlank String operationName,@Positive int goodQty,@PositiveOrZero int defectQty,@Size(max=200) String remark){}
    public record ReportResult(String orderNo,int completedQty,int defectQty,int progress,String status){}
}
