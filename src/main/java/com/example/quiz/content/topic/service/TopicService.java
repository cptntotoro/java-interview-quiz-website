package com.example.quiz.content.topic.service;

import com.example.quiz.content.topic.dto.TopicCreateRequest;
import com.example.quiz.content.topic.dto.TopicResponse;
import org.springframework.transaction.annotation.Transactional;

public interface TopicService {
    @Transactional
    TopicResponse create(TopicCreateRequest request);

    @Transactional
    TopicResponse publish(Long id);
}
