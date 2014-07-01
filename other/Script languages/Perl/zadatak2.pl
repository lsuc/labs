#!/usr/bin/perl

print "Ucitajte niz brojeva odvojenih razmakom:";
$lista = <STDIN>;
chomp($lista);
@brojevi = split /\ /, $lista;

if(@brojevi != 0){
	$zbroj = 0;

	foreach $broj (@brojevi){
	$zbroj += $broj;
}
$broj_elem = $#brojevi+1;
print "Aritmeticka sredina unesenih brojeva je ". $zbroj/$broj_elem;
}
else {
	print "Niste unijeli nijedan broj.";
}