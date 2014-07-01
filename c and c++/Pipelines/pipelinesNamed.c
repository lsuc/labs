#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <errno.h>
#include <sys/stat.h>
#include <sys/types.h>

#include <fcntl.h>

#define MAXREAD 20  

#define READING     0
#define WRITING     1

int main(void)
{
    int     i, readBytes = 0;
    int     fdNew1, fdNew2, fdNew3, pfIn, pfOut;

    char    zadaci[3][20] = { "5+1\n", "1-2\n", "2+*3\n" };
    int     brojZad = 3;
    char    msgBuff[MAXREAD], solution[MAXREAD];

    // stvaramo dva cjevovoda
    unlink("./cjevIn");
    unlink("./cjevOut");

    if( mknod("./cjevIn", S_IFIFO | 00600, 0) == -1 ) {
        puts("Error creating In pipeline");
        exit(0);
    }

    if( mknod("./cjevOut", S_IFIFO | 00600, 0) == -1 ) {
        puts("Error creating In pipeline");
        exit(0);
    }

    switch (fork()) {
        case -1:
            puts("Error creating child!");
            exit(1);

        case 0:
            pfIn = open("./cjevIn", O_RDONLY);          // najprije otvaramo datoteku za cjevovod
            close(0);                                   // zatim zatvaramo standardni ulaz
            fdNew1 = dup(pfIn);                         // pa dupliramo opisnik datoteke za èitanje iz cjevovoda
            if( fdNew1 == -1 )
                puts("Error duplicating std.input!");

            pfOut = open("./cjevOut", O_WRONLY);        // otvaramo datoteku za drugi (izlazni) cjevovod
            close(1);                                   // zatim zatvaramo standardni izlaz
            fdNew2 = dup(pfOut);                        // i dupliramo opisnik datoteke za pisanje u cjevovod
            if( fdNew2 == -1 )
                puts("Error duplicating std.output!");

            
            close(2);
            fdNew3 = dup(pfOut);
            if( fdNew3 == -1 )
                puts("Error duplicating std.err!");

            if( execlp("bc", "bc", NULL) == -1 )        // na kraju kreiramo proces bc
                puts("Error creating BC process");

            exit(0);

        default:
            pfIn = open("./cjevIn", O_WRONLY);
            pfOut = open("./cjevOut", O_RDONLY);

            puts("close finished");

            for(i=0; i<brojZad; i++ )                   // sad idemo u cjevovod poslati redom sve zadatke
            {
                printf("Zadatak glasi: %s", zadaci[i]);
                printf("Unesite rjesenje = ");
                gets(solution);

                int c = write(pfIn, zadaci[i], strlen(zadaci[i]));

                do {
                    readBytes = read(pfOut, msgBuff, MAXREAD);
                } while( readBytes < 2 );

                msgBuff[readBytes] = 0;

                if( msgBuff[0] == '(' )                 // s tim znakom uvijek pocinje prijava greske u izrazu
                {
                    puts("NEISPRAVAN IZRAZ\n");

                    // moramo procitati još jednu poruku jer "syntax error" bc javlja u drugoj poruci - na windowsima neki problem
                    readBytes = read(pfOut, msgBuff, MAXREAD);
                    msgBuff[readBytes] = 0;
                }
                else
                {
                    int bcRes = atoi(msgBuff);
                    int userRes = atoi(solution);

                    if( bcRes == userRes )
                        puts("ISPRAVNO\n");
                    else
                        printf("NEISPRAVNO, toèan odgovor je %s\n", msgBuff);
                }
            }
    }

    exit(0);/* zatvara sve deskriptore */
}

