package dao.administrator;

import dao.DaoException;
import model.Choice;

import java.util.ArrayList;

public interface ChoiceDao extends dao.ChoiceDao {

    void deleteChoice(Choice choice) throws DaoException;

    void addChoice(Choice choice) throws DaoException;

    void updateChoice(Choice choice) throws DaoException;

    void exchangeChoices(Choice choice1, Choice choice2) throws DaoException;

}
