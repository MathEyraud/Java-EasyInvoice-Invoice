// Récupère les données de l'endpoint '../invoice'
fetch('/invoice')
    .then(res => res.json()) // Convertit la réponse en JSON
    .then(res => {

        // Obtient l'élément HTML avec l'ID 'invoice-list'
        var invoiceListNode = document.getElementById('invoice-list');

        // Efface tout contenu existant dans l'élément 'invoice-list'
        invoiceListNode.innerHTML = "";

        // Crée un nouvel élément de tableau
        var table = document.createElement("table");

        // Définit l'attribut border du tableau à 1
        table.setAttribute("border", "1");

        // Ajoute le tableau à l'élément 'invoice-list'
        invoiceListNode.appendChild(table);

        // Parcourt la liste des factures reçues de l'API
        res.forEach(invoice => {

            // Crée une nouvelle ligne de tableau
            var tr = document.createElement("tr");

            // Ajoute la ligne au tableau
            table.appendChild(tr);

            // Crée une nouvelle cellule de tableau pour le numéro de facture
            var td = document.createElement("td");

            // Crée un nœud de texte avec le numéro de la facture
            var text = document.createTextNode(`${invoice.number}`);

            // Ajoute le nœud de texte à la cellule de tableau
            td.appendChild(text);

            // Ajoute la cellule de tableau à la ligne
            tr.appendChild(td);

            // Crée une nouvelle cellule de tableau pour le nom du client
            td = document.createElement("td");

            // Crée un nœud de texte avec le nom du client
            text = document.createTextNode(`${invoice.customer.name}`);

            // Ajoute le nœud de texte à la cellule de tableau
            td.appendChild(text);

            // Ajoute la cellule de tableau à la ligne
            tr.appendChild(td);

            // Crée une nouvelle cellule de tableau pour le bouton 'Détails'
            td = document.createElement("td");

            // Crée un nouvel élément de bouton
            var button = document.createElement("button");

            // Définit l'attribut type du bouton à 'button'
            button.setAttribute("type", "button");

            // Définit l'événement onclick du bouton pour appeler la fonction showDetail avec le numéro de la facture
            button.onclick = function () {
                showDetail(`${invoice.number}`);
            };

            // Crée un nœud de texte avec le label 'Détails' pour le bouton
            text = document.createTextNode("Détails");

            // Ajoute le nœud de texte au bouton
            button.appendChild(text);

            // Ajoute le bouton à la cellule de tableau
            td.appendChild(button);

            // Ajoute la cellule de tableau à la ligne
            tr.appendChild(td);
        });
    });

// Fonction pour afficher les détails d'une facture spécifique
function showDetail(invoiceNumber) {

    // Récupère les données de l'endpoint pour le numéro de facture spécifique
    fetch(invoiceNumber)
        .then(res => res.json()) // Convertit la réponse en JSON
        .then(res => {

            // Obtient l'élément HTML avec l'ID 'invoice-detail'
            var invoiceDetailNode = document.getElementById('invoice-detail');
            // Efface tout contenu existant dans l'élément 'invoice-detail'
            invoiceDetailNode.innerHTML = "";

            // Crée un nouveau paragraphe pour le numéro de facture
            var p = document.createElement("p");
            // Crée un nœud de texte avec le numéro de la facture
            var text = document.createTextNode(`Numéro : ${res.number}`);
            // Ajoute le nœud de texte au paragraphe
            p.appendChild(text);
            // Ajoute le paragraphe à l'élément 'invoice-detail'
            invoiceDetailNode.appendChild(p);

            // Crée un nouveau paragraphe pour le nom du client
            p = document.createElement("p");
            // Crée un nœud de texte avec le nom du client
            text = document.createTextNode(`Nom du client : ${res.customer.name}`);
            // Ajoute le nœud de texte au paragraphe
            p.appendChild(text);
            // Ajoute le paragraphe à l'élément 'invoice-detail'
            invoiceDetailNode.appendChild(p);

            // Crée un nouveau paragraphe pour le numéro de commande
            p = document.createElement("p");
            // Crée un nœud de texte avec le numéro de commande
            text = document.createTextNode(`Numéro de commande : ${res.orderNumber}`);
            // Ajoute le nœud de texte au paragraphe
            p.appendChild(text);
            // Ajoute le paragraphe à l'élément 'invoice-detail'
            invoiceDetailNode.appendChild(p);

            // Crée un nouveau paragraphe pour les détails du client
            p = document.createElement("p");
            // Crée un nœud de texte avec le pays du client
            text = document.createTextNode(`Pays du client : ${res.customer.address.country}`);
            // Ajoute le nœud de texte au paragraphe
            p.appendChild(text);
            // Ajoute le paragraphe à l'élément 'invoice-detail'
            invoiceDetailNode.appendChild(p);
        });
}
