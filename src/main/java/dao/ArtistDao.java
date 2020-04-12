package dao;

import actors.Artist;
import database.Database;

import java.sql.SQLException;

public interface ArtistDao {
    boolean create(String name, String country, Database db) throws SQLException;
    Artist findByName(String name, Database db) throws SQLException;
}
