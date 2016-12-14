# Another hackjob shell script. loginAlert is meant to be called as one of those 
# on-login programs (there's a directory for those, don't ask me what it is though),
# and what it does is send off a message via email that on [loginDate], [loginIdent] logged
# in from [clientIP].

# Theoretically, it can be considered a final line of security; if tripped by an
# unplanned user, the implication is that someone has gained unauthorized access, and
# the device should be shut down as soon as possible. Delay between script execution
# and message delivery should ideally be minimized.

# Todo: find a way to obfuscate information such that an attacker that somehow
# manages to access either email account or the device running this script does
# not instantly get access to hardcoded contact information. Find a way to minimize
# time between execution of script and delivery of mail; this one may be out of our
# power as it currently stands.

# Preregs and intended use: make sure ssmtp is installed and set up successfully.

# Note: Explore uses for initiating a shutdown command whenever someone accesses the device
# from an IP address outside a certain range. 

loginDate=$(/bin/date)
loginIdent=$USER
clientIP=$(echo $SSH_CLIENT | awk '{print $1}')
echo "$clientIP logged in as $loginIdent on $loginDate." | mail -s LOGIN email@email.email
exit 0
