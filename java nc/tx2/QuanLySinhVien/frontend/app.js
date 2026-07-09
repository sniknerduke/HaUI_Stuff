const API_BASE_URL = 'http://localhost:8080/api';
let jwtToken = localStorage.getItem('token');

document.addEventListener('DOMContentLoaded', () => {
    if (jwtToken) {
        showSection('students-section');
        fetchStudents();
    } else {
        showSection('login-section');
    }

    document.getElementById('login-form').addEventListener('submit', handleLogin);
    document.getElementById('register-form').addEventListener('submit', handleRegister);
    document.getElementById('student-form').addEventListener('submit', saveStudent);
    document.getElementById('faculty-form').addEventListener('submit', saveFaculty);
    document.getElementById('major-form').addEventListener('submit', saveMajor);
    document.getElementById('class-form').addEventListener('submit', saveClass);
    document.getElementById('course-form').addEventListener('submit', saveCourse);
    document.getElementById('enrollment-form').addEventListener('submit', saveEnrollment);
    document.getElementById('grade-form').addEventListener('submit', saveGrade);
});

function toggleAuthMode() {
    const loginForm = document.getElementById('login-form');
    const registerForm = document.getElementById('register-form');
    const title = document.getElementById('auth-title');
    const subtitle = document.getElementById('auth-subtitle');

    if (loginForm.style.display === 'none') {
        loginForm.style.display = 'block';
        registerForm.style.display = 'none';
        title.textContent = 'Welcome Back';
        subtitle.textContent = 'Please login to your account';
    } else {
        loginForm.style.display = 'none';
        registerForm.style.display = 'block';
        title.textContent = 'Create Account';
        subtitle.textContent = 'Register for a new account';
    }
}

async function handleRegister(e) {
    e.preventDefault();
    const username = document.getElementById('reg-username').value;
    const password = document.getElementById('reg-password').value;
    const fullName = document.getElementById('reg-fullname').value;
    const email = document.getElementById('reg-email').value;
    const role = document.getElementById('reg-role').value;
    const messageDiv = document.getElementById('register-message');

    try {
        const response = await fetch(`${API_BASE_URL}/auth/register`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ username, password, fullName, email, role })
        });
        
        const data = await response.json();
        
        if (response.ok) {
            messageDiv.className = 'mb-3 text-center small fw-bold text-success';
            messageDiv.textContent = 'Registration successful! Switching to login...';
            messageDiv.style.display = 'block';
            setTimeout(() => {
                messageDiv.style.display = 'none';
                document.getElementById('register-form').reset();
                toggleAuthMode();
            }, 2000);
        } else {
            messageDiv.className = 'mb-3 text-center small fw-bold text-danger';
            let errorText = data.message || 'Registration failed';
            
            // Render specific validation errors if the backend offers them
            if (data.errors && typeof data.errors === 'object') {
                const validationIssues = Object.values(data.errors).join(', ');
                errorText += ` - ${validationIssues}`;
            }

            messageDiv.textContent = errorText;
            messageDiv.style.display = 'block';
        }
    } catch (error) {
        messageDiv.className = 'mb-3 text-center small fw-bold text-danger';
        messageDiv.textContent = 'Network error or server is down.';
        messageDiv.style.display = 'block';
    }
}

function showSection(sectionId) {
    const sections = [
        'login-section', 
        'students-section',
        'faculties-section',
        'majors-section',
        'classes-section',
        'courses-section',
        'enrollments-section',
        'grades-section'
    ];
    sections.forEach(s => {
        const el = document.getElementById(s);
        if (el) el.style.display = 'none';
    });
    
    document.getElementById(sectionId).style.display = 'block';

    if (sectionId !== 'login-section') {
        document.getElementById('main-nav').style.display = 'block';
    } else {
        document.getElementById('main-nav').style.display = 'none';
    }
}

