if [ "$#" -ne 2 ];
then
echo "Broj ulaznih parametara mora biti 2!"
exit 1;
fi
if [ ! -d "$1" ];
then
echo "Nema izvornog kazala ili nije direktorij!";
exit 1;
fi
if [ ! -e "$2" ];
then
mkdir "./$2";
fi
for i in $(ls "./$1")
do 
naziv=$(stat -c %y "./$1/$i" |cut -d "-" -f 1,2)
if [ ! -e "$2/$naziv" ]; then mkdir "$2/$naziv"; fi
mv "./$1/$i" "./$2/$naziv/$i";
done