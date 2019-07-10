package dao;

import model.items.Quest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestDAO implements IQuestDAO {
    //this class contains methods to process quests: show, create, update

    private Connection connection;
    private DBCreator dbCreator;

    public QuestDAO() {
        dbCreator = new DBCreator();
    }

    public List<Quest> getQuestsList() throws DBException {
        List<Quest> allQuests = new ArrayList<Quest>();
        try {
            Connection con = dbCreator.connectToDatabase();
            con.setAutoCommit(false);
            Statement stmt = con.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM quests;");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String questName = resultSet.getString("quest_name");
                String questCategory = resultSet.getString("quest_category");
                String questDescription = resultSet.getString("quest_description");
                int questAward = resultSet.getInt("quest_award");
                Quest newQuest = new Quest(id, questName, questDescription, questCategory, questAward);
                allQuests.add(newQuest);
            }
            resultSet.close();
            stmt.close();
            con.close();

            System.out.println("Operation done successfully");
            System.out.println("all quests size: " + allQuests.size());
            return allQuests;

        } catch (SQLException e) {
            throw new DBException("SQLException occurred in getQuestsList()");
        } catch (Exception e) {
            throw new DBException("Unidentified exception occurred in getQuestsList()");
        }
    }


    public void createNewQuest(Quest quest) throws DBException {
        String query = "INSERT INTO quests(quest_award, quest_category, quest_description, quest_name) VALUES (?,?,?,?)";
        try {
            connection = dbCreator.connectToDatabase();
            PreparedStatement statement = connection.prepareStatement(query);


            statement.setInt(1, quest.getReward());
            statement.setString(2, quest.getCategory());
            statement.setString(3, quest.getDescription());
            statement.setString(4, quest.getName());

            statement.executeUpdate();
            connection.close();

        } catch (SQLException e) {
            throw new DBException("SQLException occurred in createNewQuest(Quest quest)");
        } catch (Exception e) {
            throw new DBException("Unidentified exception occurred in createNewQuest(Quest quest)");
        }
    }

    //todo
    public void addQuestToAvailable() {

    }

    public void updateQuestCategory(Quest quest) throws DBException {
        // Update based on quest id
        String query = "UPDATE quests SET quest_category = ? WHERE id = ?";
        try {
            connection = dbCreator.connectToDatabase();
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, quest.getCategory());
            statement.setInt(2, quest.getId());

            statement.executeUpdate();
            connection.close();

        } catch (SQLException e) {
            throw new DBException("SQLException occurred in updateQuestCategory(Quest quest)");
        } catch (Exception e) {
            throw new DBException("Unidentified exception occurred in updateQuestCategory(Quest quest)");
        }

    }


    public void updateQuest(String questName, int newValue) throws DBException {
        DBCreator dbCreator = new DBCreator();
        try {
            Connection connection = dbCreator.connectToDatabase();
            PreparedStatement stm = connection.prepareStatement("update  Quests set quest_award = ? where quest_name like ?");
            stm.setInt(1, newValue);
            stm.setString(2, questName);
            stm.executeUpdate();

        } catch (SQLException e) {
            throw new DBException("SQLException occurred in updateQuest()");
        } catch (Exception e) {
            throw new DBException("Unidentified exception occurred in updateQuest()");
        }
    }


    //todo
    public void markAchievedQuests() {

    }

}
