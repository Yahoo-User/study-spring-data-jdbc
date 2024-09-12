package org.zerock.myapp.runner;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


//@Log4j2
@Slf4j

@NoArgsConstructor

@Component
// Runner 클래스는, 스프링부트 프로젝트 수행시, 자동으로 콜백수행되는 클래스를 의미합니다.
// Runner 클래스는, 반드시 위와같이 @Component 어노테이션을 붙여서, Spring Context (즉,
// 스프링 빈즈 컨테이너)에 빈으로 자동등록 되어야만 자동실행됩니다.
public class CustomApplicationRunner implements ApplicationRunner {


    @Override
    public void run(ApplicationArguments args) {
        log.trace("run({}) invoked.", args);

    } // run

} // end class
