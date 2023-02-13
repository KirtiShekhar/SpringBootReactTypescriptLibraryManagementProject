package com.fullstack.library.management.backend.additional.serviceImplementation;

import com.fullstack.library.management.backend.additional.entity.History;
import com.fullstack.library.management.backend.additional.response.ResponseMessages;
import com.fullstack.library.management.backend.additional.service.HistoryService;
import com.fullstack.library.management.backend.additional.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryServiceImplementation implements HistoryService
{
    @Autowired
    private HistoryRepository historyRepository;

    @Override
    public ResponseMessages<Page<History>> getBookCheckOutHistoryForUserPageWise(String userEmail, int page, int size)
    {
        ResponseMessages<Page<History>> historyPageWiseResponseMessage = new ResponseMessages<>();
        Pageable pageable = PageRequest.of(page, size);
        historyPageWiseResponseMessage.setData(historyRepository.findBooksByUserEmail(userEmail,pageable));
        return historyPageWiseResponseMessage;
    }

    @Override
    public ResponseMessages<List<History>> getAllHistory()
    {
        ResponseMessages<List<History>> historyListResponseMessages = new ResponseMessages<>();
        historyListResponseMessages.setData(historyRepository.findAll());
        return historyListResponseMessages;
    }
}
