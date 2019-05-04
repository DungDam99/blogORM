package model;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import javax.persistence.*;
import java.time.LocalDate;

@Component
@Entity
@Table(name = "blog")
public class Blog implements Validator {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "date")
    private LocalDate date = LocalDate.now();

    private String name;

    private String topic;

    private String author;

    public Blog() {
    }

    public Blog(String name, String topic, String author) {
        this.name = name;
        this.topic = topic;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Blog.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Blog blog = (Blog) target;

        String name = blog.getName();
        ValidationUtils.rejectIfEmpty(errors, "name", "name.empty");
        if (name.length() > 45 || name.length() < 5){
            errors.rejectValue("name", "name.length");
        }

        String topic = blog.getTopic();
        ValidationUtils.rejectIfEmpty(errors, "topic", "topic.empty");
        if (topic.length() > 45 || topic.length() <5){
            errors.rejectValue("topic", "topic.length");
        }

        String author = blog.getAuthor();
        ValidationUtils.rejectIfEmpty(errors, "author", "author.empty");
        if (author.length() > 45 || author.length() <5 ){
            errors.rejectValue("author", "author.length");
        }
    }
}
