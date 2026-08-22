/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.itsm.repository; import cn.zhuatech.itsm.model.ServiceTicket; import org.springframework.data.jpa.repository.JpaRepository; import java.util.List;
public interface ServiceTicketRepository extends JpaRepository<ServiceTicket,Long>{List<ServiceTicket> findAllByOrderByDueDateAsc();List<ServiceTicket> findBySupportGroupCodeOrderByDueDateAsc(String code);long countByStatus(ServiceTicket.Status status);}
