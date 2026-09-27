package com.example.quiz.content.question.service;

import com.example.quiz.content.question.dto.*;
import com.example.quiz.content.question.entity.QuestionMatchingPair;
import com.example.quiz.content.question.entity.QuestionOption;
import com.example.quiz.content.question.entity.QuestionOrderItem;
import com.example.quiz.content.question.repository.QuestionMatchingPairRepository;
import com.example.quiz.content.question.repository.QuestionOptionRepository;
import com.example.quiz.content.question.repository.QuestionOrderItemRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Сервис структуры ответа вопроса
 */
@Service
public class QuestionStructureServiceImpl implements QuestionStructureService {

    /**
     * JPA репозиторий вариантов ответа
     */
    private final QuestionOptionRepository questionOptionRepository;

    /**
     * JPA репозиторий пар для сопоставления
     */
    private final QuestionMatchingPairRepository questionMatchingPairRepository;

    /**
     * JPA репозиторий элементов последовательности
     */
    private final QuestionOrderItemRepository questionOrderItemRepository;

    public QuestionStructureServiceImpl(QuestionOptionRepository questionOptionRepository,
                                        QuestionMatchingPairRepository questionMatchingPairRepository,
                                        QuestionOrderItemRepository questionOrderItemRepository
    ) {
        this.questionOptionRepository = questionOptionRepository;
        this.questionMatchingPairRepository = questionMatchingPairRepository;
        this.questionOrderItemRepository = questionOrderItemRepository;
    }

    /**
     * Сохранить структуру вопроса
     *
     * @param questionUuid UUID вопроса
     * @param request      запрос
     */
    @Override
    public void save(UUID questionUuid, AdminQuestionCreateRequest request) {
        switch (request.type()) {
            case SINGLE_CHOICE, MULTIPLE_CHOICE, TRUE_FALSE, FIND_ERROR -> saveOptions(questionUuid, request.options());

            case MATCHING -> saveMatchingPairs(questionUuid, request.matchingPairs());

            case ORDERING -> saveOrderItems(questionUuid, request.orderItems());

            case SELF_ASSESSMENT, FREE_TEXT, DEFINITION_TO_TERM -> {
            }
        }
    }

    /**
     * Сохранить структуру вопроса
     *
     * @param questionUuid UUID вопроса
     * @param request      запрос
     */
    @Override
    public void save(UUID questionUuid, AdminQuestionUpdateRequest request) {
        switch (request.type()) {
            case SINGLE_CHOICE, MULTIPLE_CHOICE, TRUE_FALSE, FIND_ERROR -> saveOptions(questionUuid, request.options());

            case MATCHING -> saveMatchingPairs(questionUuid, request.matchingPairs());

            case ORDERING -> saveOrderItems(questionUuid, request.orderItems());

            case SELF_ASSESSMENT, FREE_TEXT, DEFINITION_TO_TERM -> {
            }
        }
    }

    /**
     * Заменить структуру вопроса
     *
     * @param questionUuid UUID вопроса
     * @param request      запрос
     */
    @Override
    public void replace(UUID questionUuid, AdminQuestionUpdateRequest request) {
        delete(questionUuid);
        save(questionUuid, request);
    }

    /**
     * Удалить структуру вопроса
     *
     * @param questionUuid UUID вопроса
     */
    @Override
    public void delete(UUID questionUuid) {
        questionOptionRepository.deleteAllByQuestionUuid(questionUuid);
        questionMatchingPairRepository.deleteAllByQuestionUuid(questionUuid);
        questionOrderItemRepository.deleteAllByQuestionUuid(questionUuid);
    }

    private void saveOptions(UUID questionUuid, List<AdminQuestionOptionRequest> requests) {
        List<QuestionOption> options = new ArrayList<>();

        for (int i = 0; i < requests.size(); i++) {
            AdminQuestionOptionRequest request = requests.get(i);

            QuestionOption option = new QuestionOption();
            option.setQuestionUuid(questionUuid);
            option.setText(request.text());
            option.setCorrect(request.correct());
            option.setSortOrder(i + 1);

            options.add(option);
        }

        questionOptionRepository.saveAll(options);
    }

    private void saveMatchingPairs(UUID questionUuid, List<AdminQuestionMatchingPairRequest> requests) {
        List<QuestionMatchingPair> pairs = new ArrayList<>();

        for (int i = 0; i < requests.size(); i++) {
            AdminQuestionMatchingPairRequest request = requests.get(i);

            QuestionMatchingPair pair = new QuestionMatchingPair();
            pair.setQuestionUuid(questionUuid);
            pair.setLeftText(request.leftText());
            pair.setRightText(request.rightText());
            pair.setSortOrder(i + 1);

            pairs.add(pair);
        }

        questionMatchingPairRepository.saveAll(pairs);
    }

    private void saveOrderItems(UUID questionUuid, List<AdminQuestionOrderItemRequest> requests) {
        List<QuestionOrderItem> items = new ArrayList<>();

        for (int i = 0; i < requests.size(); i++) {
            AdminQuestionOrderItemRequest request = requests.get(i);

            QuestionOrderItem item = new QuestionOrderItem();
            item.setQuestionUuid(questionUuid);
            item.setText(request.text());
            item.setCorrectOrder(i + 1);

            items.add(item);
        }

        questionOrderItemRepository.saveAll(items);
    }
}