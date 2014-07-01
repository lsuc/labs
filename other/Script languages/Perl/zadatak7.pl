#!/usr/bin/perl

if(@ARGV < 1){	
	die "Potrebno je unijeti prvi znak sifrirane abecede\n";
}


$pom =  ord(shift @ARGV) - ord('A') ;
$pom = 26 - $pom;


while(<>){
	
	@rijec = split //;	
	
	
	$redak ="";
	foreach $znak (@rijec){
		
		
		$ascii = ord(uc $znak);
		
		if((64 < $ascii) && ( $ascii < 91)){
			$slovo = (($ascii+ $pom)-65)%26+65;
			$slovo = chr($slovo);
			$redak = $redak . "$slovo";			
		}
		else {
			$redak = $redak . "$znak";
		}
		
		
		
	
	}
	print "$redak";
	
}