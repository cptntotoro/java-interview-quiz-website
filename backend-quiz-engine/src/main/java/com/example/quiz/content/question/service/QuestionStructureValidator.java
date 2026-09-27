package com.example.quiz.content.question.service;

import com.example.quiz.content.question.dto.*;
import com.example.quiz.content.question.entity.QuestionType;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Валидатор структуры ответа вопроса
 */
@Component
public class QuestionStructureValidator {

    /**
     * Проверить структуру вопроса
     *
     * @param request запрос
     */
    public void validate(AdminQuestionCreateRequest request) {
        validate(
                request.type(),
                request.referenceAnswer(),
                request.options(),
                request.matchingPairs(),
                request.orderItems()
        );
    }

    /**
     * Проверить структуру вопроса
     *
     * @param request запрос
     */
    public void validate(AdminQuestionUpdateRequest request) {
        validate(
                request.type(),
                request.referenceAnswer(),
                request.options(),
                request.matchingPairs(),
                request.orderItems()
        );
    }

    private void validate(
            QuestionType type,
            String referenceAnswer,
            List<AdminQuestionOptionRequest> options,
            List<AdminQuestionMatchingPairRequest> matchingPairs,
            List<AdminQuestionOrderItemRequest> orderItems
    ) {
        switch (type) {
            case SINGLE_CHOICE, MULTIPLE_CHOICE, TRUE_FALSE, FIND_ERROR ->
                    validateOptions(type, referenceAnswer, options, matchingPairs, orderItems);

            case MATCHING ->
                    validateMatching(referenceAnswer, matchingPairs, options, orderItems);

            case ORDERING ->
                    validateOrdering(referenceAnswer, orderItems, options, matchingPairs);

            case SELF_ASSESSMENT, FREE_TEXT, DEFINITION_TO_TERM ->
                    validateTextAnswer(referenceAnswer, options, matchingPairs, orderItems);
        }
    }

    private void validateOptions(
            QuestionType type,
            String referenceAnswer,
            List<AdminQuestionOptionRequest> options,
            List<AdminQuestionMatchingPairRequest> matchingPairs,
            List<AdminQuestionOrderItemRequest> orderItems
    ) {
        if (hasValue(referenceAnswer)) {
            throw new IllegalArgumentException(
                    "Для структурированного вопроса referenceAnswer должен быть пустым"
            );
        }

        validateNoMatchingOrOrdering(matchingPairs, orderItems);

        if (options == null || options.size() < 2) {
            throw new IllegalArgumentException(
                    "Для типа вопроса " + type + " необходимо минимум 2 варианта ответа"
            );
        }

        validateUniqueOptionTexts(options);

        long correctCount = options.stream()
                .filter(AdminQuestionOptionRequest::correct)
                .count();

        switch (type) {
            case SINGLE_CHOICE, TRUE_FALSE -> {
                if (correctCount != 1) {
                    throw new IllegalArgumentException(
                            "Для типа вопроса " + type +
                                    " должен быть ровно один правильный вариант"
                    );
                }
            }

            case MULTIPLE_CHOICE -> {
                if (correctCount < 2) {
                    throw new IllegalArgumentException(
                            "Для MULTIPLE_CHOICE должно быть минимум 2 правильных варианта"
                    );
                }
            }

            case FIND_ERROR -> {
                if (correctCount == 0) {
                    throw new IllegalArgumentException(
                            "Для FIND_ERROR должен быть указан хотя бы один ошибочный вариант"
                    );
                }
            }

            default -> throw new IllegalArgumentException(
                    "Неподдерживаемый тип вопроса: " + type
            );
        }

        if (type == QuestionType.TRUE_FALSE && options.size() != 2) {
            throw new IllegalArgumentException(
                    "Для TRUE_FALSE должно быть ровно 2 варианта ответа"
            );
        }
    }

