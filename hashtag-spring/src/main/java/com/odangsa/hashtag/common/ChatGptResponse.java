package com.odangsa.hashtag.common;

import java.awt.*;
import java.util.List;
import java.util.Optional;

public record ChatGptResponse(List<Choice> choices) {
    public Optional<String> getText(){
        if(choices == null || choices.isEmpty())
            return Optional.empty();
        return Optional.of(choices.get(0).message.content);
    }

    record Choice(Message message){}
    record Message(String role, String content){}
}
