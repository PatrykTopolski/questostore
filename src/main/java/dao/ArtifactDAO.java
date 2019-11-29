package dao;

import model.items.Artifact;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArtifactDAO implements IArtifactDAO {
    private Connection connection;
    private DBCreator dbCreator;

    public ArtifactDAO() {
        dbCreator = new DBCreator();
    }

    public List<Artifact> getArtifactsList() throws DBException {
        try {
            List<Artifact> allArtifacts = new ArrayList();
            Connection con = dbCreator.connectToDatabase();
            con.setAutoCommit(false);
            Statement stmt = con.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM artifacts ORDER BY id;");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("artifact_name");
                String category = resultSet.getString("artifact_category");
                String description = resultSet.getString("artifact_description");
                int price = resultSet.getInt("artifact_price");
                boolean availability = resultSet.getBoolean("artifact_availability");
                Artifact newArtifact = new Artifact(id, name, description, category, price, availability);
                allArtifacts.add(newArtifact);
            }

            resultSet.close();
            stmt.close();
            con.close();
            return allArtifacts;
        } catch (SQLException e) {
            throw new DBException("SQLException occurred in getStudents(int roomId))");
        } catch (Exception e) {
            throw new DBException("Unidentified exception occurred in getStudents(int roomId)");
        }
    }

    public void addUserArtifact(int userID, int artifactID) throws DBException {
        try {
            String query = "INSERT INTO users_artifacts (artifact_id, user_id) VALUES (?,?)";
            connection =  dbCreator.connectToDatabase();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, artifactID);
            statement.setInt(2, userID);
            statement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            throw new DBException("SQLException occurred in addUserArtifact");

        } catch (Exception e) {
            throw new DBException("Unidentified exception occurred in addUserArtifact");
        }
    }

    public Artifact getArtifact(int id) throws DBException {
        try {
            String query = "select * from artifacts where id = ?";
            connection = dbCreator.connectToDatabase();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            connection.close();

            if (result.next()) {
                id = result.getInt("id");
                String name = result.getString("artifact_name");
                String description = result.getString("artifact_description");
                String category = result.getString("artifact_category");
                int reward = result.getInt("artifact_price");
                Boolean availability = result.getBoolean("artifact_availability");
                return new Artifact(id, name, description, category, reward, availability);
            }
            throw new DBException("No quest found in DB with id = " + id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("SQLException occurred in getQuest(int id)");
        } catch (Exception e) {
            throw new DBException("Unidentified exception occurred in getQuest(int id)");
        }
    }

    public List<Artifact> getUsersArtifacts(int id) throws DBException {
        try {
            String query = "select * from  users_artifacts where user_id = ? ";
            connection = dbCreator.connectToDatabase();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            connection.close();
            List<Artifact> usersArtifacts = new ArrayList<>();

            while (result.next()) {
                id = result.getInt("artifact_id");
                usersArtifacts.add(getArtifact(id));
            }
            return usersArtifacts;
        } catch (SQLException e) {
            throw new DBException("SQLException occurred in getUsersQuests(int id)");

        } catch (Exception e) {
            e.printStackTrace();
            throw new DBException("Unidentified exception occurred in getUsersQuests(int id)");
        }
    }

    public void createArtifact(Artifact artifact) throws DBException {
        String query = "INSERT INTO Artifacts (artifact_name, artifact_category, artifact_description, artifact_price, artifact_availability)" +
                " VALUES (?,?,?,?,?)";
        try {
            connection = dbCreator.connectToDatabase();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, artifact.getName());
            statement.setString(2, artifact.getCategory());
            statement.setString(3, artifact.getDescription());
            statement.setInt(4, artifact.getPrice());
            statement.setBoolean(5, artifact.isAvailability());
            statement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            throw new DBException("SQLException occurred in createArtifact()");

        } catch (Exception e) {
            throw new DBException("Unidentified exception occurred in createArtifact()");
        }
    }

    public void updateArtifact(int artifactId, int newPrice) throws DBException {
        DBCreator dbCreator = new DBCreator();
        try {
            Connection connection = dbCreator.connectToDatabase();
            PreparedStatement stm = connection.prepareStatement("update  Artifacts set artifact_price = ? where id = ?");
            stm.setInt(1, newPrice);
            stm.setInt(2, artifactId);
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("SQLException occurred in updateArtifact()");

        } catch (Exception e) {
            throw new DBException("Unidentified exception occurred in updateArtifact()");
        }
    }


    public void markBoughtArtifacts(int studentId, int questId) throws DBException {
        DBCreator dbCreator = new DBCreator();
        try {
            Connection connection = dbCreator.connectToDatabase();
            PreparedStatement stm = connection.prepareStatement("INSERT INTO users_quests (user_id, quest_id) VALUES (?, ?)");
            stm.setInt(1, studentId);
            stm.setInt(2, questId);
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("SQLException occurred in updateArtifact()");

        } catch (Exception e) {
            throw new DBException("Unidentified exception occurred in updateArtifact()");
        }
    }

    public List<Artifact> getBoughtArtifactsList(int userId) throws DBException {
        DBCreator dbCreator = new DBCreator();
        List<Artifact> boughtArtifacts = new ArrayList();
        try {
            Connection con = dbCreator.connectToDatabase();

            PreparedStatement stmt = con.prepareStatement("SELECT art.id, artifact_name, artifact_category, artifact_description, artifact_price\n" +
                    "FROM users_artifacts usersArt INNER JOIN artifacts art\n" +
                    "ON usersArt.artifact_id = art.id\n" +
                    "WHERE user_id = ?;");
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("artifact_name");
                String description = rs.getString("artifact_description");
                String category = rs.getString("artifact_category");
                int price = rs.getInt("artifact_price");
                Artifact nextArtifact = new Artifact(id, name, category, price, description);
                boughtArtifacts.add(nextArtifact);
            }
            stmt.close();
            con.close();
            return boughtArtifacts;
        } catch (SQLException e) {
            throw new DBException("SQLException occurred in getBoughtArtifactsList()");
        } catch (Exception e) {
            throw new DBException("Unidentified exception occurred in getBoughtArtifactsList()");
        }
    }
}
