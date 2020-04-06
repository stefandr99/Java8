package dao;

import actors.Artist;

import java.sql.SQLException;

public interface ArtistDao {
    boolean create(String name, String country) throws SQLException;
    Artist findByName(String name) throws SQLException;
}
