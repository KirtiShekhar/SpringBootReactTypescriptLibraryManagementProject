package com.fullstack.library.management.backend.additional.response;

import java.util.ArrayList;
import java.util.List;

public class Messages
{
    private List<String> messageList = new ArrayList<>();
    private List<String> warnings = new ArrayList<>();
    private List<String> errors = new ArrayList<>();

    public List<String> getMessageList()
    {
        if(messageList == null){ return new ArrayList<>(); }
        return messageList;
    }

    public void setMessageList(List<String> messageList) {
        this.messageList = messageList;
    }

    public List<String> getWarnings()
    {
        if(warnings == null){ return new ArrayList<>(); }
        return warnings;
    }

    public void setWarnings(List<String> warnings) {
        this.warnings = warnings;
    }

    public List<String> getErrors()
    {
        if(errors == null){ return new ArrayList<>(); }
        return errors;
    }

    public void setErrors(List<String> errors)
    {
        this.errors = errors;
    }

    public void addMessages(String message)
    {
        if(messageList == null){ messageList = new ArrayList<>(); }
        messageList.add(message);
    }

    public void addWarnings(String warning)
    {
        if(warnings == null){ warnings = new ArrayList<>(); }
        warnings.add(warning);
    }

    public void addError(String error)
    {
        if(errors == null){ errors = new ArrayList<>(); }
        errors.add(error);
    }

    public void merge(Messages messagesMerge)
    {
        if(messagesMerge == null){ return; }
        messagesMerge.getMessageList().forEach(this::addMessages);
        messagesMerge.getWarnings().forEach(this::addWarnings);
        messagesMerge.getErrors().forEach(this::addError);
    }
}
