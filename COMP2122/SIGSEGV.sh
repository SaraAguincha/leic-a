for FILE in auto-tests/*; do

    # Test name
    echo ${FILE}

    ./l22/l22 $FILE 2>&1 -o xml/$FILE.xml | tail -n 1
done
