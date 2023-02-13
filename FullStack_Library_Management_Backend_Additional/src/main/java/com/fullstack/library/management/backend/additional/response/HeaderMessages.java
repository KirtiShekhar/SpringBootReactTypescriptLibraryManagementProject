package com.fullstack.library.management.backend.additional.response;

import java.util.List;
import java.util.ArrayList;

public class HeaderMessages
{
    private List<HeaderMessage> messageList = new ArrayList<>();
    private List<HeaderMessage> warnings = new ArrayList<>();
    private List<HeaderMessage> errors = new ArrayList<>();

    public List<HeaderMessage> getMessageList()
    {
        if(messageList == null){ return new ArrayList<>(); }
        return messageList;
    }

    public void setMessageList(List<HeaderMessage> messageList) {
        this.messageList = messageList;
    }

    public List<HeaderMessage> getWarnings()
    {
        if(warnings == null){ return new ArrayList<>(); }
        return warnings;
    }

    public void setWarnings(List<HeaderMessage> warnings) {
        this.warnings = warnings;
    }

    public List<HeaderMessage> getErrors()
    {
        if(errors == null){ return new ArrayList<>(); }
        return errors;
    }

    public void setErrors(List<HeaderMessage> errors)
    {
        this.errors = errors;
    }

    public void addMessages(HeaderMessage message)
    {
        if(messageList == null){ messageList = new ArrayList<>(); }
        messageList.add(message);
    }

    public void addWarnings(HeaderMessage warning)
    {
        if(warnings == null){ warnings = new ArrayList<>(); }
        warnings.add(warning);
    }

    public void addError(HeaderMessage error)
    {
        if(errors == null){ errors = new ArrayList<>(); }
        errors.add(error);
    }

    public void merge(HeaderMessages headerMessagesMerge)
    {
        if(headerMessagesMerge == null){ return; }
        headerMessagesMerge.getMessageList().forEach(this::addMessages);
        headerMessagesMerge.getWarnings().forEach(this::addWarnings);
        headerMessagesMerge.getErrors().forEach(this::addError);
    }
}
