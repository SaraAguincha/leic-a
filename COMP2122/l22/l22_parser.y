%{
//-- don't change *any* of these: if you do, you'll break the compiler.
#include <algorithm>
#include <memory>
#include <cstring>
#include <cdk/compiler.h>
#include <cdk/types/types.h>
#include ".auto/all_nodes.h"
#define LINE                         compiler->scanner()->lineno()
#define yylex()                      compiler->scanner()->scan()
#define yyerror(compiler, s)         compiler->scanner()->error(s)
//-- don't change *any* of these --- END!
%}

%parse-param {std::shared_ptr<cdk::compiler> compiler}

%union {
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
};

%token <i> tINTEGER
%token <d> tREAL
%token <s> tIDENTIFIER tSTRING
%token tINT_TYPE tREAL_TYPE tSTRING_TYPE tVOID_TYPE
%token tFOREIGN tUSE tPUBLIC tVAR tPRIVATE
%token tAND tOR tNOT
%token tIF tTHEN tELIF tELSE tWHILE tDO tSTOP tAGAIN tWRITE tWRITELN tRETURN
%token tINPUT tNULL
%token tBEGIN tEND tSIZEOF tARROW


%nonassoc tIF tTHEN
%nonassoc tELSE tELIF

%right '='
%left tAND tOR
%left tGE tLE tEQ tNE '>' '<'
%left '+' '-'
%left '*' '/' '%'
%nonassoc tUNARY
%nonassoc '@'

%type <node> program arg instruction if_false block_instruction variable_decl variable_decl_block
%type <sequence> file expressions args opt_expressions opt_variable_decls instructions opt_instructions variable_decls
%type <expression> expression integer real func_def block_expression comp_expression
%type <lvalue> lvalue 
%type <i> qualifier
%type <type> data_type var_type func_type void_type
%type <block> block
%type <s> string
%type <vector> types


%{
//-- The rules below will be included in yyparse, the main parsing function.
%}
%%

file                : opt_variable_decls program       { compiler->ast(new cdk::sequence_node(LINE, $2, $1)); } /* not sure about this*/
                    ;

program	            : tBEGIN block tEND                { $$ = new l22::program_node(LINE, $2); }
                    ;

block               : '{' opt_variable_decls opt_instructions '}'        { $$ = new l22::block_node(LINE, $2, $3); }
                    ;

// TODO - TYPE_UNSPEC should be size 0, but the program_node postfix runs before the var_dec_node type checker, so we dont know how much space should be allocated
var_type            : tVAR                             { $$ = cdk::primitive_type::create(8, cdk::TYPE_UNSPEC); }
                    ;


variable_decl       : qualifier    data_type tIDENTIFIER                 { $$ = new l22::variable_declaration_node(LINE, $1, $2, *$3, nullptr); }
                    |              data_type tIDENTIFIER                 { $$ = new l22::variable_declaration_node(LINE, tPRIVATE,  $1, *$2, nullptr); }
                    | qualifier    data_type tIDENTIFIER '=' expression  { $$ = new l22::variable_declaration_node(LINE, $1, $2, *$3, $5); }
                    |              data_type tIDENTIFIER '=' expression  { $$ = new l22::variable_declaration_node(LINE, tPRIVATE, $1, *$2, $4); }
                    | tFOREIGN     var_type  tIDENTIFIER '=' expression  { $$ = new l22::variable_declaration_node(LINE, tFOREIGN, $2, *$3, $5); }
                    | tPUBLIC      var_type  tIDENTIFIER '=' expression  { $$ = new l22::variable_declaration_node(LINE, tPUBLIC, $2, *$3, $5); }
                    |              var_type  tIDENTIFIER '=' expression  { $$ = new l22::variable_declaration_node(LINE, tPRIVATE, $1, *$2, $4); }
                    | qualifier              tIDENTIFIER '=' expression  { $$ = new l22::variable_declaration_node(LINE, $1, nullptr, *$2, $4 ); } // TODO review tVar stuff
                    ;

