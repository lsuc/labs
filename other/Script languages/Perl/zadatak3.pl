#!/usr/bin/perl


while(<>){
	
	@rijeci = split /\ /;
	
	@vrijeme = split/:/,$rijeci[3];
	
	$sat = $vrijeme[0].":".$vrijeme[1];
	$polje{$sat} = $polje{$sat} + 1;
	
	@dat1 = split /:/, $sat;
	@dat2 = split /\[/, $dat1[0];
	
	$datumi{$dat2[1]} += 1;
	
}
my @kljucevi = keys %datumi;
@kljucevi = sort @kljucevi;


foreach $key (@kljucevi) {
	print "Date: $key\n";
	print "sat : broj pristupa\n";
	
	
	
	
	while ( (${key2}, ${value2}) = each %polje ) {
	$_ = ${key2};
	
	if(m/($key)/){
	@pom = split /:/, ${key2};
	$newK = $pom[1];
	$sorted{$newK} = ${value2};
	
	
	
	}
	
}
	

	foreach $kljuc (sort keys %sorted){
   print "$kljuc : $sorted{$kljuc} \n";
}
	foreach $kljuc (keys %sorted) {
		delete $sorted{$kljuc};
	}
}