async function handleLogin(e) {
    e.preventDefault();
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    const errorDiv = document.getElementById('login-error');

    try {
        const response = await fetch(`${API_BASE_URL}/auth/login`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ username, password })
        });
        
        const data = await response.json();
        
        if (response.ok) {
            jwtToken = data.data.token || data.data.accessToken;
            localStorage.setItem('token', jwtToken);
            errorDiv.style.display = 'none';
            showSection('students-section');
            fetchStudents();
        } else {
            errorDiv.textContent = data.message || 'Login failed';
            errorDiv.style.display = 'block';
        }
    } catch (error) {
        errorDiv.textContent = 'Network error or server is down.';
        errorDiv.style.display = 'block';
    }
}

function logout() {
    jwtToken = null;
    localStorage.removeItem('token');
    showSection('login-section');
    document.getElementById('login-form').reset();
}

let currentStudentPage = 0;

async function fetchStudents(page = 0) {
    const keyword = document.getElementById('search-keyword').value;
    const searchStatus = document.getElementById('search-status').value;
    // can hook up class ID dropdown later once fetched from classes

    let url = `${API_BASE_URL}/students/search?page=${page}&size=10`;
    if (keyword) url += `&keyword=${encodeURIComponent(keyword)}`;
    if (searchStatus) url += `&status=${searchStatus}`;

    try {
        const response = await fetch(url, {
            headers: { 'Authorization': `Bearer ${jwtToken}` }
        });
        
        if (response.status === 401 || response.status === 403) {
            logout();
            return;
        }

        const data = await response.json();
        
        if (response.ok) {
            // Spring data JPA Page structure usually puts content in `content` and paging info in top level
            const students = data.data.content || data.data || [];
            currentStudentPage = data.data.number || 0;
            const totalPages = data.data.totalPages || 1;
            const isLast = data.data.last;

            renderStudents(students);
            
            document.getElementById('student-page-info').textContent = `Page ${currentStudentPage + 1}`;
            document.getElementById('btn-prev-student').disabled = currentStudentPage === 0;
            document.getElementById('btn-next-student').disabled = isLast;
        } else {
            alert('Failed to fetch students: ' + data.message);
        }
    } catch (error) {
        console.error('Error fetching students:', error);
    }
}

