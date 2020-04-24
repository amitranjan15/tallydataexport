package com.tallydataexport.models.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@XmlRootElement(name = "REQUESTDESC")
@XmlAccessorType(XmlAccessType.FIELD)
@Component
@Scope("prototype")
public class RequestDesc {

    @XmlElement(name = "REPORTNAME")
    private String reportName="ODBC Report";

    @XmlElement(name = "SQLREQUEST")
    private SqlRequest sqlRequest=new SqlRequest();

    @XmlElement(name = "STATICVARIABLES")
    private StaticVariables staticVariables;
}
