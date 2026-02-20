const apiUrl = '/api/uzytkownicy';
let currentPage = 0;       // aktualna strona
let pageSize = 5;          // ile rekordów na stronę
let currentSearch = '';    // przechowuje nazwisko do wyszukiwania

// 🔹 Funkcja pobierająca użytkowników z backendu

function fetchUsers() {
    let url = `${apiUrl}/search?page=${currentPage}&size=${pageSize}`;
    if (currentSearch) url += `&nazwisko=${encodeURIComponent(currentSearch)}`;

    fetch(url)
        .then(res => res.json())
        .then(data => {
            const tbody = document.getElementById('userList');
            tbody.innerHTML = '';
            data.content.forEach(user => {  // content – Spring Page wrapper
                const tr = document.createElement('tr');
                tr.innerHTML = `
                    <td>${user.id}</td>
                    <td>${user.imie}</td>
                    <td>${user.nazwisko}</td>
                    <td>${user.email}</td>
                    <td>
                        <button onclick="editUser(${user.id}, '${user.imie}', '${user.nazwisko}', '${user.email}')">Edytuj</button>
                        <button onclick="deleteUser(${user.id})">Usuń</button>
                    </td>
                `;
                tbody.appendChild(tr);
            });

            document.getElementById('pageInfo').textContent = `Strona ${data.number + 1} z ${data.totalPages}`;
        });
}

// 🔹 Dodawanie użytkownika
document.getElementById('addUserForm').addEventListener('submit', function(e) {
    e.preventDefault();
    const imie = document.getElementById('imie').value;
    const nazwisko = document.getElementById('nazwisko').value;
    const email = document.getElementById('email').value;

    fetch(apiUrl, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({ imie, nazwisko, email })
    }).then(() => {
        this.reset();
        fetchUsers();
    });
});

// 🔹 Edycja użytkownika
function editUser(id, imie, nazwisko, email) {
    const form = document.getElementById('editUserForm');
    document.getElementById("editBox").style.display = "block";

    document.getElementById('editId').value = id;
    document.getElementById('editImie').value = imie;
    document.getElementById('editNazwisko').value = nazwisko;
    document.getElementById('editEmail').value = email;
}

document.getElementById('editUserForm').addEventListener('submit', function(e) {
    e.preventDefault();
    const id = document.getElementById('editId').value;
    const imie = document.getElementById('editImie').value;
    const nazwisko = document.getElementById('editNazwisko').value;
    const email = document.getElementById('editEmail').value;

    fetch(`${apiUrl}/${id}`, {
        method: 'PUT',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({ imie, nazwisko, email })
    }).then(() => {
        document.getElementById('editUserForm').style.display = 'none';
        fetchUsers();
    });
});

document.getElementById("cancelEdit").addEventListener("click", () => {
    document.getElementById("editBox").style.display = "none";
});



function deleteUser(id) {

    if (!confirm("Czy na pewno chcesz usunąć tego użytkownika?")) {
        return;
    }

    fetch(`${apiUrl}/${id}`, { method: 'DELETE' })
        .then(() => fetchUsers());
}


// 🔹 Wyszukiwanie
document.getElementById('searchForm').addEventListener('submit', function(e) {
    e.preventDefault();
    currentSearch = document.getElementById('searchNazwisko').value;
    currentPage = 0;   // wracamy na pierwszą stronę
    fetchUsers();
});

// 🔹 Paginacja
document.getElementById('prevPage').addEventListener('click', () => {
    if (currentPage > 0) currentPage--;
    fetchUsers();
});

document.getElementById('nextPage').addEventListener('click', () => {
    currentPage++;
    fetchUsers();
});

// 🔹 Wywołanie przy starcie
fetchUsers();
