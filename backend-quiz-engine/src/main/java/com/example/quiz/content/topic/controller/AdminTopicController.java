package com.example.quiz.content.topic.controller;

import com.example.quiz.common.dto.PageResponse;
import com.example.quiz.common.query.SortDirection;
import com.example.quiz.content.common.ContentStatus;
import com.example.quiz.content.topic.dto.AdminTopicListResponse;
import com.example.quiz.content.topic.dto.AdminTopicResponse;
import com.example.quiz.content.topic.dto.TopicCreateRequest;
import com.example.quiz.content.topic.dto.TopicUpdateRequest;
import com.example.quiz.content.topic.entity.Topic;
import com.example.quiz.content.topic.mapper.AdminTopicMapper;
import com.example.quiz.content.topic.mapper.AdminTopicQueryMapper;
import com.example.quiz.content.topic.query.AdminTopicListView;
import com.example.quiz.content.topic.service.AdminTopicService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/topics")
public class AdminTopicController {

    /**
     * Сервис управления темами
     */
    private final AdminTopicService adminTopicService;

    /**
     * Маппер тем в DTO
     */
    private final AdminTopicMapper adminTopicMapper;

    /**
     * Маппер списка тем
     */
    private final AdminTopicQueryMapper adminTopicQueryMapper;

    public AdminTopicController(AdminTopicService adminTopicService, AdminTopicMapper adminTopicMapper,
                                AdminTopicQueryMapper adminTopicQueryMapper) {
        this.adminTopicService = adminTopicService;
        this.adminTopicMapper = adminTopicMapper;
        this.adminTopicQueryMapper = adminTopicQueryMapper;
    }

    @GetMapping
    public PageResponse<AdminTopicListResponse> find(@RequestParam(required = false) ContentStatus status,
                                                     @RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "20") int size,
                                                     @RequestParam(defaultValue = "ASC") SortDirection direction) {
        PageResponse<AdminTopicListView> result = adminTopicService.find(status, page, size, direction);

        return new PageResponse<>(
                result.content().stream()
                        .map(adminTopicQueryMapper::toListResponse)
                        .toList(),
                result.page(),
                result.size(),
                result.hasNext()
        );
    }

    @PostMapping
    public AdminTopicResponse create(@Valid @RequestBody TopicCreateRequest request) {
        Topic topic = adminTopicService.create(request);
        return adminTopicMapper.toResponse(topic);
    }

    @PutMapping("/{uuid}")
    public AdminTopicResponse update(@PathVariable UUID uuid, @Valid @RequestBody TopicUpdateRequest request) {
        Topic topic = adminTopicService.update(uuid, request);
        return adminTopicMapper.toResponse(topic);
    }

    @PatchMapping("/{uuid}/publish")
    public AdminTopicResponse publish(@PathVariable UUID uuid) {
        Topic topic = adminTopicService.publish(uuid);
        return adminTopicMapper.toResponse(topic);
    }

    @PatchMapping("/{uuid}/archive")
    public AdminTopicResponse archive(@PathVariable UUID uuid) {
        Topic topic = adminTopicService.archive(uuid);
        return adminTopicMapper.toResponse(topic);
    }
}
