#include <string>
#include "targets/type_checker.h"
#include ".auto/all_nodes.h"  // automatically generated
#include <cdk/types/primitive_type.h>

#ifndef tREQUIRE
#include "l22_parser.tab.h"
#endif

#define ASSERT_UNSPEC { if (node->type() != nullptr && !node->is_typed(cdk::TYPE_UNSPEC)) return; }

//---------------------------------------------------------------------------

void l22::type_checker::processUnaryExpression(cdk::unary_operation_node *const node, int lvl) {
  node->argument()->accept(this, lvl + 2);
  if (!node->argument()->is_typed(cdk::TYPE_INT)) throw std::string("wrong type in argument of unary expression");

  node->type(node->argument()->type());
}

void l22::type_checker::processIntExpression(cdk::binary_operation_node *const node, int lvl) {
  ASSERT_UNSPEC;
  node->left()->accept(this, lvl + 2);
  if (!node->left()->is_typed(cdk::TYPE_INT)) {
    throw std::string("integer expression expected in binary operator (left)");
  }

  node->right()->accept(this, lvl + 2);
  if (!node->right()->is_typed(cdk::TYPE_INT)) {
    throw std::string("integer expression expected in binary operator (right)");
  }

  node->type(cdk::primitive_type::create(4, cdk::TYPE_INT));
}

void l22::type_checker::processIntDouExpression(cdk::binary_operation_node *const node, int lvl) {
  ASSERT_UNSPEC;
  node->left()->accept(this, lvl + 2);
  node->right()->accept(this, lvl + 2);

  if (node->left()->is_typed(cdk::TYPE_DOUBLE) && node->right()->is_typed(cdk::TYPE_DOUBLE)) {
    node->type(cdk::primitive_type::create(8, cdk::TYPE_DOUBLE));
  } else if (node->left()->is_typed(cdk::TYPE_DOUBLE) && node->right()->is_typed(cdk::TYPE_INT)) {
    node->type(cdk::primitive_type::create(8, cdk::TYPE_DOUBLE));
  } else if (node->left()->is_typed(cdk::TYPE_INT) && node->right()->is_typed(cdk::TYPE_DOUBLE)) {
    node->type(cdk::primitive_type::create(8, cdk::TYPE_DOUBLE));
  } else if (node->left()->is_typed(cdk::TYPE_INT) && node->right()->is_typed(cdk::TYPE_INT)) {
    node->type(cdk::primitive_type::create(4, cdk::TYPE_INT));
  } else if (node->left()->is_typed(cdk::TYPE_UNSPEC) && node->right()->is_typed(cdk::TYPE_UNSPEC)) {
    node->type(cdk::primitive_type::create(4, cdk::TYPE_INT));
    node->left()->type(cdk::primitive_type::create(4, cdk::TYPE_INT));
    node->right()->type(cdk::primitive_type::create(4, cdk::TYPE_INT));
  } else {
    throw std::string("wrong types in binary expression");
  }
}

void l22::type_checker::processPtrIntDouExpression(cdk::binary_operation_node *const node, int lvl) {
  ASSERT_UNSPEC;
  node->left()->accept(this, lvl + 2);
  node->right()->accept(this, lvl + 2);

  if (node->left()->is_typed(cdk::TYPE_DOUBLE) && node->right()->is_typed(cdk::TYPE_DOUBLE)) {
    node->type(cdk::primitive_type::create(8, cdk::TYPE_DOUBLE));
  } else if (node->left()->is_typed(cdk::TYPE_DOUBLE) && node->right()->is_typed(cdk::TYPE_INT)) {
    node->type(cdk::primitive_type::create(8, cdk::TYPE_DOUBLE));
  } else if (node->left()->is_typed(cdk::TYPE_INT) && node->right()->is_typed(cdk::TYPE_DOUBLE)) {
    node->type(cdk::primitive_type::create(8, cdk::TYPE_DOUBLE));
  } else if (node->left()->is_typed(cdk::TYPE_POINTER) && node->right()->is_typed(cdk::TYPE_INT)) {
    node->type(node->left()->type());
  } else if (node->left()->is_typed(cdk::TYPE_INT) && node->right()->is_typed(cdk::TYPE_POINTER)) {
    node->type(node->right()->type());
  } else if (node->left()->is_typed(cdk::TYPE_INT) && node->right()->is_typed(cdk::TYPE_INT)) {
    node->type(cdk::primitive_type::create(4, cdk::TYPE_INT));
  } else if (node->left()->is_typed(cdk::TYPE_UNSPEC) && node->right()->is_typed(cdk::TYPE_UNSPEC)) {
    node->type(cdk::primitive_type::create(4, cdk::TYPE_INT));
    node->left()->type(cdk::primitive_type::create(4, cdk::TYPE_INT));
    node->right()->type(cdk::primitive_type::create(4, cdk::TYPE_INT));
  } else {
    throw std::string("wrong types in binary expression");
  }
}

