package model.dao;

import java.util.ArrayList;
import java.util.List;
import model.ModelException;
import model.entities.Post;

class MySQLPostDAO implements PostDAO {

    @Override
    public boolean save(Post post) throws ModelException {
        String sqlInsert = "INSERT INTO posts VALUES(DEFAULT, ?, ?, ?);";

        DataBaseHandler dbHandler = new DataBaseHandler();
        dbHandler.prepareStatement(sqlInsert);

        dbHandler.setString(1, post.getContent());
        dbHandler.setString(2, post.getDate().toString());
        dbHandler.setInt(3, post.getUser_id());

        int rowsAffected = dbHandler.executeUpdate();

        dbHandler.close();

        return rowsAffected > 0;
    }

    @Override
    public boolean update(Post post) throws ModelException {
        String sqlUpdate = "UPDATE posts SET content = ?, post_date = ?, user_id = ? WHERE id = ?;";

        DataBaseHandler dbHandler = new DataBaseHandler();
        dbHandler.prepareStatement(sqlUpdate);

        dbHandler.setString(1, post.getContent());
        dbHandler.setString(2, post.getDate().toString());
        dbHandler.setInt(3, post.getUser_id());
        dbHandler.setInt(4, post.getId());

        int rowsAffected = dbHandler.executeUpdate();

        dbHandler.close();

        return rowsAffected > 0;
    }

    @Override
    public boolean delete(Post post) throws ModelException {
        String sqlDelete = "DELETE FROM posts WHERE id = ?;";

        DataBaseHandler dbHandler = new DataBaseHandler();
        dbHandler.prepareStatement(sqlDelete);

        dbHandler.setInt(1, post.getId());

        int rowsAffected = dbHandler.executeUpdate();

        dbHandler.close();

        return rowsAffected > 0;
    }

    @Override
    public List<Post> listAll() throws ModelException {
        List<Post> posts = new ArrayList<>();

        String sqlQuery = "SELECT * FROM posts";

        DataBaseHandler dbHandler = new DataBaseHandler();
        dbHandler.statement();

        dbHandler.executeQuery(sqlQuery);

        while (dbHandler.next()) {
            int postId = dbHandler.getInt("id");
            String postContent = dbHandler.getString("content");
            String postDate = dbHandler.getString("post_date");
            int postUserId = dbHandler.getInt("user_id");

            Post post = new Post(postId);
            post.setContent(postContent);
            post.setDate(java.sql.Date.valueOf(postDate));
            post.setUser_id(postUserId);

            posts.add(post);
        }

        dbHandler.close();

        return posts;
    }

    @Override
    public Post findById(int id) throws ModelException {
        String sql = "SELECT * FROM posts WHERE id = ?;";

        DataBaseHandler dbHandler = new DataBaseHandler();
        dbHandler.prepareStatement(sql);
        dbHandler.setInt(1, id);

        dbHandler.executeQuery();

        Post post = null;
        while (dbHandler.next()) {
            int postId = dbHandler.getInt("id");
            String postContent = dbHandler.getString("content");
            String postDate = dbHandler.getString("post_date");
            int postUserId = dbHandler.getInt("user_id");

            post = new Post(postId);
            post.setContent(postContent);
            post.setDate(java.sql.Date.valueOf(postDate));
            post.setUser_id(postUserId);
            break;
        }

        dbHandler.close();

        return post;
    }
}
