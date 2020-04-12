package controllers;

import actors.Artist;
import dao.ArtistDaoImp;
import database.Database;

import java.sql.*;

public class ArtistController {
    private ArtistDaoImp query = new ArtistDaoImp();

    /**
     * Controller-ul va afisa rezultatul inserarii unui artist in baza de date.
     *  Mesaj de succes sau mesaj de eroare
     * @param name numele artistului care trebuie inserat
     * @param country tara din care provine acesta
     * @throws SQLException
     */
    public void create(String name, String country, Database db) throws SQLException {
        if (query.create(name, country, db)) {
            System.out.println("Ati introdus un nou artist!");
        } else {
            System.out.println("Ups! A intervenit o problema. Va rugam reveniti!");
        }
    }

    /**
     * Controller-ul va afisa artistul cerut din baza de date
     * @param name numele artistului care este cerut din baza de date
     * @throws SQLException
     */
    public void findByName(String name, Database db) throws SQLException {
        Artist artist;
        artist = query.findByName(name, db);
        if(artist != null)
            System.out.println(artist);
        else System.out.println("ERROR: Nu s-a gasit niciun artist cu acest nume!");
    }
}