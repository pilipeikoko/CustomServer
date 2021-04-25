package org.bsuir.controller;

import org.bsuir.model.Patient;
import org.bsuir.request.*;
import org.bsuir.response.*;
import org.bsuir.exception.CustomServerException;
import org.bsuir.util.CustomDate;
import org.bsuir.storage.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.List;

public class ServerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerController.class);

    private final Repository repository;
    private ServerResponse serverResponse;

    public ServerResponse handle(ServerRequest serverRequest) throws CustomServerException {

        LOGGER.info("Request: " + serverRequest.getRequestType().toString().toLowerCase());
        switch (serverRequest.getRequestType()) {
            case DELETE_BY_DOCTORS_NAME_OR_DATE -> {
                DeleteByDoctorsFullNameOrDate deleteRequest = (DeleteByDoctorsFullNameOrDate) serverRequest;
                int amount = delete(deleteRequest.getDoctorsFullName(), deleteRequest.getBirthday());
                serverResponse = new DeleteResponse(amount);
            }
            case SEARCH_BY_FULL_NAME_OR_ADDRESS -> {
                SearchByFullNameOrAddress deleteRequest = (SearchByFullNameOrAddress) serverRequest;
                List<Patient> list = search(deleteRequest.getFullName(), deleteRequest.getAddress());
                serverResponse = new SearchResponse(list);
            }
            case SEARCH_BY_BIRTHDAY -> {
                SearchByDate deleteRequest = (SearchByDate) serverRequest;
                List<Patient> list = search(deleteRequest.getDate());
                serverResponse = new SearchResponse(list);
            }
            case SEARCH_BY_DOCTORS_NAME_OR_DATE -> {
                SearchByDoctorsFullNameOrDate deleteRequest = (SearchByDoctorsFullNameOrDate) serverRequest;
                List<Patient> list = search(deleteRequest.getDoctorsFullName(), deleteRequest.getBirthday());
                serverResponse = new SearchResponse(list);
            }
            case ADD -> {
                AddRequest addRequest = (AddRequest) serverRequest;
                add(addRequest.getPatient());
                serverResponse = new AddResponse(true);
            }
            case DELETE_BY_FULL_NAME_OR_ADDRESS -> {
                DeleteByFullNameOrAddress deleteRequest = (DeleteByFullNameOrAddress) serverRequest;
                int amount = delete(deleteRequest.getFullName(), deleteRequest.getAddress());
                serverResponse = new DeleteResponse(amount);
            }

            case DELETE_BY_BIRTHDAY -> {
                DeleteByDate deleteRequest = (DeleteByDate) serverRequest;
                int amount = delete(deleteRequest.getDate());
                serverResponse = new DeleteResponse(amount);
            }

            case SAVE_TO_FILE -> {
                SaveToFile saveToFile = (SaveToFile) serverRequest;
                save(saveToFile.getPath());
                serverResponse = new FileResponse(true);
            }
            case READ_FROM_FILE -> {
                ReadFromFile readFromFile = (ReadFromFile) serverRequest;
                read(readFromFile.getPath());
                serverResponse = new FileResponse(true);
            }
            case FIND_PAGE -> {
                FindPage findPage = (FindPage) serverRequest;
                List<Patient> list = getPage(findPage.getPage(), findPage.getAmountOfDataOnThePage());
                serverResponse = new PageResponse(list);
            }
            case GET_AMOUNT_OF_PATIENTS -> {
                GetAmountOfPatients getAmountOfPatients = (GetAmountOfPatients) serverRequest;
                int amountOfPatients = repository.getListOfPatients().size();
                serverResponse = new AmountResponse(amountOfPatients);
            }
            case FIND_PAGE_BY_LIST -> {
                FindPageByList findPageByList = (FindPageByList) serverRequest;
                List<Patient> list = getPage(findPageByList.getPage(), findPageByList.getAmountOfDataOnThePage(), findPageByList.getListOfPatients());
                serverResponse = new PageResponse(list);
            }
            default -> throw new CustomServerException("Wrong attribute");
        }

        return serverResponse;
    }

    private List<Patient> getPage(int page, int amountOfDataOnThePage, List<Patient> listOfPatients) {
        return repository.getPage(page, amountOfDataOnThePage, listOfPatients);
    }

    public ServerController(Repository repository) {
        this.repository = repository;
        ;
    }

    public int delete(String fullName, String address) {
        return repository.delete(fullName, address);
    }

    public int delete(CustomDate date) {
        return repository.delete(date);
    }

    public int delete(String doctorsFullName, CustomDate dateOfReceipt) {
        return repository.delete(doctorsFullName, dateOfReceipt);
    }

    public List<Patient> search(String fullName, String address) {
        return repository.search(fullName, address);
    }

    public List<Patient> search(CustomDate date) {
        return repository.search(date);
    }

    public List<Patient> search(String doctorsFullName, CustomDate dateOfReceipt) {
        return repository.search(doctorsFullName, dateOfReceipt);
    }

    public List<Patient> getPage(int page, int amount) {
        return repository.getPage(page, amount);
    }

    public void save(String path) throws CustomServerException {
        try {
            repository.saveToFile(path);
        } catch (IOException | TransformerException | SAXException | ParserConfigurationException exception) {
            serverResponse = new FileResponse(false);
            throw new CustomServerException("Couldn't save to file");
        }
    }

    public void read(String path) throws CustomServerException {
        try {
            repository.readFromFile(path);
        } catch (ParserConfigurationException | SAXException | IOException exception) {
            serverResponse = new FileResponse(false);
            throw new CustomServerException("Couldn't read from file");
        }
    }

    public void add(Patient patient) {
        repository.add(patient);
    }

}
