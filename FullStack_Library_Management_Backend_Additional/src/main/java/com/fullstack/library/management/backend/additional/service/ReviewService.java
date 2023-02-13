package com.fullstack.library.management.backend.additional.service;

import com.fullstack.library.management.backend.additional.dto.ReviewRequestDto;
import com.fullstack.library.management.backend.additional.entity.Review;
import com.fullstack.library.management.backend.additional.exceptions.EmailMissingException;
import com.fullstack.library.management.backend.additional.exceptions.ReviewExistException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ReviewService {
    void saveBookReview(String userEmail, ReviewRequestDto reviewRequest) throws ReviewExistException, EmailMissingException;

    List<Review> getAllReviews();

    Page<Review> getAllReviewsPageWise(int page, int size);

    Review getReviewByReviewId(Long reviewId);

    Page<Review> findReviewsByBookId(Long bookId, int pageNo, int size);

    Boolean isUserReviewListed(String userEmail, Long bookId);
}
