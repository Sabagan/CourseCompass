document.addEventListener('DOMContentLoaded', () => {
    // Build all the Timetables on the Plan Page
    buildTimetables();
    // Fetch available courses when the page loads
    fetchAvailableCoursesForAll();
    // Load existing plan from the database
    setTimeout(() => {
        loadPlan();
    }, 1000);

    // Modal elements
    const courseModal = document.getElementById('courseModal');
    const courseSelect = document.getElementById('courseSelect');
    const addCourseToPlanBtn = document.getElementById('addCourseToPlan');
    const coursePlanContainer = document.getElementById('coursePlanContainer');
    let currentYear, currentSemester, currentTimetable;

    // Use event delegation to handle dynamically added .add-course-btn elements
    document.body.addEventListener('click', (event) => {
        if (event.target.classList.contains('add-course-btn')) {
            currentYear = event.target.dataset.year;
            currentSemester = event.target.dataset.semester;
            currentTimetable = event.target.dataset.timetable;
            fetchAvailableCourses(currentTimetable);
            showModal();
        }
    });

    document.querySelector('.close').addEventListener('click', hideModal);

    addCourseToPlanBtn.addEventListener('click', addCourseToPlan);

    function showModal() {
        courseModal.style.display = 'block';
    }

    function hideModal() {
        courseModal.style.display = 'none';
    }

    const addTimetableBtn = document.getElementById('add-timetable-btn');
    addTimetableBtn.addEventListener('click', () => {
        fetch('api/timetable/newTimetable', {
            method: 'POST'
        })
            .then(response => response.json())
            .then(timetableId => {
                console.log(timetableId);
                buildTimetable(timetableId);
            })
            .catch(err => console.error(err));
    });

    function buildTimetable(timetableId) {
        coursePlanContainer.insertAdjacentHTML("beforeend", `
                    <table data-timetable="${timetableId}">
                        <thead>
                            <tr>
                                <th>Year</th>
                                <th>Fall Semester</th>
                                <th>Winter Semester</th>
                                <th>Summer Semester</th>
                            </tr>
                        </thead>
                        <tbody id="years-container-${timetableId}"> 
                        </tbody>
                    </table>
               `);

        addYears(timetableId);
    }

    async function fetchAvailableCoursesForAll() {
        try {
            const response = await fetch('/api/timetable/allTimetableIds');

            if (!response.ok) throw new Error('Failed to fetch timetable IDs');
            const timetableIds = await response.json();

            timetableIds.forEach(timetableId => {
                fetchAvailableCourses(timetableId);
            })
        }
        catch (error) {
            console.error('Error fetching', error);
        }
    }

    async function buildTimetables() {
        try {
            const response = await fetch('/api/timetable/allTimetableIds');

            if (!response.ok) throw new Error('Failed to fetch timetable IDs');
            const timetables = await response.json();

            timetables.forEach(timetable => {
                buildTimetable(timetable);
            });
        }
        catch (error) {
            console.error('Error building timetables:', error);
        }
    }

    async function addYears(timetable) {
        const yearsContainer = document.getElementById(`years-container-${timetable}`);
        try {
            const response = await fetch(`/api/timetable/${timetable}`);
            if (!response.ok) throw new Error('Failed to fetch number of years');
            const numberOfYears = await response.json();

            for (let year = 1; year <= numberOfYears; year++) {
                let rowHTML = `<tr data-year="${year}"><td>Year ${year}</td>`;
                const semesters = ['Fall', 'Winter', 'Summer'];

                semesters.forEach(semester => {
                    rowHTML += `
                        <td>
                            <div class="course-container" data-semester="${semester}"></div>
                            <button class="add-course-btn" type="button" data-year="${year}" data-semester="${semester}" data-timetable="${timetable}">+</button>
                        </td>
                    `;
                });

                rowHTML += `</tr>`;
                yearsContainer.insertAdjacentHTML('beforeend', rowHTML);
            }
        }
        catch (error) {
            console.error('Error adding years:', error);
        }
    }

    async function fetchAvailableCourses(timetableId) {
        try {
            const response = await fetch(`/api/timetable/availableCourses${timetableId}`); // Adjust findbyuserid to include timetable number too
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
                    timetableId: currentTimetable,
                    year: currentYear,
                    semester: currentSemester,
                    courseName: courseName // Adjusted based on the backend's request structure
                })
            });
            if (response.ok) {
                addCourseToDOM(currentTimetable, currentYear, currentSemester, { courseName });
                hideModal();
                fetchAvailableCoursesForAll();
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
            if (!response.ok) throw new Error(`HTTP error! Status: ${response.status}`);

            const plan = await response.json();
            plan.forEach(({ timetableId, year, semester, courseName }) => {
                console.log('timetableId', timetableId);
                console.log('year', year);
                console.log('semester', semester);
                console.log('courseName', courseName);
                addCourseToDOM(timetableId, year, semester, { courseName });
            });
        } catch (error) {
            console.error('Error loading plan:', error);
        }
    }

    function addCourseToDOM(timetable, year, semester, course) {
        const courseContainer = document.querySelector(`table[data-timetable="${timetable}"] tr[data-year="${year}"] .course-container[data-semester="${semester}"]`);

        if (!courseContainer) {
            console.error(`Course container not found for timetable ${timetable}, year ${year}, semester ${semester}`);
            return; // Exit the function early if container is not found
        }

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

        removeBtn.addEventListener('click', () => removeCourseFromPlan(timetable, year, semester, course.courseName));

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

    async function removeCourseFromPlan(timetable, year, semester, courseName) {
        try {
            const response = await fetch(`/api/timetable/remove?courseName=${encodeURIComponent(courseName)}&year=${year}&timetable=${timetable}&semester=${encodeURIComponent(semester)}`, {
                method: 'DELETE'
            });
            if (response.ok) {
                // Hide the removed course while on the page
                // The API will take effect once the page reloads (manually by the user, when they visit another page and come back)
                $(`.course`).filter(function () {
                    return $(this).text().trim().startsWith(courseName);
                }).hide();
                fetchAvailableCoursesForAll();
            } else {
                console.error('Failed to remove course');
            }
        } catch (error) {
            console.error('Error removing course:', error);
        }
    }
});
