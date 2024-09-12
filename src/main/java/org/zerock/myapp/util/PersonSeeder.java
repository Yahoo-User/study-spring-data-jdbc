package org.zerock.myapp.util;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Slf4j
@NoArgsConstructor

@Component
public class PersonSeeder {
    // spring-boot-starter-data-jdbc 패키지 안에 이미, spring-jdbc 라이브러리가
    // 포함되어 있기 때문에, 아래의 빈을 사용가능합니다.
    @Autowired
    private JdbcTemplate jdbcTemplate;      // Spring JDBC 핵심 빈


    @PostConstruct
    void postConstruct() {  // 빈 객체가 자동생성된 직후에 호출되는 콜백
        log.trace("postConstructor");

        Objects.requireNonNull(this.jdbcTemplate);
        log.info("\t+ this.jdbcTemplate: {}", this.jdbcTemplate);
    } // postConstruct

    @PreDestroy
    void preDestroy() { // 빈 객체가 언젠가 파괴될때, 파괴직전에 호출되는 콜백
        log.trace("preDestroy");

    } // preDestroy

    // 데이터베이스에 생성되어 있는 Person 테이블에 데이터를
    // 입력하는 메소드
    public void prepareData() {
        log.trace("prepareData() invoked.");

        this.jdbcTemplate.execute("TRUNCATE TABLE person");
        this.jdbcTemplate
            .execute("""
                    INSERT INTO person (name, age)
                    VALUES
                        ('NAME', 23),
                        ('NAME', 24),
                        ('NAME_3', 25),
                        ('NAME_4', 26),
                        ('NAME', 27),
                        ('NAME_6', 28),
                        ('NAME_7', 29)
                    """);
    } // prepareData



} // end class
