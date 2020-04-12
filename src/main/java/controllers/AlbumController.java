package controllers;

import actors.Album;
import dao.AlbumDaoImp;
import database.Database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlbumController {
    private AlbumDaoImp query = new AlbumDaoImp();

    /**
     * Controller-ul va afisa rezultatul inserarii unui album in baza de date.
     * @param name numele albumului care va fi inserat in bd
     * @param artistId id-ul artistului care a lansat albumul
     * @param releaseYear anul in care a fost lansat albumul
     * @throws SQLException
     */
    public void create(String name, int artistId, int releaseYear, Database db) throws SQLException {
        if(query.create(name, artistId, releaseYear, db)) {
            System.out.println("Ati introdus un nou album!");
        } else {
            System.out.println("Ups! A intervenit o problema. Va rugam reveniti!");
        }
    }

    /**
     * Controller-ul va afisa albumele artistului indicat ca argument
     * @param artistId id-ul artistului al carui albume se cere a fi afisate
     * @throws SQLException
     */
    public void findByArtist(int artistId, Database db) throws SQLException {
        List<Album> albums;
        albums = query.findByArtist(artistId, db);
        if(albums != null) {
            for (Album album : albums)
                System.out.println(album);
        }
        else System.out.println("ERROR: Nu s-a gasit albumul cerut!");
    }
}