function renderStudents(students) {
    const tbody = document.getElementById('students-tbody');
    tbody.innerHTML = '';
    
    if(!students || students.length === 0) {
        tbody.innerHTML = '<tr><td colspan="7" class="text-center">No students found.</td></tr>';
        return;
    }

    students.forEach(student => {
        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td class="fw-semibold text-muted">#${student.id}</td>
            <td><span class="badge bg-secondary">${student.studentCode || ''}</span></td>
            <td class="fw-bold">${student.fullName || student.firstName + ' ' + student.lastName}</td>
            <td><a href="mailto:${student.email || ''}" class="text-decoration-none">${student.email || ''}</a></td>
            <td>${student.phone || ''}</td>
            <td><span class="badge ${student.status === 'DANG_HOC' ? 'bg-success' : 'bg-warning'}">${student.status || 'DANG_HOC'}</span></td>
            <td class="text-end">
                <button class="btn btn-sm btn-outline-primary px-3 rounded-pill me-1" onclick='openEditStudentModal(${JSON.stringify(student).replace(/'/g, "&apos;")})'>Edit</button>
                <button class="btn btn-sm btn-outline-danger px-3 rounded-pill" onclick="deleteStudent(${student.id})">Delete</button>
            </td>
        `;
        tbody.appendChild(tr);
    });
}

function openAddStudentModal() {
    document.getElementById('student-form').reset();
    document.getElementById('student-id').value = '';
    document.getElementById('studentModalTitle').textContent = 'Add New Student';
    document.getElementById('student-error').style.display = 'none';
    const modal = new bootstrap.Modal(document.getElementById('studentModal'));
    modal.show();
}

function openEditStudentModal(student) {
    document.getElementById('student-id').value = student.id;
    document.getElementById('stu-code').value = student.studentCode || '';
    document.getElementById('stu-firstname').value = student.firstName || '';
    document.getElementById('stu-lastname').value = student.lastName || '';
    document.getElementById('stu-email').value = student.email || '';
    document.getElementById('stu-phone').value = student.phone || '';
    if(student.dateOfBirth) document.getElementById('stu-dob').value = student.dateOfBirth;
    if(student.gender) document.getElementById('stu-gender').value = student.gender;
    if(student.status) document.getElementById('stu-status').value = student.status;
    document.getElementById('stu-classid').value = student.classId || ''; // Update mapping if your backend passes nested object
    document.getElementById('stu-address').value = student.address || '';
    
    document.getElementById('studentModalTitle').textContent = 'Edit Student';
    document.getElementById('student-error').style.display = 'none';
    const modal = new bootstrap.Modal(document.getElementById('studentModal'));
    modal.show();
}

async function saveStudent(e) {
    e.preventDefault();
    const id = document.getElementById('student-id').value;
    const isEdit = !!id;
    
    const payload = {
        studentCode: document.getElementById('stu-code').value,
        firstName: document.getElementById('stu-firstname').value,
        lastName: document.getElementById('stu-lastname').value,
        email: document.getElementById('stu-email').value,
        phone: document.getElementById('stu-phone').value,
        dateOfBirth: document.getElementById('stu-dob').value || null,
        gender: document.getElementById('stu-gender').value,
        status: document.getElementById('stu-status').value,
        classId: document.getElementById('stu-classid').value,
        address: document.getElementById('stu-address').value
    };

    const url = isEdit ? `${API_BASE_URL}/students/${id}` : `${API_BASE_URL}/students`;
    const method = isEdit ? 'PUT' : 'POST';
    const errorDiv = document.getElementById('student-error');

    try {
        const response = await fetch(url, {
            method: method,
            headers: { 
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${jwtToken}`
            },
            body: JSON.stringify(payload)
        });
        
        const data = await response.json();
        
        if (response.ok) {
            bootstrap.Modal.getInstance(document.getElementById('studentModal')).hide();
            fetchStudents(); // Refresh table
        } else {
            errorDiv.textContent = data.message || 'Operation failed';
            
            // if errors map exist from Spring Validation
            if(data.errors) {
                errorDiv.textContent += ': ' + JSON.stringify(data.errors);
            }
            
            errorDiv.style.display = 'block';
        }
    } catch (error) {
        errorDiv.textContent = 'Network error or server is down.';
        errorDiv.style.display = 'block';
    }
}

async function deleteStudent(id) {
    if (!confirm('Are you sure you want to delete this student?')) return;

    try {
        const response = await fetch(`${API_BASE_URL}/students/${id}`, {
            method: 'DELETE',
            headers: { 'Authorization': `Bearer ${jwtToken}` }
        });
        
        if (response.ok) {
            fetchStudents(currentStudentPage); // refresh
        } else {
            const data = await response.json();
            alert('Failed to delete: ' + (data.message || ''));
        }
    } catch (error) {
        alert('Network error');
    }
}

// -------------------------------------------------------------
// MODULES FETCHERS (Faculties, Majors, Classes, Courses, Enrollments, Grades)
// -------------------------------------------------------------
async function genericFetch(endpoint, tbodyId, renderFn) {
    try {
        const res = await fetch(`${API_BASE_URL}/${endpoint}`, {
            headers: { 'Authorization': `Bearer ${jwtToken}` }
        });
        if (res.ok) {
            const data = await res.json();
            const items = data.data.content || data.data || [];
            renderFn(items, tbodyId);
        }
    } catch (err) {
        console.error(`Error loading ${endpoint}:`, err);
    }
}

