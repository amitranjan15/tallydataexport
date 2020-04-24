package com.tallydataexport.controllers;

import com.tallydataexport.common.CommonAPICall;
import com.tallydataexport.common.MarshallerUnmarshal;
import com.tallydataexport.models.request.Envelope;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import static com.tallydataexport.common.CommonAPICall.getDataByTableFromTally;
import static com.tallydataexport.common.ConstantText.TALLY_URL;

@Log4j2
@Controller
public class TallyView implements Initializable {

    @FXML
    ListView<String> companyList;
    @FXML
    Label notificationLabel;

    @FXML
    Label errorConnection;

    @FXML
    TextField hostname;

    @FXML
    TextField port;

    ObservableList<String> items = FXCollections.observableArrayList();

    Preferences userPreferences = Preferences.userRoot();

    String tallyUrl = "";

    ConcurrentHashMap cMapForStoreTablesList = new ConcurrentHashMap();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (userPreferences.get("hostname", null) == null) {
            hostname.setText("localhost");
            port.setText("9001");
            tallyUrl = String.format(TALLY_URL, hostname.getText(), port.getText());
            userPreferences.put("hostname", hostname.getText());
            userPreferences.put("port", port.getText());
        } else {
            hostname.setText(userPreferences.get("hostname", ""));
            port.setText(userPreferences.get("port", ""));
            tallyUrl = String.format(TALLY_URL, hostname.getText(), port.getText());
        }
        log.info(tallyUrl);
        items.clear();
        getCompnayList();
    }

    public void getCompnayList() {

        String response = checkConnection();
        if (response.contains("refused")) {
            notificationLabel.setText("Tally not connected");
            return;
        } else if (!response.contains("<ENVELOPE")) {
            notificationLabel.setText("Company not found or Company not selected in tally");
            return;
        }
        notificationLabel.setText("");

        try {
            var resXml = (com.tallydataexport.models.response.Envelope) MarshallerUnmarshal.stringXmlToObject(JAXBContext.newInstance(com.tallydataexport.models.response.Envelope.class), response);
            log.info(resXml.getBody().getExportDataResponse().getResultDesc().getRowDesc().getHeaderCol().size());
            int i;
            var headerList = resXml.getBody().getExportDataResponse().getResultDesc().getRowDesc().getHeaderCol();
            var rowList = resXml.getBody().getExportDataResponse().getResultData().getRow();
            for (i = 0; i < headerList.size(); i++) {
                if (headerList.get(i).getName().equalsIgnoreCase("$Name")) {
                    break;
                }
            }

            for (int j = 0; j < rowList.size(); j++) {
                items.add(rowList.get(j).getCol().get(i));
            }
            log.info(resXml.getBody().getExportDataResponse().getResultData().getRow().get(0).getCol().size());
            companyList.setItems(items);
            scheduledTask();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public void refreshBtn(ActionEvent actionEvent) throws Exception {
        items.clear();
        getCompnayList();
    }

    public void saveURL(ActionEvent actionEvent) throws Exception {
        tallyUrl = String.format(TALLY_URL, hostname.getText(), port.getText());
        userPreferences.put("hostname", hostname.getText());
        userPreferences.put("port", port.getText());
        String response = checkConnection();
        if (response.contains("refused")) {
            errorConnection.setText("Tally not connected");
            errorConnection.setStyle("-fx-text-fill: red;");
            return;
        }
        errorConnection.setText("Successfully connected");
        errorConnection.setStyle("-fx-text-fill: green;");
    }

    public String checkConnection() {
        Envelope en = new Envelope();
        en.getBody().getExportData().getRequestDesc().getSqlRequest().setSql(String.format(en.getBody().getExportData().getRequestDesc().getSqlRequest().getSql(), "*", "Company"));
        String xml = "";
        try {
            xml = MarshallerUnmarshal.objectToXmlString(JAXBContext.newInstance(Envelope.class), en);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return CommonAPICall.callTallyApi(xml, tallyUrl);
    }

    public void clearDataFromLocal() {
        try {
            userPreferences.clear();
        } catch (BackingStoreException e) {
            log.error(e.getMessage());
        }
    }


    public void scheduledTask() {
        var resposne = getDataByTableFromTally("$Name", "ODBCTables", tallyUrl);
        var headerList = resposne.getExportDataResponse().getResultDesc().getRowDesc().getHeaderCol();
        var rowList = resposne.getExportDataResponse().getResultData().getRow();
        SortedSet<String> tableList = new TreeSet<>();
        for (int i = 0; i < rowList.size(); i++) {
            tableList.addAll(rowList.get(i).getCol());
        }
        // tableList.forEach(System.out::println);
        System.out.println(tableList.size());
    }
}
