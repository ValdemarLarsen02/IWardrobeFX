## IWardrobe af data mikes(Jonas, Micke og Valdemar)

SP4 - ICE - IWardrobe

Funktionelle Krav:

IWardrobe er designet til at forbedre oplevelsen for alle gæster i nattelivet ved sikkert og effektivt at håndtere deres personlige ejendele. Systemet tilbyder en række nøglefunktioner til forbedring af garderobeservice i diverse virksomheder.

1. Kapacitet til Samtidig Håndtering: Systemet er i stand til at håndtere hundredevis af gæster samtidigt uden forsinkelser eller systemnedbrud.

2. Digital Billetudstedelse: Ved indlevering modtager hver gæst en digital billet, der unikt kan matches med deres jakke eller anden værdigenstand.

3. Effektiv Matchning ved Afhentning: Ved afgang sikrer systemet en hurtig og præcis matchning af billet til den korrekte jakke.

4. Brugervenlig Interface: IWardrobe har en intuitiv brugerflade, der er let at anvende både for personalet og gæsterne.

5. Datalevering til Natklubber: Systemet leverer værdifulde data, som kan bruges til at optimere drift og kundeservice i natklubber.

6. Integreret Betalingsløsning: Gæsterne kan betale for garderobetjenesten direkte igennem applikationen, hvilket sikrer en smidig transaktion.

7. Support til Personale: IWardrobe assisterer garderobepersonalet med organisering og effektiv placering af genstande, hvilket minimerer fejl og ventetid.

NAVNEORD LISTE:
Gæster - Customer(class)
Ejendele - attribut(customer - Viser de genstande en kunde har afleveret i garderoben)
Virksomheder - Company(class)
Kapacitet - Capacity(Attribut i company, der viser nuværende mængde jakker/genstande)
Billetudstedelse - TicketHandler(class)
Afhentning - TicketHandler(class)
Interface (JAVAFX)
Personalet - Admin(class)
Drift -  Admin(class)
Kundeservice -CustomerService(class)
Payment (class)
Organisering - Organizer(class)
Placering - Location(class - Finder ud af hvilken natklub kunden er på både når man kommer og forlader)
Ventetid - Statistik(attribut i Admin)
Fejl - ErrorHandler(class)
FileIO (class)
