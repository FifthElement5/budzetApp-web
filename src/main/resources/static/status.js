const apiUrl = '/api/status';
let currentPage = 0;
let pageSize = 5;
let currentSearch = '';

function fetchStatusy() {
    let url = `${apiUrl}?page=${currentPage}&size=${pageSize}`;
    if (currentSearch) url += `&nazwa=${encodeURIComponent(currentSearch)}`;

    fetch(url)
        .then(res => res.json())
        .then(data => {
            const tbody = document.getElementById('statusList');
            tbody.innerHTML = '';

            data.content.forEach(s => {
                const tr = document.createElement('tr');
                tr.innerHTML = `
                    <td>${s.id}</td>
                    <td>${s.nazwa}</td>
                    <td>
                        <button onclick="editStatus(${s.id}, '${s.nazwa}')">Edytuj</button>
                        <button onclick="deleteStatus(${s.id})">Usuń</button>
                    </td>
                `;
                tbody.appendChild(tr);
            });

            document.getElementById('pageInfo').textContent =
                `Strona ${data.number + 1} z ${data.totalPages}`;
        });
}

document.getElementById('addStatusForm').addEventListener('submit', function(e) {
    e.preventDefault();
    const nazwa = document.getElementById('nazwa').value;

    fetch(apiUrl, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({ nazwa })
    }).then(() => {
        this.reset();
        fetchStatusy();
    });
});

function editStatus(id, nazwa) {
    document.getElementById("editBox").style.display = "block";
    document.getElementById('editId').value = id;
    document.getElementById('editNazwa').value = nazwa;
}

document.getElementById('editStatusForm').addEventListener('submit', function(e) {
    e.preventDefault();
    const id = document.getElementById('editId').value;
    const nazwa = document.getElementById('editNazwa').value;

    fetch(`${apiUrl}/${id}`, {
        method: 'PUT',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({ nazwa })
    }).then(() => {
        document.getElementById('editBox').style.display = "none";
        fetchStatusy();
    });
});

document.getElementById("cancelEdit").addEventListener("click", () => {
    document.getElementById("editBox").style.display = "none";
});

function deleteStatus(id) {
    if (!confirm("Czy na pewno chcesz usunąć ten status?")) return;

    fetch(`${apiUrl}/${id}`, { method: 'DELETE' })
        .then(() => fetchStatusy());
}

document.getElementById('searchForm').addEventListener('submit', function(e) {
    e.preventDefault();
    currentSearch = document.getElementById('searchNazwa').value;
    currentPage = 0;
    fetchStatusy();
});

document.getElementById('prevPage').addEventListener('click', () => {
    if (currentPage > 0) currentPage--;
    fetchStatusy();
});

document.getElementById('nextPage').addEventListener('click', () => {
    currentPage++;
    fetchStatusy();
});

fetchStatusy();
