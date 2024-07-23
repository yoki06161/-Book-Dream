package com.bookdream.sbb.prod_repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Prod_ScoreRepository extends JpaRepository<Prod_Score, Integer> {

}
