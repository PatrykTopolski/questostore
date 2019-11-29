package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


public class WalletDAO implements IWalletDAO {
    private DBCreator dbCreator;

    public WalletDAO() {
        dbCreator = new DBCreator();
    }

    public int showWallet(int id) throws DBException {
        try {
            DBCreator dbCreator = new DBCreator();
            Connection connection = dbCreator.connectToDatabase();
            PreparedStatement stm = connection.prepareStatement("select coolcoins from studentpersonals where user_id = ? ");
            stm.setInt(1, id);
            ResultSet result = stm.executeQuery();

            if (result.next()) {
                return result.getInt("coolcoins");
            }
            throw new DBException("Didn't find student with id: " + id);

        } catch (SQLException e) {
            throw new DBException("SQLException occurred in showWallet()");
        } catch (Exception e) {
            throw new DBException("Unidentified exception occurred in showWallet()");
        }
    }

    public Map<Integer, Integer> seeStudentsWallets() throws DBException {
        try {
            DBCreator dbCreator = new DBCreator();
            Connection connection = dbCreator.connectToDatabase();
            PreparedStatement stm = connection.prepareStatement("select user_id, experience_points, coolcoins from studentpersonals ");
            ResultSet result = stm.executeQuery();
            Map<Integer, Integer> resultMap = new HashMap<Integer, Integer>();

            if (result.next()) {
                int studentID = result.getInt("user_id");
                int studentExperiencePoints = result.getInt("experience_points");
                int studentCoins = result.getInt("coolcoins");
                int balance = studentExperiencePoints - studentCoins;
                resultMap.put(studentID, balance);
                return resultMap;
            }
            throw new DBException("No students found");
        } catch (SQLException e) {
            throw new DBException("SQLException occurred in seeStudentsWallet()");
        } catch (Exception e) {
            throw new DBException("Unidentified exception occurred in seeStudentsWallet()");
        }
    }


    public void buyArtifact(int userID, int artifactID) throws DBException {
        boolean checkIfCanBeBought = checkIfTransactionPossible(userID, artifactID);
        if (checkIfCanBeBought) {
            updateMoneyAmount(userID, artifactID);
            addArtifactToItems(userID, artifactID);
        }
    }


    private void updateMoneyAmount(int userID, int artifactID) throws DBException {
        try {
            DBCreator dbCreator = new DBCreator();
            Connection connection = dbCreator.connectToDatabase();
            int coolcoins = showWallet(userID);
            int artifactCost = getArtifactCost(artifactID);
            int restCoins = coolcoins - artifactCost;
            PreparedStatement stm = connection.prepareStatement("update  studentpersonals set coolcoins = ? where user_id = ?");
            stm.setInt(1, restCoins);
            stm.setInt(2, userID);
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("SQLException occurred in updateMoneyAmount()");
        } catch (Exception e) {
            throw new DBException("Unidentified exception updateMoneyAmount()");
        }
    }

    private void addArtifactToItems(int userID, int artifactID) throws DBException {
        try {
            DBCreator dbCreator = new DBCreator();
            Connection connection = dbCreator.connectToDatabase();
            PreparedStatement stm = connection.prepareStatement("insert into  users_artifacts (user_id, artifact_id ) values  (?,?)");
            stm.setInt(1, userID);
            stm.setInt(2, artifactID);
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("SQLException occurred in addArtifactToItems()");
        } catch (Exception e) {
            throw new DBException("Unidentified exception occurred in addArtifactToItems()");
        }
    }

    private boolean checkIfTransactionPossible(int userID, int artifactID) throws DBException {
        int coolcoins = showWallet(userID);
        int artifactCost = getArtifactCost(artifactID);
        return coolcoins >= artifactCost;
    }

    private int getArtifactCost(int artifactID) throws DBException {
        try {
            DBCreator dbCreator = new DBCreator();
            Connection connection = dbCreator.connectToDatabase();
            PreparedStatement stm = connection.prepareStatement("select artifact_price from artifacts where  id = ? ");
            stm.setInt(1, artifactID);
            ResultSet result = stm.executeQuery();
            int artifactCost = 0;
            if (result.next()) {
                artifactCost = result.getInt("artifact_price");
            }
            connection.close();
            return artifactCost;
        } catch (SQLException e) {
            throw new DBException("SQLException occurred in getArtifactCost()");
        } catch (Exception e) {
            throw new DBException("Unidentified exception occurred in getArtifactCost()");
        }
    }
}
