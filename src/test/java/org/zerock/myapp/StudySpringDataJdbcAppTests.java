package org.zerock.myapp;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.util.Objects;


//@Log4j2
@Slf4j

@SpringBootTest
class StudySpringDataJdbcAppTests {
	@Autowired
	private DataSource dataSource;


	@Test
	void contextLoads() {
		log.trace("contextLoads() invoked.");
		Objects.requireNonNull(this.dataSource);
		log.info("\t+ this.dataSource: {}", this.dataSource);
	} // contextLoads

} // end class


