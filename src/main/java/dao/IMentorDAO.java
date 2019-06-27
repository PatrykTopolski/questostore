package dao;


import model.items.Artifact;
import model.items.Quest;
import model.users.Codecooler;

import java.sql.SQLException;
import java.util.Map;

public interface IMentorDAO {

    Codecooler createCodecooler();
    Quest createNewQuest();
    void addQuestToAvailable();
    void addQuestCategory();
    Artifact createArtifact();
    void updateQuest();
    void updateArtifact();
    void addArtifactCategory();
    void markAchivedQuests();
    void markBoughtArtifacts();
    int seeStudentWallet(int id) throws SQLException;
    Map<Integer, Integer> seeStudentsWallet(int id) throws SQLException;

}
