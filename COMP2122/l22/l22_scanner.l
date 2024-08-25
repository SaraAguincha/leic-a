%option c++ prefix="l22_scanner_" outfile="l22_scanner.cpp"
%option stack noyywrap yylineno 8bit debug
%{ 
// make relevant includes before including the parser's tab file
#include <string>
#include <sstream>
#include <cdk/ast/sequence_node.h>
#include <cdk/ast/expression_node.h>
#include <cdk/ast/lvalue_node.h>
#include "l22_parser.tab.h"

static bool restart_functions = false;

static int lLevelsToPop = 0, lIndex = 0;
static std::vector<int> indents;   // known indentation levels

// output stream for building string literals
static std::ostringstream strlit;

inline bool valid(int indent) {
    lLevelsToPop = lIndex = -1;
    bool indentOk = false;
    for (size_t ix = 0; ix < indents.size(); ix++) {
        if (indent == indents[ix]) {
            lLevelsToPop = indents.size() - ix - 1;
            lIndex = ix;
            indentOk = true;
        }
    }
    return indentOk;
}

// don't change this
#define yyerror LexerError
%}
%x X_COMMENT 
%x X_STRING X_BACKSLASH
%x X_NEWLINE
%%
  yydebug=1; set_debug(1);
  {
        if (restart_functions) {
          std::cerr << "POPPING FUNCTIONS (" << indents.size() << ")" << std::endl;
          indents.pop_back();
          if (indents.size() == 0) restart_functions = false;
          return '}';
        }
        else {
          if (lLevelsToPop > 0) {
            std::cerr << "POPPING LEVELS (" << lLevelsToPop << ")" << std::endl;
            lLevelsToPop--;
            indents.pop_back();
            return '}';
          }
        }
  }

  /* ====================================================================== */
  /* ====[                      Indentaçao                            ]==== */
  /* ====================================================================== */

\n                   yy_push_state(X_NEWLINE); /* delay decision */

<X_NEWLINE>^[ ]+  {
    yy_pop_state();

    if (valid(yyleng)) {
          std::cerr << "CASE 1" << std::endl;
          if (lLevelsToPop == 0) {
            // at the same level: ignore index
            // do not return tINDENT;
            std::cerr << "CASE 1a (levels to pop = 0)" << std::endl;
            return ';';
          }
          else {
            // nothing is done here: the scanner will ignore the input while levels > 0
            // and send block-end tokens to the parser
            std::cerr << "CASE 1b (levels to pop = " << lLevelsToPop << ")" << std::endl;
            // at least one has to be returned here...
            if (lLevelsToPop > 0) {
              std::cerr << "POPPING LEVELS (" << lLevelsToPop << ")" << std::endl;
              lLevelsToPop--;
              indents.pop_back();
              return '}';
            }
          }
    }
    else if (indents.size() == 0) {
          // first block to open
          std::cerr << "CASE 2" << std::endl;
          indents.push_back(yyleng);
          return '{';
    }
    else if (yyleng > indents.back()) {
          // this is like the one before, but not the first
          std::cerr << "CASE 3" << std::endl;
          indents.push_back(yyleng);
          return '{';
    }
    else {
          // something wrong: bad indent: communicate to user
          std::cerr << "CASE 4" << std::endl;
          std::cerr << "bad indent at line " << yylineno;
    }
}

<X_NEWLINE>\n      ; // ignore successive newline chars
<X_NEWLINE>.       {
  // in this case, we have a new function starting
  // first, return whatever we are seeing to the input
  yyless(0);
  yy_pop_state();
  if (indents.size() > 0) {
    std::cerr << "should restart " << indents.size() << " functions at " << yylineno << std::endl;
    indents.pop_back();
    if (indents.size() > 0) { restart_functions = true; }
    return '}';
  }
  else return ';'; // top-level declarations
}

  /* ====================================================================== */
  /* ====[                           Tipos                            ]==== */
  /* ====================================================================== */

"int"                       return tINT_TYPE;
"double"                    return tREAL_TYPE;
"text"                      return tSTRING_TYPE;
"void"                      return tVOID_TYPE;

  /* ====================================================================== */
  /* ====[                           Declaraçoes                      ]==== */
  /* ====================================================================== */

