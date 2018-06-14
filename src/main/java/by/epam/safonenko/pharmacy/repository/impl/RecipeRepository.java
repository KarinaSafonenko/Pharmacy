package by.epam.safonenko.pharmacy.repository.impl;

import by.epam.safonenko.pharmacy.connection.ConnectionPool;
import by.epam.safonenko.pharmacy.connection.ProxyConnection;
import by.epam.safonenko.pharmacy.entity.Recipe;
import by.epam.safonenko.pharmacy.exception.RepositoryException;
import by.epam.safonenko.pharmacy.repository.Repository;
import by.epam.safonenko.pharmacy.repository.util.UpdateByClientAndPackId;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RecipeRepository implements Repository<Recipe>, UpdateByClientAndPackId {
    private static final String INSERT_RECIPE = "INSERT INTO pharmacy.recipe (client, pack_id) values(?, ?)";
    private static final String DELETE_RECIPE = "DELETE FROM pharmacy.recipe WHERE client = ? AND pack_id = ?";

    public void add(String client, int packId) throws RepositoryException {
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        try(PreparedStatement current = connection.prepareStatement(INSERT_RECIPE)) {
            executeUpdate(current, client, packId);
        } catch (SQLException e) {
            throw new RepositoryException(e);
        } finally {
            releaseConnection(connection);
        }
    }

    public void delete(String client, int packId) throws RepositoryException {
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        try(PreparedStatement current = connection.prepareStatement(DELETE_RECIPE)) {
            executeUpdate(current, client, packId);
        } catch (SQLException e) {
            throw new RepositoryException(e);
        } finally {
            releaseConnection(connection);
        }
    }


}
