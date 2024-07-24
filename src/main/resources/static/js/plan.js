document.addEventListener('DOMContentLoaded', () => {
    // Fetch available courses when the page loads
    fetchAvailableCourses();
    // Load existing plan from the database
    loadPlan();

    // Modal elements
    const courseModal = document.getElementById('courseModal');
    const courseSelect = document.getElementById('courseSelect');
    const addCourseToPlanBtn = document.getElementById('addCourseToPlan');
    let currentYear, currentSemester;

    document.querySelectorAll('.add-course-btn').forEach(btn => {
        btn.addEventListener('click', (event) => {
            currentYear = event.target.dataset.year;
            currentSemester = event.target.dataset.semester;
            showModal();
        });
    });

    document.querySelector('.close').addEventListener('click', hideModal);

    addCourseToPlanBtn.addEventListener('click', addCourseToPlan);

    function showModal() {
        courseModal.style.display = 'block';
    }

    function hideModal() {
        courseModal.style.display = 'none';
    }

    async function fetchAvailableCourses() {
        try {
            const response = await fetch('/api/timetable/availableCourses');
            const courses = await response.json();
            populateCourseSelect(courses);
        } catch (error) {
            console.error('Error fetching available courses:', error);
        }
    }

    function populateCourseSelect(courses) {
        courseSelect.innerHTML = '<option value="" disabled selected>Choose a course</option>';
        courses.forEach(course => {
            const option = document.createElement('option');
            option.value = course.courseName; // Adjusted based on the backend's response structure
            option.textContent = course.courseName; // Adjusted based on the backend's response structure
            courseSelect.appendChild(option);
        });
    }

    async function addCourseToPlan() {
        const courseName = courseSelect.value;
        if (!courseName) return;

        try {
            const response = await fetch('/api/timetable/save', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    year: currentYear,
                    semester: currentSemester,
                    courseName: courseName // Adjusted based on the backend's request structure
                })
            });
            if (response.ok) {
                addCourseToDOM(currentYear, currentSemester, { courseName });
                hideModal();
                // window.location.reload();
                fetchAvailableCourses();
            } else {
                console.error('Failed to add course');
            }
        } catch (error) {
            console.error('Error adding course:', error);
        }
    }

    async function loadPlan() {
        try {
            const response = await fetch('/api/timetable/all');
            const plan = await response.json();
            plan.forEach(({ year, semester, courseName }) => {
                addCourseToDOM(year, semester, { courseName });
            });
        } catch (error) {
            console.error('Error loading plan:', error);
        }
    }

    function addCourseToDOM(year, semester, course) {
        const courseContainer = document.querySelector(`tr[data-year="${year}"] .course-container[data-semester="${semester}"]`);
        const courseElement = document.createElement('div');
        courseElement.className = 'course';
        courseElement.textContent = course.courseName;

        const removeBtn = document.createElement('button');
        removeBtn.textContent = '-';
        removeBtn.className = 'remove-course-btn';
        removeBtn.style.marginLeft = '10px';
        removeBtn.style.backgroundColor = 'red';
        removeBtn.style.color = 'white';
        removeBtn.style.border = 'none';
        removeBtn.style.marginTop = '12px';
        removeBtn.style.marginBottom = '12px';

        removeBtn.addEventListener('click', () => removeCourseFromPlan(year, semester, course.courseName));

        courseElement.appendChild(removeBtn);
        courseContainer.appendChild(courseElement);

        // Add border-bottom to previous course divs
        const courses = courseContainer.querySelectorAll('.course');
        courses.forEach((course, index) => {
            course.style.borderBottom = index === courses.length - 1 ? 'none' : '1px solid #ccc';
            course.style.marginTop = '12px';
            course.style.marginBottom = '12px';
        });
    }

    async function removeCourseFromPlan(year, semester, courseName) {
        try {
            const response = await fetch(`/api/timetable/remove?courseName=${encodeURIComponent(courseName)}&year=${year}&semester=${encodeURIComponent(semester)}`, {
                method: 'DELETE'
            });
            if (response.ok) {
                // Hide the removed course while on the page
                // The API will take effect once the page reloads (manually by the user, when they visit another page and come back)
                $(`.course`).filter(function () {
                    return $(this).text().trim().startsWith(courseName);
                }).hide();
            } else {
                console.error('Failed to remove course');
            }
        } catch (error) {
            console.error('Error removing course:', error);
        }
    }
});
