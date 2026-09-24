package com.example.quiz.content.topic.controller;

import com.example.quiz.content.topic.dto.PublicTopicDetailsResponse;
import com.example.quiz.content.topic.dto.PublicTopicResponse;
import com.example.quiz.content.topic.mapper.PublicTopicQueryMapper;
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
    private final PublicTopicQueryMapper publicTopicQueryMapper;

    public PublicTopicController(PublicTopicService publicTopicService, PublicTopicQueryMapper publicTopicQueryMapper) {
        this.publicTopicService = publicTopicService;
        this.publicTopicQueryMapper = publicTopicQueryMapper;
    }

    @GetMapping
    public List<PublicTopicResponse> findPublished() {
        return publicTopicQueryMapper.toListResponse(publicTopicService.findPublished());
    }

    @GetMapping("/{slug}")
    public PublicTopicDetailsResponse findPublishedBySlug(@PathVariable String slug) {
        return publicTopicQueryMapper.toDetailsResponse(publicTopicService.findPublishedBySlug(slug));
    }
}
