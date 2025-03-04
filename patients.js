document.getElementById('patient-form').addEventListener('submit', function (e) {
    e.preventDefault();
    
    let name = document.getElementById('name').value;
    let age = document.getElementById('age').value;
    let contact = document.getElementById('contact').value;

    let patientList = document.getElementById('patient-list');
    let li = document.createElement('li');
    li.textContent = Name: ${name}, Age: ${age}, Contact: ${contact};
    patientList.appendChild(li);

    document.getElementById('patient-form').reset();
});