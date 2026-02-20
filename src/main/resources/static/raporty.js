function pobierzBilans() {
    const dateVal = document.getElementById('selectDate').value;
    if (!dateVal) return;

    const [year, month] = dateVal.split('-');

    fetch(`/api/raporty/bilans?year=${year}&month=${month}`)
        .then(res => res.json())
        .then(data => {
            // 1. Ustawiamy tabelę i harmonogram
            const tbody = document.getElementById('bilansTableBody');
            const harmonogramDiv = document.getElementById('listaHarmonogram');

            tbody.innerHTML = '';
            if (harmonogramDiv) harmonogramDiv.innerHTML = '';

            let totalIncomes = 0;
            let totalExpenses = 0;


 // 2. Robimy Harmonogram
 const dzis = new Date();
 const wydatki = data
     .filter(item => item.typ === 'WYDATEK')
     .sort((a, b) => new Date(a.termin) - new Date(b.termin));

 wydatki.forEach(exp => {
     const terminData = new Date(exp.termin);
     const roznicaDni = Math.ceil((terminData - dzis) / (1000 * 60 * 60 * 24));

     // Sprawdzamy warunek: status "OCZEKUJE" (lub Twój odpowiednik) i termin < 5 dni
     // Upewnij się, że napis 'Oczekuje' zgadza się z tym, co masz w bazie/tabeli status
     const czyPilne = (exp.statusOpis === 'Oczekuje' && roznicaDni <= 5) || (exp.statusOpis === 'Spóźnione');

     const div = document.createElement('div');
     div.style.marginBottom = "10px"; // Mały odstęp



//     div.innerHTML = `
//         <p>
//             <strong>${exp.termin}</strong> - ${exp.nazwa}: ${exp.kwota.toFixed(2)} zł
//             <span class="status-pill">${exp.statusOpis}</span>
//             ${czyPilne ? `<button onclick="wyslijPrzypomnienie('${exp.nazwa}', '${exp.termin}', ${exp.kwota})">Wyślij przypomnienie</button>` : ''}
//         </p>
//     `;

div.innerHTML = `
    <p>
        <strong>${exp.termin}</strong> - ${exp.nazwa}: ${exp.kwota.toFixed(2)} zł
        <span class="status-pill">${exp.statusOpis}</span>
        ${czyPilne ? `<button onclick="wyslijPrzypomnienie('${exp.nazwa}', '${exp.termin}', ${exp.kwota})">Wyślij przypomnienie</button>` : ''}
    </p>
`;
     if (harmonogramDiv) harmonogramDiv.appendChild(div);
 });

            // 3. Robimy Główną Tabelę i liczymy sumy
            data.forEach(item => {
                const isIncome = item.typ === 'PRZYCHOD';
                if (isIncome) totalIncomes += item.kwota;
                else totalExpenses += item.kwota;

                const tr = document.createElement('tr');
                tr.innerHTML = `
                    <td class="${isIncome ? 'typ-przychod' : 'typ-wydatek'}">
                        ${isIncome ? '[+] PRZYCHÓD' : '[-] WYDATEK'}
                    </td>
                    <td>${item.nazwa}</td>
                    <td>${item.kwota.toFixed(2)} zł</td>
                    <td>${item.termin}</td>
                    <td><span class="status-pill">${item.statusOpis}</span></td>
                `;
                tbody.appendChild(tr);
            });

            // 4. Aktualizacja Dashboardu (tych kart na górze)
            const balance = totalIncomes - totalExpenses;
            document.getElementById('sumIncomes').textContent = totalIncomes.toFixed(2) + " zł";
            document.getElementById('sumExpenses').textContent = totalExpenses.toFixed(2) + " zł";

            const balElement = document.getElementById('finalBalance');
            balElement.textContent = balance.toFixed(2) + " zł";
            balElement.style.color = balance >= 0 ? 'green' : 'red';
        })
        .catch(err => console.error("Błąd ładowania bilansu:", err));
}

function wyslijRaport() {
    const email = document.getElementById('emailOdbiorcy').value;
    const dateVal = document.getElementById('selectDate').value;

    if (!email || !dateVal) {
        alert("Wpisz e-mail i wybierz datę!");
        return;
    }

    const [year, month] = dateVal.split('-');
    const params = new URLSearchParams();
    params.append('year', year);
    params.append('month', month);
    params.append('email', email);

    fetch('/api/raporty/wyslij-email', {
        method: 'POST',
        body: params,
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
    })
    .then(res => {
        if (res.ok) alert("Raport wysłany na maila!");
        else alert("Błąd wysyłki.");
    })
    .catch(err => console.error(err));
}

// Uruchom przy starcie strony
document.addEventListener('DOMContentLoaded', pobierzBilans);



function wyslijPrzypomnienie(nazwa, termin, kwota) {
    // 1. Pobieramy maila z Twojego nowego pola
    const email = document.getElementById('emailPrzypomnienie').value;

    if (!email) {
        alert("Wpisz e-mail w polu przypomnienia!");
        return;
    }

    // 2. Pakujemy dane o tym konkretnym wydatku
    const params = new URLSearchParams();
    params.append('email', email);
    params.append('nazwa', nazwa);
    params.append('termin', termin);
    params.append('kwota', kwota);

    // 3. Strzał do Twojego backendu
    fetch('/api/raporty/wyslij-przypomnienie', {
        method: 'POST',
        body: params,
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
    })
    .then(res => {
        if (res.ok) alert("Przypomnienie o " + nazwa + " wysłane!");
        else alert("Błąd wysyłki przypomnienia.");
    })
    .catch(err => console.error("Błąd sieci:", err));
}