// === FACULTIES ===
function fetchFaculties() {
    genericFetch('faculties', 'faculties-tbody', (items, id) => {
        const tbody = document.getElementById(id);
        tbody.innerHTML = '';
        if(!items.length) { tbody.innerHTML = '<tr><td colspan="4" class="text-center">No data.</td></tr>'; return; }
        items.forEach(i => {
            tbody.innerHTML += `<tr>
                <td>${i.id}</td><td>${i.facultyCode || i.code || ''}</td><td>${i.facultyName || i.name || ''}</td>
                <td class="text-end">
                    <button class="btn btn-sm btn-outline-primary px-3 rounded-pill me-1" onclick='openEditFacultyModal(${JSON.stringify(i).replace(/'/g, "&apos;")})'>Edit</button>
                    <button class="btn btn-sm btn-outline-danger px-3 rounded-pill" onclick="deleteFaculty(${i.id})">Delete</button>
                </td>
            </tr>`;
        });
    });
}
function openAddFacultyModal() {
    document.getElementById('faculty-form').reset();
    document.getElementById('faculty-id').value = '';
    document.getElementById('facultyModalTitle').textContent = 'Add Faculty';
    document.getElementById('faculty-error').style.display = 'none';
    new bootstrap.Modal(document.getElementById('facultyModal')).show();
}
function openEditFacultyModal(fac) {
    document.getElementById('faculty-id').value = fac.id;
    document.getElementById('fac-code').value = fac.facultyCode || fac.code || '';
    document.getElementById('fac-name').value = fac.facultyName || fac.name || '';
    document.getElementById('facultyModalTitle').textContent = 'Edit Faculty';
    document.getElementById('faculty-error').style.display = 'none';
    new bootstrap.Modal(document.getElementById('facultyModal')).show();
}
async function saveFaculty(e) {
    e.preventDefault();
    const id = document.getElementById('faculty-id').value;
    const payload = { code: document.getElementById('fac-code').value, name: document.getElementById('fac-name').value };
    await saveEntity('faculties', id, payload, 'facultyModal', 'faculty-error', fetchFaculties);
}
async function deleteFaculty(id) { await deleteEntity('faculties', id, fetchFaculties); }

// === MAJORS ===
function fetchMajors() {
    genericFetch('majors', 'majors-tbody', (items, id) => {
        const tbody = document.getElementById(id);
        tbody.innerHTML = '';
        if(!items.length) { tbody.innerHTML = '<tr><td colspan="5" class="text-center">No data.</td></tr>'; return; }
        items.forEach(i => {
            tbody.innerHTML += `<tr>
                <td>${i.id}</td><td>${i.majorCode || i.code || ''}</td><td>${i.majorName || i.name || ''}</td><td>${i.facultyId || (i.faculty ? i.faculty.id : '')}</td>
                <td class="text-end">
                    <button class="btn btn-sm btn-outline-primary px-3 rounded-pill me-1" onclick='openEditMajorModal(${JSON.stringify(i).replace(/'/g, "&apos;")})'>Edit</button>
                    <button class="btn btn-sm btn-outline-danger px-3 rounded-pill" onclick="deleteMajor(${i.id})">Delete</button>
                </td>
            </tr>`;
        });
    });
}
function openAddMajorModal() {
    document.getElementById('major-form').reset();
    document.getElementById('major-id').value = '';
    document.getElementById('majorModalTitle').textContent = 'Add Major';
    document.getElementById('major-error').style.display = 'none';
    new bootstrap.Modal(document.getElementById('majorModal')).show();
}
function openEditMajorModal(maj) {
    document.getElementById('major-id').value = maj.id;
    document.getElementById('maj-code').value = maj.majorCode || maj.code || '';
    document.getElementById('maj-name').value = maj.majorName || maj.name || '';
    document.getElementById('maj-facultyid').value = maj.facultyId || (maj.faculty ? maj.faculty.id : '');
    document.getElementById('majorModalTitle').textContent = 'Edit Major';
    document.getElementById('major-error').style.display = 'none';
    new bootstrap.Modal(document.getElementById('majorModal')).show();
}
async function saveMajor(e) {
    e.preventDefault();
    const id = document.getElementById('major-id').value;
    const payload = { 
        code: document.getElementById('maj-code').value, 
        name: document.getElementById('maj-name').value,
        facultyId: document.getElementById('maj-facultyid').value
    };
    await saveEntity('majors', id, payload, 'majorModal', 'major-error', fetchMajors);
}
async function deleteMajor(id) { await deleteEntity('majors', id, fetchMajors); }

