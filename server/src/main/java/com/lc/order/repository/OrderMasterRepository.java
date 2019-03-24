package com.lc.order.repository;

import com.lc.order.dataobject.OrderMaster;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author lc
 * @date 2019/3/17
 */
public interface OrderMasterRepository extends JpaRepository<OrderMaster,String> {

}
