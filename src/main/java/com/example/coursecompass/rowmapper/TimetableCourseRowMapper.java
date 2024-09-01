package com.example.coursecompass.rowmapper;

import com.example.coursecompass.model.TimetableCourse;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TimetableCourseRowMapper implements RowMapper<TimetableCourse> {
    @Override
    public TimetableCourse mapRow(ResultSet rs, int rowNum) throws SQLException {
        TimetableCourse timetableCourse = new TimetableCourse();
        timetableCourse.setUserId(rs.getLong("user_id"));
        timetableCourse.setTimetableId(rs.getInt("timetable_id"));
        timetableCourse.setYear(rs.getInt("year"));
        timetableCourse.setSemester(rs.getString("semester"));
        timetableCourse.setCourseName(rs.getString("course_name"));
        return timetableCourse;
    }
}