// === CLASSES ===
function fetchClasses() {
    genericFetch('classes', 'classes-tbody', (items, id) => {
        const tbody = document.getElementById(id);
        tbody.innerHTML = '';
        if(!items.length) { tbody.innerHTML = '<tr><td colspan="5" class="text-center">No data.</td></tr>'; return; }
        items.forEach(i => {
            tbody.innerHTML += `<tr>
                <td>${i.id}</td><td>${i.classCode || i.code || ''}</td><td>${i.className || i.name || ''}</td><td>${i.majorId || (i.major ? i.major.id : '')}</td>
                <td class="text-end">
                    <button class="btn btn-sm btn-outline-primary px-3 rounded-pill me-1" onclick='openEditClassModal(${JSON.stringify(i).replace(/'/g, "&apos;")})'>Edit</button>
                    <button class="btn btn-sm btn-outline-danger px-3 rounded-pill" onclick="deleteClass(${i.id})">Delete</button>
                </td>
            </tr>`;
        });
    });
}
function openAddClassModal() {
    document.getElementById('class-form').reset();
    document.getElementById('class-id').value = '';
    document.getElementById('classModalTitle').textContent = 'Add Class';
    document.getElementById('class-error').style.display = 'none';
    new bootstrap.Modal(document.getElementById('classModal')).show();
}
function openEditClassModal(cls) {
    document.getElementById('class-id').value = cls.id;
    document.getElementById('cls-code').value = cls.classCode || cls.code || '';
    document.getElementById('cls-name').value = cls.className || cls.name || '';
    document.getElementById('cls-majorid').value = cls.majorId || (cls.major ? cls.major.id : '');
    document.getElementById('cls-year').value = cls.academicYear || '';
    document.getElementById('classModalTitle').textContent = 'Edit Class';
    document.getElementById('class-error').style.display = 'none';
    new bootstrap.Modal(document.getElementById('classModal')).show();
}
async function saveClass(e) {
    e.preventDefault();
    const id = document.getElementById('class-id').value;
    const payload = { 
        code: document.getElementById('cls-code').value, 
        name: document.getElementById('cls-name').value,
        majorId: document.getElementById('cls-majorid').value,
        academicYear: document.getElementById('cls-year').value || null
    };
    await saveEntity('classes', id, payload, 'classModal', 'class-error', fetchClasses);
}
async function deleteClass(id) { await deleteEntity('classes', id, fetchClasses); }

// === COURSES ===
function fetchCourses() {
    genericFetch('courses', 'courses-tbody', (items, id) => {
        const tbody = document.getElementById(id);
        tbody.innerHTML = '';
        if(!items.length) { tbody.innerHTML = '<tr><td colspan="5" class="text-center">No data.</td></tr>'; return; }
        items.forEach(i => {
            tbody.innerHTML += `<tr>
                <td>${i.id}</td><td>${i.courseCode || ''}</td><td>${i.courseName || i.name || ''}</td><td>${i.credits || ''}</td>
                <td class="text-end">
                    <button class="btn btn-sm btn-outline-primary px-3 rounded-pill me-1" onclick='openEditCourseModal(${JSON.stringify(i).replace(/'/g, "&apos;")})'>Edit</button>
                    <button class="btn btn-sm btn-outline-danger px-3 rounded-pill" onclick="deleteCourse(${i.id})">Delete</button>
                </td>
            </tr>`;
        });
    });
}
function openAddCourseModal() {
    document.getElementById('course-form').reset();
    document.getElementById('course-id').value = '';
    document.getElementById('courseModalTitle').textContent = 'Add Course';
    document.getElementById('course-error').style.display = 'none';
    new bootstrap.Modal(document.getElementById('courseModal')).show();
}
function openEditCourseModal(crs) {
    document.getElementById('course-id').value = crs.id;
    document.getElementById('crs-code').value = crs.courseCode || crs.code || '';
    document.getElementById('crs-name').value = crs.courseName || crs.name || '';
    document.getElementById('crs-credits').value = crs.credits || '';
    document.getElementById('crs-desc').value = crs.description || '';
    document.getElementById('courseModalTitle').textContent = 'Edit Course';
    document.getElementById('course-error').style.display = 'none';
    new bootstrap.Modal(document.getElementById('courseModal')).show();
}
async function saveCourse(e) {
    e.preventDefault();
    const id = document.getElementById('course-id').value;
    const payload = { 
        courseCode: document.getElementById('crs-code').value, 
        name: document.getElementById('crs-name').value,
        credits: document.getElementById('crs-credits').value,
        description: document.getElementById('crs-desc').value
    };
    await saveEntity('courses', id, payload, 'courseModal', 'course-error', fetchCourses);
}
async function deleteCourse(id) { await deleteEntity('courses', id, fetchCourses); }