void l22::type_checker::processLogicalExpression(cdk::binary_operation_node *const node, int lvl) {
  ASSERT_UNSPEC;
  node->left()->accept(this, lvl + 2);
  if (!node->left()->is_typed(cdk::TYPE_INT)) {
    throw std::string("integer expression expected in binary logical expression (left)");
  }

  node->right()->accept(this, lvl + 2);
  if (!node->right()->is_typed(cdk::TYPE_INT)) {
    throw std::string("integer expression expected in binary logical expression (right)");
  }

  node->type(cdk::primitive_type::create(4, cdk::TYPE_INT));
}

void l22::type_checker::processComparasionExpression(cdk::binary_operation_node *const node, int lvl){
  ASSERT_UNSPEC;
  node->left()->accept(this, lvl + 2);
  node->right()->accept(this, lvl + 2);

  if (node->left()->is_typed(cdk::TYPE_DOUBLE) && node->right()->is_typed(cdk::TYPE_DOUBLE)) {
    node->type(cdk::primitive_type::create(8, cdk::TYPE_DOUBLE));
  } else if (node->left()->is_typed(cdk::TYPE_DOUBLE) && node->right()->is_typed(cdk::TYPE_INT)) {
    node->type(cdk::primitive_type::create(8, cdk::TYPE_DOUBLE));
  } else if (node->left()->is_typed(cdk::TYPE_INT) && node->right()->is_typed(cdk::TYPE_DOUBLE)) {
    node->type(cdk::primitive_type::create(8, cdk::TYPE_DOUBLE));
  } else if (node->left()->is_typed(cdk::TYPE_INT) && node->right()->is_typed(cdk::TYPE_INT)) {
    node->type(cdk::primitive_type::create(4, cdk::TYPE_INT));
  } else if (node->left()->is_typed(cdk::TYPE_UNSPEC) && node->right()->is_typed(cdk::TYPE_UNSPEC)) {
    node->type(cdk::primitive_type::create(4, cdk::TYPE_INT));
    node->left()->type(cdk::primitive_type::create(4, cdk::TYPE_INT));
    node->right()->type(cdk::primitive_type::create(4, cdk::TYPE_INT));
  } else {
    throw std::string("wrong types in comparation expression");
  }
}

