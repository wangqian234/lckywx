package com.mvc.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mvc.entiy.SmallGoods;

/**
 * 小件货运
 * @author lijing
 * @date 2017年8月11日
 */
public interface SmallGoodsRepository extends JpaRepository<SmallGoods, Integer>{
	// 根据使用时间查询小件货运信息
	//@Query("select b from BusNeed b where bune_gath_time = :bune_gath_time and is_delete=0 ")
	//public List<SmallGoods> findByUsertime(@Param("bune_gath_time") String useDate);
	@Query("select s from SmallGoods s where smgo_end = :endPlace and smgo_send_time = :sendTime and is_delete=0 ")
	public List<SmallGoods> findByTimeAndPlace(@Param("endPlace") String endPlace,@Param("sendTime") Date sendTime);
}