"foreign"                   return tFOREIGN;
"use"                       return tUSE;
"public"                    return tPUBLIC;
"var"                       return tVAR;

  /* ====================================================================== */
  /* ====[                           Operadores                       ]==== */
  /* ====================================================================== */

"and"                       return tAND;
"or"                        return tOR;
"not"                       return tNOT;

  /* ====================================================================== */
  /* ====[                           Instruçoes                       ]==== */
  /* ====================================================================== */

"if"                        return tIF;
"then:"                     return tTHEN;
"elif"                      return tELIF;
"else:"                     return tELSE;
"while"                     return tWHILE;
"do:"                       return tDO;
"stop"                      return tSTOP;
"again"                     return tAGAIN;
"write"                     return tWRITE;
"writeln"                   return tWRITELN;
"return"                    return tRETURN;

  /* ====================================================================== */
  /* ====[                          Expressoes                        ]==== */
  /* ====================================================================== */

"input"                     return tINPUT;
"null"                      return tNULL;

  /* ====================================================================== */
  /* ====[                           Outras                           ]==== */
  /* ====================================================================== */

"begin"                     return tBEGIN; 
"end"                       return tEND; 
"sizeof"                    return tSIZEOF;
"->"                        return tARROW;

  /* ====================================================================== */
  /* ====[                         Operadores                         ]==== */
  /* ====================================================================== */

[\[\](),?:]                 return *yytext;
[-+*/%<>=]                  return *yytext;
"not"                       return tNOT;
">="                        return tGE; 
"<="                        return tLE; 
"=="                        return tEQ; 
"!="                        return tNE;



[A-Za-z][A-Za-z0-9_]*  yylval.s = new std::string(yytext); return tIDENTIFIER;

  /* ====================================================================== */
  /* ====[                  Cadeias de Caracteres                     ]==== */
  /* ====================================================================== */

"\""                           yy_push_state(X_STRING);
<X_STRING>\\                   yy_push_state(X_BACKSLASH);
<X_STRING>"\""                 {
                                 yylval.s = new std::string(strlit.str());
                                 strlit.str("");
                                 yy_pop_state();
                                 return tSTRING;
                               }
<X_STRING>.                    strlit << *yytext;

<X_BACKSLASH>n                 strlit << '\n'; yy_pop_state();
<X_BACKSLASH>r                 strlit << '\r'; yy_pop_state();
<X_BACKSLASH>t                 strlit << '\t'; yy_pop_state();
<X_BACKSLASH>\\                strlit << '\\'; yy_pop_state();

<X_BACKSLASH>[0-6]{1,3}      { strlit << (char)(unsigned char)strtoul(yytext, NULL, 7); yy_pop_state(); }
      
<X_BACKSLASH>.                 strlit << *yytext; yy_pop_state();

  /* ====================================================================== */
  /* ====[                         Inteiros                           ]==== */
  /* ====================================================================== */

0[0-6]+                yylval.i = strtoul(yytext, NULL, 7); return tINTEGER;

[0-9]+                 yylval.i = strtoul(yytext, NULL, 10); return tINTEGER;

([0-9]*\.[0-9]+|[0-9]+\.[0-9]*)([eE][-+]?[0-9]+)? { yylval.d = strtod(yytext, NULL); return tREAL; }

  /* ====================================================================== */
  /* ====[                        Comentarios                         ]==== */
  /* ====================================================================== */

";".*\n                     ; /* ignore comment until newline */

"(*"                        yy_push_state(X_COMMENT);
<X_COMMENT>"(*"             yy_push_state(X_COMMENT);
<X_COMMENT>"*)"             yy_pop_state();
<X_COMMENT>.|\n             ; /* ignore comments */

[-()<>=+*/%;{}.]            return *yytext;

  /* ====================================================================== */
  /* ====[                    (other characters)                      ]==== */
  /* ====================================================================== */

<<EOF>>                {
  std::cout << "AT <<EOF>> OPEN INDENTS ARE " << indents.size() << std::endl;
  if (indents.size() > 0) {
    indents.pop_back();
    return '}';
  }
  return 0;  // nothing left to do: tell parser to proceed
}

[[:space:]]                 ; /* ignore (only) whitespace */

.                           yyerror("Unknown character");

%%
