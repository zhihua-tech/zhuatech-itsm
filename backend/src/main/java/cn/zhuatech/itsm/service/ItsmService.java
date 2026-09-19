/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.itsm.service;
import cn.zhuatech.itsm.common.BusinessException; import cn.zhuatech.itsm.dto.ItsmDto.*; import cn.zhuatech.itsm.model.*; import cn.zhuatech.itsm.repository.*; import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional; import java.util.*;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Service @Transactional(readOnly=true) public class ItsmService {
    private final ServiceTicketRepository orders; private final WorklogRepository reports; private final ConfigurationItemRepository configurationItem; private final SlaReviewRepository slaReviews; private final CurrentUserService current;
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public ItsmService(ServiceTicketRepository orders,WorklogRepository reports,ConfigurationItemRepository configurationItem,SlaReviewRepository slaReviews,CurrentUserService current){this.orders=orders;this.reports=reports;this.configurationItem=configurationItem;this.slaReviews=slaReviews;this.current=current;}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Dashboard shopfloorDashboard(){String center=current.get().getSupportGroupCode();List<ServiceTicket> list=center==null?orders.findAllByOrderByDueDateAsc():orders.findBySupportGroupCodeOrderByDueDateAsc(center);return dashboard(list);}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Dashboard adminDashboard(){return dashboard(orders.findAllByOrderByDueDateAsc());}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public List<ServiceTicketView> serviceTickets(){return orders.findAllByOrderByDueDateAsc().stream().map(this::view).toList();}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Transactional public ReportResult report(Long id,ReportRequest request){ServiceTicket order=orders.findById(id).orElseThrow(()->new BusinessException("服务工单不存在"));if(order.getStatus()==ServiceTicket.Status.COMPLETED)throw new BusinessException("已关闭工单不能继续反馈");if(order.getCompletedQty()+request.goodQty()>order.getPlannedQty())throw new BusinessException("已解决事项不能超过剩余事项");order.report(request.goodQty(),request.defectQty());reports.save(new Worklog(order,request.operationName(),request.goodQty(),request.defectQty(),current.get().getFullName(),request.remark()));return new ReportResult(order.getOrderNo(),order.getCompletedQty(),order.getDefectQty(),progress(order),order.getStatus().name());}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    private Dashboard dashboard(List<ServiceTicket> list){int planned=list.stream().mapToInt(ServiceTicket::getPlannedQty).sum(),done=list.stream().mapToInt(ServiceTicket::getCompletedQty).sum(),defects=list.stream().mapToInt(ServiceTicket::getDefectQty).sum();int rate=planned==0?0:Math.round(done*100f/planned);List<Metric> metrics=List.of(new Metric("工单事项",String.format("%,d",planned),list.size()+" 张服务工单","blue"),new Metric("工单解决率",rate+"%",String.format("%,d / %,d",done,planned),"green"),new Metric("SLA 达标率",String.format("%.1f%%",done+defects==0?100d:done*100d/(done+defects)),defects+" 项阻塞","warn"),new Metric("服务异常",configurationItem.countByStatus(ConfigurationItem.Status.ALARM)+"",slaReviews.countByResult(SlaReview.Result.PENDING)+" 项 SLA 待确认","red"));return new Dashboard(metrics,list.stream().map(this::view).toList(),configurationItem.findAllByOrderByCodeAsc().stream().map(e->new ConfigurationItemView(e.getCode(),e.getName(),e.getSupportGroup().getName(),e.getStatus().name(),e.getOee(),e.getLastHeartbeat())).toList(),slaReviews.findTop10ByOrderByIdDesc().stream().map(i->new SlaReviewView(i.getSlaReviewNo(),i.getServiceTicket().getOrderNo(),i.getServiceTicket().getProductName(),i.getSlaReviewType(),i.getSampleQty(),i.getDefectQty(),i.getResult().name(),i.getInspector())).toList());}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    private ServiceTicketView view(ServiceTicket o){return new ServiceTicketView(o.getId(),o.getOrderNo(),o.getProductCode(),o.getProductName(),o.getSupportGroup().getName(),o.getSupportGroup().getWorkshop(),o.getPlannedQty(),o.getCompletedQty(),o.getDefectQty(),o.getDueDate(),o.getStatus().name(),o.getBatchNo(),progress(o));}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    private int progress(ServiceTicket o){return o.getPlannedQty()==0?0:Math.min(100,Math.round(o.getCompletedQty()*100f/o.getPlannedQty()));}
}