variable_decl_block : qualifier    data_type tIDENTIFIER '=' block_expression  { $$ = new l22::variable_declaration_node(LINE, $1, $2, *$3, $5); }
                    |              data_type tIDENTIFIER '=' block_expression  { $$ = new l22::variable_declaration_node(LINE, tPRIVATE, $1, *$2, $4); }
                    | tFOREIGN     var_type  tIDENTIFIER '=' block_expression  { $$ = new l22::variable_declaration_node(LINE, tFOREIGN, $2, *$3, $5); }
                    | tPUBLIC      var_type  tIDENTIFIER '=' block_expression  { $$ = new l22::variable_declaration_node(LINE, tPUBLIC, $2, *$3, $5); }
                    |              var_type  tIDENTIFIER '=' block_expression  { $$ = new l22::variable_declaration_node(LINE, tPRIVATE, $1, *$2, $4); }
                    | qualifier              tIDENTIFIER '=' block_expression  { $$ = new l22::variable_declaration_node(LINE, $1, nullptr, *$2, $4 ); } // TODO review tVar stuff
                    ;

variable_decls      : variable_decl        ';'                                 { $$ = new cdk::sequence_node(LINE, $1);     }
                    | variable_decl        ';'  variable_decls                 { std::reverse($3->nodes().begin(), $3->nodes().end()); 
                                                                                 $$ = new cdk::sequence_node(LINE, $1, $3); 
                                                                                 std::reverse($$->nodes().begin(), $$->nodes().end());}
                    | variable_decl_block                                      { $$ = new cdk::sequence_node(LINE, $1);     }
                    | variable_decl_block       variable_decls                 { std::reverse($2->nodes().begin(), $2->nodes().end());
                                                                                 $$ = new cdk::sequence_node(LINE, $1, $2); 
                                                                                 std::reverse($$->nodes().begin(), $$->nodes().end());}
                    ;

opt_variable_decls  : /* empty */                      { $$ = new cdk::sequence_node(LINE); }
                    | variable_decls                   { $$ = $1; }
                    ;

qualifier           : tFOREIGN                         { $$ = tFOREIGN; }
                    | tUSE                             { $$ = tUSE; }
                    | tPUBLIC                          { $$ = tPUBLIC; }
                    ;

data_type           : tINT_TYPE                        { $$ = cdk::primitive_type::create(4, cdk::TYPE_INT);     }
                    | tREAL_TYPE                       { $$ = cdk::primitive_type::create(8, cdk::TYPE_DOUBLE);  }
                    | tSTRING_TYPE                     { $$ = cdk::primitive_type::create(4, cdk::TYPE_STRING);  }
                    //| tVOID_TYPE                       { $$ = cdk::primitive_type::create(4, cdk::TYPE_VOID);    }
                    | '[' data_type ']'                { $$ = cdk::reference_type::create(4, $2); }
                    //| '[' tVOID_TYPE ']'               { $$ = cdk::reference_type::create(4, cdk::primitive_type::create(4, cdk::TYPE_VOID)); }
                    | func_type                        { $$ = $1; }
                    ;

void_type           : tVOID_TYPE                       { $$ = cdk::primitive_type::create(4, cdk::TYPE_VOID);    }
                    | '[' void_type ']'                { $$ = cdk::reference_type::create(4, $2); }
                    ;

types               :           data_type         { auto vector = new std::vector<std::shared_ptr<cdk::basic_type>>; $$ = vector; }
                    | types ',' data_type         { $$ = $1; $$->push_back($3); }
                    | types ',' void_type         { $$ = $1; $$->push_back($3); }
                    ;

arg                 : data_type   tIDENTIFIER          { $$ = new l22::variable_declaration_node(LINE, tPRIVATE, $1, *$2, nullptr); delete $2; }
                    ;

args                :          arg                     { $$ = new cdk::sequence_node(LINE, $1);     }
                    | args ',' arg                     { $$ = new cdk::sequence_node(LINE, $3, $1); }
                    ;

