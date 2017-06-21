#!/bin/sh

##############################################################################
##                                                                          ##
##  This is a template for writing Subversion bug reproduction scripts.     ##
##                                                                          ##
##  It creates a repository containing the standard Greek Tree (see         ##
##  http://svn.apache.org/repos/asf/subversion/trunk/subversion/tests/greek-tree.txt) ##
##  and checks out a working copy containing that tree.  Please adjust      ##
##  this script however you need to to demonstrate your bug.  When it's     ##
##  ready, post the bug report to dev@subversion.apache.org -- after        ##
##  http://subversion.apache.org/docs/community-guide/issues.html#reporting-bugs, ##
##  of course.                                                              ##
##                                                                          ##
##############################################################################

# You might need to adjust these lines to point to your
# compiled-from-source Subversion binaries, if using those:
if [ -z "$SVN" ]; then
  SVN=`which svn`
  SVNSERVE=`which svnserve`
  SVNADMIN=`which svnadmin`
fi

# Make sure we don't use $HOME/.subversion/.
SVN="${SVN} --config-dir=`pwd`/svn-repro-config-dir"
SVNADMIN="${SVNADMIN} --config-dir=`pwd`/svn-repro-config-dir"
SVNSERVE="${SVNSERVE} --config-dir=`pwd`/svn-repro-config-dir"

# Use English output.
LC_ALL=C; export LC_ALL

# Select an access method.  If svn://, the svnserve setup is
# handled automagically by this script; but if http://, then
# you'll have to configure it yourself first.
# 
# URL=http://localhost/SOMETHING/repos
# URL=svn://localhost/repos
URL=file:///`pwd`/repos

if [ -e repos ]; then rm -rf repos; fi
if [ -e wc ]; then rm -rf wc; fi
if [ -e import-me ]; then rm -rf import-me; fi

${SVNADMIN} create repos

# These are for svnserve only.
echo "[general]" > repos/conf/svnserve.conf
echo "anon-access = write" >> repos/conf/svnserve.conf
echo "auth-access = write" >> repos/conf/svnserve.conf

# The server will only be contacted if $URL is svn://foo, of course.
${SVNSERVE} --pid-file svnserve-pid -d -r `pwd`
# And put the kill command in a file, in case need to run it manually.
echo "kill -9 `cat svnserve-pid`" > k
chmod a+rwx k

echo "### Making a Greek Tree for import..."
mkdir import-me
mkdir import-me/trunk
mkdir import-me/tags
mkdir import-me/branches
mkdir import-me/trunk/A
mkdir import-me/trunk/A/B/
mkdir import-me/trunk/A/C/
mkdir import-me/trunk/A/D/
mkdir import-me/trunk/A/B/E/
mkdir import-me/trunk/A/B/F/
mkdir import-me/trunk/A/D/G/
mkdir import-me/trunk/A/D/H/
echo "This is the file 'iota'."        > import-me/trunk/iota
echo "This is the file 'A/mu'."        > import-me/trunk/A/mu
echo "This is the file 'A/B/lambda'."  > import-me/trunk/A/B/lambda
echo "This is the file 'A/B/E/alpha'." > import-me/trunk/A/B/E/alpha
echo "This is the file 'A/B/E/beta'."  > import-me/trunk/A/B/E/beta
echo "This is the file 'A/D/gamma'."   > import-me/trunk/A/D/gamma
echo "This is the file 'A/D/G/pi'."    > import-me/trunk/A/D/G/pi
echo "This is the file 'A/D/G/rho'."   > import-me/trunk/A/D/G/rho
echo "This is the file 'A/D/G/tau'."   > import-me/trunk/A/D/G/tau
echo "This is the file 'A/D/H/chi'."   > import-me/trunk/A/D/H/chi
echo "This is the file 'A/D/H/omega'." > import-me/trunk/A/D/H/omega
echo "This is the file 'A/D/H/psi'."   > import-me/trunk/A/D/H/psi
echo "### Done."
echo ""
echo "### Importing it..."
(cd import-me; ${SVN} import -q -m "Initial import." ${URL})
echo "### Done."
echo ""

${SVN} co -q ${URL}/trunk wc

cd wc
echo "### This is where your reproduction recipe goes. ###"
cd ..

# Put kill command in a file, in case need to run it manually.
echo "kill -9 `cat svnserve-pid`" > k
chmod a+rwx k
./k
