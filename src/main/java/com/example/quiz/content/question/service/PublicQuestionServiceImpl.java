package com.example.quiz.content.question.service;

import com.example.quiz.common.dto.PageResponse;
import com.example.quiz.common.exception.QuestionNotFoundException;
import com.example.quiz.common.query.SortDirection;
import com.example.quiz.content.question.query.QuestionDetailsView;
import com.example.quiz.content.question.query.QuestionListView;
import com.example.quiz.content.question.query.QuestionSortField;
import com.example.quiz.content.question.repository.QuestionQueryRepository;
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
    private final QuestionQueryRepository questionQueryRepository;

    public PublicQuestionServiceImpl(QuestionQueryRepository questionQueryRepository) {
        this.questionQueryRepository = questionQueryRepository;
    }

    @Override
    public PageResponse<QuestionListView> findPublished(int page, int size, QuestionSortField sort, SortDirection direction) {
        validatePage(page);
        int normalizedSize = normalizeSize(size);

        List<QuestionListView> questions = questionQueryRepository.findPublished(page, normalizedSize, sort, direction);

        boolean hasNext = questions.size() > normalizedSize;

        if (hasNext) {
            questions = questions.subList(0, normalizedSize);
        }

        return new PageResponse<>(questions, page, normalizedSize, hasNext);
    }

    @Override
    public QuestionDetailsView findPublishedBySlug(String slug) {
        return questionQueryRepository.findPublishedBySlug(slug).orElseThrow(() -> new QuestionNotFoundException(slug));
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