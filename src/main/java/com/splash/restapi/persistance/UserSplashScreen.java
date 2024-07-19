package com.splash.restapi.persistance;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "viewed_splash_screens")
public class UserSplashScreen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    @Getter
    private Long id;

    @Setter
    @Getter
    @Column(nullable = false)
    private String email;

    @ManyToOne
    @JoinColumn(name = "splash_screen_id", nullable = false)
    @Getter
    @Setter
    private SplashScreen splashScreen;
    @Setter
    @Getter
    private LocalDateTime dateSeen;
    @Setter
    @Getter
    @Column(nullable = false)
    private boolean consulted; // true if the splash screen has been seen





}