// === ENROLLMENTS ===
let currentEnrollmentPage = 0;
async function fetchEnrollments(page = 0) {
    currentEnrollmentPage = page;
    try {
        const res = await fetch(`${API_BASE_URL}/enrollments?page=${page}&size=10`, {
            headers: { 'Authorization': `Bearer ${jwtToken}` }
        });
        if (res.ok) {
            const data = await res.json();
            const items = data.data.content || [];
            renderEnrollments(items);
            document.getElementById('enrollment-page-info').textContent = `Page ${data.data.number + 1}`;
            document.getElementById('btn-prev-enrollment').disabled = data.data.number === 0;
            document.getElementById('btn-next-enrollment').disabled = data.data.last;
        }
    } catch (err) {
        console.error(err);
    }
}
async function fetchEnrollmentsByStudent() {
    const sId = document.getElementById('search-enrollment-studentid').value;
    if(!sId) return fetchEnrollments(0);
    try {
        const res = await fetch(`${API_BASE_URL}/enrollments/student/${sId}`, {
            headers: { 'Authorization': `Bearer ${jwtToken}` }
        });
        if (res.ok) {
            const data = await res.json();
            const items = data.data || [];
            renderEnrollments(items);
            document.getElementById('enrollment-page-info').textContent = `Page 1`;
            document.getElementById('btn-prev-enrollment').disabled = true;
            document.getElementById('btn-next-enrollment').disabled = true;
        }
    } catch (err) {
        console.error(err);
    }
}
function renderEnrollments(items) {
    const tbody = document.getElementById('enrollments-tbody');
    tbody.innerHTML = '';
    if(!items.length) { tbody.innerHTML = '<tr><td colspan="5" class="text-center">No data.</td></tr>'; return; }
    items.forEach(i => {
        tbody.innerHTML += `<tr>
            <td>${i.id}</td>
            <td>${i.studentName || (i.student ? i.student.fullName : i.studentId)}</td>
            <td>${i.courseName || (i.course ? i.course.courseName : i.courseId)}</td>
            <td>${i.semester || ''}</td>
            <td class="text-end">
                <span class="badge bg-secondary">Managed in Grades</span>
            </td>
        </tr>`;
    });
}
function openAddEnrollmentModal() {
    document.getElementById('enrollment-form').reset();
    document.getElementById('enrollment-error').style.display = 'none';
    new bootstrap.Modal(document.getElementById('enrollmentModal')).show();
}
async function saveEnrollment(e) {
    e.preventDefault();
    const payload = { 
        studentId: document.getElementById('enr-studentid').value, 
        courseId: document.getElementById('enr-courseid').value,
        semester: document.getElementById('enr-semester').value
    };
    await saveEntity('enrollments', null, payload, 'enrollmentModal', 'enrollment-error', fetchEnrollments);
}

