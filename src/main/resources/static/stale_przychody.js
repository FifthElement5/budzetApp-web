const apiUrl = '/api/stale-przychody';
let currentPage = 0;
let pageSize = 5;
let currentSearch = '';

function fetchSPrzychody() {
    let url = `${apiUrl}?page=${currentPage}&size=${pageSize}`;
    if (currentSearch) url += `&nazwa=${encodeURIComponent(currentSearch)}`;

    fetch(url)
        .then(res => res.json())
        .then(data => {
            const tbody = document.getElementById('sPrzychodyList');
            tbody.innerHTML = '';
            data.content.forEach(p => {
                const tr = document.createElement('tr');
                tr.innerHTML = `
                    <td>${p.id}</td>
                    <td>${p.nazwa}</td>
                    <td>${p.kwota}</td>
                    <td>${p.termin}</td>
                    <td>${p.dataWplywu || '-'}</td>
                    <td>
                        <button onclick="editSPrzychody(${p.id}, ${p.idUzytkownika}, '${p.nazwa}', ${p.kwota}, '${p.termin}', '${p.dataWplywu || ''}', ${p.idStatus})">Edytuj</button>
                        <button onclick="deleteSPrzychody(${p.id})">Usuń</button>
                    </td>
                `;
                tbody.appendChild(tr);
            });
            document.getElementById('pageInfo').textContent = `Strona ${data.number + 1} z ${data.totalPages}`;
        });
}

document.getElementById('addSPrzychodyForm').addEventListener('submit', function(e) {
    e.preventDefault();
    const body = {
        idUzytkownika: document.getElementById('idUzytkownika').value,
        idKategorii: document.getElementById('idKategorii').value,
        idDzial: document.getElementById('idDzial').value || null,
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
    }).then(() => { this.reset(); fetchSPrzychody(); });
});

function editSPrzychody(id, idUzyt, nazwa, kwota, termin, dataWp, idStat) {
    document.getElementById("editBox").style.display = "block";
    document.getElementById('editId').value = id;
    document.getElementById('editIdUzytkownika').value = idUzyt;
    document.getElementById('editNazwa').value = nazwa;
    document.getElementById('editKwota').value = kwota;
    document.getElementById('editTermin').value = termin;
    document.getElementById('editDataWplywu').value = dataWp;
    document.getElementById('editIdStatus').value = idStat;
}

document.getElementById('editSPrzychodyForm').addEventListener('submit', function(e) {
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
    }).then(() => { document.getElementById('editBox').style.display = 'none'; fetchSPrzychody(); });
});

document.getElementById("cancelEdit").addEventListener("click", () => {
    document.getElementById("editBox").style.display = "none";
});

function deleteSPrzychody(id) {
    if (!confirm("Czy na pewno chcesz usunąć ten stały przychód?")) return;
    fetch(`${apiUrl}/${id}`, { method: 'DELETE' }).then(() => fetchSPrzychody());
}

document.getElementById('searchForm').addEventListener('submit', function(e) {
    e.preventDefault();
    currentSearch = document.getElementById('searchNazwa').value;
    currentPage = 0;
    fetchSPrzychody();
});

document.getElementById('prevPage').addEventListener('click', () => {
    if (currentPage > 0) { currentPage--; fetchSPrzychody(); }
});

document.getElementById('nextPage').addEventListener('click', () => {
    currentPage++;
    fetchSPrzychody();
});

fetchSPrzychody();