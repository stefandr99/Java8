package dao;

import actors.Album;
import database.Database;

import java.sql.SQLException;
import java.util.List;

public interface AlbumDao {
    boolean create(String name, int artistId, int releaseYear, Database db) throws SQLException;
    List<Album> findByArtist(int artistId, Database db) throws SQLException;
}
