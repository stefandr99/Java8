package controllers;

import actors.Album;
import actors.Chart;
import dao.ChartDaoImp;
import database.Database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ChartController {
    private ChartDaoImp query = new ChartDaoImp();

    /**
     * Controller ce va introduce chart-uri in bd
     * @param albumId - id-ul albumului
     * @param criteria - criteriul dupa care se face clasamentul
     * @param rank - rank-ul albumului in clasamentul indicat de criteriu
     * @param db - conexiunea la bd
     * @throws SQLException
     */
    public void create(int albumId, String criteria, int rank, Database db) throws SQLException {
        if(query.create(albumId, criteria, rank, db)) {
            System.out.println("Ati introdus un nou album in chart!");
        } else {
            System.out.println("Ups! A intervenit o problema. Va rugam reveniti!");
        }
    }

    /**
     * Controller-ul care va gestiona returnarea clasamentului in functie de criteriul primit
     *  si de tara (daca e nevoie)
     * @param criteria - criteriul dupa care se realizeaza clasamentul
     * @param country - DACA ESTE CAZUL - tara dupa care se realizeaza clasamentul
     * @param db - conexiunea la bd
     * @throws SQLException
     */
    public void findByCriteria(String criteria, String country, Database db) throws SQLException {
        System.out.println();
        List<Chart> charts;
        charts = query.findByCriteria(criteria, country, db);
        if(criteria.compareTo("artist") == 0)
            System.out.println("Clasament dupa popularitatea artistului");
        if(criteria.compareTo("album") == 0)
            System.out.println("Clasament dupa numarul de vanzari ale albumelor");
        if(criteria.compareTo("country") == 0)
            switch (country) {
                case ("Romania"): System.out.println("Clasamentul albumelor din ROMANIA"); break;
                case ("USA"): System.out.println("Clasamentul albumelor din USA"); break;
                case ("UK"): System.out.println("Clasamentul albumelor din UK"); break;
            }

        if(charts != null) {
            for (Chart chart : charts)
                System.out.println(chart);
        }
        else System.out.println("ERROR: Nu s-a gasit raspunsul cerut!");
        System.out.println();
    }
}
