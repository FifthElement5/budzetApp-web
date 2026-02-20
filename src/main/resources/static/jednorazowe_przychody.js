const apiUrl = '/api/jednorazowe-przychody';
let currentPage = 0;
let pageSize = 5;
let currentSearch = '';

// Pobieranie danych
function fetchJPrzychody() {
    let url = `${apiUrl}?page=${currentPage}&size=${pageSize}`;
    if (currentSearch) url += `&nazwa=${encodeURIComponent(currentSearch)}`;

    fetch(url)
        .then(res => res.json())
        .then(data => {
            const tbody = document.getElementById('jPrzychodyList');
            tbody.innerHTML = '';
            data.content.forEach(p => {
                const tr = document.createElement('tr');
                tr.innerHTML = `
                    <td>${p.id}</td>
                    <td>${p.nazwa}</td>
                    <td>${p.kwota}</td>
                    <td>${p.termin}</td>
                    <td>${p.dataWplywu || '-'}</td>
                    <td>${p.idStatus}</td>
                    <td>
                        <button onclick="editJPrzychody(${p.id}, ${p.idUzytkownika}, '${p.nazwa}', ${p.kwota}, '${p.termin}', '${p.dataWplywu || ''}', ${p.idStatus})">Edytuj</button>
                        <button onclick="deleteJPrzychody(${p.id})">Usu\u0144</button>
                    </td>
                `;
                tbody.appendChild(tr);
            });
            document.getElementById('pageInfo').textContent = `Strona ${data.number + 1} z ${data.totalPages}`;
        });
}

// Dodawanie
document.getElementById('addJPrzychodyForm').addEventListener('submit', function(e) {
    e.preventDefault();
    const body = {
        idUzytkownika: document.getElementById('idUzytkownika').value,
        idKategorii: document.getElementById('idKategorii').value,
        idDzial: document.getElementById('idDzial').value,
        nazwa: document.getElementById('nazwa').value,
        kwota: document.getElementById('kwota').value,
        termin: document.getElementById('termin').value,
        dataWplywu: document.getElementById('dataWplywu').value || null,
        idStatus: document.getElementById('idStatus').value
    };

    fetch(apiUrl, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(body)
    }).then(() => {
        this.reset();
        fetchJPrzychody();
    });
});

// Edycja - otwarcie okna
function editJPrzychody(id, idUzyt, nazwa, kwota, termin, dataWp, idStat) {
    document.getElementById("editBox").style.display = "block";
    document.getElementById('editId').value = id;
    document.getElementById('editIdUzytkownika').value = idUzyt;
    document.getElementById('editNazwa').value = nazwa;
    document.getElementById('editKwota').value = kwota;
    document.getElementById('editTermin').value = termin;
    document.getElementById('editDataWplywu').value = dataWp;
    document.getElementById('editIdStatus').value = idStat;
}

// Edycja - zapis
document.getElementById('editJPrzychodyForm').addEventListener('submit', function(e) {
    e.preventDefault();
    const id = document.getElementById('editId').value;
    const body = {
        idUzytkownika: document.getElementById('editIdUzytkownika').value,
        nazwa: document.getElementById('editNazwa').value,
        kwota: document.getElementById('editKwota').value,
        termin: document.getElementById('editTermin').value,
        dataWplywu: document.getElementById('editDataWplywu').value || null,
        idStatus: document.getElementById('editIdStatus').value
    };

    fetch(`${apiUrl}/${id}`, {
        method: 'PUT',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(body)
    }).then(() => {
        document.getElementById('editBox').style.display = 'none';
        fetchJPrzychody();
    });
});

document.getElementById("cancelEdit").addEventListener("click", () => {
    document.getElementById("editBox").style.display = "none";
});

// Usuwanie
function deleteJPrzychody(id) {
    if (!confirm("Usun\u0105\u0107 ten przych\u00f3d?")) return;
    fetch(`${apiUrl}/${id}`, { method: 'DELETE' }).then(() => fetchJPrzychody());
}

// Szukanie i Paginacja
document.getElementById('searchForm').addEventListener('submit', function(e) {
    e.preventDefault();
    currentSearch = document.getElementById('searchNazwa').value;
    currentPage = 0;
    fetchJPrzychody();
});

document.getElementById('prevPage').addEventListener('click', () => {
    if (currentPage > 0) { currentPage--; fetchJPrzychody(); }
});

document.getElementById('nextPage').addEventListener('click', () => {
    currentPage++;
    fetchJPrzychody();
});

fetchJPrzychody();