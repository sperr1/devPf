# Hackjob attempt at solving the problem "how do I ssh to my raspberry pi
# when I have no domain name?" Solution is to retrieve the device's IP address,
# then communicate it in some fashion. In this case, I opted to use email, and
# for my personal use the email is sending to a phone number, resulting in a
# text message. 

# Todo: find a way to obfuscate information such that an attacker that somehow
# manages to access either email account or the device running this script does
# not instantly get access to hardcoded contact information. Also find a way to
# get ip value without relying on ipinfo.io

# Prereqs and intented use: make sure ssmtp is installed and set up successfully.
# In a further act of hackjob stupidity, I designed this with the intent to be run
# every 12 hours in crontab; Crontab by default likes to send emails when it can, 
# which can result in whatever email service you're using intercepting and potentially
# blacklisting unintentional spam.

# note: the code sorta doesn't always work right on the first run, somehow it doesn't
# preserve the ipval temp value across boots so it mistakenly starts off with the nonmatching
# case in the ifelse branch.
ipval=$(wget http://ipinfo.io/ip -qO -)
echo $ipval
ipCur=$(cat filename) #you cat to whatever you're using as the tmp file to hold the ip
if [ $ipCur = $ipval ]
then
 echo "matching values"
 echo $ipval | mail -s RPi email@email.email #put email here
else
 echo "nonmatching"
 echo "$ipval" > filename #make sure filename is consistent
 echo $ipval | mail -s RPi email@email.email 
fi
exit 0
