%{
    int _startRegNum=0;
%}

%start rules_list

%token DIGIT 


%left '+' '-'
%left '*' '/'

%%

rules_list    :   /*empty */
        |   rules_list beg '\n'
        |   rules_list error '\n'
                {
                    yyerrok();
                }
        ;

beg    :   S
                {
                    
                }
       
        ;

S   :
           S S '*'
                {
			
			System.Console.WriteLine("MUL R"+($1)+" , R"+($2)+" , R"+(_startRegNum));
			
                    $$ = _startRegNum++;
                }
        |   S  S '/'
                {
			
			System.Console.WriteLine("DIV R"+($1)+" , R"+($2)+" , R"+(_startRegNum));
			
                    $$ = _startRegNum++;
                }
        
        |   S  S '+'
                {
			
		System.Console.WriteLine("ADD R"+($1)+" , R"+($2)+" , R"+(_startRegNum));
			
                    $$ = _startRegNum++;
                }
        |   S '-'
                {
			
			System.Console.WriteLine("SUB "+ 0 +" , R"+($1)+" , R"+(_startRegNum));
			
                    $$ = _startRegNum++;
                }
           
        |   number
        ;

number  :   DIGIT

                {
			System.Console.WriteLine("MOVE "+$1+" , R"+(_startRegNum));
			
                    $$ = _startRegNum++;
                   
                }
       
        ;

%%

    Parser() : base(null) { }

    static void Main(string[] args)
    {
        Parser parser = new Parser();
        
        System.IO.TextReader reader;
        if (args.Length > 0)
            reader = new System.IO.StreamReader(args[0]);
        else
            reader = System.Console.In;
            
        parser.Scanner = new Lexer(reader);
        //parser.Trace = true;
        
        parser.Parse();
    }


    class Lexer: QUT.Gppg.AbstractScanner<int,LexLocation>
    {
         private System.IO.TextReader reader;
 
    
         public Lexer(System.IO.TextReader reader)
         {
             this.reader = reader;
         }
    
         public override int yylex()
         {
             char pom;
             int ord = reader.Read();
             
             if (ord == -1)
                 return (int)Tokens.EOF;
             else
                 pom = (char)ord;
    
             if (pom == '\n')
                return pom;
             else if (char.IsWhiteSpace(pom))
                 return yylex();
             else if (char.IsDigit(pom))
             {
                 yylval = pom - '0';
                 return (int)Tokens.DIGIT;
             }
             
             else
                 switch (pom)
                 {
                     case '+':
                     case '-':
                     case '*':
                     case '/':
		     
                    
                         return pom;
                     default:
                         Console.Error.WriteLine("Uneseni znak '{0}' je neispravan", pom);
                         return yylex();
                 }
         }
    
         public override void yyerror(string format, params object[] args)
         {
             Console.Error.WriteLine(format, args);
         }
    }
