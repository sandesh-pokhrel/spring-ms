package com.sandesh.miscservice.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import java.security.SecureRandom;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class BatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager ptm;

    // @Bean
    public CommandLineRunner jobRunner(Job complexJob, JobLauncher jobLauncher) {
        return args -> jobLauncher.run(complexJob,
                new JobParameters(
                        Map.of("id", new JobParameter<>(new SecureRandom().nextInt(100, 999), Integer.class),
                                "random_unique", new JobParameter<>(UUID.randomUUID().toString(), String.class))));
    }

    @Bean
    public Job firstJob(Step firstStep) {
        return new JobBuilder("firstJob", jobRepository)
                .start(firstStep)
                .split(new SimpleAsyncTaskExecutor())
                .add(secondStepFlow())
                .end()
                .build();
    }

    @Bean
    public Job complexJob() {
        return new JobBuilder("complexJob", jobRepository)
                .start(complexFlow()).end()
                .build();
    }

    @Bean
    public Step firstStep(Tasklet firstTasklet) {
        return new StepBuilder("firstStep", jobRepository)
                .tasklet(firstTasklet, ptm)
                .build();
    }


    @Bean
    public SimpleFlow complexFlow() {
        return new FlowBuilder<SimpleFlow>("complexFlow")
                .next(new StepBuilder("first_complex_step", jobRepository)
                        .tasklet(((contribution, chunkContext) -> {
                            log.info("Simulating delay (first_complex_tasklet) .. ");
                            Thread.sleep(500);
                            return RepeatStatus.FINISHED;
                        }), ptm)
                        .build())
                .on("completed").to(new StepBuilder("first_complex_step_finished", jobRepository)
                        .tasklet(((contribution, chunkContext) -> {
                            log.info("Simulating delay (first_complex_step_finished) .. ");
                            Thread.sleep(500);
                            return RepeatStatus.FINISHED;
                        }), ptm)
                        .build())
                .on("failed").to(new StepBuilder("first_complex_step_failed", jobRepository)
                        .tasklet(((contribution, chunkContext) -> {
                            log.info("Simulating delay (first_complex_step_failed) .. ");
                            Thread.sleep(500);
                            return RepeatStatus.FINISHED;
                        }), ptm)
                        .build())
                .build();
    }

    @Bean
    public SimpleFlow secondStepFlow() {
        return new FlowBuilder<SimpleFlow>("firstFlow")
                .next(new StepBuilder("secondStep", jobRepository)
                        .tasklet(((contribution, chunkContext) -> {
                            log.info("Simulating delay (second tasklet) .. ");
                            Thread.sleep(500);
                            return RepeatStatus.FINISHED;
                        }), ptm)
                        .build())
                .build();
    }

    @Bean
    public Tasklet firstTasklet() {
        return (contribution, chunkContext) -> {
            log.info("Simulating delay (first tasklet) .. ");
            Thread.sleep(15000);
            return RepeatStatus.FINISHED;
        };
    }
}
