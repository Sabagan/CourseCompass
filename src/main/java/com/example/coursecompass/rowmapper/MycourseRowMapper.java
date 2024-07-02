package com.example.coursecompass.rowmapper;

import com.example.coursecompass.model.Mycourse;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MycourseRowMapper implements RowMapper<Mycourse> {
    @Override
    public Mycourse mapRow(ResultSet resultSet, int i) throws SQLException {
        Mycourse mycourse = new Mycourse();
        mycourse.setUserId(resultSet.getLong("user_id"));
        mycourse.setCourseProgram(resultSet.getString("course_program"));
        mycourse.setCourseCode(resultSet.getString("course_code"));
        mycourse.setCourseName(resultSet.getString("course_name"));
        mycourse.setCourseDescription(resultSet.getString("course_description"));
        return mycourse;
    }
}