// === GRADES ===
async function fetchTranscript() {
    const studentId = document.getElementById('transcript-studentid').value;
    if(!studentId) return;
    try {
        const res = await fetch(`${API_BASE_URL}/grades/transcript/${studentId}`, {
            headers: { 'Authorization': `Bearer ${jwtToken}` }
        });
        if (res.ok) {
            const data = await res.json();
            const t = data.data;
            document.getElementById('transcript-info').style.display = 'block';
            document.getElementById('transcript-student-name').textContent = t.studentName || '';
            document.getElementById('transcript-student-code').textContent = t.studentCode || '';
            document.getElementById('transcript-gpa').textContent = 'GPA: ' + (t.gpa ? t.gpa.toFixed(2) : 'N/A');
            
            const tbody = document.getElementById('grades-tbody');
            tbody.innerHTML = '';
            if(!t.grades || !t.grades.length) {
                tbody.innerHTML = '<tr><td colspan="7" class="text-center">No grades found.</td></tr>';
            } else {
                t.grades.forEach(g => {
                    tbody.innerHTML += `<tr>
                        <td>${g.courseCode || ''}</td>
                        <td>${g.courseName || ''}</td>
                        <td>${g.credits || ''}</td>
                        <td>${g.midtermScore !== null ? g.midtermScore : '-'}</td>
                        <td>${g.finalScore !== null ? g.finalScore : '-'}</td>
                        <td class="fw-bold">${g.overallScore !== null ? g.overallScore : '-'}</td>
                        <td class="text-end">
                            <button class="btn btn-sm btn-outline-primary px-3 rounded-pill" onclick='openEditGradeModal(${g.enrollmentId}, ${g.midtermScore || 0}, ${g.finalScore || 0})'>Update</button>
                        </td>
                    </tr>`;
                });
            }
        } else {
            alert('Could not fetch transcript');
        }
    } catch (err) {
        console.error(err);
    }
}

function openEditGradeModal(enrollmentId, mid, fin) {
    document.getElementById('grd-enrollmentid').value = enrollmentId;
    document.getElementById('grd-midterm').value = mid;
    document.getElementById('grd-final').value = fin;
    document.getElementById('grade-error').style.display = 'none';
    new bootstrap.Modal(document.getElementById('gradeModal')).show();
}
async function saveGrade(e) {
    e.preventDefault();
    const payload = { 
        enrollmentId: document.getElementById('grd-enrollmentid').value, 
        midtermScore: document.getElementById('grd-midterm').value,
        finalScore: document.getElementById('grd-final').value
    };
    await saveEntity('grades', null, payload, 'gradeModal', 'grade-error', fetchTranscript);
}


// === UTILS ===
async function saveEntity(endpoint, id, payload, modalId, errorId, successCallback) {
    const url = id ? `${API_BASE_URL}/${endpoint}/${id}` : `${API_BASE_URL}/${endpoint}`;
    const method = id ? 'PUT' : 'POST';
    const errorDiv = document.getElementById(errorId);
    
    try {
        const res = await fetch(url, {
            method: method,
            headers: { 'Content-Type': 'application/json', 'Authorization': `Bearer ${jwtToken}` },
            body: JSON.stringify(payload)
        });
        const data = await res.json();
        if (res.ok) {
            bootstrap.Modal.getInstance(document.getElementById(modalId)).hide();
            if(successCallback) successCallback();
        } else {
            errorDiv.textContent = data.message || 'Operation failed';
            if(data.errors) errorDiv.textContent += ': ' + JSON.stringify(data.errors);
            errorDiv.style.display = 'block';
        }
    } catch (err) {
        errorDiv.textContent = 'Network error or server down.';
        errorDiv.style.display = 'block';
    }
}
async function deleteEntity(endpoint, id, successCallback) {
    if(!confirm('Are you sure you want to delete this item?')) return;
    try {
        const res = await fetch(`${API_BASE_URL}/${endpoint}/${id}`, {
            method: 'DELETE',
            headers: { 'Authorization': `Bearer ${jwtToken}` }
        });
        if (res.ok) {
            if(successCallback) successCallback();
        } else {
            const data = await res.json();
            alert('Failed to delete: ' + (data.message || ''));
        }
    } catch(err) {
        alert('Network error');
    }
}