void l22::type_checker::processEqualityExpression(cdk::binary_operation_node *const node, int lvl){
  ASSERT_UNSPEC;
  node->left()->accept(this, lvl + 2);
  node->right()->accept(this, lvl + 2);

  if (node->left()->is_typed(cdk::TYPE_DOUBLE) && node->right()->is_typed(cdk::TYPE_DOUBLE)) {
    node->type(cdk::primitive_type::create(8, cdk::TYPE_DOUBLE));
  } else if (node->left()->is_typed(cdk::TYPE_DOUBLE) && node->right()->is_typed(cdk::TYPE_INT)) {
    node->type(cdk::primitive_type::create(8, cdk::TYPE_DOUBLE));
  } else if (node->left()->is_typed(cdk::TYPE_INT) && node->right()->is_typed(cdk::TYPE_DOUBLE)) {
    node->type(cdk::primitive_type::create(8, cdk::TYPE_DOUBLE));
  } else if (node->left()->is_typed(cdk::TYPE_POINTER) && node->right()->is_typed(cdk::TYPE_INT)) {
    node->type(node->left()->type());
  } else if (node->left()->is_typed(cdk::TYPE_INT) && node->right()->is_typed(cdk::TYPE_POINTER)) {
    node->type(node->right()->type());
  } else if (node->left()->is_typed(cdk::TYPE_INT) && node->right()->is_typed(cdk::TYPE_INT)) {
    node->type(cdk::primitive_type::create(4, cdk::TYPE_INT));
  } else if (node->left()->is_typed(cdk::TYPE_UNSPEC) && node->right()->is_typed(cdk::TYPE_UNSPEC)) {
    node->type(cdk::primitive_type::create(4, cdk::TYPE_INT));
    node->left()->type(cdk::primitive_type::create(4, cdk::TYPE_INT));
    node->right()->type(cdk::primitive_type::create(4, cdk::TYPE_INT));
  } else {
    throw std::string("wrong types in equality expression");
  }
}

//---------------------------------------------------------------------------

void l22::type_checker::do_sequence_node(cdk::sequence_node *const node, int lvl) {
  for (size_t i = 0; i < node->size(); i++)
    node->node(i)->accept(this, lvl);
}

//---------------------------------------------------------------------------

void l22::type_checker::do_nil_node(cdk::nil_node *const node, int lvl) {
  // EMPTY
}
void l22::type_checker::do_data_node(cdk::data_node *const node, int lvl) {
  // EMPTY
}
void l22::type_checker::do_double_node(cdk::double_node *const node, int lvl) {
  ASSERT_UNSPEC;
  node->type(cdk::primitive_type::create(8, cdk::TYPE_DOUBLE));
}
void l22::type_checker::do_not_node(cdk::not_node *const node, int lvl) {
  processUnaryExpression(node, lvl);
}
void l22::type_checker::do_and_node(cdk::and_node *const node, int lvl) {
  processLogicalExpression(node, lvl);
}
void l22::type_checker::do_or_node(cdk::or_node *const node, int lvl) {
  processLogicalExpression(node, lvl);
}

// Delivery 1-----------------------------------------------------------------

void l22::type_checker::do_address_of_node(l22::address_of_node *const node, int lvl) {
  ASSERT_UNSPEC;
  node->argument()->accept(this, lvl + 2);
}

void l22::type_checker::do_nullptr_node(l22::nullptr_node *const node, int lvl) {
  ASSERT_UNSPEC;
  node->type(cdk::reference_type::create(4, nullptr));
}

void l22::type_checker::do_variable_declaration_node(l22::variable_declaration_node *const node, int lvl) {
  
  if (node->initializer() != nullptr) {
    node->initializer()->accept(this, lvl + 2);

    if (node->is_typed(cdk::TYPE_INT)) {
      if (!node->initializer()->is_typed(cdk::TYPE_INT)) {
        throw std::string("wrong type for initializer (integer expected).");
      }

    } 
    else if (node->is_typed(cdk::TYPE_DOUBLE)) {
      if (!node->initializer()->is_typed(cdk::TYPE_INT) && !node->initializer()->is_typed(cdk::TYPE_DOUBLE)) {
        throw std::string("wrong type for initializer (integer or double expected).");
      }

    } 
    else if (node->is_typed(cdk::TYPE_STRING)) {
      if (!node->initializer()->is_typed(cdk::TYPE_STRING)) {
        throw std::string("wrong type for initializer (string expected).");
      }

    } 
    else if (node->is_typed(cdk::TYPE_POINTER)) {
      //DAVID: FIXME: trouble!!!
      if (!node->initializer()->is_typed(cdk::TYPE_POINTER)) {
        auto in = (cdk::literal_node<int>*)node->initializer();
        if (in == nullptr || in->value() != 0) throw std::string("wrong type for initializer (pointer expected).");
      }

    } 
    else if (node->is_typed(cdk::TYPE_UNSPEC)) {
      
      if (!node->initializer()->is_typed(cdk::TYPE_INT)    && !node->initializer()->is_typed(cdk::TYPE_DOUBLE)
       && !node->initializer()->is_typed(cdk::TYPE_STRING) && !node->initializer()->is_typed(cdk::TYPE_POINTER)
       && !node->initializer()->is_typed(cdk::TYPE_FUNCTIONAL)){
        throw std::string("wrong type for initializer (any type expected).");
       }
      else {
        node->type(node->initializer()->type());
      }   
    } 
    else {
      throw std::string("unknown type for initializer.");
    }
  }

  const std::string &id = node->identifier();
  auto symbol = l22::make_symbol(node->qualifier(), node->type(), id, (bool)node->initializer());
  if (_symtab.insert(id, symbol)) {
    _parent->set_new_symbol(symbol);  // advise parent that a symbol has been inserted
  } else {
    throw std::string("variable '" + id + "' redeclared");
  }
}

