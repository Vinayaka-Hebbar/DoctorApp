package com.example.vinayakahebbar.doctorapp.storage;

import com.example.vinayakahebbar.doctorapp.model.EmergencyContact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vinayaka Hebbar on 10-01-2018.
 */

public class EmergencyStorage {
    private List<EmergencyContact> contactList;

    public EmergencyStorage() {
        contactList = new ArrayList<>();
    }

    public List<EmergencyContact> getContactList(){
        contactList.add(new EmergencyContact("Contect Name",12345));
        return contactList;
    }
}
