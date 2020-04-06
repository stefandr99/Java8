package dao;

import actors.Album;

import java.sql.SQLException;
import java.util.List;

public interface AlbumDao {
    boolean create(String name, int artistId, int releaseYear) throws SQLException;
    List<Album> findByArtist(int artistId) throws SQLException;
}
