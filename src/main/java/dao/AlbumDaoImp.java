package dao;

import actors.Album;
import actors.Artist;
import database.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AlbumDaoImp implements AlbumDao {
    /**
     * Aceeasi descriere ca si create-ul din calasa "ArtistDaoImp" cu mentiunea ca aici primim alti parametri, specifici
     *  unui album
     * @param name numele albumului
     * @param artistId id-ul artistului care a lansat albumul
     * @param releaseYear anul in care a fost lansat albumul
     * @return true - inserare cu succes
     *         false - altfel
     * @throws SQLException
     */
    public boolean create(String name, int artistId, int releaseYear) throws SQLException {
        Database db = Database.getConnection();
        String sql = "insert into albums (name, artist_id, release_year) values (?, ?, ?)";
        PreparedStatement pstmt = db.conn.prepareStatement(sql);
        pstmt.setString(1, name);
        pstmt.setInt(2, artistId);
        pstmt.setInt(3, releaseYear);
        if(pstmt.executeUpdate() == 1)
            return true;
        return false;
    }

    /**
     * Cautarea unui/unor albume dupa id-ul artistului caruia/carora apartin.
     * Se vor adauga in lista de albume toate albumele gasite si se va returna lista de albume, altfel se returneaza null
     * @param artistId
     * @return
     * @throws SQLException
     */
    public List<Album> findByArtist(int artistId) throws SQLException {
        Database db = Database.getConnection();
        String sql = "select * from albums where artist_id = " + artistId;
        Statement stmt = db.conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        List<Album> albums = new ArrayList<>();
        while (rs.next()) {
            Album album = new Album();
            album.setId(rs.getInt("id"));
            album.setName(rs.getString("name"));
            album.setArtistId(rs.getInt("artist_id"));
            album.setReleaseYear(rs.getInt("release_year"));
            albums.add(album);
        }
        if(!albums.isEmpty())
            return albums;
        return null;
    }
}
