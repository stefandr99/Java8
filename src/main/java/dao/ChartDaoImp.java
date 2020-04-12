package dao;

import actors.Album;
import actors.Chart;
import com.sun.org.apache.xpath.internal.objects.XNull;
import database.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ChartDaoImp implements ChartDao {

    /**
     * Functie ce va adauga in chart un album care va fi clasificat dupa un anumit criteriu
     * @param albumId - id-ul albumului care se introduce in tabelul charts
     * @param criteria - criteriul dupa care va fi comparat acest album
     *                 Cele 3 criterii dupa care se face clasementul sunt:
     *                  -> Popularitatea artistului
     *                  -> Numarul de vanzari ale fiecarui album
     *                  -> Popularitatea albumului intr-o anumita tara (avem doar tarile Romania, USA si UK)
     * @param rank - rank-ul pe care il are albumul in clasament (clasament dupa criteriul primit ca parametru)
     * @param db (conexiunea la bd)
     * @return true - daca s-a executat corect, false altfel
     * @throws SQLException
     */
    public boolean create(int albumId, String criteria, int rank, Database db) throws SQLException {
        String sql = "insert into charts (album_id, criteria, rank) values (?, ?, ?)";
        PreparedStatement pstmt = db.conn.prepareStatement(sql);
        pstmt.setInt(1, albumId);
        pstmt.setString(2, criteria);
        pstmt.setInt(3, rank);
        return pstmt.executeUpdate() == 1;
    }

    /**
     * Aici se returneaza clasamentul albumelor dupa criteriul trimis ca parametru
     * Se fac interograri diferite in functie de ce clasament dorim sa obtinem
     * Se completeaza lista de chart-uri cu rezultatele obtinute in urma interogarii
     * @param criteria - criteriul dupa care se doreste clasamentul (artist, album, tara)
     * @param country - DACA ESTE CAZUL - tara dupa care se face clasamentul
     * @param db - conexiunea la bd
     * @return - lista reprezentand clasamentul
     * @throws SQLException
     */
    public List<Chart> findByCriteria(String criteria, String country, Database db) throws SQLException {
        String sql;
        if(criteria.compareTo("country") == 0) { // rank dupa tara artistului
            sql = "select c.rank as rank, al.name as al_name, al.release_year as release, ar.name as ar_name, ar.country as country " +
                    "from artists ar join albums al on ar.id = al.artist_id " +
                    "join charts c on c.album_id = al.id " +
                    "where c.criteria = 'country' and ar.country = '" + country +
                    "' order by c.rank asc";
        }
        else if(criteria.compareTo("album") == 0) { // rank dupa numarul de vanzari ale albumului
            sql = "select c.rank as rank, al.name as al_name, al.release_year as release, ar.name as ar_name, ar.country as country " +
                    "from artists ar join albums al on ar.id = al.artist_id " +
                    "join charts c on c.album_id = al.id " +
                    "where c.criteria = 'album' "+
                    "order by c.rank asc";
        }
        else { // dupa popularitatea artistului
            sql = "select c.rank as rank, al.name as al_name, al.release_year as release, ar.name as ar_name, ar.country as country " +
                    "from artists ar join albums al on ar.id = al.artist_id " +
                    "join charts c on c.album_id = al.id " +
                    "where c.criteria = 'artist' "+
                    "order by c.rank asc";
        }
        Statement stmt = db.conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        List<Chart> charts = new ArrayList<>();
        int i = 1;
        while (rs.next()) {
            Chart chart = new Chart();
            chart.setIdAlbum(i);
            chart.setCriteria(rs.getString("country"));
            chart.setRank(rs.getInt("rank"));
            chart.setReleaseYear(rs.getInt("release"));
            chart.setAlbumName(rs.getString("al_name"));
            chart.setArtistName(rs.getString("ar_name"));
            chart.setCountry(rs.getString("country"));
            charts.add(chart);
            i++;
        }
        if(!charts.isEmpty())
            return charts;
        return null;
    }
}
