package dao;

import model.items.Artifact;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArtifactDAO implements IArtifactDAO {
    //this class contains methods to process artifacts(show, create, update)

    private Connection connection;
    private DBCreator dbCreator;

    ArtifactDAO() {
        dbCreator = new DBCreator();
    }

    public List<Artifact> getArtifactsList() throws DBException {
        try {
            List<Artifact> allArtifacts = new ArrayList();
            Connection con = dbCreator.connectToDatabase();

            con.setAutoCommit(false);
            Statement stmt = con.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM artifacts;");
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
            System.out.println("Operation done successfully");
            System.out.println("all artifact size: " + allArtifacts.size());
            return allArtifacts;

        } catch (SQLException e) {
            throw new DBException("SQLException occurred in getStudents(int roomId))");

        } catch (Exception e) {
            throw new DBException("Unidentified exception occurred in getStudents(int roomId)");
        }

    }


    //todo
    public void addArtifactCategory() {

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

    public void updateArtifact(String artifactName, int newPrice) throws DBException {
        DBCreator dbCreator = new DBCreator();
        try {
            Connection connection = dbCreator.connectToDatabase();
            PreparedStatement stm = connection.prepareStatement("update  Artifacts set artifact_price = ? where artifact_name like ?");
            stm.setInt(1, newPrice);
            stm.setString(2, artifactName);
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("SQLException occurred in updateArtifact()");

        } catch (Exception e) {
            throw new DBException("Unidentified exception occurred in updateArtifact()");
        }
    }

    //todo
    public void markBoughtArtifacts() {

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