    private void validateMatching(
            String referenceAnswer,
            List<AdminQuestionMatchingPairRequest> matchingPairs,
            List<AdminQuestionOptionRequest> options,
            List<AdminQuestionOrderItemRequest> orderItems
    ) {
        if (hasValue(referenceAnswer)) {
            throw new IllegalArgumentException(
                    "Для MATCHING referenceAnswer должен быть пустым"
            );
        }

        validateNoOptionsOrOrdering(options, orderItems);

        if (matchingPairs == null || matchingPairs.size() < 2) {
            throw new IllegalArgumentException(
                    "Для MATCHING необходимо минимум 2 пары"
            );
        }

        Set<String> leftValues = new HashSet<>();
        Set<String> rightValues = new HashSet<>();

        for (AdminQuestionMatchingPairRequest pair : matchingPairs) {
            if (!leftValues.add(pair.leftText().trim())) {
                throw new IllegalArgumentException(
                        "Левые значения в MATCHING должны быть уникальными: " +
                                pair.leftText()
                );
            }

            if (!rightValues.add(pair.rightText().trim())) {
                throw new IllegalArgumentException(
                        "Правые значения в MATCHING должны быть уникальными: " +
                                pair.rightText()
                );
            }
        }
    }

    private void validateOrdering(
            String referenceAnswer,
            List<AdminQuestionOrderItemRequest> orderItems,
            List<AdminQuestionOptionRequest> options,
            List<AdminQuestionMatchingPairRequest> matchingPairs
    ) {
        if (hasValue(referenceAnswer)) {
            throw new IllegalArgumentException(
                    "Для ORDERING referenceAnswer должен быть пустым"
            );
        }

        validateNoOptionsOrMatching(options, matchingPairs);

        if (orderItems == null || orderItems.size() < 2) {
            throw new IllegalArgumentException(
                    "Для ORDERING необходимо минимум 2 элемента"
            );
        }

        Set<String> values = new HashSet<>();

        for (AdminQuestionOrderItemRequest item : orderItems) {
            if (!values.add(item.text().trim())) {
                throw new IllegalArgumentException(
                        "Элементы ORDERING должны быть уникальными: " +
                                item.text()
                );
            }
        }
    }

    private void validateTextAnswer(
            String referenceAnswer,
            List<AdminQuestionOptionRequest> options,
            List<AdminQuestionMatchingPairRequest> matchingPairs,
            List<AdminQuestionOrderItemRequest> orderItems
    ) {
        if (!hasValue(referenceAnswer)) {
            throw new IllegalArgumentException(
                    "Для текстового вопроса необходимо указать referenceAnswer"
            );
        }

        if (hasValues(options) || hasValues(matchingPairs) || hasValues(orderItems)) {
            throw new IllegalArgumentException(
                    "Для текстового вопроса структурированный ответ недопустим"
            );
        }
    }

    private void validateNoMatchingOrOrdering(
            List<AdminQuestionMatchingPairRequest> matchingPairs,
            List<AdminQuestionOrderItemRequest> orderItems
    ) {
        if (hasValues(matchingPairs) || hasValues(orderItems)) {
            throw new IllegalArgumentException(
                    "Для этого типа вопроса matchingPairs и orderItems недопустимы"
            );
        }
    }

    private void validateNoOptionsOrOrdering(
            List<AdminQuestionOptionRequest> options,
            List<AdminQuestionOrderItemRequest> orderItems
    ) {
        if (hasValues(options) || hasValues(orderItems)) {
            throw new IllegalArgumentException(
                    "Для MATCHING options и orderItems недопустимы"
            );
        }
    }

    private void validateNoOptionsOrMatching(
            List<AdminQuestionOptionRequest> options,
            List<AdminQuestionMatchingPairRequest> matchingPairs
    ) {
        if (hasValues(options) || hasValues(matchingPairs)) {
            throw new IllegalArgumentException(
                    "Для ORDERING options и matchingPairs недопустимы"
            );
        }
    }

    private void validateUniqueOptionTexts(List<AdminQuestionOptionRequest> options) {
        Set<String> texts = new HashSet<>();

        for (AdminQuestionOptionRequest option : options) {
            if (!texts.add(option.text().trim())) {
                throw new IllegalArgumentException(
                        "Варианты ответа должны быть уникальными: " +
                                option.text()
                );
            }
        }
    }

    private boolean hasValue(String value) {
        return value != null && !value.isBlank();
    }

    private boolean hasValues(List<?> values) {
        return values != null && !values.isEmpty();
    }
}