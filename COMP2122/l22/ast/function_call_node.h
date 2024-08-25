#ifndef __L22_AST_FUNCTION_CALL_H__
#define __L22_AST_FUNCTION_CALL_H__

#include <string>
#include <cdk/ast/basic_node.h>
#include <cdk/ast/sequence_node.h>
#include <cdk/ast/expression_node.h>

namespace l22 {

  /**
   * Class for describing function call nodes.
   *
   */
  class function_call_node: public cdk::expression_node {
    cdk::expression_node *_expression;
    cdk::sequence_node *_arguments;

  public:
    /**
     * Constructor for a function call that has arguments.
     */
    function_call_node(int lineno, cdk::expression_node *expression, cdk::sequence_node *arguments) :
        cdk::expression_node(lineno), _expression(expression), _arguments(arguments) {
    }

  public:
    cdk::expression_node *expression() {
      return _expression;
    }
    cdk::sequence_node *arguments() {
      return _arguments;
    }
    cdk::expression_node *argument(size_t ax) {
      return dynamic_cast<cdk::expression_node*>(_arguments->node(ax));
    }

    void accept(basic_ast_visitor *sp, int level) {
      sp->do_function_call_node(this, level);
    }

  };

} // l22

#endif
