if [ "$#" -ne 2 ]
then
	echo "Potrebna su dva parametra!"
	exit 1
fi
if [ ! -e "$1" ]
then
	echo "Datoteka s tim imenom ne postoji."
	exit 1
fi
if [ ! -e "$2" ]
then
	echo "Datoteka s tim imenom ne postoji."
	exit 1
fi
echo "JMBAG		Prezime	Ime	(grupa)	Broj_bodova_1 Broj_bodova_2"
while read JMBAG2 Broj_bodova_2
do
	redak1="$(grep "$JMBAG2" "$1")"
	if [ $? -ne 0 ]
	then
		echo "${JMBAG2}	 -- 		--  	(--) 	--		${Broj_bodova_2}"
	else
	
		Prezime=$(echo $redak1|cut -d ";" -f 2|cut -d "," -f 1)
		Ime=$(echo $redak1|cut -d ";" -f 2|cut -d "," -f 2)
		Broj_bodova_1=$(echo $redak1|cut -d ";" -f 3)
		grupa=$(echo $redak1|cut -d ";" -f 4)
		echo "$JMBAG2 $Prezime	$Ime ($grupa) 	${Broj_bodova_1}  ${Broj_bodova_2}"
	fi	
		
		
done < "$2"