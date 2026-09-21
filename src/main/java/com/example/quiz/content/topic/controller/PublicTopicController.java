package com.example.quiz.content.topic.controller;

import com.example.quiz.content.topic.dto.TopicResponse;
import com.example.quiz.content.topic.mapper.TopicQueryMapper;
import com.example.quiz.content.topic.service.PublicTopicService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/public/topics")
public class PublicTopicController {

    /**
     * Публичный сервис тем
     */
    private final PublicTopicService publicTopicService;

    /**
     * Маппер тем из БД в DTO ответа
     */
    private final TopicQueryMapper topicQueryMapper;

    public PublicTopicController(PublicTopicService publicTopicService, TopicQueryMapper topicQueryMapper) {
        this.publicTopicService = publicTopicService;
        this.topicQueryMapper = topicQueryMapper;
    }

    @GetMapping
    public List<TopicResponse> findPublished() {
        return topicQueryMapper.toTopicResponseList(publicTopicService.findPublished());
    }

    @GetMapping("/{slug}")
    public TopicResponse findPublishedBySlug(@PathVariable String slug) {
        return topicQueryMapper.toTopicResponse(publicTopicService.findPublishedBySlug(slug));
    }
}
