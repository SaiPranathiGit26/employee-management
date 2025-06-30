const form = document.getElementById('employeeForm');
const table = document.querySelector('#employeeTable tbody');
const modeToggle = document.getElementById('modeToggle');

// Dark mode toggle
modeToggle.addEventListener('click', () => {
  document.body.classList.toggle('dark');
});

// Submit form
form.addEventListener('submit', async (e) => {
  e.preventDefault();
  const name = document.getElementById('name').value;
  const email = document.getElementById('email').value;
  const department = document.getElementById('department').value;

  await fetch('http://localhost:8081/api/employees', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ name, email, department })
  });

  form.reset();
  loadEmployees();
});

async function loadEmployees() {
  const res = await fetch('http://localhost:8081/api/employees');
  const employees = await res.json();
  table.innerHTML = '';
  employees.forEach((e, index) => {
    table.innerHTML += `
      <tr>
        <td>${index + 1}</td>
        <td>${e.id}</td>
        <td>${e.name}</td>
        <td>${e.email}</td>
        <td>${e.department}</td>
        <td><button class="deleteBtn" onclick="deleteEmployee(${e.id})">Delete</button></td>
      </tr>`;
  });
}

async function deleteEmployee(id) {
  await fetch(`http://localhost:8081/api/employees/${id}`, {
    method: 'DELETE'
  });
  loadEmployees();
}

loadEmployees();
