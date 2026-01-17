function usage
	{
	echo ""
	echo "usage: $0 -[cgt]"
	echo ""
	echo "-d make dependency lists"
	echo "-c clean up all the files"
	echo "-g get all the files"
	echo "-h - this message"
	echo "-o list other files in rcs"
	echo "-p print files"
	echo "-s hide all the files"
	echo "-t tell what is out for edit"
	echo "-u tell unused"
	echo "-z zip files"
	}
function get
	{
	for i
	do
		if test ! -f $i
			then co $i
		fi
	done
	}
function clean
	{
	for i
	do
		if test -f $i
			then rcsclean $i
		fi
	done
	}
function print_file
	{
	for i
	do
		tops $i > pq/$i
	done
	}
function yip
	{
	month=`date | awk '{ print $2}'`
	day=`date | awk '{ print $3}'`
	for i
	do
		/pkzip/pkzip -a grb$month$day $i
	done
	}
function zip
	{
	month=`date | awk '{ print $2}'`
	day=`date | awk '{ print $3}'`
	for i
	do
		/pkzip/pkzip -a gra$month$day $i
	done
	}
function other_in_rcs
	{
	for i in rcs/*
	do
		if test ! -f $(basename $i)
			then echo $(basename $i)
		fi
	done
	}
function doit
	{
	action=$1
	shift
	for i
	do
		$action $i
	done
	}

function tell
	{
	for i
	do
		rlog -R -L -l $i
	done
	}

function make_dependency_lists
	{
	for i
	do
		depends $i
	done
	}
function init
	{
	get files.ksh
	. files
	echo `echo $all | wc -w` files
	}
function hide
	{
	for i
	do
		if test -f $i
			then chmod +h $i
		fi
	done
	}
function used
	{
	for i
	do
		grep $i ../*/*.rc > $$.tmp
		if test ! $? -eq 0
			then echo "$i - not used"
		fi
	done
	}

# main entry point

set -- $(getopt cdghopyztsu $*)
if [ $? -ne 0 ]
	then
	usage
	exit 2
fi
program=$0
verbosity=1
while true						# forever
do
	case $1 in 
		-c) init; clean $all;;
		-d) init; make_dependency_lists $sources;;
		-g) init; get $all;;
		-h) usage;;
		-o) init; other_in_rcs;;
		-p) init; print_file $all;;
		-s) init;
			hide $data_files;
			hide $cards $wheel_bitmaps;;
		-t) init; time tell $all;;
		-u) init;
			used $data_files;
			used $cards $wheel_bitmaps;;
		-y) init;
			yip $cards $wheel_bitmaps
			yip $all;
			chmod -w grb$month$day.zip;;
		-z) init;
			zip $all
			zip $cards $wheel_bitmaps
			chmod -w gra$month$day.zip;;
		--) shift; break;;
		\?) usage; exit 2;
	esac
	shift
done
if test -f $$.tmp
	then rm $$.tmp
fi
