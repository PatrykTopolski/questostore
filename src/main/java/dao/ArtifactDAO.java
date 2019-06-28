package dao;

import model.items.Artifact;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArtifactDAO {
    //this class contains methods to process artifacts(show, create, update)

    Connection connection;
    DBCreator dbCreator;

    ArtifactDAO(){
        dbCreator = new DBCreator();
    }

    public List<Artifact> showAllArtifacts() throws SQLException{
        List<Artifact> allArtifacts = new ArrayList();
        Connection con = dbCreator.connectToDatabase();
        Statement stmt = null;
        ResultSet resultSet = null;

        try {
            con.setAutoCommit(false);
            stmt = con.createStatement();
            resultSet = stmt.executeQuery( "SELECT * FROM artifacts;" );
            while (resultSet.next() ) {
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
        } catch ( Exception e ) {
            System.out.println(e);
        }
        System.out.println("Operation done successfully");
        System.out.println("all artifact size: " + allArtifacts.size());
        return allArtifacts;
    }


    //todo
    public void addArtifactCategory() {

    }


    public void createArtifact(Artifact artifact) {
        String query = "INSERT INTO Artifacts (artifact_name, artifact_category, artifact_description, artifact_price, artifact_availability)" +
                " VALUES (?,?,?,?,?)";
        PreparedStatement statement = null;
        try {
            connection = dbCreator.connectToDatabase();
            statement = connection.prepareStatement(query);

            statement.setString(1, artifact.getName());
            statement.setString(2, artifact.getCategory());
            statement.setString(3, artifact.getDiscription());
            statement.setInt(4, artifact.getPrice());
            statement.setBoolean(5, artifact.isAvaliability());

            statement.executeUpdate();
            connection.close();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void updateArtifact(String artifactName, int newPrice) {
        DBCreator dbCreator = new DBCreator();
        try {
            Connection connection = dbCreator.connectToDatabase();
            PreparedStatement stm = connection.prepareStatement("update  Artifacts set artifact_price = ? where artifact_name like ?");
            stm.setInt(1, newPrice);
            stm.setString(2, artifactName);
            stm.executeUpdate();
        }catch (SQLException e){
            System.out.println(e);
        }
    }

    //todo
    public void markBoughtArtifacts() {

    }

    public List<Artifact> showBoughtArtifacts(int userId) throws SQLException{
        DBCreator dbCreator = new DBCreator();
        Connection con = dbCreator.connectToDatabase();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Artifact> boughtArtifacts = new ArrayList();

        try {
            stmt = con.prepareStatement("SELECT art.id, artifact_name, artifact_category, artifact_description, artifact_price\n" +
                    "FROM users_artifacts usersArt INNER JOIN artifacts art\n" +
                    "ON usersArt.artifact_id = art.id\n" +
                    "WHERE user_id = ?;");
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();

            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("artifact_name");
                String discription = rs.getString("artifact_description");
                String category = rs.getString("artifact_category");
                int price = rs.getInt("artifact_price");
                Artifact nextArtifact = new Artifact(id, name, category, price, discription);

                boughtArtifacts.add(nextArtifact);
            }
            stmt.close();
            con.close();
        } catch ( Exception e ) {
            System.out.println(e);
        }
        return boughtArtifacts;
    }
}