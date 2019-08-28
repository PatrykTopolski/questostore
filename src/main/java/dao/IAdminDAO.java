package dao;


import model.items.Level;

import java.sql.SQLException;
import java.util.List;

public interface IAdminDAO{

    void createNewClass();
    List<Level> getLevelList() throws SQLException;
    void addLevel(String name, int maxValue) throws DBException;
}