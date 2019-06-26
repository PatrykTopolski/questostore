package dao;


import model.items.Artifact;
import model.items.Quest;
import model.users.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface IUserDAO {


    ArrayList<Artifact> seeArtifactsList();
    List<Quest> seeQuestsList() throws SQLException;
    User seeProfile();
    void updateMyProfile();

}
