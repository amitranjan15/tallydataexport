package com.tallydataexport.models.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ENVELOPE")
@XmlAccessorType(XmlAccessType.FIELD)
@Setter
@Getter
@Component
@Scope("prototype")
public class Envelope {
	@XmlElement(name = "HEADER")
	private String header="";

	@XmlElement(name = "BODY")
	private Body body;
}