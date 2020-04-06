package dao;

import actors.Artist;
import database.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class ArtistDaoImp implements ArtistDao {

    /**
     * Functie ce va introduce artisti in tabelul "artists" din baza de date.
     * Stabilesc conexiunea la baza de date, creez interogarea, pregatesc statementul si completez cele doua
     *  valori ce trebuie completate in query-ul sql (name, respectiv country)
     * @param name numele artistului
     * @param country tara din care provine artistul
     * @return true - daca s-a adaugat cu succes artistul
     *         false - altfel
     * @throws SQLException
     */
    public boolean create(String name, String country) throws SQLException {
        Database db = Database.getConnection();
        String sql = "insert into artists (name, country) values (?, ?)";
        PreparedStatement pstmt = db.conn.prepareStatement(sql);
        pstmt.setString(1, name);
        pstmt.setString(2, country);
        if(pstmt.executeUpdate() == 1)
            return true;
        return false;
    }

    /**
     * Cautarea unui artist dupa nume.
     * Aceiasi pasi ca mai sus.
     * Daca -> am gasit un artist in baza de date cu numele cautat, vom crea un obiect Artist nou, vom asigna fiecarei
     *  variabile membru valoarea corespunzatoare
     * Daca nu am gasit, returnam null
     * @param name numele artistului care trebuie cautat
     * @return Obiect artist daca s-a gasit
     *         null altfel
     * @throws SQLException
     */
    public Artist findByName(String name) throws SQLException {
        Database db = Database.getConnection();
        String sql = "select * from artists where name = '" + name + "'";
        Statement stmt = db.conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        if(rs.next()) {
            Artist artist = new Artist();
            artist.setId( rs.getInt("id") );
            artist.setName( rs.getString("name") );
            artist.setCountry(rs.getString("country"));
            return artist;
        }
        return null;
    }
}
