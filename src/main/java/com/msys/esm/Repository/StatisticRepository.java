package com.msys.esm.Repository;

import com.msys.esm.Model.Statistic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StatisticRepository extends JpaRepository<Statistic, Integer> {
    Optional<Statistic> findByPlatformName (String platformName);
}