void l22::type_checker::do_return_node(l22::return_node *const node, int lvl) {
  if (!node->retval()) {
    throw std::string("unknown type for initializer.");
  }
  if (_function->type() != nullptr && _function->is_typed(cdk::TYPE_VOID)) throw std::string(
      "initializer specified for void function.");

  node->retval()->accept(this, lvl + 2);

  if (_function->type() == nullptr) {
    _function->set_type(node->retval()->type());
    return; // simply set the type
  }

  if (_inBlockReturnType == nullptr) {
    _inBlockReturnType = node->retval()->type();
  } else {
    if (_inBlockReturnType != node->retval()->type()) {
      throw std::string("all return statements in a function must return the same type.");
    }
  }

  std::cout << "FUNCT TYPE " << (_function->type() == nullptr ? "auto" : cdk::to_string(_function->type())) << std::endl;
  std::cout << "RETVAL TYPE " << cdk::to_string(node->retval()->type()) << std::endl;

  if (_function->is_typed(cdk::TYPE_INT)) {
    if (!node->retval()->is_typed(cdk::TYPE_INT)) throw std::string("wrong type for initializer (integer expected).");
  } else if (_function->is_typed(cdk::TYPE_DOUBLE)) {
    if (!node->retval()->is_typed(cdk::TYPE_INT) && !node->retval()->is_typed(cdk::TYPE_DOUBLE)) {
      throw std::string("wrong type for initializer (integer or double expected).");
    }
  } else if (_function->is_typed(cdk::TYPE_STRING)) {
    if (!node->retval()->is_typed(cdk::TYPE_STRING)) {
      throw std::string("wrong type for initializer (string expected).");
    }
  } else if (_function->is_typed(cdk::TYPE_POINTER)) {
    // TODO - trouble here and in variable declaration
    int ft = 0, rt = 0;
    auto ftype = _function->type();
    while (ftype->name() == cdk::TYPE_POINTER) {
      ft++;
      ftype = cdk::reference_type::cast(ftype)->referenced();
    }
    auto rtype = node->retval()->type();
    while (rtype != nullptr && rtype->name() == cdk::TYPE_POINTER) {
      rt++;
      rtype = cdk::reference_type::cast(rtype)->referenced();
    }

    std::cout << "FUNCT TYPE " << cdk::to_string(_function->type()) << " --- " << ft << " -- " << ftype->name() << std::endl;
    std::cout << "RETVAL TYPE " << cdk::to_string(node->retval()->type()) << " --- " << rt << " -- " << cdk::to_string(rtype)
        << std::endl;

    bool compatible = (ft == rt) && (rtype == nullptr || (rtype != nullptr && ftype->name() == rtype->name()));
    if (!compatible) throw std::string("wrong type for return expression (pointer expected).");
  } 
}

void l22::type_checker::do_stop_node(l22::stop_node *const node, int lvl) {
  // PURPOSELY EMPTY
}

