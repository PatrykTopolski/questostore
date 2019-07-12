package dao;

import java.sql.SQLException;
import java.util.Map;

public interface IWalletDAO {
//    this interface contains methods to see, process wallet and purchase artifacts

    int showWallet(int id) throws DBException;
    void buyArtifact(int userID, int artifactID) throws DBException;
    void buyArtifactWithTeammates();
    Map<Integer, Integer> seeStudentsWallets() throws DBException;


}
