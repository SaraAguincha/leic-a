/* A Bison parser, made by GNU Bison 3.5.1.  */

/* Bison interface for Yacc-like parsers in C

   Copyright (C) 1984, 1989-1990, 2000-2015, 2018-2020 Free Software Foundation,
   Inc.

   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <http://www.gnu.org/licenses/>.  */

/* As a special exception, you may create a larger work that contains
   part or all of the Bison parser skeleton and distribute that work
   under terms of your choice, so long as that work isn't itself a
   parser generator using the skeleton or a modified version thereof
   as a parser skeleton.  Alternatively, if you modify or redistribute
   the parser skeleton itself, you may (at your option) remove this
   special exception, which will cause the skeleton and the resulting
   Bison output files to be licensed under the GNU General Public
   License without this special exception.

   This special exception was added by the Free Software Foundation in
   version 2.2 of Bison.  */

/* Undocumented macros, especially those whose name start with YY_,
   are private implementation details.  Do not rely on them.  */

#ifndef YY_YY_L22_PARSER_TAB_H_INCLUDED
# define YY_YY_L22_PARSER_TAB_H_INCLUDED
/* Debug traces.  */
#ifndef YYDEBUG
# define YYDEBUG 1
#endif
#if YYDEBUG
extern int yydebug;
#endif

/* Token type.  */
#ifndef YYTOKENTYPE
# define YYTOKENTYPE
  enum yytokentype
  {
    tINTEGER = 258,
    tREAL = 259,
    tIDENTIFIER = 260,
    tSTRING = 261,
    tINT_TYPE = 262,
    tREAL_TYPE = 263,
    tSTRING_TYPE = 264,
    tVOID_TYPE = 265,
    tFOREIGN = 266,
    tUSE = 267,
    tPUBLIC = 268,
    tVAR = 269,
    tPRIVATE = 270,
    tAND = 271,
    tOR = 272,
    tNOT = 273,
    tIF = 274,
    tTHEN = 275,
    tELIF = 276,
    tELSE = 277,
    tWHILE = 278,
    tDO = 279,
    tSTOP = 280,
    tAGAIN = 281,
    tWRITE = 282,
    tWRITELN = 283,
    tRETURN = 284,
    tINPUT = 285,
    tNULL = 286,
    tBEGIN = 287,
    tEND = 288,
    tSIZEOF = 289,
    tARROW = 290,
    tGE = 291,
    tLE = 292,
    tEQ = 293,
    tNE = 294,
    tUNARY = 295
  };
#endif

/* Value type.  */
#if ! defined YYSTYPE && ! defined YYSTYPE_IS_DECLARED
union YYSTYPE
{
#line 17 "l22_parser.y"

  //--- don't change *any* of these: if you do, you'll break the compiler.
  YYSTYPE() : type(cdk::primitive_type::create(0, cdk::TYPE_VOID)) {}
  ~YYSTYPE() {}
  YYSTYPE(const YYSTYPE &other) { *this = other; }
  YYSTYPE& operator=(const YYSTYPE &other) { type = other.type; return *this; }

  std::shared_ptr<cdk::basic_type> type;        /* expression type */
  //-- don't change *any* of these --- END!

  int                     i;	/* integer value */
  double                  d;	/* double value */
  std::string             *s;	/* symbol name or string literal */
  cdk::basic_node         *node;	/* node pointer */
  cdk::sequence_node      *sequence;
  cdk::expression_node    *expression; /* expression nodes */
  cdk::lvalue_node        *lvalue;
  l22::block_node         *block;
  std::vector<std::shared_ptr<cdk::basic_type>> *vector;

#line 119 "l22_parser.tab.h"

};
typedef union YYSTYPE YYSTYPE;
# define YYSTYPE_IS_TRIVIAL 1
# define YYSTYPE_IS_DECLARED 1
#endif


extern YYSTYPE yylval;

int yyparse (std::shared_ptr<cdk::compiler> compiler);

#endif /* !YY_YY_L22_PARSER_TAB_H_INCLUDED  */
