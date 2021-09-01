package com.bootcamp.msDebitService.consumer;

import com.bootcamp.msDebitService.events.Event;

public interface EventConsumer<T extends Event> {
	  void consumeEvent(T event);
}
