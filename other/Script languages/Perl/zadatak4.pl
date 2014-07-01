#!/usr/bin/perl

$index = 0;

while(<>){

	if(! m/JMBAG;Prezime;Ime;Termin;/) {
	
		@podaci = split /\ /;
	
	
		@student = split /;/, $podaci[0];
		@mjestoDat = split /;/, $podaci[3];
	
		@pocetak = split/:/,$podaci[1];
		$pocSat = $pocetak[0];
		$pocMin = $pocetak[1];
	
		@kraj = split/:/,$podaci[4];
		$krajSat =$kraj[0];
		$krajMin = $kraj[1];
		$krajSec = $kraj[2];
	
	
		if(( ($krajSat - $pocSat) > 1 ) || ($student[3] ne $mjestoDat[1]) || ((($krajSat - $pocSat) ==1) &&($krajMin != 0 || $krajSec != 0))){ 
		
		
		
			$nezakljucani[$index] = "$student[0] $student[1] $student[2] - PROBLEM: $student[3] $podaci[1] --> $mjestoDat[1] $podaci[4]";
			$index+=1;
		}
	}
		
}
foreach $stud ( @nezakljucani){
		print "$stud";
	}