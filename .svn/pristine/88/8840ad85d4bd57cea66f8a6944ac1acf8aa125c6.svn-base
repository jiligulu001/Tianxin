@echo off

rem ##############################################################################
rem ##                                                                          ##
rem ##  This is a template for writing Subversion bug reproduction scripts.     ##
rem ##                                                                          ##
rem ##  It creates a repository containing the standard Greek Tree (see         ##
rem ##  http://svn.apache.org/repos/asf/subversion/trunk/subversion/tests/greek-tree.txt) ##
rem ##  and checks out a working copy containing that tree.  Please adjust      ##
rem ##  this script however you need to to demonstrate your bug.  When it's     ##
rem ##  ready, post the bug report to dev@subversion.apache.org -- after        ##
rem ##  http://subversion.apache.org/docs/community-guide/issues.html#reporting-bugs, ##
rem ##  of course.                                                              ##
rem ##                                                                          ##
rem ##############################################################################

:defineCommands
rem You might need to adjust these lines to point to your
rem compiled-from-source Subversion binaries, if using those:
for %%i in (svn.exe) do set SVN="%%~$PATH:i"
for %%i in (svnadmin.exe) do set SVNADMIN="%%~$PATH:i"

:defineUrls
rem Only supported access method: file://. If http:// or svn://, then
rem you'll have to configure it yourself first.
set URL=file:///%CD%/repos
set URL=%URL:\=/%
echo Base url for repo: %URL%

:cleanAllDirsAndCreateRepo
if exist repos rmdir /s /q repos
if exist import-me rmdir /s /q import-me
if exist wc rmdir /s /q wc
%SVNADMIN% create repos

:prepareGreekTree
echo Making a Greek Tree for import...
mkdir import-me
mkdir import-me\trunk
mkdir import-me\tags
mkdir import-me\branches
mkdir import-me\trunk\A
mkdir import-me\trunk\A\B\
mkdir import-me\trunk\A\C\
mkdir import-me\trunk\A\D\
mkdir import-me\trunk\A\B\E\
mkdir import-me\trunk\A\B\F\
mkdir import-me\trunk\A\D\G\
mkdir import-me\trunk\A\D\H\
echo This is the file 'iota'.        > import-me\trunk\iota
echo This is the file 'A\mu'.        > import-me\trunk\A\mu
echo This is the file 'A\B\lambda'.  > import-me\trunk\A\B\lambda
echo This is the file 'A\B\E\alpha'. > import-me\trunk\A\B\E\alpha
echo This is the file 'A\B\E\beta'.  > import-me\trunk\A\B\E\beta
echo This is the file 'A\D\gamma'.   > import-me\trunk\A\D\gamma
echo This is the file 'A\D\G\pi'.    > import-me\trunk\A\D\G\pi
echo This is the file 'A\D\G\rho'.   > import-me\trunk\A\D\G\rho
echo This is the file 'A\D\G\tau'.   > import-me\trunk\A\D\G\tau
echo This is the file 'A\D\H\chi'.   > import-me\trunk\A\D\H\chi
echo This is the file 'A\D\H\omega'. > import-me\trunk\A\D\H\omega
echo This is the file 'A\D\H\psi'.   > import-me\trunk\A\D\H\psi
echo Importing it...
cd import-me
%SVN% import -q -m "Initial import." %URL%
cd ..

:checkoutTrunk
echo Checking out working copy..
%SVN% co -q %URL%/trunk wc

rem This is where your reproduction recipe goes.
rem [some stuff]
goto :eof

