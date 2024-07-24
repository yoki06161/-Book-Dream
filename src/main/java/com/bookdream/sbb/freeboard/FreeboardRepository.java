package com.bookdream.sbb.freeboard;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FreeboardRepository extends JpaRepository<Freeboard, Long> {
    Page<Freeboard> findByTitleContaining(String title, Pageable pageable);
}