void l22::type_checker::do_continue_node(l22::continue_node *const node, int lvl) {
  // PURPOSELY EMPTY
}

void l22::type_checker::do_index_node(l22::index_node *const node, int lvl) {
  ASSERT_UNSPEC;
  std::shared_ptr < cdk::reference_type > btype;

  if (node->base()) {
    node->base()->accept(this, lvl + 2);
    btype = cdk::reference_type::cast(node->base()->type());
    if (!node->base()->is_typed(cdk::TYPE_POINTER)) throw std::string("pointer expression expected in index left-value");
  } else {
    btype = cdk::reference_type::cast(_function->type());
    if (!_function->is_typed(cdk::TYPE_POINTER)) throw std::string("return pointer expression expected in index left-value");
  }

  node->index()->accept(this, lvl + 2);
  if (!node->index()->is_typed(cdk::TYPE_INT)) throw std::string("integer expression expected in left-value index");

  node->type(btype->referenced());
}

void l22::type_checker::do_stack_alloc_node(l22::stack_alloc_node *const node, int lvl) {
  node->argument()->accept(this, lvl + 2);
  if (!node->argument()->is_typed(cdk::TYPE_INT)) {
    throw std::string("integer expression expected in allocation expression");
  }
  node->type(cdk::reference_type::create(4, cdk::primitive_type::create(1, cdk::TYPE_VOID)));
}

void l22::type_checker::do_sizeof_node(l22::sizeof_node *const node, int lvl) {
  ASSERT_UNSPEC;
  node->expression()->accept(this, lvl + 2);
  node->type(cdk::primitive_type::create(4, cdk::TYPE_INT));
}

void l22::type_checker::do_block_node(l22::block_node *const node, int lvl) {
  // PURPOSELY EMPTY
}

void l22::type_checker::do_neg_node(cdk::neg_node *const node, int lvl) {
  processUnaryExpression(node, lvl);
}

void l22::type_checker::do_identity_node(l22::identity_node *const node, int lvl) {
  processUnaryExpression(node, lvl);
}

void l22::type_checker::do_function_call_node(l22::function_call_node *const node, int lvl) {
  //TODO
  /*if(!node->expression()->is_typed(cdk::TYPE_FUNCTIONAL)){
    throw std::string("invalid function call");
  }*/

  //std::shared_ptr<cdk::functional_type> functionalType = cdk::functional_type::cast(node->expression()->type());

  // cdk::primitive_type::create(0, cdk::TYPE_VOID)

  /*if(node->arguments()){
    if (node->arguments()->size() == functionalType->input_length()) {
      node->arguments()->accept(this, lvl + 4);
      for (size_t ax = 0; ax < node->arguments()->size(); ax++) {
        if (node->argument(ax)->type() == functionalType->input(ax)) continue;
        //if (functionalType->input(ax)->is_typed(cdk::TYPE_DOUBLE) && node->argument(ax)->is_typed(cdk::TYPE_INT)) continue;
        throw std::string("type mismatch for argument " + std::to_string(ax + 1));
      }
    } 
    else {
      throw std::string(
          "number of arguments in call (" + std::to_string(node->arguments()->size()) + ") must match declaration ("
              + std::to_string(functionalType->input_length()) + ").");
    }
  }*/
}

void l22::type_checker::do_function_definition_node(l22::function_definition_node *const node, int lvl) {
 _inBlockReturnType = nullptr;
 
  std::vector < std::shared_ptr < cdk::basic_type >> argtypes;
  if (node->arguments()) {
    for (size_t ax = 0; ax < node->arguments()->size(); ax++)
      argtypes.push_back(node->argument(ax)->type());
  }

  node->type(cdk::functional_type::create(argtypes, node->type()));
}

//---------------------------------------------------------------------------

void l22::type_checker::do_integer_node(cdk::integer_node *const node, int lvl) {
  ASSERT_UNSPEC;
  node->type(cdk::primitive_type::create(4, cdk::TYPE_INT));
}

