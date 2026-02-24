package com.example.vibeapp.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

@Configuration
public class DatabaseInitConfig {

    private static final Logger log = LoggerFactory.getLogger(DatabaseInitConfig.class);

    @Bean
    public CommandLineRunner initDatabase(DataSource dataSource) {
        return args -> {
            try (Connection conn = dataSource.getConnection();
                    Statement stmt = conn.createStatement()) {
                // 커넥션을 열어 H2 파일 DB가 생성되도록 트리거
                stmt.execute("SELECT 1");
                log.info("H2 Database connection initialized: {}", conn.getMetaData().getURL());
            } catch (Exception e) {
                log.error("Failed to initialize database connection", e);
            }
        };
    }
}
