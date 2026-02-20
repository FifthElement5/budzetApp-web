const apiUrl = '/api/kategorie';
let currentPage = 0;
let pageSize = 5;
let currentSearch = '';

function fetchKategorie() {
    let url = `${apiUrl}?page=${currentPage}&size=${pageSize}`;
    if (currentSearch) url += `&nazwa=${encodeURIComponent(currentSearch)}`;

    fetch(url)
        .then(res => res.json())
        .then(data => {
            const tbody = document.getElementById('kategorieList');
            tbody.innerHTML = '';

            data.content.forEach(k => {
                const tr = document.createElement('tr');
                tr.innerHTML = `
                    <td>${k.id}</td>
                    <td>${k.nazwa}</td>
                    <td>
                        <button onclick="editKategoria(${k.id}, '${k.nazwa}')">Edytuj</button>
                        <button onclick="deleteKategoria(${k.id})">Usuń</button>
                    </td>
                `;
                tbody.appendChild(tr);
            });

            document.getElementById('pageInfo').textContent =
                `Strona ${data.number + 1} z ${data.totalPages}`;
        });
}

document.getElementById('addKategoriaForm').addEventListener('submit', function(e) {
    e.preventDefault();
    const nazwa = document.getElementById('nazwa').value;

    fetch(apiUrl, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({ nazwa })
    }).then(() => {
        this.reset();
        fetchKategorie();
    });
});

function editKategoria(id, nazwa) {
    document.getElementById("editBox").style.display = "block";
    document.getElementById('editId').value = id;
    document.getElementById('editNazwa').value = nazwa;
}

document.getElementById('editKategoriaForm').addEventListener('submit', function(e) {
    e.preventDefault();
    const id = document.getElementById('editId').value;
    const nazwa = document.getElementById('editNazwa').value;

    fetch(`${apiUrl}/${id}`, {
        method: 'PUT',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({ nazwa })
    }).then(() => {
        document.getElementById('editBox').style.display = "none";
        fetchKategorie();
    });
});

document.getElementById("cancelEdit").addEventListener("click", () => {
    document.getElementById("editBox").style.display = "none";
});

function deleteKategoria(id) {
    if (!confirm("Czy na pewno chcesz usunąć tę kategorię?")) return;

    fetch(`${apiUrl}/${id}`, { method: 'DELETE' })
        .then(() => fetchKategorie());
}

document.getElementById('searchForm').addEventListener('submit', function(e) {
    e.preventDefault();
    currentSearch = document.getElementById('searchNazwa').value;
    currentPage = 0;
    fetchKategorie();
});

document.getElementById('prevPage').addEventListener('click', () => {
    if (currentPage > 0) currentPage--;
    fetchKategorie();
});

document.getElementById('nextPage').addEventListener('click', () => {
    currentPage++;
    fetchKategorie();
});

fetchKategorie();
