package com.example.coursecompass.rowmapper;

import com.example.coursecompass.model.Timetable;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TimetableRowMapper implements RowMapper<Timetable> {
    @Override
    public Timetable mapRow(ResultSet rs, int rowNum) throws SQLException {
        Timetable timetable = new Timetable();
        timetable.setUserId(rs.getLong("user_id"));
        timetable.setYear(rs.getInt("year"));
        timetable.setSemester(rs.getString("semester"));
        timetable.setCourseName(rs.getString("course_name"));
        return timetable;
    }
}