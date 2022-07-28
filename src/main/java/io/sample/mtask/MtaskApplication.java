package io.sample.mtask;

import io.micrometer.prometheus.PrometheusMeterRegistry;
import io.micrometer.prometheus.rsocket.PrometheusRSocketClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.cloud.task.listener.annotation.AfterTask;
import org.springframework.cloud.task.repository.TaskExecution;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableTask
public class MtaskApplication {

	@Autowired
	PrometheusMeterRegistry prometheusMeterRegistry;

	@Autowired
	PrometheusRSocketClient client;

	public static void main(String[] args) {
		SpringApplication.run(MtaskApplication.class, args);
	}

	@Bean
	ApplicationRunner applicationRunner () {
		return new ApplicationRunner() {
			@Override
			public void run(ApplicationArguments args) throws Exception {
				System.out.println("SLEEPING");
				Thread.sleep(5000);
				System.out.println("Awake");
			}
		};
	}


	@AfterTask
	public void afterTask(TaskExecution taskExecution) {
		System.out.println(prometheusMeterRegistry.scrape());
//		try {
//			Thread.sleep(10000);
//		} catch (InterruptedException e) {
//			throw new RuntimeException(e);
//		}
		client.pushAndClose();
	}
}
