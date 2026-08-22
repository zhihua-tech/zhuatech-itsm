/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.itsm.repository; import cn.zhuatech.itsm.model.SupportGroup; import org.springframework.data.jpa.repository.JpaRepository; import java.util.Optional;
public interface SupportGroupRepository extends JpaRepository<SupportGroup,Long>{Optional<SupportGroup> findByCode(String code);}
