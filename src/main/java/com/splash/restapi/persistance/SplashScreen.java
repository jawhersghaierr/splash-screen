package com.splash.restapi.persistance;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Entity
@Data
@Table(name="splash_screens")
public class SplashScreen {
    @Id
    private String id;

    private String label;
    @Lob
    private String content;

    @Setter
    @Getter
    @Column(columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP", nullable = false)
    private LocalDateTime date;

    private Boolean status;

    // Automatically set the date before persisting or updating
    @PrePersist
    protected void onCreate() {
        date = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        date = LocalDateTime.now();
    }
}
