package com.example.quiz.content.question.service;

import com.example.quiz.common.dto.PageResponse;
import com.example.quiz.common.exception.QuestionNotFoundException;
import com.example.quiz.common.query.SortDirection;
import com.example.quiz.content.question.entity.QuestionDifficulty;
import com.example.quiz.content.question.query.PublicQuestionAnswerView;
import com.example.quiz.content.question.query.PublicQuestionDetailsView;
import com.example.quiz.content.question.query.PublicQuestionListView;
import com.example.quiz.content.question.query.QuestionSortField;
import com.example.quiz.content.question.repository.PublicQuestionQueryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublicQuestionServiceImpl implements PublicQuestionService {

    /**
     * Максимальный размер страницы
     */
    private static final int MAX_SIZE = 100;

    /**
     * Репозиторий для чтения опубликованных вопрсов
     */
    private final PublicQuestionQueryRepository publicQuestionQueryRepository;

    public PublicQuestionServiceImpl(PublicQuestionQueryRepository publicQuestionQueryRepository) {
        this.publicQuestionQueryRepository = publicQuestionQueryRepository;
    }

    @Override
    public PageResponse<PublicQuestionListView> findPublishedByDifficulty(QuestionDifficulty difficulty, int page, int size,
                                                                          QuestionSortField sort, SortDirection direction) {
        validatePage(page);
        int normalizedSize = normalizeSize(size);

        List<PublicQuestionListView> questions = publicQuestionQueryRepository.findPublished(difficulty, page, normalizedSize,
                sort, direction);

        boolean hasNext = questions.size() > normalizedSize;

        if (hasNext) {
            questions = questions.subList(0, normalizedSize);
        }

        return new PageResponse<>(questions, page, normalizedSize, hasNext);
    }

    @Override
    public PublicQuestionDetailsView findPublishedBySlug(String slug) {
        return publicQuestionQueryRepository.findPublishedBySlug(slug)
                .orElseThrow(() -> new QuestionNotFoundException(slug));
    }

    @Override
    public PageResponse<PublicQuestionListView> findPublishedByTopicAndDifficulty(String topicSlug, QuestionDifficulty difficulty,
                                                                                  int page, int size, QuestionSortField sort,
                                                                                  SortDirection direction) {
        validatePage(page);
        int normalizedSize = normalizeSize(size);

        List<PublicQuestionListView> questions = publicQuestionQueryRepository.findPublishedByTopic(topicSlug, difficulty,
                page, normalizedSize, sort, direction);

        boolean hasNext = questions.size() > normalizedSize;

        if (hasNext) {
            questions = questions.subList(0, normalizedSize);
        }

        return new PageResponse<>(questions, page, normalizedSize, hasNext);
    }

    @Override
    public PublicQuestionAnswerView findPublishedAnswerBySlug(String slug) {
        return publicQuestionQueryRepository.findPublishedAnswerBySlug(slug)
                .orElseThrow(() -> new QuestionNotFoundException(slug));
    }

    private void validatePage(int page) {
        if (page < 0) {
            throw new IllegalArgumentException("номер страницы должен быть больше или равен 0");
        }
    }

    private int normalizeSize(int size) {
        if (size < 1) {
            throw new IllegalArgumentException("размер страницы должен быть больше 0");
        }

        return Math.min(size, MAX_SIZE);
    }
}