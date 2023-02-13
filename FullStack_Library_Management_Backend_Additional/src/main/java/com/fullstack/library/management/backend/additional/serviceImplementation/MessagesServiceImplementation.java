package com.fullstack.library.management.backend.additional.serviceImplementation;

import com.fullstack.library.management.backend.additional.dto.AdminMessageRequest;
import com.fullstack.library.management.backend.additional.dto.UserMessageRequest;
import com.fullstack.library.management.backend.additional.entity.Messages;
import com.fullstack.library.management.backend.additional.exceptions.EmailMissingException;
import com.fullstack.library.management.backend.additional.exceptions.MessagesNotFoundException;
import com.fullstack.library.management.backend.additional.repository.MessagesRepository;
import com.fullstack.library.management.backend.additional.response.ResponseMessages;
import com.fullstack.library.management.backend.additional.service.MessagesService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MessagesServiceImplementation implements MessagesService
{
    @Autowired
    private MessagesRepository messagesRepository;

    @Override
    public void postMessage(UserMessageRequest messagesRequest, String userEmail)
    {
        Messages messages = new Messages();
        BeanUtils.copyProperties(messagesRequest,messages);
        messages.setUserEmail(userEmail);
        messagesRepository.save(messages);
    }

    @Override
    public ResponseMessages<Page<Messages>> findMessagesByUserPageWise(String userEmail, int page, int size)
    {
        ResponseMessages<Page<Messages>> messagePageWiseResponseMessage = new ResponseMessages<>();
        Pageable pageable = PageRequest.of(page, size);
        messagePageWiseResponseMessage.setData(messagesRepository.findByUserEmail(userEmail,pageable));
        return messagePageWiseResponseMessage;
    }

    @Override
    public ResponseMessages<Page<Messages>> findMessagesByClosedPageWise(boolean closed, int page, int size)
    {
        ResponseMessages<Page<Messages>> messagePageWiseResponseMessage = new ResponseMessages<>();
        Pageable pageable = PageRequest.of(page, size);
        messagePageWiseResponseMessage.setData(messagesRepository.findByClosed(closed,pageable));
        return messagePageWiseResponseMessage;
    }

    @Override
    public void putMessage(AdminMessageRequest adminMessageRequest, String userEmail) throws MessagesNotFoundException, EmailMissingException
    {
        Optional<Messages> messages = messagesRepository.findById(adminMessageRequest.getQuestionId());

        if(!messages.isPresent())
        {
            throw new MessagesNotFoundException("Message not found");
        }

        messages.get().setAdminEmail(userEmail);
        messages.get().setMessageResponse(adminMessageRequest.getMessageResponse());
        messages.get().setClosed(true);
        messagesRepository.save(messages.get());
    }


}
