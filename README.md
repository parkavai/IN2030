# IN2030
Prosjektoppgave i programmering

Kommandoer

Ant:
Build.XML-filen må være i samme mappe som den du jobber med for å kunne lage referanseinterpreten "asp.jar"

Kjøring av programmet:
For å kjøre programmet, må du kjøre kommandoen: java -jar asp.jar mini.asp 

Viktig:
Referanseinterpreten heter "asp.jar". Dette er da interpreten som du skal tilsvarende lage. Men 
den interpreten som du skal lage, heter også "asp.jar" og fåås gjennom å kjøre kommandoen "ant". 
Det er viktig at "Build.xml" er inn i mappen som du skal kjøre "ant-filen" med. Men poenget er at 
hvis du skal teste hvordan din interpret fungerer, kjør da "ant" og da skal du få din "asp.jar" fil 
som er da din referanseinterpret. For at du skal teste om scanneren din opptrer som den skal f.eks,
så kan du kjøre følgende kommando:

java -jar asp.jar -testscanner "TESTPROGRAM".asp
more "TESTPROGRAM".log

Merk "TESTPROGRAM" er kun en plassholder for navnet til ditt testprogram.
Da kan du sjekke om scanneren din gjør som den skal. Du burde også kjøre den faktiske referanseinterpreten med 
samme testprogram og sammenlikne om loggen er lik for da har du gjort det helt riktig. Men ja kommentarene over 
er kun ment for å forklare deg hvordan du skal teste om de ulike "delene" som du implementerer, faktisk fungerer. 

Hvis du fremdeles trenger hjelp, se forelesningsopptaket for 28.08.2022, hvor Dag snakker om Scanneren og rett og 
slett hvordan du skal begynne med prosjektet.
