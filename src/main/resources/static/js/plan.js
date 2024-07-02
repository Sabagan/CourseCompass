document.addEventListener("DOMContentLoaded", () => {
    const modal = document.getElementById("courseModal");
    const span = document.getElementsByClassName("close")[0];
    const addCourseButtons = document.querySelectorAll(".add-course-btn");
    const courseSelect = document.getElementById("courseSelect");
    const addCourseToPlanButton = document.getElementById("addCourseToPlan");

    let selectedYear;
    let selectedSemester;

    addCourseButtons.forEach(button => {
        button.addEventListener("click", (event) => {
            selectedYear = event.target.getAttribute("data-year");
            selectedSemester = event.target.getAttribute("data-semester");
            fetchCourses();
            modal.style.display = "block";
        });
    });

    span.onclick = () => {
        modal.style.display = "none";
    };

    window.onclick = (event) => {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    };

    addCourseToPlanButton.addEventListener("click", () => {
        const courseId = courseSelect.value;
        addCourseToPlan(courseId, selectedYear, selectedSemester);
        modal.style.display = "none";
    });

    function fetchCourses() {
        fetch('/api/mycourses')
            .then(response => response.json())
            .then(data => {
                courseSelect.innerHTML = '';
                data.forEach(course => {
                    let option = document.createElement("option");
                    option.value = course.id;
                    option.text = course.courseName;
                    courseSelect.add(option);
                });
            });
    }

    function addCourseToPlan(courseId, year, semester) {
        fetch('/api/addCourseToPlan', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ courseId, year, semester })
        })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    alert("Course added successfully!");
                    // You can update the timetable here if necessary
                } else {
                    alert("Error adding course: " + data.message);
                }
            });
    }
});
