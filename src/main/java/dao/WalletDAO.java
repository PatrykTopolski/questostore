package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


public class WalletDAO implements IWalletDAO {
    //    this class contains methods to process wallet and purchase artifacts

    //TODO Get DBCreator object to private filed of WallDao class instead of creating it in every method
    private DBCreator dbCreator;

    WalletDAO() {
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
            throw new DBException("Didn't find codecooler with id: " + id);

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
                int codecoolerID = result.getInt("user_id");
                int codecoolerExperiencePoints = result.getInt("experience_points");
                int codecoolerCoins = result.getInt("coolcoins");
                int balance = codecoolerExperiencePoints - codecoolerCoins;
                resultMap.put(codecoolerID, balance);
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


    //todo
    public void buyArtifactWithTeammates() {
    }

    private void updateMoneyAmount(int userID, int artifactID) throws DBException {
        try {
            DBCreator dbCreator = new DBCreator();
            Connection connection = dbCreator.connectToDatabase();

            //REFACTOR: getCoolCoins(user id) is the same as showWallet(use id)
            //old code:
            //int coolcoins = getCoolCoins(userID);
            //new code:
            int coolcoins = showWallet(userID);
            //refactor end

            int artifactCost = getArtifactCost(artifactID);
            int restCoins = coolcoins - artifactCost;
            System.out.println(restCoins);
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

    //todo
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
        /*refactor:
        old code
        int coolcoins = getCoolCoins(userID);
        new code*/
        int coolcoins = showWallet(userID);
        int artifactCost = getArtifactCost(artifactID);
        System.out.println(coolcoins >= artifactCost);
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
            System.out.println("artifact cost" + artifactCost);
            connection.close();
            return artifactCost;

        } catch (SQLException e) {
            throw new DBException("SQLException occurred in getArtifactCost()");
        } catch (Exception e) {
            throw new DBException("Unidentified exception occurred in getArtifactCost()");
        }
    }

}
