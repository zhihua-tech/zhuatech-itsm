/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.itsm.service;
import org.junit.jupiter.api.Test; import static org.assertj.core.api.Assertions.assertThat;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
class MajorIncidentClosureGovernanceServiceTest{
 private final MajorIncidentClosureGovernanceService service=new MajorIncidentClosureGovernanceService();
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 @Test void closesCompletedIncident(){var r=service.assess(new MajorIncidentClosureGovernanceService.Request("INC-001",true,true,true,true,true,true,true,true,true));assertThat(r.decision()).isEqualTo(MajorIncidentClosureGovernanceService.Decision.CLOSE);}
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 @Test void holdsUnstableSecurityIncident(){var r=service.assess(new MajorIncidentClosureGovernanceService.Request("INC-002",false,false,false,false,false,false,false,true,false));assertThat(r.decision()).isEqualTo(MajorIncidentClosureGovernanceService.Decision.HOLD);assertThat(r.blockers()).hasSize(4);assertThat(r.actions()).hasSize(4);}
}
