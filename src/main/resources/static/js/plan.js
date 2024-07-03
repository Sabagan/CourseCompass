document.addEventListener("DOMContentLoaded", function() {
    const addCourseButtons = document.querySelectorAll('.add-course-btn');

    // Load saved timetable data on page load
    loadTimetable();

    addCourseButtons.forEach(button => {
        button.addEventListener('click', function () {
            const year = button.getAttribute('data-year');
            const semester = button.getAttribute('data-semester');
            openCourseModal(year, semester);
        });
    });

    function openCourseModal(year, semester) {
        // Fetch available courses from the backend that are not in the timetable
        fetch('/api/timetable/availableCourses')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok ' + response.statusText);
                }
                return response.json();
            })
            .then(availableCourses => {
                const modal = document.getElementById('courseModal');
                const courseSelect = document.getElementById('courseSelect');
                courseSelect.innerHTML = ''; // Clear previous options

                availableCourses.forEach(course => {
                    const option = document.createElement('option');
                    option.value = course.courseName;
                    option.textContent = course.courseName;
                    courseSelect.appendChild(option);
                });

                document.getElementById('addCourseToPlan').onclick = function () {
                    const selectedCourseName = courseSelect.value;
                    if (selectedCourseName) {
                        addCourseToTimetable(year, semester, selectedCourseName);
                        modal.style.display = 'none'; // Close modal after adding course
                    }
                };

                modal.style.display = 'block';

                modal.querySelector('.close').onclick = function () {
                    modal.style.display = 'none';
                };

                window.onclick = function (event) {
                    if (event.target === modal) {
                        modal.style.display = 'none';
                    }
                };
            })
            .catch(error => {
                console.error('Error fetching available courses:', error);
            });
    }

    function isCourseInTimetable(courseName) {
        const courseButtons = document.querySelectorAll('#timetable button[data-course-name]');
        return Array.from(courseButtons).some(button => button.getAttribute('data-course-name') === courseName);
    }

    function addCourseToTimetable(year, semester, courseName) {
        if (isCourseInTimetable(courseName)) {
            alert('This course is already added to the timetable.');
            return;
        }

        const tableRows = document.querySelectorAll('#timetable tbody tr');
        tableRows.forEach(row => {
            if (row.getAttribute('data-year') === year) {
                const semesterCell = row.querySelector(`div.course-container[data-semester="${semester}"]`);
                if (semesterCell) {
                    if (semesterCell.querySelectorAll('button[data-course-name]').length < 6) {
                        const courseButton = document.createElement('button');
                        courseButton.textContent = courseName;
                        courseButton.setAttribute('data-course-name', courseName);
                        courseButton.classList.add('course-btn');

                        const removeButton = document.createElement('button');
                        removeButton.textContent = 'Remove';
                        removeButton.classList.add('remove-course-btn');
                        removeButton.onclick = function () {
                            removeCourseFromTimetable(courseName, year, semester);
                        };

                        const container = document.createElement('div');
                        container.classList.add('course-item');
                        container.appendChild(courseButton);
                        container.appendChild(removeButton);
                        semesterCell.appendChild(container);

                        // Save course in the timetable
                        saveCourseInTimetable(year, semester, courseName);
                    } else {
                        alert('You can only add up to 6 courses per semester.');
                    }
                }
            }
        });
    }

    function saveCourseInTimetable(year, semester, courseName) {
        const data = {
            year: parseInt(year, 10),
            semester: semester,
            courseName: courseName
        };

        fetch('/api/timetable/save', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok ' + response.statusText);
                }
            })
            .catch(error => {
                console.error('Error saving course in timetable:', error);
            });
    }

    function removeCourseFromTimetable(courseName, year, semester) {
        fetch('/remove', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: new URLSearchParams({
                courseName: courseName,
                year: year,
                semester: semester
            })
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to remove course');
                }
                console.log('Course removed successfully');
                // Optionally update UI or perform other actions upon successful deletion
                // For example, remove the course button from the UI
                const buttonToRemove = document.querySelector(`button[data-course-name="${courseName}"][data-semester="${semester}"]`);
                if (buttonToRemove) {
                    buttonToRemove.parentNode.removeChild(buttonToRemove);
                }
            })
            .catch(error => console.error('Error removing course:', error));
    }

    function loadTimetable() {
        fetch('/api/timetable')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok ' + response.statusText);
                }
                return response.json();
            })
            .then(timetable => {
                console.log('Loaded timetable:', timetable); // Log loaded timetable data

                timetable.forEach(entry => {
                    addCourseToTimetable(entry.year, entry.semester, entry.courseName);
                });
            })
            .catch(error => {
                console.error('Error loading timetable:', error);
            });
    }
}