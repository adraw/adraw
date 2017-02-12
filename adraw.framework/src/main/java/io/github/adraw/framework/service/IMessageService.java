package io.github.adraw.framework.service;

import io.github.adraw.framework.model.Message;

public interface IMessageService {
	Message insertDirect(Message message);
	Message insertComment(Message message);
	Message insertAttention(Message message);
}
