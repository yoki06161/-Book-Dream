package com.bookdream.sbb.trade;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TradeRepository extends JpaRepository<Trade, Integer> {
	@Query("SELECT t FROM Trade t WHERE t.title LIKE %:kw% OR t.writer LIKE %:kw%")
    Page<Trade> findAllByKeyword(@Param("kw") String kw, Pageable pageable);
}