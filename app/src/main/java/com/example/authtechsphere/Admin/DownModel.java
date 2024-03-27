// This is the model class for the download list
package com.example.authtechsphere.Admin;

// Creating a class for DownModel
public class DownModel {

    // Declaring all the variables
    String Name,Link;


    // Creating a getter and setter for each variable
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    // Creating a constructor for the DownModel class
    public DownModel(String Name, String Link){
        this.Link=Link;
        this.Name=Name;

    }

}