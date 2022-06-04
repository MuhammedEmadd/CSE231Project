package com.company;

import java.util.ArrayList;

public class Fields{
    private String mandatory, request, type, fieldName, allowedValues, parent;
    private boolean object;
    private ArrayList<String> arrayOfStrings = new ArrayList<>();
    public Fields(String request, String fieldName, String type, String allowedValues, String mandatory){
        this.request = request;
        this.fieldName = fieldName;
        this.type = type;
        if (allowedValues == "") {
            this.allowedValues = "All Values Are Allowed";
        }
        else {
            this.allowedValues = allowedValues;
        }
        if (mandatory.equalsIgnoreCase("Y")) {
            this.mandatory = "Yes";
        }
        else if (mandatory.equalsIgnoreCase("N")) {
            this.mandatory = "No";
        }
        arrayOfStrings = splitStrings(this.fieldName);
        if (type.equalsIgnoreCase("string")) {
            object = false;
        }
        else {
            object = true;
        }
        setParent(arrayOfStrings);
    }
    private ArrayList<String> splitStrings (String fieldName) {
        ArrayList<String> stringArrayList = new ArrayList<>();
        String[] arrayStrings = fieldName.split("/");
        for (int i = 0; i<arrayStrings.length; i++) {
            stringArrayList.add(arrayStrings[i]);
        }
        stringArrayList.remove(0);
        return stringArrayList;
    }

    public boolean isObject() {
        return object;
    }
    private void setParent (ArrayList<String> stringArrayList) {
        if (stringArrayList.size()<=2) {
            parent = stringArrayList.get(0);
        }
        else if (stringArrayList.size() >= 2 && this.isObject()) {
            parent = stringArrayList.get(stringArrayList.size()-2);
        }
        else if (stringArrayList.size()>2 && !this.isObject()) {
            parent = stringArrayList.get(stringArrayList.size()-2);
        }
    }

    public String getParent() {
        return parent;
    }

    @Override
    public String toString() {
        return "I/o: " + request + " FieldName: " + arrayOfStrings + " Parent: " + parent + " type: " + type + " allowedValues: " + allowedValues + " Mandatory: " + mandatory + " isObject: " + object;
    }

    public ArrayList<String> getArrayOfStrings() {
        return arrayOfStrings;
    }

    public String getType() {
        return type;
    }


    public String getMandatory () {
        return mandatory;
    }

    public String getAllowedValues() {
        return allowedValues;
    }

    public String getRequest() {
        return request;
    }
}
