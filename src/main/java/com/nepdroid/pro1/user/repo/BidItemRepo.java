package com.nepdroid.pro1.user.repo;

import com.nepdroid.pro1.user.entity.BidItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BidItemRepo extends JpaRepository<BidItem, Integer> {


}