void l22::type_checker::do_string_node(cdk::string_node *const node, int lvl) {
  ASSERT_UNSPEC;
  node->type(cdk::primitive_type::create(4, cdk::TYPE_STRING));
}

//----------------------------------------------------------------------------

void l22::type_checker::do_add_node(cdk::add_node *const node, int lvl) {
  processPtrIntDouExpression(node, lvl);
}
void l22::type_checker::do_sub_node(cdk::sub_node *const node, int lvl) {
  processPtrIntDouExpression(node, lvl);
}
void l22::type_checker::do_mul_node(cdk::mul_node *const node, int lvl) {
  processIntDouExpression(node, lvl);
}
void l22::type_checker::do_div_node(cdk::div_node *const node, int lvl) {
  processIntDouExpression(node, lvl);
}
void l22::type_checker::do_mod_node(cdk::mod_node *const node, int lvl) {
  processIntExpression(node, lvl);
}
void l22::type_checker::do_lt_node(cdk::lt_node *const node, int lvl) {
  processComparasionExpression(node, lvl);
}
void l22::type_checker::do_le_node(cdk::le_node *const node, int lvl) {
  processComparasionExpression(node, lvl);
}
void l22::type_checker::do_ge_node(cdk::ge_node *const node, int lvl) {
  processComparasionExpression(node, lvl);
}
void l22::type_checker::do_gt_node(cdk::gt_node *const node, int lvl) {
  processComparasionExpression(node, lvl);
}
void l22::type_checker::do_ne_node(cdk::ne_node *const node, int lvl) {
  processEqualityExpression(node, lvl);
}
void l22::type_checker::do_eq_node(cdk::eq_node *const node, int lvl) {
  processEqualityExpression(node, lvl);
}

//---------------------------------------------------------------------------

void l22::type_checker::do_variable_node(cdk::variable_node *const node, int lvl) {
  ASSERT_UNSPEC;
  const std::string &id = node->name();
  auto symbol = _symtab.find(id);

  if (symbol) {
    node->type(symbol->type());
  } else {
    throw std::string("undeclared variable '" + id + "'");
  }
}

void l22::type_checker::do_rvalue_node(cdk::rvalue_node *const node, int lvl) {
  ASSERT_UNSPEC;
  try {
    node->lvalue()->accept(this, lvl);
    node->type(node->lvalue()->type());
  } catch (const std::string &id) {
    throw "undeclared variable '" + id + "'";
  }
}

