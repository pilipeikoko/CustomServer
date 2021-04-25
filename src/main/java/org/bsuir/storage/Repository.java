package org.bsuir.storage;

import org.bsuir.model.Patient;
import org.bsuir.parser.PatientsXMLReader;
import org.bsuir.parser.PatientsXMLWriter;
import org.bsuir.service.DeleteService;
import org.bsuir.service.SearchService;
import org.bsuir.util.CustomDate;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Repository {
    private List<Patient> listOfPatients;

    public Repository() {
        listOfPatients = Collections.synchronizedList(new ArrayList<>());
    }

    public List<Patient> getListOfPatients() {
        return listOfPatients;
    }

    public void readFromFile(String source) throws ParserConfigurationException, SAXException, IOException {
        PatientsXMLReader reader = new PatientsXMLReader(new File(source));

        listOfPatients = reader.readAll();
    }

    public void saveToFile(String target) throws IOException, TransformerException, SAXException, ParserConfigurationException {
        PatientsXMLWriter writer = new PatientsXMLWriter(new File(target));

        writer.writeAll(listOfPatients);
    }

    public int delete(String fullName, String address) {
        return DeleteService.delete(listOfPatients, fullName, address);
    }

    public int delete(CustomDate date) {
        return DeleteService.delete(listOfPatients, date);
    }

    public int delete(String doctorsFullName, CustomDate dateOfReceipt) {
        return DeleteService.delete(listOfPatients, doctorsFullName, dateOfReceipt);
    }

    public List<Patient> search(CustomDate date) {
        return SearchService.search(listOfPatients, date);
    }

    public List<Patient> search(String fullName, String address){
        return SearchService.search(listOfPatients,fullName,address);
    }

    public List<Patient> search(String doctorsFullName, CustomDate dateOfReceipt){
        return SearchService.search(listOfPatients,doctorsFullName,dateOfReceipt);
    }

    public void add(Patient patient) {
        listOfPatients.add(patient);
    }

    public List<Patient> getPage(int page, int amountOfDataOnThePage) {
        List<Patient> listOfPatientsOnThePage = new ArrayList<>();

        int currentRowNumber = (page - 1) * amountOfDataOnThePage;
        int lastRowNumber = (page) * amountOfDataOnThePage;

        int totalNumberOfLines = listOfPatients.size();

        if (lastRowNumber > totalNumberOfLines)
            lastRowNumber = totalNumberOfLines;


        for (; currentRowNumber < lastRowNumber; ++currentRowNumber) {
            //todo exception throws here
            listOfPatientsOnThePage.add(listOfPatients.get(currentRowNumber));
        }

        return listOfPatientsOnThePage;

    }

    public List<Patient> getPage(int page, int amountOfDataOnThePage, List<Patient> listOfPatients) {
        List<Patient> listOfPatientsOnThePage = new ArrayList<>();

        int currentRowNumber = (page - 1) * amountOfDataOnThePage;
        int lastRowNumber = (page) * amountOfDataOnThePage;

        int totalNumberOfLines = listOfPatients.size();

        if (lastRowNumber > totalNumberOfLines)
            lastRowNumber = totalNumberOfLines;

        for (; currentRowNumber < lastRowNumber; ++currentRowNumber) {
            //todo exception throws here
            listOfPatientsOnThePage.add(listOfPatients.get(currentRowNumber));
        }

        return listOfPatientsOnThePage;
    }
}
