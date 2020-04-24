package com.tallydataexport.common;

import com.tallydataexport.models.request.Envelope;
import com.tallydataexport.models.response.Body;
import lombok.extern.log4j.Log4j2;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.prefs.Preferences;

@Log4j2
public class CommonAPICall {
    Preferences userPreferences = Preferences.userRoot();
    public static String callTallyApi(String body, String URL) {
        String res=null;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .header("Content-Type", "text/xml")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        try {
            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
             res=response.body().toString();
            // log.info(res);
            log.info(response.statusCode());
        }
        catch (ConnectException ce)
        {
            res=ce.getLocalizedMessage();
            log.info(ce.getLocalizedMessage());
        }
        catch (Exception ex) {
            ex.printStackTrace();
            log.info(ex.getMessage());
        }
        return res;
    }
    /*
     * 0 index for column name
     * 1 index for table name
     * 2 index for tally full URL like : http://localhost:9000
     * */
    public synchronized static Body getDataByTableFromTally(String... texts) {
        log.info("Data fetching for Table : {} with Column : {}",texts[0],texts[1]);
        Envelope envelope = new Envelope();
        envelope.getBody().getExportData().getRequestDesc().getSqlRequest().setSql(String.format(envelope.getBody().getExportData().getRequestDesc().getSqlRequest().getSql(), texts[0], texts[1]));
        String xml = null;
        try {
            xml = MarshallerUnmarshal.objectToXmlString(JAXBContext.newInstance(Envelope.class), envelope);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        String response = CommonAPICall.callTallyApi(xml, texts[3]);
        if (response.contains("refused")) {
            throw new RuntimeException("Tally not connected");
        } else if (!response.contains("<ENVELOPE")) {
            throw new RuntimeException("Company not found or Company not selected in tally");
        }

        try {
            var resXml = (com.tallydataexport.models.response.Envelope) MarshallerUnmarshal.stringXmlToObject(JAXBContext.newInstance(com.tallydataexport.models.response.Envelope.class), response);
            log.info(resXml.getBody().getExportDataResponse().getResultDesc().getRowDesc().getHeaderCol().size());
            return resXml.getBody();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }
}
