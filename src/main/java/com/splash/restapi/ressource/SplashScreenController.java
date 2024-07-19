package com.splash.restapi.ressource;

import com.splash.restapi.persistance.SplashScreen;
import com.splash.restapi.persistance.UserSplashScreen;
import com.splash.restapi.persistance.UserSplashScreenRepository;
import com.splash.restapi.services.SplashScreenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


    @RestController
    @RequestMapping("/api/splashscreens")
    @CrossOrigin(origins = "*") // This allows all origins. For security, specify your allowed origins.
    public class SplashScreenController {

        @Autowired
        private SplashScreenService splashScreenService;
        @Autowired
        private UserSplashScreenRepository userSplashScreenRepository;

        @GetMapping
        public List<SplashScreen> getAllSplashScreens() {
            return splashScreenService.getAllSplashScreens();
        }

        @GetMapping("/{id}")
        public SplashScreen getSplashScreenById(@PathVariable String id) {
            return splashScreenService.getSplashScreenById(id);
        }
        @PostMapping
        public SplashScreen createSplashScreen(

                @RequestBody SplashScreen splashScreen) {
            return splashScreenService.createSplashScreen(splashScreen);
        }

        @DeleteMapping("/{id}")
        public void deleteSplashScreen(@PathVariable String id) {
            splashScreenService.deleteSplashScreen(id);
        }
        @PutMapping("/{id}")
        public SplashScreen updateSplashScreen(
                @PathVariable String id,
                @RequestBody SplashScreen updatedSplashScreen) {
            return splashScreenService.updateSplashScreen(id, updatedSplashScreen);
        }
        @GetMapping("/by-user")
        public UserSplashScreen getSplashScreenForUser(@RequestParam String email){
            return splashScreenService.getActiveSplashScreenForUser(email);
        }
        @PutMapping("/updateConsultedStatus")
        public ResponseEntity<String> updateConsultedStatus(@RequestParam Long userSplashScreenId) {
            UserSplashScreen userSplashScreen = userSplashScreenRepository.findById(userSplashScreenId)
                    .orElse(null);

            if (userSplashScreen == null) {
                return ResponseEntity.notFound().build();
            }

            userSplashScreen.setConsulted(true);
            userSplashScreenRepository.save(userSplashScreen);

            return ResponseEntity.ok("Consulted status updated to true");
        }

        @PutMapping("/revoke")
        public ResponseEntity<String> revokeStatus(@RequestParam Long userSplashScreenId) {
            UserSplashScreen userSplashScreen = userSplashScreenRepository.findById(userSplashScreenId)
                    .orElse(null);

            if (userSplashScreen == null) {
                return ResponseEntity.notFound().build();
            }

            userSplashScreen.setConsulted(false);
            userSplashScreenRepository.save(userSplashScreen);

            return ResponseEntity.ok("Consulted status updated to false");
        }

        @PutMapping("/{id}/start")
        public SplashScreen startSplashScreen(@PathVariable String id) {
            return splashScreenService.startSplashScreen(id);
        }
        @PutMapping("/{id}/stop")
        public SplashScreen stopSplashScreen(@PathVariable String id) {
            return splashScreenService.stopSplashScreen(id);
        }
        @GetMapping("/splash-details/{id}")
        public List<UserSplashScreen> getUserSplashScreensBySplashScreenId(@PathVariable String id) {
            return splashScreenService.getUserSplashScreensBySplashScreenId(id);
        }

}
