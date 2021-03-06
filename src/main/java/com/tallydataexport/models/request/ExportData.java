package com.tallydataexport.models.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@XmlRootElement(name = "EXPORTDATA")
@XmlAccessorType(XmlAccessType.FIELD)
@Component
@Scope("prototype")
public class ExportData {

    @XmlElement(name = "REQUESTDESC")
    private RequestDesc requestDesc=new RequestDesc();
}
