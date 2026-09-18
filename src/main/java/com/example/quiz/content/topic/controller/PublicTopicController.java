package com.example.quiz.content.topic.controller;

import com.example.quiz.content.topic.dto.TopicResponse;
import com.example.quiz.content.topic.service.PublicTopicService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/public/topics")
public class PublicTopicController {

    private final PublicTopicService publicTopicService;

    public PublicTopicController(PublicTopicService publicTopicService) {
        this.publicTopicService = publicTopicService;
    }

    @GetMapping
    public List<TopicResponse> findPublished() {
        return publicTopicService.findPublished();
    }

    @GetMapping("/{slug}")
    public TopicResponse findPublishedBySlug(@PathVariable String slug) {
        return publicTopicService.findPublishedBySlug(slug);
    }
}