void l22::type_checker::do_assignment_node(cdk::assignment_node *const node, int lvl) {
  ASSERT_UNSPEC;

  node->lvalue()->accept(this, lvl + 4);
  node->rvalue()->accept(this, lvl + 4);

  // TODO - should we really be allowed to assign a TYPE_UNSPEC to a left value

  if (node->lvalue()->is_typed(cdk::TYPE_INT)) {
    if (node->rvalue()->is_typed(cdk::TYPE_INT)) {
      node->type(cdk::primitive_type::create(4, cdk::TYPE_INT));
    } else if (node->rvalue()->is_typed(cdk::TYPE_UNSPEC)) {
      node->type(cdk::primitive_type::create(4, cdk::TYPE_INT));
      node->rvalue()->type(cdk::primitive_type::create(4, cdk::TYPE_INT));
    } else {
      throw std::string("wrong assignment to integer");
    }  
  } 
  else if (node->lvalue()->is_typed(cdk::TYPE_POINTER)) {

    //TODO: check pointer level

    if (node->rvalue()->is_typed(cdk::TYPE_POINTER)) {
      node->type(node->rvalue()->type());
    } else if (node->rvalue()->is_typed(cdk::TYPE_INT)) {
      //TODO: check that the integer is a literal and that it is zero
      node->type(cdk::primitive_type::create(4, cdk::TYPE_POINTER));
    } else if (node->rvalue()->is_typed(cdk::TYPE_UNSPEC)) {
      node->type(cdk::primitive_type::create(4, cdk::TYPE_ERROR));
      node->rvalue()->type(cdk::primitive_type::create(4, cdk::TYPE_ERROR));
    } else {
      throw std::string("wrong assignment to pointer");
    }
  } 
  else if (node->lvalue()->is_typed(cdk::TYPE_DOUBLE)) {

    if (node->rvalue()->is_typed(cdk::TYPE_DOUBLE) || node->rvalue()->is_typed(cdk::TYPE_INT)) {
      node->type(cdk::primitive_type::create(8, cdk::TYPE_DOUBLE));
    } else if (node->rvalue()->is_typed(cdk::TYPE_UNSPEC)) {
      node->type(cdk::primitive_type::create(8, cdk::TYPE_DOUBLE));
      node->rvalue()->type(cdk::primitive_type::create(8, cdk::TYPE_DOUBLE));
    } else {
      throw std::string("wrong assignment to real");
    }

  } 
  else if (node->lvalue()->is_typed(cdk::TYPE_STRING)) {

    if (node->rvalue()->is_typed(cdk::TYPE_STRING)) {
      node->type(cdk::primitive_type::create(4, cdk::TYPE_STRING));
    } else if (node->rvalue()->is_typed(cdk::TYPE_UNSPEC)) {
      node->type(cdk::primitive_type::create(4, cdk::TYPE_STRING));
      node->rvalue()->type(cdk::primitive_type::create(4, cdk::TYPE_STRING));
    } else {
      throw std::string("wrong assignment to string");
    }

  } 
  else if (node->lvalue()->is_typed(cdk::TYPE_UNSPEC)) {

    if (node->rvalue()->is_typed(cdk::TYPE_INT)) {
      node->type(cdk::primitive_type::create(4, cdk::TYPE_INT));
    } 
    else if (node->rvalue()->is_typed(cdk::TYPE_DOUBLE)) {
      node->type(cdk::primitive_type::create(8, cdk::TYPE_DOUBLE));
    }
    else if (node->rvalue()->is_typed(cdk::TYPE_STRING)) {
      node->type(cdk::primitive_type::create(4, cdk::TYPE_STRING));
    }
    else if (node->rvalue()->is_typed(cdk::TYPE_POINTER)) {
      node->type(cdk::primitive_type::create(4, cdk::TYPE_POINTER));
    } 
    else {
      throw std::string("wrong assignment to unspeced variable");
    }
  }
  else {
    throw std::string("wrong types in assignment");
  }
}

//---------------------------------------------------------------------------

void l22::type_checker::do_program_node(l22::program_node *const node, int lvl) {
  _inBlockReturnType = nullptr;

  auto mainFunc = l22::make_symbol(tPUBLIC, nullptr, "_main", false);

  _symtab.insert(mainFunc->name(), mainFunc);
  _parent->set_new_symbol(mainFunc);

}

void l22::type_checker::do_evaluation_node(l22::evaluation_node *const node, int lvl) {
  node->argument()->accept(this, lvl + 2);
}

void l22::type_checker::do_print_node(l22::print_node *const node, int lvl) {
  node->arguments()->accept(this, lvl + 2);
}

//---------------------------------------------------------------------------

void l22::type_checker::do_read_node(l22::read_node *const node, int lvl) {
  try {
    node->accept(this, lvl);
  } catch (const std::string &id) {
    throw "undeclared variable '" + id + "'";
  }
}

//---------------------------------------------------------------------------

void l22::type_checker::do_while_node(l22::while_node *const node, int lvl) {
  node->condition()->accept(this, lvl + 4);
}

//---------------------------------------------------------------------------

void l22::type_checker::do_if_node(l22::if_node *const node, int lvl) {
  node->condition()->accept(this, lvl + 4);
  if (!node->condition()->is_typed(cdk::TYPE_INT)) throw std::string("expected integer condition");
}

void l22::type_checker::do_if_else_node(l22::if_else_node *const node, int lvl) {
  node->condition()->accept(this, lvl + 4);
  if (!node->condition()->is_typed(cdk::TYPE_INT)) throw std::string("expected integer condition");
}
