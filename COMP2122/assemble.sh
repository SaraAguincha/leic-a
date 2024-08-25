rm -rf auto-tests/output
rm -rf assembly
rm auto-tests/*.asm

mkdir -p auto-tests/output
mkdir -p assembly

# cd l22; make clean; make; cd ..

RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[0;33m'
NC='\033[0m' # No Color

COUNTER=0
TOTAL=0

if [ -z "$1" ]; then
    FILES=`find auto-tests/ -maxdepth 1 -type f | sort`
else
    FILES=$1
fi

for FILE in $FILES; do

    FILE=`basename $FILE`

    TOTAL=$((TOTAL+1))

    echo ${FILE}

    FILE=${FILE%%.*}

    if [ -z "$1" ]; then
        { ./l22/l22 auto-tests/$FILE.l22; } 2>/dev/null 1>/dev/null
    else
        ./l22/l22 auto-tests/$FILE.l22
    fi
    
    yasm -felf32 auto-tests/$FILE.asm 2>/dev/null 1>/dev/null

    if [ -e $FILE.o ]; then
        echo "Sucesfully generated .o"
    else
        echo "Failed to generate .o"
        echo -e "${RED}0.75 :/${NC}"
        continue
    fi

    ld -m elf_i386 -o $FILE $FILE.o -L$HOME/compiladores/root/usr/lib -lrts 2>/dev/null 1>/dev/null

    rm -f $FILE.o

    if [ -e $FILE ]; then
        echo "Sucesfully generated executable"
    else
        echo "Failed to generate executable"
        echo -e "${RED}0.75 :/${NC}"
        continue
    fi

    ./$FILE > auto-tests/output/$FILE_a.out
    tr -d [$'\t\r\n'] < auto-tests/output/$FILE_a.out > auto-tests/output/$FILE.out
    echo >> auto-tests/output/$FILE.out

    #//[$'\t\r\n']

    rm -f $FILE
    rm -f $FILE_a.out

    DIFF=$(diff auto-tests/output/$FILE.out auto-tests/expected/$FILE.out)

    if [ "$DIFF" == "" ]; then
        echo "Correct output!"
        echo -e "${GREEN}1.00 :)${NC}"
        rm auto-tests/$FILE.asm
        COUNTER=$((COUNTER+1))
    else
        echo -e "${YELLOW}Wrong output${NC}"
        echo -e "${RED}0.75 :/${NC}"
        mv auto-tests/$FILE.asm assembly/
    fi

    echo "---------------------"
done

echo "Score: $COUNTER/$TOTAL"