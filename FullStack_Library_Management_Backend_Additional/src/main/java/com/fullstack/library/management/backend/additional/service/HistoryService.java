package com.fullstack.library.management.backend.additional.service;

import com.fullstack.library.management.backend.additional.entity.History;
import com.fullstack.library.management.backend.additional.response.ResponseMessages;
import org.springframework.data.domain.Page;

import java.util.List;

public interface HistoryService {
    ResponseMessages<Page<History>> getBookCheckOutHistoryForUserPageWise(String userEmail, int page, int size);

    ResponseMessages<List<History>> getAllHistory();
}
