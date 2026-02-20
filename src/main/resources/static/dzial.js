const apiUrl = '/api/dzial';
let currentPage = 0;
let pageSize = 5;
let currentSearch = '';

// 🔹 Funkcja pobierająca działy z backendu
function fetchDzialy() {
    let url = `${apiUrl}?page=${currentPage}&size=${pageSize}`;
    if (currentSearch) url += `&nazwa=${encodeURIComponent(currentSearch)}`;

    fetch(url)
        .then(res => res.json())
        .then(data => {
            const tbody = document.getElementById('dzialList');
            tbody.innerHTML = '';
            data.content.forEach(dzial => {
                const tr = document.createElement('tr');
                tr.innerHTML = `
                    <td>${dzial.id}</td>
                    <td>${dzial.nazwa}</td>
                    <td>
                        <button onclick="editDzial(${dzial.id}, '${dzial.nazwa}')">Edytuj</button>
                        <button onclick="deleteDzial(${dzial.id})">Usuń</button>
                    </td>
                `;
                tbody.appendChild(tr);
            });

            document.getElementById('pageInfo').textContent = `Strona ${data.number + 1} z ${data.totalPages}`;
        });
}

// 🔹 Dodawanie działu
document.getElementById('addDzailForm').addEventListener('submit', function(e) {
    e.preventDefault();
    const nazwa = document.getElementById('nazwa').value;

    fetch(apiUrl, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({ nazwa })
    }).then(() => {
        this.reset();
        fetchDzialy();
    });
});

// 🔹 Edycja działu
function editDzial(id, nazwa) {
    document.getElementById("editBox").style.display = "block";
    document.getElementById('editId').value = id;
    document.getElementById('editNazwa').value = nazwa;
}

document.getElementById('editDzailForm').addEventListener('submit', function(e) {
    e.preventDefault();
    const id = document.getElementById('editId').value;
    const nazwa = document.getElementById('editNazwa').value;

    fetch(`${apiUrl}/${id}`, {
        method: 'PUT',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({ nazwa })
    }).then(() => {
        document.getElementById('editBox').style.display = "none";
        fetchDzialy();
    });
});

document.getElementById("cancelEdit").addEventListener("click", () => {
    document.getElementById("editBox").style.display = "none";
});

// 🔹 Usuwanie działu
function deleteDzial(id) {
    if (!confirm("Czy na pewno chcesz usunąć ten dział?")) return;

    fetch(`${apiUrl}/${id}`, { method: 'DELETE' })
        .then(() => fetchDzialy());
}

// 🔹 Wyszukiwanie
document.getElementById('searchForm').addEventListener('submit', function(e) {
    e.preventDefault();
    currentSearch = document.getElementById('searchNazwa').value;
    currentPage = 0;
    fetchDzialy();
});

// 🔹 Paginacja
document.getElementById('prevPage').addEventListener('click', () => {
    if (currentPage > 0) currentPage--;
    fetchDzialy();
});

document.getElementById('nextPage').addEventListener('click', () => {
    currentPage++;
    fetchDzialy();
});

// 🔹 Wywołanie przy starcie
fetchDzialy();
