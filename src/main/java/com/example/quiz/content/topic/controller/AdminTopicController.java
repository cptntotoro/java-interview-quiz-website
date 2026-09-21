package com.example.quiz.content.topic.controller;

import com.example.quiz.content.topic.dto.TopicCreateRequest;
import com.example.quiz.content.topic.dto.TopicResponse;
import com.example.quiz.content.topic.dto.TopicUpdateRequest;
import com.example.quiz.content.topic.entity.Topic;
import com.example.quiz.content.topic.mapper.TopicMapper;
import com.example.quiz.content.topic.service.TopicService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/topics")
public class AdminTopicController {

    /**
     * Сервис управления темами
     */
    private final TopicService topicService;

    /**
     * Маппер тем в DTO
     */
    private final TopicMapper topicMapper;

    public AdminTopicController(TopicService topicService, TopicMapper topicMapper) {
        this.topicService = topicService;
        this.topicMapper = topicMapper;
    }

    @PostMapping
    public TopicResponse create(@Valid @RequestBody TopicCreateRequest request) {
        Topic topic = topicService.create(request);
        return topicMapper.toResponse(topic);
    }

    @PutMapping("/{uuid}")
    public TopicResponse update(@PathVariable UUID uuid, @Valid @RequestBody TopicUpdateRequest request) {
        Topic topic = topicService.update(uuid, request);
        return topicMapper.toResponse(topic);
    }

    @PatchMapping("/{uuid}/publish")
    public TopicResponse publish(@PathVariable UUID uuid) {
        Topic topic = topicService.publish(uuid);
        return topicMapper.toResponse(topic);
    }

    @PatchMapping("/{uuid}/archive")
    public TopicResponse archive(@PathVariable UUID uuid) {
        Topic topic = topicService.archive(uuid);
        return topicMapper.toResponse(topic);
    }
}
