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
        courseContainer.appendChild(courseElement);
    }
});
