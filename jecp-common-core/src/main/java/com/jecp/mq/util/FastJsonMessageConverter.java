package com.jecp.mq.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.AbstractJsonMessageConverter;
import org.springframework.amqp.support.converter.ClassMapper;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.MessageConversionException;

import com.alibaba.fastjson.JSON;

public class FastJsonMessageConverter extends AbstractJsonMessageConverter {

	private static Log log = LogFactory.getLog(FastJsonMessageConverter.class);

	private static ClassMapper classMapper = new DefaultClassMapper();

	public FastJsonMessageConverter() {
		super();
	}

	@Override
	protected Message createMessage(Object object, MessageProperties messageProperties) {
		byte[] bytes = null;
		try {
			String jsonString = JSON.toJSONString(object);
			bytes = jsonString.getBytes(getDefaultCharset());
		} catch (IOException e) {
			throw new MessageConversionException("Failed to convert Message content", e);
		}
		messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
		messageProperties.setContentEncoding(getDefaultCharset());
		if (bytes != null) {
			messageProperties.setContentLength(bytes.length);
		}
		classMapper.fromClass(object.getClass(), messageProperties);
		return new Message(bytes, messageProperties);
	}

	@Override
	public Object fromMessage(Message message) throws MessageConversionException {
		Object content = null;
		MessageProperties properties = message.getMessageProperties();
		if (properties != null) {
			String contentType = properties.getContentType();
			if (contentType != null && contentType.contains("json")) {
				String encoding = properties.getContentEncoding();
				if (encoding == null) {
					encoding = getDefaultCharset();
				}
				try {
					Class<?> targetClass = getClassMapper().toClass(message.getMessageProperties());
					content = convertBytesToObject(message.getBody(), encoding, targetClass);
				} catch (IOException e) {
					throw new MessageConversionException("Failed to convert Message content", e);
				}
			} else {
				log.warn("Could not convert incoming message with content-type [" + contentType + "]");
			}
		}
		if (content == null) {
			content = message.getBody();
		}
		return content;
	}

	private Object convertBytesToObject(byte[] body, String encoding, Class<?> clazz)
			throws UnsupportedEncodingException {
		String contentAsString = new String(body, encoding);
		return JSON.parseObject(contentAsString, clazz);
	}
}
