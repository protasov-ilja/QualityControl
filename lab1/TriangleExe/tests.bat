set PROGRAM=triangle

REM running program without arguments
%PROGRAM%
IF NOT ERRORLEVEL 1 GOTO err

REM running program with one argument
%PROGRAM% 1
IF NOT ERRORLEVEL 1 GOTO err

REM running program with two arguments
%PROGRAM% 1 2
IF NOT ERRORLEVEL 1 GOTO err

REM running program with four arguments
%PROGRAM% 1 2 3 4
IF NOT ERRORLEVEL 1 GOTO err

REM running program without numbers on input
%PROGRAM% a b c
IF NOT ERRORLEVEL 1 GOTO err

REM running program with one no number on input
%PROGRAM% 1 2 str
IF NOT ERRORLEVEL 1 GOTO err

REM running program there first argumenyt is not a number
%PROGRAM% a 2 3
IF NOT ERRORLEVEL 1 GOTO err

REM running program where first num is less then zero 0
%PROGRAM% -1 3.3 5.5
IF NOT ERRORLEVEL 1 GOTO err

REM running program where second num is less then zero 0
%PROGRAM% 1.1 -1 2.2
IF NOT ERRORLEVEL 1 GOTO err

REM running program where third num is less then zero 0
%PROGRAM% 1.1 2.2 -1
IF NOT ERRORLEVEL 1 GOTO err

REM running program where all nums are 0
%PROGRAM% 0 0 0 > tests\out.txt
IF ERRORLEVEL 1 GOTO err
FC /B tests\out.txt tests\not_triangle.txt
IF ERRORLEVEL 1 GOTO err

REM running program where first num is 0
%PROGRAM% 0 2 3 > tests\out.txt
IF ERRORLEVEL 1 GOTO err
FC /B tests\out.txt tests\not_triangle.txt
IF ERRORLEVEL 1 GOTO err

REM running program where second num is 0
%PROGRAM% 1 0 3 > tests\out.txt
IF ERRORLEVEL 1 GOTO err
FC /B tests\out.txt tests\not_triangle.txt
IF ERRORLEVEL 1 GOTO err

REM running program where third num is 0
%PROGRAM% 1 2 0 > tests\out.txt
IF ERRORLEVEL 1 GOTO err
FC /B tests\out.txt tests\not_triangle.txt
IF ERRORLEVEL 1 GOTO err

REM running program where shape is not triangle
%PROGRAM% 1 2 100 > tests\out.txt
IF ERRORLEVEL 1 GOTO err
FC /B tests\out.txt tests\not_triangle.txt
IF ERRORLEVEL 1 GOTO err

REM running program where shape is not triangle
%PROGRAM% 1 100 3 > tests\out.txt
IF ERRORLEVEL 1 GOTO err
FC /B tests\out.txt tests\not_triangle.txt
IF ERRORLEVEL 1 GOTO err

REM running program where shape is not triangle
%PROGRAM% 100 2 3 > tests\out.txt
IF ERRORLEVEL 1 GOTO err
FC /B tests\out.txt tests\not_triangle.txt
IF ERRORLEVEL 1 GOTO err

REM running program where shape is common triangle
%PROGRAM% 3.1 4.1 5.1 > tests\out.txt
IF ERRORLEVEL 1 GOTO err
FC /B tests\out.txt tests\common_triangle.txt
IF ERRORLEVEL 1 GOTO err

REM running program where 1 and 2 sides are equale
%PROGRAM% 4.1 4.1 5.1 > tests\out.txt
IF ERRORLEVEL 1 GOTO err
FC /B tests\out.txt tests\isosceles_triangle.txt
IF ERRORLEVEL 1 GOTO err

REM running program where 2 and 3 sides are equale
%PROGRAM% 4.1 4.1 5.1 > tests\out.txt
IF ERRORLEVEL 1 GOTO err
FC /B tests\out.txt tests\isosceles_triangle.txt
IF ERRORLEVEL 1 GOTO err

REM running program where 3 and 1 sides are equale
%PROGRAM% 4.1 5.1 4.1 > tests\out.txt
IF ERRORLEVEL 1 GOTO err
FC /B tests\out.txt tests\isosceles_triangle.txt
IF ERRORLEVEL 1 GOTO err

REM running program where all sides are equale
%PROGRAM% 3.1 3.1 3.1 > tests\out.txt
IF ERRORLEVEL 1 GOTO err
FC /B tests\out.txt tests\equilateral_triangle.txt
IF ERRORLEVEL 1 GOTO err


ECHO Program testing succeeded :-)

EXIT

:err
ECHO Program testing failed :-(
EXIT