package Hospital.Config;  // 패키지명을 config로 지정

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync  // 비동기 실행을 활성화
public class AsyncConfig {

    @Bean(name = "appointmentExecutor")
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);  // 최소 5개의 스레드 유지
        executor.setMaxPoolSize(10);  // 최대 10개까지 증가 가능
        executor.setQueueCapacity(100); // 대기열 100개 설정
        executor.setThreadNamePrefix("AppointmentThread-"); // 스레드 이름 지정
        executor.initialize();
        return executor;
    }
}