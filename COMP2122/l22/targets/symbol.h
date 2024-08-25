#ifndef __L22_TARGETS_SYMBOL_H__
#define __L22_TARGETS_SYMBOL_H__

#include <string>
#include <memory>
#include <cdk/types/basic_type.h>

namespace l22 {

  class symbol {
    std::string _name;
    int _value; // hack!

    int _qualifier;
    std::shared_ptr<cdk::basic_type> _type;
    bool _initialized;
    int _offset = 0; // 0 (zero) means global variable/function

  public://TODO check if value is 0
    symbol(int qualifier, std::shared_ptr<cdk::basic_type> type, const std::string &name, bool initialized) :
        _name(name), _value(0), _qualifier(qualifier), _type(type), _initialized(initialized){
    }

    virtual ~symbol() {
      // EMPTY
    }

    std::shared_ptr<cdk::basic_type> type() const {
      return _type;
    }

    void set_type(std::shared_ptr<cdk::basic_type> type) {
      _type = type;
    }

    void set_offset(int offset) {
      _offset = offset;
    }

    int offset() const {
      return _offset;
    }

    bool initialized() const {
      return _initialized;
    }

    bool global() const {
      return _offset == 0;
    }

    int qualifier() const {
      return _qualifier;
    }

    bool is_typed(cdk::typename_type name) const {
      return _type->name() == name;
    }

    const std::string &name() const {
      return _name;
    }

    long value() const {
      return _value;
    }

    long value(long v) {
      return _value = v;
    }
  };

  inline auto make_symbol(int qualifier, std::shared_ptr<cdk::basic_type> type, const std::string &name, bool initialized) {
    return std::make_shared<symbol>(qualifier, type, name, initialized);
  }

} // l22

#endif
