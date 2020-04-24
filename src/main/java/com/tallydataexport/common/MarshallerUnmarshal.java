package com.tallydataexport.common;

import lombok.extern.log4j.Log4j2;

import java.io.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

@Log4j2
public class MarshallerUnmarshal {

    /*
     * context = JAXBContext.newInstance(anyClass.class)
     * obj = new anyClass();
     * */
    public static String objectToXmlString(JAXBContext context, Object obj) {
        String xmlString = "";
        try {
            Marshaller m = context.createMarshaller();

            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE); // To format XML

            StringWriter sw = new StringWriter();
            m.marshal(obj, sw);
            xmlString = sw.toString();

        } catch (JAXBException e) {
            e.printStackTrace();
        }

        //log.info(xmlString);
        return xmlString;
    }

    /*
     * jaxbContext = JAXBContext.newInstance(anyClass.class)
     * xml = 'xml in string';
     * return object type so you have to cast into you own class : (anyClass) jaxbUnmarshaller.unmarshal(new StringReader(xml));
     * */
    public static Object stringXmlToObject(JAXBContext jaxbContext, String xml) {
        Object returnObj = null;
        try {
            // JAXBContext.newInstance(Envelope.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            returnObj = jaxbUnmarshaller.unmarshal(new StringReader(xml));

            //log.info(returnObj);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return returnObj;
    }

}
