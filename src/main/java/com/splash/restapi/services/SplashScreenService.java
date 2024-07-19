package com.splash.restapi.services;

import com.splash.restapi.persistance.SplashScreen;
import com.splash.restapi.persistance.SplashScreenRepository;
import com.splash.restapi.persistance.UserSplashScreen;
import com.splash.restapi.persistance.UserSplashScreenRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class SplashScreenService {

    @Autowired
    private SplashScreenRepository splashScreenRepository;
    @Autowired
    private UserSplashScreenRepository userSplashScreenRepository;

    public List<SplashScreen> getAllSplashScreens() {
        return splashScreenRepository.findAllByOrderByStatusDescDateDesc();
    }

    public SplashScreen getSplashScreenById(String id) {
        return splashScreenRepository.findById(id).orElse(null);
    }

    public SplashScreen createSplashScreen(SplashScreen splashScreen) {
        if (splashScreen.getStatus()){
            splashScreenRepository.updateAllStatusesToFalse();
        }
        splashScreen.setId(generateId(splashScreen));
        return splashScreenRepository.save(splashScreen);
    }

    @Transactional
    public void deleteSplashScreen(String id) {
        userSplashScreenRepository.deleteBySplashScreenId(id);
        splashScreenRepository.deleteById(id);
    }
    public SplashScreen updateSplashScreen(String id, SplashScreen updatedSplashScreen) {
        SplashScreen existingSplashScreen = splashScreenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SplashScreen not found with id: " + id));

        // Update fields from updatedSplashScreen
        existingSplashScreen.setLabel(updatedSplashScreen.getLabel());
        existingSplashScreen.setContent(updatedSplashScreen.getContent());
        existingSplashScreen.setStatus(updatedSplashScreen.getStatus());

        // You can choose to update 'date' field here if needed

        return splashScreenRepository.save(existingSplashScreen);
    }

    @Transactional
    public SplashScreen startSplashScreen(String id) {
        Optional<SplashScreen> splashScreenOptional = splashScreenRepository.findById(id);
        if (splashScreenOptional.isPresent()) {
            SplashScreen splashScreen = splashScreenOptional.get();
            splashScreen.setStatus(true);
            return splashScreenRepository.save(splashScreen);
        } else {
            throw new RuntimeException("Splash screen not found");
        }
    }

    @Transactional
    public SplashScreen stopSplashScreen(String id) {
        Optional<SplashScreen> splashScreenOptional = splashScreenRepository.findById(id);
        if (splashScreenOptional.isPresent()) {
            SplashScreen splashScreen = splashScreenOptional.get();
            splashScreen.setStatus(false);
            return splashScreenRepository.save(splashScreen);
        } else {
            throw new RuntimeException("Splash screen not found");
        }
    }

    public String generateId(SplashScreen splashScreen) {
        Random random=new Random();
        int randomNumber=10000000 + random.nextInt(90000000);
        return "modernisation-splash-" + randomNumber;
    }


    public UserSplashScreen getActiveSplashScreenForUser(String email) {
        List<SplashScreen> activeSplashScreens = splashScreenRepository.findByStatus(true);
        List<UserSplashScreen> userSplashScreens = userSplashScreenRepository.findByEmail(email);

        Optional<UserSplashScreen> latestUserSplashScreen = userSplashScreens.stream()
                .filter(userSplash -> activeSplashScreens.stream()
                        .anyMatch(splash -> splash.getId().equals(userSplash.getSplashScreen().getId())))
                .max(Comparator.comparing(UserSplashScreen::getDateSeen));

        if (latestUserSplashScreen.isPresent()) {
            return latestUserSplashScreen.get();
        } else {
            for (SplashScreen splashScreen : activeSplashScreens) {
                boolean seen = userSplashScreens.stream()
                        .anyMatch(userSplash -> userSplash.getSplashScreen().getId().equals(splashScreen.getId()));

                if (!seen) {
                    UserSplashScreen userSplashScreen = new UserSplashScreen();
                    userSplashScreen.setEmail(email);
                    userSplashScreen.setSplashScreen(splashScreen);
                    userSplashScreen.setDateSeen(LocalDateTime.now());
                    userSplashScreen.setConsulted(false);
                    userSplashScreenRepository.save(userSplashScreen);
                    return userSplashScreen;
                }
            }
        }
        return null;
    }

    public List<UserSplashScreen> getUserSplashScreensBySplashScreenId(String splashScreenId) {

        return userSplashScreenRepository.findBySplashScreenId(splashScreenId);
    }




}