func_def            : '(' ')'       tARROW    data_type  ':' block    { $$ = new l22::function_definition_node(LINE, $4, nullptr, $6); }
                    | '(' args ')'  tARROW    data_type  ':' block    { $$ = new l22::function_definition_node(LINE, $5, $2, $7);      }
                    | '(' ')'       tARROW    tVOID_TYPE ':' block    { $$ = new l22::function_definition_node(LINE, cdk::primitive_type::create(4, cdk::TYPE_VOID), nullptr, $6); }
                    | '(' args ')'  tARROW    tVOID_TYPE ':' block    { $$ = new l22::function_definition_node(LINE, cdk::primitive_type::create(4, cdk::TYPE_VOID), $2, $7);      }
                    ;

func_type           : data_type '<' types '>'          { $$ = cdk::functional_type::create(*$3, $1); }
                    | data_type '<'       '>'          { $$ = cdk::functional_type::create($1); }    
                    | void_type '<' types '>'          { $$ = cdk::functional_type::create(*$3, $1); }
                    | void_type '<'       '>'          { $$ = cdk::functional_type::create($1); }     
                    ;

opt_instructions    : /* empty */                      { $$ = new cdk::sequence_node(LINE); }
                    | instructions                     { $$ = $1; }
                    ;

instructions        : instruction                       { $$ = new cdk::sequence_node(LINE, $1);     }
                    | block_instruction                 { $$ = new cdk::sequence_node(LINE, $1);     }
                    | instruction  ';'  instructions    { std::reverse($3->nodes().begin(), $3->nodes().end()); 
                                                          $$ = new cdk::sequence_node(LINE, $1, $3); 
                                                          std::reverse($$->nodes().begin(), $$->nodes().end());}
                    | block_instruction instructions    { std::reverse($2->nodes().begin(), $2->nodes().end()); 
                                                          $$ = new cdk::sequence_node(LINE, $1, $2); 
                                                          std::reverse($$->nodes().begin(), $$->nodes().end());}
                    ;
                    

// TODO - make instructions support functions -> comp_expressions instead of expressions
instruction         :           expression                       { $$ = new l22::evaluation_node(LINE, $1); }
                    | tWRITE    expressions                      { $$ = new l22::print_node(LINE, $2, false); }
                    | tWRITELN  expressions                      { $$ = new l22::print_node(LINE, $2, true); }
                    | tAGAIN                                     { $$ = new l22::continue_node(LINE); }
                    | tSTOP                                      { $$ = new l22::stop_node(LINE); }
                    | tRETURN   expression                       { $$ = new l22::return_node(LINE, $2); }
                    | tRETURN                                    { $$ = new l22::return_node(LINE, nullptr); }
                    | block                                      { $$ = $1; }
                    ;

block_instruction   : tIF expression tTHEN block                 { $$ = new l22::if_node(LINE, $2, $4); }
                    | tIF expression tTHEN block if_false        { $$ = new l22::if_else_node(LINE, $2, $4, $5); }
                    | tWHILE '(' expression ')' tDO block        { $$ = new l22::while_node(LINE, $3, $6); }
                    | block_expression                           { $$ = new l22::evaluation_node(LINE, $1); }
                    ;

if_false            : tELSE block                                { $$ = $2; }
                    | tELIF expression tTHEN block               { $$ = new l22::if_node(LINE, $2, $4); }
                    | tELIF expression tTHEN block if_false      { $$ = new l22::if_else_node(LINE, $2, $4, $5); }
                    ;    

comp_expression     : expression                                 { $$ = $1; } 
                    | block_expression                           { $$ = $1; } 
                    ;

// block_expression -> Used for future implementations (future support of more expressions with blocks)
block_expression    : func_def                                   { $$ = $1; }
                    ;

