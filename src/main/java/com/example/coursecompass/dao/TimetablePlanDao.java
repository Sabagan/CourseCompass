package com.example.coursecompass.dao;

import com.example.coursecompass.model.TimetablePlan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TimetablePlanDao {

    private final JdbcTemplate jdbcTemplate;

    public TimetablePlanDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addTimetable(TimetablePlan timetablePlan) {
        String sql = "INSERT into timetables (user_id, years) VALUES (?, ?)";
        jdbcTemplate.update(sql, timetablePlan.getUserId(), timetablePlan.getYears());
    }

    // This would involve changing the years
    public void updateTimetable(Long userId, Integer timetableId, Integer years) {
        String sql = "UPDATE timetables SET years = ? WHERE user_id = ? AND timetable_id = ?";
        jdbcTemplate.update(sql, years, userId, timetableId);
    }

    public void deleteTimetable(Long userId, Integer timetableId) {
        String sql = "DELETE FROM timetables WHERE user_id = ? AND timetable_id = ?";
        jdbcTemplate.update(sql, userId, timetableId);
    }

    public List<Integer> getTimetableIdsByUser(Long userId) {
        String sql = "SELECT timetable_id FROM timetables WHERE user_id = ?";
        List<Integer> timetableIds = jdbcTemplate.queryForList(sql, Integer.class, userId);
        return timetableIds;
    }

    public Integer getYearsForTimetable(Long userId, Integer timetableId) {
        String sql = "SELECT years FROM timetables WHERE user_id = ? AND timetable_id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, userId, timetableId);
    }
}
