ocument.getElementById('doctor-form').addEventListener('submit', function (e) {
    e.preventDefault();
    
    let name = document.getElementById('doctor-name').value;
    let specialization = document.getElementById('specialization').value;
    let availability = document.getElementById('availability').value;

    let doctorList = document.getElementById('doctor-list');
    let li = document.createElement('li');
    li.textContent = Name: ${name}, Specialization: ${specialization}, Availability: ${availability};
    doctorList.appendChild(li);

    document.getElementById('doctor-form').reset();
});