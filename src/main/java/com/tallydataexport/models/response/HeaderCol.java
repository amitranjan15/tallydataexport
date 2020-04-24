package com.tallydataexport.models.response;

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
@XmlRootElement(name = "COL")
@XmlAccessorType(XmlAccessType.FIELD)
@Component
@Scope("prototype")
public class HeaderCol {

    @XmlElement(name = "NAME")
    private String name;

    @XmlElement(name = "ALIAS")
    private String alias;

    @XmlElement(name = "TYPE")
    private String type;

    @XmlElement(name = "LENGTH")
    private String lenght;

    @XmlElement(name = "PRECISION")
    private String prcision;

    @XmlElement(name = "NULLABLE")
    private String nullable;
}
