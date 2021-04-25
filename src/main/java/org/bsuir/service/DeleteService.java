package org.bsuir.service;

import org.bsuir.model.Patient;
import org.bsuir.util.CustomDate;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class DeleteService {

    private DeleteService() {

    }

    public static int delete(List<Patient> listOfPatients, String fullName, String address) {
        int counter = 0;

        for (int i = 0; i < listOfPatients.size(); ++i) {
            Patient currentPatient = listOfPatients.get(i);
            if (currentPatient.getFullName().equals(fullName)
                    || currentPatient.getPlaceOfResidence().equals(address)) {
                listOfPatients.remove(i--);
                ++counter;
            }
        }
        return counter;
    }

    public static int delete(List<Patient> listOfPatients, CustomDate date) {
        int counter = 0;

        for (int i = 0; i < listOfPatients.size(); ++i) {
            Patient currentPatient = listOfPatients.get(i);
            if (currentPatient.getBirthday().equals(date)) {
                listOfPatients.remove(i--);
                ++counter;
            }
        }
        return counter;
    }

    public static int delete(List<Patient> listOfPatients, String doctorsFullName, CustomDate dateOfReceipt) {
        int counter = 0;

        for (int i = 0; i < listOfPatients.size(); ++i) {
            Patient currentPatient = listOfPatients.get(i);
            if (currentPatient.getDoctorsFullName().equals(doctorsFullName)
                    || currentPatient.getDateOfReceipt().equals(dateOfReceipt)) {
                listOfPatients.remove(i--);
                ++counter;
            }
        }
        return counter;
    }
}
