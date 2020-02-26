/**
 * This will open a fixed window with the complete information of a course.
 * TODO include KCs.
 * @param courseData - the response given by the database. Remember to keep the same name convention as the Java code.
 * @author Robin
 */
function showCourseInfo(courseData) {

    var name = document.getElementById("courseInformation.courseName");
    var description = document.getElementById("courseInformation.description");
    var examiner = document.getElementById("courseInformation.examiner");
    var credit = document.getElementById("courseInformation.credit");



    var code = document.getElementById("courseInformation.courseCode");

    name.innerText = courseData.name;
    description.innerText = courseData.description;
    examiner.innerText = courseData.examiner;
    credit.innerText = courseData.credit;
    code.innerText = courseData.courseCode;

    document.getElementById("courseInformation").style.display = "flex";
}

/**
 * This just hides the course info screen. Nothing fancy going on here.
 * @author Robin
 */
function hideCourseInfo() {
    document.getElementById("courseInformation").style.display = "none";
}

function toggleCourseKC(id) {
    document.getElementById(id).style.display="block";
}

