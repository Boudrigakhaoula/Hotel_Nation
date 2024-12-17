# -*- coding: utf-8 -*-
"""
Scraping des hôtels sur TunisieBooking (Tunisie) (sans pagination)
"""
import requests
from bs4 import BeautifulSoup
import pandas as pd
import json

# Liste des URL à scraper (uniquement la première page)
urls_to_scrape = [
    "https://tn.tunisiebooking.com/hotels_sousse.html",
    "https://tn.tunisiebooking.com/hotels_hammamet.html",
    "https://tn.tunisiebooking.com/hotels-tunisie.html",
    "https://tn.tunisiebooking.com/hotels_tabarka.html",
    "https://tn.tunisiebooking.com/hotels_monastir.html",
    "https://tn.tunisiebooking.com/hotels_mahdia.html",
    "https://tn.tunisiebooking.com/hotels_djerba.html",
    "https://tn.tunisiebooking.com/hotels_tunis.html",
    "https://tn.tunisiebooking.com/hotels_tozeur.html",
    "https://tn.tunisiebooking.com/hotels_tozeur.html",
    "https://tn.tunisiebooking.com/hotels_korbous.html",
    "https://tn.tunisiebooking.com/hotels_zarzis.html",
    "https://tn.tunisiebooking.com/hotels_sfax.html",
    "https://tn.tunisiebooking.com/hotels_nabeul.html",
    "https://tn.tunisiebooking.com/hotels_gammarth.html",
    "https://tn.tunisiebooking.com/hotels_douz.html",
    "https://tn.tunisiebooking.com/hotels_bizerte.html",
    "https://tn.tunisiebooking.com/hotels_ain_draham.html",
    "https://tn.tunisiebooking.com/hotels_gabes.html",
    "https://tn.tunisiebooking.com/hotels_sbeitla.html",
    "https://tn.tunisiebooking.com/hotels_el_jem.html",
    "https://tn.tunisiebooking.com/hotels_kairouan.html",
    "https://tn.tunisiebooking.com/hotels_ksar_ghilane.html",
    "https://tn.tunisiebooking.com/hotels_gafsa.html",
    "https://tn.tunisiebooking.com/hotels_tataouine.html",
    "https://tn.tunisiebooking.com/hotels_ksar_ghilane.html"
]

# Initialisation
data = []

# Boucle sur chaque URL
for base_url in urls_to_scrape:
    print(f"Scraping de la région : {base_url}")

    # Extraire la localisation proprement
    location = base_url.split('/')[-1].replace("hotels_", "").replace(".html", "").capitalize()

    # Envoyer une requête GET pour la première page
    response = requests.get(base_url)

    # Vérifier le statut de la réponse
    if response.status_code != 200:
        print(f"Erreur lors de la récupération de la page {base_url}.")
        continue

    # Analyser le contenu HTML
    soup = BeautifulSoup(response.text, "html.parser")

    # Extraire les hôtels
    hotels = soup.findAll("div", class_="un_destination")
    if not hotels:
        print(f"Pas d'hôtels trouvés pour la région {location}.")
        continue

    # Traiter chaque hôtel
    for hotel in hotels:
        name = hotel.find("div", class_="titre-hotel").text.strip() if hotel.find("div", class_="titre-hotel") else "Non spécifié"
        hotel_names = [h["Name"] for h in data]
        if name in hotel_names:
            print(f"Hôtel déjà existant : {name}. Ignoré.")
            continue

        stars = hotel.find("div", class_="adresse3").text.strip() if hotel.find("div", class_="adresse3") else "Non spécifié"
        description = hotel.find("div", class_="options")
        description = description.text.strip() if description else "Non spécifié"
        dispo = hotel.find("div", class_="dispo")
        type_ = dispo.text.strip() if dispo else "Non spécifié"
        prix_div = hotel.find("div", class_="formule")
        price = prix_div.find("span").text.strip().replace("TND", "").replace(",", "").strip() if prix_div else "0"
        image = hotel.find("div", class_="titre-hotel")
        if image and 'onclick' in image.attrs:
            onclick_value = image['onclick']
            # Extraire l'URL de onclick="location.href='URL'"
            url_start = onclick_value.find("'") + 1
            url_end = onclick_value.rfind("'")
            link = onclick_value[url_start:url_end]
        else:
            link = "Non spécifié"

        try:
            price_float = float(price)
        except ValueError:
            price_float = 0

        data.append({
            "Name": name,
            "Price": price_float,
            "Location": location,
            "Stars": stars,
            "Type": type_,
          
            "Link": link
        })

# Vérifier si des données ont été collectées
if data:
    # Sauvegarder dans un fichier JSON
    output_file = "hotels_data_tunisie_booking.json"
    with open(output_file, "w", encoding="utf-8") as json_file:
        json.dump(data, json_file, ensure_ascii=False, indent=4)
    print(f"Scraping terminé. {len(data)} hôtels collectés et sauvegardés dans {output_file}.")
else:
    print("Aucune donnée n'a été collectée.")
