package com.fullstack.library.management.backend.additional.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseMessages<T>
{
    private Messages messages = new Messages();
    private HeaderMessages headerMessages = new HeaderMessages();
    private T data;

    public ResponseMessages(Messages messages, T data)
    {
        this.messages = messages;
        this.data = data;
    }

    public ResponseMessages(HeaderMessages headerMessages, T data)
    {
        this.headerMessages = headerMessages;
        this.data = data;
    }

    public void addMessages(String message)
    {
        if(messages == null){ messages = new Messages(); }
        messages.addMessages(message);
    }

    public void addWarnings(String warning)
    {
        if(warning == null){ messages = new Messages(); }
        messages.addWarnings(warning);
    }

    public void addError(String error)
    {
        if(error == null){ messages = new Messages(); }
        messages.addError(error);
    }

    public void addMessages(HeaderMessage message)
    {
        if(messages == null){ headerMessages = new HeaderMessages(); }
        headerMessages.addMessages(message);
    }

    public void addWarnings(HeaderMessage warning)
    {
        if(headerMessages == null){ headerMessages = new HeaderMessages(); }
        headerMessages.addWarnings(warning);
    }

    public void addError(HeaderMessage error)
    {
        if(headerMessages == null){ headerMessages = new HeaderMessages(); }
        headerMessages.addError(error);
    }

    public void merge(Messages messagesMerge)
    {
        if(messagesMerge == null){ return; }
        messagesMerge.getMessageList().forEach(this::addMessages);
        messagesMerge.getWarnings().forEach(this::addWarnings);
        messagesMerge.getErrors().forEach(this::addError);
    }

    public void merge(HeaderMessages headerMessagesMerge)
    {
        if(headerMessagesMerge == null){ return; }
        headerMessagesMerge.getMessageList().forEach(this::addMessages);
        headerMessagesMerge.getWarnings().forEach(this::addWarnings);
        headerMessagesMerge.getErrors().forEach(this::addError);
    }
}
