package com.tallydataexport.models.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.xml.bind.annotation.*;

@Getter
@Setter
@XmlRootElement(name = "EXPORTDATARESPONSE")
@XmlAccessorType(XmlAccessType.FIELD)
@Component
@Scope("prototype")
public class ExportDataResponse {

    @XmlAttribute(name = "ResultType")
    private String resultType="";

    @XmlElement(name = "RESULTDESC")
    private ResultDesc resultDesc;

    @XmlElement(name = "RESULTDATA")
    private ResultData resultData;
}
