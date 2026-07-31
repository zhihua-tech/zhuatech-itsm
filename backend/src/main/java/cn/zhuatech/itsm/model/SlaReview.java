/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
package cn.zhuatech.itsm.model;
import jakarta.persistence.*; import java.time.LocalDateTime;
@Entity @Table(name="itsm_sla_review") public class SlaReview extends BaseEntity {
    public enum Result { PENDING, PASSED, FAILED }
    @Column(nullable=false,unique=true,length=32) private String slaReviewNo; @ManyToOne(optional=false,fetch=FetchType.LAZY) private ServiceTicket serviceTicket;
    @Column(nullable=false,length=30) private String slaReviewType; @Column(nullable=false) private int sampleQty; @Column(nullable=false) private int defectQty; @Enumerated(EnumType.STRING) @Column(nullable=false,length=20) private Result result;
    @Column(length=50) private String inspector; @Column(nullable=false) private LocalDateTime createdAt;
    protected SlaReview(){} public SlaReview(String slaReviewNo,ServiceTicket serviceTicket,String slaReviewType,int sampleQty,int defectQty,Result result,String inspector){this.slaReviewNo=slaReviewNo;this.serviceTicket=serviceTicket;this.slaReviewType=slaReviewType;this.sampleQty=sampleQty;this.defectQty=defectQty;this.result=result;this.inspector=inspector;this.createdAt=LocalDateTime.now();}
    public String getSlaReviewNo(){return slaReviewNo;} public ServiceTicket getServiceTicket(){return serviceTicket;} public String getSlaReviewType(){return slaReviewType;} public int getSampleQty(){return sampleQty;} public int getDefectQty(){return defectQty;} public Result getResult(){return result;} public String getInspector(){return inspector;}
}
