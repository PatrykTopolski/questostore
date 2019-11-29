package dao;

import model.items.Quest;
import java.util.List;

public interface IQuestDAO {
    List<Quest> getQuestsList() throws DBException;
    void createNewQuest(Quest quest) throws DBException;
    void updateQuestCategory(Quest quest) throws DBException;
    void updateQuest(String questName, int newValue) throws DBException;
    void markAchievedQuests(int questId, int userId) throws DBException;
    Quest getQuest(int id) throws DBException;
    List<Quest> getUsersQuests(int id) throws DBException;
}
