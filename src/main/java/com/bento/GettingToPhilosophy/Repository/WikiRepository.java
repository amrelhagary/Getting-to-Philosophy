package com.bento.GettingToPhilosophy.Repository;

import com.bento.GettingToPhilosophy.Entity.Wiki;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WikiRepository extends JpaRepository<Wiki, Integer> {
}
