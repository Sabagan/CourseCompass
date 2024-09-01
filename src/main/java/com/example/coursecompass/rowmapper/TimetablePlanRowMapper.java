package com.example.coursecompass.rowmapper;


import com.example.coursecompass.model.TimetableCourse;
import com.example.coursecompass.model.TimetablePlan;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TimetablePlanRowMapper implements RowMapper<TimetablePlan> {
    @Override
    public TimetablePlan mapRow(ResultSet rs, int rowNum) throws SQLException {
        TimetablePlan timetablePlan = new TimetablePlan();
        timetablePlan.setUserId(rs.getLong("user_id"));
        timetablePlan.setTimetableId(rs.getInt("timetable_id"));
        timetablePlan.setYears(rs.getInt("years"));
        return timetablePlan;
    }
}
