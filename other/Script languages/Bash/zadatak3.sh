for i in $(ls localhost_access_log.*-02-*.txt);
do 
	datum="$(echo $i | cut -d "-" -f 3 | cut -d "." -f 1 )-$(echo $i | cut -d "-" -f 2)-$(echo $i | cut -d "." -f 2 | cut -d "-" -f 1) "
	echo $datum
	echo "---------------------------------------------------"
	cat $i | cut -d "\"" -f 2 | sort | uniq -c | sort -n -r | sed -r 's/([0-9]+)(.*)/\1 : \2/'
done
	