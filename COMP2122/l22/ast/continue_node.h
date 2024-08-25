#ifndef __L22_AST_CONTINUE_NODE_H__
#define __L22_AST_CONTINUE_NODE_H__

#include <cdk/ast/basic_node.h>

namespace l22 {

  /**
   * Class for describing continue of nodes.
   */
  class continue_node: public cdk::basic_node {

  public:
    inline continue_node(int lineno) :
        cdk::basic_node(lineno) {
    }

  public:
    void accept(basic_ast_visitor *sp, int level) {
      sp->do_continue_node(this, level);
    }

  };

} // l22

#endif
