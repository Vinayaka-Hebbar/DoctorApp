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
        contactList.add(new EmergencyContact("Sanjeevini Ambulance Service",9448496666L));
        contactList.add(new EmergencyContact("VMEDO Ambulance Service",9343180000L));
        contactList.add(new EmergencyContact("Yashaswini Ambulance Service",9632966665L));
        return contactList;
    }
}
