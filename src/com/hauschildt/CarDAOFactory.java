package com.hauschildt;

public class CarDAOFactory {
    private static final String DAO_SOURCE = "CSV";

    public static CarDAO getCarDAO(){
        CarDAO dao = null;
        switch(DAO_SOURCE){
            case "CSV":
                dao = new CarDAOCSV();
                break;
            case "XML":
                dao = new CarDAOXML();
                break;
            case "MYSQL":
                dao = new CarDAOMySQL();
                break;
            default:
        }
        return dao;
    }
}
