package com.Model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

@Repository
public class JdbcCatRepository {

    private static ObjectMapper objectMapper = new ObjectMapper();

    private final JdbcTemplate jdbc;
    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    public JdbcCatRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
        this.jdbcInsert = new SimpleJdbcInsert(jdbc).withTableName("cat").usingGeneratedKeyColumns("id");
    }

    public Iterable<Cat> findAllCats() {
        return jdbc.query("select * from cat",
                JdbcCatRepository::mapRowToCat);
    }

    public Cat findCatById(int id) {
        return jdbc.queryForObject("select * from cat where id=?", JdbcCatRepository::mapRowToCat, id);
    }

    // save cat to db, and get cat by primary key value
    public Cat saveCat(Cat cat) {
        Map<String, Object> values = objectMapper.convertValue(cat, Map.class);
        int id = jdbcInsert.executeAndReturnKey(values).intValue();
        return findCatById(id);

        // too long, simply by SimpleJdbcInsert
//        return jdbc.query("insert into cat (name, gender, longHaired, roundFace, lively, picPath, thPicPath" +
//                ") values (?,?,?,?,?,?,?)",
//                cat.getName(), cat.getGender(), cat.isLongHaired(), cat.isRoundFace(), cat.isLively(), cat.getPicPath(), cat.getThPicPath());
    }

    public static Cat mapRowToCat(ResultSet rs, int rowNum) throws SQLException {

        return new Cat(rs.getInt("id"),
                rs.getString("name"),
                rs.getString("gender"),
                rs.getBoolean("longHaired"),
                rs.getBoolean("roundFace"),
                rs.getBoolean("lively"),
                rs.getString("picPath"),
                rs.getString("thPicPath")
        );
    }
}
