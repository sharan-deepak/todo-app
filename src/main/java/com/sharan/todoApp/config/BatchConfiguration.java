package com.sharan.todoApp.config;

import com.sharan.todoApp.listener.JobCompletionNotificationListener;
import com.sharan.todoApp.model.TodoItem;
import com.sharan.todoApp.processor.TodoItemProcessor;
import com.sharan.todoApp.repository.TodoRepo;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public DataSource dataSource;

    @Autowired
    private TodoRepo todoRepo;

    @Bean
    public FlatFileItemReader<TodoItem> reader() {
        FlatFileItemReader<TodoItem> reader = new FlatFileItemReader<TodoItem>();
        reader.setResource(new ClassPathResource("todoList.csv"));
        reader.setLineMapper(new DefaultLineMapper<TodoItem>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[] { "id", "title","status" });
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<TodoItem>() {{
                setTargetType(TodoItem.class);
            }});
        }});
        return reader;
    }
    @Bean
    public TodoItemProcessor processor() {
        return new TodoItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<TodoItem> writer() {
        JdbcBatchItemWriter<TodoItem> writer = new JdbcBatchItemWriter<TodoItem>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<TodoItem>());
        writer.setSql("INSERT INTO todo (id, title,status) VALUES (:id, :title,:status)");
        writer.setDataSource(dataSource);
        return writer;

    }

    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener) {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1())
                .end()
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<TodoItem, TodoItem> chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

}
