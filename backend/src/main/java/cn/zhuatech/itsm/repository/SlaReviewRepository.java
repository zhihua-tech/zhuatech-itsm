/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.itsm.repository; import cn.zhuatech.itsm.model.SlaReview; import org.springframework.data.jpa.repository.JpaRepository; import java.util.List;
public interface SlaReviewRepository extends JpaRepository<SlaReview,Long>{List<SlaReview> findTop10ByOrderByIdDesc();long countByResult(SlaReview.Result result);}
