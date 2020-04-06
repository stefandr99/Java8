package controllers;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        ArtistController artist = new ArtistController();
        AlbumController album = new AlbumController();

        /* ARTISTI CREATI*/
        //artist.create("Stefan Dragoi", "Romania"); // id = 1
        //artist.create("Freddie Mercury", "Marea Britanie"); // id = 2
        //artist.create("Michael Jackson", "SUA"); // id = 3

        /* DE CREAT */
        //artist.create("Smiley", "Romania");
        //artist.create("Eminem", "SUA");


        artist.findByName("Stefan Dragoi");
        artist.findByName("Freddie Mercury");
        artist.findByName("Michael Jackson");
        artist.findByName("Bla Bla Bla"); // Artist FALS
        artist.findByName("Smiley");
        artist.findByName("Eminem");

        /*ALBUME CREATE*/
        //album.create("Computer Science Mystery", 1, 2020); // Stefan Dragoi
        //album.create("A Night At The Opera", 2, 1975); //Freddie Mercury
        //album.create("Queen II", 2, 1974); //Freddie Mercury

        /*DE CREAT*/
        //album.create("Recovery", 5, 2010); //Eminem
        //album.create("Acasa", 4, 2013); //Smiley

        album.findByArtist(1);
        album.findByArtist(2);
        album.findByArtist(100); // Album FALS
        /* DE AFISAT */
        album.findByArtist(4); //Eminem
        album.findByArtist(5); //Smiley
    }
}
