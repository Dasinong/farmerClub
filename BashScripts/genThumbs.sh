#!/bin/bash
if [ $# -ne 2 ]; then
	echo "example usage: ./genThumbs.sh [dir] [depth]"
	exit
fi

testMode=true
while true; do
	read -p "Do you want to run the script in dry-run mode? [y/n]" yn
	case $yn in
		[Yy]* ) testMode=true;break;;
		[Nn]* ) testMode=false;break;;
		* ) echo "Please answer yes or no.";;
	esac
done

for file in `find $1 -maxdepth $2 -mindepth 1 -type f`
do
	fileType=`file --mime-type -b "$file" | awk -F'/' '{print $1}'`
	if [ "x$fileType" == "ximage" ]; then
		# imageSize=`stat -f "%z" "$file"`
		imageDimensions=`identify -format "%W %H" "$file"`
		imageWidth=`echo $imageDimensions | awk '{print $1}'`
		imageHeight=`echo $imageDimensions | awk '{print $2}'`

		if [ $imageWidth -ge 100 ] || [ $imageHeight -ge 100 ]; then
			dirname=$(dirname "$file")
			filename=$(basename "$file")
			extension="${filename##*.}"
			filename="${filename%.*}"

			if [ "$testMode" = true ]; then
				echo "will generate ${dirname}/thumb_${filename}.$extension"
			else
				# what I want to do is resize so that height >= 180 and width >= 140,
				# and then crop the image with dimensions 180x140
				convert "$file" -resize '180x140^' -gravity center -crop 180x140+0+0 +repage "${dirname}/thumb_${filename}.$extension"
			fi
		fi
	fi
done
