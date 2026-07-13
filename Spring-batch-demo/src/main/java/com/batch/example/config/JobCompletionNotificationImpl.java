package com.batch.example.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionNotificationImpl implements JobExecutionListener {

  Logger logger = LoggerFactory.getLogger(JobCompletionNotificationImpl.class);


  @Override
  public void beforeJob(JobExecution jobExecution) {
    logger.info("Job started");
  }



  @Override
  public void afterJob(JobExecution jobExecution) {
    if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
      logger.info("Job completed");
    }
  }
}
