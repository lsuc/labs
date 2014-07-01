#!/usr/bin/perl

if(@ARGV != 1){	
	die "Potrebno je navesti 1 ulaznu datoteku kao argument\n";
}

if(! open DAT,$ARGV[0]){
	die "Datoteka nije uspjesno otvorena\n" ;
}

$_ = <DAT>;
while(m/\#/){
$_ = <DAT>;

}
chomp;


@faktori = split /;/;


while(<DAT>){
	
	if(! m/\#/){
	chomp;
	@stud = split /;/;
	
		if(@stud == (@faktori + 3)){
	
			
			$koefNum = 0;
			$prezIme = $stud[1]. ",".$stud[2];
			$jmbag = $stud[0];
	
			while($koefNum < @faktori){
		
				$kljuc = "$prezIme ($jmbag)";
				$rangStud{$kljuc} = $rangStud{$kljuc} + ($faktori[$koefNum] * $stud[$koefNum + 3]);
				$koefNum += 1;
			
			}	
		}
		else{
			die "Broj koef i komponenata ocjena nije jednak\n";
		}	
	}
}

print "Lista po rangu:\n";
print "------------------\n";
foreach $key (reverse sort {$rangStud{$a} cmp $rangStud{$b}} keys %rangStud){
   print "$key : $rangStud{$key}\n";
}


