/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.itsm.deep;import jakarta.persistence.*;import java.time.*;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Entity @Table(name="deep_itsm_ci",uniqueConstraints=@UniqueConstraint(columnNames="ci_code")) class ManagedCI{@Id@GeneratedValue(strategy=GenerationType.IDENTITY)Long id;@Column(name="ci_code")String ciCode;String name;String type;String criticality;String owner;String status="DRAFT";@Version long lockVersion;/**
                                                                                                                                                                                                                                                                                                                         * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                                                                                                                                                         */
protected ManagedCI(){}/**
                                                                                                                                                                                                                                                                                                                                                * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                                                                                                                                                                                */
ManagedCI(String c,String n,String t,String cr,String o){ciCode=c;name=n;type=t;criticality=cr;owner=o;}}
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Entity @Table(name="deep_itsm_incident",uniqueConstraints=@UniqueConstraint(columnNames="incident_no")) class ItsmIncident{@Id@GeneratedValue(strategy=GenerationType.IDENTITY)Long id;@Column(name="incident_no")String incidentNo;Long ciId;String priority;String summary;String status="OPEN";String assignee;LocalDateTime dueAt;boolean major;String resolution;LocalDateTime resolvedAt;@Version long lockVersion;/**
                                                                                                                                                                                                                                                                                                                                                                                                                           * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                                                                                                                                                                                                                                                           */
protected ItsmIncident(){}/**
                                                                                                                                                                                                                                                                                                                                                                                                                                                     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                                                                                                                                                                                                                                                                                     */
ItsmIncident(String n,Long c,String p,String s,int h){incidentNo=n;ciId=c;priority=p;summary=s;major="P1".equals(p);dueAt=LocalDateTime.now().plusHours(h);}}
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Entity @Table(name="deep_itsm_problem",uniqueConstraints=@UniqueConstraint(columnNames="problem_no")) class ItsmProblem{@Id@GeneratedValue(strategy=GenerationType.IDENTITY)Long id;@Column(name="problem_no")String problemNo;Long incidentId;String rootCause;String knownError;String workaround;String status="OPEN";@Version long lockVersion;/**
                                                                                                                                                                                                                                                                                                                                                     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                                                                                                                                                                                     */
protected ItsmProblem(){}/**
                                                                                                                                                                                                                                                                                                                                                                              * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                                                                                                                                                                                                              */
ItsmProblem(String n,Long i,String r,String k,String w){problemNo=n;incidentId=i;rootCause=r;knownError=k;workaround=w;}}
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Entity @Table(name="deep_itsm_change",uniqueConstraints=@UniqueConstraint(columnNames="change_no")) class ItsmChange{@Id@GeneratedValue(strategy=GenerationType.IDENTITY)Long id;@Column(name="change_no")String changeNo;Long ciId;String changeType;int riskScore;String description;String rollbackPlan;String status="DRAFT";String evidenceHash;@Version long lockVersion;/**
                                                                                                                                                                                                                                                                                                                                                                                 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                                                                                                                                                                                                                 */
protected ItsmChange(){}/**
                                                                                                                                                                                                                                                                                                                                                                                                         * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                                                                                                                                                                                                                                         */
ItsmChange(String n,Long c,String t,int r,String d,String p){changeNo=n;ciId=c;changeType=t;riskScore=r;description=d;rollbackPlan=p;}}
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Entity @Table(name="deep_itsm_audit") class ItsmAudit{@Id@GeneratedValue(strategy=GenerationType.IDENTITY)Long id;String action;String aggregateNo;String detail;LocalDateTime createdAt=LocalDateTime.now();/**
                                                                                                                                                                                                               * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                                               */
protected ItsmAudit(){}/**
                                                                                                                                                                                                                                      * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                                                                      */
ItsmAudit(String a,String n,String d){action=a;aggregateNo=n;detail=d;}}