// Functions as expressions are missing
expression          : integer                                    { $$ = $1; }
                    | real                                       { $$ = $1; }
                    | string                                     { $$ = new cdk::string_node(LINE, $1); }
                    | tNULL                                      { $$ = new l22::nullptr_node(LINE); }
                    /* LEFT VALUES */
                    | lvalue                                     { $$ = new cdk::rvalue_node(LINE, $1); }
                    /* ASSIGNMENTS */
                    | lvalue '=' comp_expression                 { $$ = new cdk::assignment_node(LINE, $1, $3); }
                    /* ARITHMETIC EXPRESSIONS */
                    | expression '+' expression                  { $$ = new cdk::add_node(LINE, $1, $3); }
                    | expression '-' expression                  { $$ = new cdk::sub_node(LINE, $1, $3); }
                    | expression '*' expression                  { $$ = new cdk::mul_node(LINE, $1, $3); }
                    | expression '/' expression                  { $$ = new cdk::div_node(LINE, $1, $3); }
                    | expression '%' expression                  { $$ = new cdk::mod_node(LINE, $1, $3); }
                    /* LOGICAL EXPRESSIONS */
                    | expression  '<' expression                 { $$ = new cdk::lt_node(LINE, $1, $3); }
                    | expression tLE  expression                 { $$ = new cdk::le_node(LINE, $1, $3); }
                    | expression tEQ  expression                 { $$ = new cdk::eq_node(LINE, $1, $3); }
                    | expression tGE  expression                 { $$ = new cdk::ge_node(LINE, $1, $3); }
                    | expression  '>' expression                 { $$ = new cdk::gt_node(LINE, $1, $3); }
                    | expression tNE  expression                 { $$ = new cdk::ne_node(LINE, $1, $3); }
                    /* LOGICAL EXPRESSIONS */
                    | expression tAND  expression                { $$ = new cdk::and_node(LINE, $1, $3); }
                    | expression tOR   expression                { $$ = new cdk::or_node (LINE, $1, $3); }
                    /* UNARY EXPRESSION */
                    | '-'  expression %prec tUNARY               { $$ = new cdk::neg_node(LINE, $2); }
                    | '+'  expression %prec tUNARY               { $$ = new l22::identity_node(LINE, $2); }
                    | tNOT expression                            { $$ = new cdk::not_node(LINE, $2); }
                    /* OTHER EXPRESSION */
                    | tINPUT                                     { $$ = new l22::read_node(LINE); }
                    /* OTHER EXPRESSION */
                    | expression '(' opt_expressions ')'         { $$ = new l22::function_call_node(LINE, $1, $3); delete $1; }
                    | tSIZEOF '(' expression ')'                 { $$ = new l22::sizeof_node(LINE, $3); }
                    /* OTHER EXPRESSION */
                    | '(' expression ')'                         { $$ = $2; }
                    | '[' expression ']'                         { $$ = new l22::stack_alloc_node(LINE, $2); }
                    | lvalue '?'                                 { $$ = new l22::address_of_node(LINE, $1); }
                    ;


lvalue              : tIDENTIFIER                                       { $$ = new cdk::variable_node(LINE, *$1); delete $1; }
                    | lvalue '[' expression ']'                         { $$ = new l22::index_node(LINE, new cdk::rvalue_node(LINE, $1), $3); }
                    | '('expression')' '['expression']'                 { $$ = new l22::index_node(LINE, $2, $5); }
                    | expression '('opt_expressions')''['expression']'  { $$ = new l22::index_node(LINE, new l22::function_call_node(LINE, $1, $3), $6); }
                    ;

                    

expressions         : expression                                 { $$ = new cdk::sequence_node(LINE, $1);     }
                    | expressions ',' expression                 { $$ = new cdk::sequence_node(LINE, $3, $1); }
                    ;

opt_expressions     : /* empty */                                { $$ = new cdk::sequence_node(LINE); }
                    | expressions                                { $$ = $1; }
                    ;


integer             : tINTEGER                                   { $$ = new cdk::integer_node(LINE, $1); };
real                : tREAL                                      { $$ = new cdk::double_node(LINE, $1); };
string              : tSTRING                                    { $$ = $1; }
                    | string tSTRING                             { $$ = $1; $$->append(*$2); delete $2; }
                    ;

%%
