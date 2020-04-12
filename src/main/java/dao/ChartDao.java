package dao;

import actors.Album;
import actors.Chart;
import database.Database;

import java.sql.SQLException;
import java.util.List;

public interface ChartDao {
    boolean create(int albumId, String criteria, int rank, Database db) throws SQLException;
    List<Chart> findByCriteria(String criteria, String country, Database db) throws SQLException;
}
