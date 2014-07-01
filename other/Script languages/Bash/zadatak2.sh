grep -i -E "banana|jabuka|jagoda|dinja|lubenica" namirnice.txt
grep -i -v -E "banana|jabuka|jagoda|dinja|lubenica" namirnice.txt > ne-voce.txt
grep -E -R '\<[[:upper:]]{3}[[:digit:]]{6}\>' ~/projekti/
find ~/ -mtime -14 -mtime +7 -ls
for i in $(seq 1 15); do echo "Broj $i"; done
brojac=0;
for file in $(ls *.*);
	do if [ -f "$file" ]
	then 
		brojac=$(($brojac+1));
		echo "$brojac : $file ... $(wc -l $file|cut -d " " -f 1) redaka";
		echo "----------------------------";
		head -n 3 $file;
		echo;
	fi
done
