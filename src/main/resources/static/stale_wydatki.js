const apiUrl = '/api/stale-wydatki';
let currentPage = 0;
let pageSize = 5;
let currentSearch = '';

function fetchSWydatki() {
    let url = `${apiUrl}?page=${currentPage}&size=${pageSize}`;
    if (currentSearch) url += `&nazwa=${encodeURIComponent(currentSearch)}`;

    fetch(url)
        .then(res => res.json())
        .then(data => {
            const tbody = document.getElementById('sWydatkiList');
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
                        <button onclick="editSWydatki(${w.id}, ${w.idUzytkownika}, '${w.nazwa}', ${w.kwota}, '${w.termin}', '${w.dataZaplaty || ''}', ${w.idStatus})">Edytuj</button>
                        <button onclick="deleteSWydatki(${w.id})">Usuń</button>
                    </td>
                `;
                tbody.appendChild(tr);
            });
            document.getElementById('pageInfo').textContent = `Strona ${data.number + 1} z ${data.totalPages}`;
        });
}

document.getElementById('addSWydatkiForm').addEventListener('submit', function(e) {
    e.preventDefault();
    const body = {
        idUzytkownika: document.getElementById('idUzytkownika').value,
        idKategorii: document.getElementById('idKategorii').value,
        idDzial: document.getElementById('idDzial').value || null,
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
    }).then(() => { this.reset(); fetchSWydatki(); });
});

function editSWydatki(id, idUzyt, nazwa, kwota, termin, dataZap, idStat) {
    document.getElementById("editBox").style.display = "block";
    document.getElementById('editId').value = id;
    document.getElementById('editIdUzytkownika').value = idUzyt;
    document.getElementById('editNazwa').value = nazwa;
    document.getElementById('editKwota').value = kwota;
    document.getElementById('editTermin').value = termin;
    document.getElementById('editDataZaplaty').value = dataZap;
    document.getElementById('editIdStatus').value = idStat;
}

document.getElementById('editSWydatkiForm').addEventListener('submit', function(e) {
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
    }).then(() => { document.getElementById('editBox').style.display = 'none'; fetchSWydatki(); });
});

document.getElementById("cancelEdit").addEventListener("click", () => {
    document.getElementById("editBox").style.display = "none";
});

function deleteSWydatki(id) {
    if (!confirm("Czy na pewno chcesz usunąć ten stały wydatek?")) return;
    fetch(`${apiUrl}/${id}`, { method: 'DELETE' }).then(() => fetchSWydatki());
}

document.getElementById('searchForm').addEventListener('submit', function(e) {
    e.preventDefault();
    currentSearch = document.getElementById('searchNazwa').value;
    currentPage = 0;
    fetchSWydatki();
});

document.getElementById('prevPage').addEventListener('click', () => {
    if (currentPage > 0) { currentPage--; fetchSWydatki(); }
});

document.getElementById('nextPage').addEventListener('click', () => {
    currentPage++;
    fetchSWydatki();
});

fetchSWydatki();