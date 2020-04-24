package com.tallydataexport.models.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.xml.bind.annotation.*;

@Getter
@Setter
@XmlRootElement(name = "BODY")
@XmlAccessorType(XmlAccessType.FIELD)
@Component
@Scope("prototype")
public class Body {
    @XmlElement(name = "EXPORTDATARESPONSE")
    private ExportDataResponse exportDataResponse;
}
