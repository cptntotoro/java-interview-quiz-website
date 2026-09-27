package com.example.quiz.content.topic.service;

import com.example.quiz.common.exception.TopicNotFoundException;
import com.example.quiz.content.topic.entity.Topic;
import com.example.quiz.content.topic.repository.TopicRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;

    public TopicServiceImpl(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    @Override
    public void validateExists(UUID uuid) {
        if (!topicRepository.existsById(uuid)) {
            throw new TopicNotFoundException(uuid);
        }
    }

    @Override
    public void validateExists(Set<UUID> uuids) {
        Set<UUID> existingUuids = topicRepository.findAllById(uuids).stream()
                .map(Topic::getUuid)
                .collect(Collectors.toSet());

        if (existingUuids.size() != uuids.size()) {
            Set<UUID> missingUuids = new HashSet<>(uuids);
            missingUuids.removeAll(existingUuids);

            throw new TopicNotFoundException(String.valueOf(missingUuids));
        }
    }
}
