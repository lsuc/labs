// This code was generated by the Gardens Point Parser Generator
// Copyright (c) Wayne Kelly, QUT 2005-2010
// (see accompanying GPPGcopyright.rtf)

// GPPG version 1.4.3
// Machine:  FER-E1E69A6C903
// DateTime: 3.1.2011 21:55:01
// UserName: lsuc
// Input file <jproc.y - 3.1.2011 21:54:52>

// options: no-lines

using System;
using System.Collections.Generic;
using System.Globalization;
using System.Text;
using QUT.Gppg;

public enum Tokens {error=48,
    EOF=49,DIGIT=50};

public class Parser: ShiftReduceParser<int, LexLocation>
{
  // Verbatim content from jproc.y - 3.1.2011 21:54:52
    int _startRegNum=0;
  // End verbatim content from jproc.y - 3.1.2011 21:54:52

#pragma warning disable 649
  private static Dictionary<int, string> aliasses;
#pragma warning restore 649
  private static Rule[] rules = new Rule[12];
  private static State[] states = new State[15];
  private static string[] nonTerms = new string[] {
      "rules_list", "$accept", "beg", "S", "number", };

  static Parser() {
    states[0] = new State(-2,new int[]{-1,1});
    states[1] = new State(new int[]{49,2,48,5,50,14},new int[]{-3,3,-4,7,-5,13});
    states[2] = new State(-1);
    states[3] = new State(new int[]{10,4});
    states[4] = new State(-3);
    states[5] = new State(new int[]{10,6});
    states[6] = new State(-4);
    states[7] = new State(new int[]{45,12,50,14,10,-5},new int[]{-4,8,-5,13});
    states[8] = new State(new int[]{42,9,47,10,43,11,45,12,50,14},new int[]{-4,8,-5,13});
    states[9] = new State(-6);
    states[10] = new State(-7);
    states[11] = new State(-8);
    states[12] = new State(-9);
    states[13] = new State(-10);
    states[14] = new State(-11);

    for (int sNo = 0; sNo < states.Length; sNo++) states[sNo].number = sNo;

    rules[1] = new Rule(-2, new int[]{-1,49});
    rules[2] = new Rule(-1, new int[]{});
    rules[3] = new Rule(-1, new int[]{-1,-3,10});
    rules[4] = new Rule(-1, new int[]{-1,48,10});
    rules[5] = new Rule(-3, new int[]{-4});
    rules[6] = new Rule(-4, new int[]{-4,-4,42});
    rules[7] = new Rule(-4, new int[]{-4,-4,47});
    rules[8] = new Rule(-4, new int[]{-4,-4,43});
    rules[9] = new Rule(-4, new int[]{-4,45});
    rules[10] = new Rule(-4, new int[]{-5});
    rules[11] = new Rule(-5, new int[]{50});
  }

  protected override void Initialize() {
    this.InitSpecialTokens((int)Tokens.error, (int)Tokens.EOF);
    this.InitStates(states);
    this.InitRules(rules);
    this.InitNonTerminals(nonTerms);
  }

  protected override void DoAction(int action)
  {
    switch (action)
    {
      case 4: // rules_list -> rules_list, error, '\n'
{
                    yyerrok();
                }
        break;
      case 5: // beg -> S
{
                    
                }
        break;
      case 6: // S -> S, S, '*'
{
			
			System.Console.WriteLine("MUL R"+(ValueStack[ValueStack.Depth-3])+" , R"+(ValueStack[ValueStack.Depth-2])+" , R"+(_startRegNum));
			
                    CurrentSemanticValue = _startRegNum++;
                }
        break;
      case 7: // S -> S, S, '/'
{
			
			System.Console.WriteLine("DIV R"+(ValueStack[ValueStack.Depth-3])+" , R"+(ValueStack[ValueStack.Depth-2])+" , R"+(_startRegNum));
			
                    CurrentSemanticValue = _startRegNum++;
                }
        break;
      case 8: // S -> S, S, '+'
{
			
		System.Console.WriteLine("ADD R"+(ValueStack[ValueStack.Depth-3])+" , R"+(ValueStack[ValueStack.Depth-2])+" , R"+(_startRegNum));
			
                    CurrentSemanticValue = _startRegNum++;
                }
        break;
      case 9: // S -> S, '-'
{
			
			System.Console.WriteLine("SUB "+ 0 +" , R"+(ValueStack[ValueStack.Depth-2])+" , R"+(_startRegNum));
			
                    CurrentSemanticValue = _startRegNum++;
                }
        break;
      case 11: // number -> DIGIT
{
			System.Console.WriteLine("MOVE "+ValueStack[ValueStack.Depth-1]+" , R"+(_startRegNum));
			
                    CurrentSemanticValue = _startRegNum++;
                   
                }
        break;
    }
  }

  protected override string TerminalToString(int terminal)
  {
    if (aliasses != null && aliasses.ContainsKey(terminal))
        return aliasses[terminal];
    else if (((Tokens)terminal).ToString() != terminal.ToString(CultureInfo.InvariantCulture))
        return ((Tokens)terminal).ToString();
    else
        return CharToString((char)terminal);
  }


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
}
