package com.returnFlow.repository;

import com.returnFlow.entity.Dispute;
import com.returnFlow.entity.DisputeStatus;
import com.returnFlow.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DisputeRepository extends JpaRepository<Dispute, Long> {

    List<Dispute> findByRaisedBy(User user);

    List<Dispute> findByStatus(DisputeStatus status);
}