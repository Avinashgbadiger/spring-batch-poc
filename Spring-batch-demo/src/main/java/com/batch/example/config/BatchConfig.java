package com.batch.example.config;


import com.batch.example.model.Product;
import javax.sql.DataSource;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.batch.infrastructure.item.ItemReader;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.batch.infrastructure.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.infrastructure.item.file.FlatFileItemReader;
import org.springframework.batch.infrastructure.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
public class BatchConfig {


  @Bean
  public Job jobBean(JobRepository jobRepository, JobCompletionNotificationImpl listener, Step steps) {
    return new JobBuilder("job", jobRepository)
        .listener(listener)
        .start(steps)
        .build();

  }

  @Bean
  public Step steps(
      JobRepository jobRepository,
      DataSourceTransactionManager transactionManager,
      ItemReader<Product> reader,
      ItemProcessor<Product, Product> processor,
      ItemWriter<Product> writer) {

    return new StepBuilder("steps", jobRepository)
        .<Product, Product>chunk(5)
        .transactionManager(transactionManager)
        .reader(reader)
        .processor(processor)
        .writer(writer)
        .build();
  }

  //Reader
  @Bean
  public FlatFileItemReader<Product> itemReader() {
    return new FlatFileItemReaderBuilder<Product>()
        .name("itemBuilder")
        .resource(new ClassPathResource("data.csv"))
        .delimited()
        .names("productId","title","description","price","discount")
        .targetType(Product.class)
        .strict(false)
        .build();
  }

  //Processor


  //Write
  @Bean
  public ItemWriter<Product> itemWriter(DataSource dataSource) {

    return new JdbcBatchItemWriterBuilder<Product>()
        .dataSource(dataSource)
        .sql("""
            INSERT INTO product
            (product_id, title, description, price, discount, discounted_price)
            VALUES
            (:productId, :title, :description, :price, :discount, :discountedPrice)
        """)
        .beanMapped()
        .build();
  }

}

