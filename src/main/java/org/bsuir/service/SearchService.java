package org.bsuir.service;

import org.bsuir.model.Patient;
import org.bsuir.util.CustomDate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SearchService {

    private SearchService() {

    }

    public static List<Patient> search(List<Patient> listOfPatients, CustomDate date) {

        List<Patient> result = new ArrayList<>();
        for (Patient currentPatient : listOfPatients) {
            if (currentPatient.getBirthday().equals(date)) {
                result.add(currentPatient);
            }
        }
        return result;
    }

    public static List<Patient> search(List<Patient> listOfPatients, String fullName, String address) {
        List<Patient> result = new ArrayList<>();

        for (Patient currentPatient : listOfPatients) {
            if (currentPatient.getFullName().equals(fullName)
                    || currentPatient.getPlaceOfResidence().equals(address)) {
                result.add(currentPatient);
            }
        }
        return result;
    }

    public static List<Patient> search(List<Patient> listOfPatients, String doctorsFullName, CustomDate dateOfReceipt) {
        List<Patient> result = new ArrayList<>();

        for (Patient currentPatient : listOfPatients) {
            if (currentPatient.getDoctorsFullName().equals(doctorsFullName)
                    || currentPatient.getDateOfReceipt().equals(dateOfReceipt)) {
                result.add(currentPatient);
            }
        }
        return result;
    }
}
