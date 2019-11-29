package dao;

import java.sql.SQLException;
import java.util.Map;

public interface IWalletDAO {
    int showWallet(int id) throws DBException;
    void buyArtifact(int userID, int artifactID) throws DBException;
    Map<Integer, Integer> seeStudentsWallets() throws DBException;
}
