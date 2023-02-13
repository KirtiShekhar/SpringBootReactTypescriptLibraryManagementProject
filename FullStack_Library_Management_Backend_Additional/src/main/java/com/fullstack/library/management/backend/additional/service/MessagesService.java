package com.fullstack.library.management.backend.additional.service;

import com.fullstack.library.management.backend.additional.dto.AdminMessageRequest;
import com.fullstack.library.management.backend.additional.dto.UserMessageRequest;
import com.fullstack.library.management.backend.additional.entity.Messages;
import com.fullstack.library.management.backend.additional.exceptions.EmailMissingException;
import com.fullstack.library.management.backend.additional.exceptions.MessagesNotFoundException;
import com.fullstack.library.management.backend.additional.response.ResponseMessages;
import org.springframework.data.domain.Page;

public interface MessagesService {
    void postMessage(UserMessageRequest messagesRequest, String userEmail);

    ResponseMessages<Page<Messages>> findMessagesByUserPageWise(String userEmail, int page, int size);

    ResponseMessages<Page<Messages>> findMessagesByClosedPageWise(boolean closed, int page, int size);

    void putMessage(AdminMessageRequest adminMessageRequest, String userEmail) throws MessagesNotFoundException, EmailMissingException;
}
