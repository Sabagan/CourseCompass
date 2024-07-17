package com.example.coursecompass.rowmapper;

import com.example.coursecompass.model.Course;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/*
* Implementing the `RowMapper` interface provided by Spring
* RowMapper interface is used to map rows of a ResultSet (from a SQL query)
  to instances of a Java class (in this case, `course`)
+ Allows us to easily convert the data retrieved from the database into Java
  objects that your application can work with
*/
public class CourseRowMapper implements RowMapper<Course> {

    // This method will be called for each row in the ResultSet
    // Result Set rs: Contains the data fetched from the database
    // int rowNum: current row number in the result set
    @Override
    public Course mapRow(ResultSet rs, int rowNum) throws SQLException {

        // A new instance of the `Course` class is created.
        // This object will be populated with the data from the current row in the ResultSet
        Course course = new Course();

        /*
         * Each line here retrieves a specific column from the current row of
           ResultSet using the appropriate getter method and sets the corresponding
           property on the `Course` object
         */
        course.setId(rs.getLong("id"));
        course.setCourseProgram(rs.getString("course_program"));
        course.setCourseCode(rs.getString("course_code"));
        course.setCourseName(rs.getString("course_name"));
        course.setCourseDescription(rs.getString("course_description"));

        // The populated `Course` object is returned
        return course;
    }
}

/*
 * You don't call the `mapRow` method directly. Instead, it is called by the Spring framework when
   you use a `JdbcTemplate` method that retrieves data from the database and needs to map the result
   set to a list of objects. Typucally, you use the `query` method of `JdbcTemplate` and pass an instance
   of `CourseRowMapper` to it
 */