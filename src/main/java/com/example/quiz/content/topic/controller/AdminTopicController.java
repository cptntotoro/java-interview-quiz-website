package com.example.quiz.content.topic.controller;

import com.example.quiz.content.topic.dto.TopicCreateRequest;
import com.example.quiz.content.topic.dto.TopicResponse;
import com.example.quiz.content.topic.service.TopicService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/topics")
public class AdminTopicController {

    private final TopicService topicService;

    public AdminTopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TopicResponse create(@Valid @RequestBody TopicCreateRequest request) {
        return topicService.create(request);
    }

    @PatchMapping("/{id}/publish")
    public TopicResponse publish(@PathVariable Long id) {
        return topicService.publish(id);
    }
}
