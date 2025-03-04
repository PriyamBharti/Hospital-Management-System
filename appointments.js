document.getElementById('appointment-form').addEventListener('submit', function (e) {
    e.preventDefault();
    
    let doctorId = document.getElementById('doctor-id').value;
    let patientId = document.getElementById('patient-id').value;
    let appointmentTime = document.getElementById('appointment-time').value;

    let appointmentList = document.getElementById('appointment-list');
    let li = document.createElement('li');
    li.textContent = `Doctor ID: ${doctorId}, Patient ID: ${patientId}, Time: ${appointmentTime}`;
    appointmentList.appendChild(li);

    document.getElementById('appointment-form').reset();
});
