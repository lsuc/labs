if [ $# -ne 2 ]; then
	echo "Broj ulaznih parametara mora biti jednak dva." 1>&2
	exit 1
fi
if [ -f "$1" ]; then
	echo "Prvi parametar mora biti direktorij." 1>&2
	exit 1
fi

if [ -f "$2" ]; then
	echo "Drugi parametar mora biti direktorij." 1>&2
	exit 1
fi
for dat1 in $(ls ./"$1")
do
	#if [ ! -r "${dat1}" ]; then
	#	echo "${dat1} se preskace jer se ne moze procitati." 1>&2
	#	continue
	#fi
	if [ -n "$(find ./"$2" -name ${dat1})" ]; then
			if [ "$(stat -c %Y ./$1/${dat1})" -gt "$(stat -c %Y ./$2/${dat1})" ]; then 
				cp -p "./$1/${dat1}" "./$2/${dat1}"
				echo "$1/${dat1} -> $2"
			fi
	else
			cp -p "./$1/${dat1}" "./$2/${dat1}"
			echo "$1/${dat1} -> $2"
	fi
done
for dat2 in $(ls ./"$2")
do
	#if [ ! -r "${dat2}" ]; then
	#	echo "${dat2} se preskace jer se ne moze procitati." 1>&2
	#	continue;
	#fi
	if [ -n "$(find ./"$1" -name ${dat2})" ]; then
			if [ "$(stat -c %Y ./$2/${dat2})" -gt "$(stat -c %Y ./$1/${dat2})" ]; then 
				cp -p "./$2/${dat2}" "./$1/${dat2}"
				echo "$2/${dat2} -> $1"
			fi
	else
			cp -p "./$2/${dat2}" "./$1/${dat2}"
			echo "$2/${dat2} -> $1"
		fi
done
