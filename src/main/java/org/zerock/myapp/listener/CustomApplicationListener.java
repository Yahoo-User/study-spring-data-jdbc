package org.zerock.myapp.listener;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


//@Log4j2
@Slf4j

@NoArgsConstructor

@Component
public class CustomApplicationListener implements ApplicationListener<ApplicationEvent> {

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        log.trace("onApplicationEvent({}) invoked.", event.getClass().getSimpleName());
    } // onApplicationEvent

} // end class
