package com.tallydataexport.models.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.xml.bind.annotation.*;

@Getter
@Setter
@XmlRootElement(name = "SQLREQUEST")
@XmlAccessorType(XmlAccessType.FIELD)
@Component
@Scope("prototype")
public class SqlRequest {

    @XmlAttribute(name = "Type")
    String type="General";
    @XmlAttribute(name = "Method")
    String method="SQLExecute";

    @XmlValue
    private String sql="SELECT %s FROM %s";
}
