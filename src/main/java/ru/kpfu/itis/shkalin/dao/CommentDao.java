package ru.kpfu.itis.shkalin.dao;

import ru.kpfu.itis.shkalin.entity.Comment;

import java.sql.Connection;
import java.util.List;

public class CommentDao implements Dao<Comment> {

    private Connection connection;

    public CommentDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Comment comment) {

    }

    @Override
    public Comment get(int id) {
        return null;
    }

    @Override
    public Comment get(String name) {
        return null;
    }

    @Override
    public List<Comment> getAll() {
        return null;
    }

    @Override
    public void update(Comment comment) {

    }

    @Override
    public void delete(int id) {

    }
}
