RED='\033[0;31m'
GREEN='\033[0;32m'
NC='\033[0m' # No Color

COUNTER=0
TOTAL=0

for FILE in `ls auto-tests`; do

    # Total tests
    TOTAL=$((TOTAL+1))

    # Test name
    echo ${FILE}

    # Increase counter if grep returns a match
    if ./l22/l22 auto-tests/$FILE 2>&1 -o test.xml | grep "** Syntax error"; then
        echo -e "${RED}Boa noite.\nLê o manual de referência.${NC}"
    else
        COUNTER=$((COUNTER+1))
        echo -e "${GREEN}0.75${NC}"
    fi
done

echo "Score: $COUNTER/$TOTAL"
