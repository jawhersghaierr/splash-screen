package com.splash.restapi.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserSplashScreenRepository extends JpaRepository<UserSplashScreen, Long> {
    List<UserSplashScreen> findByEmail(String email);
    List<UserSplashScreen> findBySplashScreenId(String splashScreenId);
    void deleteBySplashScreenId(String splashScreenId);

}
