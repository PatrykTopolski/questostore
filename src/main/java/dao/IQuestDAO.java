package dao;

import model.items.Quest;

import java.sql.SQLException;
import java.util.List;

public interface IQuestDAO {
    //this interface contains methods to process quests: show, create, update

    List<Quest> getQuestsList() throws DBException;
    void createNewQuest(Quest quest) throws DBException;
    void addQuestToAvailable();
    void updateQuestCategory(Quest quest) throws DBException;
    void updateQuest(String questName, int newValue) throws DBException;
    void markAchievedQuests();
}
