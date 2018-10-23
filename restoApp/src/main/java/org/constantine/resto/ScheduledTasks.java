package org.constantine.resto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class ScheduledTasks {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Scheduled(cron = "0 0/10 * * * ?")
    public void scheduleTaskWithFixedRate() {
        logger.info("Keeping Server Active :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()) );
    }


}
