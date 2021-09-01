package com.bootcamp.msDebitService.events;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class YankiCreatedEvent implements Event {

    private static final String EVENT = "YankiAccountCreated";
    private String yankiId;

    @Override
    public String getEvent() {
        return EVENT;
    }
}
