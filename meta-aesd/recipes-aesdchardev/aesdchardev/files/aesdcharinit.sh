#!/bin/sh

case $1 in
	start)
		start-stop-daemon -S -n initchardriver -a /bin/aesdchar_load
		;;
	stop)
		start-stop-daemon -K -n initchardriver
		start-stop-daemon -S -n unloadchardriver -a /bin/aesdchar_unload
		;;
	*)
		echo "Usage: {start|stop}"
		exit 1
esac
exit 0