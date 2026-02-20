const apiUrl = '/api/jednorazowe-wydatki';
let currentPage = 0;
let pageSize = 5;
let currentSearch = '';

function fetchJWydatki() {
    let url = `${apiUrl}?page=${currentPage}&size=${pageSize}`;
    if (currentSearch) url += `&nazwa=${encodeURIComponent(currentSearch)}`;

    fetch(url)
        .then(res => res.json())
        .then(data => {
            const tbody = document.getElementById('jWydatkiList');
            tbody.innerHTML = '';
            data.content.forEach(w => {
                const tr = document.createElement('tr');
                tr.innerHTML = `
                    <td>${w.id}</td>
                    <td>${w.nazwa}</td>
                    <td>${w.kwota}</td>
                    <td>${w.termin}</td>
                    <td>${w.dataZaplaty || '-'}</td>
                    <td>
                        <button onclick="editJWydatki(${w.id}, ${w.idUzytkownika}, '${w.nazwa}', ${w.kwota}, '${w.termin}', '${w.dataZaplaty || ''}', ${w.idStatus})">Edytuj</button>
                        <button onclick="deleteJWydatki(${w.id})">Usuń</button>
                    </td>
                `;
                tbody.appendChild(tr);
            });
            document.getElementById('pageInfo').textContent = `Strona ${data.number + 1} z ${data.totalPages}`;
        });
}

document.getElementById('addJWydatkiForm').addEventListener('submit', function(e) {
    e.preventDefault();
    const body = {
        idUzytkownika: document.getElementById('idUzytkownika').value,
        idKategorii: document.getElementById('idKategorii').value,
        idDzial: document.getElementById('idDzial').value,
        nazwa: document.getElementById('nazwa').value,
        kwota: document.getElementById('kwota').value,
        termin: document.getElementById('termin').value,
        dataZaplaty: document.getElementById('dataZaplaty').value || null,
        idStatus: document.getElementById('idStatus').value
    };

    fetch(apiUrl, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(body)
    }).then(() => { this.reset(); fetchJWydatki(); });
});

function editJWydatki(id, idUzyt, nazwa, kwota, termin, dataZap, idStat) {
    document.getElementById("editBox").style.display = "block";
    document.getElementById('editId').value = id;
    document.getElementById('editIdUzytkownika').value = idUzyt;
    document.getElementById('editNazwa').value = nazwa;
    document.getElementById('editKwota').value = kwota;
    document.getElementById('editTermin').value = termin;
    document.getElementById('editDataZaplaty').value = dataZap;
    document.getElementById('editIdStatus').value = idStat;
}

document.getElementById('editJWydatkiForm').addEventListener('submit', function(e) {
    e.preventDefault();
    const id = document.getElementById('editId').value;
    const body = {
        idUzytkownika: document.getElementById('editIdUzytkownika').value,
        nazwa: document.getElementById('editNazwa').value,
        kwota: document.getElementById('editKwota').value,
        termin: document.getElementById('editTermin').value,
        dataZaplaty: document.getElementById('editDataZaplaty').value || null,
        idStatus: document.getElementById('editIdStatus').value
    };

    fetch(`${apiUrl}/${id}`, {
        method: 'PUT',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(body)
    }).then(() => { document.getElementById('editBox').style.display = 'none'; fetchJWydatki(); });
});

document.getElementById("cancelEdit").addEventListener("click", () => {
    document.getElementById("editBox").style.display = "none";
});

function deleteJWydatki(id) {
    if (!confirm("Usunąć ten wydatek?")) return;
    fetch(`${apiUrl}/${id}`, { method: 'DELETE' }).then(() => fetchJWydatki());
}

document.getElementById('searchForm').addEventListener('submit', function(e) {
    e.preventDefault();
    currentSearch = document.getElementById('searchNazwa').value;
    currentPage = 0;
    fetchJWydatki();
});

document.getElementById('prevPage').addEventListener('click', () => {
    if (currentPage > 0) { currentPage--; fetchJWydatki(); }
});

document.getElementById('nextPage').addEventListener('click', () => {
    currentPage++;
    fetchJWydatki();
});

fetchJWydatki();