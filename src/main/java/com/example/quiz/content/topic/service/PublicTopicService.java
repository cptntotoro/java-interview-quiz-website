package com.example.quiz.content.topic.service;

import com.example.quiz.content.topic.dto.TopicResponse;

import java.util.List;

public interface PublicTopicService {
    List<TopicResponse> findPublished();

    TopicResponse findPublishedBySlug(String slug);
}
