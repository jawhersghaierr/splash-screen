package com.splash.restapi.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SplashScreenRepository extends JpaRepository<SplashScreen,String> {

    @Modifying
    @Transactional
    @Query("UPDATE SplashScreen s SET s.status = false WHERE s.status = true")
    void updateAllStatusesToFalse();

    @Query("SELECT s FROM SplashScreen s ORDER BY s.status DESC, s.date DESC")
    List<SplashScreen> findAllByOrderByStatusDescDateDesc();
    List<SplashScreen> findByStatus(Boolean status);

}
