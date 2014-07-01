#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <errno.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/wait.h>
#include <unistd.h>

#define MAXREAD 100  /* najveca duljina poruke*/

#define READING     0
#define WRITING     1

int main(void)
{
    int     pipeToBCInput[2], pipeToBCOutput[2];
    int     fdNew1, fdNew2, fdNew3;
    int     i, c, readBytes = 0;

    char    zadaci[6][20] = { "2+*3\n", "2+2\n", "3+3\n", "8+6\n", "111+222\n", "10/5\n" };
    int     brojZad = 6;
    

    // stvaramo dva cjevovoda
    if (pipe(pipeToBCInput) == -1) {
        puts("Error creating input pipeline!");
        exit(1);
    }

    if (pipe(pipeToBCOutput) == -1) {
        puts("Error creating output pipeline!");
        exit(1);
    }

    switch (fork())
    {
        case -1:
            puts("Error creating child process!");
            exit(1);

        case 0:
            close(0);                                   // najprije zatvaramo standardni ulaz
            fdNew1 = dup(pipeToBCInput[READING]);       // zatim dupliramo  opisnik datoteke za èitanje iz cjevovoda

            if( fdNew1 == -1 ) puts("Error duplicating std.input!");

            close(1);                                   // zatim zatvaramo standardni izlaz
            fdNew2 = dup(pipeToBCOutput[WRITING]);

            if( fdNew2 == -1 ) puts("Error duplicating std.output!");

            
            close(2);
            fdNew3 = dup(pipeToBCOutput[WRITING]);

            close(pipeToBCInput[WRITING]);              // zatvaramo kraj za pisanje jer nam u tom cjevovodu ne treba
            close(pipeToBCOutput[READING]);             // zatvaramo kraj za èitanje koji nam ne treba na ovom cjevovodu
			close(pipeToBCInput[READING]);              // zatvaramo kraj za èitanje
            close(pipeToBCOutput[WRITING]);             // zatvaramo na drugom cjevovodu kraj za pisanje

            // na kraju kreiramo proces bc
            if( execlp("bc", "bc", NULL) == -1 )
                puts("Error creating BC process");

            exit(0);

        default:
            close(pipeToBCInput[READING]);              // zatvaramo kraj za èitanje
            close(pipeToBCOutput[WRITING]);             // zatvaramo na drugom cjevovodu kraj za pisanje

            
    }
	for(i=0; i<brojZad; i++ )               // u sad idemo u cjevovod poslati redom sve zadatke
            {
				char    msgBuff[MAXREAD] = "";
				char	solution[MAXREAD] ="";
                printf("Zadatak glasi: %s", zadaci[i]);
                printf("Unesite rjesenje = ");
                gets(solution);

                c = write(pipeToBCInput[WRITING], zadaci[i], strlen(zadaci[i]));

                //printf("Write returned %d\n", c);;

                //do {
                    readBytes = read(pipeToBCOutput[READING], msgBuff, MAXREAD);
                //} while( readBytes < 2 );

                //msgBuff[readBytes] = 0;

                //printf("Read bytes: %d\n", readBytes);
                //printf("bc result = %s\n", msgBuff);
				//printf("%c", msgBuff[0]);
				
                if( msgBuff[0] =='s' )                 // s time znakom uvijek pocinje prijava greske u izrazu
                {
                    puts("NEISPRAVAN IZRAZ\n");
                    
                    readBytes = read(pipeToBCOutput[READING], msgBuff, MAXREAD);
                    //msgBuff[readBytes] = 0;
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
    exit(0);
}
